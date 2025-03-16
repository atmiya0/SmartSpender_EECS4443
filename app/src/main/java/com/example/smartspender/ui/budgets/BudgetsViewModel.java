package com.example.smartspender.ui.budgets;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.smartspender.dao.TransactionDao;
import com.example.smartspender.database.TransactionDatabase;
import com.example.smartspender.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BudgetsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Transaction>> budgets = new MutableLiveData<>(new ArrayList<>());
    private LiveData<List<Transaction>> allTransactions;
    private ExecutorService executorService;
    private TransactionDao transactionDao;

    //Database
    public BudgetsViewModel(Application application) {
        super(application);
        TransactionDatabase db = TransactionDatabase.getInstance(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Transaction>> getBudgets() {
        return budgets;
    }

    public void addBudget(Transaction budget) {
        List<Transaction> currentList = budgets.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateBudget", "Inserting budget into recyclerview");
        currentList.add(budget);
        budgets.setValue(currentList);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public void insert(Transaction transaction) {
        executorService.execute(() -> transactionDao.insert(transaction));
    }

    public void delete(Transaction transaction) {
        executorService.execute(() -> transactionDao.delete(transaction));
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