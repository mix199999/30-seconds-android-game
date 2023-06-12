package com.example.a30secondsgame.FragmentsLoggedUser;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a30secondsgame.ConfigManager;
import com.example.a30secondsgame.R;
import com.example.a30secondsgame.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentSettings extends Fragment {

    private Spinner primaryLanguageSpinner;
    private Spinner secondaryLanguageSpinner;

    private String firstLanguageId, secondLanguageId;
    TextView userNameTextView;
    Button saveChangesBt;
    int resId;
    ImageView avatarView;
    User user;
    public FragmentSettings() {
        // Required empty public constructor
    }
    private ConfigManager configManager;

    public static FragmentSettings newInstance(String firstLanguageId, String secondLanguageId, User user) {
        FragmentSettings fragment = new FragmentSettings();
        Bundle bundle = new Bundle();
        bundle.putString("firstLanguageId", firstLanguageId);
        bundle.putString("secondLanguageId", secondLanguageId);
        bundle.putSerializable("userObj", user);
        fragment.setArguments(bundle);
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

        View view =inflater.inflate(R.layout.fragment_settings, container, false);
        Bundle bundle = getArguments();
        firstLanguageId =bundle.getString("firstLanguageId");
        secondLanguageId =bundle.getString("secondLanguageId");
        user = (User) bundle.getSerializable("userObj");
        primaryLanguageSpinner = view.findViewById(R.id.primaryLanguageSpinner);
        secondaryLanguageSpinner = view.findViewById(R.id.secondaryLanguageSpinner);
        saveChangesBt = view.findViewById(R.id.saveChangesBt);
        configManager = new ConfigManager(requireContext());
        userNameTextView = view.findViewById(R.id.usernameText);
        userNameTextView.setText(user.getUsername());
        avatarView = view.findViewById(R.id.userAvatar);
        String imageName = configManager.getImageNameFromConfig();
        if(!imageName.equals(""))
        {

        }



        Country[] countries = {
                new Country("English", R.drawable.united_kingdom, 1),
                new Country("Polish", R.drawable.poland,2),
                new Country("Germany", R.drawable.germany,4),
                new Country("Italian", R.drawable.italy,5),
                new Country("Spanish", R.drawable.spain,3)
        };

        CountryArrayAdapter adapter = new CountryArrayAdapter(requireContext(), countries);
        primaryLanguageSpinner.setAdapter(adapter);
        secondaryLanguageSpinner.setAdapter(adapter);

        for (int i = 0; i < countries.length; i++) {
            if (countries[i].getLanguageId() == Integer.parseInt(firstLanguageId)) {
                primaryLanguageSpinner.setSelection(i);
            }
            if (countries[i].getLanguageId() == Integer.parseInt(secondLanguageId)) {
                secondaryLanguageSpinner.setSelection(i);
            }
        }
        saveChangesBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedPrimaryLanguageId = ((Country) primaryLanguageSpinner.getSelectedItem()).getLanguageId();
                int selectedSecondaryLanguageId = ((Country) secondaryLanguageSpinner.getSelectedItem()).getLanguageId();
                configManager.setPrimaryLanguageInConfig(Integer.toString(selectedPrimaryLanguageId));
                configManager.setSecondaryLanguageInConfig(Integer.toString(selectedSecondaryLanguageId));
                Drawable selectedImage = avatarView.getDrawable();
                if (selectedImage != null) {
                    String drawableName = getResources().getResourceEntryName(resId);

                    configManager.setImageNameInConfig(drawableName);

                }
            }
        });

        ImageView userAvatarImageView = view.findViewById(R.id.userAvatar);
        userAvatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageList(v);
            }
        });


        return view;
    }


    public static class Country  {
        private String name;
        private int flagResId;
        private int languageId;

        public int getLanguageId() {
            return this.languageId;
        }

        public Country(String name, int flagResId, int languageId) {
            this.name = name;
            this.flagResId = flagResId;
            this.languageId = languageId;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }


    private static class CountryArrayAdapter extends ArrayAdapter<Country> {

        private LayoutInflater inflater;

        public CountryArrayAdapter(Context context, Country[] countries) {
            super(context, 0, countries);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.language_spinner_item, parent, false);
            }

            Country country = getItem(position);
            if (country != null) {
                TextView languageTextView = view.findViewById(R.id.languageTextView);
                ImageView flagImageView = view.findViewById(R.id.flagImageView);

                languageTextView.setText(country.name);
                flagImageView.setImageResource(country.flagResId);
            }

            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }
    public void showImageList(View view) {
        // Creating a list of drawables
        List<Drawable> imageList = new ArrayList<>();
        imageList.add(getResources().getDrawable(R.drawable.poland));
        imageList.add(getResources().getDrawable(R.drawable.united_kingdom));
        imageList.add(getResources().getDrawable(R.drawable.germany));
        imageList.add(getResources().getDrawable(R.drawable.italy));
        imageList.add(getResources().getDrawable(R.drawable.spain));


        // Creating an adapter for the ListView
        ArrayAdapter<Drawable> adapter = new ArrayAdapter<Drawable>(requireContext(), android.R.layout.simple_list_item_1, imageList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_image, parent, false);
                }

                ImageView imageView = convertView.findViewById(R.id.userAvatar);
                imageView.setImageDrawable(getItem(position));

                return convertView;
            }
        };

        // Creating a dialog with the ListView
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, (dialog, which) -> {
            // Image selection
            Drawable selectedImage = imageList.get(which);
            // You can use the selected image here, for example, assign it to an ImageView
            ImageView userAvatarImageView = view.findViewById(R.id.userAvatar);
            userAvatarImageView.setImageDrawable(selectedImage);
            String drawablePath = selectedImage.toString();
            String a = "aa";
            dialog.dismiss();
        });

        builder.show();
    }





}