package com.example.a30secondsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import net.sourceforge.jtds.jdbc.JtdsConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHelper {

    private static final String SERVER_ADDRESS = "10.0.2.2";
    private static final String PORT = "1433";
    private static final String DATABASE_NAME = "ANDROID_GAME_PROJECT";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "user";

    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://" + SERVER_ADDRESS + ":" + PORT +
            ";databaseName=" + DATABASE_NAME +
            ";user=" + USERNAME +
            ";password=" + PASSWORD +
            ";ssl=request";

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (ClassNotFoundException | SQLException e) {
            Log.e("DbHelper", "Error while connecting to the database", e);
        }
        return connection;
    }

    public interface CheckUserCallback {
        void onUserChecked(boolean userExists);
    }

    public void checkUser(User user, CheckUserCallback callback) {
        new CheckUserTask(callback).execute(user);
    }

    private class CheckUserTask extends AsyncTask<User, Void, Boolean> {
        private final CheckUserCallback callback;

        CheckUserTask(CheckUserCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(User... users) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            User user = users[0];

            try (Connection connection = getConnection()) {
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getPassword());

                    ResultSet resultSet = statement.executeQuery();

                    boolean userExists = resultSet.next();
                    statement.close();
                    return userExists;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean userExists) {
            callback.onUserChecked(userExists);
        }
    }

    public interface AddUserCallback {
        void onUserAdded(boolean success);
    }

    public void addUser(User user, AddUserCallback callback) {
        new AddUserTask(callback).execute(user);
    }

    private class AddUserTask extends AsyncTask<User, Void, Boolean> {
        private final AddUserCallback callback;

        AddUserTask(AddUserCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(User... users) {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            User user = users[0];

            try (Connection connection = getConnection()) {
                if (connection != null) {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getPassword());
                    statement.setString(3, user.getEmail());

                    int rowsAffected = statement.executeUpdate();
                    statement.close();
                    return rowsAffected > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            callback.onUserAdded(success);
        }
    }
}