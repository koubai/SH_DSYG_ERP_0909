package com.cn.common.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cn.common.util.Constants;
import com.cn.common.util.PropertiesConfig;
import com.cn.dsyg.dao.Dict01Dao;
import com.cn.dsyg.dto.Dict01Dto;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.WarehouserptDto;

public class PoiAccountCGDlist extends Poi2007Base {
	
	private static final Logger log = LogManager.getLogger(Poi2007Base.class);
	
	private BigDecimal rate = new BigDecimal("0.13");
	private BigDecimal rate100 = new BigDecimal(13); 

	public BigDecimal getRate() {
		return rate;
	}


	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}


	public BigDecimal getRate100() {
		rate100 = rate.multiply(new BigDecimal(100));
		return rate100;
	}


	public void setRate100(BigDecimal rate100) {
		this.rate100 = rate100;
	}


	/**
	 * 创建Excel2007
	 */
	public void createExcel(OutputStream out) {
		try {
			//从模板复制输出文件
			String filePath = PoiSalesPrice.class.getClassLoader().getResource("").toURI().getPath();
			filePath = filePath.replace("WEB-INF/classes/", "");
			path = filePath;
	        File source_file = new File(filePath + "page/cgdlst_temple.xls");
	        
	        //ディレクトリ、ファイルの存在を確認する
	        if (source_file.exists()){
	        	log.info("template Excel fileName=[" + path + name + "] is exist.");
	        	log.info("from1 fileName=[" + source_file.getPath() + "] .");
	        		        
		        POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(source_file.getPath()));
		        HSSFWorkbook workbook = new HSSFWorkbook(filein);
		    	HSSFSheet sheet = workbook.getSheet("sample");
//		    	os = new FileOutputStream(filePath + "page/"+ name); 
				//输出title
				//writeTitle(sheet, workbook);
				//输出Head部分
				//writeHead(sheet, workbook);
				//输出数据部分
				writeData(sheet, workbook);
				
				//输出Excel
				out.flush();
				workbook.write(out);
				log.info("createExcel fileName=[" + filePath + name + "] success.");
	        }else{
	        	log.info("template Excel fileName=[" + filePath + name + "] is not exist.");
	        }
	        
		} catch (Exception e) {
			log.error("createExcel fileName=[" + path + name + "] error:" + e);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				log.error("createExcel close os error:" + e);
			}
		}
	}
	
	
	/**
	 * 输出数据部分
	 * @param sheet
	 */
	@Override
	public void writeData(HSSFSheet sheet, HSSFWorkbook workbook) {
		HSSFRow row = null;
		WarehouserptDto warehouserpt = new WarehouserptDto();
		//式样
		HSSFCellStyle style = workbook.createCellStyle();
		//水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//添加边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);

		//添加数据
		int num = 0;		
		
		//税率
		String str_rate = dictMap.get(Constants.DICT_RATE);
		if (str_rate != null && !str_rate.isEmpty()){
			rate = new BigDecimal(str_rate);
			rate100 = rate.multiply(new BigDecimal(100));
		}		
		//用友账套序号
		String str_accountno = dictMap.get(Constants.DICT_ACCOUNTNO1);

		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			//对货物数据解析
			for(int j = 0; j < warehouserpt.getListProduct().size(); j++) {
				ProductDto product = warehouserpt.getListProduct().get(j);
		
				row = sheet.createRow(num + 2);
				HSSFCell cell0 = row.createCell(0);
				HSSFCell cell1 = row.createCell(1);
				HSSFCell cell2 = row.createCell(2);
				HSSFCell cell3 = row.createCell(3);
				HSSFCell cell4 = row.createCell(4);
				HSSFCell cell5 = row.createCell(5);
				HSSFCell cell6 = row.createCell(6);
				HSSFCell cell7 = row.createCell(7);
				HSSFCell cell8 = row.createCell(8);
				HSSFCell cell9 = row.createCell(9);
				HSSFCell cell10 = row.createCell(10);
				HSSFCell cell11 = row.createCell(11);
				HSSFCell cell12 = row.createCell(12);
				HSSFCell cell13 = row.createCell(13);
				HSSFCell cell14 = row.createCell(14);
				HSSFCell cell15 = row.createCell(15);
				HSSFCell cell16 = row.createCell(16);
				HSSFCell cell17 = row.createCell(17);
				HSSFCell cell18 = row.createCell(18);
				HSSFCell cell19 = row.createCell(19);
				HSSFCell cell20 = row.createCell(20);
				HSSFCell cell21 = row.createCell(21);
				HSSFCell cell22 = row.createCell(22);
				HSSFCell cell23 = row.createCell(23);
				HSSFCell cell24 = row.createCell(24);
				HSSFCell cell25 = row.createCell(25);
				HSSFCell cell26 = row.createCell(26);
				HSSFCell cell27 = row.createCell(27);
				HSSFCell cell28 = row.createCell(28);
				HSSFCell cell29 = row.createCell(29);
				HSSFCell cell30 = row.createCell(30);
				HSSFCell cell31 = row.createCell(31);
				HSSFCell cell32 = row.createCell(32);
				HSSFCell cell33 = row.createCell(33);
				HSSFCell cell34 = row.createCell(34);
				HSSFCell cell35 = row.createCell(35);
				HSSFCell cell36 = row.createCell(36);
				HSSFCell cell37 = row.createCell(37);
				HSSFCell cell38 = row.createCell(38);
				HSSFCell cell39 = row.createCell(39);
				HSSFCell cell40 = row.createCell(40);
				HSSFCell cell41 = row.createCell(41);
				HSSFCell cell42 = row.createCell(42);
				HSSFCell cell43 = row.createCell(43);
				HSSFCell cell44 = row.createCell(44);
				HSSFCell cell45 = row.createCell(45);
				HSSFCell cell46 = row.createCell(46);
				HSSFCell cell47 = row.createCell(47);
				HSSFCell cell48 = row.createCell(48);
				HSSFCell cell49 = row.createCell(49);
				HSSFCell cell50 = row.createCell(50);
				HSSFCell cell51 = row.createCell(51);
				HSSFCell cell52 = row.createCell(52);
				HSSFCell cell53 = row.createCell(53);
				HSSFCell cell54 = row.createCell(54);
				HSSFCell cell55 = row.createCell(55);
				HSSFCell cell56 = row.createCell(56);
				HSSFCell cell57 = row.createCell(57);
				HSSFCell cell58 = row.createCell(58);
				HSSFCell cell59 = row.createCell(59);
				HSSFCell cell60 = row.createCell(60);
				HSSFCell cell61 = row.createCell(61);
				HSSFCell cell62 = row.createCell(62);
				HSSFCell cell63 = row.createCell(63);
				HSSFCell cell64 = row.createCell(64);
				HSSFCell cell65 = row.createCell(65);
				HSSFCell cell66 = row.createCell(66);
				HSSFCell cell67 = row.createCell(67);
				HSSFCell cell68 = row.createCell(68);
				HSSFCell cell69 = row.createCell(69);
				HSSFCell cell70 = row.createCell(70);
				HSSFCell cell71 = row.createCell(71);
				HSSFCell cell72 = row.createCell(72);
				HSSFCell cell73 = row.createCell(73);
				HSSFCell cell74 = row.createCell(74);
				HSSFCell cell75 = row.createCell(75);
				HSSFCell cell76 = row.createCell(76);
				HSSFCell cell77 = row.createCell(77);
				HSSFCell cell78 = row.createCell(78);
				HSSFCell cell79 = row.createCell(79);
				HSSFCell cell80 = row.createCell(80);
				HSSFCell cell81 = row.createCell(81);
				HSSFCell cell82 = row.createCell(82);
				HSSFCell cell83 = row.createCell(83);
				HSSFCell cell84 = row.createCell(84);
				HSSFCell cell85 = row.createCell(85);
				HSSFCell cell86 = row.createCell(86);
				HSSFCell cell87 = row.createCell(87);
				HSSFCell cell88 = row.createCell(88);
				HSSFCell cell89 = row.createCell(89);
				
				//编号
				cell0.setCellValue("普通采购");
				cell0.setCellStyle(style);
				//出库单号
				cell1.setCellValue(warehouserpt.getWarehouseno());
				cell1.setCellStyle(style);
				
			// 业务类型	
				cell0.setCellValue("普通采购");
				cell0.setCellStyle(style);
			// 订单编号	
				// get CKD account no
//				cell1.setCellValue(warehouserpt.getCKG_No());
				cell1.setCellValue(String.format("%09d", Integer.valueOf(str_accountno)));
				cell1.setCellStyle(style);
			// 订单日期	客户要求使用创建日期
		        //SimpleDateFormatクラスでフォーマットパターンを設定する
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		    
				cell2.setCellValue(sdf.format(warehouserpt.getCreatedate()));
				cell2.setCellStyle(style);
			// 供应商编号	
//				cell3.setCellValue(warehouserpt.getSupplierAccountId());
				cell3.setCellValue(warehouserpt.getRes04());
				cell3.setCellStyle(style);
			// 是否含税	
				// 0 不含税， 1 含税
				cell4.setCellValue("1");
				cell4.setCellStyle(style);
			// 供方联系人编码	
//				cell5.setCellValue(num + 1);
//				cell5.setCellStyle(style);
			// 供方联系人	
//				cell6.setCellValue(num + 1);
//				cell6.setCellStyle(style);
			// 供方银行名称	
//				cell7.setCellValue(num + 1);
//				cell7.setCellStyle(style);
			// 供方银行账号	
//				cell8.setCellValue(num + 1);
//				cell8.setCellStyle(style);
			// 部门编码	
				cell9.setCellValue("01");
				cell9.setCellStyle(style);
			// 业务员编码	
//				cell10.setCellValue(num + 1);
//				cell10.setCellStyle(style);
			// 采购类型编码	
//				cell11.setCellValue(num + 1);
//				cell11.setCellStyle(style);
			// 到货地址	
//				cell12.setCellValue(num + 1);
//				cell12.setCellStyle(style);
			// 运输方式	
//				cell13.setCellValue(num + 1);
//				cell13.setCellStyle(style);
			// 币种	
				cell14.setCellValue("人民币");
				cell14.setCellStyle(style);
			// 汇率	
				cell15.setCellValue("1");
				cell15.setCellStyle(style);
			// 表头税率	
				//get tax
				cell16.setCellValue(rate100.toString());
				cell16.setCellStyle(style);
			// 付款条件	
//				cell17.setCellValue(num + 1);
//				cell17.setCellStyle(style);
			// 备注	
//				cell18.setCellValue(num + 1);
//				cell18.setCellStyle(style);
			// 扣税类别	
				// 0: 外税    1：内税
				cell19.setCellValue("1");
				cell19.setCellStyle(style);
			// 制单人	
				cell20.setCellValue("003");
				cell20.setCellStyle(style);
			// 收付款协议编码	
//				cell21.setCellValue(num + 1);
//				cell21.setCellStyle(style);
			// 表头自定义项1	
//				cell22.setCellValue(num + 1);
//				cell22.setCellStyle(style);
			// 表头自定义项2	
//				cell23.setCellValue(num + 1);
//				cell23.setCellStyle(style);
			// 表头自定义项3	
//				cell24.setCellValue(num + 1);
//				cell24.setCellStyle(style);
			// 表头自定义项4	
//				cell25.setCellValue(num + 1);
//				cell25.setCellStyle(style);
			// 表头自定义项5	
//				cell26.setCellValue(num + 1);
//				cell26.setCellStyle(style);
			// 表头自定义项6	
//				cell27.setCellValue(num + 1);
//				cell27.setCellStyle(style);
			// 表头自定义项7	
//				cell28.setCellValue(num + 1);
//				cell28.setCellStyle(style);
			// 表头自定义项8	
//				cell29.setCellValue(num + 1);
//				cell29.setCellStyle(style);
			// 表头自定义项9	
//				cell30.setCellValue(num + 1);
//				cell30.setCellStyle(style);
			// 表头自定义项10	
//				cell31.setCellValue(num + 1);
//				cell31.setCellStyle(style);
			// 表头自定义项11	
//				cell32.setCellValue(num + 1);
//				cell32.setCellStyle(style);
			// 表头自定义项12	
//				cell33.setCellValue(num + 1);
//				cell33.setCellStyle(style);
			// 表头自定义项13	
//				cell34.setCellValue(num + 1);
//				cell34.setCellStyle(style);
			// 表头自定义项14	
//				cell35.setCellValue(num + 1);
//				cell35.setCellStyle(style);
			// 表头自定义项15	
//				cell36.setCellValue(num + 1);
//				cell36.setCellStyle(style);
			// 表头自定义项16	
//				cell37.setCellValue(num + 1);
//				cell37.setCellStyle(style);
			// 存货编码	
				cell38.setCellValue(product.getItem16());
			cell38.setCellStyle(style);
			// 是否检验	
				// 0: 非质检    1: 质检
				cell39.setCellValue("0");
				cell39.setCellStyle(style);
			// 单位编码	
				cell40.setCellValue(product.getItem17());
				cell40.setCellStyle(style);
			// 主计量数量	
				cell41.setCellValue(product.getAmount());
				cell41.setCellStyle(style);
			// 换算率	
//				cell42.setCellValue(num + 1);
//				cell42.setCellStyle(style);
			// 件数	
//				cell43.setCellValue(num + 1);
//				cell43.setCellStyle(style);
			// 原币含税单价	
				cell44.setCellValue(product.getWarehousetaxprice());
				cell44.setCellStyle(style);
			// 原币单价	
				cell45.setCellValue(product.getUnitprice());
				cell45.setCellStyle(style);
			// 原币金额	
				cell46.setCellValue(product.getAmount());
				cell46.setCellStyle(style);
			// 原币税额
				BigDecimal bgnum = new BigDecimal(product.getNum());
				BigDecimal bgunitprice = new BigDecimal(product.getUnitprice());
				BigDecimal taxprice = rate.multiply(bgnum.multiply(bgunitprice)); 
				cell47.setCellValue(taxprice.toString());
				cell47.setCellStyle(style);
			// 原币价税合计	
				cell48.setCellValue(product.getRes06());
				cell48.setCellStyle(style);
			// 税率	
				cell49.setCellValue(rate100.toString());
				cell49.setCellStyle(style);
			// 本币单价	
				cell50.setCellValue(product.getUnitprice());
				cell50.setCellStyle(style);
			// 本币金额	
				cell51.setCellValue(product.getAmount());
				cell51.setCellStyle(style);
			// 本币税额	
				cell52.setCellValue(taxprice.toString());
				cell52.setCellStyle(style);
			// 本币价税合计	
				cell53.setCellValue(product.getRes06());
				cell53.setCellStyle(style);
			// 项目大类编码	
//				cell54.setCellValue(num + 1);
//				cell54.setCellStyle(style);
			// 项目编码	
//				cell55.setCellValue(num + 1);
//				cell55.setCellStyle(style);
			// 项目名称	
//				cell56.setCellValue(num + 1);
//				cell56.setCellStyle(style);
			// 计划到货日期	客户要求使用创建日期
				cell57.setCellValue(sdf.format(warehouserpt.getCreatedate()));
				cell57.setCellStyle(style);
			// 表体自定义项1	
/*				cell58.setCellValue(num + 1);
				cell58.setCellStyle(style);
			// 表体自定义项2	
				cell59.setCellValue(num + 1);
				cell59.setCellStyle(style);
			// 表体自定义项3	
				cell60.setCellValue(num + 1);
				cell60.setCellStyle(style);
			// 表体自定义项4	
				cell61.setCellValue(num + 1);
				cell61.setCellStyle(style);
			// 表体自定义项5	
				cell62.setCellValue(num + 1);
				cell62.setCellStyle(style);
			// 表体自定义项6	
				cell63.setCellValue(num + 1);
				cell63.setCellStyle(style);
			// 表体自定义项7	
				cell64.setCellValue(num + 1);
				cell64.setCellStyle(style);
			// 表体自定义项8	
				cell65.setCellValue(num + 1);
				cell65.setCellStyle(style);
			// 表体自定义项9	
				cell66.setCellValue(num + 1);
				cell66.setCellStyle(style);
			// 表体自定义项10	
				cell67.setCellValue(num + 1);
				cell67.setCellStyle(style);
			// 表体自定义项11	
				cell68.setCellValue(num + 1);
				cell68.setCellStyle(style);
			// 表体自定义项12	
				cell69.setCellValue(num + 1);
				cell69.setCellStyle(style);
			// 表体自定义项13	
				cell70.setCellValue(num + 1);
				cell70.setCellStyle(style);
			// 表体自定义项14	
				cell71.setCellValue(num + 1);
				cell71.setCellStyle(style);
			// 表体自定义项15	
				cell72.setCellValue(num + 1);
				cell72.setCellStyle(style);
			// 表体自定义项16	
				cell73.setCellValue(num + 1);
				cell73.setCellStyle(style);
			// 自由项1	
				cell74.setCellValue(num + 1);
				cell74.setCellStyle(style);
			// 自由项2	
				cell75.setCellValue(num + 1);
				cell75.setCellStyle(style);
			// 自由项3	
				cell76.setCellValue(num + 1);
				cell76.setCellStyle(style);
			// 自由项4	
				cell77.setCellValue(num + 1);
				cell77.setCellStyle(style);
			// 自由项5	
				cell78.setCellValue(num + 1);
				cell78.setCellStyle(style);
			// 自由项6	
				cell79.setCellValue(num + 1);
				cell79.setCellStyle(style);
			// 自由项7	
				cell80.setCellValue(num + 1);
				cell80.setCellStyle(style);
			// 自由项8	
				cell81.setCellValue(num + 1);
				cell81.setCellStyle(style);
			// 自由项9	
				cell82.setCellValue(num + 1);
				cell82.setCellStyle(style);
			// 自由项10	
				cell83.setCellValue(num + 1);
				cell83.setCellStyle(style);
			// 需求跟踪方式	
				cell84.setCellValue(num + 1);
				cell84.setCellStyle(style);
			// 需求跟踪号	
				cell85.setCellValue(num + 1);
				cell85.setCellStyle(style);
			// 需求跟踪行号	
				cell86.setCellValue(num + 1);
				cell86.setCellStyle(style);
			// 工厂编码	
				cell87.setCellValue(num + 1);
				cell87.setCellStyle(style);
			// 录入日期	
				cell88.setCellValue(num + 1);
				cell88.setCellStyle(style);
			// 录入员
				cell89.setCellValue(num + 1);
				cell89.setCellStyle(style);
*/
				num++;
			}
		}		
		
		/*
		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			if(warehouserpt.getListProduct() != null && warehouserpt.getListProduct().size() > 0) {
				//对货物数据解析
				for(int j = 0; j < warehouserpt.getListProduct().size(); j++) {
					ProductDto product = warehouserpt.getListProduct().get(j);
					row = sheet.createRow(num + 5);
					HSSFCell cell0 = row.createCell(0);
					HSSFCell cell1 = row.createCell(1);
					HSSFCell cell2 = row.createCell(2);
					HSSFCell cell3 = row.createCell(3);
					HSSFCell cell4 = row.createCell(4);
					HSSFCell cell5 = row.createCell(5);
					HSSFCell cell6 = row.createCell(6);
					HSSFCell cell7 = row.createCell(7);
					HSSFCell cell8 = row.createCell(8);
					HSSFCell cell9 = row.createCell(9);
					HSSFCell cell10 = row.createCell(10);
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
					//规格
					cell4.setCellValue(product.getTypeno());
					cell4.setCellStyle(style);
					//颜色
					cell5.setCellStyle(style);
					cell5.setCellValue(dictMap.get(Constants.DICT_COLOR_TYPE + "_" + product.getColor()));
					//包装
					cell6.setCellStyle(style);
					cell6.setCellValue(product.getItem10());
					//数量
					cell7.setCellStyle(style);
					BigDecimal n = null;
					BigDecimal price = null;
					BigDecimal amount = null;
					
					if(product.getNum() != null && !"".equals(product.getNum())) {
						n = new BigDecimal(product.getNum());
						cell7.setCellValue(StringUtil.BigDecimal2StrAbs(n, 2));
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
							cell9.setCellValue(StringUtil.BigDecimal2StrAbs(amount, 6));
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
		HSSFCell cell6 = row.createCell(6);
		HSSFCell cell7 = row.createCell(7);
		HSSFCell cell8 = row.createCell(8);
		HSSFCell cell9 = row.createCell(9);
		cell6.setCellValue("数量合计");
		cell6.setCellStyle(style);
		cell7.setCellValue(StringUtil.BigDecimal2StrAbs(count, 2));
		cell7.setCellStyle(style);
		cell8.setCellValue("金额合计");
		cell8.setCellStyle(style);
		cell9.setCellValue(StringUtil.BigDecimal2StrAbs(amountCount, 6));
		cell9.setCellStyle(style);
		*/
	}
	
	
	public void copyExcel(String fromexcel, String newexcel) {
		HSSFWorkbook wb = null;
		FileInputStream fis =null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fromexcel);
			fos = new FileOutputStream(newexcel);
			wb = new HSSFWorkbook(fis);
			HSSFSheet fromsheet = wb.getSheet("sample");
			HSSFSheet tosheet = wb.createSheet("Sheet4");
			copySheet(fromsheet, tosheet);                                      
			wb.write(fos);
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null)
					fis.close();
				if(fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static void copySheet(HSSFSheet newSheet, HSSFSheet sheet) {  
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
