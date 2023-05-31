package com.example.a30secondsgame.FragmentsGame;

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
import androidx.fragment.app.Fragment;

import com.example.a30secondsgame.DbHelper;
import com.example.a30secondsgame.GameActivity;
import com.example.a30secondsgame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FragmentMatchSynonyms extends Fragment {

    private static final int BACKGROUND_COLOR = Color.argb(100, 255, 255, 255);
    private ConstraintLayout constraintLayout;
    private TextView[] firstGroupTextViews;
    private TextView[] secondGroupTextViews;

    private ArrayList<String> firstGroupSynonyms;
    private ArrayList<String> secondGroupSynonyms;

    private HashMap<String, String> pairs;
    private TextView selectedTextView;
    private int points;
    private TextView counterTextView;
    DbHelper dbHelper;

    public static FragmentMatchSynonyms newInstance() {
        return new FragmentMatchSynonyms();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = ((GameActivity) requireActivity()).getDbHelper();
        Cursor cursor = dbHelper. getAnswersTasksMatchSynonymsTable(1);

        firstGroupSynonyms = new ArrayList<>();
        secondGroupSynonyms = new ArrayList<>();
        pairs = new HashMap<>();

        int answer1ColumnIndex = cursor.getColumnIndex("answer1");
        int answer2ColumnIndex = cursor.getColumnIndex("answer2");

        if (answer1ColumnIndex >= 0 && answer2ColumnIndex >= 0) {
            while (cursor.moveToNext()) {
                String answer1 = cursor.getString(answer1ColumnIndex);
                String answer2 = cursor.getString(answer2ColumnIndex);
                firstGroupSynonyms.add(answer1);
                secondGroupSynonyms.add(answer2);
                pairs.put(answer1, answer2);
            }
            cursor.close();

        }
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_synonyms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        for (int i = 0; i < firstGroupTextViews.length; i++) {
            firstGroupTextViews[i].setText(firstGroupSynonyms.get(i));
            secondGroupTextViews[i].setText(secondGroupSynonyms.get(i));
            firstGroupTextViews[i].setTag("firstGroup");
            secondGroupTextViews[i].setTag("secondGroup");
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView currentTextView = (TextView) v;
                currentTextView.setBackgroundColor(BACKGROUND_COLOR);
                String currentTag = (String) currentTextView.getTag();
                if (selectedTextView == null || currentTag.equals(selectedTextView.getTag())) {
                    selectedTextView = currentTextView;

                } else {
                    if (pairMatch(selectedTextView, currentTextView)  ) {


                        // Increment points counter and update counterTextView
                        points++;
                        counterTextView.setText(String.valueOf(points));

                        // Hide the matched pair of TextViews
                        selectedTextView.setVisibility(View.INVISIBLE);
                        currentTextView.setVisibility(View.INVISIBLE);

                        selectedTextView = null;
                    } else {

                        final TextView previousTextView = selectedTextView;
                        selectedTextView.setVisibility(View.INVISIBLE);
                        currentTextView.setVisibility(View.INVISIBLE);
                        constraintLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentTextView.setBackgroundColor(Color.TRANSPARENT);
                                previousTextView.setBackgroundColor(Color.TRANSPARENT);
                                selectedTextView = null;
                            }
                        }, 1000);
                    }
                }
            }
        };

        for (TextView textView : firstGroupTextViews) {
            textView.setOnClickListener(onClickListener);
        }
        for (TextView textView : secondGroupTextViews) {
            textView.setOnClickListener(onClickListener);
        }
    }

    private boolean pairMatch(TextView textView1, TextView textView2) {
        String text1 = textView1.getText().toString();
        String text2 = textView2.getText().toString();



        String synonym1 = pairs.get(text1);
        String synonym2 = pairs.get(text2);
        if (synonym1 != null && synonym2 != null) {
            return synonym1.equals(text2) || synonym2.equals(text1);
        } else if (synonym1 != null) {
            return synonym1.equals(text2);
        } else if (synonym2 != null) {
            return synonym2.equals(text1);
        } else {
            return false;
        }




    }

}