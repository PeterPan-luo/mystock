package org.freemoney.socket.client;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class MultiThreadClientSimulator {

	static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(500);
	static ThreadPoolExecutor pool = new ThreadPoolExecutor(50, 300, 5, TimeUnit.SECONDS, queue);
	static final String serverIP = "127.0.0.1";
	static final int serverPort = 8989;
	
	public static void main(String[] args) {
		for (int i = 0; i < 5000; i++) {
			pool.execute(new ClientSendThread(serverIP, serverPort));
		}
	}

}
