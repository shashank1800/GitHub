package com.shashankbhat.github.AsyncTasks;

import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.shashankbhat.github.Login;
import com.shashankbhat.github.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static com.shashankbhat.github.Utils.Constants.USERNAME;


public class NavHeaderAsyncTask extends AsyncTask<Void, Void, String[]> {
    @Override
    protected String[] doInBackground(Void... voids) {

        String headerObject[] = new String[3];

        String url = "https://github.com/"+ Login.sp.getString(USERNAME,"") +"?tab=repositories";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements username = doc.getElementsByClass("p-name");
            System.out.println("text : " + username.text());

            Elements nickname = doc.getElementsByClass("p-nickname");
            System.out.println("text : " + nickname.text());

            Elements avatar = doc.getElementsByClass("u-photo");
            System.out.println("text : " + avatar.attr("href"));

            headerObject[0] = username.text();
            headerObject[1] = nickname.text();
            headerObject[2] = avatar.attr("href");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return headerObject;
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);
        MainActivity.mUsername.setText(s[0]);
        MainActivity.mNickname.setText(s[1]);
        Glide.with(MainActivity.context).load(s[2]).into(MainActivity.mAvatar);
    }
}
