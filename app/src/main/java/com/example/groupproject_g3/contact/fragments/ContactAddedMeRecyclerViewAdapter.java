package com.example.groupproject_g3.contact.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactAddedMeItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import java.util.List;

public class ContactAddedMeRecyclerViewAdapter extends RecyclerView.Adapter<ContactAddedMeRecyclerViewAdapter.ContactAddedMeViewHolder> {

    private final ContactAddedMeViewModel addedMeViewModel;

    private final UserInfoViewModel userInfoViewModel;

    private final List<ContactItem> mContacts;

    /**
     * Creates a new instance of the recycler view adapter for contacts who added this user.
     * @param contacts The list of contacts to display.
     * @param addedMeViewModel The model which would be used to deny or accept contact requests.
     * @param userInfoViewModel View model containing information of the user.
     */
    public ContactAddedMeRecyclerViewAdapter(List<ContactItem> contacts, ContactAddedMeViewModel addedMeViewModel, UserInfoViewModel userInfoViewModel) {
        mContacts = contacts;
        this.addedMeViewModel = addedMeViewModel;
        this.userInfoViewModel = userInfoViewModel;
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
            binding.buttonAcceptContactRequest.setOnClickListener(view -> {
                addedMeViewModel.acceptRequest(userInfoViewModel.getJwt(), userInfoViewModel.getUserId(), contactItem.getUserId());
                mContacts.remove(contactItem);
                notifyItemRemoved(getLayoutPosition());
            });
            binding.buttonCancelContactRequest.setOnClickListener(view -> {
                mContacts.remove(contactItem);
                notifyItemRemoved(getLayoutPosition());
                addedMeViewModel.connectDelete(userInfoViewModel.getJwt(), userInfoViewModel.getUserId(), contactItem.getUserId());
            });
        }
    }
}
