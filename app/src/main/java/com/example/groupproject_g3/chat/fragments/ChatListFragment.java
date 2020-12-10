package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.databinding.FragmentChatListBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentChatListBinding binding;

    /** A direct reference to the user info view model. Easy access to JWT and user id. */
    private UserInfoViewModel mUserInfo;

    /** View model which allows for the addition of new Chats. */
    private ChatListViewModel mListView;

    /**Instance of the chat view model*/
    private ChatViewModel mChatModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserInfo = provider.get(UserInfoViewModel.class);
        mListView = provider.get(ChatListViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mListView.connectGet(mUserInfo.getJwt(), mUserInfo.getUserId());
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
        mListView.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            if (!chatList.isEmpty()) {
                binding.recyclerChatItem.setAdapter(new ChatListRecyclerViewAdapter(mListView, mUserInfo, mChatModel, chatList));
                binding.textNoChats.setVisibility(View.GONE);
            }
        });
    }

}