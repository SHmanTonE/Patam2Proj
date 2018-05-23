package searcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

import searchable.Searchable;
import solution.Solution;
import state.State;

public class Dfs<T> implements Searcher<T> {

	Stack<State<T>> stack;
	int nodesCounter;

	@Override
	public Solution<T> search(Searchable<T> searchable) {
		this.stack = new Stack<State<T>>();
		State<T> currentState = searchable.getInitialState();
		stack.add(currentState);
		HashSet<State<T>> closedSet = new HashSet<State<T>>();
		// long time = System.currentTimeMillis();

		while (!stack.isEmpty()) {
			currentState = stackPop();
			closedSet.add(currentState);

			if (searchable.isGoalState(currentState)) { // if goal reached
				// time = System.currentTimeMillis() - time;
				// System.out.println("time : " +time);
				return backTrace(currentState, searchable.getInitialState());
			}
			ArrayList<State<T>> possibleStates = searchable.getPossibleStates(currentState);
			// System.out.println(nodesCounter);
			for (State<T> possibleState : possibleStates) {
				if ((!closedSet.contains(possibleState)) && (!stack.contains(possibleState))) {
					possibleState.setCameFrom(currentState);
					possibleState.setCost(possibleState.getCost() + currentState.getCost());// ??
					stack.add(possibleState);
				}
			}

		}
		return null;

	}

	@Override
	public int getNodesEvaluated() {
		return nodesCounter;
	}

	State<T> stackPop() { // one helper method for poll & counter raising
		nodesCounter++;
		return stack.pop();
	}

	protected Solution<T> backTrace(State<T> goalState, State<T> initialState) {
		// TODO Auto-generated method stub
		//// need stack for all states
		Solution<T> solution = new Solution<T>();
		solution.getStates().push(goalState);
		State<T> lastState = goalState.getCameFrom();
		if (goalState.getCameFrom() == null)
			return solution;
		while (!lastState.equals(initialState)) {
			solution.getStates().push(lastState);
			lastState = lastState.getCameFrom();
		}
		solution.getStates().push(initialState);
		return solution;
	}

	// public Solution<T> search(Searchable<T> searchable) {
	// openList = new LinkedList<State<T>>();
	// openList.add(searchable.getInitialState());
	// HashSet<State<T>> closedSet = new HashSet<State<T>>();
	// while (!openList.isEmpty()) {
	// State<T> currentState = openListPop();
	// closedSet.add(currentState);
	// long time = System.currentTimeMillis();
	// if (searchable.isGoalState(currentState)) { // if goal reached
	// time = System.currentTimeMillis() - time;
	// System.out.println("time : " +time);
	// return backTrace(currentState, searchable.getInitialState());
	// }
	// ArrayList<State<T>> possibleStates =
	// searchable.getPossibleStates(currentState);
	// System.out.println(nodesCounter);
	// for (State<T> possibleState : possibleStates) {
	// if ((!closedSet.contains(possibleState)) &&
	// (!openList.contains(possibleState))) {
	// possibleState.setCameFrom(currentState);
	// possibleState.setCost(possibleState.getCost() + currentState.getCost());// ??
	// openList.add(possibleState);
	// }
	// }
	// }
	// return null;
	// }

}
