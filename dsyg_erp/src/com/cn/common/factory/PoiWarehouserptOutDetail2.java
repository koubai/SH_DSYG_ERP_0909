package com.cn.common.factory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouserptDto;

// 送货单2023新款
public class PoiWarehouserptOutDetail2 extends Poi2007Base {
	
	private static final Logger log = LogManager.getLogger(Poi2007Base.class);
	
	/**
	 * 创建Excel2007
	 * @throws InvalidFormatException 
	 */
	public void createExcel(OutputStream out) {
		//从模板复制输出文件
		try {
			String filePath;
			filePath = PoiSalesPrice.class.getClassLoader().getResource("").toURI().getPath();
			path = filePath;	        
			filePath = filePath.replace("WEB-INF/classes/", "");
	        File source_file = new File(filePath + "page/warehouserpt_out_detail_noprice_template.xlsx" );
	        if (source_file.exists()){
	        	log.info("template fileName=[" + source_file.getPath() + "] .");	        	
//	        	File dest_file = new File(filePath + name);
	        	try {
//	    	        if (!dest_file.exists()){	        		
//	    	        	FileUtils.copyDirectory(source_file, dest_file);
//	    	        	log.info("dest Excel fileName=[" + dest_file + "] created.");
	    	        	
    	        	FileInputStream inputStream = new FileInputStream(source_file);
    	        	XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);
    	            XSSFSheet sheet = workbook.getSheet("form");

        			//输出title
        			//writeTitle(sheet, workbook);
        			//输出Head部分
        			//writeHead(sheet, workbook);
        			//输出数据部分
    	            writeData(sheet, workbook);
    	            
    	            inputStream.close();

    	            out.flush();
        			workbook.write(out);
        			out.close();
    				log.info("createExcel fileName=[" + filePath + name + "] success.");
	        	} catch (IOException e) {
	        	    e.printStackTrace();
	        	} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	        }
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 输出数据部分
	 * @param sheet
	 * @throws ParseException 
	 */
	@Override
	public void writeData(XSSFSheet sheet, XSSFWorkbook workbook){
		XSSFRow row = null;
		
		WarehouserptDto warehouserpt = new WarehouserptDto();
		//式样
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFCellStyle style1 = workbook.createCellStyle();
		XSSFCellStyle style2 = workbook.createCellStyle();
		XSSFCellStyle style3 = workbook.createCellStyle();
		XSSFCellStyle style4 = workbook.createCellStyle();
		XSSFCellStyle style5 = workbook.createCellStyle();
		XSSFCellStyle style6 = workbook.createCellStyle();
		//水平居左
		style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		//添加边框 全
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);

		XSSFFont font9 = workbook.createFont();
		XSSFFont font12 = workbook.createFont();
		//字体大小
		font9.setFontHeightInPoints((short)9);
		font12.setFontHeightInPoints((short)12);
		//式样
		//水平居中
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setFont(font12);
		
		//添加边框 (无左)
		style2 = (XSSFCellStyle) style.clone();
		style2.setBorderLeft(XSSFCellStyle.BORDER_NONE);
		style2.setFont(font9);
		//添加边框 (无左右)
		style3 = (XSSFCellStyle) style.clone();
		style3.setBorderLeft(XSSFCellStyle.BORDER_NONE);
		style3.setBorderRight(XSSFCellStyle.BORDER_NONE);
		style3.setFont(font9);
		//添加边框 (无右)
		style4 = (XSSFCellStyle) style.clone();
		style4.setBorderRight(XSSFCellStyle.BORDER_NONE);
		style4.setFont(font9);
		//添加边框 (下线)
		style5 = (XSSFCellStyle) style.clone();
		style5.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style5.setBorderLeft(XSSFCellStyle.BORDER_NONE);
		style5.setBorderTop(XSSFCellStyle.BORDER_NONE);
		style5.setBorderRight(XSSFCellStyle.BORDER_NONE);
		//添加边框 全 靠右
		style6 =(XSSFCellStyle) style.clone();
		style6.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

		//添加数据
		int num = 0;		
		BigDecimal count = new BigDecimal(0);
		BigDecimal amountCount = new BigDecimal(0);

		
		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			//对货物数据解析
			row = sheet.getRow(3);
			XSSFCell cell3 = row.getCell(3);
			XSSFCell cell8 = row.getCell(8);
			
			//客户名称
			cell3.setCellValue(warehouserpt.getSuppliername());
//			cell3.setCellStyle(style);
			//送货单位
			cell8.setCellValue("东升盈港");
//			cell8.setCellStyle(style);
			
			row = sheet.getRow(4);
			cell3 = row.getCell(3);
			cell8 = row.getCell(8);
			XSSFCell cell10 = row.getCell(10);
			
			//送货地址
			cell3.setCellValue(warehouserpt.getSupplieraddress());
//			cell3.setCellStyle(style);
			//电话
			cell8.setCellValue(warehouserpt.getSuppliertel());
//			cell8.setCellStyle(style);
			//FAX
//			cell10.setCellValue(warehouserpt.getSupplierfax());
//			cell10.setCellStyle(style);
			
			row = sheet.getRow(5);
			cell8 = row.getCell(8);

			//送货日期
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			Date wd = new Date();
			String str_wd = "";
			try {
				if (warehouserpt.getWarehousedate()!= null)
					wd = sdf.parse(warehouserpt.getWarehousedate());
					str_wd = sdf.format(wd);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (wd != null)
				cell8.setCellValue(str_wd);
//			cell8.setCellStyle(style);
			
			row = sheet.getRow(6);
			cell3 = row.getCell(3);
			cell8 = row.getCell(8);
			
			//联系人
			cell3.setCellValue(warehouserpt.getSuppliermanager());
//			cell3.setCellStyle(style);
			//送货单号
			cell8.setCellValue(warehouserpt.getWarehouseno());
//			cell8.setCellStyle(style);
			
			row = sheet.getRow(7);
			cell3 = row.getCell(3);
			cell8 = row.getCell(8);
			
			//联系人电话
			cell3.setCellValue(warehouserpt.getSuppliertel());
//			cell3.setCellStyle(style);
			//QR
			//cell8.setCellValue(warehouserpt.getWarehouseno());
			//cell8.setCellStyle(style);
			// for matrix code  -->start
			if (!getImagepath().equals("")){
				File imagefile = new File(getImagepath());
				if (imagefile.exists()) {
					ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
				    BufferedImage bufferImg;
					try {
						bufferImg = ImageIO.read(imagefile);
					    ImageIO.write(bufferImg, "jpeg", byteArrayOut);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    XSSFDrawing patriarch = sheet.createDrawingPatriarch();
//				    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 150, 100, 210, (short) 0, 0, (short) 1, 1);
//				    patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
//				    XSSFClientAnchor anchor =
//				    	new XSSFClientAnchor(0, 0, 0, 0,(short) 8, 0,(short)9, 4);
				    XSSFClientAnchor anchor =
				    	new XSSFClientAnchor(5, 5, 5, 5,(short) 8, 7,(short)11, 9);
				    patriarch.createPicture(anchor ,workbook.addPicture(byteArrayOut.toByteArray(),
				    	HSSFWorkbook.PICTURE_TYPE_JPEG));
				}
			}				

			row = sheet.getRow(8);
			cell3 = row.getCell(3);
			cell8 = row.getCell(8);
			
			//邮政编码   不明
			cell3.setCellValue("");
//			cell3.setCellStyle(style);
			
			row = sheet.getRow(9);
			cell3 = row.getCell(3);
			cell8 = row.getCell(8);
			//发运方式
			cell3.setCellValue(warehouserpt.getExpressname());
//			cell3.setCellStyle(style);
			//业务员
//			cell8.setCellValue(warehouserpt.getExpressmanager());
//			cell8.setCellStyle(style);
			
			row = sheet.getRow(10);
			cell3 = row.getCell(3);
			cell8 = row.getCell(8);
			
			//快递单号
			cell8.setCellValue(warehouserpt.getExpressno());
//			cell8.setCellStyle(style);
			
		}		
		
		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			if(warehouserpt.getListProduct() != null && warehouserpt.getListProduct().size() > 0) {
				//对货物数据解析
				for(int j = 0; j < warehouserpt.getListProduct().size(); j++) {
					ProductDto product = warehouserpt.getListProduct().get(j);
					row = sheet.createRow(num + 14);
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
//					XSSFCell cell12 = row.createCell(12);
//					XSSFCell cell13 = row.createCell(13);
//					XSSFCell cell14 = row.createCell(14);
					
					//编号
					cell0.setCellValue(num + 1);
					cell0.setCellStyle(style);
					//仓库编号
					if (warehouserpt.getWarehouseno().length()>12)
						cell1.setCellValue(warehouserpt.getWarehouseno().substring(12));
					else 
						cell1.setCellValue("SH");
					cell1.setCellStyle(style);
					//订单号
//					cell2.setCellValue(warehouserpt.getWarehouseno());
					cell2.setCellValue(product.getParentid());
					cell2.setCellStyle(style4);

//					cell3.setCellValue("");
					cell3.setCellStyle(style2);
					
					//产品名称 品名					
					cell4.setCellValue(product.getTradename());
					cell4.setCellStyle(style4);
					
//					cell5.setCellValue("");
					cell5.setCellStyle(style3);
//					cell6.setCellValue("");
					cell6.setCellStyle(style2);
					
					//规格
					cell7.setCellValue(product.getTypeno() + " (" + dictMap.get(Constants.DICT_COLOR_TYPE + "_" + product.getColor()) + ") ");
					cell7.setCellStyle(style4);
//					cell8.setCellValue("");
					cell8.setCellStyle(style2);

					//数量
					BigDecimal n = null;
					BigDecimal price = null;
					BigDecimal amount = null;
					
					if(product.getNum() != null && !"".equals(product.getNum())) {
						n = new BigDecimal(product.getNum());
						cell9.setCellValue(StringUtil.BigDecimal2StrAbs(n, 2));
						count = count.add(n);
					} else {
						cell9.setCellValue("");
					}
					cell9.setCellStyle(style6);
					//单位
					cell10.setCellValue(dictMap.get(Constants.DICT_UNIT_TYPE + "_" + product.getUnit()));
					cell10.setCellStyle(style);
					//件数
					cell11.setCellValue("1");
					cell11.setCellStyle(style6);

					//含税单价cell8
//					cell12.setCellStyle(style);
/*					
					if(StringUtil.isNotBlank(product.getWarehousetaxprice())) {
						cell12.setCellValue(product.getWarehousetaxprice());
						//金额=数量*含税单价
						if(product.getNum() != null && !"".equals(product.getNum())) {
							price = new BigDecimal(product.getWarehousetaxprice());
//							amount = price.multiply(n);
							amount = new BigDecimal(product.getAmount());
							cell13.setCellValue(StringUtil.BigDecimal2StrAbs(amount, 6));
							//合计金额
							amountCount = amountCount.add(amount);
						} else {
							//数量为空，所以金额也无法计算
							cell13.setCellValue("");
						}
					} else {
						cell13.setCellValue("");
						//单价为空，所以金额也无法计算
						cell14.setCellValue("");
					}					
*/					
					num++;
				}
			}
		}
		//输出合计
		int k = 0;
		for (k = num+14; k < num+17; k++) {
			row = sheet.createRow(k);
			XSSFCell cell0 = row.createCell(0);
			cell0.setCellStyle(style);
			XSSFCell cell1 = row.createCell(1);
			cell1.setCellStyle(style);
			XSSFCell cell2 = row.createCell(2);
			cell2.setCellStyle(style4);
			XSSFCell cell3 = row.createCell(3);
			cell3.setCellStyle(style2);
			XSSFCell cell4 = row.createCell(4);
			cell4.setCellStyle(style4);
			XSSFCell cell5 = row.createCell(5);
			cell5.setCellStyle(style3);
			XSSFCell cell6 = row.createCell(6);
			cell6.setCellStyle(style2);
			XSSFCell cell7 = row.createCell(7);
			cell7.setCellStyle(style4);
			XSSFCell cell8 = row.createCell(8);
			cell8.setCellStyle(style2);
			XSSFCell cell9 = row.createCell(9);
			cell9.setCellStyle(style);
			XSSFCell cell10 = row.createCell(10);
			cell10.setCellStyle(style);
			XSSFCell cell11 = row.createCell(11);
			cell11.setCellStyle(style);
//			XSSFCell cell12 = row.createCell(12);
//			cell12.setCellStyle(style);
//			XSSFCell cell13 = row.createCell(13);
//			cell13.setCellStyle(style);
			if (k == num+16) {
				cell0.setCellValue("合计");
				cell0.setCellStyle(style);
				cell9.setCellValue(StringUtil.BigDecimal2StrAbs(count, 2));
				cell9.setCellStyle(style6);
				cell11.setCellValue(num);
				cell11.setCellStyle(style6);
		//		cell12.setCellValue("金额合计");
		//		cell12.setCellStyle(style);
		//		cell13.setCellValue(StringUtil.BigDecimal2StrAbs(amountCount, 6));
		//		cell13.setCellStyle(style);
			}		
			row = sheet.createRow(num + 18);
			cell8 = row.createCell(8);
			cell8.setCellValue("签收");
			cell8.setCellStyle(style1);			
					
			cell9 = row.createCell(9);
			cell9.setCellValue("");
			cell9.setCellStyle(style5);
			cell10 = row.createCell(10);
			cell10.setCellValue("");
			cell10.setCellStyle(style5);
			cell11 = row.createCell(11);
			cell11.setCellValue("");
			cell11.setCellStyle(style5);			
		}
	}
	
	/**
	 * 导出Excel2007
	 * @param out
	 */
	public void exportExcel(OutputStream out) {
		try {
			createExcel(out);
			log.info("exportExcel success.");
		} catch (Exception e) {
			log.error("exportExcel error:" + e);
		}
	}
}
