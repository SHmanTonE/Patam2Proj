package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import clienthandler.ClientHandler;

public class MyServer implements Server {

	private int port;
	private volatile boolean stop;

	public MyServer(int port) {

		this.port = port;
		stop = false;
	}

	private void startServer(ClientHandler clientHandler) throws IOException {
		ServerSocket theServer = new ServerSocket(port);
		theServer.setSoTimeout(7000);
		//adir
		while (isRunning()) {
			try {
				// System.out.println("Waiting for a client on port: " + port);
				Socket aClient = theServer.accept();
				// System.out.println(aClient.getInetAddress() + " Connected");
				clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
				aClient.close();
				// System.out.println(aClient.getInetAddress()+" Connection closed");

				//break; // not threaded server yet
			} catch (SocketTimeoutException e) {
				// System.out.println(e.getMessage() + " ~~SocketTimeoutException");
			} catch (IOException e) {
				// System.out.println(e.getMessage() + " ~~IOException");
			}
		}
		theServer.close();
		stopServer();
	}

	@Override
	public void runServer(ClientHandler clientHandler) {
		new Thread(() -> {
			try {
				startServer(clientHandler);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}).start();

		// try {
		// startServer(clientHandler);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public void stopServer() {
		setRunning(false);
	}

	public boolean isRunning() {
		return !stop;
	}

	public void setRunning(boolean stop) {
		this.stop = !stop;
	}

}
