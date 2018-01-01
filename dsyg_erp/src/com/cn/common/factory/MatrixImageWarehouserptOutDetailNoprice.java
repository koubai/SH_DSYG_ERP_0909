package com.cn.common.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cn.common.util.Constants;
import com.cn.common.util.DateUtil;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.ProductDto;
import com.cn.dsyg.dto.PurchaseDto;
import com.cn.dsyg.dto.WarehouserptDto;

public class MatrixImageWarehouserptOutDetailNoprice extends MatrixImageBase {
	
	/**
	 * 输出大标题
	 * @param sheet
	 */
	public void writeTitle() {
		setContent("东升盈港出货明细单");
		setContent(getContent() + "\r\n");
		
		WarehouserptDto warehouserpt = new WarehouserptDto();
		//添加数据
		warehouserpt = (WarehouserptDto) datas.get(0);
		setContent(getContent() + "客户：" + warehouserpt.getSuppliername() + "\r\n");
		setContent(getContent() + "发货单号：" + warehouserpt.getWarehouseno() + "\r\n");
		setContent(getContent() + "发货日期：" + warehouserpt.getShowWarehousedate() + "\r\n");
	}
	
	/**
	 * 输出数据部分
	 * @param sheet
	 */
	@Override
	public void writeData() {
		WarehouserptDto warehouserpt = new WarehouserptDto();

		//添加数据
		int num = 0;
		for(int i = 0; i < datas.size(); i++) {
			warehouserpt = (WarehouserptDto) datas.get(i);
			if(warehouserpt.getListProduct() != null && warehouserpt.getListProduct().size() > 0) {
				//对货物数据解析
				for(int j = 0; j < warehouserpt.getListProduct().size(); j++) {
					ProductDto product = warehouserpt.getListProduct().get(j);

					setContent(getContent() + product.getParentid()+ ",");
					setContent(getContent() + product.getBrand()+ ",");
					setContent(getContent() + product.getTradename()+ ",");
					setContent(getContent() + product.getTypeno()+ ",");
					setContent(getContent() + dictMap.get(Constants.DICT_COLOR_TYPE + "_" + product.getColor())+ ",");
					setContent(getContent() + dictMap.get(Constants.DICT_UNIT_TYPE + "_" + product.getUnit())+ ",");
					if(product.getNum() != null && !"".equals(product.getNum())) {
						//Float n = Float.valueOf(product.getNum());
						BigDecimal d = new BigDecimal(product.getNum());
							setContent(getContent() + StringUtil.BigDecimal2StrAbs(d, 2)+ ",");
						//}
					} else {
						setContent(getContent() + ""+ ",");
					}
					if (product.getRes09()==null || product.getRes09().equals("null"))
						setContent(getContent() + ""+ ",");
					else
						setContent(getContent() + product.getRes09() + ",");					
					num++;
				}
			}
		}
	}
	
	/**
	 * 输出Head部分
	 * @param sheet
	 */
	@Override
	public void writeHead() {
	}
}
