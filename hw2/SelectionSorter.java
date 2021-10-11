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
 * This class implements selection sort.
 *
 */

@SuppressWarnings("unused")
public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "SeletionSort";
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 */
	@Override

	// Run method, O(n^2) worst case scenario
	public void sort() {
		// Load elements starting from the left most element
		for (int i = 0; i < points.length; i++) {
			// Load comparison value
			for (int j = i + 1; j < points.length; j++) {
				// if element point[j] is smaller than element point[i] then swap
				if (pointComparator.compare(points[j], points[i]) < 0) {
					swap(points, i, j);
				}
			}
		}
	}

	// Swap points
	private void swap(Point[] points, int i, int j) {
		Point swap = points[i];
		points[i] = points[j];
		points[j] = swap;
	}
}