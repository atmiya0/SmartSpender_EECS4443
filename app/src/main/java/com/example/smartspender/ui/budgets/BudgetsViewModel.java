package com.example.smartspender.ui.budgets;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartspender.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BudgetsViewModel extends ViewModel {

    private final MutableLiveData<List<Transaction>> budgets = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Transaction>> getBudgets() {
        return budgets;
    }

    public void addBudget(Transaction budget) {
        List<Transaction> currentList = budgets.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateBudget", "Inserting budget into database");
        currentList.add(budget);
        budgets.setValue(currentList);
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