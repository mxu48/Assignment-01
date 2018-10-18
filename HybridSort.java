/* Goal: Attempts to utlize a combination of sorting algorithms to sort an array more efficiently than MergeSort. 
* CS 245 - Assignment 1
* D.G. Brizan
* 10/17/2018
* Mina Xu @jxu74
*/
import java.util.*;
public class HybridSort implements SortingAlgorithm {

	public void sort (int [] a) {
		//Size of a sequence of ascending numbers - a sorted regions, do not have to sort this
		int runSize = 200;
		ArrayList <int []> runs = getRuns(a, runSize);
		ArrayList <int []> regions = getRegions(a, runSize, runs);
		hybridSort(a, runs, regions);

	}

	public static ArrayList getRuns (int [] a, int runSize) {
		int runsInd = -1; /* Index to keep track of number ascending numbers */
		int start = -1; /* Start of run */
		int end = -1; /* End of run */
		int endRun = -1; /* Where the last run left off */
		int last = -1; /* Last run before rest */
		int ind = 0; /* Index of runs array */
		
		ArrayList <int[]> runs = new ArrayList <int[]>(); /* ArrayList of int [] containing indeces of runs */

		for(int i = 0; i < a.length-1; i++) {
			int next = i + 1;
			int prev = i - 1;
			/* Check if can be start of a run */
			if(a[i] <= a[next]){
				/* If current index was last element of a run, do not count as new run */
				if((i != endRun)) {
					/* If previous was greater or previous was end of a run, can count current as new start of run */
					if(prev >= 0 && (a[prev] > a[i] || a[prev] == endRun)) {
						runsInd = 0; /* Initialize runsInd */
						start = i;
					} else if (i == 0) {
						runsInd = 0;
						start = i;
					}
				}
				runsInd++; /* Increment number of consecutive asceding nums */

			} else {
				/* If first in array and next is less than current - single */
				if(i == 0 && a[next] <= a[i]) {
					start = i; 
					end = i; 
					runsInd = 0;
				}
				/* If not first in array */
				if(prev >= 0) {
					if(a[next] <= a[i] && a[prev] >= a[i]) { /* If next is less current and previous is greater than current - single */
						start = i;
						end = i;
						runsInd = 0;
					} else if(a[next] < a[i] && runsInd < runSize-1) { /* Not a single, but not yet at end of possible run */
						runsInd = 0;
					} 
				}
			}

			/* If run is good size, mark end and create a runArr to add to arraylist of runs */ 
			if(runsInd >= runSize-1 && i != endRun) {
				if(a[i] > a[next] || a[i] == a.length-1) {
					end = start+(runsInd);
					/* Create array to hold indeces of run */
					int [] runArr = new int [2];
					runArr[0] = start;
					runArr[1] = end;
					/* Add run to runs arraylit */
					runs.add(ind, runArr);
					ind++; 
					runsInd = 0; /* Reset num of runInd to catch next run start */
				}
				
			} /* End add to array */
			endRun = end; /* Mark last end */
			last = endRun; /* For rest */
		} /* Close for */


		/* Get rest of array as run if valid */
		runsInd = 0;
		for(int rest = last+1; rest <= a.length-1; rest++) {
			start = rest;
			if(rest != a.length-1) { /* If not last */
				if(a[rest] < a[rest+1]) { /* If ascending */
					if(runsInd >= runSize-1) {
						runsInd++;
						end = a.length-1;
						int [] runArr = new int [2];
						runArr[0] = start;
						runArr[1] = end;
						runs.add(ind, runArr);
						ind++; 
						rest = end;
					}
				} 
			} else if(runsInd >= runSize-1) { /* If at end and is valid run */
				end = a.length-1;
				int [] runArr = new int [2];
				runArr[0] = start;
				runArr[1] = end;
				runs.add(ind, runArr);
				ind++; 
			}
		}
		return runs;
	}

	public static boolean emptyRun (ArrayList <int[]> runs) { /* Check if runs arraylist is empty */
		if(runs.size() == 0) {
			return true;
		}
		return false;	
	}

	public static ArrayList getRegions(int [] a, int runSize, ArrayList <int []> runs) {
		int regionStart = 0; /* Start of region */
		int regionEnd = 0; /* End of region */
		int endIndex = 0; /* Start of run - where region ends */
		int ind = 0; /* Index of regions arraylist */
		int startIndex = 0; /* End of run - where next region begins */
		int max = 0; /* Max size of region */
		int index = 0; /* Location in original list a */
		ArrayList <int[]> regions = new ArrayList <int[]>();
	
		if(index < a.length) {
			if(!emptyRun(runs)) {
				for(int q = 0; q < runs.size(); q++) { /* For each runs, get space in array inbetween runs */
					endIndex = runs.get(q)[0];
					startIndex = runs.get(q)[1] + 1;
					while(index < endIndex) {
						if(index + runSize-1 < endIndex) { /* If possible to reach runSize */
							max = runSize;
						} else {
							max = endIndex - index; /* If regions is < runRize */
						}
	 					for(int j = 0; j < max; j++) { /* Get region */
							regionStart = index;
							regionEnd = index + j;
						}
							int [] regArr = new int [2];
							regArr[0] = regionStart;
							regArr[1] = regionEnd;
							regions.add(ind, regArr);
							ind++; 
							index = index + max;

						if (endIndex == 0 || endIndex == 1) {
							regionStart = regionStart;
							regionEnd = regionEnd;
						}
					} /* End while */
					index = startIndex;
				} /* End For Checking runs */
			}
				
			/* Mark rest of array into regions */
			for(int i = index; i <=  a.length-1; i++) {
				if(i + runSize-1 < a.length-1) {
					max = runSize;
				} else {
					max = a.length-1 - i;
				}
				regionStart = i;
				regionEnd = regionStart + max;
				
				int [] regArr = new int [2];
				regArr[0] = regionStart;
				regArr[1] = regionEnd;
				regions.add(ind, regArr);
				ind++; 
				i = i + max;
			}

		} /* End get regions */
				
		for(int [] r : regions) { /* Sort each region in regions arraylist */
			int lower = r[0];
			int upper = r[1];
			sortRegions(a, lower, upper);
		}
		return regions;
	}


	public static void sortRegions (int [] a, int lower, int upper) {
	 	//Sort all sequences of runSize or smaller outside runs - regions between runs -  w/ in-place algorithm quickSort
		quickSort(a, lower, upper);
	}

	public static void swap(int [] a, int x, int y) {
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}

	public static int partition(int [] a, int lower, int upper) {
		/* Set pivot to be first val */
		int pivot = lower;
		/* Begin after pivot until end of array */
		int start = lower+1;
		int end = upper;

		if(lower == upper) { /* If same val - no need to sort */
			return lower;
		}
		if(start == end){ /* No change */
			start = start;
			end = end;
		}

		/* Check that index of lower is less than index of upper */
		while(start < end) {
			/* If val at start is greater than val at end, swap the two */
			if(a[start] >= a[end]) {
				swap(a, start, end);
			}
			/* Move up while val at start is less than val at pivot */
			while(a[start] <= a[pivot]) { 
				if(start < upper) {
					start++;
				} else {
					break;
				}
			}
			/* Move down while val at end is greater than val at pivot */
			while(a[end] > a[pivot]) {
				if(end >= start) {
					end--;
				} else {
					break;
				}
				
			}
			/* Check if left side < right side, swap if needed */
			if(start <= upper && start < end) {
				swap(a, start, end);
				start++;
				end--;
			}
		}
		/* Pivot in place where upper left off = left side now less than pivot, right side greater than pivot */
		if(a[end] < a[pivot]) {
			swap(a, pivot, end);
		}
		return end;
	} /* Close partition */

	public static void quickSort(int [] a, int lower, int upper) { 	/* In-place algorithm to sort the regions */
		int pivot = partition(a, lower, upper);
		if(lower < pivot-1) {
			quickSort(a, lower, pivot-1);
		}
		if(pivot+1 < upper) {
			quickSort(a, pivot+1, upper);
		}
	} /* Close quickSort */


	public static void hybridSort (int [] a, ArrayList<int[]> runs, ArrayList <int[]> regions) {
		/* Recursively merge adjacent pairs of runs and regions int one large sorted array */
		int left = 0;
		int right = a.length-1;
		/* Get new runs array after sorted runs and regions list - list is all runs, runSize is variable */
		ArrayList <int[]> totalRuns = new ArrayList <int[]> ();
		totalRuns = getRuns(a, 1); /* Default runSize of 1 to get all variable runSize runs */
		int numRuns = totalRuns.size(); /* Number of runs in orignal array is the size of runs arraylist */
		int lastEnd = 0;
		int lastStart = 0;

		while(numRuns > 1) { /* End when only single run = entire sorted array */

			for(int i = 0; i < totalRuns.size()-1; i+=2) {
				/* Get left array = current run, right array = next run and merge into sorted array */
				int [] leftIndexes = totalRuns.get(i);
				int [] rightIndexes = totalRuns.get(i+1);
				merge(a, left, right, totalRuns, leftIndexes, rightIndexes);
			}
			totalRuns = getRuns(a, 1); /* Get new runs from updated array */
			numRuns = totalRuns.size(); /* Update new number of runs w/ merged array */
		}

	}

	public static void merge(int [] a, int left, int right, ArrayList <int[]> totalRuns, int [] leftIndexes, int [] rightIndexes) {
		/* With runs input - recursively merge adjacent pairs */
		/* Left index of left array and right index of right array */
		int leftInd = 0;
		int rightInd = 0;
		int target = 0; /* Index of target sorted array */

		int leftLen = leftIndexes[1] - leftIndexes[0] + 1; /* Length of leftArray */
		int rightLen = rightIndexes[1] - rightIndexes[0] + 1; /* Length of rightArray */
		/* Subarrays specifed by the indexes */
		int [] leftArr = new int[leftLen];
		int [] rightArr = new int[rightLen];
		/* Array to merge into */
		int [] targetArr = new int [leftLen + rightLen];

		/* Copy values into leftArr specified by left indeces set */
		for(int i = leftIndexes[0]; i <= leftIndexes[1]; i++) {
			leftArr[i-leftIndexes[0]] = a[i];
		}

		/* Copy values into rightArr specified by right indeces set */
		for(int j = rightIndexes[0]; j <= rightIndexes[1]; j++) {
			rightArr[j-rightIndexes[0]] = a[j];
		}

		/* Compare both arrays to find smaller val to add to targetArr */
		while (leftInd < leftArr.length && rightInd < rightArr.length) {
			/* If leftInd of leftArr is less than rightInd of rightArr, copy left val into target */
            if (leftArr[leftInd] <= rightArr[rightInd]) {
                targetArr[target] = leftArr[leftInd];
                leftInd++;
            } else {
            	/* If rightInd of rightArr is less than leftInd of leftArr, copy right val into target */
                targetArr[target] = rightArr[rightInd];
                rightInd++;
            }
            target++;
        }
        /* Copy remaining sorted values from the right or left array into target array */
        while (leftInd < leftArr.length) {
            targetArr[target] = leftArr[leftInd];
            leftInd++;
            target++;
        }
        while (rightInd < rightArr.length) {
            targetArr[target] = rightArr[rightInd];
            rightInd++;
            target++;
        }

		/* Copy sorted target back into array a */
		for(int i = 0; i < targetArr.length; i++) {
            int indexToCopy = (leftIndexes[0] + i);
			a[indexToCopy] = targetArr[i];
		}

    } /* Close merge */

}