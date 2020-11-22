package com.example.groupproject_g3.weather.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherHoursViewModel extends AndroidViewModel {

    /** Contains basic weather information to be represented */
    private MutableLiveData<List<WeatherBasicInformation>> information;

    public WeatherHoursViewModel(@NonNull Application application) {
        super(application);
        information = new MutableLiveData<>();
        information.setValue(new ArrayList<WeatherBasicInformation>());
    }

    /**
     * Adds an observer to add to the Hours list
     * @param owner - the user data
     * @param observer - the observer
     */
    public void addHoursListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<WeatherBasicInformation>> observer) {
        information.observe(owner, observer);
    }
}
