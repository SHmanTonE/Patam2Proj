package searchable;

import java.util.ArrayList;

import state.State;

public interface Searchable <T>{
	
	State<T> getInitialState();
	//State<T> getGoalState();
	boolean isGoalState(State<T> state);
	ArrayList<State<T>> getPossibleStates(State<T> state);
	
}
