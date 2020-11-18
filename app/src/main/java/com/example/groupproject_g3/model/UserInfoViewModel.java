/**
 * View Model for the user information.
 */
package com.example.groupproject_g3.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModel extends ViewModel {

    /**My email instance field.*/
    private final String mEmail;

    /**My JWT instance field*/
    private final String mJwt;

    /** The id of the current user. */
    private final int mUserId;

    /**
     * Constuctor.
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt, int userId) {
        mEmail = email;
        mJwt = jwt;
        mUserId = userId;
    }

    /**
     * Getter for email.
     * @return mEmail.
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Getter for jwt.
     * @return mJwt
     */
    public String getJwt() {
        return mJwt;
    }

    /**
     * Getter for the user id.
     * @return the user's id.
     */
    public int getUserId() { return mUserId; }

    /**
     * Inner Class for factory implementation.
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        /**My email instance field.*/
        private final String email;

        /**jwt instance field*/
        private final String jwt;

        /** The id of the user to generate a user info view model for. */
        private final int userId;

        /**
         * Constructor.
         *
         * @param email email.
         * @param jwt jwt.
         */
        public UserInfoViewModelFactory(String email, String jwt, int userId) {
            this.email = email;
            this.jwt = jwt;
            this.userId = userId;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, userId);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}
