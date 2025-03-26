package com.example.smartspender;

import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExpenseFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testExpenseFragmentIsDisplayed() {
        // Navigate to ExpenseFragment if needed
        onView(withId(R.id.navigation_expenses)).perform(click());

        // Check if fragment title is displayed
        onView(withText("Your monthly expense is")).check(matches(isDisplayed()));
    }

    @Test
    public void testDatePicker(){
        onView(withId(R.id.navigation_expenses)).perform(click());

        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2025, 3, 19));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_expense_date)).check(matches(withText("19/3/2025")));
    }

    @Test
    public void testDropdown(){
        onView(withId(R.id.navigation_expenses)).perform(click());
        onView(withId(R.id.input_expense_type)).perform(click());

        //checks if these are in the dropdown menu
        onView(withText("Food")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Housing")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Transportation")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Entertainment")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Shopping")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Healthcare")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Education")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Other")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

        onView(withText("Transportation")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.input_expense_type)).check(matches(withText("Transportation")));
    }

    @Test
    public void testAddExpense(){
        // Navigate to ExpenseFragment
        onView(withId(R.id.navigation_expenses)).perform(click());

        // Type Expense amount
        onView(withId(R.id.input_expense_amount)).perform(typeText("1000"), closeSoftKeyboard());

        // Choose date
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withText("OK")).perform(click());  // Confirm selected date

        // Select a category from dropdown
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        // Click the Create Budget button
        onView(withId(R.id.add_expense_button)).perform(click());

        // Verify that the new budget appears in RecyclerView
        onView(withText("Food")).check(matches(isDisplayed()));
    }

    @Test
    public void testMultipleExpenses(){
        // Navigate to ExpenseFragment
        onView(withId(R.id.navigation_expenses)).perform(click());

        onView(withId(R.id.input_expense_amount)).perform(typeText("1000"), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.add_expense_button)).perform(click());

        onView(withId(R.id.input_expense_amount)).perform(typeText("500"), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Healthcare")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.add_expense_button)).perform(click());

        onView(withText("Food")).check(matches(isDisplayed()));
        onView(withText("Healthcare")).check(matches(isDisplayed()));
        onView(withText("$1500")).check(matches(isDisplayed()));
    }
}
