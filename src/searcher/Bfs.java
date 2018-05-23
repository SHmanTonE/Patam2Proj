package searcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import searchable.Searchable;
import solution.Solution;
import state.State;

public class Bfs<T> extends CommonSearcher<T> {

	//// Queue<State<T>> openList;
	//// int nodesCounter;

	public Bfs() { // ????????????????????
	
	}

	@Override
	public Solution<T> search(Searchable<T> searchable) {
		openList = new LinkedList<State<T>>();
		openList.add(searchable.getInitialState());
		HashSet<State<T>> closedSet = new HashSet<State<T>>();
		// long time = System.currentTimeMillis();

		while (!openList.isEmpty()) {
			State<T> currentState = openListPop();
			closedSet.add(currentState);
			if (searchable.isGoalState(currentState)) { // if goal reached
				// time = System.currentTimeMillis() - time;
				// System.out.println("time : " +time);
				return backTrace(currentState, searchable.getInitialState());
			}
			ArrayList<State<T>> possibleStates = searchable.getPossibleStates(currentState);
			// System.out.println(nodesCounter);
			for (State<T> possibleState : possibleStates) {
				if ((!closedSet.contains(possibleState)) && (!openList.contains(possibleState))) {
					possibleState.setCameFrom(currentState);
					possibleState.setCost(possibleState.getCost() + currentState.getCost());// ??
					openList.add(possibleState);
				}
			}
		}
		return null;
	}

}
