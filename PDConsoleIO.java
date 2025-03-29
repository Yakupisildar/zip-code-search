import java.util.*;
import java.io.*;

/**
 * This class is a possible user interface for the Place Database that uses the
 * console to display the menu of command choices.
 */
public class PDConsoleIO {

    /**
     * A reference to the PlaceDB object to be processed. Globally available to
     * the command-processing methods.
     */
    private PlaceDB theDatabase = null;

    /**
     * Scanner to read from input console.
     */
    private Scanner scIn = null;

    // Constructor
    /**
     * Default constructor.
     */
    public PDConsoleIO() {
        scIn = new Scanner(System.in);
    }

    // Methods
    /**
     * Method to display the command choices and process user commands.
     *
     * @param thePlaceDatabase A reference to the PlaceDB to be processed
     */
    public void processCommands(PlaceDB thePlaceDatabase) {
        String[] commands = {
            "Add Place",
            "Look Up by Zipcode",
            "List All Places by Zipcode Prefix",
            "Distance Between Zipcodes",
            "Sort By Town Name",
            "Lookup by Town Name",
            "Exit"};

        theDatabase = thePlaceDatabase;
        int choice;
        do {
            for (int i = 0; i < commands.length; i++) {
                System.out.println("Select " + i + ": "
                        + commands[i]);
            }

            choice = scIn.nextInt(); // Read the next choice.
            scIn.nextLine(); // Skip trailing newline.
            switch (choice) {
                case 0:
                    doAddPlace();
                    break;
                case 1:
                    doLookupByZipcode();
                    break;
                case 2:
                    doListAllPlaces();
                    break;
                case 3:
                    doDistance();
                    break;
                case 4:
                    doSortByTownName();
                    break;
                case 5:
                    listAllPlacesWithSameName();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("*** Invalid choice "
                            + choice
                            + " - try again!");
            }

        } while (choice != commands.length - 1);
        System.exit(0);
    }

    /**
     * Method to add a place. pre: The database exists. post: A new place is
     * added.
     */
    private void doAddPlace() {
        //taking input form user.
        System.out.println("Enter zipcode");
        Scanner sc = new Scanner(System.in);
        String zipcode = sc.nextLine();
        System.out.println("Enter town");
        String town = sc.nextLine();
        System.out.println("Enter state");
        String state = sc.nextLine();
        System.out.println("Enter numbers latitude and longitude");
        System.out.println("in this format: ..., ...");
        System.out.println("if do not want to enter location, enter 'None' to at least one of them be carefull about capital letters");
        boolean done = false; // control for string tokenizer.
        String latitude = null;
        String longitude = null;
        // work with string tokenizer until user enters valid latitude and longitude.
        while (!done) {
            String location = sc.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(location, ", ");
            if (tokenizer.countTokens() == 2) {
                latitude = tokenizer.nextToken();
                longitude = tokenizer.nextToken();
                done = true;
            } else {
                System.out.println("please enter just one latitude and longitude");
                System.out.println("in this format: ..., ...");
            }
        }
        System.out.println("Enter population");
        String population = sc.nextLine();
        // control is place has zip or state or town.
        if ((zipcode.equals("") || zipcode.equals("None")) || (town.equals("") || town.equals("None")) || (state.equals("None") || state.equals(""))) {
            System.out.println("there can be a no place without zipcode or state or town\n");
        } else {
            // if place is not located
            // has not added to "" case because of String tokenizer user can not enter "" value with only just enter, so if user do not want to enter location, need to enter None for at least one of them.
            if (latitude.equals("None") || longitude.equals("None")) {
                // if place not populated and not located
                if (population.equals("") || population.equals("None")) {
                    Place place = new Place(zipcode, town, state);
                    theDatabase.addPlace(place);
                } // if place populated but not located
                else {
                    System.out.println("there can be a no place with population but without location\n");
                }
            } else {
                // if locations is not null or none convert them to double values
                double latitudeD = Double.parseDouble(latitude);
                double longitudeD = Double.parseDouble(longitude);
                if (population.equals("") || population.equals("None")) {
                    // if place is located but not populated. (Population is "None" or ""). 
                    Place locatedPlace = new LocatedPlace(zipcode, town, state, latitudeD, longitudeD);
                    theDatabase.addPlace(locatedPlace);
                } else {
                    // if population is not "" or "None" value convert it to int
                    int pop = Integer.parseInt(population);
                    Place populatedPlace = new PopulatedPlace(zipcode, town, state, latitudeD, longitudeD, pop);
                    theDatabase.addPlace(populatedPlace);
                }
            }
        }
    }

    /**
     * Method to lookup a place by zipcode. pre: The database exists. post: No
     * changes made to the database.
     */
    private void doLookupByZipcode() {
        // Request the zipcode.
        System.out.println("Enter zipcode");
        String theZip = scIn.nextLine();
        if (theZip.equals("")) {
            return; // Dialog was cancelled.
        }
        // Look up the zipcode.
        Place p = theDatabase.lookupByZipcode(theZip);
        if (p != null) { // Zipcode was found.
            System.out.println(p.toString());
            System.out.println();
        } else { // not found.
            // Display the result.
            System.out.println("No such zipcode");
            System.out.println();

        }
    }

    /**
     * Method to list all places whose zipcodes start with entered prefix. pre:
     * The database exists. post: No changes made to the database.
     */
    private void doListAllPlaces() {
        System.out.println("Enter zipcode prefix");
        String prefix = scIn.nextLine();
        if (prefix.equals("")) {
            return; // Dialog was cancelled.
        }

        theDatabase.listAllPlaces(prefix);

    }

    /**
     * Method to compute the distance between two zipcodes. pre: The database
     * exists. post: No changes made to the database.
     */
    private void doDistance() {
        System.out.println("Enter two zipcodes in this format: ..., ...");
        boolean isValid = false; // control for string tokenizer.
        String zip1 = null;
        String zip2 = null;
        // repeate the proces until user enter valid input.
        while (!isValid) {
            String inputZips = scIn.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(inputZips, ", ");
            if (tokenizer.countTokens() == 2) {
                zip1 = tokenizer.nextToken();
                zip2 = tokenizer.nextToken();
                isValid = true;
            } else {
                System.out.println("in valid input");
                System.out.println("please enter two zips in this format: ..., ...");
            }
        }
        // use distance method from MyPlaceDatabase class and get distance bewteen two zips.
        System.out.println(theDatabase.distance(zip1, zip2));
        System.out.println();

    }

    private void doSortByTownName() {
        System.out.println("file is sorting");
        theDatabase.sortListByNameWithBubbleSort();
        System.out.println("file sorted");
    }

    private void listAllPlacesWithSameName() {
        // method for list places accoring to user's input
        System.out.println("please enter town name for search (Be carefull about capital letters).");
        String target = scIn.nextLine();
        if (target.equals("")) {
            return;
        } else {
            theDatabase.betterLookUpByTownName(target);
        }
    }

    public static void main(String args[]) {
        File binaryFile = new File("database.out");

        //control do file exist or not
        //if exist start the console
        if (binaryFile.exists()) {
            ArrayList<Place> places = readFromBinaryFile();
            PDConsoleIO ui = new PDConsoleIO();
            PlaceDB pd = new MyPlaceDatabase(places);
            try {
                ui.processCommands(pd);
            } catch (InputMismatchException e) {
                System.out.println("you entered invalid input please enter integer when you are in the menu");
                System.out.println("please start program again");
            }

        } //if file do not exist, will start the process for reading and storing informations.
        else {
            readFromTextFiles();
            System.out.println(" files readed please run program again ");
        }
    }

    public static void readFromTextFiles() {
        ArrayList<Place> temporaryList = new ArrayList<>();
        String fileName = "uszipcodes.csv";
        try {
            Scanner sc = new Scanner(new File(fileName));
            //skip the first lint
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // need to use limited version of split method. if it is not used
                // blank parts at the and in the file will not be added to the array list (will be removed)
                // and if we try to reach the parts[3] there will be ArrayIndexOutOfBoundException.
                String[] parts = line.split(",", -1);
                String zip = parts[0];
                String town = parts[1];
                String state = parts[2];
                String populationText = parts[3];
                // if population parts is not empty (""). That means places is populatedPlace.
                if (!populationText.isEmpty()) {
                    int population = Integer.parseInt(populationText);
                    PopulatedPlace populatedPlace = new PopulatedPlace(zip, town, state, population);
                    temporaryList.add(populatedPlace);
                } // place is locatedPlace or just Place
                else {
                    // initially create just place object, it will be changed after the combine informations
                    Place unknownPlace = new Place(zip, town, state);
                    temporaryList.add(unknownPlace);
                }
            }
            //close the text file after read the informations.
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        try {
            Scanner sc = new Scanner(new File("ziplocs.csv"));
            //skip the first line
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                //can be used without limit parameter because the last blank parts is not needed.
                String parts[] = line.split(",");
                String zip = parts[0].substring(1, parts[0].length() - 1);

                // index of element which has the same zip at the temporaryList or -1.
                // if latitude is not empty that means place is located or populated
                // there is no need to do something for else if latitude and longitude is empty
                // Because temporaryList holds them as Place object already.               
                if (!parts[5].isEmpty()) {
                    double latitude = Double.parseDouble(parts[5]);
                    double longitude = Double.parseDouble(parts[6]);

                    // contol zip code is in the temporaryList. If it is, will return the index of that object.
                    int index = controlZipWithBinarySearch(temporaryList, zip);
                    if (index != -1) {
                        if (temporaryList.get(index) instanceof PopulatedPlace) {
                            PopulatedPlace populatedPlace = new PopulatedPlace((PopulatedPlace) temporaryList.get(index));
                            populatedPlace.setLatitude(latitude);
                            populatedPlace.setLongitude(longitude);

                            // replace the combined form with old one which has the same zip.
                            temporaryList.set(index, populatedPlace);
                        } else {
                            String zipLocated = temporaryList.get(index).getZipcode();
                            String townLocated = temporaryList.get(index).getTown();
                            String stateLocated = temporaryList.get(index).getState();
                            LocatedPlace locatedPlace = new LocatedPlace(zipLocated, townLocated,
                                    stateLocated, latitude, longitude);
                            // replace the combined form with Place object which has the same zip.
                            temporaryList.set(index, locatedPlace);

                        }
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("database.out"));
            outputStream.writeObject(temporaryList);
            outputStream.close();
        } catch (EOFException e) {
            System.out.println("end of the file");
        } catch (IOException e) {
            System.out.println("problem occured while writing to the binary file");
        }
    }

    public static int controlZipWithBinarySearch(ArrayList<Place> places, String target) {
        //method which returns the index of target element after the binarySearch.
        int first = 0;
        int last = places.size() - 1;
        while (first <= last) {
            int mid = (first + last) / 2;
            if (places.get(mid).getZipcode().equals(target)) {
                return mid;
            } // using compareTo is more apropriate way because every zip code contains 5 digits
            // there can be one or multiple 0 before zips
            // if target smaller than mid element
            else if (places.get(mid).getZipcode().compareTo(target) > 0) {
                last = mid - 1;
            } // if target greater than mid element
            else if (places.get(mid).getZipcode().compareTo(target) < 0) {
                first = mid + 1;
            }
        }
        // element not in the list.
        return -1;
    }

    public static ArrayList<Place> readFromBinaryFile() {
        // method for reading informations from binnaryFile which we created before.
        ArrayList<Place> places = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("database.out"));
            places = (ArrayList<Place>) inputStream.readObject();
        } catch (EOFException e) {

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("problem occured while reading forom file");
        }

        return places;
    }

}
