package cachemanager;

import java.io.IOException;

import solution.Solution;

public interface CacheManager<Problem> {

	Solution<Problem> isSolved(Problem problem);
	void saveSolution(Problem problem, Solution<Problem> solution) throws IOException;

}
