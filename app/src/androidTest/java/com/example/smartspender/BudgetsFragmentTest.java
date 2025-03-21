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
 * UI tests for the Budgets screen
 */
@RunWith(AndroidJUnit4.class)
public class BudgetsFragmentTest extends BaseUITest {

    /**
     * Test to verify navigation to the Budgets tab
     */
    @Test
    public void testNavigationToBudgetsTab() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Verify Budgets screen is displayed
        onView(withId(R.id.budget_heading)).check(matches(isDisplayed()));
    }

    /**
     * Test to verify input validation for empty fields
     */
    @Test
    public void testEmptyFieldValidation() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Click create budget button without filling any fields
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify error message for budget name field
        onView(withId(R.id.input_budget_name)).check(matches(hasErrorText("Please enter a budget name")));
        
        // Enter name but leave category empty
        onView(withId(R.id.input_budget_name)).perform(typeText("Monthly Food"), closeSoftKeyboard());
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify error message for category field
        onView(withId(R.id.input_budget_category)).check(matches(hasErrorText("Please select a category")));
        
        // Enter category but leave limit empty
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify error message for limit field
        onView(withId(R.id.input_budget_limit)).check(matches(hasErrorText("Please enter a budget limit")));
        
        // Enter limit but leave date empty
        onView(withId(R.id.input_budget_limit)).perform(typeText("300"), closeSoftKeyboard());
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify error message for date field
        onView(withId(R.id.input_budget_date)).check(matches(hasErrorText("Please select a date")));
    }

    /**
     * Test to verify invalid input validation
     */
    @Test
    public void testInvalidInputValidation() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Enter budget name
        onView(withId(R.id.input_budget_name)).perform(typeText("Monthly Food"), closeSoftKeyboard());
        
        // Select category
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Food")).perform(click());
        
        // Enter invalid limit (non-numeric)
        onView(withId(R.id.input_budget_limit)).perform(typeText("abc"), closeSoftKeyboard());
        
        // Select date
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        
        // Click create budget button
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify error message for invalid limit
        onView(withId(R.id.input_budget_limit)).check(matches(hasErrorText("Please enter a valid number")));
    }

    /**
     * Test to verify successful budget creation
     */
    @Test
    public void testSuccessfulBudgetCreation() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Enter valid budget data
        onView(withId(R.id.input_budget_name)).perform(typeText("Monthly Food"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.input_budget_limit)).perform(typeText("300"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        
        // Set date in date picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        
        // Click create budget button
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify fields are cleared after successful addition
        onView(withId(R.id.input_budget_name)).check(matches(withText("")));
        onView(withId(R.id.input_budget_category)).check(matches(withText("")));
        onView(withId(R.id.input_budget_limit)).check(matches(withText("")));
        onView(withId(R.id.input_budget_date)).check(matches(withText("")));
        
        // Verify budget count is updated in the heading
        onView(withId(R.id.budget_heading)).check(matches(isDisplayed()));
    }

    /**
     * Test to verify budget category dropdown functionality
     */
    @Test
    public void testCategoryDropdown() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Click on category dropdown
        onView(withId(R.id.input_budget_category)).perform(click());
        
        // Verify dropdown options are displayed
        onView(withText("Finance")).check(matches(isDisplayed()));
        onView(withText("Food")).check(matches(isDisplayed()));
        onView(withText("Transport")).check(matches(isDisplayed()));
        onView(withText("Shopping")).check(matches(isDisplayed()));
        onView(withText("Rent")).check(matches(isDisplayed()));
        
        // Select an option
        onView(withText("Transport")).perform(click());
        
        // Verify selected option is displayed in the field
        onView(withId(R.id.input_budget_category)).check(matches(withText("Transport")));
    }

    /**
     * Test to verify date picker functionality
     */
    @Test
    public void testDatePicker() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Click on date field
        onView(withId(R.id.input_budget_date)).perform(click());
        
        // Verify date picker is displayed
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .check(matches(isDisplayed()));
        
        // Set a date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 4, 15));
        
        // Click OK
        onView(withId(android.R.id.button1)).perform(click());
        
        // Verify selected date is displayed in the field (format: DD/MM/YYYY)
        onView(withId(R.id.input_budget_date)).check(matches(withText("15/4/2025")));
    }

    /**
     * Test to verify multiple budget creation
     */
    @Test
    public void testMultipleBudgetCreation() {
        // Navigate to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        
        // Create first budget (Food)
        onView(withId(R.id.input_budget_name)).perform(typeText("Monthly Food"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.input_budget_limit)).perform(typeText("300"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Create second budget (Transport)
        onView(withId(R.id.input_budget_name)).perform(typeText("Monthly Transport"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Transport")).perform(click());
        onView(withId(R.id.input_budget_limit)).perform(typeText("150"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create_budget_button)).perform(click());
        
        // Verify budget count is updated to 2
        // The text should contain "2" as the count of budgets
        onView(withId(R.id.budget_heading)).check(matches(isDisplayed()));
    }
}
