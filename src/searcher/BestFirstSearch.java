package searcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import searchable.Searchable;
import solution.Solution;
import state.State;

public class BestFirstSearch<T> extends CommonSearcher<T> {

	//// Queue<State<T>> openList;
	//// int nodesCounter;

	public BestFirstSearch() {

	}

	@Override
	public Solution<T> search(Searchable<T> searchable) {
		openList = new PriorityQueue<State<T>>(new StateComparator());

		openList.add(searchable.getInitialState());
		HashSet<State<T>> closedSet = new HashSet<State<T>>();
//		long time = System.currentTimeMillis();

		while (!openList.isEmpty()) {
			State<T> currentState = openListPop();
			closedSet.add(currentState);
			if (searchable.isGoalState(currentState)) { // if goal reached
//				time = System.currentTimeMillis() - time;    check run time for the algo
//				System.out.println("time : " + time);
				return backTrace(currentState, searchable.getInitialState());
			}
			ArrayList<State<T>> possibleStates = searchable.getPossibleStates(currentState);
//			System.out.println(nodesCounter);
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
	
	private class StateComparator implements Comparator<State<T>> {
		@Override
		public int compare(State<T> state1, State<T> state2) {
			// TODO Auto-generated method stub
			return (int) (state2.getCost()-state1.getCost());
		}
	}

}
