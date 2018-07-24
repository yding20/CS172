// references: https://docs.oracle.com/javase/tutorial/extra/generics/methods.html
// Author : yanhao ding
// Partner : Xin Bian


import java.util.*;
import java.util.function.Function;

public class lab2{

//  1. Array of Objects method
//	public static void printArray(Object[] input) {
//		for(Object element: input)
//			System.out.print(element);
//		System.out.print("\n");
//	}

// 2. Method OverLoading 
//	public static void printArray(Integer[] input) {
//		for(Integer element: input)
//			System.out.print(element);
//		System.out.print("\n");
//	}
//
//	public static void printArray(Double[] input) {
//		for(Double element: input)
//			System.out.print(element);
//		System.out.print("\n");
//	}
//
//	public static void printArray(Character[] input) {
//		for(Character element: input)
//			System.out.print(element);
//		System.out.print("\n");
//	}
//
//	public static void printArray(String[] input) {
//		for(String element: input)
//			System.out.print(element);
//		System.out.print("\n");
//	}

// 3. Generic Methods
// Type inference : Compiler will infer the type that is needed	

	public static<T> void printArray(T[] input) {
		for(T element: input)
			System.out.print(element);
		System.out.print("\n");
	}

// 4.  non-generic techniques, write a method getMax()
//	Note: lab2.java uses unchecked or unsafe operations.
//	Note: Recompile with -Xlint:unchecked for details.

//	public static Comparable getMax(Comparable[] anArray) {
//		Object max = new Object();
//		max = anArray[0];
//		for (int i = 0; i < anArray.length; i++) {
//			if (anArray[i].compareTo(max) > 0)	max = anArray[i];
//		}
//		return (Comparable) max;
//	}

// 5. generic getMax()
	public static<T extends Comparable<T>> Comparable<T> getMax(T[] anArray) {
		T max = anArray[0];
		for (int i = 0; i < anArray.length; i++) {
			if (anArray[i].compareTo(max) > 0)	max = anArray[i];
		}
		return max;
	}

	public static void main(String[] args) {
		Integer [] intArry = {1, 2, 3, 4, 5 };
		Double [] doubArry = {1.1, 2.2, 3.3, 4.4};
		Character [] charArray = {'H','E','L', 'L', 'O' };
		String [] strArray = {"once", "upon", "a", "time" };
		printArray(intArry);
		printArray(doubArry);
		printArray(charArray);
		printArray(strArray);


		System.out.println("max Integer is: " + getMax(intArry));
		System.out.println("max Double is: " + getMax(doubArry));
		System.out.println("max Character is: " + getMax(charArray));
		System.out.println("max String is: " + getMax(strArray));


		Function<Character[], Character> findMax = (Character[] anArray) -> {
			Character max = anArray[0];
			for (int i = 0; i < anArray.length; i++) {
				if (anArray[i].compareTo(max) > 0)	max = anArray[i];
			}
			return max;			
		};

		System.out.println("Functional  Interface findMax : ");
		System.out.println(findMax.apply(charArray));
	}
} 















