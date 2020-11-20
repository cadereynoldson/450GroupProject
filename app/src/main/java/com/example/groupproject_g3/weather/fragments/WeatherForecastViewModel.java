/**
 * View model of the weather forecast within Android.
 */
package com.example.groupproject_g3.weather.fragments;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject_g3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class WeatherForecastViewModel extends AndroidViewModel {

    /** Contains basic weather information to be represented */
    private MutableLiveData<List<WeatherBasicInformation>> information;

    /**
     * A model of the weather forecast
     * @param application - the application retrieving the data.
     */
    public WeatherForecastViewModel(@NonNull Application application) {
        super(application);
        information = new MutableLiveData<>();
        information.setValue(new ArrayList<WeatherBasicInformation>());
    }

    /**
     * Adds an observer to add to the Forecast list
     * @param owner - the user data
     * @param observer - the observer
     */
    public void addForecastListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<WeatherBasicInformation>> observer) {
        information.observe(owner, observer);
    }

    /**
     * Gets the five day weather forecase given a JWT, latitude, and longitude.
     * @param authVal
     * @param latitude
     * @param longitude
     */
    public void connectGet(String authVal, double latitude, double longitude) {
        String url = "https://cloud-chat-450.herokuapp.com/weather/forecast/latLon/?lat=" + latitude + "&long=" + longitude;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", authVal);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);

    }

    /**
     * Handles the data display after the successful retrieval of data.
     * @param result - the data to be displayed within the application.
     */
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            Log.e("TESTING DISPLAY", result.toString()); //TODO REMOVE
            if (result.has(getString.apply(R.string.key_weather_forecast_list))) {
                JSONArray data = result.getJSONArray(getString.apply(R.string.key_weather_forecast_list));
                for (int i = 0; i < data.length(); i++) {
                    JSONObject info = data.getJSONObject(i);
                    Log.i("Forecast Weather Result ", info.toString());
                    //fetch date, temperature, weather, and time.
                    long dateTime = ((long) info.getInt(getString.apply(R.string.key_weather_date_time))) * 1000l;
                    //Fetch weather array and get main value from the response.
                    JSONArray weatherArr = info.getJSONArray(getString.apply(R.string.key_weather_current_weather));
                    String currentWeather = weatherArr.getJSONObject(0).getString(getString.apply(R.string.key_weather_current_weather_main));
                    //Fetch main array and get the temperature value from it.
                    JSONObject tempData = info.getJSONObject(getString.apply(R.string.key_weather_current_weather_main));
                    Double currentTemp = tempData.getDouble(getString.apply(R.string.key_weather_current_temp));
                    Date date = new Date(dateTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String dateDisplay = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
                    String time = new SimpleDateFormat("HH:mm").format(date);
                    WeatherBasicInformation forecastInfo = new WeatherBasicInformation(dateDisplay, time, currentTemp.toString(), currentWeather);
                    if (!information.getValue().contains(forecastInfo)) {
                        information.getValue().add(forecastInfo);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error in fetching weather data", e.getMessage());
        }

        information.setValue(information.getValue());
    }

    private void handleError(final VolleyError error) {
        //TODO: Add better error detection
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

}
