package org.freemoney.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {

	public static boolean copyFile(java.io.File filefrom, java.io.File fileto,
			boolean rewrite) {
		if (!filefrom.exists()) {
			System.out.println("�ļ�������");
			return false;
		}
		if (!filefrom.isFile()) {
			System.out.println("���ܹ������ļ���");
			return false;
		}
		if (!filefrom.canRead()) {
			System.out.println("���ܹ���ȡ��Ҫ���Ƶ��ļ�");
			return false;
		}
		if (!fileto.getParentFile().exists()) {
			fileto.getParentFile().mkdirs();
		}
		if (fileto.exists() && rewrite) {
			fileto.delete();
		}
		try {
			java.io.FileInputStream fosfrom = new java.io.FileInputStream(
					filefrom);
			java.io.FileOutputStream fosto = new FileOutputStream(fileto);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosfrom.close();
			fosto.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean copyFile(String from, String to) {
		java.io.File filefrom = new java.io.File(from);
		java.io.File fileto = new java.io.File(to);
		return copyFile(filefrom, fileto, true);
	}

	/**
	 * �½�Ŀ¼
	 * 
	 * @param folderPath
	 *            String �� c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("�½�Ŀ¼��������");
			e.printStackTrace();
		}
	}

	/**
	 * �½��ļ�
	 * 
	 * @param filePathAndName
	 *            String �ļ�·�������� ��c:/fqf.txt
	 * @param fileContent
	 *            String �ļ�����
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			System.out.println("�½�Ŀ¼��������");
			e.printStackTrace();

		}

	}
	
	public static void copyErrorFile(String file){
		String path=file.substring(0, file.lastIndexOf(File.separator)+1);
		String filename=file.substring(file.lastIndexOf(File.separator)+1,file.length());
		String folder=path+"error";
		String newFileName=folder+File.separator+filename;
		FileUtil.newFolder(folder);
		FileUtil.copyFile(file, newFileName);
	}
}
