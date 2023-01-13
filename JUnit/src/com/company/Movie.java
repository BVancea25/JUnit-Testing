package com.company;

public class Movie {
    private String title;
    private String description;
    private String runtime;
    private String genre;
    private String ageRestrictions;
    private String language;


    public Movie(String title, String description, String runtime, String genre, String ageRestrictions, String language) {
        this.title = title;
        this.description = description;
        this.runtime = runtime;
        this.genre = genre;
        this.ageRestrictions = ageRestrictions;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getAgeRestrictions() {
        return ageRestrictions;
    }

    public String getLanguage() {
        return language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAgeRestrictions(String ageRestrictions) {
        this.ageRestrictions = ageRestrictions;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
