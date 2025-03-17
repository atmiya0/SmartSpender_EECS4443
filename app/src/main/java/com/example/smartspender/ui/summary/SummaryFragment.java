package com.example.smartspender.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspender.R;
import com.example.smartspender.adapters.TopExpensesAdapter;
import com.example.smartspender.model.Budget;
import com.example.smartspender.model.Expense;
import com.example.smartspender.model.Income;
import com.example.smartspender.ui.budgets.BudgetsViewModel;
import com.example.smartspender.ui.expenses.ExpensesViewModel;
import com.example.smartspender.ui.income.IncomeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SummaryFragment extends Fragment {

    private RecyclerView recyclerView;
    private TopExpensesAdapter topExpensesAdapter;
    private TextView totalIncomeAmount;
    private TextView totalExpensesAmount;
    private TextView summaryHeading;
    private TextView remainingBudgetAmount;
    private TextView totalBudgetAmount;
    private IncomeViewModel incomeViewModel;
    private ExpensesViewModel expensesViewModel;
    private BudgetsViewModel budgetsViewModel;
    
    // Variables to track totals
    private double totalIncome = 0.0;
    private double totalExpenses = 0.0;
    private double totalBudgetLimit = 0.0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        // Initialize ViewModels with the same scope as the other fragments
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        expensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);
        budgetsViewModel = new ViewModelProvider(this).get(BudgetsViewModel.class);
        
        // Initialize TextViews
        summaryHeading = view.findViewById(R.id.summary_heading);
        totalIncomeAmount = view.findViewById(R.id.total_income_amount);
        totalExpensesAmount = view.findViewById(R.id.total_expenses_amount);
        remainingBudgetAmount = view.findViewById(R.id.text_remaining_amount);
        totalBudgetAmount = view.findViewById(R.id.textTotalBudget);
        
        // Set the dynamic month and year in the heading
        updateMonthYearHeading();
        
        // Load data from databases
        incomeViewModel.getAllIncomes().observe(getViewLifecycleOwner(), incomes -> {
            incomeViewModel.setIncomes(incomes);
        });
        
        expensesViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
            expensesViewModel.setExpenses(expenses);
        });
        
        budgetsViewModel.getAllBudgets().observe(getViewLifecycleOwner(), budgets -> {
            budgetsViewModel.setBudgets(budgets);
        });
        
        // Set up RecyclerView for top expenses
        recyclerView = view.findViewById(R.id.recycler_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Initialize the top expenses adapter
        topExpensesAdapter = new TopExpensesAdapter();
        recyclerView.setAdapter(topExpensesAdapter);
        
        // Update the heading to show "TOP 3 EXPENSES" instead of "TOP 5 EXPENSES"
        TextView transactionHeading = view.findViewById(R.id.transactionHeading);
        transactionHeading.setText("TOP 3 EXPENSES");
        
        // Observe income data changes
        incomeViewModel.getIncomes().observe(getViewLifecycleOwner(), incomes -> {
            // Calculate total income
            totalIncome = 0.0;
            for (Income income : incomes) {
                totalIncome += income.getIncome_amount();
            }
            
            // Update the total income display
            totalIncomeAmount.setText(String.format("$%.0f", totalIncome));
            
            // Update remaining budget calculation
            updateRemainingBudget();
        });
        
        // Observe expense data changes
        expensesViewModel.getExpenses().observe(getViewLifecycleOwner(), expenses -> {
            // Calculate total expenses
            totalExpenses = 0.0;
            for (Expense expense : expenses) {
                totalExpenses += expense.getExpense_amount();
            }
            
            // Update the total expenses display
            totalExpensesAmount.setText(String.format("$%.0f", totalExpenses));
            
            // Update the top expenses adapter with the current expenses list
            topExpensesAdapter.setExpenses(expenses);
            
            // Update remaining budget calculation
            updateRemainingBudget();
        });
        
        // Observe budget data changes
        budgetsViewModel.getBudgets().observe(getViewLifecycleOwner(), budgets -> {
            // Calculate total budget limit
            totalBudgetLimit = 0.0;
            for (Budget budget : budgets) {
                totalBudgetLimit += budget.getBudget_limit();
            }
            
            // Update remaining budget calculation
            updateRemainingBudget();
        });

        return view;
    }
    
    /**
     * Updates the month and year in the heading to show the current month and year
     */
    private void updateMonthYearHeading() {
        Calendar calendar = Calendar.getInstance();
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);
        
        String headingText = String.format("Monthly Summary for %s %d", month, year);
        summaryHeading.setText(headingText);
    }
    
    /**
     * Updates the remaining budget calculation based on total budget limit and total expenses
     */
    private void updateRemainingBudget() {
        // Calculate remaining budget (total budget limit - total expenses)
        double remainingBudget = totalBudgetLimit - totalExpenses;
        
        // Update the UI
        remainingBudgetAmount.setText(String.format("$%.0f", remainingBudget));
        totalBudgetAmount.setText(String.format("/$%.0f", totalBudgetLimit));
    }
}