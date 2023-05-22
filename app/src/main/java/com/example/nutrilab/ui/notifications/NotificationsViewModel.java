package com.example.nutrilab.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("New Diet");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getList() {
        return mText;
    }
}