/* Goal: Implementation of MergeSort algorithm to compare with HybridSort.
* CS 245 - Assignment 1
* D.G. Brizan
* 10/17/2018
* Mina Xu @jxu74
*/
import java.util.Arrays;
public class MergeSort implements SortingAlgorithm {
	
	public void sort(int [] a) {
		/* Start and ending indexes of original array a */
		int left = 0;
		int right = a.length-1;
		mergeSort(a, left, right);
	}

	public void mergeSort(int [] a, int left, int right) {
		/* Base case */
		if(left >= right) {
			return;
		}

		if(left < right) {
			/* Split array in two */
			int mid = (left + right)/2;
			/* Sort left side, sort right side then merge sorted array */
			mergeSort(a, left, mid);
			mergeSort(a, mid+1, right);
			merge(a, left, right);
		}
	} /* Close mergeSort */

	public void merge(int [] a, int left, int right) {
		/* Left index of left array and right index of right array */
		int leftInd = 0;
		int rightInd = 0;
		/* Index of target sorted array */
		int target = 0;
		int mid = (left+right)/2;

		int [] leftArr = new int [mid+1-left];	/* Size of mid+1 - start */
		int [] rightArr = new int [right-mid];	/* Size of the end - mid */
		int [] targetArr = new int [leftArr.length+rightArr.length];

		/* Copy values on left side of array into left array */
		for(int i = left; i <= mid; i++) {
			leftArr[i-left] = a[i];
		}

		/* Copy values on right side of array into right array */
		for(int j = mid + 1; j <= right; j++) {
			rightArr[j-mid-1] = a[j];
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
			a[left+i] = targetArr[i];
		}
	} /* Close merge */
}