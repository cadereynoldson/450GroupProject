/**
 * Main Activity.
 */
package com.example.groupproject_g3.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Main activity for this application.
 *
 * @author
 * @version September 2020
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        //take note that we are not using the constructor explicitly, the no-arg
        //constructor is called implicitly
        new ViewModelProvider(
                this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt()))
                .get(UserInfoViewModel.class);
        buildMenu();
    }

    /**
     * Builds the bottom navigation menu and places it on the main activity display.
     */
    private void buildMenu() {
        BottomNavigationView navView = findViewById(R.id.bottom_nav_menu);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_weather,
                R.id.navigation_messages,
                R.id.navigation_contacts,
                R.id.navigation_profile
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}