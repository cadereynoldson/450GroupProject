package com.example.groupproject_g3.weather.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherHoursItemBinding;

import java.util.List;

public class WeatherHoursRecyclerViewAdapter extends RecyclerView.Adapter<WeatherHoursRecyclerViewAdapter.HoursInfoViewHolder> {

    /** list containing the weather information. */
    private final List<WeatherBasicInformation> weatherInfo;

    /**
     * The view adapter for the recycler weather forecast, adapting based on imported info.
     * @param info - the data to be used.
     */
    public WeatherHoursRecyclerViewAdapter(List<WeatherBasicInformation> info) {
        weatherInfo = info;
    }

    @NonNull
    @Override
    public WeatherHoursRecyclerViewAdapter.HoursInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HoursInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_weather_hours_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHoursRecyclerViewAdapter.HoursInfoViewHolder holder, int position) {
        holder.setHoursInfo(weatherInfo.get(position));
    }

    @Override
    public int getItemCount() { return weatherInfo.size(); }

    public class HoursInfoViewHolder extends RecyclerView.ViewHolder {

        /** The view of the hours. */
        public final View mView;

        public FragmentWeatherHoursItemBinding binding;

        /** Basic Weather information to be used. */
        public WeatherBasicInformation info;

        public HoursInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherHoursItemBinding.bind(mView);
        }

        /**
         * Sets the hours info once retrieved.
         * @param info - the retreived information on weather hours.
         */
        public void setHoursInfo(final WeatherBasicInformation info) {
            this.info = info;
            binding.textHours.setText(info.getTime());
            binding.textHoursTemp.setText(info.getTemperature());
        }
    }
}
