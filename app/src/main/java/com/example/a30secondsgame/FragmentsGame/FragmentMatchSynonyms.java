package com.example.a30secondsgame.FragmentsGame;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.a30secondsgame.DbHelper;
import com.example.a30secondsgame.GameActivity;
import com.example.a30secondsgame.Models.Models;
import com.example.a30secondsgame.OnButtonClickListener;
import com.example.a30secondsgame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class FragmentMatchSynonyms extends Fragment {


    private ConstraintLayout constraintLayout;
    private TextView[] firstGroupTextViews;
    private TextView[] secondGroupTextViews;

    private ArrayList<String> firstGroupSynonyms;
    private ArrayList<String> secondGroupSynonyms;
    private double score = 0.0;

    private Models.AnswerTaskMatchSynonyms firstAnswer;
    private Models.AnswerTaskMatchSynonyms secondAnswer;

    private List<Models.AnswerTaskMatchSynonyms> currentTasks = new ArrayList<>();
    private List<Models.AnswerTaskMatchSynonyms> tasks = new ArrayList<>();


    List<Integer> currentTaskIds = new ArrayList<>();

    private HashMap<String, String> pairs;
    private TextView selectedTextView;
    private int points;
    private TextView counterTextView;
    DbHelper dbHelper;
    String languageId;

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
    public static FragmentMatchSynonyms newInstance(String languageId) {
        FragmentMatchSynonyms fragment = new FragmentMatchSynonyms();
        Bundle args = new Bundle();
        args.putString("firstLanguageId", languageId);
        fragment.setArguments(args);
        return fragment;



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_synonyms, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dbHelper = ((GameActivity) requireActivity()).getDbHelper();
            languageId = bundle.getString("firstLanguageId");
            tasks = dbHelper.saveAnswersTasksMatchSynonymsToObjects(languageId);

            constraintLayout = view.findViewById(R.id.constraintLayout);
            counterTextView = view.findViewById(R.id.counterTextView);
            firstGroupTextViews = new TextView[]{
                    view.findViewById(R.id.textView1),
                    view.findViewById(R.id.textView2),
                    view.findViewById(R.id.textView3),
                    view.findViewById(R.id.textView4)
            };

            secondGroupTextViews = new TextView[]{
                    view.findViewById(R.id.textView5),
                    view.findViewById(R.id.textView6),
                    view.findViewById(R.id.textView7),
                    view.findViewById(R.id.textView8)
            };

            loadText();
            // Pobierz Context z View
            Context context = view.getContext();

            // Dodaj OnClickListener dla każdego TextView w pierwszej grupie
            for (TextView textView : firstGroupTextViews) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        firstAnswer  = (Models.AnswerTaskMatchSynonyms) view.getTag();

                        view.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border_dimmed));
                        checkIsPairMatch();
                    }
                });
            }

            // Dodaj OnClickListener dla każdego TextView w drugiej grupie
            for (TextView textView : secondGroupTextViews) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        secondAnswer = (Models.AnswerTaskMatchSynonyms) view.getTag();

                        view.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border_dimmed));
                        checkIsPairMatch();
                    }
                });
            }
        }

        return view;
    }


    private void checkIsPairMatch() {
        if (firstAnswer != null && secondAnswer != null) {
            if (firstAnswer.getAnswer1().equals(secondAnswer.getAnswer1()) || firstAnswer.getAnswer2().equals(secondAnswer.getAnswer2())) {
                score += 0.25;
                View firstView = findViewByTag(constraintLayout, firstAnswer);
                View secondView = findViewByTag(constraintLayout, secondAnswer);

                if (firstView != null) {
                    for (TextView textView : firstGroupTextViews) {
                        if (textView.getTag().equals(firstAnswer)) {
                            textView.setVisibility(View.GONE);
                        }
                    }


                }

                if (secondView != null) {
                    for (TextView textView : secondGroupTextViews) {
                        if (textView.getTag().equals(secondAnswer)) {
                            textView.setVisibility(View.GONE);
                        }
                    }
                }

            } else if (!firstAnswer.getAnswer1().equals(secondAnswer.getAnswer1()) || !firstAnswer.getAnswer2().equals(secondAnswer.getAnswer2()) ) {
                View firstView = findViewByTag(constraintLayout, firstAnswer);

                View secondView = findViewByTag(constraintLayout, secondAnswer);

                if (firstView != null) {
                    for (TextView textView : firstGroupTextViews) {
                        if (textView.getTag().equals(firstAnswer)) {
                            textView.setVisibility(View.GONE);
                        }
                    }
                }

                if (secondView != null) {
                    for (TextView textView : secondGroupTextViews) {
                        if (textView.getTag().equals(secondAnswer)) {
                            textView.setVisibility(View.GONE);
                        }
                    }
                }

            }
            firstAnswer = null;
            secondAnswer = null;
            checkAllViewsAreGone();
        }

    }

    private void checkAllViewsAreGone() {
        boolean allViewsAreGone = true;
        for (TextView textView : firstGroupTextViews) {
            if (textView.getVisibility() != View.GONE) {
                allViewsAreGone = false;
                break;
            }
        }
        if (!allViewsAreGone) {
            for (TextView textView : secondGroupTextViews) {
                if (textView.getVisibility() != View.GONE) {
                    allViewsAreGone = false;
                    break;
                }
            }
        }
        if (allViewsAreGone) {
            listener.onButtonClick(score);
        }
    }


    private View findViewByTag(ViewGroup parent, Models.AnswerTaskMatchSynonyms answerTaskMatchSynonyms) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Object tag = child.getTag();
            if (tag != null && tag.equals(answerTaskMatchSynonyms)) {
                return child;
            }
            if (child instanceof ViewGroup) {
                View result = findViewByTag((ViewGroup) child, answerTaskMatchSynonyms);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }



    void loadText()
    {
        ArrayList<Integer> usedIds = new ArrayList<Integer>();
        int tempId;

        do {
            tempId = getRandomTaskId();
            if(!usedIds.contains(tempId))
            {
                currentTasks.add(tasks.get(tempId));
                usedIds.add(tempId);
            }
        } while(currentTasks.size() != 4);

        for(int i = 0; i < currentTasks.size(); i++)
        {
            firstGroupTextViews[i].setTag(currentTasks.get(i));
            firstGroupTextViews[i].setText(currentTasks.get(i).getAnswer1());
        }

        Collections.shuffle(currentTasks);

        for(int i = 0; i < currentTasks.size(); i++)
        {
            secondGroupTextViews[i].setTag(currentTasks.get(i));
            secondGroupTextViews[i].setText(currentTasks.get(i).getAnswer2());
        }
    }






    public int randomNumber(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max);
        return randomNum;
    }


    int getRandomTaskId() {
        // Get all task_ids
        List<Integer> taskIds = new ArrayList<>();
        for (Models.AnswerTaskMatchSynonyms question : tasks) {
            taskIds.add(question.getTaskId());
        }
        Collections.sort(taskIds);
        taskIds = new ArrayList<>(new LinkedHashSet<>(taskIds));


        int randomIdx = randomNumber(taskIds.size() - 1);


        return taskIds.get(randomIdx);
    }

}