package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentAddChatBinding;
import com.example.groupproject_g3.databinding.FragmentChatListBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentChatListBinding binding;

    /** A direct reference to the user info view model. Easy access to JWT and user id. */
    private UserInfoViewModel mUserInfo;

    /** View model which allows for the addition of new Chats. */
    private AddChatViewModel mAddView;

    private ChatListViewModel mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserInfo = provider.get(UserInfoViewModel.class);
        mListView = provider.get(ChatListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * Calls the add view model with the user input.
     * @param view the view in which this is called by.
     */
    private void addContactToChat(View view) {

    }

    private void observeResponse(final JSONObject response) {

    }
}