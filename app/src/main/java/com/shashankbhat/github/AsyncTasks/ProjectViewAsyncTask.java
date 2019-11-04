package com.shashankbhat.github.AsyncTasks;

import android.os.AsyncTask;
import com.shashankbhat.github.Objects.ProjectViewObject;
import com.shashankbhat.github.ProjectView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectViewAsyncTask extends AsyncTask<String, Void,Void> {

    private ArrayList<ProjectViewObject> projectViewObjects;
    public static String url;

    @Override
    protected Void doInBackground(String... strings) {
        url = "https://github.com"+strings[0];

        projectViewObjects = new ArrayList<>();
        System.out.println(url);

        try {
            Document doc = Jsoup.connect(url).get();
            Elements projectObject = doc.select("[class=js-navigation-item]");

            for(Element ele : projectObject) {

                String file_type = ele.select("svg").attr("aria-label");
                String file_name = ele.select("[class=js-navigation-open]").text();
                String commit_comment = ele.select("[class=link-gray]").text();
                String time = ele.select("time-ago").text();
                String clickUrl = ele.select("[class=js-navigation-open]").attr("href");

                System.out.println("text "+clickUrl);
                projectViewObjects.add(new ProjectViewObject(file_type,file_name,commit_comment,time,clickUrl));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ProjectView.projectViewAdapter.setRepositoryProjectObjects(projectViewObjects);
    }
}