package com.example.smartspender.ui.income;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartspender.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class IncomeViewModel extends ViewModel {

    private final MutableLiveData<List<Transaction>> incomes = new MutableLiveData<>(new ArrayList<>());


    public LiveData<List<Transaction>> getIncomes() {
        return incomes;
    }

    public void addIncome(Transaction income) {
        List<Transaction> currentList = incomes.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateIncome", "Inserting income into database");
        currentList.add(income);
        incomes.setValue(currentList);
    }
}