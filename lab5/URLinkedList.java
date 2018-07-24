
import java.io.*;
import java.util.*;

public class URLinkedList<E> implements URList<E> {

	private URLink<E> head;
	private URLink<E> tail;
	private int size;

	class URLink<E> { // Doubly linked list node
		private E e; // Value for this node
		private URLink<E> n; // Pointer to next node in list
		private URLink<E> p; // Pointer to previous node
		// Constructors
		URLink(E it, URLink<E> inp, URLink<E> inn) { e = it; p = inp; n = inn; }
		URLink(URLink<E> inp, URLink<E> inn) { p = inp; n = inn; }
		// Get and set methods for the data members
		public E element() { return e; } // Return the value
		public E setElement(E it) { return e = it; } // Set element value
		public URLink<E> next() { return n; } // Return next link
		public URLink<E> setNext(URLink<E> nextval) { return n = nextval; } // Set next link
		public URLink<E> prev() { return p; } // Return prev link
		public URLink<E> setPrev(URLink<E> prevval) { return p = prevval; } // Set prev link
	}

	// the constructor
	public URLinkedList() {
		head = new URLink<E>(null, null);
		tail = new URLink<E>(null, null);
		head.setPrev(null);
		head.setNext(tail);
		tail.setPrev(head);
		tail.setNext(null);
		size = 0;
	}

	public URLinkedList(URLink<E> fromLink, URLink<E> toLink, int size) {
		head = fromLink.prev();
		tail = toLink.next();
		this.size = size;
	}

	// still work when the list is empty
	public boolean add(E e) {
		URLink<E> newLink = new URLink<E>(e, tail.prev(), tail);
		tail.prev().setNext(newLink);
		tail.setPrev(newLink);
		size++;
		return true;
	}

	// Assume index range from 0~size
	public void add(int index, E element) {
		URLink<E> tempLink = indexLink(index);
		URLink<E> newLink = new URLink<E>(element, tempLink.prev(), tempLink);
		tempLink.prev().setNext(newLink);
		tempLink.setPrev(newLink);
		size++;
	}

	private URLink<E> indexLink(int index) {
		if (index <= 0) throw new IllegalArgumentException("position larger than size");
		if (index > size) throw new IllegalArgumentException("position larger than size");
		URLink<E> tempLink = head;
		for (int i = 0; i <= index; i++)
			if (i != 0)
				tempLink = tempLink.next();	
		return 	tempLink;
	} 

	public boolean addAll(Collection<? extends E> c) {
		for (E e: c) {
			add(e);
		}
		return true;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		URLink<E> tempLink = indexLink(index);
		for (E e: c) {
			URLink<E> newLink = new URLink<E>(e, tempLink.prev(), tempLink);
			tempLink.next().setPrev(newLink);
			tempLink.prev().setNext(newLink);
			tempLink.setPrev(newLink);
			size++;
		}
			
		return true;	
	}

	public void clear() {
		head.setNext(tail);
		tail.setPrev(head);
		size = 0;
	}

	public boolean contains(Object o) {
		URLink<E> currLink = head;
		while ( currLink != tail) {
			if (o.equals(currLink.element()))
				return true;
			currLink = currLink.next();
		}
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
		if (index == 0)		throw new IllegalArgumentException("head element is null");
		URLink<E> tempLink = indexLink(index);
		return tempLink.element();
	}

	public int indexOf(Object o) {
		URLink<E> currLink = head.next();
		int count = 0;
		while ( currLink != tail) {
			count++;
			if (o.equals(currLink.element())) 
				return count;
			currLink = currLink.next();
		}
		return -1;		
	}

	public boolean isEmpty() {
		if (size == 0)	return true;
		else 			return false;
	}

	public Iterator<E> iterator() {
		return new URIterator();
	}

	private class URIterator implements Iterator<E> {
		private URLink<E> current = head.next();
		public boolean hasNext() 	{return current != tail;}
		public void remove() 		{throw new UnsupportedOperationException();}
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
            E e = current.element();
            current = current.next(); 
            return e;
		}
	}

	public E remove(int index) {
		if (index == 0)		throw new IllegalArgumentException("cannot remove head");
		URLink<E> tempLink = indexLink(index);
		E e = tempLink.element();
		tempLink.prev().setNext(tempLink.next());
		tempLink.next().setPrev(tempLink.prev());
		size--;
		return e;
	}

	public boolean remove(Object o) {
		URLink<E> currLink = head.next();
		while ( currLink != tail) {
			if (o.equals(currLink.element())) {
				currLink.prev().setNext(currLink.next());
				currLink.next().setPrev(currLink.prev());
				size--;
				return true;
			}
			currLink = currLink.next();
		}
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		for (Object o: c) {
			remove(o);
		}
		return true;
	}

	public E set(int index, E element) {
		URLink<E> tempLink = indexLink(index);
		tempLink.setElement(element);
		return element;
	}

	public int size() {
		return size;
	}

	public URLinkedList<E> subList(int fromIndex, int toIndex) {
		if (fromIndex <= 0) throw new IllegalArgumentException("index must be larger than zero");
		if (toIndex > size)	 throw new IllegalArgumentException("index must be smaller than size");
		int n = toIndex - fromIndex + 1;
		URLink<E> fromLink = indexLink(fromIndex);
		URLink<E> toLink = indexLink(toIndex);
		URLinkedList<E> sublist = new URLinkedList<E>(fromLink, toLink, n);
		return sublist;
	}

	public Object[] toArray() {
		URLink<E> currLink = head.next();
		Object[] arr = new Object[size]; 
		for (int i = 0; i < size; i++) {
			arr[i] = currLink.element();
			currLink = currLink.next();
		}
		return arr;
	}

	public void addFirst(E e) {
		URLink<E> newLink = new URLink<E>(e, head, head.next());
		head.next().setPrev(newLink);
		head.setNext(newLink);
		size++;
	}

	public void addLast(E e) {
		URLink<E> newLink = new URLink<E>(e, tail.prev(), tail);
		tail.prev().setNext(newLink);
		tail.setPrev(newLink);
		size++;
	}

	public E peekFirst() {
		if (head.next() == tail)	return null;
		return head.next().element();
	}

	public E peekLast() {
		if (tail.prev() == tail)	return null;
		return tail.prev().element();
	}

	public E pollFirst() {
		if (head.next() == tail)	return null;
		E e = head.next().element();
		head.next().next().setPrev(head);
		head.setNext(head.next().next());
		size--;
		return e;
	}

	public E pollLast() {
		if (tail.prev() == tail)	return null;
		E e = tail.prev().element();
		tail.prev().prev().setNext(tail);
		tail.setPrev(tail.prev().prev());
		size--;
		return e;
	}
	


	public static void main(String args[]) {
		URLinkedList<String> testLink = new URLinkedList<String>();
		List<String> list = Arrays.asList("d", "i", "n", "g");
		testLink.addAll(list);
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		testLink.add("Y");
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		testLink.add("H");
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		List<String> list2 = Arrays.asList("*", "*");
		testLink.addAll(5 ,list2);
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		System.out.print(testLink.get(8));
		System.out.print("\n");

		System.out.print(testLink.indexOf("H"));
		System.out.print("\n");
//
//		System.out.print(testLink.contains("*"));
//		System.out.print("\n");
//
//		List<String> list3 = Arrays.asList("*", "*", "H", "d");
//		System.out.print(testLink.containsAll(list3));
//		System.out.print("\n");
//
		System.out.print(testLink.remove(5));
		System.out.print("\n");
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		System.out.print("size is:  " + testLink.size());
		System.out.print("\n");

		for (String s : testLink.subList(2, 6))
			System.out.print(s);
		System.out.print("\n");

		for (int i = 0; i < testLink.toArray().length; i++)
			System.out.print(testLink.toArray()[i]);
		System.out.print("\n");

		testLink.addFirst("@");
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		testLink.addLast("@");
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		System.out.print(testLink.peekFirst());
		System.out.print("\n");

		System.out.print(testLink.peekLast());
		System.out.print("\n");

		testLink.pollFirst();
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		testLink.pollLast();
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		List<String> list4 = Arrays.asList("Y", "H");
		testLink.removeAll(list4);
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");

		testLink.set(5, "$");
		for (String s : testLink)
			System.out.print(s);
		System.out.print("\n");
	}

}

