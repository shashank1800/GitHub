package com.shashankbhat.github;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.shashankbhat.github.Api.VolleyRequestQueue;
import com.shashankbhat.github.Objects.GithubUser;

import org.json.JSONException;

import static com.shashankbhat.github.Utils.Constants.GITHUB_USER;
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

        mSubmitButton.setOnClickListener(v -> {
            spinKitView.setVisibility(View.VISIBLE);
            if(mUsername.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter your github username", Toast.LENGTH_SHORT).show();
                showAboutDevDialog();
            }
            else{
                loginTask();
            }

        });

        mHelp.setOnClickListener(v -> showAboutDevDialog());
    }

    private void showAboutDevDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_login_help, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loginTask(){
        String url = "https://api.github.com/users/" + mUsername.getText().toString();
        RequestQueue queue = VolleyRequestQueue.getInstance(context);

        JsonObjectRequest githubUserRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    GithubUser githubUser = new GithubUser();
                    try {
                        String username = response.getString("login");
                        githubUser.setUsername(username);
                        githubUser.setName(response.getString("name"));
                        githubUser.setBio(response.getString("bio"));
                        githubUser.setPublicRepos(response.getInt("public_repos"));
                        githubUser.setAvatarUrl(response.getString("avatar_url"));
                        githubUser.setLocation(response.getString("location"));
                        githubUser.setCompany(response.getString("company"));
                        githubUser.setType(response.getString("type"));

                        githubUser.setFollowers(response.getInt("followers"));
                        githubUser.setFollowing(response.getInt("following"));

                        int years = getYearsBetween(response.getString("created_at"),response.getString("updated_at"));
                        githubUser.setYears(years);

                        saveUserIntoCache(githubUser);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    context.startActivity(new Intent(context, MainActivity.class));
                    ((Activity) context).finish();
                }, error -> {
                    spinKitView.setVisibility(View.GONE);
                    Toast.makeText(context, "Invalid username", Toast.LENGTH_SHORT).show();
                });

        queue.add(githubUserRequest);
    }
    private int getYearsBetween(String created_at, String updated_at) {

        int created = Integer.parseInt(created_at.substring(0,4));
        int updated = Integer.parseInt(updated_at.substring(0,4));

        return updated - created;
    }

    private void saveUserIntoCache(GithubUser githubUser) {

        SharedPreferences.Editor editor = sp.edit();

        Gson gson = new Gson();
        String jsonGithubUser = gson.toJson(githubUser);
        System.out.println(jsonGithubUser);

        editor.putString(GITHUB_USER, jsonGithubUser);
        editor.putString(USERNAME, mUsername.getText().toString());
        editor.apply();

    }


}
