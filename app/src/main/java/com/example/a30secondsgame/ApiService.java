package com.example.a30secondsgame;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


import java.util.Map;


public class ApiService extends AsyncTask<String,Void,String>
{


    private static final String API_BASE_URL = "http://10.0.2.2/game_rest_API";
    private String apiUrl;
    private Map<String,String> postData;
    private ApiResponseCallback callback;




    public ApiService(String apiUrl,Map<String,String> postData, ApiResponseCallback callBack)
    {
        this.apiUrl = apiUrl;
        this.postData = postData;
        this.callback = callBack;

    }


    @Override
    protected String doInBackground(String... params)
    {
        try{
            URL url = new URL(API_BASE_URL+apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(getPostDataString(postData));
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine())!=null)
                {
                    response.append(line);
                }
                bufferedReader.close();
                return response.toString();
            }else
                return null;

        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected  void onPostExecute(String result)
    {
        if(result == null)
        {
            callback.onError("nie mozna polaczyc sie z serwerem");
        }
        else {
            callback.onSuccess(result);
        }
    }

    private String getPostDataString(Map<String, String> params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            if (entry.getValue() != null) {
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }
        return result.toString();
    }
    public interface  ApiResponseCallback{
        void onSuccess(String response);
        void onError(String error);
    }


}
