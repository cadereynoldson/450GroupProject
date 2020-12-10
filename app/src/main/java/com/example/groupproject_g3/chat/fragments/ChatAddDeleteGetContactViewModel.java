package com.example.groupproject_g3.chat.fragments;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatAddDeleteGetContactViewModel extends AndroidViewModel {

    /**The landing URL*/
    private static final String URL = "https://cloud-chat-450.herokuapp.com/chats/";

    /**The latest response from the webserver.*/
    private MutableLiveData<JSONObject> mResponse;


    /**
     * Constructor for view model.
     *
     * @param application
     */
    public ChatAddDeleteGetContactViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Response Observer. Observes the response from the webservice.
     * Resets value in the response ONLY for this model (Avoid displaying notifications twice)
     *
     * @param owner    owner.
     * @param observer observer.
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.setValue(new JSONObject());
        mResponse.observe(owner, observer);
    }

    /**
     * Get the list of current users within chat.
     *
     * @param authVal JWT for authentication.
     * @param chatID the current chat ID.
     */
    public void connectGet(final String authVal, final int chatID) {
        JSONObject body = new JSONObject();
        try {
            body.put("chatid", chatID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                URL +
                        chatID,
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
     * Call to add users into chat.
     *
     * @param authVal JWT for authentication.
     * @param userID the users ID for adding.
     * @param chatID the chat ID.
     */
    public void connectPut(final String authVal, final int userID, final int chatID) {
        JSONObject body = new JSONObject();
        try {
            body.put("chatId", chatID);
            body.put("memberId", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                URL,
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
     * Call to delete user from chat.
     *
     * @param authVal JWT for authentication.
     * @param chatId the current chat ID.
     * @param userEmail the user's email to delete.
     */
    public void connectDelete(final String authVal, final int chatId, final String userEmail) {
        String url = URL +
                chatId +
                "/" +
                userEmail;

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                this::handleDelete,
                this::handleDeleteError
        ) {
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
     * Handling of the error response from the server.
     *
     * @param error the error.
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
        } else {
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


    /**
     * handle delete error
     *
     * @param volleyError the error.
     */
    private void handleDeleteError(final VolleyError volleyError) {
        Log.e("Delete Error:", "Error in deletion.");
    }

    /**
     * Handles the actual delete
     *
     * @param jsonObject the response.
     */
    private void handleDelete(final JSONObject jsonObject) {
        Log.i("Delete Successful.", "Success in deletion");
    }
}
