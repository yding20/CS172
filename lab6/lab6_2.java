import java.util.Stack;

public class lab6_2 {

    private int[] a;
    private int n;
    private Stack<int[]> stack;

    public lab6_2(int size) {
        a = new int[size];
        n = size;
        stack = new Stack<int[]>();
        enumerate(a, 0);
    }

    // draw n-by-n percolation system

    public Stack<int[]> getStack() {
        return stack;
    }

    private static final int DELAY = 2000;

    public void draw() {
        int[] dra = stack.pop();
        //StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05*n, 1.05*n);
        StdDraw.setYscale(-0.05*n, 1.05*n);   // leave a border to write text
        StdDraw.filledSquare(n/2.0, n/2.0, n/2.0); // mark background all black

        // draw n-by-n grid
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (col%2 != row%2) {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                else {
                    StdDraw.setPenColor(StdDraw.GRAY);
                }
                if (dra[row-1] == col-1) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                }
                // Y is begin from bottom
                StdDraw.filledSquare(col - 0.5, n - row + 0.5, 0.25);
            }
        }

    }

    private void enumerate(int[] q, int k) {
        int n = q.length;
        if (k == n) {
            int[] newarr = new int[n];
            for (int i = 0; i < n; i++)
                newarr[i] = q[i];
            stack.push(newarr);
            //System.exit(0);
        }
        else {
            for (int i = 0; i < n; i++) {
                q[k] = i;
                if (isConsistent(q, k)) enumerate(q, k+1);
            }
        }
    } 

    public void printQueens(int[] q) {
        int n = q.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (q[i] == j) System.out.print("Q ");
                else           System.out.print("* ");
            }
            System.out.println();
        }  
        System.out.println();
    }

    public boolean isConsistent(int[] q, int n) {
        for (int i = 0; i < n; i++) {
            if (q[i] == q[n])             return false;   // same column
            if ((q[i] - q[n]) == (n - i)) return false;   // same major diagonal
            if ((q[n] - q[i]) == (n - i)) return false;   // same minor diagonal
        }
        return true;
    }

    public static void main(String[] args) {
        chessboard oneInstance = new chessboard(8);
        while (!oneInstance.getStack().empty()) {
            oneInstance.draw();
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
    }
}
