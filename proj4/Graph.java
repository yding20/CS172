import java.util.Stack;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Set;

public class Graph extends javax.swing.JFrame {

    private int V;
    private int E;
    private Stack<Edge>[] adj;
    HashMap<String, Node> hmnode;
    double Xmax = Double.NEGATIVE_INFINITY;;
    double Xmin = Double.POSITIVE_INFINITY;;
    double Ymax = Double.NEGATIVE_INFINITY;;
    double Ymin = Double.POSITIVE_INFINITY;;

    private class Node {
        private int nodeNum;
        private double x;
        private double y;
        private Node(int num, double x, double y) {
            nodeNum = num;
            this.x = x;
            this.y = y;
        }
    }

    public Graph() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        hmnode = new HashMap<String, Node>();
        try {
            File file = new File("p4dataset/nys.txt");

            Scanner sc0 = new Scanner(file);
            while (sc0.hasNextLine()) {
                String line0 = sc0.nextLine();
                String[] stringarr0 = line0.split("\\s+");
                if (stringarr0[0].equals("i")) 
                    V++;
            }
            adj = (Stack<Edge>[]) new Stack[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Stack<Edge>();
            }

            Scanner sc = new Scanner(file);
            int count = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] stringarr = line.split("\\s+");
                if (stringarr[0].equals("i")) {
                    String nodeName = stringarr[1];
                    String strx = stringarr[2];
                    String stry = stringarr[3];
                    Double douX = -Double.parseDouble(strx);
                    Double douY = Double.parseDouble(stry);

                    if (douX < Xmin)
                        Xmin = douX;
                    if (douX > Xmax)
                        Xmax = douX;

                    if (douY < Ymin)
                        Ymin = douY;
                    if (douY > Ymax)
                        Ymax = douY;

                    Node node = new Node(count, douX, douY);
                    hmnode.put(nodeName, node);
                    count++;
                } else {
                    String v = stringarr[2];
                    String w = stringarr[3];
                    int i = hmnode.get(v).nodeNum;
                    int j = hmnode.get(w).nodeNum;
                    double vx = hmnode.get(v).x;
                    double vy = hmnode.get(v).y;
                    double wx = hmnode.get(w).x;
                    double wy = hmnode.get(w).y;
                    double weight = Math.sqrt((vx-wx)*(vx-wx)+(vy-wy)*(vy-wy));
                    Edge e = new Edge(i, j, weight, vx, vy, wx, wy);
                    addEdge(e);
                    E++;
                }
            }
        } catch (FileNotFoundException e) { } 
    }

    public void addEdge(Edge e) {
        int i = e.either();
        int j = e.other(i);
        adj[i].push(e);
        adj[j].push(e);
    }

    public void getS () {
        for (int m = 0; m < 10; m++) {
            for (Edge e: adj[m]) {
                int i = e.either();
                int j = e.other(i);
                double weight = e.weight();
//                System.out.print("The node " + i + " :   ");
//                System.out.print(i + ">>>" + j + "    ");
                //System.out.print(i + ">>>" + j + "  :  "+ weight + "       ");
        }
//        System.out.print("\n");
//        System.out.println("************");
        }
        System.out.println(Xmin);
        System.out.println(Xmax);
        System.out.println(Ymin);
        System.out.println(Ymax);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double vx;
        double vy;
        double wx;
        double wy;
        double xrange = Xmax-Xmin;
        double yrange = Ymax-Ymin;
        double range = xrange;
        if (xrange < yrange)
            range = yrange;
        double ratio = (1000/range)/2;

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
    }


	public static void main(String args[]) {
        Graph G = new Graph();
        G.getS();
        G.setVisible(true);










	}

}