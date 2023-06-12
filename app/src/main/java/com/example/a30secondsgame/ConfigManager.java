package com.example.a30secondsgame;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConfigManager {
    private static final String CONFIG_FILE_NAME = "config.json";
    private Context context;

    public ConfigManager(Context context) {
        this.context = context;
    }

    public void createDefaultConfigFile() {
        try {
            JSONObject defaultConfig = new JSONObject();
            defaultConfig.put("primaryLanguage", "1");
            defaultConfig.put("secondaryLanguage", "2");
            defaultConfig.put("login", "");
            defaultConfig.put("password", "");
            defaultConfig.put("firstRun", "true");
            String lastOpened = getCurrentDateTime();
            defaultConfig.put("lastOpened", lastOpened);
            defaultConfig.put("imageName","");

            // Zapisz zawartość pliku konfiguracyjnego
            saveConfigFile(defaultConfig);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLoginFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("login", "");
        }
        return "";
    }

    public void setLoginInConfig(String login) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("login", login);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setFirstRunInConfig(String status) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("firstRun", status);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFirstRunFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("firstRun", "");
        }
        return "";
    }

    public String getPasswordFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("password", "");
        }
        return "";
    }

    public void setPasswordInConfig(String password) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("password", password);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLastOpenedFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("lastOpened", "");
        }
        return "";
    }

    public void setLastOpenedInConfig(String lastOpened) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("lastOpened", lastOpened);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPrimaryLanguageFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("primaryLanguage", "English");
        }
        return "English";
    }

    public void setPrimaryLanguageInConfig(String primaryLanguage) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("primaryLanguage", primaryLanguage);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getSecondaryLanguageFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("secondaryLanguage", "Polish");
        }
        return "Polish";
    }

    public void setSecondaryLanguageInConfig(String secondaryLanguage) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("secondaryLanguage", secondaryLanguage);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject readConfigFile() {
        try {
            InputStream inputStream = context.openFileInput(CONFIG_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String configString = new String(buffer, "UTF-8");
            return new JSONObject(configString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    JSONObject readConfigFileOrCreateNew() {
        JSONObject configJson = readConfigFile();
        if (configJson == null) {
            configJson = new JSONObject();
            createDefaultConfigFile();
        }
        return configJson;
    }

    private void saveConfigFile(JSONObject configJson) {
        try {
            String configString = configJson.toString();
            FileOutputStream outputStream = context.openFileOutput(CONFIG_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(configString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }
    public void setImageNameInConfig(String imageName) {
        try {
            JSONObject configJson = readConfigFileOrCreateNew();
            configJson.put("imageName", imageName);
            saveConfigFile(configJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getImageNameFromConfig() {
        JSONObject configJson = readConfigFile();
        if (configJson != null) {
            return configJson.optString("imageName", "");
        }
        return "";
    }



}
