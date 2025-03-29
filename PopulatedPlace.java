/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.Serializable;

/**
 *
 * @author harun
 */
public class PopulatedPlace extends LocatedPlace implements Serializable {

    //instances.
    private int population;

    // default constructor.
    public PopulatedPlace() {
        int population = 0;
    }

    // constructors.
    public PopulatedPlace(String zipcode, String town, String state, double latitude, double longitude, int population) {
        super(zipcode, town, state, latitude, longitude);
        this.population = population;
    }

    // create another consturactor which will help us ,when seperating places each other ,while reading from text files.
    // latitude and longitute can be adjusted after read the second file.
    public PopulatedPlace(String zipcode, String town, String state, int population) {
        this.setZipcode(zipcode);
        this.setState(state);
        this.setTown(town);
        this.setPopulation(population);
        // initially set latitude and longitude as 0 ,befor the combine informations.
        this.setLatitude(0);
        this.setLongitude(0);
    }

    // copy constructor.
    public PopulatedPlace(PopulatedPlace other) {
        super(other);
        population = other.population;
    }

    // getters.
    public int getPopulation() {
        return population;
    }

    // setters.
    public void setPopulation(int population) {
        this.population = population;
    }

    public String toString() {
        return super.toString() + ", " + population + " ";
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (this.getClass() != other.getClass()) {
            return false;
        } else {
            PopulatedPlace o = (PopulatedPlace) other;
            return super.equals(o) && this.population == o.population;
        }
    }
}
