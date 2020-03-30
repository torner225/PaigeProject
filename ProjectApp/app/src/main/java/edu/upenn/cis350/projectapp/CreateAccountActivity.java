package edu.upenn.cis350.projectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.net.URL;
import java.net.URLEncoder;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    /**
     * Upon clicking the button to submit a new account request.
     *
     * @param v The View representing the submit button
     */
    public void onSubmitNewAccountButtonClick (View v) {
        EditText emailET = (EditText) findViewById(R.id.newEmailField);
        String email = emailET.getText().toString();
        EditText usernameET = (EditText) findViewById(R.id.newUsernameField);
        String username = usernameET.getText().toString();
        EditText passwordET = (EditText) findViewById(R.id.newPasswordField);
        String password = passwordET.getText().toString();
        String status = null;

        //Access the database and attempt to create a new account
        try {
            URL url = new URL("http://10.0.2.2:3000/createAcc?email="
                    + URLEncoder.encode(email, "UTF-8") + "&username="
                    + URLEncoder.encode(username, "UTF-8") + "&password="
                    + URLEncoder.encode(password, "UTF-8"));
            CreateAccountInDB task = new CreateAccountInDB();
            task.execute(url);
            status = task.get();
        } catch (Exception e) {
            //TODO: something
            return;
        }

        TextView statusPrompt = (TextView) findViewById(R.id.statusPrompt);
        if (status.equals("success")) {
            //If the account was successfully created, inform the user
            statusPrompt.setText("Success! Your account request has been sent to an administrator. You will be able to login upon approval.");
        } else if (status.equals("duplicate")) {
            //If the account could not be created because the username was in use, inform the user
            statusPrompt.setText("The entered username is already in use. Please try another.");
        } else {
            //If an error was thrown, inform the user and prompt re-entry
            statusPrompt.setText("An error has occurred. Please try again.");
        }
    }
}
