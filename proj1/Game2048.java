import java.util.Random;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game2048 extends JPanel {
	private int[][] board;
	private int stepNum;

	final Color[] colorTable = {
    new Color(0x701710), new Color(0xFFE4C3), new Color(0xfff4d3),
    new Color(0xffdac3), new Color(0xe7b08e), new Color(0xe7bf8e),
    new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
    new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)};

    private Color gridColor = new Color(0xBBADA0);
    private Color emptyColor = new Color(0xCDC1B4);
    private Color startColor = new Color(0xFFEBCD);
    private Color stepcolor = new Color(0x663300);

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
		stepNum = -1;
		setPreferredSize(new Dimension(900, 700));
        setBackground(new Color(0xFAF8EF));
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setFocusable(true);
 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startGame();
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        move("w");
                        //addNum()；
                        break;
                    case KeyEvent.VK_DOWN:
                        move("s");
                        break;
                    case KeyEvent.VK_LEFT:
                        move("a");
                        break;
                    case KeyEvent.VK_RIGHT:
                        move("d");
                        break;
                }
                repaint();
            }
        });
	}

	@Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g);
    }

	void startGame() {
		stepNum++;
    }

    void drawGrid(Graphics2D g) {
        g.setColor(gridColor);
        g.fillRoundRect(200, 100, 499, 499, 15, 15);

        if (stepNum == -1) {
        	g.setColor(startColor);
            g.fillRoundRect(215, 115, 469, 469, 7, 7);
 
            g.setColor(gridColor.darker());
            g.setFont(new Font("SansSerif", Font.BOLD, 128));
            g.drawString("2048", 310, 270);
 
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.setColor(gridColor);
            g.drawString("click to start a new game", 330, 470);
            g.drawString("(use arrow keys to move tiles)", 310, 530);
        }
 
 		if (stepNum >= 0 && !isFull() && isWin()) {
        	for (int r = 0; r < 4; r++) {
        	    for (int c = 0; c < 4; c++) {
        	        if (board[r][c] == 0) {
        	            g.setColor(emptyColor);
        	            g.fillRoundRect(215 + c * 121, 115 + r * 121, 106, 106, 7, 7);
        	        } else {
        	            drawTile(g, r, c);
        	        }
        	    }
        	}
        	g.setColor(stepcolor);
       		g.setFont(new Font("SansSerif", Font.BOLD, 24));
       		g.drawString("Steps  :  " + stepNum, 400, 670);
       		g.setFont(new Font("SansSerif", Font.BOLD, 24));
        }

        if (isFull()) {
        	g.setColor(startColor);
            g.fillRoundRect(215, 115, 469, 469, 7, 7);
 
            g.setColor(gridColor.darker());
            g.setFont(new Font("SansSerif", Font.BOLD, 128));
            g.drawString("2048", 310, 270);
 
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString("game over", 400, 350);
        }

        if (!isWin()) {
        	g.setColor(startColor);
            g.fillRoundRect(215, 115, 469, 469, 7, 7);
 
            g.setColor(gridColor.darker());
            g.setFont(new Font("SansSerif", Font.BOLD, 128));
            g.drawString("2048", 310, 270);
 
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            g.drawString("You win", 400, 350);
        }
    }
 
    void drawTile(Graphics2D g, int r, int c) {
        int value = board[r][c];
 
        g.setColor(colorTable[(int) (Math.log(value) / Math.log(2)) + 1]);
        g.fillRoundRect(215 + c * 121, 115 + r * 121, 106, 106, 7, 7);
        String s = String.valueOf(value);
 
        g.setColor(value < 128 ? colorTable[0] : colorTable[1]);
 
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int dec = fm.getDescent();
 
        int x = 215 + c * 121 + (106 - fm.stringWidth(s)) / 2;
        int y = 115 + r * 121 + (asc + (106 - (asc + dec)) / 2);
 
        g.drawString(s, x, y);
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

		addNum();
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
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("2048");
        f.setResizable(true);
        f.add(new Game2048(), BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);















	}
}