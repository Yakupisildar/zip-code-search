/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.Serializable;

/**
 *
 * @author harun
 */
public class Place implements Serializable {

    // instnaces.
    private String zipcode;
    private String town;
    private String state;

    //constructors.
    public Place() {
        zipcode = null;
        town = null;
        state = null;
    }

    public Place(String zipcode, String town, String state) {
        this.zipcode = zipcode;
        this.state = state;
        this.town = town;
    }

    // coppy constructor.
    public Place(Place other) {
        zipcode = other.zipcode;
        state = other.state;
        town = other.town;
    }

    // getters
    public String getZipcode() {
        return zipcode;
    }

    public String getTown() {
        return town;
    }

    public String getState() {
        return state;
    }

    // setters
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return zipcode + ": " + town + ", " + state;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (this.getClass() != other.getClass()) {
            return false;
        } else {
            Place o = (Place) other;
            return zipcode.equals(o.zipcode) && state.equals(o.state) && town.equals(o.town);
        }
    }
}
