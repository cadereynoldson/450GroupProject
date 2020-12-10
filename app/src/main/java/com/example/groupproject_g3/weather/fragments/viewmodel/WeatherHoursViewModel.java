package com.example.groupproject_g3.weather.fragments.viewmodel;

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
import com.example.groupproject_g3.weather.fragments.WeatherInformation;

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

public class WeatherHoursViewModel extends AndroidViewModel {

    /** Contains basic weather information to be represented */
    private MutableLiveData<List<WeatherInformation>> information;

    public WeatherHoursViewModel(@NonNull Application application) {
        super(application);
        information = new MutableLiveData<>();
        information.setValue(new ArrayList<>());
    }

    public void addHoursWeatherObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<WeatherInformation>> observer) {
        information.observe(owner, observer);
    }

    /**
     * Gets the five day weather forecase given a JWT, latitude, and longitude.
     * @param authVal
     * @param latitude
     * @param longitude
     */
    public void connectGet(String authVal, double latitude, double longitude) {
        String url = "https://cloud-chat-450.herokuapp.com/weather/24hour/latLon/?lat=" + latitude + "&long=" + longitude;

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

    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {

            JSONObject sunData = result.getJSONObject(getString.apply(R.string.key_weather_24hour_current));
            long sunriseInfo = ((long) sunData.getInt(getString.apply(R.string.key_weather_current_sunrise)))* 1000L;

            Date riseDate = new Date(sunriseInfo);
            Calendar rise = Calendar.getInstance();
            rise.setTime(riseDate);
            String sunrise = new SimpleDateFormat("HH:mm").format(riseDate);

            long sunsetInfo = ((long) sunData.getInt(getString.apply(R.string.key_weather_current_sunset)))* 1000L;

            Date setDate = new Date(sunsetInfo);
            Calendar set = Calendar.getInstance();
            set.setTime(setDate);
            String sunset = new SimpleDateFormat("HH:mm").format(setDate);


            if (result.has(getString.apply(R.string.key_weather_24hour_hourly))) {
                JSONArray data = result.getJSONArray("hourly");

                for (int i = 0; i < data.length(); i++) {

                    JSONObject info = data.getJSONObject(i);

                    //fetch date, temperature, weather, and time.
                    long dateTime = ((long) info.getInt(getString.apply(R.string.key_weather_date_time))) * 1000l;

                    JSONArray weatherArr = info.getJSONArray(getString.apply(R.string.key_weather_current_weather));
                    String icon = weatherArr.getJSONObject(0).getString(getString.apply(R.string.key_weather_current_weather_icon));
                    String urlIcon = "https://openweathermap.org/img/wn/" + icon + "@2x.png";

                    Integer currentTemp = info.getInt(getString.apply(R.string.key_weather_current_temp));
                    Integer value = currentTemp - 32;
                    Integer celsius = value * 5/9;

                    Date date = new Date(dateTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    String dateDisplay = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

                    String time = new SimpleDateFormat("HH").format(date);

                    WeatherInformation HoursInfo = new WeatherInformation.Builder(
                            dateDisplay,
                            currentTemp.toString())
                            .addCelsius(celsius.toString())
                            .addTime(time)
                            .addIcon(urlIcon)
                            .addSunrise(sunrise)
                            .addSunset(sunset)
                            .build();

                    if (!information.getValue().contains(HoursInfo)) {
                        information.getValue().add(HoursInfo);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        information.setValue(information.getValue());
    }

    /**
     * Handles any error during the connection process.
     * @param error
     */
    private void handleError(final VolleyError error) {
        //TODO: Add better error detection
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }
}

