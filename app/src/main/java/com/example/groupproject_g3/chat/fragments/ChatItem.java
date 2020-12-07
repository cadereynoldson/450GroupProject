package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.contact.fragments.ContactItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatItem {

    private final int mChatID;
    private final String mChatName;
    private HashMap<Integer, MutableLiveData<List<ChatMessage>>> mMessages;

    public ChatItem(int theChatID, String theChatName){
        this.mChatID = theChatID;
        this.mChatName = theChatName;
     //   this.mMessages = theMessages;
    }

    public int getChatID(){
        return mChatID;
    }
    public String getChatName(){
        return mChatName;
    }
    public HashMap getMessages() {return mMessages;}

    public String toString(){
        return "ChatName = " + mChatName + "ChatID = " + mChatID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatItem))
            return false;
        else
            return ((ChatItem) o).getChatID() == mChatID;
    }

}