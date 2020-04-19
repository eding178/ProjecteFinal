package com.example.projectefinal.ui.maquines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaquinesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MaquinesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is maquines fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}