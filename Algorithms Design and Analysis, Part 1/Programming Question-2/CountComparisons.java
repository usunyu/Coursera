/*
Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. As you know, 
the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore three different 
pivoting rules.
You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray of length m, you 
should simply add m−1 to your running total of comparisons. (This is because the pivot element is compared to each 
of the other m−1 elements in the subarray in this recursive call.)

WARNING: The Partition subroutine can be implemented in several different ways, and different implementations can 
give you differing numbers of comparisons. For this problem, you should implement the Partition subroutine exactly 
as it is described in the video lectures (otherwise you might get the wrong answer).

Question 1
DIRECTIONS FOR THIS PROBLEM:

For the first part of the programming assignment, you should always use the first element of the array as the pivot 
element.

Question 2
DIRECTIONS FOR THIS PROBLEM:

Compute the number of comparisons (as in Problem 1), always using the final element of the given array as the pivot 
element. Again, be sure to implement the Partition subroutine exactly as it is described in the video lectures. 
Recall from the lectures that, just before the main Partition subroutine, you should exchange the pivot element 
(i.e., the last element) with the first element.

Question 3
DIRECTIONS FOR THIS PROBLEM:

Compute the number of comparisons (as in Problem 1), using the "median-of-three" pivot rule. [The primary motivation 
behind this rule is to do a little bit of extra work to get much better performance on input arrays that are nearly 
sorted or reverse sorted.] In more detail, you should choose the pivot as follows. Consider the first, middle, and 
final elements of the given array. (If the array has odd length it should be clear what the "middle" element is; for 
an array with even length 2k, use the kth element as the "middle" element. So for the array 4 5 6 7, the "middle" 
element is the second one ---- 5 and not 6!) Identify which of these three elements is the median (i.e., the one 
whose value is in between the other two), and use this as your pivot. As discussed in the first and second parts of 
this programming assignment, be sure to implement Partition exactly as described in the video lectures (including 
exchanging the pivot element with the first element just before the main Partition subroutine).

EXAMPLE: For the input array 8 2 4 5 7 1 you would consider the first (8), middle (4), and last (1) elements; since 
4 is the median of the set {1,4,8}, you would use 4 as your pivot element.

SUBTLE POINT: A careful analysis would keep track of the comparisons made in identifying the median of the three 
candidate elements. You should NOT do this. That is, as in the previous two problems, you should simply add m−1 to 
your running total of comparisons every time you recurse on a subarray with length m.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

class CountComparisons {
	private static int DefaultSize = 10000;
	
	private static void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	
	private static int partition(int[] array, int start, int end) {
		int pivot = array[start];
		int i = start + 1, j = start + 1;
		while(j <= end) {
			if(array[j] < pivot) {
				swap(array, i++, j);
			}
			j++;
		}
		swap(array, start, i - 1);
		return i - 1;
	}
	
	private static int partition2(int[] array, int start, int end) {
		swap(array, start, end);
		return partition(array, start, end);
	}
	
	private static void medianOfThree(int[] array, int start, int end) {
		int length = end - start + 1;
		if(length < 3) return;
		int mid = start + (end - start) / 2;
		int max = Math.max(array[start], Math.max(array[mid], array[end]));
		int min = Math.min(array[start], Math.min(array[mid], array[end]));
		if(array[mid] > min && array[mid] < max)
			swap(array, mid, start);
		else if(array[end] > min && array[end] < max)
			swap(array, end, start);
	}
	
	private static int partition3(int[] array, int start, int end) {
		medianOfThree(array, start, end);
		return partition(array, start, end);
	}

	private static long quickSort(int[] array, int start, int end) {
		if(end <= start) return 0;
		// Question 1
		//int p = partition(array, start, end);
		// Question 2
		//int p = partition2(array, start, end);
		// Question 3
		int p = partition3(array, start, end);
		long comparisons = end - start;
		comparisons += quickSort(array, start, p - 1);
		comparisons += quickSort(array, p + 1, end);
		return comparisons;
	}
	
	private static long quickSort(int[] array) {
		return quickSort(array, 0, array.length - 1);
	}

	private static int[] input(String path) {
		int[] array = new int[DefaultSize];
		int index = 0;
		try {
			File file = new File(path);
			Scanner sc = new Scanner(file);
			while(sc.hasNextInt())
				array[index++] = sc.nextInt();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Please input file path.");
			System.exit(-1);
		}
		
		int[] array = input(args[0]);
		//int[] array = {21,3,34,5,13,8,2,55,1,19};
		System.out.println(Arrays.toString(array));
		
		long comp = quickSort(array);
		System.out.println(Arrays.toString(array));
		
		System.out.println(comp);
	}
}