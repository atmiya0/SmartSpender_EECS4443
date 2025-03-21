package com.example.smartspender;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite that runs all UI tests for the SmartSpender app
 * This provides a convenient way to run all tests together and analyze coverage
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    IncomeFragmentTest.class,
    ExpensesFragmentTest.class,
    BudgetsFragmentTest.class,
    SummaryFragmentTest.class
})
public class SmartSpenderTestSuite {
    // This class serves as a container for the test suite
}
