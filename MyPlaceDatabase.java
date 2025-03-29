/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

/**
 *
 * @author harun
 */
public class MyPlaceDatabase implements PlaceDB {

    private ArrayList<Place> places; // array list for holding places.
    private int numberOfPlaces; // number of places in array list (unecessary because used Array List.)
    private boolean isSorted;  // control for is arrayList sorted or not

    // default constructor.
    public MyPlaceDatabase() {
        places = new ArrayList<Place>();
        numberOfPlaces = 0;
        isSorted = false;
    }

    // constructor which takes ArrayList parameter. This parameter list contains the informations which readed from the files. 
    public MyPlaceDatabase(ArrayList<Place> readedList) {
        places = readedList;  // shallow copy is not problem because user can not change the storedList.
        isSorted = false;
    }

    // method for looking places from their zips
    public Place lookupByZipcode(String zipcode) {
        for (int i = 0; i < places.size(); i++) { // control each place in list with for loop.
            if (places.get(i).getZipcode().equals(zipcode)) { // if parameter equals with zip from list.
                return places.get(i);
            }
        }
        return null;
    }

    public void addPlace(Place newPlace) {
        // add place if place is not in the list
        boolean isHere = false;
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getZipcode().equals(newPlace.getZipcode())) {
                System.out.println("place has already added to the list can not add again.");
                isHere = true;
            }
        }
        if (!isHere) {
            places.add(newPlace);
            numberOfPlaces++;
            if (newPlace.getClass() == Place.class) {
                System.out.println("place has added to the list\n");
            } else if (newPlace.getClass() == LocatedPlace.class) {
                System.out.println("located place has added to the list\n");
            } else {
                System.out.println("populated place has added to the list\n");
            }
        }
    }

    // list all places with start giving prefix (for zip codes).
    public void listAllPlaces(String prefix) {
        int last = prefix.length() - 1;
        String str = "";
        for (int i = 0; i < places.size(); i++) {
            // if prefix equals another zip prefix in the list add them the str varaible and in the end return str.
            if (prefix.equals(places.get(i).getZipcode().substring(0, last + 1))) { // use substring method from String class for taking or considering part of String.
                str += places.get(i).toString() + "\n";
            }
        }
        System.out.println(str);
    }

    @Override
    public double distance(String zip1, String zip2) {
        Place p1 = null;
        Place p2 = null;
        boolean isInList1 = false;
        boolean isInList2 = false;
        for (int i = 0; i < places.size(); i++) {
            // if zip1 equals with another zip in the list assign the p1 to that place object adress.
            if (zip1.equals(places.get(i).getZipcode())) {
                p1 = places.get(i);
                isInList1 = true; // if zip1 in list make it true.
            }
            // if zip2 equals with another zip in the list assign the p2 to that place object adress.
            if (zip2.equals(places.get(i).getZipcode())) {
                p2 = places.get(i);
                isInList2 = true; // if zip2 in list make it true.
            }

        }
        // if both zips in list start process for getting distance between them(places with these zips).
        if (!(isInList1 && isInList2)) {
            System.out.println("places is not in the list");
            return -1;
        }

        if (p1 instanceof LocatedPlace && p2 instanceof LocatedPlace) { // control do places have location
            LocatedPlace place1 = (LocatedPlace) p1; //for avoid compile error because Place class has no getLatitute and getLongitude methods.
            LocatedPlace place2 = (LocatedPlace) p2; //for avoid compile error because Place class has no getLatitute and getLongitude methods.
            double latitude1 = place1.getLatitude();
            double latitude2 = place2.getLatitude();
            double longitude1 = place1.getLongitude();
            double longitude2 = place2.getLongitude();
            System.out.println("distance between places is: ");
            return Math.sqrt(Math.pow(latitude1 - latitude2, 2) + Math.pow(longitude1 - longitude2, 2)); // Returns Euclidian distance
        } else {
            System.out.println("at least on of the entered zip is not belong the located place");
            return -1; // if any of them has no location return -1.
        }
    }

    public void sortListByNameWithBubbleSort() {
        // method for sort list with bubble sort.
        boolean isSwapped;
        for (int i = 0; i < places.size() - 1; i++) {
            isSwapped = false;
            for (int j = 0; j < places.size() - 1 - i; j++) {
                Place place1 = places.get(j);
                Place place2 = places.get(j + 1);
                if (place1.getTown().compareTo(place2.getTown()) > 0) {
                    places.set(j + 1, place1);
                    places.set(j, place2);
                    isSwapped = true;
                }
            }
            if (!isSwapped) {
                break;
            }
        }
        isSorted = true;
    }

    public void betterLookUpByTownName(String target) {
        // method for list informations about all places which has same TownName.

        String str = "";  //varaible for collect all information about places which has same TownName.
        int rank = 0;  // varaible which represent the rank of places in the list.
        boolean isMoreExist = true;  // control for while loop (explained in the next lines).

        if (isSorted) {
            /* if list is sorted it will use binary search.
             * when wanted to list all places which has same Town Name, using binarySearch can be problematic.
             * because when element in the list is founded by binary search it still remains in the list
             * that means, if binary search is used again, it will again find the same element which equals the first founded.
             * so it will give informations about same place again and again.
             * for the fix this I control the places before and after the first founded place(with special conditions for boundaries)
             * normally this process can handle with less line of code ,but in that condition
             * output will be not well-orginizer. With the way ,which I used, place that has fewest position will be the top at the output.
             */
            int fewest = 0;
            int greatest = 0;

            ArrayList<Place> tempList = new ArrayList<>();
            ArrayList<Integer> holdsIndex = new ArrayList<>();
            Place place = recursiveBinary(0, places.size() - 1, target);
            if (place != null) {
                //find the fewest index which contains the target element

                fewest = places.indexOf(place);
                try {
                    while (places.get(fewest - 1).getTown().equals(target)) {
                        fewest--;
                    }
                } catch (IndexOutOfBoundsException e) {

                }
                //find the greatest index which contains the target element.    
                greatest = places.indexOf(place);
                isMoreExist = true;
                try {
                    while (places.get(greatest + 1).getTown().equals(target)) {
                        greatest++;
                    }
                } catch(IndexOutOfBoundsException e) {

                }
                for (int i = fewest; i <= greatest; i++) {
                    Place willWrite = places.get(i);
                    int indexOfPlace = places.indexOf(willWrite);
                    int rankOfPlace = findRankInPopulation(willWrite);
                    str += this.addInformationToString(rankOfPlace, willWrite, indexOfPlace);
                }

            }
        } else {
            // if list is not sorted use for loop for search (sequantial search).

            for (int i = 0; i < places.size(); i++) {
                if (target.equals(places.get(i).getTown())) {
                    rank = this.findRankInPopulation(places.get(i));
                    str += this.addInformationToString(rank, places.get(i), places.indexOf(places.get(i)));
                }
            }
        }
        System.out.println(str);
    }

    private String addInformationToString(int rank, Place place, int indexOfPlace) {
        // helper method for writing the informations about places which has same Town names.
        // without this method, inside of betterLookUpByTownName will be so compilicated unfortinately it is still compilicated.
        String rankMessage;
        String str = "";
        if (rank == -1) {
            rankMessage = " No Rank ";
        } else {
            rankMessage = "" + rank;
        }
        str += place.toString() + " position in list : "
                + (indexOfPlace + 1)
                + " population rank in the list : " + rankMessage + "\n";

        return str;
    }

    private Place recursiveBinary(int left, int right, String target) {
        // helper method for recursive binnary search.

        int mid = (right + left) / 2;
        String town = places.get(mid).getTown();
        if (left > right) {
            return null;
        } else {
            if (target.compareTo(town) == 0) {
                return places.get(mid);
            } else if (target.compareTo(town) < 0) {
                return recursiveBinary(left, mid - 1, target);
            } else {
                return recursiveBinary(mid + 1, right, target);
            }
        }

    }

    private int findRankInPopulation(Place place) {
        /* 
         * helper method for finding rank in population
         * if two PopulatedPlace has same population their rank also will be same at the list
         * some place has 0 population like Boston. their rank in the list 32976. This number less then our size
         * becasue of two reasons one: as I said earlier if two place has same population their ranl will be same
         * two: Only PopulatedPlaces has rank not populated ones is not inculuded and their rank = No Rank.
        */
        int count = 0;  // varaible for follow rank.
        if (place.getClass() != PopulatedPlace.class) {
            return -1;
        } else {
            PopulatedPlace first = (PopulatedPlace) place;
            for (int i = 0; i < places.size() - 1; i++) {
                if (places.get(i).getClass() == PopulatedPlace.class) {
                    PopulatedPlace second = (PopulatedPlace) places.get(i);
                    // if place's population is less then the other one that means place's rank is greater then the other one
                    // because of count represent the rank, count will be increased.
                    if (first.getPopulation() < second.getPopulation()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private int getIndexOfTarget(Place targetPlace) {
        // helper method for get index of target place.
        return places.indexOf(targetPlace);
    }

    private boolean getIsSorted() {
        return isSorted;
    }

    public String toString() {
        // using Array List toString method.
        return places.toString();
    }

}
