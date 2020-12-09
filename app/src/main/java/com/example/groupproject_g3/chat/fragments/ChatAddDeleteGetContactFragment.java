package com.example.groupproject_g3.chat.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.groupproject_g3.R;
import com.example.groupproject_g3.contact.fragments.ContactAddFragment;
import com.example.groupproject_g3.contact.fragments.ContactAddViewModel;
import com.example.groupproject_g3.databinding.FragmentChatAddDeleteGetContactBinding;
import com.example.groupproject_g3.databinding.FragmentChatItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatAddDeleteGetContactFragment extends Fragment {

    /**
     * The selected search by value. Default is email.
     */
    private String selectedSearch = "Email";
    private FragmentChatAddDeleteGetContactBinding binding;
    private UserInfoViewModel mUserInfoModel;
    private ChatAddDeleteGetContactViewModel mChatAddDelGetModel;
    private ChatListViewModel mChatListModel;
    private List<ChatItem> mChats;
    private ArrayAdapter<String> mAdapter;
    private List<Integer> mChatIDs;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mChatAddDelGetModel = provider.get(ChatAddDeleteGetContactViewModel.class);
        mChatListModel = provider.get(ChatListViewModel.class);
        mUserInfoModel = provider.get(UserInfoViewModel.class);
        mChatListModel.connectGet(mUserInfoModel.getJwt(), mUserInfoModel.getUserId());
        mChats = mChatListModel.getChats();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = FragmentChatAddDeleteGetContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createChatListSpinnerAdapter();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.chat_search_by_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerSearchBy.setAdapter(adapter);
        binding.spinnerSearchBy.setOnItemSelectedListener(new ChatAddDeleteGetContactFragment.SpinnerActivity());
        binding.buttonAddContactToChat.setOnClickListener(this::addContact);
        binding.buttonDeleteContactFromChat.setOnClickListener(this::deleteContact);
        mChatAddDelGetModel.addResponseObserver(getViewLifecycleOwner(), this::observeResponse);
    }

    private void createChatListSpinnerAdapter() {
        List<String> spinnerArray = new ArrayList<>();
        mChatIDs = new ArrayList<>();
        for (int i = 0; i < mChats.size(); i++) {
            String chatName = mChats.get(i).getChatName();
            int chatID = mChats.get(i).getChatID();
            mChatIDs.add(chatID);
            spinnerArray.add(chatName);
        }
        mAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSelectChat.setAdapter(mAdapter);
    }

    /**
     * Calls the add view model with the user input.
     *
     * @param view the view in which this is called by.
     */
    private void addContact(final View view) {
        final String input = binding.textSearch.getText().toString().toLowerCase();
        if (input.isEmpty()) {
            binding.textSearch.setError("Please enter a value.");
        }
        mChatAddDelGetModel.getID(mUserInfoModel.getJwt(), input);
        final int contactID = mChatAddDelGetModel.getmMemberID();
        final int chatID = mChatIDs.get((int) binding.spinnerSelectChat.getSelectedItemId());
        if (contactID == mUserInfoModel.getUserId()) {
            binding.textSearch.setError("Paradox attempted...do not add the added self to chat");
        }
        if (input.equals(mUserInfoModel.getEmail())) {
            binding.textSearch.setError("You were already added upon creation.");
        } else if (contactID == -1) {
            binding.textSearch.setError("get_id failed.");
        } else {
            mChatAddDelGetModel.connectPut(mUserInfoModel.getJwt(), contactID, chatID);
        }
    }

    /**
     * Calls the delete view model with the user input.
     *
     * @param view the view in which this is called by.
     */
    private void deleteContact(final View view) {
        final String temp = selectedSearch.toLowerCase();
        final IntFunction<String> getString = getResources()::getString;
        final String userEmail = binding.textSearch.getText().toString().toLowerCase();
        if (userEmail.isEmpty()) {
            binding.textSearch.setError("Please enter a value.");
        }
        final int chatID = mChatIDs.get((int) binding.spinnerSelectChat.getSelectedItemId());
        mChatAddDelGetModel.getID(mUserInfoModel.getJwt(), userEmail);
        final int contactID = mChatAddDelGetModel.getmMemberID();
        if (contactID == mUserInfoModel.getUserId()) {
            binding.textSearch.setError("It's not worth it...please stay strong!");
        } else if (contactID == -1) {
            binding.textSearch.setError("get_id failed!");
        } else if (!temp.equals(getString.apply(R.string.key_contacts_email).toLowerCase())) {
            binding.textSearch.setError("Please delete using email.");
        } else
            mChatAddDelGetModel.connectDelete(mUserInfoModel.getJwt(), chatID, userEmail);
    }

    /**
     * Observers responses from the server.
     *
     * @param response the response JSON object.
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.textSearch.setError(response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse error.", e.getMessage());
                }
            }
        }
    }

    /**
     * Spinner activity. Allows for custom use of the spinner object (Transitions between email and username).
     */
    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
            String selectedVal = parent.getItemAtPosition(position).toString();
            binding.textSearch.setHint(selectedVal);
            selectedSearch = selectedVal;
        }

        @Override
        public void onNothingSelected(final AdapterView<?> parent) {
        }
    }
}