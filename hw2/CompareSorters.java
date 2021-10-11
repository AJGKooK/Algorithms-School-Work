package edu.iastate.cs228.hw2;

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Aaron Goff
 *
 */

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {

		Scanner user_input = new Scanner(System.in);

		System.out.println(
				"Performances of Four Sorting Algorithms in Point Scanning\nkeys: 1(random integers) 2 (file input) 3 (exit)");

		int userIn = user_input.nextInt();
		int numOf = 0;
		while (userIn == 1 || userIn == 2) { // While loop in order to count number of final iterations
			numOf++;
			System.out.println("Trial " + numOf + ": " + numOf);

			// Random integer input
			if (userIn == 1) {
				System.out.println("Enter number of random points: ");
				String numOfRand = user_input.next();
				int result = Integer.parseInt(numOfRand);

				// Generate random points
				Random rand = new Random();
				Point[] point = generateRandomPoints(result, rand);
				System.out.println("---------------------------------");

				// Run sorting cases
				RotationalPointScanner[] scanners = new RotationalPointScanner[4];
				for (int i = 0; i < scanners.length; i++) {
					scanners[i] = new RotationalPointScanner(point, Algorithm.values()[i]);
					scanners[i].scan();
					System.out.println(scanners[i].stats());
					scanners[i].draw();
					scanners[i].writePointsToFile();
				}

			}
			// File input
			if (userIn == 2) {
				System.out.println("Points from a file\nFile name: ");

				try {
					// Take file name from user
					String fileName = user_input.next();
					System.out.println("---------------------------------");

					// Run sorting cases
					RotationalPointScanner[] scanners = new RotationalPointScanner[4];
					for (int i = 0; i < scanners.length; i++) {
						scanners[i] = new RotationalPointScanner(fileName, Algorithm.values()[i]);
						scanners[i].scan();
						System.out.println(scanners[i].stats());
						scanners[i].draw();
						scanners[i].writePointsToFile();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			System.out.println(
					"---------------------------------\n\nPerformances of Four Sorting Algorithms in Point Scanning\nkeys: 1(random integers) 2 (file input) 3 (exit)");
			userIn = user_input.nextInt();

		}
		// Exit
		if (userIn == 3) {
			user_input.close();
			System.out.println("Exiting\nTotal number of iterations: " + numOf);
		} else {
			System.out.println("Incorrect user input");
		}

	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] �
	 * [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException("Points less than 1");
		} else {
			Point temp;
			Point[] point = new Point[numPts];
			Random random = rand;
			// Creating pseudo-random numbers between -50 and 50
			for (int i = 0; i < numPts; i++) {
				temp = new Point(random.nextInt(101) - 50, random.nextInt(101) - 50);
				point[i] = temp;

			}
			return point;
		}

	}

}
