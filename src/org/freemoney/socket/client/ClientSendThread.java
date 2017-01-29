package org.freemoney.socket.client;

import java.util.List;

import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class ClientSendThread implements Runnable{
	private String serverIP;
	private int serverPort;
	public ClientSendThread(String serverIp, int serverPort) {
		this.serverIP = serverIp;
		this.serverPort = serverPort;
	}
	@Override
	public void run() {
		ClientCommandProxy proxy= new ClientCommandProxy(serverIP, serverPort);
		
		List<FundStockListVO> result=proxy.getFundListByStock(201002, "600028");
		System.out.println("------------------------------------------------------------------");
		System.out.printf("%16s %8s %8s %12s %8s","基金名称","股票代码","股票名称","持仓数量","流通比例\n");
		for(int i=0;i<result.size();i++){
			FundStockListVO record=result.get(i);
			System.out.printf("\n%16s %8s %8s %12d %8.2f", record.getFundname().trim(),
					record.getStockid(),record.getStockname(),record.getStocknumber(),record.getCircleratio());
		}
		System.out.println("\n------------------------------------------------------------------");
		
		List<StockListVO> resultSar=proxy.getStockAddReduceTable(201002, 201002);
		System.out.println("getStockAddReduceTable="+resultSar.size());
		
		List<FundCompanyStockListVO> resultFc=proxy.getFcStockAddReduceTable(201002, 201002);
		System.out.println("getFcStockAddReduceTable="+resultFc.size());
	}
	
}
