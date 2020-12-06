/**
 * Main Activity.
 */
package com.example.groupproject_g3.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.chat.fragments.ChatMessage;
import com.example.groupproject_g3.chat.fragments.ChatViewModel;
import com.example.groupproject_g3.contact.fragments.ContactAddedMeViewModel;
import com.example.groupproject_g3.contact.fragments.ContactListViewModel;
import com.example.groupproject_g3.contact.fragments.ContactSentRequestsViewModel;
import com.example.groupproject_g3.databinding.ActivityMainBinding;
import com.example.groupproject_g3.model.ContactNotificationCountViewModel;
import com.example.groupproject_g3.model.NewMessageCountViewModel;
import com.example.groupproject_g3.model.PushyTokenViewModel;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.example.groupproject_g3.services.PushReceiver;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MainPushMessageReceiver mPushMessageReceiver;
    private ContactsPushReceiver mContactsPushReceiver;
    private NewMessageCountViewModel mNewMessageModel;
    private ContactNotificationCountViewModel mContactsNotificationModel;
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * A BroadcastReceiver that listens for messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {
        private ChatViewModel mModel =
                new ViewModelProvider(MainActivity.this)
                        .get(ChatViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc =
                    Navigation.findNavController(
                            MainActivity.this, R.id.nav_host_fragment);
            NavDestination nd = nc.getCurrentDestination();
            if (intent.hasExtra("chatMessage")) {
                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");
                //If the user is not on the chat screen, update the
                // NewMessageCountView Model
                if (nd.getId() != R.id.navigation_chats) {
                    mNewMessageModel.increment();
                }
                //Inform the view model holding chatroom messages of the new
                //message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
    }

    /**
     * Notifies the contacts page to update itself.
     */
    private class ContactsPushReceiver extends BroadcastReceiver {
        private ContactSentRequestsViewModel sentRequestsViewModel = new ViewModelProvider(MainActivity.this)
                .get(ContactSentRequestsViewModel.class);
        private ContactAddedMeViewModel addedMeViewModel = new ViewModelProvider(MainActivity.this)
                .get(ContactAddedMeViewModel.class);
        private ContactListViewModel listViewModel = new ViewModelProvider(MainActivity.this)
                .get(ContactListViewModel.class);
        private UserInfoViewModel userInfoViewModel = new ViewModelProvider(MainActivity.this)
                .get(UserInfoViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc =
                    Navigation.findNavController(
                            MainActivity.this, R.id.nav_host_fragment);
            NavDestination nd = nc.getCurrentDestination();
            sentRequestsViewModel.connectGet(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
            addedMeViewModel.connectGet(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
            listViewModel.connectGet(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null)
            mPushMessageReceiver = new MainPushMessageReceiver();
        if (mContactsPushReceiver == null)
            mContactsPushReceiver = new ContactsPushReceiver();
        IntentFilter messageFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        IntentFilter contactsFilter = new IntentFilter(PushReceiver.CONTACTS_UPDATED);
        registerReceiver(mPushMessageReceiver, messageFilter);
        registerReceiver(mContactsPushReceiver, contactsFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null)
            unregisterReceiver(mPushMessageReceiver);
        if (mContactsPushReceiver != null)
            unregisterReceiver(mContactsPushReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);
        mContactsNotificationModel = new ViewModelProvider(this).get(ContactNotificationCountViewModel.class);
        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new ViewModelProvider(
                this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getJwt()))
                .get(UserInfoViewModel.class);
        buildMenu();
        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = binding.bottomNavMenu.getOrCreateBadge(R.id.navigation_chats);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });
        mContactsNotificationModel.addNotifictionCountObserver(this, count -> {
            BadgeDrawable badge = binding.bottomNavMenu.getOrCreateBadge(R.id.navigation_contacts);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                badge.clearNumber();
                badge.setVisible(false);
            }
        });
    }

    /**
     * Builds the bottom navigation menu and places it on the main activity display.
     */
    private void buildMenu() {
        BottomNavigationView navView = findViewById(R.id.bottom_nav_menu);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_weather,
                R.id.navigation_chats,
                R.id.navigation_contacts,
                R.id.navigation_profile
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_chats) {
                mNewMessageModel.reset();
            } else if (destination.getId() == R.id.navigation_contacts) {
                mContactsNotificationModel.reset();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drop_down, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        SharedPreferences prefs =
                getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        //End the app completely
        PushyTokenViewModel model = new ViewModelProvider(this)
                .get(PushyTokenViewModel.class);
        //when we hear back from the web service quit
        model.addResponseObserver(this, result -> finishAndRemoveTask());
        model.deleteTokenFromWebservice(
                new ViewModelProvider(this)
                        .get(UserInfoViewModel.class)
                        .getJwt()
        );    }

}