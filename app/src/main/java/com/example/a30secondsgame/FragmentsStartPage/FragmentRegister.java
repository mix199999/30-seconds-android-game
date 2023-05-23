package com.example.a30secondsgame.FragmentsStartPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a30secondsgame.R;
import com.example.a30secondsgame.User;

public class FragmentRegister extends Fragment {


    public interface OnRegisterClickListener {
        void onRegisterClick(User user);
    }
    User user;
    Button  registerBtn;
    EditText usernameText, passwordText, emailText;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    public void onAttach(@NonNull Context context) {
      sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
      editor = sharedPreferences.edit();

        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment,container,false);
        usernameText = view.findViewById(R.id.usernameText);
        passwordText = view.findViewById(R.id.passwordText);
        emailText = view.findViewById(R.id.emailText);
       registerBtn = view.findViewById(R.id.registerBtn);




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                user.setPassword(passwordText.getText().toString());
                user.setUsername(usernameText.getText().toString());
                user.setEmail(emailText.getText().toString());
                editor.putString("username", user.getUsername());
                editor.putString("password", user.getPassword());
                editor.putString("email", user.getEmail());
                editor.apply();

                if (getActivity() instanceof OnRegisterClickListener) {
                    ((OnRegisterClickListener) getActivity()).onRegisterClick(user);
                }

            }
        });
        return view;
    }
}
