package com.example.groupproject_g3.contact.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactAddedMeItemBinding;

import java.util.List;

public class ContactAddedMeRecyclerViewAdapter extends RecyclerView.Adapter<ContactAddedMeRecyclerViewAdapter.ContactAddedMeViewHolder> {

    private final List<ContactItem> mContacts;

    public ContactAddedMeRecyclerViewAdapter(List<ContactItem> contacts) {
        mContacts = contacts;
    }

    @NonNull
    @Override
    public ContactAddedMeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactAddedMeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact_added_me_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAddedMeViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ContactAddedMeViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentContactAddedMeItemBinding binding;

        public ContactItem mContactItem;

        public ContactAddedMeViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentContactAddedMeItemBinding.bind(mView);
        }

        public void setContact(final ContactItem contactItem) {
            mContactItem = contactItem;
            binding.textAddedMeUsername.setText(mContactItem.getUserName());
            binding.textAddedMeName.setText(mContactItem.getFirstName() + " " + mContactItem.getLastName());
        }
    }
}
