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
	private int s;
	private int t;
	private String start;
	private String dest;

	public Dijkstra(Graph G, String start, String dest) {
        //setPreferredSize(new Dimension(900, 700));
		this.G = G;
		this.start = start;
		this.dest = dest;
		s = (int) G.gethmnode(start)[2];
		t = (int) G.gethmnode(dest)[2];

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
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width =  getWidth();   // Find out the width of this component.
        int height = getHeight(); 
//        System.out.println(width + "**" + height);
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

        double xcor = (G.gethmnode(start)[0]-Xmin)*ratio + width*0.1;
        double ycor = (G.gethmnode(start)[1]-Ymin)*ratio + height*0.1;
        g2.setPaint(Color.BLUE);
        g2.fill (new Ellipse2D.Double(ycor-5, xcor-5, 10, 10));

        double xcor2 = (G.gethmnode(dest)[0]-Xmin)*ratio + width*0.1;
        double ycor2 = (G.gethmnode(dest)[1]-Ymin)*ratio + height*0.1;
        g2.setPaint(Color.YELLOW);
        g2.fill (new Ellipse2D.Double(ycor2-5, xcor2-5, 10, 10));
        g2.setColor(Color.BLACK);
       	g2.setStroke(new BasicStroke(1));
        g2.drawString("Start point in BLUE : " , 10, 40);
        g2.drawString("End point in YELLOW : " , 10, 60);
        g2.drawString("Click to find the path : " , 10, 80);


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
        Graph G = new Graph(args[0]);
        if (args[1].equals("--directions") || (args[1].equals("--show") &&
        	(args.length == 2)) || (args[1].equals("--show") && (args[2].equals("--directions")))) {
        	String start = args[args.length-2];
        	String dest = args[args.length-1];
	        Dijkstra content= new Dijkstra(G, start, dest);

	        JFrame window = new JFrame("GPS");
	        window.setContentPane(content);
	        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        window.setLocation(120,70);
	        window.setSize(550, 550);
	        window.setVisible(true);
        }

    }

}



// java Dijkstra p4dataset/nys.txt --show --directions i22659 i194129
// java Dijkstra p4dataset/ur.txt --show --directions HOYT MOREY
// java Dijkstra p4dataset/ur.txt --show --directions GILBERT-LONG MELIORA-NORTH






//        int t = 194129;
//        int s = 22659;

//        int t = 20;
//        int s = 0;



