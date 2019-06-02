package com.tictactoe.core;

public class TheBoard {
	private static final int ROWS = 3;
	private static final int COLUMNS = 3;
	private Seed[][] seedArray;
	private Seed theSeed;
	private int currentRow = -1;
	private int currentCol = -1;

	public TheBoard() {

		seedArray = new Seed[ROWS][COLUMNS];
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				seedArray[r][c] = Seed.EMPTY;
			}

		}

	}

	public void reset() {
		seedArray = new Seed[ROWS][COLUMNS];
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				seedArray[r][c] = Seed.EMPTY;
			}

		}
	}

	public GameState checkWinAndTie(GameState currentState) {
		// horizontal
		GameState state = currentState;
		if (seedArray[0][0] == theSeed && seedArray[0][1] == theSeed && seedArray[0][2] == theSeed
				|| seedArray[1][0] == theSeed && seedArray[1][1] == theSeed && seedArray[1][2] == theSeed
				|| seedArray[2][0] == theSeed && seedArray[2][1] == theSeed && seedArray[2][2] == theSeed ||
				// vertical
				seedArray[0][0] == theSeed && seedArray[1][0] == theSeed && seedArray[2][0] == theSeed
				|| seedArray[0][1] == theSeed && seedArray[1][1] == theSeed && seedArray[2][1] == theSeed
				|| seedArray[0][2] == theSeed && seedArray[1][2] == theSeed && seedArray[2][2] == theSeed ||
				// top left to bottom right
				seedArray[0][0] == theSeed && seedArray[1][1] == theSeed && seedArray[2][2] == theSeed ||
				// top right to bottom left
				seedArray[0][2] == theSeed && seedArray[1][1] == theSeed && seedArray[2][0] == theSeed) {

			if (theSeed == Seed.CROSS) {
				state = GameState.X_WON;

			} else if (theSeed == Seed.NOUGHT) {
				state = GameState.O_WON;

			}

		} else if (isFull() == true) {

			state = GameState.TIE;

		}

		return state;

	}

	public boolean isFull() {
		boolean state = false;
		int count = 0;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {

				if (seedArray[r][c] != Seed.EMPTY) {
					count++;
				}
			}

		}

		if (count == 9) {

			state = true;
			;
		}
		return state;
	}

	public static int getROWS() {
		return ROWS;
	}

	public static int getColumns() {
		return COLUMNS;
	}

	public Seed getBoardSeed(int row, int col) {

		Seed content = null;

		if (seedArray[row][col] == Seed.CROSS) {
			content = Seed.CROSS;
		} else if (seedArray[row][col] == Seed.NOUGHT) {
			content = Seed.NOUGHT;
		} else {
			content = Seed.EMPTY;
		}

		return content;
	}

	public boolean playerMove(Seed currentPlayer, int mouseX, int mouseY) {

		boolean validInput = false;

		updateSeed(currentPlayer);

		setCurrentRowAndCol(mouseX, mouseY);

		if (currentRow >= 0 && currentRow < TheBoard.getROWS() && currentCol >= 0 && currentCol < TheBoard.getColumns()
				&& seedArray[currentRow][currentCol] == Seed.EMPTY) {

			seedArray[currentRow][currentCol] = theSeed; // update game-board content
			validInput = true;
		}
		return validInput;
	}

	public void setCurrentRowAndCol(int mouseX, int mouseY) {
		int x = mouseX;
		int y = mouseY;
		// first row
		if (x <= 100 && y <= 100) {
			currentRow = 0;
			currentCol = 0;
		} else if (x >= 105 && x <= 205 && y <= 100) {
			currentRow = 0;
			currentCol = 1;
		} else if (x >= 210 && y <= 100) {
			currentRow = 0;
			currentCol = 2;
		}
		// second row
		else if (x <= 100 && y >= 105 && y <= 205) {
			currentRow = 1;
			currentCol = 0;
		} else if (x >= 105 && x <= 205 && y >= 105 && y <= 205) {
			currentRow = 1;
			currentCol = 1;
		} else if (x >= 210 & y >= 105 && y <= 205) {
			currentRow = 1;
			currentCol = 2;
		}
		// third row
		else if (x <= 100 && y >= 210) {
			currentRow = 2;
			currentCol = 0;
		} else if (x >= 105 && x <= 205 && y >= 210) {
			currentRow = 2;
			currentCol = 1;
		} else if (x >= 210 && y >= 210) {
			currentRow = 2;
			currentCol = 2;
		}

	}

	public void updateSeed(Seed currentPlayer) {
		if (currentPlayer == Seed.CROSS) {

			theSeed = Seed.CROSS;
		} else if (currentPlayer == Seed.NOUGHT) {
			theSeed = Seed.NOUGHT;
		}

	}

}
