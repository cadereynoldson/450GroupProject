package com.example.groupproject_g3.contact.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;

/**
 * Represents a contact between two users.
 */
public class ContactItem {

    /** The username of the contact. */
    private final String mUserName;

    /** The email of the contact. */
    private final String mEmail;

    /** The first name of the contact. */
    private final String mFirstName;

    /** The last name of the contact. */
    private final String mLastName;

    public ContactItem(String firstName, String lastName, String username, String email) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserName = username;
        mEmail = email;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String toString() {
        return "Username=" + mUserName + ", Email=" + mEmail + ", FirstName=" + mFirstName + ", LastName=" + mLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ContactItem))
            return false;
        else
            return ((ContactItem) o).mEmail.equals(mEmail) && ((ContactItem) o).mUserName.equals(mUserName)
                    && ((ContactItem) o).mFirstName.equals(mFirstName) && ((ContactItem) o).mLastName.equals(mLastName);
    }

}