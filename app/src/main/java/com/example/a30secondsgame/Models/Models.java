package com.example.a30secondsgame.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Models
{



    public static class Language {
        private int id;
        private String languageCode;
        public Language(int id, String languageCode) {
            this.id = id;
            this.languageCode = languageCode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }


        public static List<Language> parseLanguageFromJson(String json) {
            List<Language> languages = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String languageCode = jsonObject.getString("language_code");
                    Language language = new Language(id, languageCode);
                    languages.add(language);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return languages;
        }
    }

    public static class QuestionTaskFillInTheBlanks {
        private int id;
        private int taskId;
        private int languageId;
        private String questionText;
        public QuestionTaskFillInTheBlanks(int id, int taskId, int languageId, String questionText) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.questionText = questionText;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public static List<QuestionTaskFillInTheBlanks> parseQuestionTaskFillInTheBlanksFromJson(String json) {
            List<QuestionTaskFillInTheBlanks> questions = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int taskId = jsonObject.getInt("task_id");
                    int languageId = jsonObject.getInt("language_id");
                    String questionText = jsonObject.getString("question_text");
                    QuestionTaskFillInTheBlanks question = new QuestionTaskFillInTheBlanks(id, taskId, languageId, questionText);
                    questions.add(question);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return questions;
        }
    }

    public static class AnswerTaskFillInTheBlanks {
        private int id;
        private int taskId;
        private int languageId;
        private String answer_text;
        private int isCorrect;
        public AnswerTaskFillInTheBlanks(int id, int taskId, int languageId, String answerText, int isCorrect) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answer_text = answerText;
            this.isCorrect = isCorrect;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public String getAnswer_text() {
            return answer_text;
        }

        public void setAnswer_text(String answer_text) {
            this.answer_text = answer_text;
        }

        public int isCorrect() {
            return isCorrect;
        }

        public void setCorrect(int correct) {
            isCorrect = correct;
        }

        public static List<AnswerTaskFillInTheBlanks> parseAnswerTaskFillInTheBlanksFromJson(String json) {
            List<AnswerTaskFillInTheBlanks> answers = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int taskId = jsonObject.getInt("task_id");
                    int languageId = jsonObject.getInt("language_id");
                    String answerText = jsonObject.getString("answer_text");
                    int isCorrect = jsonObject.getInt("is_correct");
                    AnswerTaskFillInTheBlanks answer = new AnswerTaskFillInTheBlanks(id, taskId, languageId, answerText, isCorrect);
                    answers.add(answer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return answers;
        }
    }



    public static class AnswerTaskMatchSynonyms {
        private int id;
        private int taskId;
        private int languageId;
        private String answer1;
        private String answer2;
        public AnswerTaskMatchSynonyms(int id, int taskId, int languageId, String answer1, String answer2) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answer1 = answer1;
            this.answer2 = answer2;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }
        public static List<AnswerTaskMatchSynonyms> parseAnswerTaskMatchSynonymsFromJson(String json) {
            List<AnswerTaskMatchSynonyms> answers = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int taskId = jsonObject.getInt("task_id");
                    int languageId = jsonObject.getInt("language_id");
                    String answer1 = jsonObject.getString("answer1");
                    String answer2 = jsonObject.getString("answer2");
                    AnswerTaskMatchSynonyms answer = new AnswerTaskMatchSynonyms(id, taskId, languageId, answer1, answer2);
                    answers.add(answer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return answers;
        }
    }



    public static class QuestionTaskMultipleChoice {
        private int id;
        private int taskId;
        private int languageId;
        private String questionText;
        public QuestionTaskMultipleChoice(int id, int taskId, int languageId, String questionText) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.questionText = questionText;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public static List<QuestionTaskMultipleChoice> parseQuestionTaskMultipleChoiceFromJson(String json) {
            List<QuestionTaskMultipleChoice> questions = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int taskId = jsonObject.getInt("task_id");
                    int languageId = jsonObject.getInt("language_id");
                    String questionText = jsonObject.getString("question_text");
                    QuestionTaskMultipleChoice question = new QuestionTaskMultipleChoice(id, taskId, languageId, questionText);
                    questions.add(question);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return questions;
        }
    }

    public static class AnswerTaskMultipleChoice {
        private int id;
        private int taskId;
        private int languageId;
        private String answer_text;
        private int isCorrect;
        public AnswerTaskMultipleChoice(int id, int taskId, int languageId, String answerText, int isCorrect) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answer_text = answerText;
            this.isCorrect = isCorrect;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public String getAnswer_text() {
            return answer_text;
        }

        public void setAnswer_text(String answer_text) {
            this.answer_text = answer_text;
        }

        public int isCorrect() {
            return isCorrect;
        }

        public void setCorrect(int correct) {
            isCorrect = correct;
        }


        public static List<AnswerTaskMultipleChoice> parseAnswerTaskMultipleChoiceFromJson(String json) {
            List<AnswerTaskMultipleChoice> answers = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int taskId = jsonObject.getInt("task_id");
                    int languageId = jsonObject.getInt("language_id");
                    String answerText = jsonObject.getString("answer_text");
                    int isCorrect = jsonObject.getInt("is_correct");
                    AnswerTaskMultipleChoice answer = new AnswerTaskMultipleChoice(id, taskId, languageId, answerText, isCorrect);
                    answers.add(answer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return answers;
        }
    }



    public static class AnswerTaskTranslateSentences {
        private int id;
        private int taskId;
        private int languageId;
        private String answer;
        public AnswerTaskTranslateSentences(int id, int taskId, int languageId, String answer) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answer = answer;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public int getLanguageId() {
            return languageId;
        }

        public void setLanguageId(int languageId) {
            this.languageId = languageId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }



        public static List<AnswerTaskTranslateSentences> parseAnswerTaskTranslateSentencesFromJson(String json) {
            List<AnswerTaskTranslateSentences> answers = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    int taskId = jsonObject.getInt("task_id");
                    int languageId = jsonObject.getInt("language_id");
                    String answer = jsonObject.getString("answer");
                    AnswerTaskTranslateSentences ans = new AnswerTaskTranslateSentences(id, taskId, languageId, answer);
                    answers.add(ans);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return answers;
        }
    }

    public static class Leaderboard{

        public static List<Leaderboard> parseLeaderboardFromJson(String json)
        {
            List<Leaderboard> leaderboardList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i<jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String username =jsonObject.getString("username");
                    int score = jsonObject.getInt("score");
                    Leaderboard player = new Leaderboard(username,score);
                    leaderboardList.add(player);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            return leaderboardList;
        }

        String username;
        int score;

        public Leaderboard(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
