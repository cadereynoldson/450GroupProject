package com.example.groupproject_g3.contact.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class AddContactFragmentDirections {
  private AddContactFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddContactFragmentToNavigationContacts() {
    return new ActionOnlyNavDirections(R.id.action_addContactFragment_to_navigation_contacts);
  }
}
