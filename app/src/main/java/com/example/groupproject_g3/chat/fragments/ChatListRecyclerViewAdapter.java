package com.example.groupproject_g3.chat.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentChatItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.util.List;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    private final List<ChatItemFragment> mChats;

    private final ChatListViewModel mChatListViewModel;


    public ChatListRecyclerViewAdapter(ChatListViewModel listViewModel,  List<ChatItemFragment> chats) {
        this.mChatListViewModel =  listViewModel;
        mChats = chats;
    }

    @NonNull
    @Override
    public ChatListRecyclerViewAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListRecyclerViewAdapter.ChatListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact_item, parent, false));
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

        public ChatItemFragment mChatItem;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentChatItemBinding.bind(mView);
        }

        public void setChat(final ChatItemFragment chatItem) {
            mChatItem = chatItem;
            binding.textChatID.setText(mChatItem.getChatID());
            binding.textChatID.setText(mChatItem.getChatName());
        }
    }
}
