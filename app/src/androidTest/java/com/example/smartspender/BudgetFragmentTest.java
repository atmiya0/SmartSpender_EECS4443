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
public class BudgetFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBudgetsFragmentIsDisplayed() {
        // Navigate to BudgetsFragment if needed
        onView(withId(R.id.navigation_budgets)).perform(click());

        // Check if fragment title is displayed
        onView(withText("You have 0 active budgets")).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateBudget(){
        // Navigate to BudgetsFragment
        onView(withId(R.id.navigation_budgets)).perform(click());

        // Type budget name
        onView(withId(R.id.input_budget_name)).perform(typeText("Justin"), closeSoftKeyboard());

        // Type budget name
        onView(withId(R.id.input_budget_limit)).perform(typeText("1000"), closeSoftKeyboard());

        // Choose date
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withText("OK")).perform(click());  // Confirm selected date

        // Select a category from dropdown
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Food")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        // Click the Create Budget button
        onView(withId(R.id.create_budget_button)).perform(click());

        // Verify that the new budget appears in RecyclerView
        onView(withText("Justin")).check(matches(isDisplayed()));
    }

    @Test
    public void testFieldCompletion(){
        // Navigate to BudgetsFragment
        onView(withId(R.id.navigation_budgets)).perform(click());

        onView(withId(R.id.input_budget_name)).perform(typeText("John"), closeSoftKeyboard());

        onView(withId(R.id.input_budget_limit)).perform(typeText("30"), closeSoftKeyboard());

        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withText("OK")).perform(click());

        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Finance")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        // Click the Create Budget button
        onView(withId(R.id.create_budget_button)).perform(click());

        onView(withId(R.id.input_budget_name)).check(matches(withText("")));
        onView(withId(R.id.input_budget_category)).check(matches(withText("")));
        onView(withId(R.id.input_budget_date)).check(matches(withText("")));
        onView(withId(R.id.input_budget_limit)).check(matches(withText("")));
    }

    @Test
    public void testMultipleBudgets(){
        onView(withId(R.id.navigation_budgets)).perform(click());

        //create budget for Joe
        onView(withId(R.id.input_budget_name)).perform(typeText("Joe"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_limit)).perform(typeText("300"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Entertainment")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.create_budget_button)).perform(click());

        //create budget for larry
        onView(withId(R.id.input_budget_name)).perform(typeText("Larry"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_limit)).perform(typeText("59.99"), closeSoftKeyboard());
        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_budget_category)).perform(click());
        onView(withText("Shopping")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.create_budget_button)).perform(click());

        //checks if updated
        onView(withText("You have 2 active budgets")).check(matches(isDisplayed()));
        onView(withText("Joe")).check(matches(isDisplayed()));
        onView(withText("$59.99")).check(matches(isDisplayed()));
    }

    @Test
    public void testDatePicker(){
        onView(withId(R.id.navigation_budgets)).perform(click());

        onView(withId(R.id.input_budget_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2025, 3, 19));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.input_budget_date)).check(matches(withText("19/3/2025")));
    }

    @Test
    public void testDropdownCategory(){
        onView(withId(R.id.navigation_budgets)).perform(click());
        onView(withId(R.id.input_budget_category)).perform(click());
        
        //checks if these are in the dropdown menu
        onView(withText("Finance")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Food")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Transport")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Shopping")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Rent")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Utilities")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withText("Entertainment")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));

        onView(withText("Shopping")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.input_budget_category)).check(matches(withText("Shopping")));
    }

    @Test
    public void testEmptyFieldValidation(){
        onView(withId(R.id.navigation_budgets)).perform(click());

        onView(withId(R.id.create_budget_button)).perform(click());
        //checks if error pops up if name is empty
        onView(withId(R.id.input_budget_name)).check(matches(hasErrorText("Please enter a budget name")));
    }
}
