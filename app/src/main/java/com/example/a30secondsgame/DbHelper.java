package com.example.a30secondsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a30secondsgame.Models.ModelTaskFillBlank;
import com.example.a30secondsgame.Models.ModelTaskMatchSynonyms;
import com.example.a30secondsgame.Models.ModelTaskMultipleChoice;
import com.example.a30secondsgame.Models.ModelTaskTranslateSentences;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasksManager";

    // Tabela dla Fill In The Blank
    private static final String TABLE_TASKS_FILL_BLANK = "tasks_fill_blank";
    private static final String KEY_ID_FILL = "id";
    private static final String KEY_SENTENCE = "sentence";
    private static final String KEY_CORRECT_ANSWER = "correct_answer";
    private static final String KEY_WRONG_ANSWER1 = "wrong_answer1";
    private static final String KEY_WRONG_ANSWER2 = "wrong_answer2";
    private static final String KEY_WRONG_ANSWER3 = "wrong_answer3";

    // Tabela dla Match Synonyms
    private static final String TABLE_TASKS_MATCH_SYNONYMS = "tasks_match_synonyms";
    private static final String KEY_ID_SYN = "id";
    private static final String KEY_WORD1 = "word1";
    private static final String KEY_WORD2 = "word2";

    // Tabela dla Multiple Choice
    private static final String TABLE_TASKS_MULTIPLE_CHOICE = "tasks_multiple_choice";
    private static final String KEY_ID_MC = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_CHOICE1 = "choice1";
    private static final String KEY_CHOICE2 = "choice2";
    private static final String KEY_CHOICE3 = "choice3";
    private static final String KEY_ANSWER = "answer";

    // Tabela dla Translate Sentences
    private static final String TABLE_TASKS_TRANSLATE_SENTENCES = "tasks_translate_sentences";
    private static final String KEY_ID_TS = "id";
    private static final String KEY_QUESTION_TS = "question";
    private static final String KEY_ANSWER_TS = "answer";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_FILL_BLANK_TABLE =  "CREATE TABLE " + "TABLE_TASKS_FILL_BLANK_TABLE" + "("
                + "KEY_ID" + " INTEGER PRIMARY KEY,"
                        + KEY_SENTENCE + " TEXT,"
                        + KEY_CORRECT_ANSWER + " TEXT,"
                        + KEY_WRONG_ANSWER1 + " TEXT,"
                        + KEY_WRONG_ANSWER2 + " TEXT,"
                        + KEY_WRONG_ANSWER3 + " TEXT" + ")";

        db.execSQL(CREATE_TASKS_FILL_BLANK_TABLE);

        String CREATE_TASKS_MATCH_SYNONYMS_TABLE = "CREATE TABLE " + TABLE_TASKS_MATCH_SYNONYMS + "("
                + KEY_ID_SYN + " INTEGER PRIMARY KEY,"
                + KEY_WORD1 + " TEXT,"
                + KEY_WORD2 + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_MATCH_SYNONYMS_TABLE);

        String CREATE_TASKS_MULTIPLE_CHOICE_TABLE = "CREATE TABLE " + TABLE_TASKS_MULTIPLE_CHOICE + "("
                + KEY_ID_MC + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION + " TEXT,"
                + KEY_CHOICE1 + " TEXT,"
                + KEY_CHOICE2 + " TEXT,"
                + KEY_CHOICE3 + " TEXT,"
                + KEY_ANSWER + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_MULTIPLE_CHOICE_TABLE);

        String CREATE_TASKS_TRANSLATE_SENTENCES_TABLE = "CREATE TABLE " + TABLE_TASKS_TRANSLATE_SENTENCES + "("
                + KEY_ID_TS + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION_TS + " TEXT,"
                + KEY_ANSWER_TS + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TRANSLATE_SENTENCES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_FILL_BLANK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_MATCH_SYNONYMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_MULTIPLE_CHOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS_TRANSLATE_SENTENCES);
        onCreate(db);
    }


    public long addTaskFillBlank(ModelTaskFillBlank taskFillBlank) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SENTENCE, taskFillBlank.getSentence());
        values.put(KEY_CORRECT_ANSWER, taskFillBlank.getCorrect_answer());
        values.put(KEY_WRONG_ANSWER1, taskFillBlank.getWrong_answer1());
        values.put(KEY_WRONG_ANSWER2, taskFillBlank.getWrong_answer2());
        values.put(KEY_WRONG_ANSWER3, taskFillBlank.getWrong_answer3());

        long newRowId = db.insert(TABLE_TASKS_FILL_BLANK, null, values);
        db.close();
        return newRowId;
    }

    public long addTaskMatchSynonyms(ModelTaskMatchSynonyms taskMatchSynonyms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WORD1, taskMatchSynonyms.getWord1());
        values.put(KEY_WORD2, taskMatchSynonyms.getWord2());

        long newRowId = db.insert(TABLE_TASKS_MATCH_SYNONYMS, null, values);
        db.close();
        return newRowId;
    }

    public long addTaskMultipleChoice(ModelTaskMultipleChoice taskMultipleChoice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTION, taskMultipleChoice.getQuestion());
        values.put(KEY_CHOICE1, taskMultipleChoice.getChoice1());
        values.put(KEY_CHOICE2, taskMultipleChoice.getChoice2());
        values.put(KEY_CHOICE3, taskMultipleChoice.getChoice3());
        values.put(KEY_ANSWER, taskMultipleChoice.getAnswer());

        long newRowId = db.insert(TABLE_TASKS_MULTIPLE_CHOICE, null, values);
        db.close();
        return newRowId;
    }

    public long addTaskTranslateSentences(ModelTaskTranslateSentences taskTranslateSentences) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTION_TS, taskTranslateSentences.getQuestion());
        values.put(KEY_ANSWER_TS, taskTranslateSentences.getAnswer());

        long newRowId = db.insert(TABLE_TASKS_TRANSLATE_SENTENCES, null, values);
        db.close();
        return newRowId;
    }

}