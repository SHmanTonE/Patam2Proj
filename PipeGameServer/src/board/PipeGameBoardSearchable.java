package board;

import java.util.ArrayList;

import board.PipeGameBoard.FromDirecetion;
import searchable.Searchable;
import state.State;

public class PipeGameBoardSearchable implements Searchable<PipeGameBoard> {

	PipeGameBoard InitialState;

	public PipeGameBoardSearchable(PipeGameBoard board) {
		this.InitialState = board;
	}

	@Override
	public State<PipeGameBoard> getInitialState() {
		return new State<PipeGameBoard>(InitialState);
	}

	@Override
	public boolean isGoalState(State<PipeGameBoard> state) {
		return state.getState().isGoal(InitialState.getGoalRowIndex(), InitialState.getGoalColIndex(),
				FromDirecetion.goal);

	}

	@Override
	public ArrayList<State<PipeGameBoard>> getPossibleStates(State<PipeGameBoard> state) {
		ArrayList<State<PipeGameBoard>> allPossibleStates = new ArrayList<State<PipeGameBoard>>();
		int row = state.getState().getRowSize();
		int col = state.getState().getColSize();
		int startRowPosition = state.getState().getStartRowIndex();
		int startColPosition = state.getState().getStartColIndex();

		char[][] currentBoard = state.getState().getBoard();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if ((currentBoard[i][j] != 's') && (currentBoard[i][j] != 'g') && (currentBoard[i][j] != ' ')) {
					if ((currentBoard[i][j] == '-') || currentBoard[i][j] == '|') {
						PipeGameBoard possibleBoard = new PipeGameBoard(currentBoard);
						possibleBoard.rotateTile(i, j);
						// need to add - if board is valid - can reach s and not out of bounds
						if (possibleBoard.isNotOutOfBoundsAndConnected(i, j)) {
							if ((possibleBoard.canReachStart(i, j, FromDirecetion.start, i, j))) {
								State<PipeGameBoard> possibleState = new State<PipeGameBoard>(possibleBoard);
								possibleState.setCost(possibleState.getState().howFar(startRowPosition,
										startColPosition, FromDirecetion.start));
								allPossibleStates.add(possibleState);
							}
						}

					} else {
						for (int k = 0; k < 3; k++) {
							PipeGameBoard possibleBoard = new PipeGameBoard(currentBoard);
							for (int l = 0; l <= k; l++) {
								possibleBoard.rotateTile(i, j);
							}

							if (possibleBoard.isNotOutOfBoundsAndConnected(i, j)) {
								if ((possibleBoard.canReachStart(i, j, FromDirecetion.start, i, j))) {
									State<PipeGameBoard> possibleState = new State<PipeGameBoard>(possibleBoard);
									possibleState.setCost(possibleState.getState().howFar(startRowPosition,
											startColPosition, FromDirecetion.start));
									allPossibleStates.add(possibleState);
								}
							}
						}
					}
				}
			}
		}

		return allPossibleStates;
	}
}
