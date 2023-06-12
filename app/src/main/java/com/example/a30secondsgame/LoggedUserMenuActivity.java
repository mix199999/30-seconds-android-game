package com.example.a30secondsgame;



import static com.example.a30secondsgame.Models.Models.AnswerTaskFillInTheBlanks.parseAnswerTaskFillInTheBlanksFromJson;
import static com.example.a30secondsgame.Models.Models.AnswerTaskMatchSynonyms.parseAnswerTaskMatchSynonymsFromJson;
import static com.example.a30secondsgame.Models.Models.AnswerTaskMultipleChoice.parseAnswerTaskMultipleChoiceFromJson;
import static com.example.a30secondsgame.Models.Models.AnswerTaskTranslateSentences.parseAnswerTaskTranslateSentencesFromJson;
import static com.example.a30secondsgame.Models.Models.Language.parseLanguageFromJson;
import static com.example.a30secondsgame.Models.Models.QuestionTaskFillInTheBlanks.parseQuestionTaskFillInTheBlanksFromJson;
import static com.example.a30secondsgame.Models.Models.QuestionTaskMultipleChoice.parseQuestionTaskMultipleChoiceFromJson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.example.a30secondsgame.FragmentsLoggedUser.FragmentHomePage;
import com.example.a30secondsgame.FragmentsLoggedUser.FragmentSettings;
import com.example.a30secondsgame.Models.Models.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoggedUserMenuActivity extends AppCompatActivity implements  FragmentHomePage.OnPlayClickListener{
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    List<Language> languagesList=new ArrayList<>();
    List<AnswerTaskTranslateSentences> answerTaskTranslateSentencesList =new ArrayList<>();
    List<AnswerTaskMultipleChoice> answerTaskMultipleChoiceList=new ArrayList<>();
    List<QuestionTaskMultipleChoice>questionTaskMultipleChoiceList=new ArrayList<>();
    List<AnswerTaskMatchSynonyms>answerTaskMatchSynonymsList=new ArrayList<>();
    List<AnswerTaskFillInTheBlanks>answerTaskFillInTheBlanksList=new ArrayList<>();
    List<QuestionTaskFillInTheBlanks>questionTaskFillInTheBlanksList=new ArrayList<>();
    BottomNavigationView bottomNavigationView ;

    String primaryLanguage;
    String secondaryLanguage;

    User user = null;
    DbHelper dbHelper;
    private int asyncOperationsCompleted = 0;

    Button playBtn;

    private ConfigManager configManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_menu);

        Intent intent =getIntent();
        user = (User) intent.getSerializableExtra("user");
        loadHomeFragment();

        obtainLanguages();
        obtainTasksTranslateSentences();
        obtainTasksFillInTheBlanks();
        obtainTasksMatchSynonyms();
        obtainQuestionsTaskMultipleChoice();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    loadHomeFragment();
                    return true;
                } else if (item.getItemId() == R.id.settings) {
                    loadSettingsFragment();
                    return true;
                } else if (item.getItemId()== R.id.leaderboard) {

                    loadLeaderboardFragment();

                }

                return false;
            }
        });

        configManager = new ConfigManager(getApplicationContext());

       primaryLanguage = configManager.getPrimaryLanguageFromConfig();
       secondaryLanguage = configManager.getSecondaryLanguageFromConfig();


    }

    private void loadLeaderboardFragment() {
    }

    private void onAsyncOperationCompleted() {
        asyncOperationsCompleted++;
        if (asyncOperationsCompleted == 5) {
            Toast.makeText(this, "udalo pobrac sie wszystkie dane", Toast.LENGTH_SHORT).show();
            dbHelper = new DbHelper(this);


            if(configManager.getFirstRunFromConfig().equals("true") ||isDateDifferenceThreeDaysOrMore(configManager.getLastOpenedFromConfig()))
            {
                loadDataToLocalDb();
                configManager.setFirstRunInConfig("false");
            }

        }
    }

    public boolean isDateDifferenceThreeDaysOrMore(String lastDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date currentDate = new Date();
            Date parsedLastDate = dateFormat.parse(lastDate);

            // Oblicz różnicę między obecną datą a przekazaną datą
            long differenceInMillis = currentDate.getTime() - parsedLastDate.getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);

            return differenceInDays >= 3;
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Jeśli wystąpił błąd podczas parsowania daty, zwróć false
        }
    }

    public void loadHomeFragment()
    {
        fragment = new FragmentHomePage();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userObj", user);
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
        configManager = new ConfigManager(getApplicationContext());
        primaryLanguage = configManager.getPrimaryLanguageFromConfig();
        secondaryLanguage = configManager.getSecondaryLanguageFromConfig();
    }

    public void loadSettingsFragment()
    {
        fragment = new FragmentSettings();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userObj", user);
        bundle.putString("firstLanguageId", primaryLanguage);
        bundle.putString("secondLanguageId", secondaryLanguage);

        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onPlayClick()
    {
        showCountdownAnimation();
        //Intent intent = new Intent(LoggedUserMenuActivity.this, ActivityPlay.class);
        //startActivity(intent);
    }


    private void showCountdownAnimation() {
        final Dialog dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.countdown_animation_layout);

        ImageView countdownGif = dialog.findViewById(R.id.countdown_gif);
        Glide.with(this)
                .asGif()
                .load(R.drawable.countdown_timer)
                .into(countdownGif);

        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Intent intent = new Intent(LoggedUserMenuActivity.this, GameActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("primaryLanguage", primaryLanguage);
                intent.putExtra("secondaryLanguage", secondaryLanguage);

                startActivity(intent);
                finish();
            }
        }, 1500); // Czas trwania animacji (w milisekundach)
    }







    private boolean obtainLanguages()
    {
        String apiUrl = "/languages.php";
        Map<String,String> postData = new HashMap<>();
        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {

            @Override
            public void onSuccess(String response) {

                languagesList =  parseLanguageFromJson(response);
                onAsyncOperationCompleted();
            }

            @Override
            public void onError(String error) {
                Log.d("Error", "obtainLanguages: " + error);

            }
        });
        apiService.execute();
            return true;


    }
    private boolean obtainTasksTranslateSentences()
    {
        String apiUrl ="/tasks/tasks_translate_sentences.php";
        Map<String,String> postData = new HashMap<>();
        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {

                answerTaskTranslateSentencesList =  parseAnswerTaskTranslateSentencesFromJson(response);
                onAsyncOperationCompleted();
            }

            @Override
            public void onError(String error) {
                Log.d("Error", "obtainLanguages: " + error);

            }
        });
        apiService.execute();
        if(answerTaskTranslateSentencesList != null)
            return true;
        else
            return false;


    }

    private boolean obtainTasksFillInTheBlanks()
    {
        String apiUrl ="/tasks/tasks_fill_in_the_blanks.php";
        Map<String,String> postData = new HashMap<>();
        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {

                JSONArray jsonResponse = null;
                try {
                    jsonResponse = new JSONArray(response);
                    JSONArray questionsJsonArray = jsonResponse.getJSONArray(0);
                    JSONArray answersJsonArray = jsonResponse.getJSONArray(1);

                    questionTaskFillInTheBlanksList = parseQuestionTaskFillInTheBlanksFromJson(questionsJsonArray.toString());
                    answerTaskFillInTheBlanksList = parseAnswerTaskFillInTheBlanksFromJson(answersJsonArray.toString());
                    onAsyncOperationCompleted();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onError(String error) {
                Log.d("Error", "obtainLanguages: " + error);

            }
        });
        apiService.execute();
        if(questionTaskFillInTheBlanksList!= null && answerTaskFillInTheBlanksList !=null)
            return true;
        else return false;
    }


    private boolean obtainTasksMatchSynonyms()
    {
        String apiUrl ="/tasks/tasks_match_synonyms.php";
        Map<String,String> postData = new HashMap<>();


        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {

                answerTaskMatchSynonymsList =  parseAnswerTaskMatchSynonymsFromJson(response);

                onAsyncOperationCompleted();
            }

            @Override
            public void onError(String error) {
                Log.d("Error", "obtainLanguages: " + error);

            }
        });
        apiService.execute();
        if(answerTaskMatchSynonymsList != null)
            return true;
        else
            return false;
    }
    private boolean obtainQuestionsTaskMultipleChoice()
    {
        String apiUrl ="/tasks/tasks_multiple_choice.php";
        Map<String,String> postData = new HashMap<>();
        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {

                JSONArray jsonResponse = null;
                try {
                    jsonResponse = new JSONArray(response);
                    JSONArray questionsJsonArray = jsonResponse.getJSONArray(0);
                    JSONArray answersJsonArray = jsonResponse.getJSONArray(1);

                    questionTaskMultipleChoiceList = parseQuestionTaskMultipleChoiceFromJson(questionsJsonArray.toString());
                    answerTaskMultipleChoiceList =parseAnswerTaskMultipleChoiceFromJson(answersJsonArray.toString());
                    onAsyncOperationCompleted();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onError(String error) {
                Log.d("Error", "obtainLanguages: " + error);

            }
        });
        apiService.execute();
        if(questionTaskMultipleChoiceList!= null && answerTaskMultipleChoiceList !=null)
            return true;
        else return false;
    }

    private void loadDataToLocalDb()
    {
        for(Language language : languagesList)
        {
            dbHelper.addLanguage(language);
        }

        for(QuestionTaskFillInTheBlanks taskFillInTheBlanks : questionTaskFillInTheBlanksList)
        {
            dbHelper.addQuestionTaskFillInTheBlanks(taskFillInTheBlanks);
        }

        for(AnswerTaskFillInTheBlanks answerTaskFillInTheBlanks : answerTaskFillInTheBlanksList)
        {
            dbHelper.addAnswerTaskFillInTheBlanks(answerTaskFillInTheBlanks);
        }

        for(AnswerTaskMatchSynonyms answerTaskMatchSynonyms : answerTaskMatchSynonymsList)
        {
            dbHelper.addAnswerTaskMatchSynonyms(answerTaskMatchSynonyms);
        }
        for(QuestionTaskMultipleChoice questionTaskMultipleChoice : questionTaskMultipleChoiceList)
        {
            dbHelper.addQuestionTaskMultipleChoice(questionTaskMultipleChoice);
        }
        for(AnswerTaskMultipleChoice answerTaskMultipleChoice: answerTaskMultipleChoiceList)
        {
            dbHelper.addAnswerTaskMultipleChoice(answerTaskMultipleChoice);
        }
        for(AnswerTaskTranslateSentences answerTaskTranslateSentences : answerTaskTranslateSentencesList)
        {
            dbHelper.addAnswerTaskTranslateSentences(answerTaskTranslateSentences);
        }



    }









}