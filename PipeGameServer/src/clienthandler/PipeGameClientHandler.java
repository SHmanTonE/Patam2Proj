package clienthandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import board.PipeGameBoard;
import cachemanager.CacheManager;
import cachemanager.PipeGameCacheManager;
import solution.Solution;
import solver.PipeGameSolver;
import solver.Solver;

public class PipeGameClientHandler implements ClientHandler {

	CacheManager<PipeGameBoard> cacheManager;
	Solver<PipeGameBoard> solver;

	public PipeGameClientHandler() { // Can be changed without parameters(default constructor)
		this.cacheManager = new PipeGameCacheManager();
		this.solver = new PipeGameSolver();
	}

	public PipeGameClientHandler(PipeGameCacheManager cacheManager, PipeGameSolver solver) { // Can be changed without
																								// parameters(default
																								// constructor)
		this.cacheManager = cacheManager;
		this.solver = solver;
	}

	@Override
	public void handleClient(InputStream in, OutputStream out) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(in));
		PrintWriter output = new PrintWriter(out);
		StringBuilder problemString = new StringBuilder();
		String msg = input.readLine();

		while (!msg.equals("done")) {
			problemString.append(msg + "\n");
			msg = input.readLine();
		}
		problemString.deleteCharAt(problemString.length() - 1);
		Solution<PipeGameBoard> solution;
		// if there is no solution in the cache

		if ((solution = cacheManager.isSolved(new PipeGameBoard(stringToCharArray(problemString.toString())))) == null) {
			solution = solver.solve(new PipeGameBoard(stringToCharArray(problemString.toString())));

			cacheManager.saveSolution((new PipeGameBoard(stringToCharArray(problemString.toString()))), solution);
		}

		output.println(solution.toString());
		output.flush();
		output.close();
		input.close();
		in.close();
		out.close();
	}

//	private String convertSpecialString(String string) {
//		char[] c = string.toCharArray();
//		for (int i = 0; i < c.length; i++) {
//			if (c[i] == '|') {
//				c[i] = 'I';
//			}
//		}
//		return new String(c);
//	}

	private char[][] stringToCharArray(String problem) {
		int colSize = problem.indexOf("\n");
		if (colSize == -1) {
			char[][] board = new char[1][problem.length()];
			board[0] = problem.toCharArray();
			return board;
		}
		problem = problem.replaceAll("\n", "");
		int rowSize = problem.length() / colSize;
		char[][] board = new char[rowSize][colSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				board[i][j] = problem.charAt((i * colSize) + j);
			}
		}
		return board;
	}

}
