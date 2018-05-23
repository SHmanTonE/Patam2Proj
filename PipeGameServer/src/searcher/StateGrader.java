package searcher;

import state.State;

public interface StateGrader<T> {
	int grade(State<T> state);
}
