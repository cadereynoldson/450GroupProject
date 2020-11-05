package com.example.groupproject_g3.authorization.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import org.json.JSONObject;

public class SignInViewModel extends AndroidViewModel {

    public SignInViewModel(@NonNull Application application) {
        super(application);
    }

    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        //TODO: something
    }

    public void connect(final String email, final String password) {
        //TODO: something
    }
}
