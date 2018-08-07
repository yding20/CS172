import java.util.PriorityQueue;
import edu.princeton.cs.algs4.IndexMinPQ;
import java.util.Stack;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dijkstra extends JPanel {
	private double[] distTo;
	private Edge[] edgeTo;
	private IndexMinPQ<Double> pq;
	private Graph G;
	private static final int DELAY = 100;
	private boolean control;
	private Stack<Edge> path;

	public Dijkstra(Graph G, int s) {
        //setPreferredSize(new Dimension(900, 700));
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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                control = true;
                repaint();
            }
        });

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


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int width =  getWidth();   // Find out the width of this component.
        int height = getHeight(); 
        System.out.println(width + "**" + height);
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
        double ratio = (height/range)/1.2;

        for (int m = 0; m < adj.length; m++) {
            for (Edge e: adj[m]) {
                vx = (e.getVX() - Xmin)*ratio + width*0.1;
                vy = (e.getVY() - Ymin)*ratio + height*0.1;
                wx = (e.getWX() - Xmin)*ratio + width*0.1;
                wy = (e.getWY() - Ymin)*ratio + height*0.1;

                Line2D lin = new Line2D.Double(vy, vx, wy, wx);
                g2.draw(lin);
            }
        }
//        int t = 194129;
//        int s = 22659;

        int t = 20;
        int s = 0;
//        String s1 = "GILBERT-LONG";
//        double xcor = (G.gethmnode(s1)[0]-Xmin)*ratio + 75;
//        double ycor = (G.gethmnode(s1)[1]-Ymin)*ratio + 75;
//        g2.setPaint(Color.RED);
//        g2.fill (new Ellipse2D.Double(ycor-5, xcor-5, 10, 10));
//
//        String s2 = "MELIORA-NORTH";
//        double xcor2 = (G.gethmnode(s2)[0]-Xmin)*ratio + 75;
//        double ycor2 = (G.gethmnode(s2)[1]-Ymin)*ratio + 75;
//        g2.setPaint(Color.RED);
//        g2.fill (new Ellipse2D.Double(ycor2-5, xcor2-5, 10, 10));
//

      	if (control)  {
      		g2.setColor(Color.GREEN);
        	g2.setStroke(new BasicStroke(5));
        	double distace = 0.0;

        	if (hasPathTo(t)) {
        	    for (Edge e : pathTo(t)) {
                	vx = (e.getVX() - Xmin)*ratio + width*0.1;
                	vy = (e.getVY() - Ymin)*ratio + height*0.1;
                	wx = (e.getWX() - Xmin)*ratio + width*0.1;
                	wy = (e.getWY() - Ymin)*ratio + height*0.1;
        	        distace = distace + e.weight();
//      	          int i = e.from();
//      	          int j = e.to();
//      	          System.out.print(i + ">>>" + j + "    ");
        	    	Line2D lin = new Line2D.Double(vy, vx, wy, wx);
        			g2.draw(lin);
        	    }
        	}
        	else {
        	    System.out.printf("%d to %d         no path\n", s, t);
        	}
        	g2.setColor(Color.BLACK);
        	g2.setStroke(new BasicStroke(1));
        	g2.drawString("distace in miles : " + distace, 10,20);
//        System.out.println("distace in miles : " + distace);
      	}

    }

    public static void pause(int t) {
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }


   	public static void main(String args[]) {
        Graph G = new Graph();
//        int s = 22659;
        int s = 0;
        Dijkstra content= new Dijkstra(G, s);
        JFrame window = new JFrame("GPS");
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(120,70);
        window.setSize(550, 550);
        window.setVisible(true);


    }

}