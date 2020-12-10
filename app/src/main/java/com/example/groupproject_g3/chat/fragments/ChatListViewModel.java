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

    /**Array of returned users from string*/
    private MutableLiveData<List<String>> mChatMembers;

    /**URL for get contacts request*/
    private static final String CONTACT_URL = "https://cloud-chat-450.herokuapp.com/chats/";

    /**The landing URL for chats with member*/
    private static final String MEMBER_URL = "https://cloud-chat-450.herokuapp.com/chats/chatmember/";

    /**The url to delete a whole chatroom*/
    private static final String DELETE_CHAT_URL = "https://cloud-chat-450.herokuapp.com/chats/delete/";

    /**
     *Constructor.
     *
     * @param application the application.
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChats = new MutableLiveData<>();
        mChats.setValue(new ArrayList<ChatItem>());
        mChatMembers = new MutableLiveData<>();
        mChatMembers.setValue(new ArrayList<String>());
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
     * Adds a response observer.
     *
     * @param owner owner
     * @param observer observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<String>> observer) {
        mChatMembers.observe(owner, observer);
    }

    /**
     * Get the list of current users within chat.
     *
     * @param authVal JWT for authentication.
     * @param chatID the current chat ID.
     */
    public void connectGetChatMembers(final String authVal, final int chatID) {
        JSONObject body = new JSONObject();
        try {
            body.put("chatid", chatID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                CONTACT_URL +
                        chatID,
                null,
                this::observeSuccess,
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
     * Handles the positive result. Parses the data and returns a chat item that is
     * placed into the chat list.
     *
     * @param result the result.
     */
    private void observeSuccess(final JSONObject result) {
        try {
            JSONObject root = result;
            Log.i("Result: ", result.toString());
            if (root.has("rows")) {
                JSONArray data =
                        root.getJSONArray("rows");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonContactEmail = data.getJSONObject(i);
                    mChatMembers.getValue().add(jsonContactEmail.getString("email") + ", ");
                    Log.i("Returned ChatMembers: ", mChatMembers.toString());
                }
            } else {
                Log.e("ERROR!", "No Chat Members array!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR!", e.getMessage());
        }
        mChatMembers.setValue(mChatMembers.getValue());
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
                MEMBER_URL +
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

    /**
     * Deletes a chatroom and all tts users from chatmembers.
     *
     * @param authVal the JWT authorization value.
     * @param chatId the id of chat.
     */
    public void connectDeleteChat(final String authVal, final int chatId) {
        JSONObject body = new JSONObject();
        try {
            body.put("chatid", chatId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                DELETE_CHAT_URL +
                chatId,
                body,
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
     * Handles the error of an deletion of a contact.
     * @param volleyError object containing the info of the error.
     */
    private void handleDeleteError(VolleyError volleyError) {
        Log.e("Delete Error:", "Error in deletion.");
        volleyError.printStackTrace();
    }

    /**
     * Handles the deletion of a contact.
     * @param jsonObject the final returned json object.
     */
    private void handleDelete(JSONObject jsonObject) {
        Log.i("Delete Successful.", "Success in deletion");
    }

}