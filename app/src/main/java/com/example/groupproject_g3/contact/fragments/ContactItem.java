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

    /** The primary key in the database. */
    private final int mPrimaryKey;

    /** The username of the contact. */
    private final String mUserName;

    /** The email of the contact. */
    private final String mEmail;

    public ContactItem(int primaryKey, String username, String email) {
        mPrimaryKey = primaryKey;
        mUserName = username;
        mEmail = email;
    }



}