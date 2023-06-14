package com.example.a30secondsgame.FragmentsLoggedUser;

import static com.example.a30secondsgame.Models.Models.Leaderboard.parseLeaderboardFromJson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a30secondsgame.ApiService;
import com.example.a30secondsgame.Models.Models;
import com.example.a30secondsgame.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentLeaderboard extends Fragment {


    private List<Models.Leaderboard> leaderboardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;

    public FragmentLeaderboard() {
        // Required empty public constructor
    }


    public static FragmentLeaderboard newInstance() {
        FragmentLeaderboard fragment = new FragmentLeaderboard();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLeaderboard);


        getLeaderboard();

        return view;
    }

    void getLeaderboard() {
        String apiUrl = "/leaderboard.php";
        Map<String, String> postData = new HashMap<>();
        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {

            @Override
            public void onSuccess(String response) {
                leaderboardList = parseLeaderboardFromJson(response);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                leaderboardAdapter = new LeaderboardAdapter(leaderboardList);
                recyclerView.setAdapter(leaderboardAdapter);

            }

            @Override
            public void onError(String error) {
                Log.d("Error", "obtainLanguages: " + error);
            }
        });
        apiService.execute();
    }





    public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

        private List<Models.Leaderboard> leaderboardList;

        public LeaderboardAdapter(List<Models.Leaderboard> leaderboardList) {
            this.leaderboardList = leaderboardList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Models.Leaderboard leaderboard = leaderboardList.get(position);
            holder.txtUsername.setText(leaderboard.getUsername());
            holder.txtScore.setText(String.valueOf(leaderboard.getScore()));
        }

        @Override
        public int getItemCount() {
            return leaderboardList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtUsername;
            TextView txtScore;

            public ViewHolder(View itemView) {
                super(itemView);
                txtUsername = itemView.findViewById(R.id.txtUsername);
                txtScore = itemView.findViewById(R.id.txtScore);
            }
        }
    }

}