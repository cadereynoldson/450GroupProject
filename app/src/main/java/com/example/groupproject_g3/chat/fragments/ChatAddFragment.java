/**
 * Add Chat Fragment.
 */
package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.groupproject_g3.databinding.FragmentChatAddBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatAddFragment extends Fragment {

    /**Binding for the fragment. Allows for direct reference of fragment components.*/
    private FragmentChatAddBinding binding;

    /**A direct reference to the user info view model. Easy access to JWT and user id.*/
    private UserInfoViewModel mUserInfo;

    /**View model which allows for the addition of new Chats.*/
    private ChatAddViewModel mChatAddModel;

    /**Instance of chat main fragment for directions access.*/
    private ChatMainFragment mChatMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserInfo = provider.get(UserInfoViewModel.class);
        mChatAddModel = provider.get(ChatAddViewModel.class);
        mChatMain = new ChatMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonAddChat.setOnClickListener(this::addChat);
        mChatAddModel.addResponseObserver(getViewLifecycleOwner(), this::observeResponse);
    }

    /**
     * Calls the add view model with the user input.
     *
     * @param view the view in which this is called by.
     */
    private void addChat(View view) {
        String name = binding.textAddChat.getText().toString();
        if (name.isEmpty())
            binding.textAddChat.setError("Please enter a name.");
        else {
            mChatAddModel.addChatAndCreator(mUserInfo.getJwt(), mUserInfo.getUserId(), name);
            binding.buttonAddChat.setOnClickListener(this::navigateToChat);
        }
    }

    /**
     * Navigate to chat.
     *
     * @param view the current view.
     */
    private void navigateToChat(final View view) {
        mChatMain.navigateToChat(mChatAddModel.mChatID, view);
    }


    /**
     * Observes resposne.
     *
     * @param response the response.
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.textAddChat.setError(response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse error.", e.getMessage());
                }
            } else
                Log.e("Added Chat", "Added Chat");
        }
    }
}