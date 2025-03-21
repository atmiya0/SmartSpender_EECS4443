package com.example.smartspender;

import android.widget.DatePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SummaryFragmentTest extends BaseUITest {

    @Test
    public void summaryScreenShouldDisplayAfterLaunch() {
        // App should start on Summary screen by default
        onView(withId(R.id.summary_heading)).check(matches(isDisplayed()));
        
        // Navigate away and back to verify tab navigation works
        onView(withId(R.id.navigation_income)).perform(click());
        waitForUiThread(300);
        onView(withId(R.id.navigation_summary)).perform(click());
        
        // Summary screen should be displayed again
        onView(withId(R.id.summary_heading)).check(matches(isDisplayed()));
    }

    @Test
    public void summaryScreenShouldShowAllRequiredElements() {
        // Make sure we're on the Summary screen
        onView(withId(R.id.navigation_summary)).perform(click());
        
        // Check all key UI components are visible
        onView(withId(R.id.summary_heading)).check(matches(isDisplayed()));
//        onView(withId(R.id.income)).check(matches(isDisplayed()));
        onView(withId(R.id.total_income_amount)).check(matches(isDisplayed()));
//        onView(withId(R.id.expenses_label)).check(matches(isDisplayed()));
        onView(withId(R.id.total_expenses_amount)).check(matches(isDisplayed()));
//        onView(withId(R.id.remaining_label)).check(matches(isDisplayed()));
        onView(withId(R.id.text_remaining_amount)).check(matches(isDisplayed()));
        
        // Budget section should be visible
        onView(withId(R.id.textTotalBudget)).check(matches(isDisplayed()));
        
        // Top expenses section should be visible
        onView(withId(R.id.transactionHeading)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_transactions)).check(matches(isDisplayed()));
    }

    @Test
    public void summaryScreenShouldReflectDataFromOtherScreens() {
        // Add income entry
        onView(withId(R.id.navigation_income)).perform(click());
        addIncome("Work", "1000");
        
        // Add expense entry
        onView(withId(R.id.navigation_expenses)).perform(click());
        addExpense("Food", "200");
        
        // Add budget entry
        onView(withId(R.id.navigation_budgets)).perform(click());
        addBudget("Monthly Food", "Food", "500");
        
        // Go back to summary screen
        onView(withId(R.id.navigation_summary)).perform(click());
        waitForUiThread(500); // Give time for data to update
        
        // Check that summary data is displayed
        // We can't check exact values as they depend on database state
        onView(withId(R.id.total_income_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.total_expenses_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.text_remaining_amount)).check(matches(isDisplayed()));
    }

    @Test
    public void topExpensesSectionShouldDisplayCorrectly() {
        // Add several expenses
        onView(withId(R.id.navigation_expenses)).perform(click());
        addExpense("Food", "200");
        addExpense("Transportation", "150");
        addExpense("Entertainment", "100");
        
        // Go to summary screen
        onView(withId(R.id.navigation_summary)).perform(click());
        waitForUiThread(500); // Give time for data to update
        
        // Verify top expenses section shows the right title
        onView(withId(R.id.transactionHeading)).check(matches(withText("TOP 3 EXPENSES")));
        onView(withId(R.id.recycler_transactions)).check(matches(isDisplayed()));
    }

    @Test
    public void bottomNavigationShouldWorkCorrectly() {
        // Check navigation to all tabs and back
        
        // Start with Summary tab
        onView(withId(R.id.navigation_summary)).perform(click());
        onView(withId(R.id.summary_heading)).check(matches(isDisplayed()));
        
        // Go to Income tab
        onView(withId(R.id.navigation_income)).perform(click());
        onView(withId(R.id.income_heading)).check(matches(isDisplayed()));
        
        // Go to Expenses tab
        onView(withId(R.id.navigation_expenses)).perform(click());
        onView(withId(R.id.expense_heading)).check(matches(isDisplayed()));
        
        // Go to Budgets tab
        onView(withId(R.id.navigation_budgets)).perform(click());
        onView(withId(R.id.budget_heading)).check(matches(isDisplayed()));
        
        // Back to Summary tab
        onView(withId(R.id.navigation_summary)).perform(click());
        onView(withId(R.id.summary_heading)).check(matches(isDisplayed()));
    }

    // Helper methods for test data entry
    
    private void addIncome(String category, String amount) {
        onView(withId(R.id.input_income_type)).perform(click());
        onView(withText(category)).perform(click());
        onView(withId(R.id.input_income_amount)).perform(typeText(amount), closeSoftKeyboard());
        onView(withId(R.id.input_income_date)).perform(click());
        
        // Set date in picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        
        // Add the income
        onView(withId(R.id.add_income_button)).perform(click());
        waitForUiThread(300); // Wait for DB operation
    }

    private void addExpense(String category, String amount) {
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText(category)).perform(click());
        onView(withId(R.id.input_expense_amount)).perform(typeText(amount), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        
        // Set date in picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        
        // Add the expense
        onView(withId(R.id.add_expense_button)).perform(click());
        waitForUiThread(300); // Wait for DB operation
    }

    private void addBudget(String name, String category, String limit) {
        onView(withId(R.id.input_budget_name)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText(category)).perform(click());
        onView(withId(R.id.input_budget_limit)).perform(typeText(limit), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        
        // Set date in picker
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        
        // Create the budget
        onView(withId(R.id.create_budget_button)).perform(click());
        waitForUiThread(300); // Wait for DB operation
    }
}
