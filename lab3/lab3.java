// Author : yanhao ding
// Partner : Xin Bian

import java.util.ArrayList;
import java.util.Arrays;

public class lab3 {

	public static void print2Darray(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j =0; j < arr[i].length; j++)
				System.out.printf("%9.0f ", 1.0*arr[i][j]);
			System.out.printf("\n");
		}
	}

	public static void print2DList(ArrayList<ArrayList<Integer>> arrL) {
		for (int i = 0; i < arrL.size(); i++) {
			for (int j = 0; j < arrL.get(i).size(); j++)
				System.out.printf("%9.0f ", 1.0*arrL.get(i).get(j));
			System.out.printf("\n");
		}
	}

	public static void runningSum2DArray(int[][] arr, int n) {

		if (n == 1) {
			for (int i = 0; i < arr.length; i++) {
				for (int j = arr[i].length - 2; j >= 0; j--)
					arr[i][j] = arr[i][j] + arr[i][j + 1];
			}
		}

		if (n == 2) {
			for (int i = 0; i < arr.length; i++) {
				for (int j =0+1; j < arr[i].length; j++)
					arr[i][j] = arr[i][j] + arr[i][j-1];
			}
		}

		if (n == 3) {
			for (int i = arr.length - 2; i >= 0; i--) {
				for (int j =0; j < arr[i].length; j++)
					arr[i][j] = arr[i][j] + arr[i + 1][j];
			}
		}

		if (n == 4) {
			for (int i = 0 + 1; i < arr.length; i++) {
				for (int j =0; j < arr[i].length; j++)
					arr[i][j] = arr[i][j] + arr[i - 1][j];
			}
		}

		print2Darray(arr);
	}


	public static void runningSum2DArrayList(ArrayList<ArrayList<Integer>> arrL, int n) {
		if (n == 1) {
			for (int i = 0; i < arrL.size(); i++) {
				for (int j = arrL.get(i).size() - 2; j >= 0 ; j--) {
					arrL.get(i).set(j, arrL.get(i).get(j) + arrL.get(i).get(j + 1));
				}
			}
		}

		if (n == 2) {
			for (int i = 0; i < arrL.size(); i++) {
				for (int j = 0 + 1; j < arrL.get(i).size(); j++) {
					arrL.get(i).set(j, arrL.get(i).get(j) + arrL.get(i).get(j - 1));
				}
			}
		}

		if (n == 3) {
			for (int i = arrL.size() - 2; i >= 0; i--) {
				for (int j = 0 ; j < arrL.get(i).size(); j++) {
					arrL.get(i).set(j, arrL.get(i).get(j) + arrL.get(i + 1).get(j));
				}
			}
		}

		if (n == 4) {
			for (int i = 0 + 1; i < arrL.size(); i++) {
				for (int j = 0; j < arrL.get(i).size(); j++) {
					arrL.get(i).set(j, arrL.get(i).get(j) + arrL.get(i-1).get(j));
				}
			}
		}
		print2DList(arrL);
	}

	public static void main(String args[]) {
		int [][] arr = {{10, 15, 30, 40},{15, 5, 8, 2}, {20, 2, 4, 2},{1, 4, 5, 0}};
		System.out.println("Printarray");
		print2Darray(arr);

		ArrayList<Integer> arrL1 = new ArrayList<Integer>();
		arrL1.add(10);
		arrL1.add(15);
		arrL1.add(30);
		arrL1.add(40);
		ArrayList<Integer> arrL2 = new ArrayList<Integer>();
		arrL2.add(15);
		arrL2.add(5);
		arrL2.add(8);
		arrL2.add(2);
		ArrayList<Integer> arrL3 = new ArrayList<Integer>();
		arrL3.add(20);
		arrL3.add(2);
		arrL3.add(4);
		arrL3.add(2);
		ArrayList<Integer> arrL4 = new ArrayList<Integer>();
		arrL4.add(1);
		arrL4.add(4);
		arrL4.add(5);
		arrL4.add(0);

		ArrayList<ArrayList<Integer>> arrL = new ArrayList<ArrayList<Integer>>();
		arrL.add(arrL1);
		arrL.add(arrL2);
		arrL.add(arrL3);
		arrL.add(arrL4);

		System.out.println("PrintList");
		print2DList(arrL);

		System.out.println("PrintSum2Darray");
		runningSum2DArray(arr, Integer.parseInt(args[0]));

		System.out.println("PrintSum2DList");
		runningSum2DArrayList(arrL, Integer.parseInt(args[0]));

	}
}
