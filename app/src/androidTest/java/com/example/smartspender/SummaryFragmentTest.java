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

import java.util.Calendar;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SummaryFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSummaryFragmentIsDisplayed() {
        // Navigate to SummaryFragment if needed
        onView(withId(R.id.navigation_summary)).perform(click());

        Calendar calendar = Calendar.getInstance();
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);

        // Check if fragment title is displayed
        onView(withText(String.format("Monthly Summary for %s %d", month, year))).check(matches(isDisplayed()));
        onView(withText("TOP 3 EXPENSES")).check(matches(isDisplayed()));
    }

    @Test
    public void testSummaryPage(){
        //Create budget
        onView(withId(R.id.navigation_budgets)).perform(click());
        onView(withId(R.id.input_budget_name)).perform(typeText("Jane"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_limit)).perform(typeText("500"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Entertainment")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.create_budget_button)).perform(click());

        //Create Income
        onView(withId(R.id.navigation_income)).perform(click());
        onView(withId(R.id.input_income_amount)).perform(typeText("300"), closeSoftKeyboard());
        onView(withId(R.id.input_income_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_income_type)).perform(click());
        onView(withText("Cash Job")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.add_income_button)).perform(click());

        //Create Expense
        onView(withId(R.id.navigation_expenses)).perform(click());
        onView(withId(R.id.input_expense_amount)).perform(typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.input_expense_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_expense_type)).perform(click());
        onView(withText("Food")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.add_expense_button)).perform(click());

        //Check if Summary is updated
        onView(withId(R.id.navigation_summary)).perform(click());
        onView(withText("Food")).check(matches(isDisplayed()));
        onView(withText("$470")).check(matches(isDisplayed()));
        onView(withText("$300")).check(matches(isDisplayed()));

    }
}
