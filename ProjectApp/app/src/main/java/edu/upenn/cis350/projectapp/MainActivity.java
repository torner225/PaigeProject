package edu.upenn.cis350.projectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Upon login button click, attempts to log in. Checks if password is correct, if account with
     * the given username exists, and if the account has been approved by an administrator.
     *
     * @param v The View representing the login button
     */
    public void onLoginButtonClick(View v) {
        EditText usernameET = (EditText) findViewById(R.id.usernameField);
        String username = usernameET.getText().toString();
        EditText passwordET = (EditText) findViewById(R.id.passwordField);
        String password = passwordET.getText().toString();
        String passwordDB = null;  //The password associated with the username in the database
        String approvalDB = null;  //The approval status of the user in the database

        //Access the database
        try {
            URL url = new URL("http://10.0.2.2:3000/getPassword?username="
                    + URLEncoder.encode(username, "UTF-8"));
            AccessLoginInformation task = new AccessLoginInformation();
            task.execute(url);
            passwordDB = task.get()[0];
            approvalDB = task.get()[1];
        } catch (Exception e) {
            //TODO: something
            return;
        }

        TextView promptRentry = (TextView) findViewById(R.id.rentryPrompt);
        if ("-1".equals(approvalDB)) {
            //If an account with the entered username does not exist, inform user
            promptRentry.setText("An account with this username does not exist.");
        } else if ("-999".equals(approvalDB)) {
            //If an error has occurred (general), inform the user and prompt re-entry
            promptRentry.setText("An error has occurred. Please try again.");
        } else if (password.equals(passwordDB) && "1".equals(approvalDB)) {
            //If the password is correct and the account has been approved, proceed
            try {
                URL url = new URL("http://10.0.2.2:3000/getPD?username="
                        + URLEncoder.encode(username, "UTF-8"));
                AccessProfileDetails task = new AccessProfileDetails();
                task.execute(url);
                JSONObject result = task.get();
                if (result != null && result.length() > 0) {
                    //If the user has filled out their basic profile information, go to home page
                    //TODO: go to home page
                } else {
                    //If the user has not filled out their basic profile, make them do so
                    Intent i = new Intent(this, ProfileDetailsActivity.class);
                    i.putExtra("USERNAME", username);
                    startActivity(i);
                }
            } catch (Exception e) {
                //TODO: something;
            }
        } else if (password.equals(passwordDB) && "0".equals(approvalDB)){
            //If the password is correct and the account has not been approved, inform user
            promptRentry.setText("Your account has not yet been approved. Please check back later.");
        } else {
            //If the password is incorrect, prompt re-entry
            promptRentry.setText("The username or password was incorrect. Please try again.");
        }
    }

    /**
     * Upon clicking on the button to create a new account, starts the Activity to do so
     *
     * @param v The View representing the "Create an Account" button
     */
    public void onCreateButtonClick(View v) {
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }

    public void testSkip(View v) {
        Intent i = new Intent(this, ProfileDetailsActivity.class);
        i.putExtra("USERNAME", "test1");
        startActivity(i);
    }

    public void testSkipTwo(View v) {
        Intent i = new Intent(this, ShortAnswerActivity.class);
        i.putExtra("USERNAME", "test1");
        startActivity(i);
    }
}
