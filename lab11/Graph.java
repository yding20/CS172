import java.util.Stack;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	private final int V;
	private int E;
	private Stack<Integer>[] adj;
	private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

	public Graph(int V) {
		this.V = V;
		this.E = 0;
		adj = (Stack<Integer>[]) new Stack[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Stack<Integer>();
		}
	}

	public void addEdge(int v, int w) {
		E++;
        adj[v].add(w);
        adj[w].add(v);
	}

	public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public List<Integer> getShortestPath(int i, int j) {
    	marked = new boolean[V];
        distTo = new int[V];
        edgeTo = new int[V];
        for (int v = 0; v < V; v++)
            distTo[v] = INFINITY;
        bfs(i);

        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = j; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    private void bfs(int s) {
        Queue<Integer> q = new LinkedList<Integer>();
        for (int v = 0; v < V; v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;
        marked[s] = true;
        q.add(s);

        while (!q.isEmpty()) {
            int v = q.poll();
            for (int w : adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }



	public static void main(String args[]) {
		try {
            File file = new File("test.txt");
            Scanner sc = new Scanner(file);
            String line0 = sc.nextLine();
            int V = Integer.parseInt(line0);
            Graph G = new Graph(V);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] stringarr = line.split(" ");
                int i = Integer.parseInt(stringarr[0]);
                int j = Integer.parseInt(stringarr[1]);
                G.addEdge(i, j);
            }

            for (int i = 0; i < V; i++) {
            	System.out.print(i + " : ");
            	for (Integer j: G.adj(i))
        			System.out.print(i + " >>> " + j + "       ");
        		System.out.print("\n");
            }

            for (Integer m : G.getShortestPath(4, 5))
            	System.out.print(m + "    ");
           	System.out.print("\n");



        } catch (FileNotFoundException e) { } 

	}
}