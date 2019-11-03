package com.shashankbhat.github.AsyncTasks;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.shashankbhat.github.Login;
import com.shashankbhat.graph.DrawGraph;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static com.shashankbhat.github.MainActivity.graphView;
import static com.shashankbhat.github.Utils.Constants.USERNAME;

public class NavGraphAsyncTask extends AsyncTask<Void, Void, int[]> {
    @Override
    protected int[] doInBackground(Void... voids) {

        int [] graphArray = new int[366];

        String url = "https://github.com/"+ Login.sp.getString(USERNAME,"");

        try {
            Document doc = Jsoup.connect(url).get();
            Elements calender = doc.select("[data-count]");
            int i=1;
            for(Element e : calender) {
                graphArray[i] = Integer.parseInt(e.attr("data-count"));
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return graphArray;
    }

    @Override
    protected void onPostExecute(int[] graphArray) {
        super.onPostExecute(graphArray);

        DrawGraph drawGraph = new DrawGraph();
        drawGraph.setBackgroundColor("#ffffff");
        drawGraph.setLineColor("#62a970");
        drawGraph.setLineWidth(20f);

        for(int i=0;i<366;i++)
            drawGraph.addPoint(i, graphArray[i]);

        BitmapDrawable drawable = drawGraph.drawGraph();
        graphView.setBackground(drawable);
    }
}
