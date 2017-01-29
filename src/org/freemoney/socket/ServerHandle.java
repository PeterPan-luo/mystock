package org.freemoney.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freemoney.socket.entity.SocketMessage;

public class ServerHandle implements Runnable{
	protected final Log log = LogFactory.getLog(getClass());
	private Socket socket;
	
	public ServerHandle(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			SocketMessage cmd =(SocketMessage) ois.readObject();
			SocketMessage returnCmd = (new CommandHandler()).handleCommand(cmd);
			oos.writeObject(returnCmd);
		} catch (Exception e) {
			log.error("SingleSocketServer error: ", e) ;
		}finally{
			if(oos != null)
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(ois != null)
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
