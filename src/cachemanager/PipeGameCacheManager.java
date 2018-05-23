package cachemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import board.PipeGameBoard;
import solution.Solution;

public class PipeGameCacheManager implements CacheManager<PipeGameBoard> {

	Map<String, Solution<PipeGameBoard>> solutions;

	public PipeGameCacheManager() {
		solutions = new HashMap<String, Solution<PipeGameBoard>>();
		File dir = new File("Solutions");

		if (!dir.isDirectory()) { // if no solutions cached at all
			dir.mkdir();
		} else if (dir.list().length > 0) { // if at least one solution already cached
			for (int i = 0; i < dir.list().length; i++) {
			}
			File[] fileList = dir.listFiles();
			for (int i = 0; i < dir.list().length; i++) {
				if (fileList[i].length() != 0) { // if the file isn't empty
					BufferedReader in;
					try {
						in = new BufferedReader(new FileReader(fileList[i]));
						String temp = new String();
						String line = null;
						try {
							line = in.readLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
						}

						while (!line.equals("done")) {
							temp = temp + line + "\n";
							line = in.readLine();
						}

						Solution<PipeGameBoard> tempSolution = new Solution<PipeGameBoard>();
						tempSolution.getSolution().add(temp + "done");
						solutions.put(fileList[i].getName().replaceAll(".txt", ""), tempSolution);
						// System.out.println(tempSolution);
						in.close();
						// System.out.println("Load Solution from Cache");

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}

				}
			}
		}
	}

	@Override
	public Solution<PipeGameBoard> isSolved(PipeGameBoard problem) {
		Solution<PipeGameBoard> solution;
		if ((solution = solutions.get((new Integer(problem.toString().hashCode()).toString())))!= null) { // if there is a solution in the HashMap
			return solution;
		}
		return null;
		//new Integer(problem.toString().hashCode()).toString())
	}

	@Override
	public void saveSolution(PipeGameBoard problem, Solution<PipeGameBoard> solution) throws IOException {
		solutions.put((new Integer(problem.toString().hashCode()).toString()), solution);
		BufferedWriter out = new BufferedWriter(new FileWriter("Solutions/" + (new Integer(problem.toString().hashCode())).toString() + ".txt"));
		out.write(solution.toString());
		out.flush();
		out.close();
	}

}
