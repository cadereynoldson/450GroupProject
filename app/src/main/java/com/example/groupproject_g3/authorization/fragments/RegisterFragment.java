package com.example.groupproject_g3.authorization.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    public RegisterFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonRegisterBack.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToSignInFragment());
        });
        binding.buttonRegister.setOnClickListener(this::attemptRegister);
    }

    private void attemptRegister(final View button) {
        //TODO: Attempt to register this user.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}