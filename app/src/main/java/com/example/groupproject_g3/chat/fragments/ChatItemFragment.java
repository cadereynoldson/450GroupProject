package com.example.groupproject_g3.chat.fragments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class ChatItemFragment extends Fragment {

    private final int mChatID;
    private final String mChatName;
    private ChatViewModel mChatView;
    private List<ChatMessage> mMessages;

    public ChatItemFragment(int theChatID, String theChatName){
        this.mChatID = theChatID;
        this.mChatName = theChatName;
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mChatView = provider.get(ChatViewModel.class);
        this.mMessages = mChatView.getMessageListByChatId(theChatID);
    }

    public int getChatID(){
        return mChatID;
    }
    public String getChatName(){
        return mChatName;
    }
    public List<ChatMessage> getMessages() {return mMessages;}

    public String toString(){
        return "ChatName = " + mChatName + "ChatID = " + mChatID;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof ChatItem))
//            return false;
//        else
//            return ((ChatItem) o).getChatID() == mChatID;
//    }

}