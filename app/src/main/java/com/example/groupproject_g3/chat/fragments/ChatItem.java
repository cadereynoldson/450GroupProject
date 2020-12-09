package com.example.groupproject_g3.chat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.groupproject_g3.databinding.FragmentChatAddBinding;
import com.example.groupproject_g3.databinding.FragmentChatItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.util.List;

public class ChatItem {

    private final int mChatID;
    private final String mChatName;

    public ChatItem(int theChatID, String theChatName){
        this.mChatID = theChatID;
        this.mChatName = theChatName;
       }

    public int getChatID(){
        return mChatID;
    }
    public String getChatName(){
        return mChatName;
    }

    public String toString(){
        return "ChatName = " + mChatName + ", ChatID = " + mChatID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatItem))
            return false;
        else
            return ((ChatItem) o).getChatID() == mChatID;
    }

}