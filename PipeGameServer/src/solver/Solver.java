package solver;

import solution.Solution;

public interface Solver <Problem>{

	Solution<Problem> solve(Problem problem);
	
}
