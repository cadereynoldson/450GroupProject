/**
 * Class which creates the Authorization Activity.
 */
package com.example.groupproject_g3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.model.PushyTokenViewModel;

import me.pushy.sdk.Pushy;

public class AuthorizationActivity extends AppCompatActivity {

    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        //If it is not already running, start the Pushy listening service
        Pushy.listen(this);
        initiatePushyTokenRequest();
    }
}