import java.util.Random;
import java.util.*;
import java.io.*;


public class Game2048 {
	private int[][] board;
	private int stepNum;

	public Game2048() {
		Random rand = new Random();
		int px1 = rand.nextInt(4);
		int py1 = rand.nextInt(4);
		int px2 = rand.nextInt(4);
		int py2 = rand.nextInt(4);

		while ((px1 == px2) && (py1 == px2)) {
			px2 = rand.nextInt(4);
			py2 = rand.nextInt(4);	
		}

		board = new int[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				if ((i == px1 && j == py1) || (i == px2 && j == py2))
					board[i][j] = getValue();
			}

		stepNum = 0;
	}

	private int getPos() {
		Random rand = new Random();
		return rand.nextInt(4);
	}

	private int getValue() {
		if (Math.random() < 0.2)	return 4;
		else						return 2;
	}

	private boolean isFull() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (board[i][j] == 0)
					return false;
		return true;
	}

	public void printBoard() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] != 0)
					System.out.print(board[i][j] + "\t");
				else 
					System.out.print("*" + "\t");
			}
			System.out.print("\n");
		}
	}

	public boolean addNum() {
		if (isFull()) {
			System.out.println("GG");
			return false;
		}

		int px = getPos();
		int py = getPos();

		while(board[px][py] != 0) {
			px = getPos();
			py = getPos();
		}

		board[px][py] = getValue();
		return true;
	}

	public void move(String s) {

		int[][] oldboard = new int[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				oldboard[i] = board[j];

		if (s.equals("d"))
			moveRight();

		if (s.equals("a"))	
			moveLeft();

		if (s.equals("s"))	
			moveDown();

		if (s.equals("w"))	
			moveUp();

		increStep(oldboard);

		System.out.println("You step is : " + stepNum);
	}

	private boolean increStep(int[][] oldboard) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (oldboard[i] != board[j]) {
					stepNum++;
					return true;
				}
		return false;
	}

	private void moveRight() {
		for (int i = 0; i < 4; i++)
			for (int j = 3; j > 0; j--){
				if (board[i][j] ==  0) {
					board[i][j] = board[i][j-1];
					board[i][j-1] = 0;
				} else if (board[i][j] == board[i][j-1]) {
					board[i][j] = board[i][j] + board[i][j-1];
					board[i][j-1] = 0;
				}
			}

		for (int i = 0; i < 4; i++)
			for (int j = 3; j > 0; j--) 
				if ((board[i][j] ==  0 && board[i][j-1] != 0) || 
												(board[i][j] !=0 && board[i][j] == board[i][j-1]))
					moveRight();
		return;
	}

	private void moveLeft() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++){
				if (board[i][j] ==  0) {
					board[i][j] = board[i][j+1];
					board[i][j+1] = 0;
				} else if (board[i][j] == board[i][j+1]) {
					board[i][j] = board[i][j] + board[i][j+1];
					board[i][j+1] = 0;
				}
			}
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++) 
				if (board[i][j] ==  0 && board[i][j+1] != 0 ||
										(board[i][j] !=0 && board[i][j] == board[i][j+1]))
					moveLeft();
		return;
	}

	private void moveDown() {
		for (int j = 0; j < 4; j++)
			for (int i = 3; i > 0; i--){
				if (board[i][j] ==  0) {
					board[i][j] = board[i-1][j];
					board[i-1][j] = 0;
				} else if (board[i][j] == board[i-1][j]) {
					board[i][j] = board[i][j] + board[i-1][j];
					board[i-1][j] = 0;
				}
			}
		for (int j = 0; j < 4; j++)
			for (int i = 3; i > 0; i--) 
				if (board[i][j] ==  0 && board[i-1][j] != 0 || 
										(board[i][j] !=0 && board[i][j] == board[i-1][j]))
					moveDown();
		return;
	}

	private void moveUp() {
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 3; i++){
				if (board[i][j] ==  0) {
					board[i][j] = board[i+1][j];
					board[i+1][j] = 0;
				} else if (board[i][j] == board[i+1][j]) {
					board[i][j] = board[i][j] + board[i+1][j];
					board[i+1][j] = 0;
				}
			}
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 3; i++) 
				if (board[i][j] ==  0 && board[i+1][j] != 0 || 
								(board[i][j] !=0 && board[i][j] == board[i+1][j]))
					moveUp();
		return;
	}

	public boolean isWin() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (board[i][j] == 2048) {
					System.out.println("Win!!!");
					return false;
				}
		return true;
	}

	public static void main(String args[]) {
		Game2048 test = new Game2048();
		test.printBoard();

		Scanner scan = new Scanner(System.in);
		boolean TorF = true;
		boolean TorFwin = true;

		while(TorF && TorFwin) {
			String s = scan.next();
			test.move(s);
			TorFwin = test.isWin();
			//test.printBoard();
			//System.out.println("Press any key to pop a new number");
			//scan.next();
			TorF = test.addNum();
            test.printBoard();
        }
        scan.close();















	}
}