package com.shashankbhat.github.AsyncTasks;

import android.os.AsyncTask;

import com.shashankbhat.github.Login;
import com.shashankbhat.github.MainActivity;
import com.shashankbhat.github.Objects.RepositoryProject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.shashankbhat.github.Utils.Constants.USERNAME;

public class RepoAsyncTask extends AsyncTask<Void, Void, ArrayList<RepositoryProject>> {

    private ArrayList<RepositoryProject> repositoryProjects;

    @Override
    protected ArrayList<RepositoryProject> doInBackground(Void... voids) {

        String url = "https://github.com/"+ Login.sp.getString(USERNAME,"") +"?tab=repositories";
        repositoryProjects = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements projects = doc.select("[id=user-repositories-list]").select("li");

            System.out.println("Projects" + projects.size());
            for (Element src : projects) {
//                System.out.println("Projects Name: " + src.select("[itemprop=name codeRepository]").text());
//                System.out.println("Time : " + src.select("relative-time").text());
//                System.out.println("Lang : " + src.select("[itemprop=programmingLanguage]").text());

                repositoryProjects.add(new RepositoryProject(src.select("[itemprop=name codeRepository]").text(),
                        src.select("[itemprop=programmingLanguage]").text(),
                        "Updated "+src.select("relative-time").text()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return repositoryProjects;
    }

    @Override
    protected void onPostExecute(ArrayList<RepositoryProject> repositoryProjects) {
        super.onPostExecute(repositoryProjects);
        MainActivity.repoAdapter.setRepositoryProjectObjects(repositoryProjects);
    }
}