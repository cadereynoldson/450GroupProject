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
import com.example.groupproject_g3.databinding.FragmentSignInBinding;
import com.example.groupproject_g3.utils.PasswordValidator;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.groupproject_g3.utils.PasswordValidator.checkExcludeWhiteSpace;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdLength;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdSpecialChar;

/**
 * A fragment for signing into the application.
 */
public class SignInFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentSignInBinding binding;

    /** */
    private SignInViewModel mSignInModel;

    /** */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /** */
    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    /** Require empty public constructor for SignInFragment. */
    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(SignInViewModel.class);
    }

    /**
     *
     *
     * @param container
     * @param inflater
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     *
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSigninRegister.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                ));

        binding.buttonSignin.setOnClickListener(this::attemptSignIn);

        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);

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
        validateEmail();
    }

    /**
     *
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.textSigninEmail.getText().toString().trim()),
                this::validatePassword,
                result -> binding.textSigninEmail.setError("Please enter a valid Email address."));
    }

    /**
     *
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.textSigninPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.textSigninPassword.setError("Please enter a valid Password."));
    }

    /**
     *
     */
    private void verifyAuthWithServer() {
        mSignInModel.connect(
                binding.textSigninEmail.getText().toString(),
                binding.textSigninPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     * @param email users email
     * @param jwt the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt) {
        Navigation.findNavController(getView())
                .navigate(SignInFragmentDirections
                        .actionSignInFragmentToMainActivity(email, jwt));
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
                    binding.textSigninEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                try {
                    navigateToSuccess(
                            binding.textSigninEmail.getText().toString(),
                            response.getString("token")
                    );
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}