package com.shashankbhat.github.Objects;

public class RepositoryProject {
    private String project_name;
    private String language_used;
    private String updated_time;

    public RepositoryProject(String project_name, String language_used, String updated_time){
        this.project_name = project_name;
        this.language_used = language_used;
        this.updated_time = updated_time;
    }
    public String getProject_name() {
        return project_name;
    }

    public String getLanguage_used() {
        return language_used;
    }
    public String getUpdated_time() {
        return updated_time;
    }

}
