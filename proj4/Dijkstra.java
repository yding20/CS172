import java.util.PriorityQueue;
import edu.princeton.cs.algs4.IndexMinPQ;
import java.util.Stack;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class Dijkstra extends javax.swing.JFrame {
	private double[] distTo;
	private Edge[] edgeTo;
	private IndexMinPQ<Double> pq;
	private Graph G;

	public Dijkstra(Graph G, int s) {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 1000);
		this.G = G;

		distTo = new double[G.V()];
		edgeTo = new Edge[G.V()];

		for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v)) {
                relax(e);
            }
        }
	}

	private void relax(Edge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public double distTo(int v) {
        return distTo[v];
    }


    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stack<Edge>[] adj = G.getAdj();
        double vx = 0.0;
        double vy= 0.0;
        double wx = 0.0;
        double wy = 0.0;
        double Xmax = G.getXmax();
        double Xmin = G.getXmin();
        double Ymax = G.getYmax();
        double Ymin = G.getYmin();
        double xrange = Xmax-Xmin;
        double yrange = Ymax-Ymin;
        double range = xrange;
        if (xrange < yrange)
            range = yrange;
        double ratio = (1000/range)/1.4;

        for (int m = 0; m < adj.length; m++) {
            for (Edge e: adj[m]) {
                vx = (e.getVX() - Xmin)*ratio + 100;
                vy = (e.getVY() - Ymin)*ratio + 100;
                wx = (e.getWX() - Xmin)*ratio + 100;
                wy = (e.getWY() - Ymin)*ratio + 100;

                Line2D lin = new Line2D.Double(vy, vx, wy, wx);
                g2.draw(lin);
            }
        }

        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(5));
        int t = 194129;
        int s = 22659;
        if (hasPathTo(t)) {
            for (Edge e : pathTo(t)) {
            	vx = (e.getVX() - Xmin)*ratio + 100;
                vy = (e.getVY() - Ymin)*ratio + 100;
                wx = (e.getWX() - Xmin)*ratio + 100;
                wy = (e.getWY() - Ymin)*ratio + 100;
//                int i = e.from();
//                int j = e.to();
//                System.out.print(i + ">>>" + j + "    ");
            	Line2D lin = new Line2D.Double(vy, vx, wy, wx);
        		g2.draw(lin);
            }
//            System.out.println();
        }
        else {
            System.out.printf("%d to %d         no path\n", s, t);
        }
    }


   	public static void main(String args[]) {
        Graph G = new Graph();
//        G.getS();
//        G.setVisible(true);
        int s = 22659;
        Dijkstra sp = new Dijkstra(G, s);
        sp.setVisible(true);

    }

}