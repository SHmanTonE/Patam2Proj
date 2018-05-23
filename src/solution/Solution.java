package solution;

import java.util.ArrayList;
import java.util.Stack;

import state.State;

public class Solution<T> {

	ArrayList<String> solution;
	Stack<State<T>> states;

	public Solution() {
		solution = new ArrayList<String>();
		states = new Stack<State<T>>();
	}

	public ArrayList<String> getSolution() {
		return solution;
	}

	public Stack<State<T>> getStates() {
		return states;
	}

	public void setStates(Stack<State<T>> states) {
		this.states = states;
	}

	public void setSolution(ArrayList<String> solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < solution.size(); i++) {
			sb.append(solution.get(i) + System.lineSeparator());
		}
		return sb.toString();
	}
}
