package com.example.a30secondsgame.FragmentsGame;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a30secondsgame.DbHelper;
import com.example.a30secondsgame.GameActivity;
import com.example.a30secondsgame.Models.Models;
import com.example.a30secondsgame.OnButtonClickListener;
import com.example.a30secondsgame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;


public class FragmentFillBlanks extends Fragment {

    TextView questionText;
    Button firstAnswerBt, secondAnswerBt, thirdAnswerBt;
    Cursor[] cursor;
    String firstLanguageId, secondLanguageId;
    DbHelper dbHelper;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnButtonClickListener");
        }
    }
    private Models.QuestionTaskFillInTheBlanks currentTaskQuestion ;
    private List<Models.AnswerTaskFillInTheBlanks> currentTaskAnswers = new ArrayList<>();
    List<Models.QuestionTaskFillInTheBlanks> tasksQuestion= new ArrayList<>();
    List<Models.AnswerTaskFillInTheBlanks> tasksAnswers = new ArrayList<>();
    int currentTaskId;
    private OnButtonClickListener listener;

    public FragmentFillBlanks() {

    }


    public static FragmentFillBlanks newInstance(String firstLanguageId, String secondLanguageId) {
        FragmentFillBlanks fragment = new FragmentFillBlanks();
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", firstLanguageId);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_blanks, container, false);
        dbHelper = ((GameActivity) requireActivity()).getDbHelper();

        Bundle bundle = getArguments();
        if (bundle != null) {
             firstLanguageId = bundle.getString("firstLanguageId");
            dbHelper = ((GameActivity) requireActivity()).getDbHelper();
            tasksQuestion = dbHelper.saveQuestionsTasksFillInTheBlanksToObjectArray(firstLanguageId);
            tasksAnswers = dbHelper.saveAnswersTasksFillInTheBlanksToObjectArray(firstLanguageId);
            questionText = view.findViewById(R.id.questionTextView);
            firstAnswerBt = view.findViewById(R.id.firstAnswerBt);
            secondAnswerBt = view.findViewById(R.id.secondAnswerBt);
            thirdAnswerBt = view.findViewById(R.id.thirdAnswerBt);

            loadQuestionAndAnswers();
        }

        return view;
    }

    private void loadQuestionAndAnswers()
    {
        currentTaskId = getRandomTaskId();
        currentTaskQuestion = getQuestionsByTaskId(currentTaskId);
        addAnswersByTaskId(tasksAnswers, currentTaskId);
        questionText.setText(currentTaskQuestion.getQuestionText());
        firstAnswerBt.setText(currentTaskAnswers.get(0).getAnswer_text());
        firstAnswerBt.setTag(currentTaskAnswers.get(0));
        secondAnswerBt.setText(currentTaskAnswers.get(1).getAnswer_text());
        secondAnswerBt.setTag(currentTaskAnswers.get(1));
        thirdAnswerBt.setText(currentTaskAnswers.get(2).getAnswer_text());
        thirdAnswerBt.setTag(currentTaskAnswers.get(2));

        firstAnswerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Models.AnswerTaskFillInTheBlanks answer = (Models.AnswerTaskFillInTheBlanks) v.getTag();
                checkIsCorrect(answer);
            }
        });


        secondAnswerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Models.AnswerTaskFillInTheBlanks answer = (Models.AnswerTaskFillInTheBlanks) v.getTag();
                checkIsCorrect(answer);
            }
        });

        thirdAnswerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Models.AnswerTaskFillInTheBlanks answer = (Models.AnswerTaskFillInTheBlanks) v.getTag();
                checkIsCorrect(answer);
            }
        });

    }

    public int randomNumber(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max);
        return randomNum;
    }


    int getRandomTaskId() {
        // Get all task_ids
        List<Integer> taskIds = new ArrayList<>();
        for (Models.QuestionTaskFillInTheBlanks question : tasksQuestion) {
            taskIds.add(question.getTaskId());
        }
        Collections.sort(taskIds);
        taskIds = new ArrayList<>(new LinkedHashSet<>(taskIds));


        int randomIdx = randomNumber(taskIds.size() - 1);


        return taskIds.get(randomIdx);
    }


    void addAnswersByTaskId(List<Models.AnswerTaskFillInTheBlanks> tasksAnswers, int currentTaskId) {
        for (Models.AnswerTaskFillInTheBlanks answer : tasksAnswers) {
            if (answer.getTaskId() == currentTaskId ) {
                currentTaskAnswers.add(answer);
            }
        }
    }


    Models.QuestionTaskFillInTheBlanks getQuestionsByTaskId(int taskId) {
        Models.QuestionTaskFillInTheBlanks currentQuestion = null;
        for (Models.QuestionTaskFillInTheBlanks question : tasksQuestion) {
            if (question.getTaskId() == taskId && question.getLanguageId() == Integer.parseInt(firstLanguageId)) {
                currentQuestion = question;
            }

        }
        return currentQuestion;
    }

    void checkIsCorrect(Models.AnswerTaskFillInTheBlanks answer)
    {

        if(answer.isCorrect() == 1)
        {
            listener.onButtonClick(1);

        } else if (answer.isCorrect() == 0)
        {
            listener.onButtonClick(0);
        }

    }

}