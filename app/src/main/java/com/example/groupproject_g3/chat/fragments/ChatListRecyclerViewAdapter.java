package com.example.groupproject_g3.chat.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.authorization.fragments.RegisterFragmentDirections;
import com.example.groupproject_g3.authorization.fragments.SignInFragmentArgs;
import com.example.groupproject_g3.databinding.FragmentChatItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.util.List;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    private  final List<ChatItem> mChats;
    private  final ChatListViewModel mChatListViewModel;
    private  final UserInfoViewModel mUserInfoViewModel;
    private  final ChatViewModel mChatModel;
    private  final ChatMainFragment mChatMain;


    public ChatListRecyclerViewAdapter(ChatListViewModel listViewModel, UserInfoViewModel userInfoViewModel, ChatViewModel chatModel,  List<ChatItem> chats) {
        this.mChatListViewModel =  listViewModel;
        this.mUserInfoViewModel = userInfoViewModel;
        this.mChatModel = chatModel;
        mChatMain = new ChatMainFragment();
        mChats = chats;
    }

    @NonNull
    @Override
    public ChatListRecyclerViewAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListRecyclerViewAdapter.ChatListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListRecyclerViewAdapter.ChatListViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentChatItemBinding binding;

        public ChatItem mChatItem;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentChatItemBinding.bind(mView);
        }

        public void setChat(final ChatItem chatItem) {
            mChatItem = chatItem;
            binding.textChatName.setText(mChatItem.getChatName());
            binding.imagebuttonGoToChat.setOnClickListener(View -> {
                mChatMain.navigateToChat(mChatItem.getChatID(), mView);
            });
        }

    }
}
