
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
 * This class implements the mergesort algorithm.
 *
 */

@SuppressWarnings("unused")
public class MergeSorter extends AbstractSorter {
	// Other private instance variables if you need ...
	int n;

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "Merge Sort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		mergeSortRec(points);

		/**
		 * This is a recursive method that carries out mergesort on an array pts[] of
		 * points. One way is to make copies of the two halves of pts[], recursively
		 * call mergeSort on them, and merge the two sorted subarrays into pts[].
		 * 
		 * @param pts point array
		 */
	}

	// Run recursive method, O(n log(n)) worst case scenario
	private void mergeSortRec(Point[] pts) {
		if (pts.length <= 1) {
			return;
		}
		Point[] right, left;

		// Creating new Point[]'s at half length
		right = new Point[pts.length / 2];
		left = new Point[pts.length / 2];

		// Run if even number
		if ((pts.length) % 2 == 0) {

			// Populating each half of the new array's
			System.arraycopy(pts, 0, left, 0, left.length);
			System.arraycopy(pts, left.length, right, 0, right.length);

			// Run if odd number
		} else {
			// Compensating for odd number
			right = new Point[pts.length - left.length];

			// Populating each half of the new array's
			System.arraycopy(pts, 0, left, 0, left.length);
			System.arraycopy(pts, left.length, right, 0, right.length);
		}
		// Recursively creating new arrays until pts.length <= 1
		mergeSortRec(right);
		mergeSortRec(left);

		// Reassemble
		reassemble(pts.length, left.length, right.length, left, right, pts);

	}

	// Sorting and reassembly of the arrays
	private void reassemble(int ptsLength, int leftLength, int rightLength, Point[] left, Point[] right, Point[] pts) {
		int j = 0, k = 0;

		// Run until ptsLength
		for (int i = 0; i < ptsLength; i++) {

			// If j == leftLength copy array
			if (j == leftLength) {
				System.arraycopy(right, k, pts, i, rightLength - k);
				break;

				// If k == rightLength copy array
			} else if (k == rightLength) {
				System.arraycopy(left, j, pts, i, leftLength - j);
				break;

				// Load pts[i] with left[j+1]
			} else if (pointComparator.compare(left[j], right[k]) <= 0) {
				pts[i] = left[j++];

				// Load pts[i] with right[k+1]
			} else {
				pts[i] = right[k++];
			}
		}
	}
}
