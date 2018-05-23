package server;

import clienthandler.ClientHandler;

public interface Server {
	
	void runServer(ClientHandler clientHandler); // threaded server
	void stopServer();
	
}
