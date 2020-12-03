package com.example.groupproject_g3.contact.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.databinding.FragmentAddContactBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ContactAddFragment extends Fragment {

    /** The binding which allows for direct reference of the components of the Add Contact Fragment. */
    private FragmentAddContactBinding binding;

    /** The selected search by value. Default is email. */
    private String selectedSearch = "Email";

    /** A direct reference to the user info view model. Easy access to JWT and user id. */
    private UserInfoViewModel userInfoViewModel;

    /** View model which allows for the addition of new contacts. */
    private ContactAddViewModel addViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        userInfoViewModel = provider.get(UserInfoViewModel.class);
        addViewModel = provider.get(ContactAddViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.contact_search_by_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerSearchBy.setAdapter(adapter);
        binding.spinnerSearchBy.setOnItemSelectedListener(new SpinnerActivity());
        binding.buttonAddContact.setOnClickListener(this::addContact);
        addViewModel.addResponseObserver(getViewLifecycleOwner(), this::observeResponse);
    }

    /**
     * Calls the add view model with the user input.
     * @param view the view in which this is called by.
     */
    private void addContact(View view) {
        String temp = selectedSearch.toLowerCase();
        IntFunction<String> getString = getResources()::getString;
        String search = binding.textSearch.getText().toString();
        if (search.isEmpty())
            binding.textSearch.setError("Please enter a value.");
        else if (temp.equals(getString.apply(R.string.key_contacts_email).toLowerCase()))
            addViewModel.addContactByEmail(userInfoViewModel.getJwt(), userInfoViewModel.getUserId(), search);
        else if (temp.equals(getString.apply(R.string.key_contacts_username).toLowerCase()))
            addViewModel.addContactByUsername(userInfoViewModel.getJwt(), userInfoViewModel.getUserId(), search);
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.textSearch.setError(response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse error.", e.getMessage());
                }
            } else {
                Log.e("Added Contact", "Added Contact");
            }
        }
    }

    /**
     * Spinner activity. Allows for custom use of the spinner object (Transitions between email and username).
     */
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedVal = parent.getItemAtPosition(position).toString();
            binding.textSearch.setHint(selectedVal);
            selectedSearch = selectedVal;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}