/**
 * The weather view model.
 */
package com.example.groupproject_g3.weather.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.groupproject_g3.contact.fragments.ContactItem;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {

    /** The mutable data containing weather information to be used. */
    private MutableLiveData<List<ContactItem>> mWeather;

    /** Heroku URL extension to get and retrieve data. */
    private static final String connectionUrl = "https://cloud-chat-450.herokuapp.com/weathers/";

    /**
     * View model instance of the weather.
     * @param application - the application receiving the instance.
     */
    public WeatherViewModel(@NonNull Application application) {
        super(application);
    }



}
