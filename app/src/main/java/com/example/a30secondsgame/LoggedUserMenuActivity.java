package com.example.a30secondsgame;



import static com.example.a30secondsgame.Models.Models.AnswerTaskFillInTheBlanks.parseAnswerTaskFillInTheBlanksFromJson;
import static com.example.a30secondsgame.Models.Models.AnswerTaskMatchSynonyms.parseAnswerTaskMatchSynonymsFromJson;
import static com.example.a30secondsgame.Models.Models.AnswerTaskMultipleChoice.parseAnswerTaskMultipleChoiceFromJson;
import static com.example.a30secondsgame.Models.Models.AnswerTaskTranslateSentences.parseAnswerTaskTranslateSentencesFromJson;
import static com.example.a30secondsgame.Models.Models.Language.parseLanguageFromJson;
import static com.example.a30secondsgame.Models.Models.QuestionTaskFillInTheBlanks.parseQuestionTaskFillInTheBlanksFromJson;
import static com.example.a30secondsgame.Models.Models.QuestionTaskMultipleChoice.parseQuestionTaskMultipleChoiceFromJson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.a30secondsgame.FragmentsLoggedUser.FragmentHomePage;
import com.example.a30secondsgame.Models.Models.*;

import org.json.JSONArray;
import org.json.JSONException;


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
    DbHelper dbHelper;
    private int asyncOperationsCompleted = 0;

    Button playBtn;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_menu);

        Intent intent =getIntent();
        user = (User) intent.getSerializableExtra("user");
        addFragment();

        obtainLanguages();
        obtainTasksTranslateSentences();
        obtainTasksFillInTheBlanks();
        obtainTasksMatchSynonyms();
        obtainQuestionsTaskMultipleChoice();



    }
    private void onAsyncOperationCompleted() {
        asyncOperationsCompleted++;
        if (asyncOperationsCompleted == 5) {
            Toast.makeText(this, "udalo pobrac sie wszystkie dane", Toast.LENGTH_SHORT).show();
            dbHelper = new DbHelper(this);
            //todo odkomentować ogarnać jak zrobić synchronizację co x dni
         //   loadDataToLocalDb();

        }
    }
    public void addFragment()
    {
         fragment = new FragmentHomePage();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userObj", user);
        fragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
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
                startActivity(intent);
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

    }


}