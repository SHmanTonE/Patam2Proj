package searcher;

import java.util.Queue;

import solution.Solution;
import state.State;

public abstract class CommonSearcher<T> implements Searcher<T> {

	Queue<State<T>> openList; // needs to initialize in extended class
	int nodesCounter;

	public CommonSearcher() {
		nodesCounter = 0;
	}

	State<T> openListPop() { // one helper method for poll & counter raising
		nodesCounter++;
		return openList.poll();
	}
	protected Solution<T> backTrace(State<T> goalState, State<T> initialState) {
		// TODO Auto-generated method stub
			////  need stack for all states
		Solution<T> solution = new Solution<T>();
		solution.getStates().push(goalState);
		State<T> lastState = goalState.getCameFrom();
			if (goalState.getCameFrom()== null)
			return solution;
		while (!lastState.equals(initialState)) {
			solution.getStates().push(lastState);
			lastState = lastState.getCameFrom();
		}
		solution.getStates().push(initialState);
		return solution;
	}

	@Override
	public int getNodesEvaluated() {
		return nodesCounter;
	}

}
