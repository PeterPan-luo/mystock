package org.freemoney.socket;

import java.io.IOException;

import org.freemoney.utils.Constant;

public class SocketServer {

	public static void main(String[] args) {
		Thread server;
		try {
//			server = new Thread(new SingleSocketServer(Constant.PORT));
			server = new Thread(new MultiSocketServer(Constant.PORT, Constant.corePoolSize, Constant.maxPoolSize));
			
			server.start();
			System.out.println("Fund Server startup,listen on "+Constant.PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
