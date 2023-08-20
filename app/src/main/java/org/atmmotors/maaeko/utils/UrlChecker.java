package org.atmmotors.maaeko.utils;

import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker extends AsyncTask<String, Void, Boolean> {

    private OnUrlCheckListener listener;
    private Context context;

    public UrlChecker(OnUrlCheckListener listener, Context context) {
        this.listener = listener;
        this.context = context;

    }

    @Override
    protected Boolean doInBackground(String... params) {
        String urlToCheck = params[0];
        try {
            URL url = new URL(urlToCheck);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000); // Set connection timeout to 5 seconds
            connection.connect();

            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (listener != null) {
            listener.onUrlCheckCompleted(result, context);
        }
    }

    public interface OnUrlCheckListener {
        void onUrlCheckCompleted(boolean isWorking, Context context);
    }
}
