package com.example.groupproject_g3.chat.fragments;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Encapsulate chat message details.
 */
public final class ChatMessage implements Serializable {

    /**the message id*/
    private final int mMessageId;

    /**the message*/
    private final String mMessage;

    /**the sender*/
    private final String mSender;

    /**the time stamp*/
    private final String mTimeStamp;

    /**
     * Constructor.
     *
     * @param messageId the id.
     * @param message the message.
     * @param sender the sender.
     * @param timeStamp the timestamp.
     */
    public ChatMessage(int messageId, String message, String sender, String timeStamp) {
        mMessageId = messageId;
        mMessage = message;
        mSender = sender;
        mTimeStamp = timeStamp;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * ChatMessage object.
     * @param cmAsJson the String to be parsed into a ChatMessage Object.
     * @return a ChatMessage Object with the details contained in the JSON String.
     * @throws JSONException when cmAsString cannot be parsed into a ChatMessage.
     */
    public static ChatMessage createFromJsonString(final String cmAsJson) throws JSONException {
        final JSONObject msg = new JSONObject(cmAsJson);
        return new ChatMessage(msg.getInt("messageid"),
                msg.getString("message"),
                msg.getString("email"),
                msg.getString("timestamp"));
    }

    /**
     * getter for message.
     *
     * @return mMessage.
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * getter for sender.
     *
     * @return mSender.
     */
    public String getSender() {
        return mSender;
    }

    /**
     * getter for timeStamp.
     *
     * @return mTimeStamp.
     */
    public String getTimeStamp() {
        return mTimeStamp;
    }

    /**
     * getter for messageID.
     *
     * @return mMessageId.
     */
    public int getMessageId() {
        return mMessageId;
    }

    /**
     * Provides equality solely based on MessageId.
     * @param other the other object to check for equality
     * @return true if other message ID matches this message ID, false otherwise
     */
    @Override
    public boolean equals(@Nullable Object other) {
        boolean result = false;
        if (other instanceof ChatMessage) {
            result = mMessageId == ((ChatMessage) other).mMessageId;
        }
        return result;
    }
}
