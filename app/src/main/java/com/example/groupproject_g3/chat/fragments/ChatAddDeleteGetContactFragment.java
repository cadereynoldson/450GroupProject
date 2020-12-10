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
import com.example.groupproject_g3.contact.fragments.ContactItem;
import com.example.groupproject_g3.contact.fragments.ContactListViewModel;
import com.example.groupproject_g3.databinding.FragmentChatAddDeleteGetContactBinding;
import com.example.groupproject_g3.databinding.FragmentChatItemBinding;
import com.example.groupproject_g3.model.UserInfoViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatAddDeleteGetContactFragment extends Fragment {

    /** The selected search by value. Default is email.*/
    private String selectedSearch = "Email";

    /**Display for the contacts spinner if empty*/
    private final String noContactsString = "You have no contacts!";

    /**Display for the chats spinner if empty*/
    private final String noChatsString = "You have no chats!";

    /**Binding object for fragment reference*/
    private FragmentChatAddDeleteGetContactBinding binding;

    /**Instance of user info view model*/
    private UserInfoViewModel mUserInfoModel;

    /**Instance of chat add delete get contact view model view model*/
    private ChatAddDeleteGetContactViewModel mChatAddDelGetModel;

    /**Instance of contact list view model*/
    private ContactListViewModel contactsViewModel;

    /**Instance of chat list view model*/
    private ChatListViewModel mChatListModel;

    /**List of the current chats*/
    private List<ChatItem> mChats;

    /**My spinner adapter*/
    private ArrayAdapter<String> mAdapter;

    /**List of current chat IDs*/
    private List<Integer> mChatIDs;

    /**Chat items mapped based on their string representation in the spinner.*/
    private HashMap<String, ChatItem> chatItems;

    /**Contact items mapped based on their string representation in the spinner.*/
    private HashMap<String, ContactItem> contactItems;

    /**Adapter for displaying contacts in a spinner.*/
    private ArrayAdapter<String> contactsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mChatAddDelGetModel = provider.get(ChatAddDeleteGetContactViewModel.class);
        mChatListModel = provider.get(ChatListViewModel.class);
        mUserInfoModel = provider.get(UserInfoViewModel.class);
        contactsViewModel = provider.get(ContactListViewModel.class);
        mChatListModel.connectGet(mUserInfoModel.getJwt(), mUserInfoModel.getUserId());
        contactsViewModel.connectGet(mUserInfoModel.getJwt(), mUserInfoModel.getUserId());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = FragmentChatAddDeleteGetContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.buttonAddContactToChat.setOnClickListener(this::addContact);
        binding.buttonDeleteContactFromChat.setOnClickListener(this::deleteContact);
        mChatAddDelGetModel.addResponseObserver(getViewLifecycleOwner(), this::observeResponse);
        mChatListModel.addChatListObserver(getViewLifecycleOwner(), list -> {
            createChatListSpinnerAdapter(list);
        });
        contactsViewModel.addContactsListObserver(getViewLifecycleOwner(), contactItems -> {
            createContactListSpinnerAdapter(contactItems);
        });
    }


    /**
     * Create a spinner adapter from the list of the users current chat.
     *
     * @param items list of current chat items.
     */
    private void createChatListSpinnerAdapter(List<ChatItem> items) {
        List<String> spinnerArray = new ArrayList<>();
        chatItems = new HashMap<>();
        mChats = items;
        mChatIDs = new ArrayList<>();
        if (items.isEmpty()) {
            spinnerArray.add(noChatsString);
        } else {
            for (ChatItem item : items) {
                String displayName = item.getChatName();
                chatItems.put(displayName, item);
                spinnerArray.add(displayName);
            }
        }
        mAdapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(), R.layout.spinner_item, spinnerArray);
        mAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerSelectChat.setAdapter(mAdapter);
    }


    /**
     * Creates a spinner adapter that loads users current contact list.
     *
     * @param items the list of contact items.
     */
    private void createContactListSpinnerAdapter(final List<ContactItem> items) {
        List<String> spinnerArray = new ArrayList<>();
        contactItems = new HashMap<>();
        if (items.isEmpty()) {
            spinnerArray.add(noContactsString);
        } else {
            for (ContactItem item : items) {
                String displayName = item.getUserName() + "\n(" + item.getFirstName() + " " + item.getLastName() + ")";
                contactItems.put(displayName, item);
                spinnerArray.add(displayName);
            }
        }
        contactsAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, spinnerArray);
        contactsAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerSelectContact.setAdapter(contactsAdapter);
    }

    /**
     * Calls the add view model with the user input.
     *
     * @param view the view in which this is called by.
     */
    private void addContact(final View view) {
        String contactString = binding.spinnerSelectContact.getSelectedItem().toString();
        String chatName = binding.spinnerSelectChat.getSelectedItem().toString();
        ChatItem chatItem = chatItems.get(chatName);
        ContactItem contactItem = contactItems.get(contactString);
        if (contactItem != null && chatItem != null) {
            Log.i("Add to chat", "Attempting to add " + contactItem.getUserName() + " to " + chatItem.getChatName());
            mChatAddDelGetModel.connectPut(mUserInfoModel.getJwt(), contactItem.getUserId(), chatItem.getChatID());
        } else {
            binding.textSelectedContact.setError("Futile effort...added nobody complete.");
        }
    }

    /**
     * Calls the delete view model with the user input.
     *
     * @param view the view in which this is called by.
     */
    private void deleteContact(final View view) {
        String contactString = binding.spinnerSelectContact.getSelectedItem().toString();
        String chatName = binding.spinnerSelectChat.getSelectedItem().toString();
        ChatItem chatItem = chatItems.get(chatName);
        ContactItem contactItem = contactItems.get(contactString);
        if (contactItem != null && chatItem != null) {
            Log.i("Delete from chat", "Attempting to delete " + contactItem.getUserName() + " from " + chatItem.getChatName());
            mChatAddDelGetModel.connectDelete(mUserInfoModel.getJwt(), chatItem.getChatID(), contactItem.getEmail());
        } else {
            binding.textSelectedContact.setError("Cannot divide by Zero...please have contact present.");
        }
    }

    /**
     * Observers responses from the server.
     *
     * @param response the response JSON object.
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
//                Snackbar notification = Snackbar.make(binding.manageChatRoot, R.string.chat_manip_error, Snackbar.LENGTH_SHORT);
//                notification.setAnchorView(R.id.bottom_nav_menu);
//                notification.show();
            } else {
                Snackbar notification = Snackbar.make(binding.manageChatRoot, R.string.chat_manip_success, Snackbar.LENGTH_SHORT);
                notification.setAnchorView(R.id.bottom_nav_menu);
                notification.show();
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
            selectedSearch = selectedVal;
        }

        @Override
        public void onNothingSelected(final AdapterView<?> parent) {
        }
    }
}