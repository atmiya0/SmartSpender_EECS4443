package com.example.smartspender.ui.expenses;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.smartspender.dao.ExpenseDao;
import com.example.smartspender.database.ExpenseDatabase;
import com.example.smartspender.model.Expense;

import java.util.ArrayList;
import java.util.List;

// Provides a data repository for BudgetsFragment
public class ExpensesViewModel extends AndroidViewModel {

    // LiveData that UI fragments observe (BudgetsFragment uses this to update RecyclerView).
    // Whenever budgetsLiveData changes, the UI updates automatically.
    private final MutableLiveData<List<Expense>> expenses = new MutableLiveData<>(new ArrayList<>());

    // LiveData that provides a list of all budgets in the database.
    private final LiveData<List<Expense>> allExpenses;

    // ExecutorService allows us to run database operations (insert, delete)
    private final ExecutorService executorService;

    // BudgetDao provides methods for interacting with the database.
    private final ExpenseDao expenseDao;


    // Initializes the repository and sets up the ExecutorService.
    public ExpensesViewModel(Application application) {
        super(application);
        ExpenseDatabase db = ExpenseDatabase.getInstance(application);
        expenseDao = db.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Returns the LiveData that UI fragments observe.
    public LiveData<List<Expense>> getExpenses() {
        return expenses;
    }

    // Adds a Budget to the list of budgets stored in this ViewModel.
    public void addExpense(Expense expense) {
        List<Expense> currentList = expenses.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateExpense", "Inserting budget into recyclerview");
        currentList.add(expense);
        expenses.setValue(currentList);
    }

    // Returns LiveData that provides a list of all budgets in the database.
    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    //Inserts a Budget into the database
    public void insert(Expense expense) {
        executorService.execute(() -> expenseDao.insert(expense));
    }

    //Deletes a Budget from the database
    public void delete(Expense expense) {
        executorService.execute(() -> expenseDao.delete(expense));
    }

}