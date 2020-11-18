/**
 * Landing page for Messages.
 */
package com.example.groupproject_g3.message.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;

/**
 * A fragment for the message page.
 *
 * @author
 * @version September 2020
 */
public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_messages, container, false);
    }
}