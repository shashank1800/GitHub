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


public class NavHeaderAsyncTask extends AsyncTask<Void, Void, Void> {

    ArrayList<Integer> graphArray;
    @Override
    protected Void doInBackground(Void... voids) {

        graphArray = new ArrayList<>();

        String graphUrl = "https://github.com/"+ Login.sp.getString(USERNAME,"");

        try {
            //Graph
            Document document = Jsoup.connect(graphUrl).get();
            Elements calender = document.getElementsByTag("rect");
            for(Element e : calender)
                graphArray.add(Integer.parseInt(e.attr("data-count")));

        } catch (IOException e) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try{
            DrawGraph drawGraph = new DrawGraph();
            drawGraph.setBackgroundColor("#cdf4d3");
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
