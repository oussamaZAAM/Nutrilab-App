package com.example.nutrilab.ui.infos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel class for the InfosFragment.
 * <p>
 * Manages the data and provides it to the fragment.
 */
public class InfosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Constructor for InfosViewModel class.
     * Initializes the MutableLiveData object and sets the initial value.
     */
    public InfosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    /**
     * Retrieves the LiveData object containing the text.
     *
     * @return The LiveData object.
     */
    public LiveData<String> getText() {
        return mText;
    }
}