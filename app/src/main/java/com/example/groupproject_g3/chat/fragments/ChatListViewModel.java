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
import com.example.groupproject_g3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class ChatListViewModel extends AndroidViewModel {

    /**The list of chats*/
    private MutableLiveData<List<ChatItem>> mChats;

    /**The landing URL*/
    private static final String memberURL = "https://cloud-chat-450.herokuapp.com/chats/chatmember/";

    /**
     *Constructor.
     *
     * @param application the application.
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChats = new MutableLiveData<>();
        mChats.setValue(new ArrayList<ChatItem>());
    }

    /**
     * Adds an observer to the chat list.
     *
     * @param owner the owner.
     * @param observer the observer.
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ChatItem>> observer) {
        mChats.observe(owner, observer);
    }

    /**
     * Handles the errorr from the respsone.
     *
     * @param error the error.
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());

    }

    /**
     * Handles the positive result. Parses the data and returns a chat item that is
     * placed into the chat list.
     *
     * @param result the result.
     */
    private void handleResult(final JSONObject result) {
        IntFunction<String> getString = //Converts an integer key to a string.
                getApplication().getResources()::getString;
        try {
            JSONObject root = result;
            Log.i("Result: ", result.toString());
            if (root.has(getString.apply(R.string.key_chats_list))) {
                JSONArray data =
                        root.getJSONArray(getString.apply(R.string.key_chats_list));
                for(int i = 0; i < data.length(); i++) {
                    JSONObject jsonChatInfo = data.getJSONObject(i);
                    ChatItem post = new ChatItem(
                            jsonChatInfo.getInt("chatid"),
                            jsonChatInfo.getString("name"));
                            Log.i("Parsed chat room", post.toString());
                    if (!mChats.getValue().contains(post)) {
                        mChats.getValue().add(post);
                    }
                }
            } else {
                Log.e("ERROR!", "No chats array!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mChats.setValue(mChats.getValue());
    }


    /**
     * Gets a list of the current chats assiciated with member id.
     *
     * @param authVal JWT for authentication.
     * @param userId the user id.
     */
    public void connectGet(final String authVal, final int userId) {
        Request request = new JsonObjectRequest(Request.Method.GET,
                memberURL +
                userId,
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