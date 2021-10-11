package edu.iastate.cs228.hw2;

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Aaron Goff
 *
 */

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later the sorted) sequence.
 *
 */
@SuppressWarnings("unused")
public abstract class AbstractSorter {

	protected Point[] points; // array of points operated on by a sorting algorithm.
								// stores ordered points after a call to sort().

	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
										// "quicksort". Initialized by a subclass constructor.

	protected Comparator<Point> pointComparator = null;

	private Point referencePoint = null; // common reference point for computing the polar angle

	// Add other protected or private instance variables you may need.

	protected AbstractSorter() {
		// No implementation needed. Provides a default super constructor to subclasses.
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter,
		// and QuickSorter.
	}

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		if (pts == null) {
			throw new IllegalArgumentException("pts == null");
		} else if (pts.length == 0) {
			throw new IllegalArgumentException("pts.length == 0");
			// Copy points into the array points[]
		} else {
			points = new Point[pts.length];
			for (int i = 0; i < pts.length; i++) {
				points[i] = pts[i];
			}

		}

	}

	/**
	 * 
	 * @param p
	 * @throws IllegalArgumentException if p == null
	 */
	public void setReferencePoint(Point p) throws IllegalArgumentException {
		if (p == null) {
			throw new IllegalArgumentException("p == null");
		} else {
			referencePoint = p;
		}

	}

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order ==
	 * 0, by y-coordinate if order == 1, and by polar angle with respect to
	 * referencePoint if order == 2. Assign the comparator to the variable
	 * pointComparator.
	 * 
	 * If order == 2, the method cannot be called when referencePoint == null. Call
	 * setRereferencePoint() first to set referencePoint.
	 * 
	 * Need to create an object of the PolarAngleComparator class and call the
	 * compareTo() method in the Point class.
	 * 
	 * @param order 0 by x-coordinate 1 by y-coordinate 2 by polar angle w.r.t
	 *              referencePoint
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 2
	 *                                  IllegalStateException if order == 2 and
	 *                                  referencePoint == null;
	 */
	public void setComparator(int order) throws IllegalArgumentException, IllegalStateException {

		if (order == 0) {
			// Compare x
			pointComparator = new Comparator<Point>() {
				@Override
				public int compare(Point p1, Point p2) {
					return p1.getX() - p2.getX();
				}
			};
		} else if (order == 1) {
			// Compare y
			pointComparator = new Comparator<Point>() {
				@Override
				public int compare(Point p1, Point p2) {
					return p1.getY() - p2.getY();
				}
			};
		} else if (order == 2) {
			setReferencePoint(referencePoint);
			if (referencePoint == null) {
				throw new IllegalStateException("Cannot compare polar, referencePoint null");
			} else {
				// Compare polar
				pointComparator = new PolarAngleComparator(referencePoint);
			}
		} else {
			throw new IllegalArgumentException("setComparator out of bounds");
		}
	}

	/**
	 * Use the created pointComparator to conduct sorting.
	 * 
	 * Ought to be protected. Made public for testing.
	 */
	public abstract void sort();

	/**
	 * Obtain the point in the array points[] that has median index
	 * 
	 * @return median point
	 */
	public Point getMedian() {
		return points[points.length / 2];
	}

	/**
	 * Copies the array points[] onto the array pts[].
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts) {
		// Copy points[] onto pts[]
		for (int i = 0; i < pts.length; i++) {
			pts[i] = points[i];

		}
	}

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */

	// Swap values i and j
	protected void swap(int i, int j) {
		int tempi = i;
		int tempj = j;

		i = tempj;
		j = tempi;

	}
}
