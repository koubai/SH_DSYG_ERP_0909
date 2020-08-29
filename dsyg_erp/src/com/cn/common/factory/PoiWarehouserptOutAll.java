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
import com.cn.common.util.DateUtil;
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
		XSSFFont font1 = workbook.createFont();
		//加粗
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//字体大小
		font1.setFontHeightInPoints((short)20);
				
		XSSFRow row = sheet.createRow(0);
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 7));
		XSSFCell cell = row.createCell(4);
		cell.setCellValue("上海东升盈港对账明细");
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font1);
		cell.setCellStyle(style);
		
		writeTitle_sub(sheet, workbook);
	}
				
	public void writeTitle_sub(XSSFSheet sheet, XSSFWorkbook workbook) {		
		XSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)12);
		XSSFRow row = sheet.createRow(1);
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 7));
		XSSFCell cell = row.createCell(4);
		cell.setCellValue("地址：上海市杨浦区控江路760号                                       联系电话：021-65388038");
		XSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		cell.setCellStyle(style);	
	}
	
	/**
	 * 输出大标题 normal
	 * @param sheet
	 */
	@SuppressWarnings("deprecation")
	public void writeTitle2(XSSFSheet sheet, XSSFWorkbook workbook) {
		//Head部分颜色字体
		XSSFFont font = workbook.createFont();
		//加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//字体大小
// 20200829 用户要求修改 title Pei		
//		font.setFontHeightInPoints((short)18);
		font.setFontHeightInPoints((short)15);
				
		XSSFRow row = sheet.createRow(2);
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 7));
		XSSFCell cell = row.createCell(4);
// 20200829 用户要求修改 title Pei		
//		cell.setCellValue("东升盈港发货明细");
		cell.setCellValue("");
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		cell.setCellStyle(style);
	}
	
	/**
	 * 输出大标题 special
	 * @param sheet
	 */
	@SuppressWarnings("deprecation")
	public void writeTitle3(XSSFSheet sheet, XSSFWorkbook workbook, String title) {
		//Head部分颜色字体
		XSSFFont font = workbook.createFont();
		//加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//字体大小
		// 20200829 用户要求修改 title Pei		
//		font.setFontHeightInPoints((short)18);
		font.setFontHeightInPoints((short)15);
				
		XSSFRow row = sheet.createRow(2);
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 4, 7));
		XSSFCell cell = row.createCell(4);
// 20200829 用户要求修改 title Pei		
//		cell.setCellValue(title + "发货明细");
		cell.setCellValue(title + "");
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

		//Head部分颜色字体
		XSSFFont font = workbook.createFont();
		//字体大小
		font.setFontHeightInPoints((short)14);
		
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


		//添加数据
		int num = 0;
		//合计数量
		BigDecimal count = new BigDecimal(0);
		//金额合计
		BigDecimal amountCount = new BigDecimal(0);
		//客户名称
		String orgSuppliername = null;
		boolean Suppliernameflg = true;
		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			if(warehouserpt.getListProduct() != null && warehouserpt.getListProduct().size() > 0) {
				//对货物数据解析
//				String[] theme2lst = null;
//				if (warehouserpt.getRes06() != null)
//					theme2lst = warehouserpt.getRes06().split(",");
				//对特殊订单号解析
//				String[] res09lst = null;
//				if (warehouserpt.getRes07() != null)
//					res09lst = warehouserpt.getRes07().split(",");
				
				if (orgSuppliername != null && !orgSuppliername.equals(warehouserpt.getSuppliername()))
					Suppliernameflg = false;
				else
					orgSuppliername = warehouserpt.getSuppliername();
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
					
// as user requirement change 客户名称 to 订单号        20200523 pei 
//					//客户名称
//					cell2.setCellValue(warehouserpt.getSuppliername());
//					cell2.setCellStyle(style);
					// 订单号
					cell2.setCellValue(product.getRes10());
					cell2.setCellStyle(style);
					
					//品名
					cell3.setCellValue(product.getTradename());
					cell3.setCellStyle(style);
					//规格
					cell4.setCellValue(product.getTypeno());
					cell4.setCellStyle(style);
					//颜色
					cell5.setCellStyle(style);
					cell5.setCellValue(dictMap.get(Constants.DICT_COLOR_TYPE + "_" + product.getColor()));

// as user requirement change 包装 to 特殊订单号        20200523 pei 
//					//包装
//					cell6.setCellStyle(style);
//					cell6.setCellValue(product.getItem10());
					cell6.setCellStyle(style);
					cell6.setCellValue(product.getRes09());
/*					if (res09lst[j] == null || res09lst[j].equals(""))
						cell6.setCellValue("");
					else
						cell6.setCellValue(res09lst[j]);
*/					
					//数量
					cell7.setCellStyle(style);
					BigDecimal n = null;
					BigDecimal amount = null;
					
					if(product.getNum() != null && !"".equals(product.getNum())) {
						//这里需要乘-1 upd by frank，为了保证出库清单数量在退货时显示为负
						n = new BigDecimal(product.getNum()).multiply(new BigDecimal(-1));
						cell7.setCellValue(StringUtil.BigDecimal2Str(n, 2));
						count = count.add(n);
					} else {
						cell7.setCellValue("");
					}
					//含税单价cell8
					cell8.setCellStyle(style);
					cell9.setCellStyle(style);
					if(StringUtil.isNotBlank(product.getWarehousetaxprice())) {
						cell8.setCellValue(product.getWarehousetaxprice());
						//金额=数量*含税单价
						if(product.getNum() != null && !"".equals(product.getNum())) {
//							price = new BigDecimal(product.getWarehousetaxprice());
//							amount = price.multiply(n);
							amount = new BigDecimal(product.getAmount());
							cell9.setCellValue(StringUtil.BigDecimal2Str(amount, 2));
							//合计金额
							amountCount = amountCount.add(amount);
						} else {
							//数量为空，所以金额也无法计算
							cell9.setCellValue("");
						}
					} else {
						cell8.setCellValue("");
						//单价为空，所以金额也无法计算
						cell9.setCellValue("");
					}
					
					//发货日期
					cell10.setCellStyle(style);
					//cell10.setCellValue(StringUtil.getStr(warehouserpt.getShowWarehousedate()));
					cell10.setCellValue(DateUtil.dateToShortStr(warehouserpt.getCreatedate()));
					num++;
				}
			}
		}
		//输出合计
		row = sheet.createRow(num + 6);
		XSSFCell cell6 = row.createCell(6);
		XSSFCell cell7 = row.createCell(7);
		XSSFCell cell8 = row.createCell(8);
		XSSFCell cell9 = row.createCell(9);
		cell6.setCellValue("数量合计");
		cell6.setCellStyle(style);
		cell7.setCellValue(StringUtil.BigDecimal2StrAbs(count, 2));
		cell7.setCellStyle(style);
		cell8.setCellValue("金额合计");
		cell8.setCellStyle(style);
		cell9.setCellValue(StringUtil.BigDecimal2StrAbs(amountCount, 2));
		cell9.setCellStyle(style);
		
		//客户名没有变化的话就输出一个，如果有变化就输出原始
		if (Suppliernameflg == false)
			writeTitle2(sheet, workbook);
		else
			writeTitle3(sheet, workbook, orgSuppliername);
	}
	
	/**
	 * 输出Head部分
	 * @param sheet
	 */
	@Override
	public void writeHead(XSSFSheet sheet, XSSFWorkbook workbook) {
		heads = new ArrayList<String>();
		heads.add("编号");
//		sheet.setColumnWidth(0, 6 * 256);
		sheet.setColumnWidth(0, 7 * 256);
		heads.add("出库单号");
//		sheet.setColumnWidth(1, 16 * 256);
		sheet.setColumnWidth(1, 18 * 256);
//		heads.add("客户");
		heads.add("订单号");
//		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(2, 28 * 256);
		heads.add("品名");
//		sheet.setColumnWidth(3, 25 * 256);
		sheet.setColumnWidth(3, 22 * 256);
		heads.add("规格");
		sheet.setColumnWidth(4, 35 * 256);
		heads.add("颜色");
		sheet.setColumnWidth(5, 12 * 256);
//		heads.add("包装");
		heads.add("备注");
//		sheet.setColumnWidth(6, 20 * 256);
		sheet.setColumnWidth(6, 18 * 256);
		heads.add("数量");
		sheet.setColumnWidth(7, 15 * 256);
		heads.add("含税单价");
//		sheet.setColumnWidth(8, 15 * 256);
		sheet.setColumnWidth(8, 16 * 256);
		heads.add("金额");
		sheet.setColumnWidth(9, 15 * 256);
		//heads.add("发货日期");
		heads.add("创建日期");
//		sheet.setColumnWidth(10, 16 * 256);
		sheet.setColumnWidth(10, 16 * 256);
		
		//Head部分颜色字体
		XSSFFont font = workbook.createFont();
		//加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//字体大小
	//	font.setFontHeightInPoints((short)12);
		font.setFontHeightInPoints((short)14);
		
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
