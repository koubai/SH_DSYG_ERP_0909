package com.cn.common.factory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

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
	protected static String content;
	
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
	 * 创建条形码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public void createEncodeBar() {
		// 条形码的最小宽度
//		int codeWidth = 98;
		try {
            Map hints = new HashMap();  
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.CODE_39, 800, 20, hints);

            File file1 = new File(path, name);  
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);  

		} catch (Exception e) {
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
