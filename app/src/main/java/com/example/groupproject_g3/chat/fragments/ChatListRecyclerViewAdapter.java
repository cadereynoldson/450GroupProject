package com.example.groupproject_g3.chat.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentChatItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatListViewHolder> {

    /**
     * List of my Chats
     */
    private final List<ChatItem> mChats;

    /**
     * Instance of my chat list view model
     */
    private final ChatListViewModel mChatListViewModel;

    /**
     * Instance of my user info view model
     */
    private final UserInfoViewModel mUserInfoViewModel;

    /**
     * Instance of my chat view model
     */
    private final ChatViewModel mChatModel;

    /**
     * Instance of my chat main fragment for navigational assistance
     */
    private final ChatMainFragment mChatMain;

    /**
     * Instance of chat add delete get contact view model for list of chat contacts
     */
    private final ChatAddDeleteGetContactViewModel mChatAddDelGetModel;

    /**
     * List of contacts within list
     */
    private final List<String> mChatMemberList;

    /**
     * Constructor for Recycler view.
     *
     * @param listViewModel     the list view model.
     * @param userInfoViewModel the user info model.
     * @param chatModel         the chat model.
     * @param chats             the chats.
     */
    public ChatListRecyclerViewAdapter(final ChatListViewModel listViewModel,
                                       final UserInfoViewModel userInfoViewModel,
                                       final ChatViewModel chatModel,
                                       final ChatAddDeleteGetContactViewModel chatAddDelGetModel,
                                       final List<ChatItem> chats
    ) {
        this.mChatListViewModel = listViewModel;
        this.mUserInfoViewModel = userInfoViewModel;
        this.mChatModel = chatModel;
        this.mChatAddDelGetModel = chatAddDelGetModel;
        this.mChatMemberList = new ArrayList<>();
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

        /**
         * My view.
         */
        private final View mView;

        /**
         * Binding for fragment access.
         */
        private FragmentChatItemBinding binding;

        /**
         * My chat item to load.
         */
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
            binding.textNumberChatId.setText(Integer.toString(mChatItem.getChatID()));
            binding.textChatName.setText(mChatItem.getChatName());
            binding.chatCardRoot.setOnClickListener(View -> {
                mChatMain.navigateToChat(mChatItem.getChatID(), mView);
            });
            binding.textChatName.setOnClickListener(View -> {
                mChatMain.navigateToChat(mChatItem.getChatID(), mView);
            });
            binding.textChatId.setOnClickListener(View -> {
                mChatMain.navigateToChat(mChatItem.getChatID(), mView);
            });
            binding.chatDelete.setOnClickListener(this::promptUser);
        }

        /**
         * prompts user for chat deletion.
         *
         * @param view
         */
        public void promptUser(final View view) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            for(int i = 0; i < mChats.size(); i++){
                                if (mChats.get(i).getChatID() == mChatItem.getChatID()){
                                    mChats.remove(i);
                                    break;
                                }
                            }
                            mChatListViewModel.connectDeleteChat(mUserInfoViewModel.getJwt(), mChatItem.getChatID());
                            notifyItemRemoved(getLayoutPosition());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Are you sure you want to delete chat?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }
}
