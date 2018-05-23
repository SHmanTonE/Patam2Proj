package solver;

import java.util.ArrayList;
import java.util.Stack;

import board.PipeGameBoard;
import board.PipeGameBoardSearchable;
import searcher.BestFirstSearch;
import searcher.Searcher;
import solution.Solution;
import state.State;

public class PipeGameSolver implements Solver<PipeGameBoard> {

	Searcher<PipeGameBoard> searcher;

	public PipeGameSolver() { // no Algo is chosen

	}

	public PipeGameSolver(Searcher<PipeGameBoard> searcher) { // inject algo in c'tor
		this.searcher = searcher;
	}

	@Override
	public Solution<PipeGameBoard> solve(PipeGameBoard problem) {
		if (this.searcher == null) {
			this.searcher = new BestFirstSearch<PipeGameBoard>();
		}

		Solution<PipeGameBoard> solution = searcher.search(new PipeGameBoardSearchable(problem));
		Stack<State<PipeGameBoard>> stateStack = solution.getStates();
		ArrayList<String> stringSolution = solution.getSolution();
		State<PipeGameBoard> goalState = stateStack.firstElement();
		State<PipeGameBoard> initialState = stateStack.lastElement();
		// PipeGameBoard
		// need to take all the state from the stack and reverse them into answer string

		char[][] endBoard = goalState.getState().getBoard();
		char[][] startBoard = initialState.getState().getBoard();
		int row = goalState.getState().getRowSize();
		int col = goalState.getState().getColSize();

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (startBoard[i][j] != endBoard[i][j]) {
					int spins = spins(startBoard[i][j], endBoard[i][j]);
					stringSolution.add(i + "," + j + "," + spins);
				}

			}

		}
		stringSolution.add("done");
		return solution;
	}

	int spins(char a, char b) {
		if (a == b)
			return 0;
		else
			return spins(rotateTile(a), b) + 1;
	}

	char rotateTile(char tile) {
		if (tile == '-')
			return '|';
		else if (tile == '|')
			return '-';
		else if (tile == '7')
			return 'J';
		else if (tile == 'J')
			return 'L';
		else if (tile == 'L')
			return 'F';
		else if (tile == 'F')
			return '7';
		else
			return tile;

	}

}
