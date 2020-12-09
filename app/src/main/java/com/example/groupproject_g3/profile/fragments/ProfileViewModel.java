package com.example.groupproject_g3.profile.fragments;

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
import com.example.groupproject_g3.contact.fragments.ContactItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

public class ProfileViewModel extends AndroidViewModel {

    /** The information of this user. */
    private MutableLiveData<ContactItem> myInfo;

    /** The index of the selected theme. */
    private int selectedThemeIndex;

    /** The URL to use for fetching user information. */
    private static final String profileInfoURL = "https://cloud-chat-450.herokuapp.com/profile/";

    /**
     * Creates a new instance of the profile view model.
     * @param application the parent application.
     */
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        myInfo = new MutableLiveData<>();
        selectedThemeIndex = 0;
    }

    /**
     * Changes the selected theme index.
     * @param newIndex the new index of the selected theme.
     */
    public void setSelectedThemeIndex(int newIndex) {
        selectedThemeIndex = newIndex;
    }

    /**
     * Returns the selected theme index.
     * @return
     */
    public int getSelectedThemeIndex() {
        return selectedThemeIndex;
    }

    /**
     * Adds an observer to the mutable live data of this class.
     * @param owner the owner of the lifecycle.
     * @param observer the observer of the live data.
     */
    public void addProfileObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<ContactItem> observer) {
        myInfo.observe(owner, observer);
    }

    /**
     * Fetches the profile information of the user.
     * @param authVal the JWT authorization token.
     * @param userId the user id.
     */
    public void getProfileInfo(String authVal, int userId) {
        Request request = new JsonObjectRequest(Request.Method.GET,
                profileInfoURL + userId,
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
     * Handles an error when fetching contact info.
     * @param volleyError the volley error.
     */
    private void handleError(VolleyError volleyError) {
        Log.e("Error", "Error with profile request");
        volleyError.printStackTrace();
    }

    /**
     * Handles a result from the profile info request.
     * @param result the JSON object from the server.
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
                JSONObject profileInfo = data.getJSONObject(0);
                ContactItem post = new ContactItem(
                        profileInfo.getString(getString.apply(R.string.key_contacts_first_name)),
                        profileInfo.getString(getString.apply(R.string.key_contacts_last_name)),
                        profileInfo.getString(getString.apply(R.string.key_contacts_username)),
                        profileInfo.getString(getString.apply(R.string.key_contacts_email)),
                        profileInfo.getInt(getString.apply(R.string.key_contacts_memberId)));
                    Log.i("Parsed Profile Info", post.toString());
                myInfo.setValue(post);
            } else {
                Log.e("ERROR!", "No contacts array!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
    }
}
