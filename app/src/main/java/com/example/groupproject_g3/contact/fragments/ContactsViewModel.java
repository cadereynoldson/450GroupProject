package com.example.groupproject_g3.contact.fragments;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject_g3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
/**
 * A  {@link AndroidViewModel} subclass.
 * create an instance of this fragment.
 *
 * Performs retrieval of and addition to the contact database from the application.
 */
public class ContactsViewModel extends AndroidViewModel {

    private MutableLiveData<List<ContactItem>> mContacts;

    private static final String connectionUrl = "https://cloud-chat-450.herokuapp.com/contacts/";

    /**
     *
     * @param application
     */
    public ContactsViewModel(@NonNull Application application) {
        super(application);
        mContacts = new MutableLiveData<>();
        mContacts.setValue(new ArrayList<ContactItem>());
    }

    public void addContactsListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ContactItem>> observer) {
        mContacts.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PTOJECT
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject result) {
        IntFunction<String> getString = //Converts an integer key to a string.
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            Log.i("Result: ", result.toString());
            if (root.has(getString.apply(R.string.key_contacts_list))) {
                JSONArray data =
                        root.getJSONArray(getString.apply(R.string.key_contacts_list));
                for(int i = 0; i < data.length(); i++) {
                    JSONObject jsonContactInfo = data.getJSONObject(i);
                    ContactItem post = new ContactItem(
                        jsonContactInfo.getString(getString.apply(R.string.key_contacts_first_name)),
                        jsonContactInfo.getString(getString.apply(R.string.key_contacts_last_name)),
                        jsonContactInfo.getString(getString.apply(R.string.key_contacts_username)),
                        jsonContactInfo.getString(getString.apply(R.string.key_contacts_email)));
                    Log.i("Parsed Contact", post.toString());
                    if (!mContacts.getValue().contains(post)) {
                         mContacts.getValue().add(post);
                    }
                }
            } else {
                Log.e("ERROR!", "No contacts array!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mContacts.setValue(mContacts.getValue());
    }

    public void connectGet(String authVal, int userId) {
        Request request = new JsonObjectRequest(Request.Method.GET,
                connectionUrl + userId,
                null, //no body for get request.
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", authVal);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }
}

