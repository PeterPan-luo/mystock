package org.freemoney.socket.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SocketMessage implements Serializable{


	private static final long serialVersionUID = 1L;
	private int cmdNo;//命令号
	private Map<String, Object> cmdBody = new HashMap<String, Object>();//消息体
	
	public static final int STOCKADDREDUCE = 1001;////股票增减仓明细查询命令
	public static final int FCSTOCKADDREDUCE=1002;//基金公司股票增减仓明细查询命令
	public static final int QUERYSTOCKQUARTER=1003;//按季查询某只股票基金持有情况 
	
	public static final String KEY_BEGINQUARTER="BEGINQUARTER";//起始季度键值
	public static final String KEY_ENDQUARTER="ENDQUARTER";		//结束季度键值
	public static final String KEY_QUERYQUARTER="QUERYQUARTER";	//查询季度键值
	public static final String KEY_STOCKID="STOCKID";			//股票代码键值
	public static final String KEY_RESULT="RESULT";				//查询结果键值
	
	public int getCmdNo() {
		return cmdNo;
	}
	public void setCmdNo(int cmdNo) {
		this.cmdNo = cmdNo;
	}
	public Map<String, Object> getCmdBody() {
		return cmdBody;
	}
	public void setCmdBody(Map<String, Object> cmdBody) {
		this.cmdBody = cmdBody;
	}
	
	
}
