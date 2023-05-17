package com.example.a30secondsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoggedUserMenuActivity extends AppCompatActivity {
TextView textView;
User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_menu);
        textView = findViewById(R.id.textView);
        Intent intent =getIntent();
        user = (User) intent.getSerializableExtra("user");
        textView.setText("Hi, " +user.getUsername()+"!");




    }
}