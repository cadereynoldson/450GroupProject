package com.example.groupproject_g3.contact.fragments;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
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
 * View model for contact requests this user has sent.
 */
public class ContactSentRequestsViewModel extends AndroidViewModel {

    /** Mutable live data of a list of contacts requests this user has sent. */
    private MutableLiveData<List<ContactItem>> mContacts;

    /** The url used for fetching users that this user has added as a contact. */
    private static final String connectionUrl = "https://cloud-chat-450.herokuapp.com/contacts/pending/";

    /** The url used for deleting sent contact requests. */
    private static final String deleteContactsURL = "https://cloud-chat-450.herokuapp.com/contacts/delete/";

    /**
     * Creates a new instance of the view model.
     * @param application the parent application.
     */
    public ContactSentRequestsViewModel (@NonNull Application application) {
        super(application);
        mContacts = new MutableLiveData<>();
        mContacts.setValue(new ArrayList<ContactItem>());
    }

    /**
     * Creates a contacts list observer. Observes changes of a list.
     * @param owner the owner of this lifecycle.
     * @param observer the observer.
     */
    public void addContactsListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ContactItem>> observer) {
        mContacts.observe(owner, observer);
    }

    /**
     * Logs that there has been an error in connecting to the server.
     * @param error the connection error.
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    /**
     * Handles parsing the resulting contacts who have added this user.
     * @param result the result to parse.
     */
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
                            jsonContactInfo.getString(getString.apply(R.string.key_contacts_email)),
                            jsonContactInfo.getInt(getString.apply(R.string.key_contacts_memberId)));
                    Log.i("Parsed Sent Contact Request", post.toString());
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

    /**
     * Connects to the server and fetches the sent contact requests of a given user.
     * @param authVal the authorization value of the user.
     * @param userId the id of the user.
     */
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

    /**
     * Deletes a contact request between this user and another user.
     * @param authVal the JWT used for authorization.
     * @param userIdOne the id of the user.
     * @param userIdTwo the id of the other user.
     */
    public void connectDelete(String authVal, int userIdOne, int userIdTwo) {
        String url = deleteContactsURL + "?memberIdOne=" + userIdOne + "&memberIdTwo=" + userIdTwo;
        Request request = new JsonObjectRequest(Request.Method.DELETE,
                url,
                null,
                this::handleDelete,
                this::handleDeleteError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", authVal);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Handles the error of an deletion of a contact request.
     * @param volleyError object containing the info of the error.
     */
    private void handleDeleteError(VolleyError volleyError) {
        Log.e("Delete Error:", "Error in deletion.");
        volleyError.printStackTrace();
    }

    /**
     * Handles the deletion of a contact request.
     * @param jsonObject the final returned json object.
     */
    private void handleDelete(JSONObject jsonObject) {
        Log.i("Delete Successful.", "Success in deletion");
    }
}
