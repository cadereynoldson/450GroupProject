package com.example.groupproject_g3.authorization.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentRegisterBinding;
import com.example.groupproject_g3.utils.PasswordValidator;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.groupproject_g3.utils.PasswordValidator.checkClientPredicate;
import static com.example.groupproject_g3.utils.PasswordValidator.checkExcludeWhiteSpace;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdDigit;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdLength;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdLowerCase;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdSpecialChar;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdUpperCase;

/**
 * A fragment for registering into the application.
 */
public class RegisterFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentRegisterBinding binding;

    /** */
    private RegisterViewModel mRegisterModel;

    /** */
    private PasswordValidator mNameValidator = checkPwdLength(1);

    /** */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /** */
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.textRegisterPassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    /** Require empty public constructor for RegisterFragment. */
    public RegisterFragment() {
        // Required empty constructor
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    /**
     *
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     *
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonRegisterBack.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToSignInFragment());
        });
        binding.buttonRegister.setOnClickListener(this::attemptRegister);
    }

    /**
     * Attempt to register into the application. Will set invalid warnings accordingly if there are
     * invalid inputs or register fails.
     * @param button the button calling this method.
     */
    private void attemptRegister(final View button) {
        validateFirst();
    }

    /**
     *
     */
    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.textRegisterFirstName.getText().toString().trim()),
                this::validateLast,
                result -> binding.textRegisterFirstName.setError("Please enter a first name."));
    }

    /**
     *
     */
    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.textRegisterLastName.getText().toString().trim()),
                this::validateEmail,
                result -> binding.textRegisterLastName.setError("Please enter a last name."));
    }

    /**
     *
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.textRegisterEmail.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.textRegisterEmail.setError("Please enter a valid Email address."));
    }

    /**
     *
     */
    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.textRegisterPassword2.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.textRegisterPassword.getText().toString().trim()),
                this::validatePassword,
                result -> binding.textRegisterPassword.setError("Passwords must match."));
    }

    /**
     *
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.textRegisterPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.textRegisterPassword.setError("Please enter a valid Password."));
    }

    /**
     *
     */
    private void verifyAuthWithServer() {
        mRegisterModel.connect(
                binding.textRegisterFirstName.getText().toString(),
                binding.textRegisterLastName.getText().toString(),
                binding.textRegisterEmail.getText().toString(),
                binding.textRegisterPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    /**
     *
     */
    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToSignInFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment();

        directions.setEmail(binding.textRegisterEmail.getText().toString());
        directions.setPassword(binding.textRegisterPassword.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.textRegisterEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}