package com.example.groupproject_g3.chat.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class AddChatFragmentDirections {
  private AddChatFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddChatFragmentToNavigationChats() {
    return new ActionOnlyNavDirections(R.id.action_addChatFragment_to_navigation_chats);
  }
}
