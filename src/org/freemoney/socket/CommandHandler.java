package org.freemoney.socket;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.freemoney.service.DataPumpService;
import org.freemoney.socket.entity.SocketMessage;
import org.freemoney.vo.FundCompanyStockListVO;
import org.freemoney.vo.FundStockListVO;
import org.freemoney.vo.StockListVO;

public class CommandHandler {
	protected final Log log = LogFactory.getLog(getClass());
	
	public SocketMessage handleCommand(SocketMessage cmd){
		log.info("received cmd = " + cmd.getCmdNo());
		DataPumpService cdService = new DataPumpService();
		Map<String, Object> cmdBody = cmd.getCmdBody();
		switch (cmd.getCmdNo()) {
		case SocketMessage.STOCKADDREDUCE://股票增减仓明细查询命令
			int beginQuater = (int) cmdBody.get(SocketMessage.KEY_BEGINQUARTER);
			int endQuater = (int) cmdBody.get(SocketMessage.KEY_ENDQUARTER);
			List<StockListVO> stockList = cdService.createStockAddReduceTable(beginQuater, endQuater, null);
			cmdBody.clear();
			cmdBody.put(SocketMessage.KEY_RESULT, stockList);
			return cmd;
		case SocketMessage.FCSTOCKADDREDUCE://基金公司股票增减仓明细查询命令
			int fbeginQuater = (int) cmdBody.get(SocketMessage.KEY_BEGINQUARTER);
			int fendQuater = (int) cmdBody.get(SocketMessage.KEY_ENDQUARTER);
			List<FundCompanyStockListVO> fcStockList = cdService.createFcStockAddReduceTable(fbeginQuater, fendQuater, null);
			cmdBody.clear();
			cmdBody.put(SocketMessage.KEY_RESULT, fcStockList);
			return cmd;
		case SocketMessage.QUERYSTOCKQUARTER://按季查询某只股票基金持有情况 
			int quarter =(int) cmdBody.get(SocketMessage.KEY_QUERYQUARTER);
			String stockId = (String) cmdBody.get(SocketMessage.KEY_STOCKID);
			List<FundStockListVO> fundStockList = cdService.getFundListByStock(quarter, stockId);
			cmdBody.clear();
			cmdBody.put(SocketMessage.KEY_RESULT, fundStockList);
			return cmd;

		default:
			log.info("CommandHandler invalid cmd= " + cmd.getCmdNo());
			return cmd;
		}
	}
}
