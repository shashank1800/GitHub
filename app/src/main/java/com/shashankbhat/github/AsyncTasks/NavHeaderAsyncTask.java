package com.shashankbhat.github.AsyncTasks;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shashankbhat.github.Login;
import com.shashankbhat.github.MainActivity;
import com.shashankbhat.graph.DrawGraph;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.shashankbhat.github.MainActivity.graphView;
import static com.shashankbhat.github.Utils.Constants.USERNAME;


public class NavHeaderAsyncTask extends AsyncTask<Void, Void, String[]> {

    String followersAndFollowing[];
    ArrayList<Integer> graphArray;
    @Override
    protected String[] doInBackground(Void... voids) {

        String headerObject[] = new String[3];
        graphArray = new ArrayList<>();

        String url = "https://github.com/"+ Login.sp.getString(USERNAME,"") +"?tab=repositories";
        String graphUrl = "https://github.com/"+ Login.sp.getString(USERNAME,"");

        try {
            Document doc = Jsoup.connect(url).get();
            Elements username = doc.getElementsByClass("p-name");
            Elements nickname = doc.getElementsByClass("p-nickname");
            Elements avatar = doc.getElementsByClass("u-photo");
            followersAndFollowing = doc.select("[class=Counter hide-lg hide-md hide-sm]").text().split(" ");

            headerObject[0] = username.text();
            headerObject[1] = nickname.text();
            headerObject[2] = avatar.attr("href");

            //Graph
            Document document = Jsoup.connect(graphUrl).get();
            Elements calender = document.select("[data-count]");
            for(Element e : calender)
                graphArray.add(Integer.parseInt(e.attr("data-count")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return headerObject;
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);

        try{
            MainActivity.mUsername.setText(s[0]);
            MainActivity.mNickname.setText(s[1]);
            Glide.with(MainActivity.context).load(s[2]).into(MainActivity.mAvatar);
            MainActivity.mFollowers.setText(followersAndFollowing[3]);
            MainActivity.mFollowing.setText(followersAndFollowing[4]);

            DrawGraph drawGraph = new DrawGraph();
            drawGraph.setBackgroundColor("#F1F1FF");
            drawGraph.setLineColor("#18004c");
            drawGraph.setLineWidth(25f);
            drawGraph.makeUnitLineInvisible(true);

            for(int i=0;i<graphArray.size();i++)
                drawGraph.addPoint(i, graphArray.get(i));

            BitmapDrawable drawable = drawGraph.drawGraph();
            graphView.setBackground(drawable);
        }
        catch (Exception ie){ }
    }
}
