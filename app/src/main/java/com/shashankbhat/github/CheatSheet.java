package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;


public class CheatSheet extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_sheet);

        context = this;

    }
}
