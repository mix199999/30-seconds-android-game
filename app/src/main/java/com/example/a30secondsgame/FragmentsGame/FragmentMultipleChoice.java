package com.example.a30secondsgame.FragmentsGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a30secondsgame.DbHelper;
import com.example.a30secondsgame.GameActivity;
import com.example.a30secondsgame.Models.Models;
import com.example.a30secondsgame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;


public class FragmentMultipleChoice extends Fragment {

    CheckBox firstAnswerCheck, secondAnswerCheck, thirdAnswerCheck;
    Button checkBt;
    TextView questionTextView;
    String firstLanguageId;
    String secondLanguageId;


    DbHelper dbHelper;
    private int currentTaskId;
    private Models.QuestionTaskMultipleChoice currentTaskQuestion ;
    private List<Models.AnswerTaskMultipleChoice> currentTaskAnswers = new ArrayList<>();



    List<Models.QuestionTaskMultipleChoice> tasksQuestion= new ArrayList<>();
    List<Models.AnswerTaskMultipleChoice> tasksAnswers = new ArrayList<>();
    public FragmentMultipleChoice() {

    }
    void addAnswersByTaskId(List<Models.AnswerTaskMultipleChoice> tasksAnswers, int currentTaskId) {
        for (Models.AnswerTaskMultipleChoice answer : tasksAnswers) {
            if (answer.getTaskId() == currentTaskId && answer.getLanguageId() == 1) {
                currentTaskAnswers.add(answer);
            }
        }
    }

    public static FragmentMultipleChoice newInstance(String firstLanguageId, String secondLanguageId) {
        FragmentMultipleChoice fragment = new FragmentMultipleChoice();
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", firstLanguageId);
        bundle.putString("secondLanguageId", secondLanguageId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        if (bundle != null) {

            firstLanguageId = bundle.getString("firstLanguageId");

        }
        else
        {
           // return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        dbHelper = ((GameActivity) requireActivity()).getDbHelper();
        tasksQuestion = dbHelper. saveQuestionsTasksMultipleChoiceToObjects("1");
        tasksAnswers = dbHelper.saveAnswersTasksMultipleChoiceToObjectArray("1");

        checkBt = view.findViewById(R.id.checkBt);
        firstAnswerCheck = view.findViewById(R.id.firstAnswerCheck);
        secondAnswerCheck = view.findViewById(R.id.secondAnswerCheck);
        thirdAnswerCheck = view.findViewById(R.id.thirdAnswerCheck);
        questionTextView = view.findViewById(R.id.questionTextView);
        loadQuestionAndAnswers();
        return view;
    }


    private void loadQuestionAndAnswers()
    {

        currentTaskId = getRandomTaskId();
       currentTaskQuestion = getQuestionsByTaskId(currentTaskId);
        addAnswersByTaskId(tasksAnswers, currentTaskId);
        questionTextView.setText(currentTaskQuestion.getQuestionText());
        firstAnswerCheck.setText(currentTaskAnswers.get(0).getAnswer_text());
       secondAnswerCheck.setText(currentTaskAnswers.get(1).getAnswer_text());
        thirdAnswerCheck.setText(currentTaskAnswers.get(2).getAnswer_text());

    }



    public int randomNumber(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max);
        return randomNum;
    }


    int getRandomTaskId() {
        // Get all task_ids
        List<Integer> taskIds = new ArrayList<>();
        for (Models.QuestionTaskMultipleChoice question : tasksQuestion) {
            taskIds.add(question.getTaskId());
        }

        // Remove duplicates
        Collections.sort(taskIds);
        taskIds = new ArrayList<>(new LinkedHashSet<>(taskIds));

        // Choose a random index
        int randomIdx = randomNumber(taskIds.size() - 1);

        // Return the task_id at that index
        return taskIds.get(randomIdx);
    }

    Models.QuestionTaskMultipleChoice getQuestionsByTaskId(int taskId) {
       Models.QuestionTaskMultipleChoice currentQuestion = null;
        for (Models.QuestionTaskMultipleChoice question : tasksQuestion) {
            if (question.getTaskId() == taskId && question.getLanguageId() == 1) {
                currentQuestion = question;
            }

        }
        return currentQuestion;
    }
}
