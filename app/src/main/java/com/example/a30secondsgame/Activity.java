package com.example.a30secondsgame;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.a30secondsgame.ApiService.ApiResponseCallback;
import com.example.a30secondsgame.FragmentsStartPage.CallbackFragment;
import com.example.a30secondsgame.FragmentsStartPage.FragmentLogin;
import com.example.a30secondsgame.FragmentsStartPage.FragmentRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Activity extends AppCompatActivity implements CallbackFragment, FragmentLogin.OnLoginClickListener, FragmentRegister.OnRegisterClickListener {

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private ConfigManager configManager;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
    }


    public void addFragment()
    {
        FragmentLogin fragment = new FragmentLogin();

        fragment.setCallbackFragment(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
        configManager = new ConfigManager(getApplicationContext());
        configManager.readConfigFileOrCreateNew();

    }


    public void replaceFragment(String type)
    {

        if(type =="register")
        {
            fragment = new FragmentRegister();

        }
        else if(type == "login")
        {
            fragment = new FragmentLogin();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();

    }
    @Override
    public void changeFragment()
    {
        replaceFragment("register");
    }


    @Override
    public void onLoginClick(User user) {
        Map<String, String> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("password", user.getPassword());

        ApiService login = new ApiService("/login.php", data, new ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("message")) {
                        // Zalogowano pomyślnie
                        configManager.setLoginInConfig(user.getUsername());
                        configManager.setPasswordInConfig(DataHashing.hashString(user.getPassword()));
                        Intent intent = new Intent(Activity.this, LoggedUserMenuActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Activity.this, "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show();
                    } else if (jsonResponse.has("error")) {
                        // Błąd logowania
                        String errorMessage = jsonResponse.getString("error");
                        Toast.makeText(Activity.this, errorMessage, Toast.LENGTH_LONG).show();

                    } else {
                        onError("Nieprawidłowa odpowiedź serwera");

                    }
                } catch (JSONException e) {
                    onError("Nieprawidłowa odpowiedź serwera");

                }
            }

            @Override
            public void onError(String error) {

                offlineLogin(user);

            }
        });

        login.execute();

    }


    boolean offlineLogin(User user)
    {
        String tempLogin = configManager.getLoginFromConfig();
        if(tempLogin.equals(user.getUsername()) && !configManager.getFirstRunFromConfig().equals("true")) {
            if (DataHashing.verifyHash(user.getPassword(), configManager.getPasswordFromConfig())) {
                Intent intent = new Intent(Activity.this, LoggedUserMenuActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                Toast.makeText(Activity.this, "Jesteś w trybie offline", Toast.LENGTH_SHORT).show();
                return true;
            } else
            {
                Toast.makeText(Activity.this, "błedne haslo", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else
        { Toast.makeText(Activity.this, "błedny login", Toast.LENGTH_LONG).show();
            return false;}
    }

    @Override
    public void onRegisterClick(@NonNull User user) {
        Map<String, String> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("password", user.getPassword());
        data.put("email", user.getEmail());
        data.put("avatar_url", "dsadsa");

        ApiService register = new ApiService("/register.php", data, new ApiResponseCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.d("ApiService-Response", response);
                    JSONObject jsonResponse = new JSONObject(response);

                    if (jsonResponse.has("message")) {
                        // Rejestracja zakończona pomyślnie
                        Toast.makeText(Activity.this, "Zarejestrowano pomyślnie", Toast.LENGTH_SHORT).show();
                        replaceFragment("login"); // Przełącz z powrotem na ekran logowania
                    } else if (jsonResponse.has("error")) {
                        // Błąd podczas rejestracji
                        String errorMessage = jsonResponse.getString("error");
                        Toast.makeText(Activity.this, errorMessage, Toast.LENGTH_LONG).show();
                    } else {
                        onError("Nieprawidłowa odpowiedź serwera");
                    }
                } catch (JSONException e) {
                    onError("Nieprawidłowa odpowiedź serwera");
                }
            }

            @Override
            public void onError(String error) {
                replaceFragment("login"); // Przełącz z powrotem na ekran logowania

            }
        });
        register.execute();

    }




}