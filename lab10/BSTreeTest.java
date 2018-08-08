
class BSTree {
	private BSTNode root;
	public class BSTNode {
		int key;
		BSTNode parent;
		BSTNode left;
		BSTNode right;

		public BSTNode(int key) {
			this.key = key;
		}

	}

	public boolean insert(int key) {
		if (search(key) == true)	return	false;
		root = insert(root, key);
		return true;
	}

	private BSTNode insert (BSTNode rt, int key) {
		if (rt == null)	return new BSTNode(key);
		int cmp = key - rt.key;
		if 		(cmp < 0)	rt.left = insert(rt.left, key);
		else if (cmp > 0)	rt.right = insert(rt.right, key);
		else 				rt.key = key;
		return rt;
	}

	public boolean delete(int key) {
		if (!search(key) == true)	return	false;
		root = delete(root, key);
		return true;
	}

	public BSTNode delete(BSTNode rt, int key) {
		if (rt == null)	return null;
		int cmp = key - rt.key;
		if 		(cmp < 0)	rt.left = delete(rt.left, key);
		else if (cmp > 0)	rt.right = delete(rt.right, key);
		else {
			if (rt.right == null)	return rt.left;
			if (rt.left == null)		return rt.right;
			BSTNode temp = getMin(rt.right);
			rt.key = temp.key;
			rt.right = deleteMin(rt.right);
		}
		return rt;
	}
	// use this way we can print key in the main method
	public int getMin() {
		return getMin(root).key;
	}

	private BSTNode getMin(BSTNode rt) {
		if (rt.left == null)	return rt;
		else 					return getMin(rt.left);
	}

	public void deleteMin() {
		root = deleteMin(root);
	}

	private BSTNode deleteMin(BSTNode rt) {
		if (rt.left == null)	return rt.right;
		rt.left = deleteMin(rt.left);
		return rt;
	}

	public boolean search(int key) {
		return search(root, key) != null;
	}

	public Integer search(BSTNode rt,int key) {
		if (rt == null)	return null;
		int cmp = key - rt.key;
		if (cmp < 0)	return search(rt.left, key);
		if (cmp > 0)	return search(rt.right, key);
		else 			return rt.key;
	}

	
	public void inorder() {
		inorder(root);
	}

	private void inorder(BSTNode rt) {
		if (rt == null)	return;
		inorder(rt.left);
		System.out.print(rt.key + "   ");
		inorder(rt.right);
	}

}


public class BSTreeTest {

	public static void main(String args[]) {
		BSTree test = new BSTree();		test.insert(5);
		test.insert(18);
		test.insert(3);
		test.insert(25);
		test.insert(27);
		test.insert(45);
		test.insert(97);
		test.insert(88);
		test.insert(26);
		test.insert(15);
		test.insert(17);
		test.insert(16);
		System.out.println("Inorder Print : ");
		test.inorder();
		System.out.print("\n");
		int[] arr = {3, 88, 27, 28};
		for (int i = 0; i < arr.length; i++) {
			if (test.search(arr[i]))
				System.out.println("search"+ arr[i] + "  :  success");
			else 
				System.out.println("search"+ arr[i] + "  :  unsuccess");
		}
		int[] arr2 = {88, 18, 5, 30};
		for (int i = 0; i < arr.length; i++) {
			if (!test.delete(arr2[i]))
				System.out.println("item  "+ arr2[i] + " not found");
		}
		System.out.println("Inorder Print : ");
		test.inorder();
		System.out.print("\n");
	}

}