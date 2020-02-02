package com.shashankbhat.github.AsyncTasks;

import android.os.AsyncTask;
import android.view.View;

import com.shashankbhat.github.ViewCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.shashankbhat.github.ViewCode.codeView;
import static com.shashankbhat.github.ViewCode.text_view;

public class CodeDownloadAsyncTask extends AsyncTask<String, Void, Void> {
    private StringBuilder code = new StringBuilder();
    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line!=null){
                code.append(line).append(" \n");
                line = bufferedReader.readLine();
            }

        } catch (Exception ignored) { }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ViewCode.spin_kit.setVisibility(View.GONE);

        try {
            codeView.setCode(code.toString());
        }catch (Exception exc){
            codeView.setVisibility(View.GONE);

            text_view.setText(code.toString());
            text_view.setVisibility(View.VISIBLE);
        }
    }
}
