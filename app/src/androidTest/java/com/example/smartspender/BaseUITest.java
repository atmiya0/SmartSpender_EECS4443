package com.example.smartspender;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Base class for SmartSpender app UI tests
 * Contains common setup and navigation methods used across test classes
 */
@RunWith(AndroidJUnit4.class)
public abstract class BaseUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    protected ActivityScenario<MainActivity> scenario;

    @Before
    public void setUp() {
        scenario = activityRule.getScenario();
        // Give the app a moment to initialize
        waitForUiThread(500);
    }

    /**
     * Helper method to navigate between tabs in the app
     * @param tabId The resource ID of the tab to navigate to
     */
    protected void navigateToTab(int tabId) {
        Espresso.onView(ViewMatchers.withId(tabId))
                .perform(ViewActions.click());
        
        // Allow time for the fragment transaction to complete
        waitForUiThread(300);
    }
    
    /**
     * Helper method to pause execution for UI thread operations to complete
     * @param millis Time to wait in milliseconds
     */
    protected void waitForUiThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
