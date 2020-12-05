/**
 * Fragment landing for Contacts.
 */
package com.example.groupproject_g3.contact.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.databinding.FragmentPageContactListBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

    /** Binding which allows for direct reference of the components of the class. */
    private FragmentPageContactListBinding binding;

    /** The view model for the class. Stores and handles all data for displaying a list of contacts. */
    private ContactListViewModel mModel;

    private UserInfoViewModel userInfoViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        userInfoViewModel = provider.get(UserInfoViewModel.class);
        mModel = provider.get(ContactListViewModel.class);
        mModel.connectGet(userInfoViewModel.getJwt(), userInfoViewModel.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageContactListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.recyclerMessages.setAdapter(new ContactRecyclerViewAdapter(mModel, userInfoViewModel, contactList));
                binding.textNoContacts.setVisibility(View.GONE);
            }
        });
    }
}