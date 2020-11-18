/**
 * Fragment landing for Contacts.
 */
package com.example.groupproject_g3.contact.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentPageContactBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    /** Binding which allows for direct reference of the components of the class. */
    private FragmentPageContactBinding binding;

    /** The view model for the class. Stores and handles all data for contacts. */
    private ContactsViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        UserInfoViewModel model = provider.get(UserInfoViewModel.class);
        mModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        mModel.connectGet(model.getJwt(), model.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPageContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonContactAdd.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(ContactFragmentDirections.actionNavigationContactsToAddContactFragment())
        );
        mModel.addContactsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.recyclerMessages.setAdapter(new ContactRecyclerViewAdapter(contactList));
                binding.textNoContacts.setVisibility(View.GONE);
            }
        });
    }
}