// Generated by view binder compiler. Do not edit!
package com.example.groupproject_g3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.groupproject_g3.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPageWeatherBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView5;

  @NonNull
  public final ImageView imageWeather;

  @NonNull
  public final TextView textView;

  @NonNull
  public final ConstraintLayout weatherRoot;

  private FragmentPageWeatherBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView5, @NonNull ImageView imageWeather, @NonNull TextView textView,
      @NonNull ConstraintLayout weatherRoot) {
    this.rootView = rootView;
    this.imageView5 = imageView5;
    this.imageWeather = imageWeather;
    this.textView = textView;
    this.weatherRoot = weatherRoot;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPageWeatherBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPageWeatherBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_page_weather, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPageWeatherBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView5;
      ImageView imageView5 = rootView.findViewById(id);
      if (imageView5 == null) {
        break missingId;
      }

      id = R.id.image_weather;
      ImageView imageWeather = rootView.findViewById(id);
      if (imageWeather == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      ConstraintLayout weatherRoot = (ConstraintLayout) rootView;

      return new FragmentPageWeatherBinding((ConstraintLayout) rootView, imageView5, imageWeather,
          textView, weatherRoot);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
