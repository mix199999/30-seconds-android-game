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

import com.example.a30secondsgame.DbHelper;
import com.example.a30secondsgame.R;
import com.example.a30secondsgame.User;

public class FragmentLogin extends Fragment {


    public interface OnLoginClickListener {
        void onLoginClick(User user);
    }


    Button loginBtn, registerBtn;
    EditText usernameText, passwordText;
    CallbackFragment callbackFragment;
    User user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DbHelper dbHelper;
    @Override
    public void onAttach(@NonNull Context context) {
         sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,container,false);
        usernameText = view.findViewById(R.id.usernameText);
        passwordText = view.findViewById(R.id.passwordText);
        Button loginBtn = view.findViewById(R.id.loginBtn);
        Button registerBtn = view.findViewById(R.id.registerBtn);
        user = new User();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPassword(passwordText.getText().toString());
                user.setUsername(usernameText.getText().toString());
                editor.putString("username", user.getUsername());
                editor.putString("password", user.getPassword());
                editor.apply();

                // Wywołanie do przejecia eventu w mainie
                if (getActivity() instanceof OnLoginClickListener) {
                    ((OnLoginClickListener) getActivity()).onLoginClick(user);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callbackFragment!=null)
                {
                    callbackFragment.changeFragment();
                }

            }
        });

        return view;
    }

    public void setCallbackFragment(CallbackFragment callbackFragment)
    {
        this.callbackFragment=callbackFragment;
    }


}
