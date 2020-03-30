package edu.upenn.cis350.projectapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.net.URL;
import java.net.URLEncoder;

public class ProfileDetailsActivity extends AppCompatActivity {

    String username;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        username = getIntent().getStringExtra("USERNAME");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.housing_selection_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.houses_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Upon hitting the submit button, save the data to the database. If any of the questions do not
     * have an answer, prompts the user to go back through and check that all questions are
     * answered.
     *
     * @param v The View representing the submit button
     */
    public void onProfileDetailsSubmit(View v) {
        //Question a
        RadioGroup grQa = (RadioGroup) findViewById(R.id.qa);
        int selectedQa = grQa.getCheckedRadioButtonId();
        String selectedQaS = null;
        if (selectedQa != -1) {
            RadioButton bt = (RadioButton) findViewById(selectedQa);
            selectedQaS = bt.getText().toString();
        }

        //Question b
        RadioGroup grQb = (RadioGroup) findViewById(R.id.qb);
        int selectedQb = grQb.getCheckedRadioButtonId();
        if (selectedQb != -1) {
            RadioButton bt = (RadioButton) findViewById(selectedQb);
            if (bt.getText().toString().equals("Yes")) {
                selectedQb = 1;
            } else {
                selectedQb = 0;
            }
        }

        //Question c
        String selectedQc = spinner.getSelectedItem().toString();

        //Question 1
        RadioGroup grQ1 = (RadioGroup) findViewById(R.id.q1);
        int selectedQ1 = grQ1.getCheckedRadioButtonId();
        if (selectedQ1 != -1) {
            RadioButton bt = (RadioButton) findViewById(selectedQ1);
            selectedQ1 = bt.getText().toString().charAt(0) - '0';
        }

        //Question 2
        RadioGroup grQ2 = (RadioGroup) findViewById(R.id.q2);
        int selectedQ2 = grQ2.getCheckedRadioButtonId();
        if (selectedQ2 != -1) {
            RadioButton bt = (RadioButton) findViewById(selectedQ2);
            selectedQ2 = bt.getText().toString().charAt(0) - '0';
        }

        //Question 3
        RadioGroup grQ3 = (RadioGroup) findViewById(R.id.q3);
        int selectedQ3 = grQ3.getCheckedRadioButtonId();
        if (selectedQ3 != -1) {
            RadioButton bt = (RadioButton) findViewById(selectedQ3);
            selectedQ3 = bt.getText().toString().charAt(0) - '0';
        }

        //Question 1
        RadioGroup grQ4 = (RadioGroup) findViewById(R.id.q4);
        int selectedQ4 = grQ4.getCheckedRadioButtonId();
        if (selectedQ4 != -1) {
            RadioButton bt = (RadioButton) findViewById(selectedQ4);
            selectedQ4 = bt.getText().toString().charAt(0) - '0';
        }


        TextView warningMessage = (TextView) findViewById(R.id.profile_details_warning_message);

        //If any of the radio button groups do not have a selected button, prompt re-entry of the
        //information. Otherwise, attempt to save the information in the database, updating if
        //needed.
        if (selectedQa == -1 || selectedQb == -1 || selectedQ1 == -1 || selectedQ2 == -1
                || selectedQ3 == -1 || selectedQ4 == -1) {
            warningMessage.setText("Every question must be answered. Please go back through the list and answer the questions you skipped.\n");
        } else {
            String status = null;
            try {
                URL url = new URL("http://10.0.2.2:3000/createPD?username="
                        + URLEncoder.encode(username, "UTF-8") + "&gender="
                        + URLEncoder.encode(selectedQaS, "UTF-8") + "&gi=" + selectedQb
                        + "&ph=" + URLEncoder.encode(selectedQc, "UTF-8") + "&q1="
                        + selectedQ1 + "&q2=" + selectedQ2 + "&q3=" + selectedQ3 + "&q4="
                        + selectedQ4);
                CreateAccountInDB task = new CreateAccountInDB();
                task.execute(url);
                status = task.get();
            } catch (Exception e) {
                //TODO: something
                return;
            }


            if (status.equals("success")) {
                warningMessage.setText("Success!\n\n\n");
                //TODO: advance to next screen
            } else if (status.equals("duplicate")) {
                String statusUpdate = null;
                try {
                    URL url = new URL("http://10.0.2.2:3000/updatePD?username="
                            + URLEncoder.encode(username, "UTF-8") + "&gender="
                            + URLEncoder.encode(selectedQaS, "UTF-8") + "&gi=" + selectedQb
                            + "&ph=" + URLEncoder.encode(selectedQc, "UTF-8") + "&q1="
                            + selectedQ1 + "&q2=" + selectedQ2 + "&q3=" + selectedQ3 + "&q4="
                            + selectedQ4);
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

}
