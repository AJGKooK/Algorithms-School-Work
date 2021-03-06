package edu.iastate.cs228.hw2;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  
 * @author Aaron Goff
 *
 */

/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class RotationalPointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected String outputFileName; // "select.txt", "insert.txt", "merge.txt", or "quick.txt"

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[]. Set
	 * outputFileName.
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public RotationalPointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("pts null or pts.length is 0");
		}
		// Copy points into array points[]
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}

		// Select Algorithm
		this.sortingAlgorithm = algo;

		// Set outputFileName
		switch (sortingAlgorithm) {
		case SelectionSort:
			outputFileName = "select.txt";
			break;
		case InsertionSort:
			outputFileName = "insert.txt";
			break;
		case MergeSort:
			outputFileName = "merge.txt";
			break;
		case QuickSort:
			outputFileName = "quick.txt";
			break;
		default:
			break;
		}
	}

	/**
	 * This constructor reads points from a file. Set outputFileName.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected RotationalPointScanner(String inputFileName, Algorithm algo)
			throws FileNotFoundException, InputMismatchException {

		// Read file
		ArrayList<Point> pointsList = new ArrayList<Point>();
		try {
			@SuppressWarnings("resource")
			Scanner fileScanner = new Scanner(new File(inputFileName));
			while (fileScanner.hasNextInt()) {
				Point p = new Point(fileScanner.nextInt(), fileScanner.nextInt());
				pointsList.add(p);
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found within protected constructor");

		}

		if (pointsList.size() * 2 % 2 != 0) {
			System.out.println(pointsList.size());
			throw new InputMismatchException("Input file contains an odd number of integers");

		}

		// Load pts with pointsList
		Point[] pts = new Point[pointsList.size()];
		for (int i = 0; i < pointsList.size(); i++) {
			pts[i] = pointsList.get(i);
		}

		this.points = pts;
		this.sortingAlgorithm = algo;

		// Set outputFileName
		switch (sortingAlgorithm) {
		case SelectionSort:
			outputFileName = "select.txt";
			break;
		case InsertionSort:
			outputFileName = "insert.txt";
			break;
		case MergeSort:
			outputFileName = "merge.txt";
			break;
		case QuickSort:
			outputFileName = "quick.txt";
			break;
		default:
			break;
		}
	}

	/**
	 * Carry out three rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates. d) Sort points[] again by the polar angle with respect to
	 * medianCoordinatePoint.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting. Copy the
	 * sorting result back onto the array points[] by calling the method getPoints()
	 * in AbstractSorter.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		AbstractSorter aSorter = null;
		long startTime;

		switch (sortingAlgorithm) {
		case SelectionSort:
			aSorter = new SelectionSorter(points);
			break;
		case InsertionSort:
			aSorter = new InsertionSorter(points);
			break;
		case MergeSort:
			aSorter = new MergeSorter(points);
			break;
		case QuickSort:
			aSorter = new QuickSorter(points);
			break;
		}

		scanTime = 0;

		// sort by x
		aSorter.setComparator(0);

		startTime = System.nanoTime();
		aSorter.sort();
		scanTime += System.nanoTime() - startTime;

		int medianX = aSorter.getMedian().getX();

		// sort by y
		aSorter.setComparator(1);

		startTime = System.nanoTime();
		aSorter.sort();
		scanTime += System.nanoTime() - startTime;

		int medianY = aSorter.getMedian().getY();

		medianCoordinatePoint = new Point(medianX, medianY);
		aSorter.setReferencePoint(medianCoordinatePoint);
		// sort by polar angle
		aSorter.setComparator(2);

		startTime = System.nanoTime();
		aSorter.sort();
		scanTime += System.nanoTime() - startTime;

		// copy sorted points back into local variable
		aSorter.getPoints(points);

		// create an object to be referenced by aSorter according to sortingAlgorithm.
		// for each of the three
		// rounds of sorting, have aSorter do the following:
		//
		// a) call setComparator() with an argument 0, 1, or 2. in case it is 2, must
		// have made
		// the call setReferencePoint(medianCoordinatePoint) already.
		//
		// b) call sort().
		//
		// sum up the times spent on the three sorting rounds and set the instance
		// variable scanTime.

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		String sort = null;

		switch (sortingAlgorithm) {
		case SelectionSort:
			sort = "Selection Sort";
			break;
		case InsertionSort:
			sort = "Insertion Sort";
			break;
		case MergeSort:
			sort = "Mergesort";
			break;
		case QuickSort:
			sort = "Quicksort";
			break;
		default:
			break;
		}

		return sort + " " + points.length + " " + scanTime;
	}

	/**
	 * Write points[] after a call to scan(). When printed, the points will appear
	 * in order of polar angle with respect to medianCoordinatePoint with every
	 * point occupying a separate line. The x and y coordinates of the point are
	 * displayed on the same line with exactly one blank space in between.
	 */
	@Override
	public String toString() {
		String out = "";

		for (Point p : points) {
			if (!out.isEmpty())
				out += " ";
			out += p.getX() + " " + p.getY();
		}

		return out;
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException {
		try {
			PrintWriter pw = new PrintWriter(new File(outputFileName));
			pw.print(this.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found within writePointsToFile()");
		}
	}

	/**
	 * This method is called after each scan for visually check whether the result
	 * is correct. You just need to generate a list of points and a list of
	 * segments, depending on the value of sortByAngle, as detailed in Section 4.1.
	 * Then create a Plot object to call the method myFrame().
	 */
	public void draw() {
		int numSegs = points.length * 2; // number of segments to draw

		// Based on Section 4.1, generate the line segments to draw for display of the
		// sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order.
		Segment[] segments = new Segment[numSegs];
		int index = 0;
		for (int i = 0; i < points.length; i++) {
			segments[index++] = new Segment(points[i], medianCoordinatePoint);
		}

		for (int i = 0; i < points.length; i++) {
			segments[index++] = new Segment(points[i], points[(i + 1) % points.length]);
		}

		String sort = null;

		switch (sortingAlgorithm) {
		case SelectionSort:
			sort = "Selection Sort";
			break;
		case InsertionSort:
			sort = "Insertion Sort";
			break;
		case MergeSort:
			sort = "Mergesort";
			break;
		case QuickSort:
			sort = "Quicksort";
			break;
		default:
			break;
		}

		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, sort);

	}

}
