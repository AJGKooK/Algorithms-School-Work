package edu.iastate.cs228.hw5;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aaron Goff
 */

public class VideoStore {
    protected SplayTree<Video> inventory;     // all the videos at the store

    // ------------
    // Constructors
    // ------------

    /**
     * Default constructor sets inventory to an empty tree.
     */
    public VideoStore() {
        // no need to implement.
    }


    /**
     * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of
     * the project description for details regarding the format of a video file.
     * <p>
     * Calls setUpInventory().
     *
     * @param videoFile no format checking on the file
     * @throws FileNotFoundException
     */
    public VideoStore(String videoFile) throws FileNotFoundException {
        if (videoFile == null)
            throw new FileNotFoundException();

        setUpInventory(videoFile);
    }


    /**
     * Accepts a video file to initialize the splay tree inventory.  To be efficient,
     * add videos to the inventory by calling the addBST() method, which does not splay.
     * <p>
     * Refer to Section 3.2 for the format of video file.
     *
     * @param videoFile correctly formated if exists
     * @throws FileNotFoundException
     */
    public void setUpInventory(String videoFile) throws FileNotFoundException {
        inventory = new SplayTree<>();
        bulkImport(videoFile);
    }


    // ------------------
    // Inventory Addition
    // ------------------

    /**
     * Find a Video object by film title.
     *
     * @param film
     * @return
     */
    public Video findVideo(String film) {
        return inventory.findElement(new Video(film)); // find video
    }


    /**
     * Updates the splay tree inventory by adding a number of video copies of the film.
     * (Splaying is justified as new videos are more likely to be rented.)
     * <p>
     * Calls the add() method of SplayTree to add the video object.
     * <p>
     * a) If true is returned, the film was not on the inventory before, and has been added.
     * b) If false is returned, the film is already on the inventory.
     * <p>
     * The root of the splay tree must store the corresponding Video object for the film. Update
     * the number of copies for the film.
     *
     * @param film title of the film
     * @param n    number of video copies
     */
    public void addVideo(String film, int n) {
        if (!available(parseFilmName(film))) {  // If list does not contain film, add film and n amount
            inventory.add(new Video(film, n));
        } else {
            inventory.root.data.addNumCopies(n); // If list does contain film, increase amount by n
        }
    }


    /**
     * Add one video copy of the film.
     *
     * @param film title of the film
     */
    public void addVideo(String film) {
        addVideo(parseFilmName(film), parseNumCopies(film));  // add video(s)
    }


    /**
     * Update the splay trees inventory by adding videos.  Perform binary search additions by
     * calling addBST() without splaying.
     * <p>
     * The videoFile format is given in Section 3.2 of the project description.
     *
     * @param videoFile correctly formatted if exists
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(videoFile));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            inventory.addBST(new Video(parseFilmName(line), parseNumCopies(line))); // import video(s)
        }
    }


    // ----------------------------
    // Video Query, Rental & Return
    // ----------------------------

    /**
     * Search the splay tree inventory to determine if a video is available.
     *
     * @param film
     * @return true if available
     */
    public boolean available(String film) {
        if (inventory.contains(film))  // return true if list contains film
            return true;
        else
            return false;
    }


    /**
     * Update inventory.
     * <p>
     * Search if the film is in inventory by calling findElement(new Video(film, 1)).
     * <p>
     * If the film is not in inventory, prints the message "Film <film> is not
     * in inventory", where <film> shall be replaced with the string that is the value
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * <p>
     * If there is at least one available copy but n is greater than the number of
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.
     *
     * @param film
     * @param n
     * @throws IllegalArgumentException    if n <= 0 or film == null or film.isEmpty()
     * @throws FilmNotInInventoryException if film is not in the inventory
     * @throws AllCopiesRentedOutException if there is zero available copy for the film.
     */
    public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException,
            AllCopiesRentedOutException {
        Video video = inventory.findElement(new Video(film, n));
        if (video == null)
            throw new FilmNotInInventoryException();

        int numAvailableCopies = video.getNumAvailableCopies();

        if (n <= 0 || film == null || film.isEmpty())
            throw new IllegalArgumentException();
        if (numAvailableCopies <= 0)
            throw new AllCopiesRentedOutException();
        if (numAvailableCopies < n)
            video.rentCopies(numAvailableCopies);  // rent maximum amount of videos
        else
            video.rentCopies(n); // rent n number of videos
    }


    /**
     * Update inventory.
     * <p>
     * 1. Calls videoRent() repeatedly for every video listed in the file.
     * 2. For each requested video, do the following:
     * a) If it is not in inventory or is rented out, an exception will be
     * thrown from videoRent().  Based on the exception, prints out the following
     * message: "Film <film> is not in inventory" or "Film <film>
     * has been rented out." In the message, <film> shall be replaced with
     * the name of the video.
     * b) Otherwise, update the video record in the inventory.
     * <p>
     * For details on handling of multiple exceptions and message printing, please read Section 3.4
     * of the project description.
     *
     * @param videoFile correctly formatted if exists
     * @throws FileNotFoundException
     * @throws IllegalArgumentException    if the number of copies of any film is <= 0
     * @throws FilmNotInInventoryException if any film from the videoFile is not in the inventory
     * @throws AllCopiesRentedOutException if there is zero available copy for some film in videoFile
     */
    public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException,
            FilmNotInInventoryException, AllCopiesRentedOutException {
        Scanner scanner = new Scanner(new File(videoFile));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue; // if there is an empty line, skip it
            String filmName = parseFilmName(line);
            try {
                videoRent(filmName, parseNumCopies(line));  // rent video(s)
            } catch (AllCopiesRentedOutException ex) {
                System.out.println("Film " + filmName + " has been rented out");
            } catch (FilmNotInInventoryException ex) {
                System.out.println("Film " + filmName + " is not in inventory");
            } catch (IllegalArgumentException ex) {
                System.out.println("Film " + filmName + " has an invalid request");
            }
        }
    }


    /**
     * Update inventory.
     * <p>
     * If n exceeds the number of rented video copies, accepts up to that number of rented copies
     * while ignoring the extra copies.
     *
     * @param film
     * @param n
     * @throws IllegalArgumentException    if n <= 0 or film == null or film.isEmpty()
     * @throws FilmNotInInventoryException if film is not in the inventory
     */
    public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException {
        Video video = inventory.findElement(new Video(film, n));
        if (n <= 0 || film == null || film.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int numRentedCopies = video.getNumRentedCopies();
        if (numRentedCopies <= n) {
            video.returnCopies(numRentedCopies); // return maximum amount of videos
        } else
            video.returnCopies(n); // return n number of videos
    }


    /**
     * Update inventory.
     * <p>
     * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section
     * 3.4 of the project description on how to handle multiple exceptions.
     *
     * @param videoFile
     * @throws FileNotFoundException
     * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
     * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
     */
    public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException,
            FilmNotInInventoryException {
        Scanner scanner = new Scanner(new File(videoFile));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue; // if there is an empty line, skip it
            String filmName = parseFilmName(line);
            try {
                videoReturn(filmName, parseNumCopies(line));  // return video(s)
            } catch (FilmNotInInventoryException ex) {
                System.out.println("Film " + filmName + " is not in inventory");
            } catch (IllegalArgumentException ex) {
                System.out.println("Film " + filmName + " has an invalid request");
            }
        }
    }


    // ------------------------
    // Methods without Splaying
    // ------------------------

    /**
     * Performs inorder traversal on the splay tree inventory to list all the videos by film
     * title, whether rented or not.  Below is a sample string if printed out:
     * <p>
     * <p>
     * Films in inventory:
     * <p>
     * A Streetcar Named Desire (1)
     * Brokeback Mountain (1)
     * Forrest Gump (1)
     * Psycho (1)
     * Singin' in the Rain (2)
     * Slumdog Millionaire (5)
     * Taxi Driver (1)
     * The Godfather (1)
     *
     * @return
     */
    public String inventoryList() {
        Iterator<Video> it = inventory.iterator();
        String temp = "Films in inventory:\n\n";

        while (it.hasNext()) {
            Video video = it.next();
            temp += video.getFilm() + " (" + video.getNumCopies() + ")\n";
        }
        return temp;
    }


    /**
     * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format,
     * see Transaction 5 in the sample simulation in Section 4 of the project description.
     *
     * @return
     */
    public String transactionsSummary() {
        return ("Rented films:\n\n" + rentedVideosList() + "\nFilms remaining in inventory:\n\n" + unrentedVideosList());
    }

    /**
     * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
     * <p>
     * Below is a sample return string when printed out:
     * <p>
     * Rented films:
     * <p>
     * Brokeback Mountain (1)
     * Forrest Gump (1)
     * Singin' in the Rain (2)
     * The Godfather (1)
     *
     * @return
     */
    private String rentedVideosList() {
        Iterator<Video> it = inventory.iterator();
        String temp = "";

        while (it.hasNext()) {
            Video video = it.next();
            if (video.getNumRentedCopies() == 0) {
                // Do nothing
            } else
                temp += video.getFilm() + " (" + video.getNumRentedCopies() + ")\n";
        }
        return temp;
    }


    /**
     * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
     * Prints only the films that have unrented copies.
     * <p>
     * Below is a sample return string when printed out:
     * <p>
     * <p>
     * Films remaining in inventory:
     * <p>
     * A Streetcar Named Desire (1)
     * Forrest Gump (1)
     * Psycho (1)
     * Slumdog Millionaire (4)
     * Taxi Driver (1)
     *
     * @return
     */
    private String unrentedVideosList() {
        Iterator<Video> it = inventory.iterator();
        String temp = "";

        while (it.hasNext()) {
            Video video = it.next();
            if (video.getNumAvailableCopies() == 0) {
                // Do nothing
            } else
                temp += video.getFilm() + " (" + video.getNumAvailableCopies() + ")\n";
        }
        return temp;
    }


    /**
     * Parse the film name from an input line.
     *
     * @param line
     * @return
     */
    public static String parseFilmName(String line) {
        line = line.split("\\(")[0];
        return line.trim();
    }

    /**
     * Parse the number of copies from an input line.
     *
     * @param line
     * @return
     */
    public static int parseNumCopies(String line) {
        Pattern r = Pattern.compile("\\((\\d+)\\)");
        Matcher m = r.matcher(line);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else
            return 1;
    }
}