package com.example.a30secondsgame;

import static com.example.a30secondsgame.Models.ModelTaskFillBlank.parseJsonToTaskList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a30secondsgame.Models.ModelTaskFillBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggedUserMenuActivity extends AppCompatActivity implements  FragmentHomePage.OnPlayClickListener{
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

Button playBtn;
User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_menu);

        Intent intent =getIntent();
        user = (User) intent.getSerializableExtra("user");

        addFragment();
        obtainTasksData();






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
                Intent intent = new Intent(LoggedUserMenuActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        }, 1500); // Czas trwania animacji (w milisekundach)
    }



    private void obtainTasksData()
    {
       String apiUrl = "/task_fill_in_the_blanks.php";
        Map<String,String> postData = new HashMap<>();
        ApiService apiService = new ApiService(apiUrl, postData, new ApiService.ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {
                List<ModelTaskFillBlank> task_fill_in_the_blanks = parseJsonToTaskList(response);
                Toast.makeText(LoggedUserMenuActivity.this, "Udalo się pobrac", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoggedUserMenuActivity.this, "Nie udalo się pobrac", Toast.LENGTH_SHORT).show();

            }
        });
        apiService.execute();

    }
}