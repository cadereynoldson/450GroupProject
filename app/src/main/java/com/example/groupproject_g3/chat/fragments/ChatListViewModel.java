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

    private MutableLiveData<List<ChatItem>> mChats;

    private static final String chatsURL = "https://cloud-chat-450.herokuapp.com/chats/";
    private static final String memberURL = "https://cloud-chat-450.herokuapp.com/chats/chatmember/";

    /**
     *
     * @param application
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChats = new MutableLiveData<>();
        mChats.setValue(new ArrayList<ChatItem>());
    }

    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super List<ChatItem>> observer) {
        mChats.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());

    }

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


    public void connectGet(String authVal, int userId) {
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

    public void connectDelete(String authVal, int chatId, String userEmail) {
        String url = chatsURL +
                "?chatId=" +
                chatId +
                "?email=" +
                userEmail;
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

    private void handleDeleteError(VolleyError volleyError) {
        Log.e("Delete Error:", "Error in deletion.");
    }

    private void handleDelete(JSONObject jsonObject) {
        Log.i("Delete Successful.", "Success in deletion");
    }
}