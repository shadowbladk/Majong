package com.example.realmajong;

import android.graphics.Movie;

import java.util.ArrayList;

public class MovieReservation {
    private String name;
    private String dateSelected;
    private ArrayList<String> seats;

    public MovieReservation() {}
    public MovieReservation(String name, String dateSelected, ArrayList<String> seats) {
        this.dateSelected = dateSelected;
        this.seats = seats;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(String dateSelected) {
        this.dateSelected = dateSelected;
    }

    public ArrayList<String> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<String> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "MovieReservation{" +
                "name='" + name + '\'' +
                ", dateSelected='" + dateSelected + '\'' +
                ", seats=" + seats +
                '}';
    }
}
