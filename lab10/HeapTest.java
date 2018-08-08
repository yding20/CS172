import java.util.Stack;

class Heap<E extends Comparable<E>> {
	private E[] Heap;
	private int n;

	public boolean isLeaf(int pos) {
		return (pos >= n/2) && (pos < n);
	}

	public int leftchild(int pos) {
		return 2*pos + 1;
	}

	public E[] heapify(E[] arr) {
		Heap = arr; 
		n = arr.length;
		for (int i = n/2-1; i>=0; i--)
			siftdown(i);
		return Heap;
	}

	private void siftdown(int pos) {
		while (!isLeaf(pos)) {
			int j = leftchild(pos);
			if (j < (n-1) && Heap[j].compareTo(Heap[j+1]) < 0)
				j++; // j is now index of child with greater value
			if (Heap[pos].compareTo(Heap[j]) > 0)	return;
			swap(Heap, pos, j);
			pos = j; // Move down
		}
	}

	private void swap(E[] Heap, int pos, int j) {
		E temp = Heap[pos];
		Heap[pos] = Heap[j];
		Heap[j] = temp;
	}

	public Iterable<E> heapsort(E[] arr2) {
		heapify(arr2);
		Stack<E> stack = new Stack<>();
		int m = arr2.length;
		for (int i = 0; i < m; i++)
			stack.push(removemax());
		return stack;
	}

	private E removemax() {
		swap(Heap, 0, --n); // swap with last element
		// n is decrement by 1, shiftdown will not include the last one we
		// just swap to last
		if (n != 0)	
			siftdown(0);  
		// it seems the last element is swap to last one but not delete, but 
		// the pointer n is point to it, next time insert will overwrite it
		return Heap[n]; 
	}


}

public class HeapTest {
	public static void main(String args[]) {
		Integer[] arr = {5, 18, 3, 25, 27, 45, 97, 88, 26, 16, 49, 67};
		Heap<Integer> test = new Heap<>();
		arr = test.heapify(arr);
		for (Integer i: arr)
			System.out.print(i + "   ");
		System.out.print("\n");

		Integer[] arr2 = {15, 99, 3, 77, 27, 45, 7, 88, 26, 5};
		for (Integer i: test.heapsort(arr2))
			System.out.print(i + "   ");
		System.out.print("\n");
	}
}
















