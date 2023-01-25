package com.cn.common.factory;

import com.cn.common.util.Constants;

/**
 * @name MatrixImageFactory.java
 * @author Pei
 * @time 2017-12-31
 * @version 1.0
 */
public class MatrixImageFactory {

	/**
	 * @param type
	 * @return
	 */
	public static MatrixImageBase getImage(String type) {
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST_NOPRICE.equals(type)) {
			//出库单明细数据(不含含税单价）
			return new MatrixImageWarehouserptOutDetailNoprice();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST2.equals(type)) {
			//出库单明细数据(不含含税单价）
			return new MatrixImageWarehouserptOutDetailListNoprice2();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST_NOPRICE2.equals(type)) {
			//出库单明细数据(不含含税单价）
			return new MatrixImageWarehouserptOutDetailListNoprice2();
		}
		return null;
	}
}
