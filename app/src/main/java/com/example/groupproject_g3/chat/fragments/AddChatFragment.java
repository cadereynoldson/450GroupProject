/**
 * Add Chat Fragment.
 */
package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.contact.fragments.ContactAddViewModel;
import com.example.groupproject_g3.databinding.FragmentAddChatBinding;
import com.example.groupproject_g3.databinding.FragmentAddContactBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddChatFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentAddChatBinding binding;

    /** A direct reference to the user info view model. Easy access to JWT and user id. */
    private UserInfoViewModel mUserInfo;

    /** View model which allows for the addition of new Chats. */
    private AddChatViewModel mAddView;

    /** Field for Incrementing the current chat ID */
    private int CURRENT_CHAT_ID = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserInfo = provider.get(UserInfoViewModel.class);
        mAddView = provider.get(AddChatViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonAddChat.setOnClickListener(this::addChat);
    }

    /**
     * Calls the add view model with the user input.
     * @param view the view in which this is called by.
     */
    private void addChat(View view) {
        String addName = binding.textAddChat.getText().toString();
        if (addName.isEmpty())
            binding.textAddChat.setError("Please enter a name.");
        else {
            mAddView.addChat(mUserInfo.getJwt(), addName, CURRENT_CHAT_ID);
            CURRENT_CHAT_ID++;
        }
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.textAddChat.setError(response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse error.", e.getMessage());
                }
            } else {
                Log.e("Added Chat", "Added Chat");
            }
        }
    }
}