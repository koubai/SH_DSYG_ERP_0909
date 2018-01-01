package com.cn.common.factory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * MatrixImage操作类基类
 * @author Pei
 * @time 2017-12-31
 * @version 1.0
 */
public class MatrixImageBase {
	
	private static final Logger log = LogManager.getLogger(MatrixImageBase.class);
	
	/**
	 * 字典表记录
	 */
	protected Map<String, String> dictMap;

	/**
	 * 文件路径
	 */
	protected String path;
	
	/**
	 * 文件名
	 */
	protected String name;
		
	/**
	 * 数据标题
	 */
	protected String title;
	
	/**
	 * 数据头部
	 */
	protected List<String> heads;
	
	/**
	 * 数据列表
	 */
	protected List<?> datas;
	
	
	/**
	 * 数据信息
	 */
	protected String content;
	
	/**
	 * 创建MatrixImage
	 */
	public void createMatrixImage() {
        try {  
        	writeTitle();
        	writeHead();
        	writeData();
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
  
            Map hints = new HashMap();  
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);  
            File file1 = new File(path, name);  
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);  
        } catch (WriterException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	/**
	 * 输出大标题
	 * @param 
	 */
	public void writeTitle() {
	}
	
	/**
	 * 输出Head部分
	 * @param 
	 */
	public void writeHead() {
	}
	
	/**
	 * 输出数据部分
	 * @param 
	 */
	public void writeData() {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getHeads() {
		return heads;
	}

	public void setHeads(List<String> heads) {
		this.heads = heads;
	}

	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	public Map<String, String> getDictMap() {
		return dictMap;
	}

	public void setDictMap(Map<String, String> dictMap) {
		this.dictMap = dictMap;
	}
}
