/**
 *  View of the New Message count model.
 */
package com.example.groupproject_g3.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class NewMessageCountViewModel extends ViewModel {

    /** The data containing the message count. */
    private MutableLiveData<Integer> mNewMessageCount;

    /**
     * Model istance of the new message count.
     */
    public NewMessageCountViewModel() {
        mNewMessageCount = new MutableLiveData<>();
        mNewMessageCount.setValue(0);
    }

    /**
     * Contains the observer for adding a count for messages.
     * @param owner - the associated user
     * @param observer - the observer
     */
    public void addMessageCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewMessageCount.observe(owner, observer);
    }

    /**
     * Increments the message count by one.
     */
    public void increment() {
        mNewMessageCount.setValue(mNewMessageCount.getValue() + 1);
    }

    /**
     * Resets the message count.
     */
    public void reset() {
        mNewMessageCount.setValue(0);
    }
}
