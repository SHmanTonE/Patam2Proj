package test;

import java.io.IOException;
import clienthandler.PipeGameClientHandler;
import server.MyServer;
import server.Server;


public class Run {

	public static void main(String[] args) throws IOException {
		Server s = new MyServer(6400);
		s.runServer(new PipeGameClientHandler());


	}

}
