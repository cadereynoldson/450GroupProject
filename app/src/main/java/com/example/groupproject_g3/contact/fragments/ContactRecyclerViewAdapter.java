package com.example.groupproject_g3.contact.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactItemBinding;

import java.util.List;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    private final List<ContactItem> mContacts;

    public ContactRecyclerViewAdapter(List<ContactItem> contacts) {
        mContacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentContactItemBinding binding;

        public ContactItem mContactItem;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentContactItemBinding.bind(mView);
        }

        public void setContact(final ContactItem contactItem) {
            mContactItem = contactItem;
            binding.textUsername.setText(mContactItem.getUserName());
            binding.textEmail.setText(mContactItem.getEmail());
            binding.textName.setText(mContactItem.getFirstName() + " " + mContactItem.getLastName());
        }
    }
}
