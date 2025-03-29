/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.Serializable;

/**
 *
 * @author harun
 */
public class LocatedPlace extends Place implements Serializable {

    // instances.
    private double latitude;
    private double longitude;

    // default constructor
    public LocatedPlace() {
        super();
        latitude = 0;
        longitude = 0;
    }

    // constructor
    public LocatedPlace(String zipcode, String town, String state, double latitude, double longitude) {
        super(zipcode, town, state);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // copy constructor.
    public LocatedPlace(LocatedPlace other) {
        super(other);
        latitude = other.latitude;
        longitude = other.longitude;
    }

    // getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // setters
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return super.toString() + ", " + latitude + ", " + longitude;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (this.getClass() != other.getClass()) {
            return false;
        } else {
            LocatedPlace o = (LocatedPlace) other;
            return super.equals(o) && this.latitude == o.latitude && this.longitude == o.longitude;
        }
    }
}
