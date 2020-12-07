package com.example.groupproject_g3.chat.fragments;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

public class ChatListViewModel extends AndroidViewModel {

    private Map<Integer, MutableLiveData<List<ChatItem>>> mChats;
    private ChatViewModel mChatView;

    private static final String chatsURL = "https://cloud-chat-450.herokuapp.com/chats/";


    /**
     *
     * @param application
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChats = new HashMap<>();
    }

    public void addMessageObserver(int chatId,
                                   @NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<ChatItem>> observer) {
        getOrCreateMapEntry(chatId).observe(owner, observer);
    }

    public List<ChatItem> getChatListByChatId(final int chatId) {
        return getOrCreateMapEntry(chatId).getValue();
    }

    private MutableLiveData<List<ChatItem>> getOrCreateMapEntry(final int chatId) {
        if(!mChats.containsKey(chatId)) {
            mChats.put(chatId, new MutableLiveData<>(new ArrayList<>()));
        }
        return mChats.get(chatId);
    }

    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PTOJECT
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject response) {
        List<ChatItem> list;
        if (!response.has("chatId")) {
            throw new IllegalStateException("Unexpected response in ChatViewModel: " + response);
        }
        try {
            list = getChatListByChatId(response.getInt("chatId"));
            JSONArray chats = response.getJSONArray("rows");
            for(int i = 0; i < chats.length(); i++) {
                JSONObject chat = chats.getJSONObject(i);
                ChatItem cItem = new ChatItem(
                        chat.getInt("chatID"),
                        chat.getString("name")
                );
                if (!list.contains(cItem)) {
                    // don't add a duplicate
                    list.add(0, cItem);
                } else {
                    // this shouldn't happen but could with the asynchronous
                    // nature of the application
                    Log.wtf("Chat message already received",
                            "Or duplicate id:" + cItem.getChatID());
                }

            }
            //inform observers of the change (setValue)
            getOrCreateMapEntry(response.getInt("chatId")).setValue(list);
        }catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }


    public void connectGet(String authVal, int userId) {
        Request request = new JsonObjectRequest(Request.Method.GET,
                chatsURL +
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