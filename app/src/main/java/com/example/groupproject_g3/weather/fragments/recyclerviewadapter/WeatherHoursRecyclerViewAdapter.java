package com.example.groupproject_g3.weather.fragments.recyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentWeatherHoursItemBinding;
import com.example.groupproject_g3.weather.fragments.WeatherInformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherHoursRecyclerViewAdapter extends RecyclerView.Adapter<WeatherHoursRecyclerViewAdapter.HoursInfoViewHolder> {

    /** list containing the weather information. */
    private final List<WeatherInformation> weatherInfo;

    /**
     * Creates a new instance of the recycler view adapter for weather 24 hours.
     * @param info The list of hours weather information to display.
     */
    public WeatherHoursRecyclerViewAdapter(List<WeatherInformation> info) {
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
    public int getItemCount() {
        return weatherInfo.size();
    }

    public class HoursInfoViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentWeatherHoursItemBinding binding;
        private WeatherInformation info;

        /**
         * Constructor for HoursInfoViewHolder.
         * @param itemView The view of this fragment.
         */
        public HoursInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentWeatherHoursItemBinding.bind(itemView);
        }

        /**
         * Set component of this fragment with specific data.
         * @param info The weather information to be used.
         */
        public void setHoursInfo(final WeatherInformation info) {
            this.info = info;
            binding.textHours.setText(info.getmTime());
            binding.textHoursTemp.setText(info.getmTemperature() + "°");
            Picasso.get().load(info.getmIcon()).into(binding.imageHoursForecast);
            if (info.getmCheckTime().matches(info.getmSunRiseTime())) {
                binding.textHours.setText(info.getmSunrise());
                binding.textHoursTemp.setText("Sunrise");
                Picasso.get().load(R.drawable.weather_sunrise).into(binding.imageHoursForecast);
            } else if (info.getmCheckTime().matches(info.getmSunsetTime())) {
                binding.textHours.setText(info.getmSunset());
                binding.textHoursTemp.setText("Sunset");
                Picasso.get().load(R.drawable.weather_sunset).into(binding.imageHoursForecast);
            }
        }
    }
}
