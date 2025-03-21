package com.example.smartspender;

import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI tests for the Income screen
 */
@RunWith(AndroidJUnit4.class)
public class IncomeFragmentTest extends BaseUITest {

    /**
     * Test to verify navigation to the Income tab
     */
    @Test
    public void testNavigationToIncomeTab() {
        // Navigate to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        
        // Verify Income screen is displayed
        onView(withId(R.id.income_heading)).check(matches(isDisplayed()));
    }

    /**
     * Test to verify input validation for empty fields
     */
    @Test
    public void testEmptyFieldValidation() {
        // Navigate to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        
        // Click add income button without filling any fields
        onView(withId(R.id.add_income_button)).perform(click());
        
        // Verify error message for category field
        onView(withId(R.id.input_income_type)).check(matches(hasErrorText("Please select a category")));
        
        // Enter category but leave amount empty
        onView(withId(R.id.input_income_type)).perform(click());
        onView(withText("Work")).perform(click());
        onView(withId(R.id.add_income_button)).perform(click());
        
        // Verify error message for amount field
        onView(withId(R.id.input_income_amount)).check(matches(hasErrorText("Please enter an amount")));
        
        // Enter amount but leave date empty
        onView(withId(R.id.input_income_amount)).perform(typeText("1000"), closeSoftKeyboard());
        onView(withId(R.id.add_income_button)).perform(click());
        
        // Verify error message for date field
        onView(withId(R.id.input_income_date)).check(matches(hasErrorText("Please select a date")));
    }

    /**
     * Test to verify invalid input validation
     */
    @Test
    public void testInvalidInputValidation() {
        // Navigate to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        
        // Enter invalid amount (non-numeric)
        onView(withId(R.id.input_income_type)).perform(click());
        onView(withText("Work")).perform(click());
        onView(withId(R.id.input_income_amount)).perform(typeText("abc"), closeSoftKeyboard());
        onView(withId(R.id.input_income_date)).perform(click());
        
        // Set date in date picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        
        // Click add income button
        onView(withId(R.id.add_income_button)).perform(click());
        
        // Verify error message for invalid amount
        onView(withId(R.id.input_income_amount)).check(matches(hasErrorText("Please enter a valid number")));
    }

    /**
     * Test to verify successful income addition
     */
    @Test
    public void testSuccessfulIncomeAddition() {
        // Navigate to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        
        // Enter valid income data
        onView(withId(R.id.input_income_type)).perform(click());
        onView(withText("Work")).perform(click());
        onView(withId(R.id.input_income_amount)).perform(typeText("2500"), closeSoftKeyboard());
        onView(withId(R.id.input_income_date)).perform(click());
        
        // Set date in date picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        
        // Click add income button
        onView(withId(R.id.add_income_button)).perform(click());
        
        // Verify fields are cleared after successful addition
        onView(withId(R.id.input_income_type)).check(matches(withText("")));
        onView(withId(R.id.input_income_amount)).check(matches(withText("")));
        onView(withId(R.id.input_income_date)).check(matches(withText("")));
        
        // Verify income is added to the list (check for total income update)
        onView(withId(R.id.total_income_amount)).check(matches(isDisplayed()));
    }

    /**
     * Test to verify dropdown functionality
     */
    @Test
    public void testCategoryDropdown() {
        // Navigate to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        
        // Click on category dropdown
        onView(withId(R.id.input_income_type)).perform(click());
        
        // Verify dropdown options are displayed
        onView(withText("Work")).check(matches(isDisplayed()));
        onView(withText("Freelance")).check(matches(isDisplayed()));
        onView(withText("Cash Job")).check(matches(isDisplayed()));
        onView(withText("Side Hustle")).check(matches(isDisplayed()));
        
        // Select an option
        onView(withText("Freelance")).perform(click());
        
        // Verify selected option is displayed in the field
        onView(withId(R.id.input_income_type)).check(matches(withText("Freelance")));
    }

    /**
     * Test to verify date picker functionality
     */
    @Test
    public void testDatePicker() {
        // Navigate to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        
        // Click on date field
        onView(withId(R.id.input_income_date)).perform(click());
        
        // Verify date picker is displayed
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .check(matches(isDisplayed()));
        
        // Set a date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 4, 15));
        
        // Click OK
        onView(withId(android.R.id.button1)).perform(click());
        
        // Verify selected date is displayed in the field (format: DD/MM/YYYY)
        onView(withId(R.id.input_income_date)).check(matches(withText("15/4/2025")));
    }
}
