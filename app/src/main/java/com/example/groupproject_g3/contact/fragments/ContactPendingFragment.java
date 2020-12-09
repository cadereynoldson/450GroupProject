package com.example.groupproject_g3.contact.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentContactAddedMeItemBinding;
import com.example.groupproject_g3.databinding.FragmentContactPendingBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

/**
 * Fragment for displaying pending contact information.
 */
public class ContactPendingFragment extends Fragment {

    /** Binding which allows for direct reference to components of this fragment. */
    private FragmentContactPendingBinding binding;

    /** View model for contacts that added this user. */
    private ContactAddedMeViewModel addedMeViewModel;

    /** View model for requests this user has sent. */
    private ContactSentRequestsViewModel sentRequestsViewModel;

    /** View model containing this user's information. */
    private UserInfoViewModel userInfoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        //Init view models
        userInfoViewModel = provider.get(UserInfoViewModel.class);
        addedMeViewModel = provider.get(ContactAddedMeViewModel.class);
        sentRequestsViewModel = provider.get(ContactSentRequestsViewModel.class);
        //Fetch data.
        sentRequestsViewModel.connectGet(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
        addedMeViewModel.connectGet(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactPendingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sentRequestsViewModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.recyclerWaitingApproval.setAdapter(new ContactSentRequestsRecyclerViewAdapter(contactList, sentRequestsViewModel, userInfoViewModel));
                binding.textNoSentRequests.setVisibility(View.GONE);
            } else {
                binding.textNoSentRequests.setVisibility(View.VISIBLE);
            }
        });
        addedMeViewModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.recyclerPendingContacts.setAdapter(new ContactAddedMeRecyclerViewAdapter(contactList, addedMeViewModel, userInfoViewModel));
                binding.textNoContactRequests.setVisibility(View.GONE);
            } else {
                binding.textNoContactRequests.setVisibility(View.VISIBLE);
            }
        });
    }
}