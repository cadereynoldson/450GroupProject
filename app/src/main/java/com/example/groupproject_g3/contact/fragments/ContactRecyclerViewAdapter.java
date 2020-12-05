package com.example.groupproject_g3.contact.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.util.List;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    private final List<ContactItem> mContacts;

    private final ContactListViewModel listViewModel;

    private final UserInfoViewModel userInfoViewModel;

    public ContactRecyclerViewAdapter(ContactListViewModel listViewModel, UserInfoViewModel userInfoViewModel, List<ContactItem> contacts) {
        this.listViewModel =  listViewModel;
        this.userInfoViewModel = userInfoViewModel;
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
            binding.titleContactsUsername.setText(mContactItem.getUserName());
            binding.textUsername.setText(mContactItem.getUserName());
            binding.textEmail.setText(mContactItem.getEmail());
            binding.textName.setText(mContactItem.getFirstName() + " " + mContactItem.getLastName());
            binding.buttonDeleteContact.setOnClickListener(view -> {
                listViewModel.connectDelete(userInfoViewModel.getJwt(), userInfoViewModel.getUserId(), contactItem.getUserId());
                mContacts.remove(contactItem);
                notifyItemRemoved(getLayoutPosition());
            });
        }
    }
}
