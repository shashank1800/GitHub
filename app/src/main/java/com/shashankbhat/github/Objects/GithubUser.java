package com.shashankbhat.github.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SHASHANK BHAT on 05-Jul-20.
 */
public class GithubUser {

    private String username,name,bio, avatarUrl, location, company, type;
    private int followers,following,years,publicRepos;

    public GithubUser() {
    }

    public GithubUser(String username, String name, String bio, String avatarUrl, String location, String company, String type, int followers, int following, int years, int publicRepos) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
        this.location = location;
        this.company = company;
        this.type = type;
        this.followers = followers;
        this.following = following;
        this.years = years;
        this.publicRepos = publicRepos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

}
