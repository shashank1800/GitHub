package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class CheatSheet extends AppCompatActivity {
    private PDFView pdfView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_sheet);

        context = this;

        pdfView = (PDFView) findViewById(R.id.pdfView);
        displayFromAsset();
    }

    private void displayFromAsset() {
        pdfView.fromAsset("github_cheat_sheet.pdf")
                .defaultPage(0)
                .scrollHandle(new DefaultScrollHandle(context))
                .load();
    }
}
