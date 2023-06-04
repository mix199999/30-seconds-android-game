package com.example.a30secondsgame.FragmentsGame;

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
import com.example.a30secondsgame.R;


public class FragmentFillBlanks extends Fragment {

TextView questionText;
Button firstAnswerBt, secondAnswerBt, thirdAnswerBt;
Cursor[] cursor;
String firstLanguageId, secondLanguageId;
DbHelper dbHelper;
    public FragmentFillBlanks() {

    }


    public static FragmentFillBlanks newInstance(String firstLanguageId, String secondLanguageId) {
        FragmentFillBlanks fragment = new FragmentFillBlanks();
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", firstLanguageId);
        bundle.putString("secondLanguageId", secondLanguageId);
                fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = ((GameActivity) requireActivity()).getDbHelper();

        Bundle bundle = getArguments();
        if (bundle != null) {
            firstLanguageId = bundle.getString("firstLanguageId");
            secondLanguageId = bundle.getString("secondLanguageId");





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
             secondLanguageId = bundle.getString("secondLanguageId");

        }

        return view;
    }
}