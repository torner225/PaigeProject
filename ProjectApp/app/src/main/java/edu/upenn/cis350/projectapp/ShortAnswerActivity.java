package edu.upenn.cis350.projectapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.net.URLEncoder;

public class ShortAnswerActivity extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = getIntent().getStringExtra("USERNAME");
    }

    /**
     * Upon clicking the submit button, saves the short answers to the database.
     *
     * @param v The View representing the submit button
     */
    public void onSubmitShortAnswer(View v) {
        EditText sa1ET = (EditText) findViewById(R.id.sa1r);
        String sa1S = sa1ET.getText().toString();

        EditText sa2ET = (EditText) findViewById(R.id.sa2r);
        String sa2S = sa2ET.getText().toString();

        EditText sa3ET = (EditText) findViewById(R.id.sa3r);
        String sa3S = sa3ET.getText().toString();

        EditText sa4ET = (EditText) findViewById(R.id.sa4r);
        String sa4S = sa4ET.getText().toString();

        EditText sa5ET = (EditText) findViewById(R.id.sa5r);
        String sa5S = sa5ET.getText().toString();

        EditText sa6ET = (EditText) findViewById(R.id.sa6r);
        String sa6S = sa6ET.getText().toString();

        EditText sa7ET = (EditText) findViewById(R.id.sa7r);
        String sa7S = sa7ET.getText().toString();

        TextView warningMessage = (TextView) findViewById(R.id.short_answer_warning_message);
        String status = null;
        try {
            URL url = new URL("http://10.0.2.2:3000/createSA?username="
                    + URLEncoder.encode(username, "UTF-8") + "&sa1="
                    + URLEncoder.encode(sa1S, "UTF-8") + "&sa2="
                    + URLEncoder.encode(sa2S, "UTF-8")+ "&sa3="
                    + URLEncoder.encode(sa3S, "UTF-8") + "&sa4="
                    + URLEncoder.encode(sa4S, "UTF-8") + "&sa5="
                    + URLEncoder.encode(sa5S, "UTF-8") + "&sa6="
                    + URLEncoder.encode(sa6S, "UTF-8") + "&sa7="
                    + URLEncoder.encode(sa7S, "UTF-8"));
            CreateAccountInDB task = new CreateAccountInDB();
            task.execute(url);
            status = task.get();
        } catch (Exception e) {
            //TODO: something
            return;
        }

        if (status.equals("success")) {
            //If the short answers were saved for the user for the first time
            warningMessage.setText("Success!\n\n\n");
            //TODO: advance to next screen
        } else if (status.equals("duplicate")) {
            //If the short answer responses were already in the database, attempt to save the new
            String statusUpdate = null;
            try {
                URL url = new URL("http://10.0.2.2:3000/updateSA?username="
                        + URLEncoder.encode(username, "UTF-8") + "&sa1="
                        + URLEncoder.encode(sa1S, "UTF-8") + "&sa2="
                        + URLEncoder.encode(sa2S, "UTF-8")+ "&sa3="
                        + URLEncoder.encode(sa3S, "UTF-8") + "&sa4="
                        + URLEncoder.encode(sa4S, "UTF-8") + "&sa5="
                        + URLEncoder.encode(sa5S, "UTF-8") + "&sa6="
                        + URLEncoder.encode(sa6S, "UTF-8") + "&sa7="
                        + URLEncoder.encode(sa7S, "UTF-8"));
                CreateAccountInDB task = new CreateAccountInDB();
                task.execute(url);
                statusUpdate = task.get();
            } catch (Exception e) {
                //TODO: something
                return;
            }

            if (statusUpdate.equals("success")) {
                warningMessage.setText("Successful update!\n\n\n");
                //TODO: advance to next screen
            } else {
                //TODO: error handling
            }

        } else {
            //TODO: error handling
        }
    }
}
