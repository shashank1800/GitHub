package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shashankbhat.github.Adapter.ProjectViewAdapter;
import com.shashankbhat.github.AsyncTasks.ProjectViewAsyncTask;
import com.shashankbhat.github.AsyncTasks.ProjectViewHeaderAsyncTask;
import com.shashankbhat.github.Objects.ProjectViewObject;
import com.shashankbhat.github.Utils.Constants;

import java.util.ArrayList;

import static com.shashankbhat.github.Utils.Constants.USERNAME;

public class ProjectView extends AppCompatActivity {

    public static TextView commits,branch,releases,contributor;
    public static String shareUrl;
    private Button share_button;

    public static Context context;

    private RecyclerView projectViewRV;
    private LinearLayoutManager lLayoutManager;

    public static ProjectViewAdapter projectViewAdapter;
    public static ArrayList<ProjectViewObject> projectViewObjects;
    public static ArrayList<Object> stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        Bundle bundle = getIntent().getExtras();
        String projectName = bundle.getString(Constants.PROJECT_NAME);

        String urlOfThisPage = "/"+Login.sp.getString(USERNAME,"")+"/"+projectName;

        commits = findViewById(R.id.commits);
        branch = findViewById(R.id.branch);
        releases = findViewById(R.id.releases);
        contributor = findViewById(R.id.contributor);

        share_button = findViewById(R.id.share_button);

        lLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        projectViewRV = findViewById(R.id.projectViewRV);
        projectViewRV.setHasFixedSize(false);
        projectViewRV.setLayoutManager(lLayoutManager);
        projectViewObjects = new ArrayList<>();

        stack = new ArrayList<>();

        projectViewAdapter = new ProjectViewAdapter(projectViewObjects);
        projectViewRV.setAdapter(projectViewAdapter);

        ProjectViewHeaderAsyncTask projectViewHeaderAsyncTask = new ProjectViewHeaderAsyncTask();
        projectViewHeaderAsyncTask.execute(urlOfThisPage);

        ProjectViewAsyncTask projectViewAsyncTask = new ProjectViewAsyncTask();
        projectViewAsyncTask.execute(urlOfThisPage);

        share_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                try {
                    intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                } catch (ActivityNotFoundException ignored) {}
                startActivity(intent);
            }
        });
    }
}
