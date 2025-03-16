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

public class BudgetsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Budget>> budgets = new MutableLiveData<>(new ArrayList<>());
    private LiveData<List<Budget>> allTransactions;
    private ExecutorService executorService;
    private BudgetDao budgetDao;

    //Database
    public BudgetsViewModel(Application application) {
        super(application);
        BudgetDatabase db = BudgetDatabase.getInstance(application);
        budgetDao = db.transactionDao();
        allTransactions = budgetDao.getAllTransactions();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Budget>> getBudgets() {
        return budgets;
    }

    public void addBudget(Budget budget) {
        List<Budget> currentList = budgets.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateBudget", "Inserting budget into recyclerview");
        currentList.add(budget);
        budgets.setValue(currentList);
    }

    public LiveData<List<Budget>> getAllTransactions() {
        return allTransactions;
    }

    public void insert(Budget budget) {
        executorService.execute(() -> budgetDao.insert(budget));
    }

    public void delete(Budget budget) {
        executorService.execute(() -> budgetDao.delete(budget));
    }



//    public BudgetsViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is Budgets fragment");
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
}