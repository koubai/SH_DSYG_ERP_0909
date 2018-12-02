package com.cn.common.factory;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cn.common.util.Constants;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.PurchaseDto;
import com.cn.dsyg.dto.WarehouseCheckDto;

public class PoiWarehouseCheck extends Poi2007Base {

	private static final Logger log = LogManager.getLogger(Poi2007Base.class);

	/**
	 * 输出大标题
	 * @param sheet
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void writeTitle(XSSFSheet sheet, XSSFWorkbook workbook) {
		//Head部分颜色字体
		XSSFFont font = workbook.createFont();
		//加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//字体大小
		font.setFontHeightInPoints((short)18);
				
		XSSFRow row = sheet.createRow(1);
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("盘点一览");
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		cell.setCellStyle(style);
	}
	
	/**
	 * 输出数据部分
	 * @param sheet
	 */
	@Override
	public void writeData(XSSFSheet sheet, XSSFWorkbook workbook) {
		XSSFRow row = null;
		WarehouseCheckDto warehouseCheck = new WarehouseCheckDto();
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFCellStyle unlockstyle = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//添加边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		//水平居中
		unlockstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//添加边框
		unlockstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		unlockstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		unlockstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		unlockstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		unlockstyle.setLocked(false);

		//添加数据
		for(int i = 0; i < datas.size(); i++) {
			row = sheet.createRow(i + 3);
			warehouseCheck = (WarehouseCheckDto) datas.get(i);
			XSSFCell cell0 = row.createCell(0);
			XSSFCell cell1 = row.createCell(1);
			XSSFCell cell2 = row.createCell(2);
			XSSFCell cell3 = row.createCell(3);
			XSSFCell cell4 = row.createCell(4);
			XSSFCell cell5 = row.createCell(5);
			XSSFCell cell6 = row.createCell(6);
			XSSFCell cell7 = row.createCell(7);
			XSSFCell cell8 = row.createCell(8);
			XSSFCell cell9 = row.createCell(9);
			XSSFCell cell10 = row.createCell(10);
			XSSFCell cell11 = row.createCell(11);
			XSSFCell cell12 = row.createCell(12);
			XSSFCell cell13 = row.createCell(13);
			XSSFCell cell14 = row.createCell(14);
			XSSFCell cell15 = row.createCell(15);
			XSSFCell cell16 = row.createCell(16);
			cell0.setCellValue(i + 1);
			cell0.setCellStyle(style);
			cell1.setCellValue(dictMap.get(Constants.DICT_GOODS_TYPE + "_" + warehouseCheck.getFieldno()));
			cell1.setCellStyle(style);
			cell2.setCellValue(warehouseCheck.getWarehousename());
			cell2.setCellStyle(style);
			cell3.setCellValue(warehouseCheck.getTradename());
			cell3.setCellStyle(style);
			cell4.setCellValue(warehouseCheck.getTypeno());
			cell4.setCellStyle(style);
			cell5.setCellValue(dictMap.get(Constants.DICT_COLOR_TYPE + "_" + warehouseCheck.getColor()));
			cell5.setCellStyle(style);
			if("0".equals(warehouseCheck.getPackaging())) {
				cell6.setCellValue("整箱");
			} else {
				cell6.setCellValue("乱尺");
			}
			cell6.setCellStyle(style);
			cell7.setCellValue(warehouseCheck.getItem10());
			cell7.setCellStyle(style);
			cell8.setCellValue(warehouseCheck.getUnit());
			cell8.setCellStyle(style);
			cell9.setCellValue(warehouseCheck.getMakearea());
			cell9.setCellStyle(style);
			cell10.setCellValue(warehouseCheck.getProductid());
			cell10.setCellStyle(style);
			cell11.setCellValue(warehouseCheck.getSuppliername());
			cell11.setCellStyle(style);
			cell12.setCellValue("" + warehouseCheck.getWarehouseamount());
			cell12.setCellStyle(style);
			//上期盘点库存详细数量及入库日期
			if (warehouseCheck.getRes02()!= null){
				String hist = genProdHist(warehouseCheck.getRes02());
				int cellHeight = sheet.getRow(i+3).getHeight(); 	
				int num = JSONArray.fromObject(warehouseCheck.getRes02()).size();
				int maxHeight =cellHeight*(num + 1);  
				cell13.setCellValue("" + hist);
				row.setHeight((short)maxHeight);
				style.setWrapText(true);    
			}else
				cell13.setCellValue("");
			cell13.setCellStyle(style);
			cell14.setCellValue("");
			cell14.setCellStyle(unlockstyle);
			cell15.setCellValue("");
			cell15.setCellStyle(unlockstyle);
			if (!compareAmount(warehouseCheck.getWarehouseamount(),warehouseCheck.getRes05()))
				cell16.setCellValue(warehouseCheck.getRes05());
			else
				cell16.setCellValue("");
			cell16.setCellStyle(unlockstyle);
		}
	}
	private boolean compareAmount(BigDecimal amt1, String str2){
		BigDecimal amt2 = new BigDecimal(0);
		if (StringUtil.isNotBlank(str2))
			amt2  = new BigDecimal(str2);
		if (amt1.compareTo(amt2) == 0)
			return true;
		return false;
	}
	
	
	/**
	 * 输出Head部分
	 * @param sheet
	 */
	@Override
	public void writeHead(XSSFSheet sheet, XSSFWorkbook workbook) {
		heads = new ArrayList<String>();
		heads.add("编号");
		sheet.setColumnWidth(0, 10 * 256);
		heads.add("主题");
		sheet.setColumnWidth(1, 15 * 256);
		heads.add("仓库");
		sheet.setColumnWidth(2, 10 * 256);
		heads.add("品名");
		sheet.setColumnWidth(3, 20 * 256);
		heads.add("规格");
		sheet.setColumnWidth(4, 50 * 256);
		heads.add("颜色");
		sheet.setColumnWidth(5, 10 * 256);
		heads.add("形式");
		sheet.setColumnWidth(6, 10 * 256);
		heads.add("包装");
		sheet.setColumnWidth(7, 30 * 256);
		heads.add("单位");
		sheet.setColumnWidth(8, 0 * 256);
		heads.add("产地");
		sheet.setColumnWidth(9, 0 * 256);
		heads.add("产品号");
		sheet.setColumnWidth(10, 0 * 256);
		heads.add("供应商");
		sheet.setColumnWidth(11, 30 * 256);
		heads.add("预测库存数量");
		sheet.setColumnWidth(12, 15 * 256);
		heads.add("上期详细信息");
		sheet.setColumnWidth(13, 15 * 256);
		heads.add("库存数量");
		sheet.setColumnWidth(14, 15 * 256);
		heads.add("详细信息");
		sheet.setColumnWidth(15, 15 * 256);
		heads.add("对照不符");
		sheet.setColumnWidth(16, 15 * 256);
		
		//Head部分颜色字体
		XSSFFont font = workbook.createFont();
		//加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//字体大小
		font.setFontHeightInPoints((short)12);
		
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//添加边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);
		//背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(new XSSFColor(new java.awt.Color(180, 180, 180)));
		XSSFRow row = sheet.createRow(2);
		
		XSSFCell cell = null;
		for(int i = 0; i < heads.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(heads.get(i));
			cell.setCellStyle(style);
		}
	}
	
	
	public String genProdHist(String in_String){
		if ((in_String == null) || (in_String.trim().equals("")))
			return null;
		
		JSONArray mapArray = JSONArray.fromObject(in_String);
		String rtn = "";
		for (int m = 0 ; m <mapArray.size(); m++){
			JSONObject json = (JSONObject)mapArray.get(m);
			rtn += json.get("in_wdate").toString()+",";
			rtn += json.get("in_wquantity").toString()+";\n";	    		
		}			
		return rtn;
	}

	/**
	 * 导出Excel2007
	 * @param out
	 */
	/*
	public void exportExcel(OutputStream out) {
		try {
			//创建Workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			//创建Sheet
			XSSFSheet sheet = createSheet(workbook);
			//输出title
			writeTitle(sheet, workbook);
			//输出Head部分
			writeHead(sheet, workbook);
			//输出数据部分
			writeData(sheet, workbook);
						
			//输出Excel
			out.flush();
			workbook.write(out);
			out.close();
			log.info("exportExcel success.");
		} catch (Exception e) {
			log.error("exportExcel error:" + e);
		}
	}
	*/

}
