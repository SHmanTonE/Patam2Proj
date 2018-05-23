package board;

public class PipeGameBoard {

	private char[][] board;
	private int rowSize, colSize;
	private int startRowIndex, startColIndex;
	private int goalRowIndex, goalColIndex;

	public enum FromDirecetion {
		up, down, left, right, goal, start;
	}
	// public PipeGameBoard(String problem) {
	// col = problem.indexOf("\n");
	// problem = problem.replaceAll("\n", "");
	// row = problem.length() / col;
	// setIndexes(problem);
	// board = new char[row][col];
	// for (int i = 0; i < row; i++) {
	// for (int j = 0; j < col; j++) {
	// board[i][j] = problem.charAt((i * col) + j);
	// }
	// }
	// }

	public PipeGameBoard(char[][] problem) {
		board = new char[problem.length][problem[0].length];
		rowSize = problem.length;
		colSize = problem[0].length;
		for (int i = 0; i < rowSize; i++) // copy problem to board
			for (int j = 0; j < colSize; j++) {
				board[i][j] = problem[i][j];
			}
		// board = problem; // maybe needs deep copying like the comment above

		this.setStartAndGoalIndexes();
	}

	public PipeGameBoard(PipeGameBoard state) {
		board = new char[state.getRowSize()][state.getColSize()];
		char[][] tempBoard = state.getBoard();
		rowSize = state.getRowSize();
		colSize = state.getColSize();
		for (int i = 0; i < rowSize; i++) // copy problem to board
			for (int j = 0; j < colSize; j++) {
				board[i][j] = tempBoard[i][j];
			}
		// board = problem; // maybe needs deep copying like the comment above

		this.setStartAndGoalIndexes();

	}

	private void setStartAndGoalIndexes() { // maybe has a better implementation
		startRowIndex = -1;
		startColIndex = -1;
		goalRowIndex = -1;
		goalColIndex = -1;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 's') {
					startRowIndex = i;
					startColIndex = j;
				} else if (board[i][j] == 'g') {
					goalRowIndex = i;
					goalColIndex = j;
				}
			}
		}
		if (startRowIndex == -1 || startColIndex == -1) {
			// Error e = new Error("There is no start on the board");
			// e.printStackTrace();
		}
		if (goalRowIndex == -1 || goalColIndex == -1) {
			// Error e = new Error("There is no goal on the board");
			// e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				sb.append(board[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	//////////// GETTERS AND SETTERS ////////////

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getColSize() {
		return colSize;
	}

	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getStartColIndex() {
		return startColIndex;
	}

	public void setStartColIndex(int startColIndex) {
		this.startColIndex = startColIndex;
	}

	public int getGoalRowIndex() {
		return goalRowIndex;
	}

	public void setGoalRowIndex(int goalRowIndex) {
		this.goalRowIndex = goalRowIndex;
	}

	public int getGoalColIndex() {
		return goalColIndex;
	}

	public void setGoalColIndex(int goalColIndex) {
		this.goalColIndex = goalColIndex;

	}

	//////////// hashCode and equals ////////////

	@Override
	public int hashCode() {
		StringBuilder board = new StringBuilder();
		for (int i = 0; i < rowSize; i++) {
			board.append(this.board[i]);
		}
		return board.toString().hashCode();
	}

	@Override
	public boolean equals(Object board) {

		if (this.hashCode() == board.hashCode())
			return true;
		else
			return false;
	}

	//////////// Methods for Searchable ////////////

	public boolean isGoal(int row, int col, FromDirecetion c) {
		int hopRight = col + 1;
		int hopLeft = col - 1;
		int hopUp = row - 1;
		int hopDown = row + 1;
		if (row < 0 || col < 0 || row >= rowSize || col >= colSize)
			return false;
		if (board[row][col] == 's')
			return true;
		if (board[row][col] == ' ')
			return false;
		if (board[row][col] == 'g' && c != FromDirecetion.goal)
			return false;
		switch (c) {
		case goal:
			return (isGoal(row, hopRight, FromDirecetion.right) || isGoal(row, hopLeft, FromDirecetion.left)
					|| isGoal(hopUp, col, FromDirecetion.up) || isGoal(hopDown, col, FromDirecetion.down));
		case up:
			if (board[row][col] == '|')
				return isGoal(hopUp, col, FromDirecetion.up);
			else if (board[row][col] == 'F')
				return isGoal(row, hopRight, FromDirecetion.right);
			else if (board[row][col] == '7')
				return isGoal(row, hopLeft, FromDirecetion.left);
			else
				return false;
		case down:
			if (board[row][col] == '|')
				return isGoal(hopDown, col, FromDirecetion.down);
			else if (board[row][col] == 'L')
				return isGoal(row, hopRight, FromDirecetion.right);
			else if (board[row][col] == 'J')
				return isGoal(row, hopLeft, FromDirecetion.left);
			else
				return false;
		case left:
			if (board[row][col] == '-')
				return isGoal(row, hopLeft, FromDirecetion.left);
			else if (board[row][col] == 'L')
				return isGoal(hopUp, col, FromDirecetion.up);
			else if (board[row][col] == 'F')
				return isGoal(hopDown, col, FromDirecetion.down);
			else
				return false;
		case right:
			if (board[row][col] == '-')
				return isGoal(row, hopRight, FromDirecetion.right);
			else if (board[row][col] == '7')
				return isGoal(hopDown, col, FromDirecetion.down);
			else if (board[row][col] == 'J')
				return isGoal(hopUp, col, FromDirecetion.up);
			else
				return false;
		default:
			return false;
		}

		

	}

	boolean canReachStart(int row, int col, FromDirecetion c, int rowSource, int colSource) {
		if (row == rowSource && col == colSource && c != FromDirecetion.start)
			return false;
		int hopRight = col + 1;
		int hopLeft = col - 1;
		int hopUp = row - 1;
		int hopDown = row + 1;
		if (row < 0 || col < 0 || row >= rowSize || col >= colSize)
			return false;
		char position = board[row][col];
		if (position == 's')
			return true;
		if (position == ' ')
			return false;
		switch (c) {
		case start:
			if (position == '|')
				return (canReachStart(hopUp, col, FromDirecetion.up, rowSource, colSource)
						|| (canReachStart(hopDown, col, FromDirecetion.down, rowSource, colSource)));
			else if (position == '-')
				return (canReachStart(row, hopRight, FromDirecetion.right, rowSource, colSource)
						|| (canReachStart(row, hopLeft, FromDirecetion.left, rowSource, colSource)));
			else if (position == 'J')
				return ((canReachStart(hopUp, col, FromDirecetion.up, rowSource, colSource))
						|| (canReachStart(row, hopLeft, FromDirecetion.left, rowSource, colSource)));
			else if (position == '7')
				return ((canReachStart(hopDown, col, FromDirecetion.down, rowSource, colSource))
						|| (canReachStart(row, hopLeft, FromDirecetion.left, rowSource, colSource)));
			else if (position == 'L')
				return ((canReachStart(hopUp, col, FromDirecetion.up, rowSource, colSource))
						|| (canReachStart(row, hopRight, FromDirecetion.right, rowSource, colSource)));
			else if (position == 'F')
				return ((canReachStart(row, hopRight, FromDirecetion.right, rowSource, colSource))
						|| (canReachStart(hopDown, col, FromDirecetion.down, rowSource, colSource)));
		case up:
			if (position == '|')
				return (canReachStart(hopUp, col, FromDirecetion.up, rowSource, colSource));
			else if (position == 'F')
				return (canReachStart(row, hopRight, FromDirecetion.right, rowSource, colSource));
			else if (position == '7')
				return canReachStart(row, hopLeft, FromDirecetion.left, rowSource, colSource);
			else
				return false;
		case down:
			if (position == 'J')
				return canReachStart(row, hopLeft, FromDirecetion.left, rowSource, colSource);
			else if (position == 'L')
				return canReachStart(row, hopRight, FromDirecetion.right, rowSource, colSource);
			else if (position == '|')
				return canReachStart(hopDown, col, FromDirecetion.down, rowSource, colSource);
			else
				return false;
		case left:
			if (position == '-')
				return canReachStart(row, hopLeft, FromDirecetion.left, rowSource, colSource);
			else if (position == 'F')
				return canReachStart(hopDown, col, FromDirecetion.down, rowSource, colSource);
			else if (position == 'L')
				return canReachStart(hopUp, col, FromDirecetion.up, rowSource, colSource);
			else
				return false;
		case right:
			if (position == '7')
				return canReachStart(hopDown, col, FromDirecetion.down, rowSource, colSource);
			else if (position == 'J')
				return canReachStart(hopUp, col, FromDirecetion.up, rowSource, colSource);
			else if (position == '-')
				return canReachStart(row, hopRight, FromDirecetion.right, rowSource, colSource);
			else
				return false;
		default:
			return false;
		}
		
	}

	void rotateTile(int row, int col) {
		if (board[row][col] == '-')
			board[row][col] = '|';
		else if (board[row][col] == '|')
			board[row][col] = '-';
		else if (board[row][col] == '7')
			board[row][col] = 'J';
		else if (board[row][col] == 'J')
			board[row][col] = 'L';
		else if (board[row][col] == 'L')
			board[row][col] = 'F';
		else if (board[row][col] == 'F')
			board[row][col] = '7';

	}

	public boolean isNotOutOfBoundsAndConnected(int row, int col) {
		int hopRight = col + 1;
		int hopLeft = col - 1;
		int hopUp = row - 1;
		int hopDown = row + 1;

		if (board[row][col] == '-') {
			if (hopRight >= colSize || hopLeft < 0)
				return false;
			else {
				if ((board[row][hopLeft] == '-') || (board[row][hopLeft] == 'F') || (board[row][hopLeft] == 'L')
						|| (board[row][hopLeft] == 's') || (board[row][hopLeft] == 'g') || (board[row][hopRight] == 'J')
						|| (board[row][hopRight] == '-') || (board[row][hopRight] == '7')
						|| (board[row][hopRight] == 's') || (board[row][hopRight] == 'g'))
					return true;
				else
					return false;
			}
		}
		if (board[row][col] == '|') {
			if (hopUp < 0 || hopDown >= rowSize)
				return false;
			else {
				if ((board[hopUp][col] == '|') || (board[hopUp][col] == 'F') || (board[hopUp][col] == '7')
						|| (board[hopUp][col] == 'g') || (board[hopUp][col] == 's') || (board[hopDown][col] == '|')
						|| (board[hopDown][col] == 'J') || (board[hopDown][col] == 'L') || (board[hopDown][col] == 's')
						|| (board[hopDown][col] == 'g'))
					return true;
				else
					return false;
			}
		}
		if (board[row][col] == 'J') {
			if (hopUp < 0 || hopLeft < 0) {
				return false;
			} else {
				if ((board[hopUp][col] == '|') || (board[hopUp][col] == 'F') || (board[hopUp][col] == '7')
						|| (board[hopUp][col] == 'g') || (board[hopUp][col] == 's') || (board[row][hopLeft] == '-')
						|| (board[row][hopLeft] == 'F') || (board[row][hopLeft] == 'L') || (board[row][hopLeft] == 's')
						|| (board[row][hopLeft] == 'g')) {
					return true;
				} else
					return false;
			}
		}
		if (board[row][col] == '7') {
			if (hopLeft < 0 || hopDown >= rowSize)
				return false;
			else {
				if ((board[row][hopLeft] == '-') || (board[row][hopLeft] == 'F') || (board[row][hopLeft] == 'L')
						|| (board[row][hopLeft] == 's') || (board[row][hopLeft] == 'g') || (board[hopDown][col] == '|')
						|| (board[hopDown][col] == 'J') || (board[hopDown][col] == 'L') || (board[hopDown][col] == 's')
						|| (board[hopDown][col] == 'g'))
					return true;
				else
					return false;
			}
		}
		if (board[row][col] == 'L') {
			if (hopUp < 0 || hopRight >= colSize)
				return false;
			else {
				if ((board[row][hopRight] == 'J') || (board[row][hopRight] == '-') || (board[row][hopRight] == '7')
						|| (board[row][hopRight] == 's') || (board[row][hopRight] == 'g') || (board[hopUp][col] == '|')
						|| (board[hopUp][col] == 'F') || (board[hopUp][col] == '7') || (board[hopUp][col] == 'g')
						|| (board[hopUp][col] == 's'))
					return true;
				else
					return false;
			}
		}
		if (board[row][col] == 'F') {
			if (hopRight >= colSize || hopDown >= rowSize)
				return false;
			else {
				if ((board[row][hopRight] == 'J') || (board[row][hopRight] == '-') || (board[row][hopRight] == '7')
						|| (board[row][hopRight] == 's') || (board[row][hopRight] == 'g')
						|| (board[hopDown][col] == '|') || (board[hopDown][col] == 'J') || (board[hopDown][col] == 'L')
						|| (board[hopDown][col] == 's') || (board[hopDown][col] == 'g'))
					return true;
				else
					return false;
			}
		}
		return true;

	}

	public int howFar(int row, int col, FromDirecetion c) {
		int hopRight = col + 1;
		int hopLeft = col - 1;
		int hopUp = row - 1;
		int hopDown = row + 1;
		if (row < 0 || col < 0 || row >= rowSize || col >= colSize)
			return 0;
		if (board[row][col] == 'g')
			return 1;
		if (board[row][col] == ' ')
			return 0;
		if (board[row][col] == 'g' && c != FromDirecetion.start)
			return 0;
		switch (c) {
		case start:
			return Math.max(Math.max(Math.max(howFar(row, hopRight, FromDirecetion.right),howFar(row, hopLeft, FromDirecetion.left))
					,howFar(hopUp, col, FromDirecetion.up))
					,howFar(hopDown, col, FromDirecetion.down))+1;
		case up:
			if (board[row][col] == '|')
				return howFar(hopUp, col, FromDirecetion.up) + 1;
			else if (board[row][col] == 'F')
				return howFar(row, hopRight, FromDirecetion.right) + 1;
			else if (board[row][col] == '7')
				return howFar(row, hopLeft, FromDirecetion.left) + 1;
			else
				return 0;
		case down:
			if (board[row][col] == '|')
				return howFar(hopDown, col, FromDirecetion.down) + 1;
			else if (board[row][col] == 'L')
				return howFar(row, hopRight, FromDirecetion.right) + 1;
			else if (board[row][col] == 'J')
				return howFar(row, hopLeft, FromDirecetion.left) + 1;
			else
				return 0;
		case left:
			if (board[row][col] == '-')
				return howFar(row, hopLeft, FromDirecetion.left) + 1;
			else if (board[row][col] == 'L')
				return howFar(hopUp, col, FromDirecetion.up) + 1;
			else if (board[row][col] == 'F')
				return howFar(hopDown, col, FromDirecetion.down) + 1;
			else
				return 0;
		case right:
			if (board[row][col] == '-')
				return howFar(row, hopRight, FromDirecetion.right) + 1;
			else if (board[row][col] == '7')
				return howFar(hopDown, col, FromDirecetion.down) + 1;
			else if (board[row][col] == 'J')
				return howFar(hopUp, col, FromDirecetion.up) + 1;
			else
				return 0;
		default:
			return 0;
		}


	}

}
