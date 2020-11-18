package com.example.groupproject_g3.contact.fragments;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Recycler View Adapter for the contact page.
 *
 * @author
 * @version November 2020
 */
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
