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

public class WeatherDaysViewModel extends AndroidViewModel {

    /** Contains basic weather information to be represented */
    private MutableLiveData<List<WeatherInformation>> information;

    public WeatherDaysViewModel(@NonNull Application application) {
        super(application);
        information = new MutableLiveData<>();
        information.setValue(new ArrayList<>());
    }

    /**
     * Adds an observer to add to the Forecast list
     * @param owner - the user data
     * @param observer - the observer
     */
    public void addDaysListObserver(@NonNull LifecycleOwner owner,
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
            // Fetch location
            JSONObject cityData = result.getJSONObject(getString.apply(R.string.key_weather_forecast_city));
            String location = cityData.getString(getString.apply(R.string.key_weather_forecast_name));
            String country = cityData.getString(getString.apply(R.string.key_weather_forecast_country));

            // Fetch longitude and latitude
            JSONObject coordData = cityData.getJSONObject(getString.apply(R.string.key_weather_current_coord));
            Double lat = coordData.getDouble(getString.apply(R.string.key_weather_current_lat));
            Double lon = coordData.getDouble(getString.apply(R.string.key_weather_current_lon));

            if (result.has(getString.apply(R.string.key_weather_forecast_list))) {
                JSONArray data = result.getJSONArray(getString.apply(R.string.key_weather_forecast_list));
                for (int i = 0; i < data.length(); i++) {
                    JSONObject info = data.getJSONObject(i);

                    //fetch date, temperature, weather, and time.
                    long dateTime = ((long) info.getInt(getString.apply(R.string.key_weather_date_time))) * 1000l;

                    //Fetch weather array and get main value from the response.
                    JSONArray weatherArr = info.getJSONArray(getString.apply(R.string.key_weather_current_weather));
                    String currentWeather = weatherArr.getJSONObject(0).getString(getString.apply(R.string.key_weather_current_weather_description));
                    String icon = weatherArr.getJSONObject(0).getString(getString.apply(R.string.key_weather_current_weather_icon));
                    String urlIcon = "https://openweathermap.org/img/wn/" + icon + "@2x.png";

                    //Fetch main array and get the temperature value from it.
                    JSONObject tempData = info.getJSONObject(getString.apply(R.string.key_weather_current_weather_main));
                    Double currentTemp = tempData.getDouble(getString.apply(R.string.key_weather_current_temp));
                    Double minTemp = tempData.getDouble(getString.apply(R.string.key_weather_current_temp_min));
                    Double maxTemp = tempData.getDouble(getString.apply(R.string.key_weather_current_temp_max));
                    Integer pressure = tempData.getInt(getString.apply(R.string.key_weather_current_pressure));
                    Integer humidity = tempData.getInt(getString.apply(R.string.key_weather_current_humidity));
                    Double feelsLike = tempData.getDouble(getString.apply(R.string.key_weather_current_feels_like));

                    // Fetch chances of rain
                    Double rainChance = info.getDouble(getString.apply(R.string.key_weather_forecast_pop));

                    // Fetch wind speed and wind direction
                    JSONObject windData = info.getJSONObject(getString.apply(R.string.key_weather_current_wind));
                    Double windSpeed = windData.getDouble(getString.apply(R.string.key_weather_current_speed));
                    Double windDirection = windData.getDouble(getString.apply(R.string.key_weather_current_deg));

                    Date date = new Date(dateTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    String dateDisplay = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

                    String time = new SimpleDateFormat("HH:mm").format(date);

                    String day = new SimpleDateFormat("EE").format(date);

                    WeatherInformation DaysInfo = new WeatherInformation.Builder(
                            dateDisplay,
                            currentTemp.toString())
                            .addDay(day)
                            .addTempMin(minTemp.toString())
                            .addTempMax(maxTemp.toString())
                            .addWeather(currentWeather)
                            .addTime(time)
                            .addFeelsLike(feelsLike.toString())
                            .addPressure(pressure.toString())
                            .addHumidity(humidity.toString())
                            .addPrecipitation(rainChance.toString())
                            .addLocation(location)
                            .addCountry(country)
                            .addLat(lat.toString())
                            .addLon(lon.toString())
                            .addSpeed(windSpeed.toString())
                            .addDirection(windDirection.toString())
                            .addIcon(urlIcon)
                            .build();

                    if (!information.getValue().contains(DaysInfo)) {
                        information.getValue().add(DaysInfo);
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
