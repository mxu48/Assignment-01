/* Goal: A class to test and time implementations of HybridSort and MergeSort.
* CS 245 - Assignment 1
* D.G. Brizan
* 10/17/2018
* Mina Xu @jxu74
*/
import java.util.Random;

public class Assignment01Test {
	
	protected int [] a;              /* Array to sort */
	protected SortingFactory factory;      	/* Creates different algorithms to use */
	protected String [] algorithms = {"mergesort", "hybridsort"}; /* Algorithms available to use */

	public Assignment01Test() {
		genArray(10000);
		factory = new SortingFactory(false);
	}
	
	public Assignment01Test(int maxSize) {
		genArray(maxSize);
		factory = new SortingFactory(false); // Do not use a default value for the algorithm name.
	}
	
	public Assignment01Test(int maxSize, String populationStrategy) {
		genArray(maxSize);
		factory = new SortingFactory(false); // Do not use a default value for the algorithm name.
	}
	
	/* Create array randomly */
	protected void genArray(int size) {
		a = new int[size];
		Random r = new Random();
		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt(100);
		}
	}
	

	/* Check if array is sorted */
	protected boolean isSorted(int [] a) {
		for (int i = 0; i < a.length-1; i++) {
			if (a[i] > a[i+1])
				return false;
		}
		return true;
	}
	
	/* Prints whether array sort has been successful */
	public void printStatus(int [] a) {
		System.out.print(a.length + "\t");
		if (isSorted(a))
			System.out.println("** SORTED **");
		else
			System.out.println("** NOT SORTED **");
	}
	
	/* Makes copies of the array to use in different sort algorithms */
	public int [] copyArray() {
		int [] copy = new int[a.length];
		System.arraycopy(a, 0, copy, 0, a.length);
		return copy;
	}
	
	/* Sends array copies to factory to compare the success and timing of different sorting algorithms */
	public void printSortingTiming() {
		for (String alg : algorithms) {
			try {
				SortingAlgorithm sort = factory.getSortingAlgorithm(alg);
				System.out.print(alg + "\t");
				int [] copy = copyArray();
				/* Start time */
				long start = System.currentTimeMillis();
				/* Call sort - on each algorithm */
				sort.sort(copy);
				long totalTime = System.currentTimeMillis() - start;
				/* End time */
				System.out.print(totalTime + " ms.\t");
				/* Check success */
				printStatus(copy);
			} catch (Exception e) {
				System.out.println("System was unable to sort array using " + alg);
			}
		}
	}

	
	public static void main(String[] args) {
		Assignment01Test timedTest = new Assignment01Test();
		int [] sizes = {50, 100, 500, 1000, 5000, 10000, 50000, 55000};
		for(int size : sizes) {
			timedTest.genArray(size);
			timedTest.printSortingTiming();
		}
		
	}

}