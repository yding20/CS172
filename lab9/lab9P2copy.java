import java.util.*;
import java.io.*;

public class lab9P2 {

    private Node root;

	private class Node {
	    Node left;
	    Node right;
	    int data;
	    
	    Node(int data) {
	        this.data = data;
	        left = null;
	        right = null;
	    }
	}

	public lab9P2() {
	}

    public void Cnode() {
        root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.right = new Node(6);
    }

    public void insert(int d) {
        root = insert(root, d);
    }

    public void level_order_print() {
        level_order_print(root);
    }

    private void level_order_print(Node root) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(true) {
            int nodeCount = q.size();
            if(nodeCount == 0)
                break;
             
            // Dequeue all nodes of current level and Enqueue all
            // nodes of next level
            while(nodeCount > 0)
            {
                Node node = q.poll();
                System.out.print(node.data + " ");
                if(node.left != null)
                    q.add(node.left);
                if(node.right != null)
                    q.add(node.right);
                nodeCount--;
            }
            System.out.println();
        }

    }

    public Node reconstruct_tree(int[] inOrder, int[] preOrder) {
        int n = inOrder.length - 1;
        root = reconstruct_tree(inOrder, preOrder, 0, n);
        return root;
    }

    private Node reconstruct_tree(int[] inOrder, int[] preOrder, int lo, int hi) {
        
        Node tnode = new Node(preOrder[lo]);

        int i = findIndex(inOrder, preOrder[lo++]);

        System.out.println("left : "+i);
        if (i > 0) {
            int[] leftinOrderarray = new int[i];
            for (int m = 0; m < i; m++)
                leftinOrderarray[m] = inOrder[m];
    
            int[] leftpreOrderarray = new int[i];
            for (int m = 0; m < i; m++)
                leftpreOrderarray[m] = preOrder[m+1];

            tnode.left = reconstruct_tree(leftinOrderarray, leftpreOrderarray, 0, i-1);
        }

        int rightlength = hi - i;
        System.out.println("right : "+ rightlength);
        //System.out.println("hi : " + hi + " i : " + i);
        if (rightlength > 0) {
            int[] rightinOrderarray = new int[rightlength];
            for (int m = 0; m < rightlength; m++)
                rightinOrderarray[m] = inOrder[i+m+1];

             System.out.println(inOrder.length);
    
            int[] rightpreOrderarray = new int[rightlength];
            for (int m = 0; m < rightlength; m++)
                rightpreOrderarray[m] = preOrder[i+m+1];

            tnode.right = reconstruct_tree(rightinOrderarray, rightpreOrderarray, 0, rightlength-1);
        }
        return tnode;
    }

    private int findIndex(int[] inOrder, int n) {
        for (int i = 0; i <inOrder.length; i++)
            if (inOrder[i] == n)    return i;
        return -1;
    }

    private Node insert(Node x, int d) {
        if (x == null) return new Node(d);
        if (d < x.data)     x.left  = insert(x.left,  d);
        else                x.right = insert(x.right, d);
        return x;
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node root) {
        if (root == null)   return;
        inOrder(root.left);
        System.out.print(root.data+" ");
        inOrder(root.right);
    }


    public static void main(String[] args) {
        lab9P2 test = new lab9P2();

        int[] preArray = new int[]{1,2,4,5,3,6};
        int[] inArray = new int[]{4,2,5,1,3,6};

        test.reconstruct_tree(inArray, preArray);

//        Scanner scan = new Scanner(System.in);
//        int t = scan.nextInt();
//
//        Node root = null;
//        while(t-- > 0) {
//            int data = scan.nextInt();
//            test.insert(data);
//        }
//        scan.close();
        test.level_order_print();
    }
    

}