package solver;

import searcher.Searcher;

public abstract class CommonSolver<T> implements Solver<T> {

	Searcher<T> searcher;

	public CommonSolver(Searcher<T> searcher) {
		this.searcher = searcher;
	}

}
