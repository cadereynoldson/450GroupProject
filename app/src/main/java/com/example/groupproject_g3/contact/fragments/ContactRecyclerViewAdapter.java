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

/**
 * Recycler view adapter for the main contacts list.
 */
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {

    /** List of contacts to display. */
    private final List<ContactItem> mContacts;

    /** View model for the list of contacts. */
    private final ContactListViewModel listViewModel;

    /** View model for this user's information. */
    private final UserInfoViewModel userInfoViewModel;

    /**
     * Creates a new instance of this recycler view adapter.
     * @param listViewModel Reference to the view model for the contacts list.
     * @param userInfoViewModel Reference to the user info view model.
     * @param contacts The contacts to display for this user.
     */
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

    /**
     * View holder for displaying contacts.
     */
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
