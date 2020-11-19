package com.example.groupproject_g3.weather.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.groupproject_g3.contact.fragments.ContactItem;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<List<ContactItem>> mWeather;

    private static final String connectionUrl = "https://cloud-chat-450.herokuapp.com/weathers/";

    public WeatherViewModel(@NonNull Application application) {
        super(application);
    }



}
