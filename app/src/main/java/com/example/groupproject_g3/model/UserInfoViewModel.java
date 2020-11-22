/**
 * View Model for the user information.
 */
package com.example.groupproject_g3.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.auth0.android.jwt.JWT;


public class UserInfoViewModel extends ViewModel {

    /**My email instance field.*/
    private final String mEmail;

    /**My JWT instance field*/
    private final String mJwt;

    /** The id of the current user. */
    private final int mUserId;

    /**
     * Constuctor.
     * @param jwt jwt.
     */
    private UserInfoViewModel(String jwt) {
        JWT token = new JWT(jwt);
        mEmail = token.getClaim("email").toString();
        mJwt = jwt;
        mUserId = token.getClaim("memberid").asInt();
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
        private final String Jwt;

        /** The id of the user to generate a user info view model for. */
        private final int userId;

        /**
         * Constructor.
         *
         * @param jwt jwt.
         */
        public UserInfoViewModelFactory(String jwt) {
            JWT token = new JWT(jwt);
            this.email = token.getClaim("email").asString();
            this.Jwt = jwt;
            this.userId = token.getClaim("memberid").asInt();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(Jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}