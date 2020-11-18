package com.example.groupproject_g3.contact.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * A view model of the contact page.
 *
 * @author
 * @version November 2020
 */
public class ContactsViewModel extends AndroidViewModel {

    private MutableLiveData<List<ContactItem>> mContacts;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        mContacts = new MutableLiveData<>();
        mContacts.setValue(new ArrayList<ContactItem>());
    }
}
