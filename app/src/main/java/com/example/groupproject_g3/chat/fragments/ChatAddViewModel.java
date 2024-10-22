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

public class ChatAddViewModel extends AndroidViewModel {

    /**The landing URL*/
    private final String chatsURL = "https://cloud-chat-450.herokuapp.com/chats/";

    /**The current response*/
    private MutableLiveData<JSONObject> mResponse;

    /**The current chat ID for adding*/
    public int mChatID;


    /**
     * Constructor.
     *
     * @param application the application.
     */
    public ChatAddViewModel(@NonNull Application application) {
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
     * Calls the post method from the service to add the chat and adding the
     * adding the creator into the chat.
     *
     * @param authVal JWT for authentication.
     * @param userID the current users ID.
     * @param chatName the chat name.
     */
    public void addChatAndCreator(final String authVal, final int userID, final String chatName) {
        JSONObject body = new JSONObject();
        try {
            body.put("name", chatName);
            body.put("memberid", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                chatsURL,
                body,
                this::handleSuccess,
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
     * Successful response handling. Stores the chat Id and sets the value.
     *
     * @param response the response.
     */
    private void handleSuccess(final JSONObject response) {
        if (!response.has("chatID")) {
            throw new IllegalStateException("Unexpected response in ChatViewModel: " + response);
        }
        try {
            response.put("success", true);
            mResponse.setValue(response);
            mChatID = response.getInt("chatID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Error handling from response.
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
