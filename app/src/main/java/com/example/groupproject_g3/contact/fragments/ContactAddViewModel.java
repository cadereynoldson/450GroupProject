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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The view model for adding a user via the add contacts page.
 */
public class ContactAddViewModel extends AndroidViewModel {

    /** The URL for adding a contact by email. */
    private final String emailUrl = "https://cloud-chat-450.herokuapp.com/contacts/add_by_email";

    /** The URL for adding a contact by username. */
    private final String usernameUrl = "https://cloud-chat-450.herokuapp.com/contacts/add_by_username";

    /** The latest response from the webserver. */
    private MutableLiveData<JSONObject> mResponse;

    /**
     * Creates a new instance of this view model.
     * @param application the parent view model.
     */
    public ContactAddViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Response Observer. Observes the response from the webservice.
     *
     * @param owner owner.
     * @param observer observer.
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Tries to add a contact by their email.
     * @param authVal the JWT used for authorization
     * @param userId the user id of the current user.
     * @param email the email of the other user.
     */
    public void addContactByEmail(String authVal, int userId, String email) {
        JSONObject body = new JSONObject();
        try {
            body.put("email", email);
            body.put("memberId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                emailUrl,
                body,
                this::showSuccess,
                this::handleError

        ) {
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
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Attempts to add a contact by their username.
     * @param authVal the JWT used of authorization/
     * @param userId the user id of the current user.
     * @param username the username of the other user.
     */
    public void addContactByUsername(String authVal, int userId, String username) {
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("memberId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                usernameUrl,
                body,
                mResponse::setValue,
                this::handleError

        ) {
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
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Places a successful json response in the live mutable data.
     * @param jsonObject the response from the server.
     */
    private void showSuccess(JSONObject jsonObject) {
        try {
            JSONObject response = new JSONObject();
            response.put("success", true);
            mResponse.setValue(response);
        } catch (JSONException e) {
            Log.e("JSON PARSE", "JSON Parse Error in handleError");
        }
    }

    /**
     * Handles an error in the addition of a contact. Notifies the contact add fragment that there has been an error.
     * @param error object containing the info of the error.
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", error.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }
}
