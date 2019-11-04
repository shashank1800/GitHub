package com.shashankbhat.github.Objects;

public class ProjectViewObject {

    private String file_type,filename, commit_comment,time,clickUrl;

    public ProjectViewObject(String file_type, String filename, String commit_comment, String time,String clickUrl){
        this.file_type = file_type;
        this.filename = filename;
        this.commit_comment = commit_comment;
        this.time = time;
        this.clickUrl = clickUrl;
    }

    public String getFilename() {
        return filename;
    }

    public String getCommit_comment() {
        return commit_comment;
    }

    public String getTime() {
        return time;
    }

    public String getFile_type() {
        return file_type;
    }
    public String getClickUrl() {
        return clickUrl;
    }
}
