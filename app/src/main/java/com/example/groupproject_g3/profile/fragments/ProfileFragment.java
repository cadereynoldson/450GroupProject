/**
 * Landing page for Profile.
 */
package com.example.groupproject_g3.profile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.activities.MainActivity;
import com.example.groupproject_g3.databinding.FragmentPageProfileBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

/**
 * The profile fragment. Displays user information and allows for switching of themes.
 */
public class ProfileFragment extends Fragment {

    /** Binging for this page. */
    private FragmentPageProfileBinding binding;

    /** The view model of the profile fragment. */
    private ProfileViewModel profileViewModel;

    /** User info view model for storing user information. */
    private UserInfoViewModel userInfoViewModel;

    /** Key of the shared preferences to reference when getting preferences. */
    public static final String sharedPrefKey = "MY_SHARED_PREF";

    /** Key of the saved radio button index. */
    public static final String radioButtonKey = "SAVED_RADIO_BUTTON_INDEX";

    /** Key of the saved theme. */
    public static final String savedThemeKey = "SAVED_THEME_KEY";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        profileViewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        profileViewModel.getProfileInfo(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageProfileBinding.inflate(inflater, container, false);
        RadioButton selectedButton = (RadioButton) binding.radioGroup.getChildAt(profileViewModel.getSelectedThemeIndex());
        selectedButton.setChecked(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel.addProfileObserver(getViewLifecycleOwner(), contactItem -> {
            binding.profileNameDisplay.setText(contactItem.getFirstName() + " " + contactItem.getLastName());
            binding.profileEmailDisplay.setText(contactItem.getEmail());
            binding.profileUsernameDisplay.setText(contactItem.getUserName());
        });
        binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedChild = getActivity().findViewById(checkedId);
            if (checkedChild == binding.themeDefualtButton) {
                updateTheme(R.style.Theme_GroupProjectG3, 0);
            } else if (checkedChild == binding.themeDarkModeButton) {
                updateTheme(R.style.Theme_DarkTheme, 1);
            }
        });
    }

    /**
     * Updates the theme given a theme id. Refreshes current theme as well.
     * @param themeId the ID of the theme. (R.styles.YourTheme)
     */
    private void updateTheme(int themeId, int selectedIndex) {
        getActivity().setTheme(themeId);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.detach(ProfileFragment.this).attach(ProfileFragment.this).commit();
        profileViewModel.setSelectedTheme(themeId, selectedIndex);
    }
}