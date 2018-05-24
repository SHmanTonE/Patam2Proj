package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clienthandler.ClientHandler;
import clienthandler.PipeGameClientHandler;

public class MyServer implements Server {

	private int port;
	private volatile boolean stop;
	volatile Queue<Socket> socketList;
	public MyServer(int port) {

		this.port = port;
		stop = false;
	}

	private void startServer() throws IOException {
		int numOfThreads = 5;
		ExecutorService es = Executors.newFixedThreadPool(numOfThreads);
		ServerSocket theServer = new ServerSocket(port);
		theServer.setSoTimeout(7000);
		socketList = new PriorityQueue<>(new PipeGameSocketComparator());

		new Thread(() -> {
			while (isRunning()) {
				try {
					// Waiting for a client
					Socket aClient = theServer.accept();
					socketList.add(aClient);

				} catch (IOException e) {
					// e.printStackTrace();
				}

			}}).start();

		while (isRunning() || !socketList.isEmpty()) {
			while (!socketList.isEmpty()) {
				Socket aClient = socketList.poll();
				es.submit(new Runnable() {
					@Override
					public void run() {
						try {
							ClientHandler ch = new PipeGameClientHandler();
							ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
							aClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}}});}}

		theServer.close();
		stopServer();
		es.shutdown();
		System.exit(0);
	}


	@Override
	public void runServer(ClientHandler clientHandler) {
		new Thread(() -> {
			try {
				startServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}).start();
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

	private class PipeGameSocketComparator implements Comparator<Socket> {
		@Override
		public int compare(Socket s1, Socket s2) {
			try {
				return s1.getInputStream().available() - s2.getInputStream().available();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}

}
