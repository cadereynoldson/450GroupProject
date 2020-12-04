/**
 * A view model of the current weather to be displayed.
 */
package com.example.groupproject_g3.weather.fragments;

import android.app.Application;
import android.graphics.drawable.Drawable;
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

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public class WeatherCurrentViewModel extends AndroidViewModel {

    /** contains the basic weather data to be displayed, started off with placeholder data */
    private static final WeatherInformation currentPlaceholder = new WeatherInformation.Builder(
            "06/02/1998", "7:36").build();

    /** Data containing mutable data for weather */
    private MutableLiveData<WeatherInformation> information;

    /**
     * A view model of the current weather to be displayed.
     * @param application - the application displaying the data.
     */
    public WeatherCurrentViewModel(@NonNull Application application) {
        super(application);
        information = new MutableLiveData<>();
        information.setValue(currentPlaceholder);
    }

    /**
     * An observer to add the current weather data as needed.
     * @param owner - the user data.
     * @param observer - the observer.
     */
    public void addCurrentWeatherObserver(@NonNull LifecycleOwner owner,
                                          @NonNull Observer<WeatherInformation> observer) {
        information.observe(owner, observer);
    }

    /**
     * Gets the current weather data given a JWT, latitude, and longitude.
     * @param authVal - value to authorize data retrieval.
     * @param latitude - latitude data.
     * @param longitude - longitude data.
     */
    public void connectGet(String authVal, double latitude, double longitude) {
        String url = "https://cloud-chat-450.herokuapp.com/weather/current/latLon/?lat=" + latitude + "&long=" + longitude;

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
     * Handles the representation of data within the application after successfully retrieving the
     * data.
     * @param result - the data to be processed.
     */
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString =
                getApplication().getResources()::getString;
        try {
            //fetch date, temperature, weather, and time.
            long dateTime = ((long) result.getInt(getString.apply(R.string.key_weather_date_time))) * 1000l;

            //Fetch weather array and get main value from the response.
            JSONArray weatherArr = result.getJSONArray(getString.apply(R.string.key_weather_current_weather));
            String currentWeather = weatherArr.getJSONObject(0).getString(getString.apply(R.string.key_weather_current_weather_description));
            String icon = weatherArr.getJSONObject(0).getString(getString.apply(R.string.key_weather_current_weather_icon));
            String urlIcon = "http://openweathermap.org/img/wn/" + icon + "@2x.png";

            //Fetch main array and get the temperature value from it.
            JSONObject tempData = result.getJSONObject(getString.apply(R.string.key_weather_current_weather_main));
            Integer currentTemp = tempData.getInt(getString.apply(R.string.key_weather_current_temp));

            JSONObject sysData = result.getJSONObject(getString.apply(R.string.key_weather_current_sys));
            String currentCountry = sysData.getString(getString.apply(R.string.key_weather_current_country));
            String currentLocation = result.getString(getString.apply(R.string.key_weather_current_name));

            Date date = new Date(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String dateDisplay = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);

            String time = new SimpleDateFormat("HH:mm").format(date);

            WeatherInformation info = new WeatherInformation.Builder(
                    dateDisplay,
                    currentTemp.toString())
                    .addTime(time)
                    .addWeather(currentWeather)
                    .addLocation(currentLocation)
                    .addCountry(currentCountry)
                    .addIcon(urlIcon)
                    .build();

            information.setValue(info);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error in fetching weather data", e.getMessage());
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
