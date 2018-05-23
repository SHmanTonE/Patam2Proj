package searcher;

import java.util.ArrayList;
import java.util.Random;

import searchable.Searchable;
import solution.Solution;
import state.State;

public class HillClimbing<T> implements Searcher<T> {
	StateGrader<T> grader;
	long timeToRun;
	int nodesCounter;

	public HillClimbing(StateGrader<T> grader, int timeToRun) {
		this.grader = grader;
		this.timeToRun = timeToRun;
	}

	@Override
	public Solution<T> search(Searchable<T> searchable) {
		State<T> next = searchable.getInitialState();
		Solution<T> solution = new Solution<T>();
		long time0 = System.currentTimeMillis();
		solution.getStates().add(next);
		while (System.currentTimeMillis() - time0 < timeToRun) {
			ArrayList<State<T>> neighbors = searchable.getPossibleStates(next);
			if (Math.random() < 0.5) { // with High probability
				int grade = 0;
				for (State<T> state : neighbors) {
					nodesCounter++;
					int g = grader.grade(state);
					if (g > grade) {
						grade = g;
						next = state;
						solution.getStates().add(next);
					}
				}

			} else {
				next = neighbors.get((new Random().nextInt(neighbors.size())));
			}
		}
		return solution;
	}

	@Override
	public int getNodesEvaluated() {
		
		return nodesCounter;
	}

}
