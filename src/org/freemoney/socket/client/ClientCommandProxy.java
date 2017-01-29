package org.freemoney.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freemoney.socket.entity.SocketMessage;
import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class ClientCommandProxy {
	protected final Log log = LogFactory.getLog(getClass());
	
	private String serverIP;//服务器IP
	private int serverPort;//服务器端口
	public ClientCommandProxy(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public List<FundStockListVO> getFundListByStock(int quarter, String stockId) {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		Socket socket = null;
		List<FundStockListVO> result = new ArrayList<>();
		try {
			SocketMessage msg = new SocketMessage();
			msg.setCmdNo(SocketMessage.QUERYSTOCKQUARTER);
			Map<String, Object> body = new HashMap<>();
			body.put(SocketMessage.KEY_BEGINQUARTER, quarter);
			body.put(SocketMessage.KEY_STOCKID, stockId);
			msg.setCmdBody(body);
			socket = new Socket(serverIP, serverPort);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			//发送消息
			oos.writeObject(msg);
			body = ((SocketMessage)ois.readObject()).getCmdBody();
			result = (List<FundStockListVO>) body.get(SocketMessage.KEY_RESULT);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(ois != null)
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(oos!= null)
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	/**
	 * 获得股票增、减仓情况列表，按增、减仓占流通盘的比例排序
	 * @param beginQuarter	比较开始季度
	 * @param endQuarter	比较结束季度 
	 * @return
	 */
	public List<StockListVO> getStockAddReduceTable(int beginQuarter,int endQuarter){
		ObjectOutputStream clientOutputStream=null;
		ObjectInputStream clientInputStream=null;
		List<StockListVO> result=new ArrayList<StockListVO>();
		try{
			SocketMessage cmdStockAddReduce=new SocketMessage();
			cmdStockAddReduce.setCmdNo(SocketMessage.STOCKADDREDUCE);
			Map<String, Object> body=new HashMap();
			body.put(SocketMessage.KEY_BEGINQUARTER, beginQuarter);
			body.put(SocketMessage.KEY_ENDQUARTER, endQuarter);
			cmdStockAddReduce.setCmdBody(body);
			Socket socketConection=new Socket(serverIP,serverPort);
			clientOutputStream=new ObjectOutputStream(socketConection.getOutputStream());
			clientInputStream=new ObjectInputStream(socketConection.getInputStream());
			clientOutputStream.writeObject(cmdStockAddReduce);
			body=((SocketMessage)clientInputStream.readObject()).getCmdBody();
			result=(List<StockListVO>)body.get(SocketMessage.KEY_RESULT);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(clientOutputStream!=null)
				try {
					clientOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(clientInputStream!=null)
				try {
					clientInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	/**
	 * 获取基金公司股票增、减仓情况列表，按增、减仓占流通盘比例排序
	 * @param beginQuarter	比较起始季度
	 * @param endQuarter	比较结束季度
	 * @return
	 */
	public List<FundCompanyStockListVO> getFcStockAddReduceTable(int beginQuarter,int endQuarter){
		ObjectOutputStream clientOutputStream=null;
		ObjectInputStream clientInputStream=null;
		List<FundCompanyStockListVO> result=new ArrayList<FundCompanyStockListVO>();
		try{
			SocketMessage cmdFcStockAddReduce=new SocketMessage();
			cmdFcStockAddReduce.setCmdNo(SocketMessage.FCSTOCKADDREDUCE);
			Map<String, Object> body=new HashMap();
			body.put(SocketMessage.KEY_BEGINQUARTER, beginQuarter);
			body.put(SocketMessage.KEY_ENDQUARTER, endQuarter);
			cmdFcStockAddReduce.setCmdBody(body);
			Socket socketConection=new Socket(serverIP,serverPort);
			clientOutputStream=new ObjectOutputStream(socketConection.getOutputStream());
			clientInputStream=new ObjectInputStream(socketConection.getInputStream());
			clientOutputStream.writeObject(cmdFcStockAddReduce);
			body=((SocketMessage)clientInputStream.readObject()).getCmdBody();
			result=(List<FundCompanyStockListVO>)body.get(SocketMessage.KEY_RESULT);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(clientOutputStream!=null)
				try {
					clientOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(clientInputStream!=null)
				try {
					clientInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
}