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

    /** The user id of the contact. */
    private final int mUserId;

    /**
     * Creates a new instance of a contact item.
     * @param firstName the first name of the contact.
     * @param lastName the last name of the contact.
     * @param username the username of the contact.
     * @param email the email of the contact.
     * @param userId the user id of the contact.
     */
    public ContactItem(String firstName, String lastName, String username, String email, int userId) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserName = username;
        mEmail = email;
        mUserId = userId;
    }

    /**
     * Getter for the username of the contact.
     * @return the username of the contact.
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Getter for the email of the contact.
     * @return the email of the contact.
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Getter for the first name of the contact.
     * @return the first name of the contact.
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Getter for the last name of the contact.
     * @return the last name of the contact.
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * Getter for the user id of the contact.
     * @return the user id of the contact.
     */
    public int getUserId() {
        return mUserId;
    }

    /**
     * Creates a string representation of this object.
     * @return a string representation of this object.
     */
    public String toString() {
        return "Username=" + mUserName + ", Email=" + mEmail + ", FirstName=" + mFirstName + ", LastName=" + mLastName + ", UserId=" + mUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ContactItem))
            return false;
        else
            return ((ContactItem) o).getUserId() == mUserId;
    }

}