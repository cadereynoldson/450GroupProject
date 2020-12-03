package com.example.groupproject_g3.contact.fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactSentRequestItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.util.List;

public class ContactSentRequestsRecyclerViewAdapter extends RecyclerView.Adapter<ContactSentRequestsRecyclerViewAdapter.SentRequestsViewHolder> {

    private final ContactSentRequestsViewModel deleteViewModel;

    private final UserInfoViewModel userInfoViewModel;

    private final List<ContactItem> mContacts;

    public ContactSentRequestsRecyclerViewAdapter(List<ContactItem> contacts, ContactSentRequestsViewModel deleteViewModel, UserInfoViewModel userInfoViewModel) {
        mContacts = contacts;
        this.deleteViewModel = deleteViewModel;
        this.userInfoViewModel = userInfoViewModel;
    }

    @NonNull
    @Override
    public SentRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SentRequestsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact_sent_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SentRequestsViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class SentRequestsViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public FragmentContactSentRequestItemBinding binding;

        public ContactItem mContactItem;

        public SentRequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentContactSentRequestItemBinding.bind(mView);
        }

        public void setContact(final ContactItem contactItem) {
            mContactItem = contactItem;
            binding.textSentReqName.setText(mContactItem.getUserName());
            binding.textSentReqUsername.setText(mContactItem.getFirstName() + " " + mContactItem.getLastName());
            binding.buttonCancelContactRequest.setOnClickListener(view -> {
                mContacts.remove(contactItem);
                notifyItemRemoved(getLayoutPosition());
                deleteViewModel.connectDelete(userInfoViewModel.getJwt(), userInfoViewModel.getUserId(), contactItem.getUserId());
            });
        }
    }
}
