package com.example.realmajong;

public class MovieData {
    private String name, date, path, genre, time, description;

    MovieData(String name, String date, String path, String genre, String time, String description) {
        this.name = name;
        this.date = date;
        this.path = path;
        this.genre = genre;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}