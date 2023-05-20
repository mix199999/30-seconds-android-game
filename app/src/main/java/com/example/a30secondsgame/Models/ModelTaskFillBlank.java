package com.example.a30secondsgame.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModelTaskFillBlank
{
    private int id;
    private String sentence;
    private String correct_answer;
    private String wrong_answer1;
    private String wrong_answer2;
    private String wrong_answer3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getWrong_answer1() {
        return wrong_answer1;
    }

    public void setWrong_answer1(String wrong_answer1) {
        this.wrong_answer1 = wrong_answer1;
    }

    public String getWrong_answer2() {
        return wrong_answer2;
    }

    public void setWrong_answer2(String wrong_answer2) {
        this.wrong_answer2 = wrong_answer2;
    }

    public String getWrong_answer3() {
        return wrong_answer3;
    }

    public void setWrong_answer3(String wrong_answer3) {
        this.wrong_answer3 = wrong_answer3;
    }




    public static List<ModelTaskFillBlank> parseJsonToTaskList(String json) {
        List<ModelTaskFillBlank> taskList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ModelTaskFillBlank task = new ModelTaskFillBlank();
                task.setId(jsonObject.getInt("id"));
                task.setSentence(jsonObject.getString("sentence"));
                task.setCorrect_answer(jsonObject.getString("correct_answer"));
                task.setWrong_answer1(jsonObject.getString("wrong_answer1"));
                task.setWrong_answer2(jsonObject.getString("wrong_answer2"));
                task.setWrong_answer3(jsonObject.getString("wrong_answer3"));
                taskList.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskList;
    }
}
