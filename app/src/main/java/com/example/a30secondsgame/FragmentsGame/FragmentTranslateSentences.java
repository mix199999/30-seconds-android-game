package com.example.a30secondsgame.FragmentsGame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class FragmentTranslateSentences extends Fragment {


    EditText answerEditText;
    TextView questionTextView;
    Button  checkBt;
    String firstLanguageId, secondLanguageId;
    DbHelper dbHelper;
    int currentTaskId ;

    List<Models.AnswerTaskTranslateSentences> firstLanguageAnswersList = new ArrayList<>();
    List<Models.AnswerTaskTranslateSentences> secondLanguageAnswersList = new ArrayList<>();
    Models.AnswerTaskTranslateSentences firstLanguageCurrentTask = null;
    Models.AnswerTaskTranslateSentences secondLanguageCurrentTask = null;

    private OnButtonClickListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnButtonClickListener");
        }
    }

    public FragmentTranslateSentences() {

    }


    public static FragmentTranslateSentences newInstance(String firstLanguageId, String secondLanguageId) {
        FragmentTranslateSentences fragment = new FragmentTranslateSentences();
        Bundle args = new Bundle();
        args.putString("firstLanguageId", firstLanguageId);
        args.putString("secondLanguageId", secondLanguageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_translate_sentences, container, false);
        Bundle bundle =getArguments();
        if(bundle != null)
        {
            firstLanguageId = bundle.getString("firstLanguageId");
            secondLanguageId = bundle.getString("secondLanguageId");
            dbHelper = ((GameActivity) requireActivity()).getDbHelper();
            firstLanguageAnswersList = dbHelper.saveAnswersTasksTranslateSentencesToObjects(firstLanguageId);
            secondLanguageAnswersList = dbHelper.saveAnswersTasksTranslateSentencesToObjects(secondLanguageId);
            questionTextView = view.findViewById(R.id.questionTextView);
            answerEditText = view.findViewById(R.id.answerEditText);
            checkBt = view.findViewById(R.id.checkBt);
            loadQuestion();



            checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkIsCorrect();
                }
            });

        }


        return view;
    }
    private void loadQuestion()
    {
        currentTaskId = getRandomTaskId();
        firstLanguageCurrentTask = getQuestionsByTaskId(currentTaskId, firstLanguageId, firstLanguageAnswersList);
        secondLanguageCurrentTask =getQuestionsByTaskId(currentTaskId, secondLanguageId, secondLanguageAnswersList);
        questionTextView.setText(firstLanguageCurrentTask.getAnswer());

    }



    public int randomNumber(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max);
        return randomNum;
    }


    int getRandomTaskId() {
        // Get all task_ids
        List<Integer> taskIds = new ArrayList<>();
        for (Models.AnswerTaskTranslateSentences question : firstLanguageAnswersList) {
            taskIds.add(question.getTaskId());
        }
        Collections.sort(taskIds);
        taskIds = new ArrayList<>(new LinkedHashSet<>(taskIds));


        int randomIdx = randomNumber(taskIds.size() - 1);


        return taskIds.get(randomIdx);
    }


    Models.AnswerTaskTranslateSentences getQuestionsByTaskId(int taskId , String lang , List<Models.AnswerTaskTranslateSentences> list) {
        Models.AnswerTaskTranslateSentences currentAnswer = null;
        for (Models.AnswerTaskTranslateSentences answer : list) {
            if (answer.getTaskId() == taskId && answer.getLanguageId() == Integer.parseInt(lang)) {
                currentAnswer = answer;
            }

        }
        return currentAnswer;
    }


    private void checkIsCorrect()
    {
        String tempQuestion = modifyString(secondLanguageCurrentTask.getAnswer());
        String tempAnswer = modifyString(answerEditText.getText().toString());
        if(tempQuestion.equals(tempAnswer))
        {
            listener.onButtonClick(1);
        }

        else if (!tempQuestion.equals(tempAnswer)) {
            listener.onButtonClick(0);
        }
    }

    public String modifyString(String input) {
        return  input.replaceAll(" ", "").toLowerCase();

    }
}