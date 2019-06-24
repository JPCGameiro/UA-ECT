package p2utils;

import static java.lang.System.*;
import java.util.Scanner;
import java.util.Arrays;

/**
 * This class contains several sorting algorithms and
 * auxiliary methods and fields for evaluating their complexity.
 */
public class Sorting 
{

  // Fields to measure times:
  public static long startTime;  // start time of latest measurement (in ns)
  public static double elapsedTime;  // seconds elapsed in latest measurement

  // Fields to count operations: 
  public static long assignmentCount = 0L;
  public static long comparisonCount = 0L;

  // Start a new measurement
  public static void startMeasuring() 
  {
    assignmentCount = 0L;
    comparisonCount = 0L;
    startTime = nanoTime();
  }

  // Stop a measurement (determine elapsedTime)
  public static void stopMeasuring() {
    elapsedTime = (double)(nanoTime() - startTime) * 1e-9;
  }


  /**
   * Sequential sort ("greedy" variation of selection sort).
   * Increasing sorting of integer subarray a[start..end[
   * @param a      the array to be (partially) sorted.
   * @param start  index of the first element to sort.
   * @param end    index of the first element not to be sorted (the last element to sort is {@code end-1}).
   * Requires:   a!=null and 0 <= start <= end <= a.length
   * Ensures:  The elements a[k] with start <= k < end are sorted.  the remaining elements are not changed.
   */
  public static void sequentialSort(int[] a, int start, int end) 
  {
    assert a!=null;
    assert 0<=start && start<=end && end<=a.length;

    for (int i=start; i<end-1; i++) { // For each element (except the last):
      for (int j=i+1; j<end; j++) {   // Scan every following element
        if (a[j] < a[i]) {            // compare them
          swap(a, i, j);              // if necessary, swap them
        }
      }
    }
  }

  /**
   * Bubble sort.
   * Optimized version.
   */
  public static void bubbleSort(int[] a, int start, int end) {
    assert a!=null;
    assert 0<=start && start<=end && end<=a.length;

    while (start < end-1) {
      int last = start;
      for (int i=start; i<end-1; i++) {
        if (a[i] > a[i+1]) {
          swap(a, i, i+1);
          last = i;   // store index of the last swap
        }
      }
      // Elements a[last+1, end[ must be sorted now
      // So, next pass may stop there
      end = last+1;
    }
  }

  /**
   * Swaps two elements of an integer array.
   * @param a the array
   * @param i index of an element to swap
   * @param j index of the other element to swap
   * {@code i},{@code j} must be valid indexes within array {@code a}
   */
  public static void swap(int[] a, int i, int j) 
  {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  
  //ordenação por inserção
  public static void insertionSort(int[] a, int start, int end) 
  {
    assert validSubarray(a, start, end);
    
    for(int i=start+1;i<=end;i++)
    {
		int j;
		int v = a[i];
		for(j=i-1;j >= start && a[j] >	v; j--)
		{
			if(v >= a[j]) break;
			a[j+1] = a[j];
		}
		a[j+1] = v;
	}
	
	assert isSorted(a, start, end); 

  }

  public static void mergeSort(int[] a, int start, int end) 
  {
	  assert start<=end && 0<=start && end<=a.length;
	  assert validSubarray(a, start, end);
	  
	  if(end-start>1)
	  {
		  int middle = (start + end)/2;
		  mergeSort(a, start, middle);
		  mergeSort(a, middle, end);
		  mergeSubarrays(a, start, middle, end);
	  }
	  
	  assert isSorted(a, start, end);	  
  }
  
  public static void mergeSubarrays(int[] a, int start, int middle, int end)
  {
	  int b[] = new int[end-start];
	  int i1 = start;
	  int i2 = middle;
	  int j=0;
	  while(i1<middle && i2 < end)
	  {
		  if(a[i1] < a[i2])
			  b[j++] = a[i1++];
		  else
			  b[j++] = a[i2++];
	  }
	  while(i1 < middle)
		  b[j++] = a[i1++];
	  while(i2 < end)
		  b[j++] = a[i2++];
	  arraycopy(b, 0 , a, start, end-start);
  }

  // Test if [start, end[ defines a valid interval of indices in array a.
  public static boolean validSubarray(int[] a, int start, int end) 
  {
    return a != null && 0 <= start && start <= end && end <= a.length;
  }

  public static boolean isSorted(int[] a, int start, int end) {
    assert validSubarray(a, start, end);
    boolean result = true;
    for(int i = start; result && i < end-1; i++)
      result = a[i] <= a[i+1];
    return result;
  }



  // Generic metque hod for sorting arrays of any reference type:
  public static <E extends Comparable<E>> void mergeSort(E[] a, int start, int end) 
  {
	  if(end-start > 1)
	  {
		  int middle = (end + start)/2;
		  mergeSort(a, start, middle);
		  mergeSort(a, middle, end);
		  mergeSubarrays(a, start, middle, end);
	  }
  }
  
  static <E extends Comparable<E>> void mergeSubarrays(E[] a, int start, int middle, int end)
  {
	  E[] b = (E[]) new Comparable[end-start];
	  int i1 = start;
	  int i2 = middle;
	  int j = 0;
	  while(i1 < middle && i2 < end)
	  {
		  if(a[i1].compareTo(a[i2]) < 0)
			  b[j++] = a[i1++];
		  else
			  b[j++] = a[i2++];			  
	  }
	  while(i1 < middle)
		b[j++] = a[i1++];
	  while(i2 < end)
		b[j++] = a[i2++];
	arraycopy(b, 0, a, start, end-start);
  }
}

