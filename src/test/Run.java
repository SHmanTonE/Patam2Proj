package test;

import java.io.IOException;

import board.PipeGameBoard;
import cachemanager.PipeGameCacheManager;
import clienthandler.PipeGameClientHandler;
import searcher.BestFirstSearch;
import searcher.Bfs;
import server.MyServer;
import server.Server;
import solver.PipeGameSolver;

public class Run {

	public static void main(String[] args) throws IOException {
		
		Server s = new MyServer(6400);
		s.runServer(new PipeGameClientHandler(new PipeGameCacheManager(),new PipeGameSolver(new Bfs<PipeGameBoard>())));
	}

}
