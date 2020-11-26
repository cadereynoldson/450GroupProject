/**
 * COntains the Recycler View Adapter for the weather forecast.
 */
package com.example.groupproject_g3.weather.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherDayItemBinding;
import com.example.groupproject_g3.databinding.FragmentWeatherHoursItemBinding;

import java.util.List;

public class WeatherForecastRecyclerViewAdapter extends RecyclerView.Adapter<WeatherForecastRecyclerViewAdapter.ForecastInfoViewHolder> {

    /** list containing the weather information. */
    private final List<WeatherBasicInformation> weatherInfo;

    /**
     * The view adapter for the recycler weather forecast, adapting based on imported info.
     * @param info - the data to be used.
     */
    public WeatherForecastRecyclerViewAdapter(List<WeatherBasicInformation> info) {
        weatherInfo = info;
    }

    @NonNull
    @Override
    public ForecastInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_weather_day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastInfoViewHolder holder, int position) {
        holder.setForecastInfo(weatherInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherInfo.size();
    }

    /**
     * A view holder for the forecast info running in the background.
     */
    public class ForecastInfoViewHolder extends RecyclerView.ViewHolder {

        /** The view of the forecast. */
        public final View mView;

        /** Binding data for the fragments in Weather. */
        public FragmentWeatherDayItemBinding binding;

        /** Basic Weather information to be used. */
        public WeatherBasicInformation info;

        /** An instance of the View Holder */
        public ForecastInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherDayItemBinding.bind(mView);
        }

        /**
         * Sets the forecast info once retrieved.
         * @param info - the retreived information on weather forecast.
         */
        public void setForecastInfo(final WeatherBasicInformation info) {
            this.info = info;
            Log.e("Creating info", info.toString());
            binding.textDate.setText(info.getDate());
            binding.textDayTemperature.setText(info.getTemperature() + "°F");
            binding.textDayWeather.setText(info.getWeather());
            binding.buttonFullInfo.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(WeatherFragmentDirections.actionNavigationWeatherToNavigationWeatherForecast());
            });
        }
    }
}
