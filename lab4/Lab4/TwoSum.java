/******************************************************************************
 *  Compilation:  javac TwoSum.java
 *  Execution:    java TwoSum input.txt
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program with n^2 running time. Reads n integers
 *  and counts the number of pairs that sum to exactly 0.
 *
 *
 *  Limitations
 *  -----------
 *     - we ignore integer overflow
 *
 *
 *  % java TwoSum 2Kints.txt
 *  2
 *
 *  % java TwoSum 1Kints.txt
 *  1
 *
 *  % java TwoSum 2Kints.txt
 *  2
 *
 *  % java TwoSum 4Kints.txt
 *  3
 *
 *  % java TwoSum 8Kints.txt
 *  19
 *
 *  % java TwoSum 16Kints.txt
 *  66
 *
 *  % java TwoSum 32Kints.txt
 *  273
 *
 ******************************************************************************/
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TwoSum {

    // print distinct pairs (i, j) such that a[i] + a[j]  = 0
    public static void printAll(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (a[i] + a[j] == 0) {
                    StdOut.println(a[i] + " " + a[j]);
                }
            }
        }
    } 

    // return number of distinct pairs (i, j) such that a[i] + a[j] = 0
    public static int count(int[] a) {          // The count in total take O(n^2) time
        int n = a.length;                       // O(1)
        int count = 0;                          // O(1)
        for (int i = 0; i < n; i++) {           // n times O(n^2)
            for (int j = i+1; j < n; j++) {     // Average n/2 times, O(n)
                if (a[i] + a[j] == 0) {         // O(1)
                    count++;                    // O(1)
                }
            }
        }
        return count;
    } 

 /**
     * 
     * counts the number of pairs sum to exactly zero; prints out the time to perform
     * the computation along with other information.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  { 
        //for (int i = 0; i < 1; i++) {
            In in = new In(args[0]);
            int[] a = in.readAllInts();
            Stopwatch timer = new Stopwatch();
            int count = count(a);
            double time = timer.elapsedTime();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
              //TODO: Replace with your own netid
            String netID = "yding20";
              StdOut.printf("%7d %7.4f   %s  %s  %s\n", count, time, timeStamp, netID, args[0]);
        //}
    } 
} 


