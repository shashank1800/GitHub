package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.shashankbhat.github.AsyncTasks.LoginUsernameAsyncTask;

import static com.shashankbhat.github.Utils.Constants.SHARED_PREF_NAME;
import static com.shashankbhat.github.Utils.Constants.USERNAME;

public class Login extends AppCompatActivity {

    private EditText mUsername;
    private Button mSubmitButton,mHelp;
    public static SharedPreferences sp;
    private Context context;
    private SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        mUsername = findViewById(R.id.username);
        mSubmitButton = findViewById(R.id.submit);
        mHelp = findViewById(R.id.help);
        spinKitView = findViewById(R.id.spin_kit);

        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        if(!sp.getString(USERNAME,"").isEmpty()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinKitView.setVisibility(View.VISIBLE);
                if(mUsername.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your github username", Toast.LENGTH_SHORT).show();
                    showAboutDevDialog();
                }
                else{
                    LoginUsernameAsyncTask loginUsernameAsyncTask = new LoginUsernameAsyncTask(context,mUsername.getText().toString(),spinKitView);
                    loginUsernameAsyncTask.execute();
                }

            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutDevDialog();
            }
        });
    }

    private void showAboutDevDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_login_help, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
