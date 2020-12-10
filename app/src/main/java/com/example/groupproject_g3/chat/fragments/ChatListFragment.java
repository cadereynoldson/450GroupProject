package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentChatListBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    /**
     * Binding for the fragment. Allows for direct reference of fragment components.
     */
    private FragmentChatListBinding binding;

    /**
     * A direct reference to the user info view model. Easy access to JWT and user id.
     */
    private UserInfoViewModel mUserInfoModel;

    /**
     * View model which allows for the addition of new Chats.
     */
    private ChatListViewModel mListModel;

    /**
     * Instance of the chat view model
     */
    private ChatViewModel mChatModel;

    /**
     * Instance of chat add delete get contact view model
     */
    private ChatAddDeleteGetContactViewModel mChatAddDelGetModel;

    /**
     * String of current contacts in chat
     */
    private StringBuilder builder;

    private List<String> mChatMemberList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserInfoModel = provider.get(UserInfoViewModel.class);
        mListModel = provider.get(ChatListViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mChatAddDelGetModel = provider.get(ChatAddDeleteGetContactViewModel.class);
        mListModel.connectGet(mUserInfoModel.getJwt(), mUserInfoModel.getUserId());
        builder = new StringBuilder();
        this.mChatMemberList = new ArrayList<>();
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
        mListModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (!chatList.isEmpty()) {
                binding.recyclerChatItem.setAdapter(new ChatListRecyclerViewAdapter(mListModel,
                        mUserInfoModel,
                        mChatModel,
                        mChatAddDelGetModel,
                        chatList));
                binding.textNoChats.setVisibility(View.GONE);
            }
        });
    }


}