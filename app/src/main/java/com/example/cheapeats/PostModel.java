package com.example.cheapeats;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.auth.User;
import com.google.type.Date;


public class PostModel {


    private Date startTime;
    private User author;
    private int flagCount, cloutValue;
    private GeoPoint location;
    private String title, description;

    /**
     * @ Data model representing an event posting in the Firestore Database
     * @param title
     * @param description
     * @param startTime
     * @param author
     * @param flagCount
     * @param cloutValue
     * @param location
     */
    public PostModel(String title, String description, Date startTime, User author, int flagCount, int cloutValue, GeoPoint location) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.author = author;
        this.flagCount = flagCount;
        this.cloutValue = cloutValue;
        this.location = location;
    }
    public PostModel(){

    }

    /**
     * Data model representing an event posting in the Firestore Database
     * @param title
     * @param description
     */
    public PostModel(String title, String description){
        this.title = title;
        this.description = description;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(int flagCount) {
        this.flagCount = flagCount;
    }

    public int getCloutValue() {
        return cloutValue;
    }

    public void setCloutValue(int cloutValue) {
        this.cloutValue = cloutValue;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
