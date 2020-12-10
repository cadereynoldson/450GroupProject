package com.example.groupproject_g3.weather.fragments.recyclerviewadapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherDaysItemBinding;
import com.example.groupproject_g3.weather.fragments.WeatherBackground;
import com.example.groupproject_g3.weather.fragments.WeatherInformation;
import com.example.groupproject_g3.weather.fragments.WeatherMainFragment;
import com.example.groupproject_g3.weather.fragments.WeatherMainFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherDaysRecyclerViewAdapter extends RecyclerView.Adapter<WeatherDaysRecyclerViewAdapter.DaysInfoViewHolder> {

    /** list containing the weather information. */
    private final List<WeatherInformation> weatherInfo;

    public WeatherDaysRecyclerViewAdapter(List<WeatherInformation> info) {
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
        private WeatherInformation info;
        private WeatherMainFragment main;

        public DaysInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherDaysItemBinding.bind(itemView);
        }

        public void setDaysInfo(final WeatherInformation info) {
            this.info = info;
            binding.textDaysDate.setText(info.getmDate());
            binding.textDaysMin.setText(info.getmTempMin() + "°");
            binding.textDaysMax.setText(info.getmTempMax() + "°");
            binding.textDays.setText(info.getmDay());
            binding.textDaysTime.setText(info.getmTime());
            binding.weatherCard.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        WeatherMainFragmentDirections
                                .actionNavigationWeatherToWeatherForecastFragment(info));
            });

            Picasso.get().load(info.getmIcon()).into(binding.imageDaysForecast);
        }
    }
}
