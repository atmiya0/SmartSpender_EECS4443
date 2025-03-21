package com.example.smartspender.util;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Utility methods for UI testing
 */
public class TestUtils {

    /**
     * Clicks on a child view with specified ID inside a parent view
     */
    public static ViewAction clickChildView(final int childId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on child view with ID: " + childId;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View childView = view.findViewById(childId);
                if (childView != null) {
                    childView.performClick();
                }
            }
        };
    }
    
    /**
     * Matcher for finding a view with a specific tag
     */
    public static Matcher<View> withTag(final Object tag) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                return tag.equals(view.getTag());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with tag: " + tag);
            }
        };
    }

    /**
     * Sets a date on a DatePicker
     */
    public static ViewAction setDate(int year, int month, int day) {
        return PickerActions.setDate(year, month, day);
    }
}
