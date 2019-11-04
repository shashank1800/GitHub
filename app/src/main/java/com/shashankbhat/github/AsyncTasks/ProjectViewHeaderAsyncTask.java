package com.shashankbhat.github.AsyncTasks;

import android.os.AsyncTask;
import com.shashankbhat.github.ProjectView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
public class ProjectViewHeaderAsyncTask extends AsyncTask<String, Void,Void> {

    private String[] viewObject;
    private String shareUrl;
    public static String url;

    @Override
    protected Void doInBackground(String... strings) {

        url = "https://github.com"+strings[0];

        viewObject = new String[4];

        try {
            Document doc = Jsoup.connect(url).get();
            String number_summary = doc.select("[class=num text-emphasized]").text();
            viewObject = number_summary.split(" ");

            shareUrl = doc.select("[class=form-control input-monospace input-sm]").attr("value");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            ProjectView.commits.setText(viewObject[0]);
            ProjectView.branch.setText(viewObject[1]);
            ProjectView.releases.setText(viewObject[2]);
            ProjectView.contributor.setText(viewObject[3]);
        }catch (Exception e){

        }
        ProjectView.shareUrl = shareUrl;

    }
}
