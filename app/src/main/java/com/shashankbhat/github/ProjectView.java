package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.shashankbhat.github.Adapter.ProjectViewAdapter;
import com.shashankbhat.github.AsyncTasks.ProjectViewAsyncTask;
import com.shashankbhat.github.AsyncTasks.ProjectViewHeaderAsyncTask;
import com.shashankbhat.github.Objects.ProjectViewObject;

import java.util.ArrayList;
import java.util.Objects;

import static com.shashankbhat.github.Utils.Constants.PROJECT_NAME;
import static com.shashankbhat.github.Utils.Constants.USERNAME;


public class ProjectView extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static TextView commits,branch,releases,contributor;
    public static String shareUrl;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public RecyclerView projectViewRV;

    @SuppressLint("StaticFieldLeak")
    public ProjectViewAdapter projectViewAdapter;
    public ArrayList<ProjectViewObject> projectViewObjects;
    public static ArrayList<Object> stack;
    public static AppCompatImageButton back_button;
    public SpinKitView spin_kit;

    public String username = "";
    public String project_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        project_name= bundle.getString(PROJECT_NAME);
        username = bundle.getString(USERNAME);

        context = this;

        String urlOfThisPage = "/"+username+"/"+project_name;

        commits = findViewById(R.id.commits);
        branch = findViewById(R.id.branch);
        releases = findViewById(R.id.releases);
        contributor = findViewById(R.id.contributor);

        Button share_button = findViewById(R.id.share_button);
        back_button = findViewById(R.id.back_button);

        spin_kit = findViewById(R.id.spin_kit);

        LinearLayoutManager lLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        projectViewRV = findViewById(R.id.projectViewRV);
        projectViewRV.setHasFixedSize(false);
        projectViewRV.setLayoutManager(lLayoutManager);
        projectViewObjects = new ArrayList<>();

        stack = new ArrayList<>();

        projectViewAdapter = new ProjectViewAdapter(projectViewObjects,projectViewRV,spin_kit);
        projectViewRV.setAdapter(projectViewAdapter);

        ProjectViewHeaderAsyncTask projectViewHeaderAsyncTask = new ProjectViewHeaderAsyncTask();
        projectViewHeaderAsyncTask.execute(urlOfThisPage);

        ProjectViewAsyncTask projectViewAsyncTask = new ProjectViewAsyncTask(projectViewAdapter);
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

        back_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                popPageOfStack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(stack.size()>0)
            popPageOfStack();
        else
            super.onBackPressed();
    }

    private void popPageOfStack(){
        projectViewAdapter.setRepositoryProjectObjects((ArrayList<ProjectViewObject>) stack.get(stack.size()-1));
        stack.remove(stack.size()-1);
        if(stack.size()==0) back_button.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
