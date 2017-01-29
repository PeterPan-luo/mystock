/*
 * Created on 2010-10-10 ÉÏÎç12:54:10
 * @author hejing
 */
package org.freemoney.utils;

public class Utils {
	public static String formatNumber(String src) {
		String[] split = src.split(",");
		String target = "";
		for (String s : split)
			target += s;
		return target.trim();
	}

	public static boolean isDoc(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (fileType.equalsIgnoreCase("doc"))
			return true;
		return false;
	}

	public static boolean isPdf(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (fileType.equalsIgnoreCase("pdf"))
			return true;
		return false;
	}

	public static boolean isHtml(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (fileType.equalsIgnoreCase("html")
				|| fileType.equalsIgnoreCase("htm"))
			return true;
		return false;
	}

	public static boolean isXml(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (fileType.equalsIgnoreCase("xml"))
			return true;
		return false;
	}

	public static boolean isPpt(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (fileType.equalsIgnoreCase("ppt"))
			return true;
		return false;
	}

	public static boolean isXls(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (fileType.equalsIgnoreCase("xls"))
			return true;
		return false;
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
