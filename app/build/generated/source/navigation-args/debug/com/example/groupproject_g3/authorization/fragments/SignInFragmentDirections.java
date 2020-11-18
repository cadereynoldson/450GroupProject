package com.example.groupproject_g3.authorization.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.groupproject_g3.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SignInFragmentDirections {
  private SignInFragmentDirections() {
  }

  @NonNull
  public static ActionSignInFragmentToMainActivity actionSignInFragmentToMainActivity(
      @NonNull String email, @NonNull String jwt, int userId) {
    return new ActionSignInFragmentToMainActivity(email, jwt, userId);
  }

  @NonNull
  public static NavDirections actionSignInFragmentToRegisterFragment() {
    return new ActionOnlyNavDirections(R.id.action_signInFragment_to_registerFragment);
  }

  public static class ActionSignInFragmentToMainActivity implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionSignInFragmentToMainActivity(@NonNull String email, @NonNull String jwt,
        int userId) {
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("email", email);
      if (jwt == null) {
        throw new IllegalArgumentException("Argument \"jwt\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("jwt", jwt);
      this.arguments.put("userId", userId);
    }

    @NonNull
    public ActionSignInFragmentToMainActivity setEmail(@NonNull String email) {
      if (email == null) {
        throw new IllegalArgumentException("Argument \"email\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("email", email);
      return this;
    }

    @NonNull
    public ActionSignInFragmentToMainActivity setJwt(@NonNull String jwt) {
      if (jwt == null) {
        throw new IllegalArgumentException("Argument \"jwt\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("jwt", jwt);
      return this;
    }

    @NonNull
    public ActionSignInFragmentToMainActivity setUserId(int userId) {
      this.arguments.put("userId", userId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("email")) {
        String email = (String) arguments.get("email");
        __result.putString("email", email);
      }
      if (arguments.containsKey("jwt")) {
        String jwt = (String) arguments.get("jwt");
        __result.putString("jwt", jwt);
      }
      if (arguments.containsKey("userId")) {
        int userId = (int) arguments.get("userId");
        __result.putInt("userId", userId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_signInFragment_to_mainActivity;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEmail() {
      return (String) arguments.get("email");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getJwt() {
      return (String) arguments.get("jwt");
    }

    @SuppressWarnings("unchecked")
    public int getUserId() {
      return (int) arguments.get("userId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionSignInFragmentToMainActivity that = (ActionSignInFragmentToMainActivity) object;
      if (arguments.containsKey("email") != that.arguments.containsKey("email")) {
        return false;
      }
      if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
        return false;
      }
      if (arguments.containsKey("jwt") != that.arguments.containsKey("jwt")) {
        return false;
      }
      if (getJwt() != null ? !getJwt().equals(that.getJwt()) : that.getJwt() != null) {
        return false;
      }
      if (arguments.containsKey("userId") != that.arguments.containsKey("userId")) {
        return false;
      }
      if (getUserId() != that.getUserId()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
      result = 31 * result + (getJwt() != null ? getJwt().hashCode() : 0);
      result = 31 * result + getUserId();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionSignInFragmentToMainActivity(actionId=" + getActionId() + "){"
          + "email=" + getEmail()
          + ", jwt=" + getJwt()
          + ", userId=" + getUserId()
          + "}";
    }
  }
}
