package com.shashankbhat.github.Adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shashankbhat.github.Objects.RepositoryProject;
import com.shashankbhat.github.R;

import java.util.ArrayList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder>{

    private ArrayList<RepositoryProject> repositoryProjectObjects;
    private Context context;
    private int time = 1000;

    public RepoAdapter(ArrayList<RepositoryProject> repositoryProjectObjects) {
        this.repositoryProjectObjects = repositoryProjectObjects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView project_name, language_used, updated_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project_name = itemView.findViewById(R.id.project_name);
            language_used = itemView.findViewById(R.id.language_used);
            updated_time = itemView.findViewById(R.id.updated_time);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_repositories_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.project_name.setText(repositoryProjectObjects.get(position).getProject_name());
        holder.language_used.setText(repositoryProjectObjects.get(position).getLanguage_used());
        holder.updated_time.setText(repositoryProjectObjects.get(position).getUpdated_time());
//        if(time>200)
//            animate(holder,time);
//        time -= 100;
    }

    private void animate(RecyclerView.ViewHolder holder, int time){

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator xTranslation = ObjectAnimator.ofFloat(holder.itemView, View.TRANSLATION_Y, 1200f, 0);
        xTranslation.setDuration(time);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(holder.itemView, View.ALPHA, 0, 1);
        alpha.setDuration(time);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(holder.itemView, View.ROTATION_X, 90, 0);
        rotate.setDuration(time);

        animatorSet.playTogether(xTranslation,alpha,rotate);
        animatorSet.setDuration(time);
        animatorSet.start();

    }

    @Override
    public int getItemCount() {
        return repositoryProjectObjects == null ? 0 : repositoryProjectObjects.size();
    }

    public void setRepositoryProjectObjects(ArrayList<RepositoryProject> repositoryProjects){
        this.repositoryProjectObjects = repositoryProjects;
        notifyDataSetChanged();
    }
}