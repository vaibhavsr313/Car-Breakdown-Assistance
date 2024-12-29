package com.example.abreak;

public class LocationHelper {

    private double Longitude;
    private double Latitude;
    private String phno;
    private String name;
    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationHelper(double longitude, double latitude, String phno, String name) {
        Longitude = longitude;
        Latitude = latitude;
        this.phno = phno;
        this.name = name;
    }

    public LocationHelper() {
    }
}
