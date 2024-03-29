package com.example.a30secondsgame.FragmentsLoggedUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a30secondsgame.R;
import com.example.a30secondsgame.User;

public class FragmentHomePage extends Fragment {


    public interface OnPlayClickListener {
        void onPlayClick();
    }

    Button playBtn;
    TextView usernameText;
    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            user = (User) getArguments().getSerializable("userObj");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        playBtn = view.findViewById(R.id.playBtn);
        usernameText = view.findViewById(R.id.usernameText);
        usernameText.setText("Hi, "+user.getUsername()+"!");


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof FragmentHomePage.OnPlayClickListener) {
                    ((FragmentHomePage.OnPlayClickListener) getActivity()).onPlayClick();
                }

            }
        });


        return view;
    }



}