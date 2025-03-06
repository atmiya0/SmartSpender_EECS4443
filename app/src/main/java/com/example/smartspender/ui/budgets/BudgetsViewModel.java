package com.example.smartspender.ui.budgets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BudgetsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BudgetsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Budgets fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}