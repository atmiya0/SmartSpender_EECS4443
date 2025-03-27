package com.example.smartspender;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//This is a file to run all tests at the same time.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        IncomeFragmentTest.class,
        ExpenseFragmentTest.class,
        BudgetFragmentTest.class,
        SummaryFragmentTest.class
})
public class SmartSpenderTestSuite {
}
