package org.freemoney.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class HandleFile {
	protected static final Log log=LogFactory.getLog(HandleFile.class.getName());
	
	public static void main(String[] args){
		String file="E:\\school\\北风网\\mystock\\mystock\\release\\doc\\201002\\162711.doc";
		System.out.println(handleFile(file));
	}
	
	/**
	 * 解析基金季报，返回字符串
	 * @param filename 全路径文件名
	 * @return
	 */
	public static String handleFile(String filename){
		String result="";
		String fileType=filename.substring(filename.lastIndexOf(".")+1,filename.length());
		if(fileType.equalsIgnoreCase("pdf")){
			result=handlePdf(filename);
		}else if(fileType.equalsIgnoreCase("doc")){
			result=handleDocTable(filename);
		}
		return result;
	}
	/**
	 * 解析DOC文档中的表格，返回字符串数据
	 * @param filename
	 * @return
	 */
	private static String handleDocTable(String filename) {
		String content="";
		FileInputStream instream=null;
		try{
			File file=new File(filename);
			if(!file.exists())
				return content;
			instream= new FileInputStream(filename);
			POIFSFileSystem pfs=new POIFSFileSystem(instream);
			HWPFDocument hwpf=new HWPFDocument(pfs);
			Range range=hwpf.getRange();//得到文档 的读取范围
			TableIterator it=new TableIterator(range);
			//迭代文档中的表格
			while(it.hasNext()){
				Table tb=(Table)it.next();
				//迭代行，默认从0开始
				for(int i=0;i<tb.numRows();i++){
					TableRow tr=tb.getRow(i);
					//迭代列，默认从0开始
					for(int j=0;j<tr.numCells();j++){
						TableCell td=tr.getCell(j);
						//取单元格的内容
						for(int k=0;k<td.numParagraphs();k++){
							Paragraph para=td.getParagraph(k);
							String s=para.text();
							if(s.isEmpty())
								continue;
							content+=s.trim()+"";
						}
					}
					content+="\n";
				}
			}
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}finally{
			if(instream!=null)
				try{
					instream.close();
				}catch(IOException e){
					e.printStackTrace();
				}
		}
		return content;
	}

	/**
	 * 解析pdf文件文本数据
	 * @param filename
	 * @return
	 */
	private static String handlePdf(String filename) {
		StringBuffer content=new StringBuffer("");
		FileInputStream instream=null;
		PDDocument pdfDocument=null;
		try{
			File file=new File(filename);
			if(!file.exists())
				return content.toString();
			instream=new FileInputStream(file);
			PDFTextStripper stripper=new PDFTextStripper();
			pdfDocument=PDDocument.load(instream);
			StringWriter writer=new StringWriter();
			stripper.writeText(pdfDocument,writer);
			content.append(writer.getBuffer().toString());
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
		}finally{
			if(instream!=null)
				try{
					instream.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			if(pdfDocument!=null){
				COSDocument cos=pdfDocument.getDocument();
				try{
					cos.close();
					pdfDocument.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}
}
