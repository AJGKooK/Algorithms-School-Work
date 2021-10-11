package edu.iastate.cs228.hw5;


import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Aaron Goff
 */

/**
 * The Transactions class simulates video transactions at a video store.
 */
public class Transactions {

    /**
     * The main method generates a simulation of rental and return activities.
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        VideoStore videostore = new VideoStore("videoList1.txt"); // Initial file input
        Scanner scannerInt = new Scanner(System.in); // Scanner for int
        Scanner scannerStr = new Scanner(System.in); // Scanner for String
        boolean trigger = true; // While trigger, to keep looping while true.

        System.out.print("Transactions at a Video Store\n" +
                "keys: 1 (rent)      2 (bulk rent)\n" +
                "      3 (return)    4 (bulk return)\n" +
                "      5 (summary)   6 (exit)\n\n" +
                "Transaction: ");

        while (trigger) {
            int userIn = scannerInt.nextInt();

            /*
            rent command
             */
            if (userIn == 1) {
                System.out.print("Film to rent: ");
                String userInput = scannerStr.nextLine();
                String filmName = videostore.parseFilmName(userInput);

                try {
                    videostore.videoRent(filmName, videostore.parseNumCopies(userInput));
                } catch (FilmNotInInventoryException e) {
                    System.out.println("Film " + userInput + " is not in inventory");
                } catch (AllCopiesRentedOutException e) {
                    System.out.println("Film " + userInput + " has been rented out");
                }
                System.out.print("\nTransaction: ");
            }


            /*
            bulk rent command
             */
            else if (userIn == 2) {
                System.out.print("Video file (rent): ");

                try {
                    videostore.bulkRent(scannerStr.nextLine());
                } catch (FilmNotInInventoryException e) {
                    e.printStackTrace();
                } catch (AllCopiesRentedOutException e) {
                    e.printStackTrace();
                }
                System.out.print("\nTransaction: ");
            }

             /*
             return command
              */
            else if (userIn == 3) {
                System.out.print("Film to return: ");
                String userInput = scannerStr.nextLine();
                String filmName = videostore.parseFilmName(userInput);
                try {
                    videostore.videoReturn(filmName, videostore.parseNumCopies(userInput));
                } catch (FilmNotInInventoryException e) {
                    e.printStackTrace();
                }

                System.out.print("\nTransaction: ");
            }

             /*
             bulk return command
              */
            else if (userIn == 4) {

                System.out.print("Video file (return): ");

                try {
                    videostore.bulkReturn(scannerStr.nextLine());
                } catch (FilmNotInInventoryException e) {
                    e.printStackTrace();
                }
                System.out.print("\nTransaction: ");
            }

             /*
             summary command
              */
            else if (userIn == 5) {
                System.out.println(videostore.transactionsSummary());
                System.out.print("\nTransaction: ");
            }

             /*
             exit command
              */
            else if (userIn == 6) {
                trigger = false;
            } else {
                // empty catch
            }
        }
    }
}
