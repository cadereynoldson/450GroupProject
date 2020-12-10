/**
 * View of the New Chat count model.
 */
package com.example.groupproject_g3.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class NewChatCountViewModel extends ViewModel {

    /** The data containing the chat count. */
    private MutableLiveData<Integer> mNewChatCount;

    /**
     * Model istance of the new chat count.
     */
    public NewChatCountViewModel() {
        mNewChatCount = new MutableLiveData<>();
        mNewChatCount.setValue(0);
    }

    /**
     * Contains the observer for adding a count for messages.
     * @param owner - the associated user
     * @param observer - the observer
     */
    public void addChatCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewChatCount.observe(owner, observer);
    }

    /**
     * Increments the chat count by one.
     */
    public void increment() {
        mNewChatCount.setValue(mNewChatCount.getValue() + 1);
    }

    /**
     * Resets the chat count.
     */
    public void reset() {
        mNewChatCount.setValue(0);
    }
}
