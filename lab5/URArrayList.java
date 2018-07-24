import java.io.*;
import java.util.*;

public class URArrayList<E> implements URList<E>{
	private static final int defaultSize = 20;
	private int maxSize;
	private int currP;
	private E[] listArray;

	URArrayList() {this(defaultSize);}
	URArrayList(int size) {
		maxSize = size;
		currP = 0;
		listArray = (E[]) new Object[maxSize];
	}

	URArrayList(E[] subarray, int defaultSize) {
		maxSize = defaultSize;
		currP = subarray.length;
		listArray = (E[]) new Object[maxSize];
		for (int i = 0; i <subarray.length; i++)
			listArray[i] = subarray[i];
	}

	public boolean add(E e) {
		if (currP >= maxSize) throw new IllegalArgumentException("position larger than size");
		listArray[currP++] = e;
		return true;
	}
	public void add(int index, E element) {
		int arrayindex = index - 1;

		if (currP >= maxSize) throw new IllegalArgumentException("position larger than size");
		if (arrayindex > currP || arrayindex < 0) 
			throw new IllegalArgumentException("index is invalid");

		E[] arrayTemp = (E[]) new Object[maxSize];
		for (int i = 0; i < maxSize; i++) {
			if (i < arrayindex)			arrayTemp[i] = listArray[i];
			else if (i == arrayindex)	arrayTemp[i] = element;
			else 						arrayTemp[i] = listArray[i-1];
		}
		listArray = arrayTemp;
		currP++;
	}

	public boolean addAll(Collection<? extends E> c) {
		if (currP + c.size() > maxSize) 
			throw new IllegalArgumentException("position larger than size");
		for (E e : c) 
			listArray[currP++] = e;
		return true;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		int arrayindex = index - 1;

		if (currP + c.size() > maxSize) 
			throw new IllegalArgumentException("position larger than size");
		if (arrayindex > currP || arrayindex < 0) 
			throw new IllegalArgumentException("index is invalid");

		E[] arrayTemp = (E[]) new Object[maxSize];
		for (int i = 0; i < arrayindex; i++) 
			arrayTemp[i] = listArray[i];

		int j = arrayindex;
		for (E e: c) {
			arrayTemp[j++] = e;
		}

		for (int i = j; i < maxSize; i++)
			arrayTemp[i] = listArray[arrayindex++];

		currP = currP + c.size();
		listArray = arrayTemp;

		return true;
	}

	public void clear() {
		currP = 0;
	}

	public boolean contains(Object o) {
		for (int i = 0; i < currP; i++)
			if (o.equals(listArray[i]))
				return true;
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o: c) {
			if (!contains(o))
				return false;
		}
		return true;
	}

		// ??????
	public boolean equals(Object o) {
		return true;
	}

	public E get(int index) {
		if (index == 0)		throw new IllegalArgumentException("index start from 1");
		if (index > currP)	throw new IllegalArgumentException("index larger than max");
		return listArray[index-1];
	}

	public int indexOf(Object o) {
		for (int i = 0; i < currP; i++)
			if (o.equals(listArray[i]))
				return i+1;
		return -1;		
	}


	public boolean isEmpty() {
		if (currP == 0)	return true;
		else 			return false;
	}


	public E remove(int index) {
		int arrayindex = index - 1;
		E[] arrayTemp = (E[]) new Object[maxSize];
		for (int i = 0; i < maxSize; i++) {
			if (i < arrayindex)		arrayTemp[i] = listArray[i];
			if (i > arrayindex)		arrayTemp[i-1] = listArray[i];
		}
		listArray = arrayTemp;
		currP--;
		return arrayTemp[arrayindex];
	}

	public boolean remove(Object o) {
		int i = indexOf(o);
		if (i == -1)		throw new IllegalArgumentException("cannot find Object");
		remove(i);
		return true;
	}

	public boolean removeAll(Collection<?> c) {
		for (Object o : c)
			remove(o);
		return true;
	}

	public E set(int index, E element) {
		listArray[index-1] = element;
		return element;
	}

	public int size() {
		return currP;
	}

	public URArrayList<E> subList(int fromIndex, int toIndex) {
		int n = toIndex - fromIndex + 1;

		E[] subarray = (E[]) new Object[n];
		for (int i = 0; i < n; i++) {
			subarray[i] = listArray[fromIndex-1];
			fromIndex++;
		}

		URArrayList<E> sublist = new URArrayList<>(subarray, defaultSize);

		return sublist;
	}

	public Object[] toArray() {
		E[] arr = (E[]) new Object[currP];
		for (int i = 0; i < currP; i++)
			arr[i] = listArray[i];
		return arr;
	}






	public Iterator<E> iterator() {
		return new URIterator();
	}

	private class URIterator implements Iterator<E> {
		int i = 0;
		public boolean hasNext() 	{return i != currP;}
		public void remove() 		{throw new UnsupportedOperationException();}
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
            return listArray[i++];
		}
	}






	public static void main(String argsp[]) {
		URArrayList<String> testArray = new URArrayList<>();
		List<String> list = Arrays.asList("d", "i", "n", "g");
		testArray.addAll(list);
		for (String s : testArray)
			System.out.print(s);
		System.out.print("\n");

		testArray.add("Y");
		for (String s : testArray)
			System.out.print(s);
		System.out.print("\n");

		testArray.add("H");
		for (String s : testArray)
			System.out.print(s);
		System.out.print("\n");

		List<String> list2 = Arrays.asList("*", "*");
		testArray.addAll(5 ,list2);
		for (String s : testArray)
			System.out.print(s);
		System.out.print("\n");

//		System.out.print(testArray.contains("*"));
//		System.out.print("\n");
//
//		List<String> list3 = Arrays.asList("*", "*", "H", "e");
//		System.out.print(testArray.containsAll(list3));
//		System.out.print("\n");

		System.out.print(testArray.get(8));
		System.out.print("\n");

		System.out.print(testArray.indexOf("d"));
		System.out.print("\n");

//		List<String> list4 = Arrays.asList("d", "H");
//		testArray.removeAll(list4);
//		for (String s : testArray)
//			System.out.print(s);
//		System.out.print("\n");

		System.out.print(testArray.remove(5));
		System.out.print("\n");
		for (String s : testArray)
			System.out.print(s);
		System.out.print("\n");

		System.out.print("size is:  " + testArray.size());
		System.out.print("\n");

		for (String s : testArray.subList(2, 6))
			System.out.print(s);
		System.out.print("\n");

		for (int i = 0; i < testArray.toArray().length; i++)
			System.out.print(testArray.toArray()[i]);
		System.out.print("\n");

//		testArray.set(1, "$");
//		for (String s : testArray)
//			System.out.print(s);
//		System.out.print("\n");









	}
}