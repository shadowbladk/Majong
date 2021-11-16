package com.example.realmajong;

public class CoworkingSpace {
    private String name;
    private String address;
    private String path;
    private String pathSF; // path for storefront pic
    private int numPeople;

    public CoworkingSpace(String name, String address, String path) {
        this.name = name;
        this.address = address;
        this.path = path;
        this.numPeople = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathSF() {
        return pathSF;
    }

    public void setPathSF(String pathSF) {
        this.pathSF = pathSF;
    }

    public void setnumPeople(int d){
        numPeople = d;
    }

    public int getnumPeople(){
        return numPeople;
    }
}