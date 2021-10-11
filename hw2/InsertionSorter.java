package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Aaron Goff
 *
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

@SuppressWarnings("unused")
public class InsertionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		algorithm = "InsertionSort";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override

	// Run method, O(n^2) worst case scenario
	public void sort() {

		// Starting from left most elements, load point with points[i] to prepare for
		// insertion sort
		for (int i = 0; i < points.length; i++) {
			int j = i - 1;
			Point point = points[i];

			// Move element downward in list until it finds a smaller element, then stop
			while (j > -1 && pointComparator.compare(points[j], point) > 0) {
				points[j + 1] = points[j];
				j--;
			}
			// Store element into new point[j] position
			j++;
			points[j] = point;
		}
	}
}
