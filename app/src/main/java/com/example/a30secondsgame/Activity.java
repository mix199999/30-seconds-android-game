package com.example.a30secondsgame;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class Activity extends AppCompatActivity implements CallbackFragment, FragmentLogin.OnLoginClickListener, FragmentRegister.OnRegisterClickListener {
    DbHelper dbHelper;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper();

        addFragment();

       /* if(dbHelper.addUser("test","test"))
        {
            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "dupa", Toast.LENGTH_SHORT).show();

        }*/

    }


    public void addFragment()
    {
        FragmentLogin fragment = new FragmentLogin();

        fragment.setCallbackFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
        dbHelper = new DbHelper();

    }


    public void replaceFragment()
    {

        fragment = new FragmentRegister();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();

    }
    @Override
    public void changeFragment()
    {
        replaceFragment();
    }


    @Override
    public void onLoginClick(User user) {


        dbHelper.checkUser(user, userExists -> {
            if (userExists) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity.this, LoggedUserMenuActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Fault", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onRegisterClick(User user) {

        dbHelper.addUser(user, success -> {
            if (success) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity.this, LoggedUserMenuActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Cannot register", Toast.LENGTH_SHORT).show();
            }
        });
    }




}