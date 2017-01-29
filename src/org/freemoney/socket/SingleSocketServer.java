package org.freemoney.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freemoney.socket.entity.SocketMessage;

public class SingleSocketServer implements Runnable{

	protected final Log log = LogFactory.getLog(getClass());
	private ServerSocket serverSocket;
	
	public SingleSocketServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		while(true){
			try {
				Socket socket = serverSocket.accept();
				ObjectInputStream ois = null;
				ObjectOutputStream oos = null;
				try {
					ois = new ObjectInputStream(socket.getInputStream());
					oos = new ObjectOutputStream(socket.getOutputStream());
					SocketMessage cmd =(SocketMessage) ois.readObject();
					SocketMessage returnCmd = (new CommandHandler()).handleCommand(cmd);
					oos.writeObject(returnCmd);
				} catch (IOException e) {
					log.error("SingleSocketServer error: ", e);
				}finally{
					if(oos != null)
						oos.close();
					if(ois != null)
						ois.close();
					if(socket != null)
						socket.close();
				}
				
			} catch (Exception e) {
				log.error("SingleSocketServer error:",e);
				e.printStackTrace();
			}
			
		}
	}
}
