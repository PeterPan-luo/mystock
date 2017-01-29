package org.freemoney.utils;

import java.util.Properties;

public class Constant {
	public static String DOCPATH="doc";
	public static String USER_HOMEDIR=new Properties(System.getProperties()).getProperty("user.dir");
	public static String STOCKDETAILBYFUND="基金持仓明细表";
	public static String STOCKHEAVYBYFUNDCOMPANY="基金公司持仓明细表";
	public static String STOCKADDBYFUND="股票增减仓明细表";
	public static String XLS="xls";
	public static String FUNDCOMPANYSTOCKADDBYFUND="基金公司增减仓明细表";
	public static int PORT=9898;
	public static int corePoolSize=50;
	public static int maxPoolSize=200;
	
	static{
		try{
			corePoolSize=Integer.parseInt(PropsUtil.getString("corePoolSize"));
			maxPoolSize=Integer.parseInt(PropsUtil.getString("maxPoolSize"));
			PORT=Integer.parseInt(PropsUtil.getString("port"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
