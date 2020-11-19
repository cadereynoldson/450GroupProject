package com.example.groupproject_g3.weather.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherDayItemBinding;

import java.util.List;

public class WeatherForecastRecyclerViewAdapter extends RecyclerView.Adapter<WeatherForecastRecyclerViewAdapter.ForecastInfoViewHolder> {

    private final List<WeatherBasicInformation> weatherInfo;

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

    public class ForecastInfoViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentWeatherDayItemBinding binding;

        public WeatherBasicInformation info;

        public ForecastInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherDayItemBinding.bind(mView);
        }

        public void setForecastInfo(final WeatherBasicInformation info) {
            this.info = info;
            Log.e("Creating info", info.toString());
            binding.textDate.setText(info.getDate());
            binding.textDayTemperature.setText(info.getTemperature() + "°F");
            binding.textDayWeather.setText(info.getWeather());
        }
    }
}
