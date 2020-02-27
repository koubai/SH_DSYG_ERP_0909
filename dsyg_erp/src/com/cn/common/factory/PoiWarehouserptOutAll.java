package com.cn.common.factory;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouserptDto;

public class PoiWarehouserptOutAll extends Poi2007Base {
	
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
				
		XSSFRow row = sheet.createRow(2);
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
		XSSFCell cell = row.createCell(4);
		cell.setCellValue("出库清单");
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
		WarehouserptDto warehouserpt = new WarehouserptDto();
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//添加边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		//添加数据
		int num = 0;
		//合计数量
		BigDecimal count = new BigDecimal(0);
		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			if(warehouserpt.getListProduct() != null && warehouserpt.getListProduct().size() > 0) {
				//对货物数据解析
				for(int j = 0; j < warehouserpt.getListProduct().size(); j++) {
					ProductDto product = warehouserpt.getListProduct().get(j);
					row = sheet.createRow(num + 5);
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
					//编号
					cell0.setCellValue(num + 1);
					cell0.setCellStyle(style);
					//出库单号
					cell1.setCellValue(warehouserpt.getWarehouseno());
					cell1.setCellStyle(style);
					//客户名称
					cell2.setCellValue(warehouserpt.getSuppliername());
					cell2.setCellStyle(style);
					//品名
					cell3.setCellValue(product.getTradename());
					cell3.setCellStyle(style);
					//住友编码
					cell4.setCellValue(product.getItem11());
					cell4.setCellStyle(style);
					//规格
					cell5.setCellValue(product.getTypeno());
					cell5.setCellStyle(style);
					//颜色
					cell6.setCellStyle(style);
					cell6.setCellValue(dictMap.get(Constants.DICT_COLOR_TYPE + "_" + product.getColor()));
					//包装
					cell7.setCellStyle(style);
					cell7.setCellValue(product.getItem10());
					//数量
					cell8.setCellStyle(style);
					if(product.getNum() != null && !"".equals(product.getNum())) {
						BigDecimal d = new BigDecimal(product.getNum());
						cell8.setCellValue(StringUtil.BigDecimal2StrAbs(d, 2));
						count = count.add(d);
					} else {
						cell8.setCellValue("");
					}
					//产地
					cell9.setCellStyle(style);
					cell9.setCellValue(dictMap.get(Constants.DICT_MAKEAREA + "_" + product.getMakearea()));
					//发货日期
					cell10.setCellStyle(style);
					cell10.setCellValue(StringUtil.getStr(warehouserpt.getShowWarehousedate()));
					num++;
				}
			}
		}
		//输出合计
		row = sheet.createRow(num + 6);
		XSSFCell cell7 = row.createCell(7);
		XSSFCell cell8 = row.createCell(8);
		cell7.setCellValue("合计");
		cell7.setCellStyle(style);
		cell8.setCellValue(StringUtil.BigDecimal2StrAbs(count, 2));
		cell8.setCellStyle(style);
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
		heads.add("出库单号");
		sheet.setColumnWidth(1, 20 * 256);
		heads.add("客户");
		sheet.setColumnWidth(2, 30 * 256);
		heads.add("品名");
		sheet.setColumnWidth(3, 25 * 256);
		heads.add("住友编码");
		sheet.setColumnWidth(4, 20 * 256);
		heads.add("规格");
		sheet.setColumnWidth(5, 20 * 256);
		heads.add("颜色");
		sheet.setColumnWidth(6, 15 * 256);
		heads.add("包装");
		sheet.setColumnWidth(7, 20 * 256);
		heads.add("数量");
		sheet.setColumnWidth(8, 15 * 256);
		heads.add("产地");
		sheet.setColumnWidth(9, 15 * 256);
		heads.add("发货日期");
		sheet.setColumnWidth(10, 20 * 256);
		
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
		XSSFRow row = sheet.createRow(4);
		
		XSSFCell cell = null;
		for(int i = 0; i < heads.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(heads.get(i));
			cell.setCellStyle(style);
		}
	}
}
