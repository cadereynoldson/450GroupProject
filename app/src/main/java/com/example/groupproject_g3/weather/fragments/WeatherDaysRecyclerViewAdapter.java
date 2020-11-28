package com.example.groupproject_g3.weather.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherDaysItemBinding;

import java.util.List;

public class WeatherDaysRecyclerViewAdapter extends RecyclerView.Adapter<WeatherDaysRecyclerViewAdapter.DaysInfoViewHolder> {

    /** list containing the weather information. */
    private final List<WeatherBasicInformation> weatherInfo;

    public WeatherDaysRecyclerViewAdapter(List<WeatherBasicInformation> info) {
        weatherInfo = info;
    }

    @NonNull
    @Override
    public WeatherDaysRecyclerViewAdapter.DaysInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaysInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_weather_days_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDaysRecyclerViewAdapter.DaysInfoViewHolder holder, int position) {
        holder.setDaysInfo(weatherInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherInfo.size();
    }

    public class DaysInfoViewHolder extends RecyclerView.ViewHolder {

        /** The view of the forecast. */
        public final View mView;
        public FragmentWeatherDaysItemBinding binding;
        private WeatherBasicInformation info;

        public DaysInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherDaysItemBinding.bind(itemView);
        }

        public void setDaysInfo(final WeatherBasicInformation info) {
            this.info = info;
            Log.e("Creating info", info.toString());
            binding.textDaysDate.setText(info.getDate());
            binding.textDaysMin.setText(info.getTemperature() + "°");
            binding.textDays.setText(info.getDay());
            binding.getRoot().setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherForecastFragment());
            });
        }
    }
}
