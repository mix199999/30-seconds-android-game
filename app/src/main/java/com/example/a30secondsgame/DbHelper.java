package com.example.a30secondsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a30secondsgame.Models.Models;
import com.example.a30secondsgame.Models.Models.*;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasksManagerDb";

    private static final String KEY_ID = "id";
    private static final String KEY_TASK_ID = "task_id";

    private static final String KEY_QUESTION = "question_text";

    private static final String KEY_ANSWER_TEXT = "answer_text";

    private static final String KEY_IS_CORRECT= "is_correct";

    private static final String KEY_FIRST_ANSWER = "answer1";
    private static final String KEY_SECOND_ANSWER = "answer2";
    private static final String KEY_ANSWER = "answer";

    private static final String KEY_LANGUAGE_CODE = "language_code";
    private static final String KEY_LANGUAGE_ID = "language_id";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private void createFillInTheBlanksTables(SQLiteDatabase db)
    {
        String CREATE_ANSWERS_TASKS_FILL_BLANK_TABLE =  "CREATE TABLE IF NOT EXISTS " +
                "ANSWERS_TASKS_FILL_IN_THE_BLANKS" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " INTEGER,"
                + KEY_LANGUAGE_ID + " INTEGER,"
                + KEY_ANSWER_TEXT + " TEXT,"
                + KEY_IS_CORRECT + " INTEGER"
                + ")";

        db.execSQL(CREATE_ANSWERS_TASKS_FILL_BLANK_TABLE);


        String CREATE_QUESTIONS_TASKS_FILL_BLANK_TABLE =  "CREATE TABLE IF NOT EXISTS " +
                "QUESTIONS_TASKS_FILL_IN_THE_BLANK" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " TEXT,"
                + KEY_LANGUAGE_ID + " INTEGER,"
                + KEY_QUESTION + " TEXT"
                +  ")";

        db.execSQL(CREATE_QUESTIONS_TASKS_FILL_BLANK_TABLE);

    }


    private void createTranslateSentencesTable(SQLiteDatabase db)
    {
        String CREATE_ANSWERS_TASKS_TRANSLATE_SENTENCES =  "CREATE TABLE IF NOT EXISTS " +
                "ANSWERS_TASKS_TRANSLATE_SENTENCES" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " INTEGER,"
                + KEY_LANGUAGE_ID + " INTEGER,"
                + KEY_ANSWER + " TEXT"
                + ")";

        db.execSQL(CREATE_ANSWERS_TASKS_TRANSLATE_SENTENCES);

    }



    private void createMultipleChoiceTables(SQLiteDatabase db)
    {
        String CREATE_ANSWERS_TASKS_MULTIPLE_CHOICE =  "CREATE TABLE IF NOT EXISTS " +
                "ANSWERS_TASKS_MULTIPLE_CHOICE" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " INTEGER,"
                + KEY_LANGUAGE_ID + " INTEGER,"
                + KEY_ANSWER_TEXT + " TEXT,"
                + KEY_IS_CORRECT + " INTEGER"
                + ")";
        db.execSQL(CREATE_ANSWERS_TASKS_MULTIPLE_CHOICE);




        String CREATE_QUESTIONS_TASKS_MULTIPLE_CHOICE =  "CREATE TABLE IF NOT EXISTS " +
                "QUESTIONS_TASKS_MULTIPLE_CHOICE" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " TEXT,"
                + KEY_LANGUAGE_ID + " INTEGER,"
                + KEY_QUESTION + " TEXT"
                +  ")";

        db.execSQL(CREATE_QUESTIONS_TASKS_MULTIPLE_CHOICE);


    }


    private void createMatchSynonymsTable(SQLiteDatabase db)
    {
        String CREATE_ANSWERS_TASKS_MATCH_SYNONYMS =  "CREATE TABLE IF NOT EXISTS " +
                "ANSWERS_TASKS_MATCH_SYNONYMS" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_ID + " INTEGER,"
                + KEY_LANGUAGE_ID + " INTEGER,"
                + KEY_FIRST_ANSWER + " TEXT,"
                + KEY_SECOND_ANSWER + " TEXT"
                + ")";

        db.execSQL(CREATE_ANSWERS_TASKS_MATCH_SYNONYMS);

    }

    private void createLanguagesTable(SQLiteDatabase db)
    {
        String CREATE_LANGUAGES =  "CREATE TABLE IF NOT EXISTS " +
                "LANGUAGES" + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_LANGUAGE_CODE + " TEXT"
                + ")";

        db.execSQL(CREATE_LANGUAGES);

    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        createMatchSynonymsTable(db);
        createFillInTheBlanksTables(db);
        createTranslateSentencesTable(db);
        createMultipleChoiceTables(db);
        createLanguagesTable(db);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LANGUAGES" );
        db.execSQL("DROP TABLE IF EXISTS ANSWERS_TASKS_TRANSLATE_SENTENCES" );
        db.execSQL("DROP TABLE IF EXISTS QUESTIONS_TASKS_FILL_IN_THE_BLANK" );
        db.execSQL("DROP TABLE IF EXISTS ANSWERS_TASKS_MULTIPLE_CHOICE" );
        db.execSQL("DROP TABLE IF EXISTS ANSWERS_TASKS_MATCH_SYNONYMS" );
        db.execSQL("DROP TABLE IF EXISTS QUESTIONS_TASKS_MULTIPLE_CHOICE" );
        db.execSQL("DROP TABLE IF EXISTS ANSWERS_TASKS_FILL_IN_THE_BLANKS" );

        onCreate(db);
    }


    public long addAnswerTaskTranslateSentences(AnswerTaskTranslateSentences taskTranslateSentences) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TASK_ID, taskTranslateSentences.getTaskId());
        values.put(KEY_LANGUAGE_ID,taskTranslateSentences.getLanguageId());
        values.put(KEY_ANSWER, taskTranslateSentences.getAnswer());

        long newRowId = db.insert("ANSWERS_TASKS_TRANSLATE_SENTENCES", null, values);
        db.close();
        return newRowId;
    }

    public long addAnswerTaskMultipleChoice(AnswerTaskMultipleChoice taskMultipleChoice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TASK_ID, taskMultipleChoice.getTaskId());
        values.put(KEY_LANGUAGE_ID,taskMultipleChoice.getLanguageId());
        values.put(KEY_ANSWER_TEXT, taskMultipleChoice.getAnswer_text());
        values.put(KEY_IS_CORRECT, taskMultipleChoice.isCorrect());

        long newRowId = db.insert("ANSWERS_TASKS_MULTIPLE_CHOICE", null, values);
        db.close();
        return newRowId;
    }

    public long addQuestionTaskMultipleChoice(QuestionTaskMultipleChoice taskMultipleChoice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TASK_ID, taskMultipleChoice.getTaskId());
        values.put(KEY_LANGUAGE_ID,taskMultipleChoice.getLanguageId());
        values.put(KEY_QUESTION, taskMultipleChoice.getQuestionText());

        long newRowId = db.insert("QUESTIONS_TASKS_MULTIPLE_CHOICE", null, values);
        db.close();
        return newRowId;
    }

    public long addAnswerTaskMatchSynonyms(AnswerTaskMatchSynonyms taskMatchSynonyms ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_TASK_ID, taskMatchSynonyms.getTaskId());
        values.put(KEY_LANGUAGE_ID,taskMatchSynonyms.getLanguageId());
        values.put(KEY_FIRST_ANSWER, taskMatchSynonyms.getAnswer1());
        values.put(KEY_SECOND_ANSWER, taskMatchSynonyms.getAnswer2());

        long newRowId = db.insert("ANSWERS_TASKS_MATCH_SYNONYMS", null, values);
        db.close();
        return newRowId;
    }



    public long addAnswerTaskFillInTheBlanks(AnswerTaskFillInTheBlanks task ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TASK_ID, task.getTaskId());
        values.put(KEY_LANGUAGE_ID,task.getLanguageId());
        values.put(KEY_ANSWER_TEXT, task.getAnswer_text());
        values.put(KEY_IS_CORRECT, task.isCorrect());


        long newRowId = db.insert("ANSWERS_TASKS_FILL_IN_THE_BLANKS", null, values);
        db.close();
        return newRowId;
    }

    public long addQuestionTaskFillInTheBlanks(QuestionTaskFillInTheBlanks task ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TASK_ID, task.getTaskId());
        values.put(KEY_LANGUAGE_ID,task.getLanguageId());
        values.put(KEY_QUESTION, task.getQuestionText());


        long newRowId = db.insert("QUESTIONS_TASKS_FILL_IN_THE_BLANK", null, values);
        db.close();
        return newRowId;
    }



    public long addLanguage(Language task ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LANGUAGE_CODE, task.getLanguageCode());
        long newRowId = db.insert("LANGUAGES", null, values);
        db.close();
        return newRowId;
    }


    public Cursor getTable(String tableName, String languageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName + " WHERE language_id = "+ languageId, null);
    }
//todo popraw to debilu po get zwraca tablice tablic
    public Cursor[] getAnswersTasksFillInTheBlanksTable(String firstLanguageId, String secondLanguageId) {
        Cursor[] table = new Cursor[2];
        table[0] = getTable("ANSWERS_TASKS_FILL_IN_THE_BLANKS", firstLanguageId);
        table[1]= getTable("ANSWERS_TASKS_FILL_IN_THE_BLANKS", secondLanguageId);
        return table;
    }

   /* public String[][] saveAnswersTasksFillInTheBlanks(String firstLanguageId, String secondLanguageId)
    {
        Cursor[] tables = getAnswersTasksFillInTheBlanksTable(firstLanguageId, secondLanguageId);
        String[][] stringArray = new String[2][];
        for (int i = 0; i < tables.length; i++) {
            ArrayList<String> rows = new ArrayList<>();
            if (tables[i] != null && tables[i].moveToFirst()) {
                do {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < tables[i].getColumnCount(); j++) {
                        row.append(tables[i].getString(j)).append(",");
                    }
                    rows.add(row.toString());
                } while (tables[i].moveToNext());
            }
            stringArray[i] = rows.toArray(new String[0]);
        }
        return stringArray;
    }*/

    public Cursor getQuestionsTasksFillInTheBlanksTable(String LanguageId) {

     return getTable("QUESTIONS_TASKS_FILL_IN_THE_BLANK", LanguageId);
    }
    public Cursor getAnswersTasksFillInTheBlanksTable(String LanguageId) {

        return getTable("ANSWERS_TASKS_FILL_IN_THE_BLANKS", LanguageId);
    }

    public List<Models.QuestionTaskFillInTheBlanks> saveQuestionsTasksFillInTheBlanksToObjectArray(String languageId)
    {
        Cursor table = getQuestionsTasksFillInTheBlanksTable(languageId);
        List<QuestionTaskFillInTheBlanks> questions = new ArrayList<>();
        if(table != null && table.moveToFirst())
        {
            do{
                questions.add(new QuestionTaskFillInTheBlanks(
                        table.getInt(0),    // id
                        table.getInt(1),    // taskId
                        table.getInt(2),    // languageId
                        table.getString(3) // question_text
                ));

            }while (table.moveToNext());
        }
        return questions;
    }



    public List<Models.AnswerTaskFillInTheBlanks> saveAnswersTasksFillInTheBlanksToObjectArray(String languageId)
    {
        Cursor table = getAnswersTasksFillInTheBlanksTable(languageId);
        List<AnswerTaskFillInTheBlanks> answers = new ArrayList<>();
        if(table != null && table.moveToFirst())
        {
            do{
                answers.add(new AnswerTaskFillInTheBlanks(
                        table.getInt(0),    // id
                        table.getInt(1),    // taskId
                        table.getInt(2),    // languageId
                        table.getString(3), // answer_text
                        table.getInt(4) //is Correct
                ));

            }while (table.moveToNext());
        }
        return answers;
    }

    public Cursor getAnswersTasksTranslateSentencesTable(String firstLanguageId, String secondLanguageId) {


        return getTable("ANSWERS_TASKS_TRANSLATE_SENTENCES", secondLanguageId);


    }
    public String[] saveTaskTranslateSentencesToStringArray(String firsLanguageId, String secondLanguageId)
    {
        Cursor table = getAnswersTasksTranslateSentencesTable(firsLanguageId, secondLanguageId);
        String[] stringArray;

            ArrayList<String> rows = new ArrayList<>();
            if (table != null && table.moveToFirst()) {
                do {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        row.append(table.getString(j)).append(",");
                    }
                    rows.add(row.toString());
                } while (table.moveToNext());
            }
            stringArray = rows.toArray(new String[0]);

        return stringArray;
    }

    public Cursor getAnswersTasksMultipleChoiceTable(String languageId) {

        return   getTable("ANSWERS_TASKS_MULTIPLE_CHOICE",languageId);

    }
    public List<AnswerTaskMultipleChoice> saveAnswersTasksMultipleChoiceToObjectArray(String languageId) {
         Cursor table = getAnswersTasksMultipleChoiceTable(languageId);
        List<AnswerTaskMultipleChoice> answers = new ArrayList<>();
        if(table != null && table.moveToFirst())
        {
            do{
                answers.add(new AnswerTaskMultipleChoice(
                        table.getInt(0),    // id
                        table.getInt(1),    // taskId
                        table.getInt(2),    // languageId
                        table.getString(3), // answerText
                        table.getInt(4) // isCorrect
                ));

            }while (table.moveToNext());
        }

        return answers;
    }


    public String[] saveAnswersTasksMultipleChoiceToStringArray(String firstLanguage)
    {
        Cursor table = getAnswersTasksMultipleChoiceTable(firstLanguage);
        String[] stringArray = null;

            ArrayList<String> rows = new ArrayList<>();
            if (table != null && table.moveToFirst()) {
                do {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        row.append(table.getString(j)).append(",");
                    }
                    rows.add(row.toString());
                } while (table.moveToNext());

            stringArray = rows.toArray(new String[0]);
        }
        return stringArray;
    }

    public Cursor getQuestionsTasksMultipleChoiceTable(String languageId ) {

        return getTable("QUESTIONS_TASKS_MULTIPLE_CHOICE", languageId);

}
    public List<QuestionTaskMultipleChoice> saveQuestionsTasksMultipleChoiceToObjects(String languageId) {
        Cursor table = getQuestionsTasksMultipleChoiceTable(languageId);
        List<QuestionTaskMultipleChoice> questions = new ArrayList<>();


            if (table != null && table.moveToFirst()) {
                do {
                    questions.add(new QuestionTaskMultipleChoice(
                            table.getInt(0),    // id
                            table.getInt(1),    // taskId
                            table.getInt(2),    // languageId
                            table.getString(3)  // questionText
                    ));
                } while (table.moveToNext());
            }

        return questions;
    }

public String[]saveTasksMatchSynonymsToStringArray(String firstLanguageId)
{
    Cursor cursor = getAnswersTasksMatchSynonymsTable(firstLanguageId);
    String[] stringArray;

        ArrayList<String> rows = new ArrayList<>();
        if(cursor != null &&  cursor.moveToFirst())
        {
            do{
                StringBuilder row = new StringBuilder();
                for( int j = 0; j<cursor.getColumnCount();j++)
                {
                    row.append(cursor.getString(j)).append(",");
                }
                rows.add(row.toString());
            }while (cursor.moveToNext());
        }
        stringArray = rows.toArray(new String[0]);

    return stringArray;
}
    public Cursor getAnswersTasksMatchSynonymsTable(String firstLanguageId) {


        return getTable("ANSWERS_TASKS_MATCH_SYNONYMS", firstLanguageId);

    }

    public Cursor getLanguagesTable() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM languages";
        return db.rawQuery(query, null);
    }

    public Cursor getAnswersTasksMatchSynonymsTable(int languageId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT answer1, answer2 FROM ANSWERS_TASKS_MATCH_SYNONYMS WHERE language_id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(languageId)});
    }


}