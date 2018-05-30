package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;

import clienthandler.ClientHandler;
import clienthandler.PipeGameClientHandler;
import test.Run;

public class MyServer implements Server {

	private int port;
	private volatile boolean stop;
	volatile BlockingQueue<Runnable> blockingQueue;
	public MyServer(int port) {

		this.port = port;
		stop = false;
	}
	private void startServer() throws IOException {
		blockingQueue = new PriorityBlockingQueue<Runnable>();
		PriorityThreadPool threadPoolExecutor = new PriorityThreadPool(1,3,10, TimeUnit.SECONDS,blockingQueue);
		ServerSocket theServer = new ServerSocket(port);
		theServer.setSoTimeout(7000);

		//add this section to test if the priority works - adding a long task at the start of the queue
//        threadPoolExecutor.execute(()->{
////            try {
////                Thread.sleep(10000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        });

		while (isRunning()) {
			try {
				Socket aClient = theServer.accept();
				threadPoolExecutor.execute((() -> { {
					try {
						ClientHandler ch = new PipeGameClientHandler();
						ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
						aClient.close();

						//add this section to test if the priority works - so we can tell what task ended first
//                            try {
//                                Thread.sleep(3000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }

					} catch (IOException e) {
						e.printStackTrace();
					}}}),null ,aClient.getInputStream().available());

			} catch (IOException e) {
				//e.printStackTrace();
			}

		}


		theServer.close();
		threadPoolExecutor.shutdown();
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


}