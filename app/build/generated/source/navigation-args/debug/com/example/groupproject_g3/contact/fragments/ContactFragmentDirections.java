package com.example.groupproject_g3.contact.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;

public class ContactFragmentDirections {
  private ContactFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationContactsToAddContactFragment() {
    return new ActionOnlyNavDirections(R.id.action_navigation_contacts_to_addContactFragment);
  }
}
