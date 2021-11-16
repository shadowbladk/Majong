package com.example.realmajong;

public class RestaurantData {
    private String name, location, logoPath, bannerPath;
    private int numPeople = 0;

    public RestaurantData(String name, String location, String logoPath, String bannerPath) {
        this.name = name;
        this.location = location;
        this.logoPath = logoPath;
        this.bannerPath = bannerPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }
}