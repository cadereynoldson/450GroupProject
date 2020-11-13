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

    /**
     * Constuctor.
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
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
     * Inner Class for factory implementation.
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        /**My email instance field.*/
        private final String email;

        /**jwt instance field*/
        private final String jwt;

        /**
         * Constructor.
         *
         * @param email email.
         * @param jwt jwt.
         */
        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}
