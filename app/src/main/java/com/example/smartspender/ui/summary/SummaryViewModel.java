package com.example.smartspender.ui.summary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SummaryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SummaryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Monthly Summary\n" +
                "for June 2025");
    }

    public LiveData<String> getText() {
        return mText;
    }
}