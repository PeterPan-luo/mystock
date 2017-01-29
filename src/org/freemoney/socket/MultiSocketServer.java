package org.freemoney.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MultiSocketServer implements Runnable{

	protected final Log log = LogFactory.getLog(getClass());
	private ServerSocket serverSocket;
	private ThreadPoolExecutor pool;
	
	public MultiSocketServer(int port, int corePoolSize, int maxPoolSize) throws IOException {
		serverSocket = new ServerSocket(port);
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(500);	
		pool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS, queue);
		
	}
	@Override
	public void run() {
		
		while(true){
			try {
				pool.execute(new ServerHandle(serverSocket.accept()));
			} catch (Exception e) {
				log.error("pool execute error: ", e);
			}
		}
	}

}
