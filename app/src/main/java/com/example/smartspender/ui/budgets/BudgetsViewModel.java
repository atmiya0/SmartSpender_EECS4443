package com.example.smartspender.ui.budgets;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.smartspender.dao.BudgetDao;
import com.example.smartspender.database.BudgetDatabase;
import com.example.smartspender.model.Budget;
import java.util.ArrayList;
import java.util.List;

// Provides a data repository for BudgetsFragment
public class BudgetsViewModel extends AndroidViewModel {

    // LiveData that UI fragments observe (BudgetsFragment uses this to update RecyclerView).
    // Whenever budgetsLiveData changes, the UI updates automatically.
    private final MutableLiveData<List<Budget>> budgets = new MutableLiveData<>(new ArrayList<>());

    // LiveData that provides a list of all budgets in the database.
    private final LiveData<List<Budget>> allBudgets;

    // ExecutorService allows us to run database operations (insert, delete)
    private final ExecutorService executorService;

    // BudgetDao provides methods for interacting with the database.
    private final BudgetDao budgetDao;


    // Initializes the repository and sets up the ExecutorService.
    public BudgetsViewModel(Application application) {
        super(application);
        BudgetDatabase db = BudgetDatabase.getInstance(application);
        budgetDao = db.budgetDao();
        allBudgets = budgetDao.getAllBudgets();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Returns the LiveData that UI fragments observe.
    public LiveData<List<Budget>> getBudgets() {
        return budgets;
    }

    // Adds a Budget to the list of budgets stored in this ViewModel.

    public void addBudget(Budget budget) {
        List<Budget> currentList = budgets.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateBudget", "Inserting budget into recyclerview");
        currentList.add(budget);
        budgets.setValue(currentList);
    }

    // Returns LiveData that provides a list of all budgets in the database.
    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
    }
    
    //Inserts a Budget into the database
    public void insert(Budget budget) {
        executorService.execute(() -> budgetDao.insert(budget));
    }

    //Deletes a Budget from the database
    public void delete(Budget budget) {
        executorService.execute(() -> budgetDao.delete(budget));
    }
    
}