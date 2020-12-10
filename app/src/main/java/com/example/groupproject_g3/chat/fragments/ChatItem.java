package com.example.groupproject_g3.chat.fragments;

public class ChatItem {

    /**The chat ID*/
    private final int mChatID;

    /**The chat name*/
    private final String mChatName;

    public ChatItem(final int theChatID, final String theChatName){
        this.mChatID = theChatID;
        this.mChatName = theChatName;
       }

    /**
     * Getter for chat ID
     *
     * @return mChatID the chat id.
     */
    public int getChatID(){
        return mChatID;
    }

    /**
     * Getter for chat name.
     *
     * @return mChatName the chat name.
     */
    public String getChatName(){
        return mChatName;
    }

    /**
     * Turns the answers into a parsed string.
     *
     * @return the string.
     */
    public String toString(){
        return "ChatName = " + mChatName + ", ChatID = " + mChatID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChatItem))
            return false;
        else
            return ((ChatItem) o).getChatID() == mChatID;
    }

}