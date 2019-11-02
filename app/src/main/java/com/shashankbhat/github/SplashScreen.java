package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screan);

        final Context context = this;
        textView = findViewById(R.id.textView);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(textView, View.ALPHA,0,1);
        alpha.setDuration(400);
        alpha.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context,Login.class));
                finish();
            }
        },400);
    }
}
