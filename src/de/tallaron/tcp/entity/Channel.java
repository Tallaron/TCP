package de.tallaron.tcp.entity;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    
    private int id;
    private boolean mature;
    private String status;
    private String displayName;
    private String name;
    private String game;
    private String broadCasterLanguage;
    private String language;
    private String url;
    private int views;
    private int followers;
    private String description;
    private List<Status> statusCollection = new ArrayList<>();
    
    public void addStatus(Status s) {
        if(!statusCollection.contains(s))
            statusCollection.add(s);
    }
    
    public void removeStatus(Status s) {
        if(statusCollection.contains(s))
            statusCollection.remove(s);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMature() {
        return mature;
    }

    public void setMature(boolean mature) {
        this.mature = mature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getBroadCasterLanguage() {
        return broadCasterLanguage;
    }

    public void setBroadCasterLanguage(String broadCasterLanguage) {
        this.broadCasterLanguage = broadCasterLanguage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Status> getStatusCollection() {
        return statusCollection;
    }

    public void setStatusCollection(List<Status> statusCollection) {
        this.statusCollection = statusCollection;
    }
    
    
    
}
