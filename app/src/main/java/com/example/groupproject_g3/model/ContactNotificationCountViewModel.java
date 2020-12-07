package com.example.groupproject_g3.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ContactNotificationCountViewModel extends ViewModel {
    private MutableLiveData<Integer> contactNotificationCount;

    public ContactNotificationCountViewModel() {
        contactNotificationCount = new MutableLiveData<>();
        contactNotificationCount.setValue(0);
    }

    public void addNotifictionCountObserver(@NonNull LifecycleOwner owner,
                                            @NonNull Observer<? super Integer> observer) {
        contactNotificationCount.observe(owner, observer);
    }

    public void increment() {
        contactNotificationCount.setValue(contactNotificationCount.getValue() + 1);
    }

    public void reset() {
        contactNotificationCount.setValue(0);
    }
}
