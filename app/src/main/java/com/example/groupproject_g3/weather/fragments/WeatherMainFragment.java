package com.example.groupproject_g3.weather.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.groupproject_g3.databinding.FragmentPageWeatherMainBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.example.groupproject_g3.weather.fragments.recyclerviewadapter.WeatherDaysRecyclerViewAdapter;
import com.example.groupproject_g3.weather.fragments.recyclerviewadapter.WeatherHoursRecyclerViewAdapter;
import com.example.groupproject_g3.weather.fragments.viewmodel.WeatherCurrentViewModel;
import com.example.groupproject_g3.weather.fragments.viewmodel.WeatherDaysViewModel;
import com.example.groupproject_g3.weather.fragments.viewmodel.WeatherHoursViewModel;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WeatherMainFragment extends Fragment {

    /** Latitude data to be used. */
    private double lat = 47.2529;

    /** Longitude data to be used. */
    private double lon = -122.4443;

    /** Binding object to be used with other fragments in Weather. */
    private FragmentPageWeatherMainBinding binding;

    /** A current model of the view model */
    private WeatherCurrentViewModel currentModel;

    private WeatherDaysViewModel daysModel;

    private WeatherHoursViewModel hoursModel;

    private WeatherBackground background;

    private WeatherConverter converter;

    Integer convertTemp;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        UserInfoViewModel model = provider.get(UserInfoViewModel.class);
        currentModel = new ViewModelProvider(getActivity()).get(WeatherCurrentViewModel.class);
        currentModel.connectGet(model.getJwt(), lat, lon); //TODO: Update hardcoded lat and long.
        hoursModel = new ViewModelProvider(getActivity()).get(WeatherHoursViewModel.class);
        hoursModel.connectGet(model.getJwt(), lat, lon);
        daysModel = new ViewModelProvider(getActivity()).get(WeatherDaysViewModel.class);
        daysModel.connectGet(model.getJwt(), lat, lon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPageWeatherMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentModel.addCurrentWeatherObserver(getViewLifecycleOwner(), currentInfo -> {
            binding.containerWeatherForecast.textCurrentDate.setText(currentInfo.getmDate());
            binding.containerWeatherForecast.textCurrentTime.setText(currentInfo.getmTime());
            binding.containerWeatherForecast.textCurrentTemperature.setText(currentInfo.getmTemperature() + "°F"); //TODO: Update for celsius
            binding.containerWeatherForecast.textCurrentDescription.setText(currentInfo.getmWeather());
            binding.chipWeatherLocation.setText(currentInfo.getmLocation() + ", " + currentInfo.getmCountry());
            Picasso.get().load(currentInfo.getmIcon()).into(binding.containerWeatherForecast.imageCurrentIcon);
            background = new WeatherBackground(view, currentInfo.getmWeather(), currentInfo.getmIcon(), getResources());

            binding.weatherC.setOnClickListener(v -> {
                binding.containerWeatherForecast.textCurrentTemperature.setText(currentInfo.getmCelsius() + "°C");
                binding.weatherC.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                binding.weatherF.setTypeface(Typeface.DEFAULT);
            });

            binding.weatherF.setOnClickListener(v -> {
                binding.containerWeatherForecast.textCurrentTemperature.setText(currentInfo.getmTemperature() + "°F");
                binding.weatherF.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                binding.weatherC.setTypeface(Typeface.DEFAULT);
            });
        });

        hoursModel.addHoursWeatherObserver(getViewLifecycleOwner(), hoursInfo -> {
            if(!hoursInfo.isEmpty()) {
                binding.weatherHours.setAdapter(new WeatherHoursRecyclerViewAdapter(hoursInfo));
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        daysModel.addDaysListObserver(getViewLifecycleOwner(), information -> {
            if(!information.isEmpty()) {
                binding.weatherDays.setAdapter(new WeatherDaysRecyclerViewAdapter(information));
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.fab.setOnClickListener(info -> {
            Navigation.findNavController(view).navigate(
                    WeatherMainFragmentDirections
                            .actionNavigationWeatherToMapsActivity());
        });
    }
}
