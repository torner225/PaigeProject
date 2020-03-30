package edu.upenn.cis350.projectapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessProfileDetails extends AsyncTask<URL, String, JSONObject> {
    protected JSONObject doInBackground(URL... urls) {
        String status = "";
        JSONObject result = null;
        try {
            URL url = urls[0];
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            result = new JSONObject(msg);
            status = result.getString("status");
        } catch (Exception e) {
            //TODO: exception handling
        }

        if (status.equals("success")) {
            return result;
        } else if (status.equals("no profile details")) {
            return new JSONObject();
        } else {
            //TODO: figure this out
            return null;
        }
    }
}
