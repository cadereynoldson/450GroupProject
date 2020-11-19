package com.example.groupproject_g3.chat.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class ChatFragmentDirections {
  private ChatFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationChatsToNavaigationAddChat() {
    return new ActionOnlyNavDirections(R.id.action_navigation_chats_to_navaigation_add_chat);
  }
}
