package com.example.a30secondsgame;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.a30secondsgame.FragmentsGame.FragmentFillBlanks;
import com.example.a30secondsgame.FragmentsGame.FragmentFinalScore;
import com.example.a30secondsgame.FragmentsGame.FragmentMatchSynonyms;
import com.example.a30secondsgame.FragmentsGame.FragmentMultipleChoice;
import com.example.a30secondsgame.FragmentsGame.FragmentTranslateSentences;
import com.example.a30secondsgame.FragmentsStartPage.FragmentLogin;
import com.example.a30secondsgame.FragmentsStartPage.FragmentRegister;
import com.example.a30secondsgame.Models.Models;
import com.example.a30secondsgame.Models.OnButtonBackClickListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
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
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements OnButtonClickListener, OnButtonBackClickListener {

   private String firstLanguageId;
   private String secondLanguageId;
   private int score = 0;
    User user = null;
   public int getScore()
   {
       return this.score;
   }
   private int randomIndex = 0;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    DbHelper dbHelper;
    List<Models.AnswerTaskTranslateSentences> test = new ArrayList<>();

    TextView timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent =getIntent();
        user = (User) intent.getSerializableExtra("user");

        firstLanguageId = intent.getStringExtra("primaryLanguage");
        secondLanguageId = intent.getStringExtra("secondaryLanguage");
        timerText = findViewById(R.id.timerTextView);
        dbHelper = new DbHelper(this);
        loadRandomFragment();

    }



    private CountDownTimer timer;
    private long remainingTime;
    public void addFragment(String firstLanguageId, String secondLanguageId)
    {
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", firstLanguageId);
        bundle.putString("secondLanguageId", secondLanguageId);
         fragment = new FragmentMatchSynonyms();
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();


    }


    public DbHelper getDbHelper() {
        return dbHelper;
    }






    private void randomFragment()
    {
        int tempIndex;
        do {
            tempIndex = new Random().nextInt(4);
        } while (randomIndex == tempIndex);
        randomIndex = tempIndex;




        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", firstLanguageId);
        bundle.putString("secondLanguageId", secondLanguageId);
        switch (randomIndex) {
            case 0:

                fragment = new FragmentFillBlanks();
                fragment.setArguments(bundle);
                break;
            case 1:

                fragment = new FragmentMatchSynonyms();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new FragmentMultipleChoice();
                fragment.setArguments(bundle);
                break;
            default:
                fragment = new FragmentTranslateSentences();
                fragment.setArguments(bundle);
                break;
        }

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
        startCountdownTimer();

    }

    private void stopTimer() {
        timer.cancel();




    }

    private void loadFinalFragment()
    {
        Fragment fragment;
        Bundle bundle = new Bundle();
        bundle.putInt("finalScore", score);
        fragment = new FragmentFinalScore();
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onButtonClick(double value) {

        if (value > 0) {
            remainingTime += 5 * value * 1000;
            score += 100 * value;
        } else if (value == 0) {
            remainingTime -= 5 * 1000;
            score -= 100 ;
        }

        if (timer != null) {
            timer.cancel();
        }

        randomFragment();
    }

    @Override
    public void onButtonBackClick() {
        // Zamykamy aktualną aktywność
        finish();

        // Tworzymy nowe intent do uruchomienia LoggedUserMenuActivity
        Intent intent = new Intent(this, LoggedUserMenuActivity.class);
        intent.putExtra("user", user);

        // Uruchamiamy LoggedUserMenuActivity poprzez intent
        startActivity(intent);
    }

    private void loadRandomFragment() {
        remainingTime = 30000; // 30 sekund

        randomFragment();
    }


    private void startCountdownTimer() {
        timer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                remainingTime = 0;
                updateTimerText();
                timerText.setText("Koniec odliczania!");
                loadFinalFragment();
            }
        };

        timer.start();
    }

    private void updateTimerText() {
        long seconds = remainingTime / 1000;
        timerText.setText("Pozostało: " + seconds + " sekund");
    }





}
