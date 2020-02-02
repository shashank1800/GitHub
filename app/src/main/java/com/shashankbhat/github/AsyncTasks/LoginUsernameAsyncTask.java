package com.shashankbhat.github.AsyncTasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.shashankbhat.github.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static com.shashankbhat.github.Login.sp;
import static com.shashankbhat.github.Utils.Constants.USERNAME;


/**
 * Created by SHASHANK BHAT on 11-Jan-20.
 * Shashank Bhat
 * shashankbhat1800@gmail.com
 */
public class LoginUsernameAsyncTask extends AsyncTask<Void, Void, Void> {

    private boolean isValid = false;
    private String username;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SpinKitView spinKitView;

    public LoginUsernameAsyncTask(Context context,String username,SpinKitView spinKitView){
        this.context = context;
        this.username = username;
        this.spinKitView = spinKitView;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String url = "https://github.com/"+ username;

        try {
            Document doc = Jsoup.connect(url).get();
            String username = doc.getElementsByClass("p-name").text();
            if(!username.isEmpty())
                isValid = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        spinKitView.setVisibility(View.GONE);
        if(isValid){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(USERNAME, username);
            editor.apply();
            editor.commit();
            context.startActivity(new Intent(context, MainActivity.class));
            ((Activity)context).finish();
        }else{
            Toast.makeText(context, "Invalid username", Toast.LENGTH_SHORT).show();
        }

    }

}
