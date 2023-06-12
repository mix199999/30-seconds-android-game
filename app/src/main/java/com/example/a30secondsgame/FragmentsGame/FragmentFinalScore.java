package com.example.a30secondsgame.FragmentsGame;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a30secondsgame.Models.OnButtonBackClickListener;
import com.example.a30secondsgame.OnButtonClickListener;
import com.example.a30secondsgame.R;


public class FragmentFinalScore extends Fragment {

    int score;
    Button backBt;
    TextView scoreTextView;
    private OnButtonBackClickListener listener;
    public FragmentFinalScore() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnButtonBackClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnButtonClickListener");
        }
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFinalScore newInstance(int finalScore) {
        FragmentFinalScore fragment = new FragmentFinalScore();
        Bundle args = new Bundle();
        args.putInt("finalScore", finalScore);
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_final_score, container, false);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            scoreTextView= view.findViewById(R.id.scoreTextView);
            backBt = view.findViewById(R.id.backBt);
            score = bundle.getInt("finalScore");
            scoreTextView.setText(Integer.toString(score));

            backBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onButtonBackClick();

                }
            });


        }

        return view;
    }


}