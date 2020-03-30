package edu.upenn.cis350.projectapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessLoginInformation extends AsyncTask<URL, String, String[]> {
    /**
     * Accesses the login information associated with a particular username within the database.
     *
     * @param urls The URL to use as the HTTP request
     * @return A String array of length two. The first element contains the password associated
     *         with the username, if it exists, or null otherwise. The second element contains a
     *         code to indicate the status of the account
     */
    protected String[] doInBackground(URL ... urls) {
        String[] out = new String[2];
        try {
            URL url = urls[0];
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            JSONObject result = new JSONObject(msg);
            String status = result.getString("status");

            if (status.equals("success")) {
                //If the password for the username was successfully obtained
                out[0] = result.getString("password");
                if (result.getBoolean("approved")) {
                    //If the account has been approved by an administrator
                    out[1] = "1";
                } else {
                    //If the account has not been approved by an administrator
                    out[1] = "0";
                }
                return out;
            } else if (status.equals("no account")) {
                //If no account with the given name was found
                out[0] = null;
                out[1] = "-1";
                return out;
            } else {
                //TODO: something
                out[0] = null;
                out[1] = "-999";
                return out;
            }
        } catch (Exception e) {
            //TODO: something
            out[0] = null;
            out[1] = "-999";
            return out;
        }
    }
}
