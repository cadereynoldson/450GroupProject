package com.example.groupproject_g3.weather.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherDayItemBinding;

import java.util.List;

public class WeatherDaysRecyclerViewAdapter extends RecyclerView.Adapter<WeatherDaysRecyclerViewAdapter.WeatherViewHolder> {

    private final List<WeatherDayItem> mWeatherDayList;

    public WeatherDaysRecyclerViewAdapter(List<WeatherDayItem> weatherHoursItems) {
        this.mWeatherDayList = weatherHoursItems;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.setWeatherDays(mWeatherDayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWeatherDayList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public FragmentWeatherDayItemBinding binding;
        public WeatherDayItem mDayItem;

        public WeatherViewHolder(View view) {
            super(view);
        }

        public void setWeatherDays(final WeatherDayItem dayItem) {
            binding.buttonFullInfo.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        WeatherFragmentDirections.actionNavigationWeatherToNavigationWeatherForecast());
            });
        }
    }
}
