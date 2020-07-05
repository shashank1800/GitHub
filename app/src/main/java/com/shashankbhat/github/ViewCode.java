package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.shashankbhat.github.AsyncTasks.CodeDownloadAsyncTask;
import com.shashankbhat.github.Utils.Constants;

import java.util.Objects;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.classifier.CodeProcessor;

public class ViewCode extends AppCompatActivity {

    private Context context;
    public static SpinKitView spin_kit;
    @SuppressLint("StaticFieldLeak")
    public static TextView text_view;
    public static CodeView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_code);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        context = this;

        try {
            CodeProcessor.init(this);
        }catch (Exception ignored){}

        Bundle bundle = getIntent().getExtras();
        String file_url = bundle.getString(Constants.FILE_URL);

        String url = "https://raw.githubusercontent.com"+file_url;
        int indexOfBlob = url.indexOf("/blob/");
        url = url.substring(0,indexOfBlob)+ url.substring(indexOfBlob+5);

        spin_kit = findViewById(R.id.spin_kit);
        text_view = findViewById(R.id.text_view);
        codeView =  findViewById(R.id.code_view);

        CodeDownloadAsyncTask codeDownloadAsyncTask = new CodeDownloadAsyncTask();
        codeDownloadAsyncTask.execute(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
