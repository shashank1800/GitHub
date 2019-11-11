package com.shashankbhat.github;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashankbhat.github.Adapter.RepoAdapter;
import com.shashankbhat.github.AsyncTasks.NavHeaderAsyncTask;
import com.shashankbhat.github.AsyncTasks.RepoAsyncTask;
import com.shashankbhat.github.Objects.RepositoryProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.shashankbhat.github.Login.sp;
import static com.shashankbhat.github.Utils.Constants.USERNAME;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View headerView;

    public static RecyclerView repositoryProjectsRV;
    private LinearLayoutManager lLayoutManager;
    private FirebaseFirestore db;

    @SuppressLint("StaticFieldLeak")
    public static TextView mUsername,mNickname;
    @SuppressLint("StaticFieldLeak")
    public static ImageView mAvatar;
    public static TextView mFollowers;
    public static TextView mFollowing;
    public static Context context;
    public static RepoAdapter repoAdapter;
    public static ArrayList<RepositoryProject> repositoryProjectsObjects;
    public static ImageView graphView ;

    public static SpinKitView spin_kit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        mAvatar = headerView.findViewById(R.id.avatar);
        graphView = headerView.findViewById(R.id.graphView);
        mUsername = headerView.findViewById(R.id.username);
        mNickname = headerView.findViewById(R.id.nickname);
        mFollowers = headerView.findViewById(R.id.followers);
        mFollowing = headerView.findViewById(R.id.following);

        spin_kit = findViewById(R.id.spin_kit);

        lLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        repositoryProjectsRV = findViewById(R.id.repositoryProjectsRV);
        repositoryProjectsRV.setHasFixedSize(false);
        repositoryProjectsRV.setLayoutManager(lLayoutManager);
        repositoryProjectsObjects = new ArrayList<>();

        repoAdapter = new RepoAdapter(repositoryProjectsObjects);
        repositoryProjectsRV.setAdapter(repoAdapter);

        NavHeaderAsyncTask navHeaderAsyncTask = new NavHeaderAsyncTask();
        navHeaderAsyncTask.execute();

        RepoAsyncTask repoAsyncTask = new RepoAsyncTask();
        repoAsyncTask.execute();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.nav_feedback_text:
                showFeedbackDialog();
                break;
            case R.id.nav_shareapp:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                try {
                    intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                } catch (ActivityNotFoundException ignored) { }
                startActivity(intent);
                break;
            case R.id.rate_us:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                break;
            case R.id.cheat_sheet:
                startActivity(new Intent(getApplicationContext(),CheatSheet.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(USERNAME,"");
                editor.apply();
                editor.commit();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                break;
            case R.id.nav_about:
                showAboutDevDialog();
                break;
        }
        return true;
    }

    private void showFeedbackDialog() {
        final ViewGroup viewGroup = findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_feedback, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Feedback");
        builder.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText feedback_text = dialogView.findViewById(R.id.feedback_text);
                if(feedback_text.getText().toString().compareTo("")==0)
                    Snackbar.make(findViewById(R.id.linearLayout), "Please enter feedback text", Snackbar.LENGTH_SHORT).show();
                else {
                    Snackbar.make(findViewById(R.id.linearLayout), "Thanks for your Feedback!", Snackbar.LENGTH_SHORT).show();
                    String uniqueID = UUID.randomUUID().toString();
                    String feedbackText = feedback_text.getText().toString();

                    Map<String, Object> feedback = new HashMap<>();
                    feedback.put("feedbackText", feedbackText);

                    db = FirebaseFirestore.getInstance();
                    db.collection("Feedback").document(uniqueID).set(feedback);

                    InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(viewGroup.getWindowToken(), 0);
                }
            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showAboutDevDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_about_dev, viewGroup, false);

        ImageView dev_photo = dialogView.findViewById(R.id.photo);
        Glide.with(context)
                .load(R.drawable.my_photo)
                .apply(RequestOptions.circleCropTransform())
                .into(dev_photo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogView.findViewById(R.id.fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlFb="https://www.facebook.com/shashankbhat114";
                String appFb="fb://facewebmodal/f?href="+urlFb;

                try {
                    if (isAppInstalled(context, "com.facebook.orca") || isAppInstalled(context, "com.facebook.katana")
                            || isAppInstalled(context, "com.example.facebook") || isAppInstalled(context, "com.facebook.android")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appFb)));
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlFb)));
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        });

        dialogView.findViewById(R.id.ln).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlLn="https://www.linkedin.com/in/shashank-bhat-924b1bb9/";
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urlLn));
                    intent.setPackage("com.linkedin.android");
                    startActivity(intent);
                }
                catch (ActivityNotFoundException anfe)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlLn)));
                }

            }
        });

        dialogView.findViewById(R.id.insta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appIs="http://instagram.com/shashank_bhat__";
                String urlIs="http://instagram.com/_u/shashank_bhat__";

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(appIs));
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                }
                catch (ActivityNotFoundException anfe)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlIs)));
                }
            }
        });
    }
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
