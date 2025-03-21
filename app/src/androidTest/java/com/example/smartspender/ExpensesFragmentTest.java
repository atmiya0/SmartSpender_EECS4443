package com.example.smartspender;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExpensesFragmentTest extends BaseUITest {

    @Before
    public void goToExpensesScreen() {
        // Start each test on the expenses screen
        onView(withId(R.id.navigation_expenses)).perform(click());
        waitForUiThread(300);
    }
    
    @Test
    public void expensesScreenShouldDisplayCorrectly() {
        // Verify main UI elements are displayed
        onView(withId(R.id.expense_heading)).check(matches(isDisplayed()));
        onView(withId(R.id.input_expense_type)).check(matches(isDisplayed()));
        onView(withId(R.id.input_expense_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.input_expense_date)).check(matches(isDisplayed()));
        onView(withId(R.id.add_expense_button)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowErrorsForEmptyFields() {
        // Try submitting with no data
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Should show category error
        onView(withId(R.id.input_expense_type)).check(matches(hasErrorText("Please select a category")));
        
        // Add category but leave amount empty
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Should show amount error
        onView(withId(R.id.input_expense_amount)).check(matches(hasErrorText("Please enter an amount")));
        
        // Add amount but leave date empty
        onView(withId(R.id.input_expense_amount)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Should show date error
        onView(withId(R.id.input_expense_date)).check(matches(hasErrorText("Please select a date")));
    }

    @Test
    public void shouldRejectInvalidAmountInput() {
        // Enter non-numeric text in amount field
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.input_expense_amount)).perform(typeText("abc"), closeSoftKeyboard());
        
        // Set a date
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        
        // Try to submit
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Should show number format error
        onView(withId(R.id.input_expense_amount)).check(matches(hasErrorText("Please enter a valid number")));
    }

    /**
     * Test to verify successful expense addition
     */
    @Test
    public void testSuccessfulExpenseAddition() {
        // Navigate to Expenses tab
        onView(withId(R.id.navigation_expenses)).perform(click());
        
        // Enter valid expense data
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.input_expense_amount)).perform(typeText("75"), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        
        // Set date in date picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        
        // Click add expense button
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Verify fields are cleared after successful addition
        onView(withId(R.id.input_expense_type)).check(matches(withText("")));
        onView(withId(R.id.input_expense_amount)).check(matches(withText("")));
        onView(withId(R.id.input_expense_date)).check(matches(withText("")));
        
        // Verify expense is added to the list (check for total expense update)
        onView(withId(R.id.total_expenses_amount)).check(matches(isDisplayed()));
    }

    /**
     * Test to verify expense category dropdown functionality
     */
    @Test
    public void testCategoryDropdown() {
        // Navigate to Expenses tab
        onView(withId(R.id.navigation_expenses)).perform(click());
        
        // Click on category dropdown
        onView(withId(R.id.input_expense_type)).perform(click());
        
        // Verify dropdown options are displayed
        onView(withText("Food")).check(matches(isDisplayed()));
        onView(withText("Housing")).check(matches(isDisplayed()));
        onView(withText("Transportation")).check(matches(isDisplayed()));
        onView(withText("Entertainment")).check(matches(isDisplayed()));
        onView(withText("Shopping")).check(matches(isDisplayed()));
        
        // Select an option
        onView(withText("Entertainment")).perform(click());
        
        // Verify selected option is displayed in the field
        onView(withId(R.id.input_expense_type)).check(matches(withText("Entertainment")));
    }

    /**
     * Test to verify date picker functionality
     */
    @Test
    public void testDatePicker() {
        // Navigate to Expenses tab
        onView(withId(R.id.navigation_expenses)).perform(click());
        
        // Click on date field
        onView(withId(R.id.input_expense_date)).perform(click());
        
        // Verify date picker is displayed
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .check(matches(isDisplayed()));
        
        // Set a date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 4, 15));
        
        // Click OK
        onView(withId(android.R.id.button1)).perform(click());
        
        // Verify selected date is displayed in the field (format: DD/MM/YYYY)
        onView(withId(R.id.input_expense_date)).check(matches(withText("15/4/2025")));
    }

    /**
     * Test to verify multiple expense categories
     */
    @Test
    public void testMultipleExpenseCategories() {
        // Navigate to Expenses tab
        onView(withId(R.id.navigation_expenses)).perform(click());
        
        // Add first expense (Food)
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.input_expense_amount)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 21));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Add second expense (Transportation)
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Transportation")).perform(click());
        onView(withId(R.id.input_expense_amount)).perform(typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2025, 3, 22));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.add_expense_button)).perform(click());
        
        // Verify expense total is updated (should show both expenses)
        onView(withId(R.id.total_expenses_amount)).check(matches(isDisplayed()));
    }
}
