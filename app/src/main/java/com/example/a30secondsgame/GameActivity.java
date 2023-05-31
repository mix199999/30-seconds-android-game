package com.example.a30secondsgame;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.a30secondsgame.FragmentsGame.FragmentFillBlanks;
import com.example.a30secondsgame.FragmentsGame.FragmentMatchSynonyms;
import com.example.a30secondsgame.FragmentsStartPage.FragmentLogin;
import com.example.a30secondsgame.FragmentsStartPage.FragmentRegister;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a30secondsgame.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

   private String firstLanguageId;
   private String secondLanguageId;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        dbHelper = new DbHelper(this);
        this.addFragment("1", "2");

    }




    public void addFragment(String firstLanguageId, String secondLanguageId)
    {
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", "1");
        bundle.putString("secondLanguageId", "2");



         fragment = new FragmentFillBlanks();
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();


    }


    public DbHelper getDbHelper() {
        return dbHelper;
    }

}