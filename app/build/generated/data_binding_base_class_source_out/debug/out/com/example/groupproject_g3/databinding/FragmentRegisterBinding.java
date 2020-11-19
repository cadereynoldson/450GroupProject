// Generated by view binder compiler. Do not edit!
package com.example.groupproject_g3.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.groupproject_g3.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonRegister;

  @NonNull
  public final ConstraintLayout frameLayout2;

  @NonNull
  public final ImageView logoRegisterCloudChat;

  @NonNull
  public final EditText textRegisterEmail;

  @NonNull
  public final EditText textRegisterFirstName;

  @NonNull
  public final EditText textRegisterLastName;

  @NonNull
  public final EditText textRegisterPassword;

  @NonNull
  public final EditText textRegisterPassword2;

  @NonNull
  public final EditText textRegisterUsername;

  private FragmentRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonRegister, @NonNull ConstraintLayout frameLayout2,
      @NonNull ImageView logoRegisterCloudChat, @NonNull EditText textRegisterEmail,
      @NonNull EditText textRegisterFirstName, @NonNull EditText textRegisterLastName,
      @NonNull EditText textRegisterPassword, @NonNull EditText textRegisterPassword2,
      @NonNull EditText textRegisterUsername) {
    this.rootView = rootView;
    this.buttonRegister = buttonRegister;
    this.frameLayout2 = frameLayout2;
    this.logoRegisterCloudChat = logoRegisterCloudChat;
    this.textRegisterEmail = textRegisterEmail;
    this.textRegisterFirstName = textRegisterFirstName;
    this.textRegisterLastName = textRegisterLastName;
    this.textRegisterPassword = textRegisterPassword;
    this.textRegisterPassword2 = textRegisterPassword2;
    this.textRegisterUsername = textRegisterUsername;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_register;
      Button buttonRegister = rootView.findViewById(id);
      if (buttonRegister == null) {
        break missingId;
      }

      ConstraintLayout frameLayout2 = (ConstraintLayout) rootView;

      id = R.id.logo_register_cloudChat;
      ImageView logoRegisterCloudChat = rootView.findViewById(id);
      if (logoRegisterCloudChat == null) {
        break missingId;
      }

      id = R.id.text_register_email;
      EditText textRegisterEmail = rootView.findViewById(id);
      if (textRegisterEmail == null) {
        break missingId;
      }

      id = R.id.text_register_first_name;
      EditText textRegisterFirstName = rootView.findViewById(id);
      if (textRegisterFirstName == null) {
        break missingId;
      }

      id = R.id.text_register_last_name;
      EditText textRegisterLastName = rootView.findViewById(id);
      if (textRegisterLastName == null) {
        break missingId;
      }

      id = R.id.text_register_password;
      EditText textRegisterPassword = rootView.findViewById(id);
      if (textRegisterPassword == null) {
        break missingId;
      }

      id = R.id.text_register_password2;
      EditText textRegisterPassword2 = rootView.findViewById(id);
      if (textRegisterPassword2 == null) {
        break missingId;
      }

      id = R.id.text_register_username;
      EditText textRegisterUsername = rootView.findViewById(id);
      if (textRegisterUsername == null) {
        break missingId;
      }

      return new FragmentRegisterBinding((ConstraintLayout) rootView, buttonRegister, frameLayout2,
          logoRegisterCloudChat, textRegisterEmail, textRegisterFirstName, textRegisterLastName,
          textRegisterPassword, textRegisterPassword2, textRegisterUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}