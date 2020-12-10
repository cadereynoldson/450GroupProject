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

    /**List of my Chats*/
    private final List<ChatItem> mChats;

    /**Instance of my chat list view model*/
    private final ChatListViewModel mChatListViewModel;

    /**Instance of my user info view model*/
    private final UserInfoViewModel mUserInfoViewModel;

    /**Instance of my chat view model*/
    private final ChatViewModel mChatModel;

    /**Instance of my chat main fragment for navigational assistance*/
    private final ChatMainFragment mChatMain;


    /**
     * Constructor for Recycler view.
     *
     * @param listViewModel the list view model.
     * @param userInfoViewModel the user info model.
     * @param chatModel the chat model.
     * @param chats the chats.
     */
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

    /**
     * Inner class for list view holder implementation.
     */
    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        /**My view.*/
        private final View mView;

        /**Binding for fragment access.*/
        private FragmentChatItemBinding binding;

        /**my chat item to load.*/
        private ChatItem mChatItem;

        /**
         * Constructor.
         *
         * @param itemView the view.
         */
        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentChatItemBinding.bind(mView);
        }

        /**
         * Sets the chat item within the list.
         *
         * @param chatItem the item.
         */
        public void setChat(final ChatItem chatItem) {
            mChatItem = chatItem;
            binding.textChatName.setText(mChatItem.getChatName());
            binding.imagebuttonGoToChat.setOnClickListener(View -> {
                mChatMain.navigateToChat(mChatItem.getChatID(), mView);
            });
        }

    }
}
