package edu.upenn.cis350.projectapp;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CreateAccountInDB extends AsyncTask<URL, String, String> {
    /**
     * Attempts to create a new account as specified in the database.
     *
     * @param urls The URL to use as the HTTP request
     * @return The status of the creation request: "success" if the account was created,
     *         "duplicate" if the account could not be created because the username was already in
     *         use, or "error" if an error was thrown
     */
    protected String doInBackground(URL ... urls) {
        try {
            URL url = urls[0];
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            JSONObject result = new JSONObject(msg);
            String status = result.getString("status");

            if (status.equals("success")) {
                //If the account was successfully created and entered into the database
                return "success";
            } else {
                if (result.getLong("code") == 11000) {
                    //If the account was not created because the username was already in use
                    return "duplicate";
                } else {
                    //If the account was not created because of an error
                    return "error";
                }
            }
        } catch (Exception e) {
            //TODO: something
            return null;
        }
    }
}