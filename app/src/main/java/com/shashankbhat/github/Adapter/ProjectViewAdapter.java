package com.shashankbhat.github.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.shashankbhat.github.AsyncTasks.ProjectViewAsyncTask;
import com.shashankbhat.github.Objects.ProjectViewObject;
import com.shashankbhat.github.ProjectView;
import com.shashankbhat.github.R;
import com.shashankbhat.github.Utils.Constants;
import com.shashankbhat.github.ViewCode;

import java.util.ArrayList;
import static com.shashankbhat.github.Utils.Constants.listOfFileType;



public class ProjectViewAdapter extends RecyclerView.Adapter<ProjectViewAdapter.ViewHolder>{

    private ArrayList<ProjectViewObject> projectViewObject;
    private Context context;
    public SpinKitView spin_kit;
    public RecyclerView projectViewRV;
    private ProjectViewAdapter projectViewAdapter;

    public ProjectViewAdapter(ArrayList<ProjectViewObject> projectViewObject,RecyclerView recyclerView,SpinKitView spin_kit) {
        this.projectViewObject = projectViewObject;
        this.projectViewRV = recyclerView;
        this.spin_kit = spin_kit;
        projectViewAdapter = this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView filename,commit_comment,time;
        ImageView file_type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            file_type = itemView.findViewById(R.id.file_type);
            filename = itemView.findViewById(R.id.filename);
            commit_comment = itemView.findViewById(R.id.commit_comment);
            time = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(projectViewObject.get(getAdapterPosition()).getFile_type().equals("directory")){
                projectViewRV.setVisibility(View.GONE);
                spin_kit.setVisibility(View.VISIBLE);

                ProjectView.back_button.setVisibility(View.VISIBLE);
                ProjectView.stack.add(projectViewObject);
                ProjectViewAsyncTask projectViewAsyncTask = new ProjectViewAsyncTask(projectViewAdapter);
                projectViewAsyncTask.execute(projectViewObject.get(getAdapterPosition()).getClickUrl());
            }else{
                String filename = projectViewObject.get(getAdapterPosition()).getFilename();

                boolean isTextFormat = false;
                for(String filetype : listOfFileType)
                    if(filename.endsWith(filetype)){
                        isTextFormat = true;
                        break;
                    }
                if(isTextFormat){
                    Intent intent = new Intent(context, ViewCode.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.FILE_URL,projectViewObject.get(getAdapterPosition()).getClickUrl());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_project_view_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(projectViewObject.get(position).getFile_type().equals("directory"))
            holder.file_type.setImageResource(R.drawable.ic_directory);
        else
            holder.file_type.setImageResource(R.drawable.ic_cheat_sheet);

        holder.filename.setText(projectViewObject.get(position).getFilename());
        holder.commit_comment.setText(projectViewObject.get(position).getCommit_comment());
        holder.time.setText(projectViewObject.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return projectViewObject == null ? 0 : projectViewObject.size();
    }

    public void setRepositoryProjectObjects(ArrayList<ProjectViewObject> projectViewObject){
        this.projectViewObject = projectViewObject;
        notifyDataSetChanged();

        spin_kit.setVisibility(View.GONE);
        projectViewRV.setVisibility(View.VISIBLE);
    }
}