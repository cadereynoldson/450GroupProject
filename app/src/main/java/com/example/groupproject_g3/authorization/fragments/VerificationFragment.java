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
import com.example.groupproject_g3.databinding.FragmentVerificationBinding;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificationFragment extends Fragment {

    /**Binding for access to fragment assets*/
    private FragmentVerificationBinding binding;

    /**View model for access to the webservice and housing information*/
    private VerificationViewModel mVerifyViewModel;

    /**Field for storing of user email argument*/
    private String mEmail;

    /**Field for storing of password argument*/
    private String mPassword;

    /**
     * Required empty constructor
     */
    public VerificationFragment(){

    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerificationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVerifyViewModel = new ViewModelProvider(getActivity())
                .get(VerificationViewModel.class);
    }

    /**
     * Call to send the email from our backend landing within the viewmodel.
     *
     * @param view the current view.
     */
    private void sendEmail(View view) {
        mVerifyViewModel.connect(mEmail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVerifyViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

        binding.buttonVerificationSendagain.setOnClickListener(this::sendEmail);
        binding.buttonVerificationReturn.setOnClickListener(button ->
                navigateToLogin());

        VerificationFragmentArgs args = VerificationFragmentArgs.fromBundle(getArguments());
        mEmail = args.getEmail();
        mPassword = args.getPassword();

    }

    /**
     * Once verify is successful, navigate to login using email and password.
     */
    private void navigateToLogin() {
        VerificationFragmentDirections.ActionVerificationFragmentToSignInFragment directions =
                VerificationFragmentDirections.actionVerificationFragmentToSignInFragment();

        directions.setEmail(mEmail);
        directions.setPassword(mPassword);

        Navigation.findNavController(getView()).navigate(directions);
//        getActivity().finish();
    }

    /**
     * An observer on the HTTP Response from the web server.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.buttonVerificationSendagain.setError(
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