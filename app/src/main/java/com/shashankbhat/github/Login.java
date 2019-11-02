package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.shashankbhat.github.Utils.Constants.SHARED_PREF_NAME;
import static com.shashankbhat.github.Utils.Constants.USERNAME;

public class Login extends AppCompatActivity {

    private EditText mUsername;
    private Button mSubmitButton;
    public static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.username);
        mSubmitButton = findViewById(R.id.submit);

        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        if(!sp.getString(USERNAME,"").isEmpty()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                if(mUsername.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter your github Username",Toast.LENGTH_SHORT).show();
                else{
                    editor.putString(USERNAME, mUsername.getText().toString());
                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

            }
        });
    }

}
