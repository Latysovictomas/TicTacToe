package com.tictactoe.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GameMain extends JFrame {

	private GameState currentState;
	private Seed currentPlayer;
	private TheBoard board;
	private DrawCanvas canvas;
	private JLabel statusBar;

	// variables for drawing
	private final int GRID_WIDTH = 5;
	private final int CELL_SIZE = 100;
	private final int CANVAS_HEIGHT = CELL_SIZE * TheBoard.getColumns() + GRID_WIDTH * 2;
	private final int CANVAS_WIDTH = CELL_SIZE * TheBoard.getROWS() + GRID_WIDTH * 2;
	private BufferedImage blueCircle;
	private BufferedImage redX;

	public GameMain() {
		// Create JPanel
		loadImages();
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		// create status Bar as JLabel
		statusBar = new JLabel(" ");
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);

		// center and page end
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.PAGE_END);
		// set JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack(); // pack all the components in this JFrame
		setTitle("Tic-Tac-Toe");
		setVisible(true);

		board = new TheBoard();
		initGame();
	}

	public static void main(String[] args) {

		// implement run
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameMain();
			}
		});

	}

	private void initGame() {
		board.reset();
		currentPlayer = Seed.CROSS;
		currentState = GameState.PLAYABLE;
	}

	private void loadImages() {
		try {
			redX = ImageIO.read(getClass().getResourceAsStream("/redX.png"));
			blueCircle = ImageIO.read(getClass().getResourceAsStream("/blueCircle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {

		int line_width = 0;
		// Draw the grid-lines
		g.setColor(Color.LIGHT_GRAY);
		for (int row = 1; row < TheBoard.getROWS(); ++row) {
			g.fillRoundRect(0, CELL_SIZE * row + line_width, CANVAS_WIDTH, GRID_WIDTH, 0, 0);

			line_width += 5;

		}
		line_width = 0;
		for (int col = 1; col < TheBoard.getColumns(); ++col) {
			g.fillRoundRect(CELL_SIZE * col + line_width, 0, GRID_WIDTH, CANVAS_HEIGHT, 0, 0);
			line_width += 5;

		}

		// Draw Seeds
		int distance = 20;
		int line_height = 0;

		for (int r = 0; r < TheBoard.getROWS(); ++r) {

			line_width = 0;
			if (r > 0)
				line_height += 5;
			for (int c = 0; c < TheBoard.getColumns(); ++c) {

				if (board.getBoardSeed(r, c) == Seed.CROSS) {
					g.drawImage(redX, c * CELL_SIZE + (distance / 2) + line_width,
							r * CELL_SIZE + (distance / 2) + line_height, CELL_SIZE - distance, CELL_SIZE - distance,
							null);
				} else if (board.getBoardSeed(r, c) == Seed.NOUGHT) {

					g.drawImage(blueCircle, c * CELL_SIZE + (distance / 2) + line_width,
							r * CELL_SIZE + (distance / 2) + line_height, CELL_SIZE - distance, CELL_SIZE - distance,
							null);
				} else if (board.getBoardSeed(r, c) == Seed.EMPTY) {
					// do nothing

				}
				line_width += 5;
			}

		}

		// Print Status bar message

		if (board.checkWinAndTie(currentState) == GameState.PLAYABLE && currentPlayer == Seed.CROSS) {
			statusBar.setText("Player 'X', your turn.");
		} else if (board.checkWinAndTie(currentState) == GameState.PLAYABLE && currentPlayer == Seed.NOUGHT) {
			statusBar.setText("Player 'O', your turn.");
		} else if (board.checkWinAndTie(currentState) == GameState.X_WON) {
			statusBar.setText("Player 'X' won! Click to play again.");
		} else if (board.checkWinAndTie(currentState) == GameState.O_WON) {
			statusBar.setText("Player 'O' won! Click to play again.");
		} else if (board.checkWinAndTie(currentState) == GameState.TIE) {
			statusBar.setText("Draw! Click to play again.");
		}
	}

	private class DrawCanvas extends JPanel implements MouseListener {

		public DrawCanvas() {
			setBackground(Color.WHITE);
			addMouseListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			render(g);

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (board.checkWinAndTie(currentState) != GameState.PLAYABLE) {
				initGame();
				repaint();
			} else {
				int mouseX = 0;
				int mouseY = 0;
				mouseX = e.getX();
				mouseY = e.getY();
				if (board.playerMove(currentPlayer, mouseX, mouseY) == true) {
					currentState = board.checkWinAndTie(currentState);
					currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
					repaint();
				} else {
					if (currentPlayer == Seed.CROSS)
						statusBar.setText("Player 'X' try again.");
					else if (currentPlayer == Seed.NOUGHT) {
						statusBar.setText("Player 'O' try again.");
					}
				}

			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}