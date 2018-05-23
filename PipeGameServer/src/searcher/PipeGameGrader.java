package searcher;

import board.PipeGameBoard;
import board.PipeGameBoard.FromDirecetion;
import state.State;

public class PipeGameGrader implements StateGrader<PipeGameBoard> {

	@Override
	public int grade(State<PipeGameBoard> state) {
		int startRowPosition = state.getState().getStartRowIndex();
		int startColPosition = state.getState().getStartColIndex();
		return state.getState().howFar(startRowPosition, startColPosition,  FromDirecetion.start);
	}

}
