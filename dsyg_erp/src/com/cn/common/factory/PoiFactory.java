package com.cn.common.factory;

import com.cn.common.util.Constants;

/**
 * @name PoiFactory.java
 * @author Frank
 * @time 2013-10-9下午8:34:00
 * @version 1.0
 */
public class PoiFactory {

	/**
	 * @param type
	 * @return
	 */
	public static Poi2007Base getPoi(String type) {
		if(Constants.EXCEL_TYPE_PURCHASELIST.equals(type)) {
			//采购单数据
			return new PoiPurchase();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_IN_LIST.equals(type)) {
			//入库单数据
			return new PoiWarehouserptIn();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_LIST.equals(type)) {
			//出库单数据
			return new PoiWarehouserptOut();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_LIST_ALL.equals(type)) {
			//出库清单数据ALL
			return new PoiWarehouserptOutAll();
		}
		
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_IN_DETAIL_LIST.equals(type)) {
			//入库单明细数据
			return new PoiWarehouserptInDetail();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_YY_IN_DETAIL_LIST.equals(type)) {
			//入库单明细数据(用友)
			return new PoiWarehouserptYYInDetail();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST.equals(type)) {
			//出库单明细数据
			return new PoiWarehouserptOutDetail();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST2.equals(type)) {
			//出库单明细数据(新)
			return new PoiWarehouserptOutDetail2();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_YY_OUT_DETAIL_LIST.equals(type)) {
			//出库单明细数据(用友)
			return new PoiWarehouserptYYOutDetail();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_IN_DETAIL_INTER_LIST.equals(type)) {
			//入库单明细数据
			return new PoiWarehouserptInDetailInter();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_INTER_LIST.equals(type)) {
			//出库单明细数据
			return new PoiWarehouserptOutDetailInter();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_INTER_LIST2.equals(type)) {
			//（新）出库配货单明细数据
			return new PoiWarehouserptOutDetailInter2();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_IN_DETAIL_LIST_NOPRICE.equals(type)) {
			//入库单明细数据(不含含税单价）
			return new PoiWarehouserptInDetailNoprice();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST_NOPRICE.equals(type)) {
			//出库单明细数据(不含含税单价）
			return new PoiWarehouserptOutDetailNoprice();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_OUT_DETAIL_LIST_NOPRICE2.equals(type)) {
			//出库单明细数据(不含含税单价）(新)
			//return new PoiWarehouserptOutDetailNoprice2();
			return new PoiWarehouserptOutDetail2();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSCHECK.equals(type)) {
			//库存盘点数据
			return new PoiWarehouseCheck();
		}
		if(Constants.EXCEL_TYPE_PRODUCT_COST.equals(type)) {
			//库存盘点数据
			return new PoiProductCost();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSCHECKIN.equals(type)) {
			//库存盘点数据
			return new PoiWarehouseCheckIn();
		}
		if(Constants.EXCEL_TYPE_WAREHOUSERPT_IN_DETAIL_CGD_LIST.equals(type)) {
			//用友数据导出
			return new PoiAccountCGDlist();
		}
		return null;
	}
}
