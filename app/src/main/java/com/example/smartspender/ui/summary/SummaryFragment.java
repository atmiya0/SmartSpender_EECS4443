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
import com.example.smartspender.adapters.BudgetAdapter;
import com.example.smartspender.model.Budget;
import com.example.smartspender.model.Expense;
import com.example.smartspender.model.Income;
import com.example.smartspender.ui.expenses.ExpensesViewModel;
import com.example.smartspender.ui.income.IncomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BudgetAdapter budgetAdapter;
    private List<Budget> budgetList;
    private TextView totalIncomeAmount;
    private TextView totalExpensesAmount;
    private IncomeViewModel incomeViewModel;
    private ExpensesViewModel expensesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        // Initialize ViewModels with the same scope as the other fragments
        incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        expensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);
        
        // Load data from database
        incomeViewModel.getAllIncomes().observe(getViewLifecycleOwner(), incomes -> {
            incomeViewModel.setIncomes(incomes);
        });
        
        expensesViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
            expensesViewModel.setExpenses(expenses);
        });
        
        // Initialize TextViews for total income and expenses
        totalIncomeAmount = view.findViewById(R.id.total_income_amount);
        totalExpensesAmount = view.findViewById(R.id.total_expenses_amount);
        
        // Set up RecyclerView for top expenses
        recyclerView = view.findViewById(R.id.recycler_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy Transactions for Summary Screen (Top 5 Expenses)
        budgetList = new ArrayList<>();
        budgetList.add(new Budget("Apple Watch", "20 March", 2000));
        budgetList.add(new Budget("Netflix Subscription", "18 March", 15));
        budgetList.add(new Budget("Gym Membership", "15 March", 50));

        budgetAdapter = new BudgetAdapter(budgetList);
        recyclerView.setAdapter(budgetAdapter);
        
        // Observe income data changes
        incomeViewModel.getIncomes().observe(getViewLifecycleOwner(), incomes -> {
            // Calculate total income
            double totalIncome = 0.0;
            for (Income income : incomes) {
                totalIncome += income.getIncome_amount();
            }
            
            // Update the total income display
            totalIncomeAmount.setText(String.format("$%.0f", totalIncome));
        });
        
        // Observe expense data changes
        expensesViewModel.getExpenses().observe(getViewLifecycleOwner(), expenses -> {
            // Calculate total expenses
            double totalExpenses = 0.0;
            for (Expense expense : expenses) {
                totalExpenses += expense.getExpense_amount();
            }
            
            // Update the total expenses display
            totalExpensesAmount.setText(String.format("$%.0f", totalExpenses));
        });

        return view;
    }
}