package org.freemoney.utils;

import java.util.Properties;

public class PropsUtil {
	private static Properties properties;
	static{
		try{
			properties=new Properties();
			properties.load(PropsUtil.class.getResourceAsStream("/freemoney.properties"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ͨ��key��ȡproperteis�е�valueֵ
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		if(key==null||properties.getProperty(key)==null)
			return null;
		else
			return properties.getProperty(key);
		
	}
}
