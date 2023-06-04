package com.example.a30secondsgame.FragmentsGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a30secondsgame.DbHelper;
import com.example.a30secondsgame.GameActivity;
import com.example.a30secondsgame.R;


public class FragmentMultipleChoice extends Fragment {

    String firstLanguageId;
    String secondLanguageId;
    String[][] tasksTables = new String[2][];
    DbHelper dbHelper;
    public FragmentMultipleChoice() {

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
        dbHelper = ((GameActivity) requireActivity()).getDbHelper();

        Bundle bundle = getArguments();
        if (bundle != null) {
            firstLanguageId = bundle.getString("firstLanguageId");
            secondLanguageId = bundle.getString("secondLanguageId");
            tasksTables = dbHelper.saveAnswersTasksMultipleChoiceToStringArray(firstLanguageId,secondLanguageId);





        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        dbHelper = ((GameActivity) requireActivity()).getDbHelper();

        Bundle bundle = getArguments();
        if (bundle != null) {
            firstLanguageId = bundle.getString("firstLanguageId");
            secondLanguageId = bundle.getString("secondLanguageId");

        }

        return view;
    }
}
