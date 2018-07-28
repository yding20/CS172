import java.util.*;
import java.io.*;

public class lab9P1 {

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

	public lab9P1() {
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

    private Node insert(Node x, int d) {
        if (x == null) return new Node(d);
        if (d < x.data)     x.left  = insert(x.left,  d);
        else                x.right = insert(x.right, d);
        return x;
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node root) {
        if (root == null)   return;
        System.out.print(root.data+" ");
        preOrder(root.left);
        preOrder(root.right);
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

    public static void main(String[] args) {
        lab9P1 test = new lab9P1();
        test.Cnode();

//        Scanner scan = new Scanner(System.in);
//        int t = scan.nextInt();
//
//        Node root = null;
//        while(t-- > 0) {
//            int data = scan.nextInt();
//            test.insert(data);
//        }
//        scan.close();

        test.inOrder();
        System.out.print("\n");
        test.preOrder();
        System.out.print("\n");
        test.level_order_print();
    }
    

}