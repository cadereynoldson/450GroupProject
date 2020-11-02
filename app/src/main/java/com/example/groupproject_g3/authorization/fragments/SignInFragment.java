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
import com.example.groupproject_g3.databinding.FragmentSignInBinding;

/**
 * A fragment for signing into the application.
 *
 */
public class SignInFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentSignInBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSignin.setOnClickListener(this::attemptSignIn);
        binding.buttonSigninRegister.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate( //Nav to register fragment.
                    SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
            );
        });
        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        binding.textSigninEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.textSigninPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());
    }

    /**
     * Attempt to sign into the application. Will set invalid warnings accordingly if there are
     * invalid inputs or sign in fails.
     * @param button the button calling this method.
     */
    private void attemptSignIn(final View button) {

        //TODO: Attempt to sign in to the application.
        //For now, just navigate to the main activity of the application.
        //TEMP CODE:
        Navigation.findNavController(getView()).navigate(
                SignInFragmentDirections.actionSignInFragmentToMainActivity()
        );
        getActivity().finish();
        //END TEMP CODE
    }
}