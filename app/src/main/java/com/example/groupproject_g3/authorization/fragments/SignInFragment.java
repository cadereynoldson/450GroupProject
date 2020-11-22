/**
 * Landing fragment for Sign-In.
 */
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
import com.example.groupproject_g3.model.PushyTokenViewModel;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.example.groupproject_g3.utils.PasswordValidator;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.groupproject_g3.utils.PasswordValidator.checkExcludeWhiteSpace;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdLength;
import static com.example.groupproject_g3.utils.PasswordValidator.checkPwdSpecialChar;


public class SignInFragment extends Fragment {

    /** Binding for the fragment. Allows for direct reference of fragment components. */
    private FragmentSignInBinding binding;

    /** View Model for the fragment. */
    private SignInViewModel mSignInModel;

    private PushyTokenViewModel mPushyTokenViewModel;
    private UserInfoViewModel mUserViewModel;


    /** An Email validator that checks for the length, white spaces, and special char '@'. */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /** A Password validator that checks for the length and white spaces. */
    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    /** Require empty public constructor for the Fragment. */
    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(SignInViewModel.class);
        mPushyTokenViewModel = new ViewModelProvider(getActivity())
                .get(PushyTokenViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

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

        //don't allow sign in until pushy token retrieved
        mPushyTokenViewModel.addTokenObserver(getViewLifecycleOwner(), token ->
                binding.buttonSignin.setEnabled(!token.isEmpty()));


        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        binding.textSigninEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.textSigninPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());

        mPushyTokenViewModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observePushyPutResponse);
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
     *Email validation.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.textSigninEmail.getText().toString().trim()),
                this::validatePassword,
                result -> binding.textSigninEmail.setError("Please enter a valid Email address."));
    }

    /**
     *Password validator.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.textSigninPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.textSigninPassword.setError("Please enter a valid Password."));
    }

    /**
     *Checks authentication with backend server.
     */
    private void verifyAuthWithServer() {
        mSignInModel.connect(
                binding.textSigninEmail.getText().toString(),
                binding.textSigninPassword.getText().toString());
    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     * @param email users email
     * @param jwt the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt, final int userId) {
        Navigation.findNavController(getView())
                .navigate(SignInFragmentDirections
                        .actionSignInFragmentToMainActivity(email, jwt, userId));
        getActivity().finish();
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
                    mUserViewModel = new ViewModelProvider(getActivity(),
                            new UserInfoViewModel.UserInfoViewModelFactory(
                                    response.getString("token")
                            )).get(UserInfoViewModel.class);
                    sendPushyToken();

                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    /**
     * Helper to abstract the request to send the pushy token to the web service
     */
    private void sendPushyToken() {
        mPushyTokenViewModel.sendTokenToWebservice(mUserViewModel.getJwt());
    }
    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to PushyTokenViewModel.
     *
     * @param response the Response from the server
     */
    private void observePushyPutResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                //this error cannot be fixed by the user changing credentials...
                binding.textSigninEmail.setError(
                        "Error Authenticating on Push Token. Please contact support");
            } else {
                navigateToSuccess(
                        binding.textSigninEmail.getText().toString(),
                        mUserViewModel.getJwt(), mUserViewModel.getUserId()
                );
            }
        }
    }

}