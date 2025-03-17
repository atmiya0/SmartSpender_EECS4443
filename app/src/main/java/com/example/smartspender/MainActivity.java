package com.example.smartspender;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.smartspender.databinding.ActivityMainBinding;
import com.example.smartspender.database.BudgetDatabase;
import com.example.smartspender.database.IncomeDatabase;
import com.example.smartspender.database.ExpenseDatabase;
import com.example.smartspender.dao.BudgetDao;
import com.example.smartspender.dao.IncomeDao;
import com.example.smartspender.dao.ExpenseDao;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ExecutorService executorService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize ExecutorService for database operations
        executorService = Executors.newSingleThreadExecutor();
        
        // Clear all databases when the app starts
        clearAllDatabases();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_summary, R.id.navigation_income, R.id.navigation_expenses,
                R.id.navigation_budgets)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Hide Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    
    /**
     * Clears all databases (Income, Expense, Budget) when the app starts
     * to ensure we're working with fresh data each time
     */
    private void clearAllDatabases() {
        executorService.execute(() -> {
            try {
                // Clear Income database
                IncomeDao incomeDao = IncomeDatabase.getInstance(this).incomeDao();
                incomeDao.deleteAllIncomes();
                Log.d("MainActivity", "Cleared Income database");
                
                // Clear Expense database
                ExpenseDao expenseDao = ExpenseDatabase.getInstance(this).expenseDao();
                expenseDao.deleteAllExpenses();
                Log.d("MainActivity", "Cleared Expense database");
                
                // Clear Budget database
                BudgetDao budgetDao = BudgetDatabase.getInstance(this).budgetDao();
                budgetDao.deleteAllBudgets();
                Log.d("MainActivity", "Cleared Budget database");
            } catch (Exception e) {
                Log.e("MainActivity", "Error clearing databases: " + e.getMessage());
            }
        });
    }
}