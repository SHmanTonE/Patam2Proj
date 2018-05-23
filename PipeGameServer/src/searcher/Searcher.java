package searcher;

import searchable.Searchable;
import solution.Solution;

public interface Searcher<T> {

	Solution<T> search(Searchable<T> searchable);
	int getNodesEvaluated();

}
