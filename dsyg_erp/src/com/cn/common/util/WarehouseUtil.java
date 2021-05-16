package com.cn.common.util;

import java.math.BigDecimal;

import com.cn.dsyg.dto.WarehouseDto;

public class WarehouseUtil {
	
	/**
	 * 库存的税后成本价格
	 * @param warehouse
	 * @param rate
	 * @return
	 */
	public static BigDecimal calcPrimeAmount(WarehouseDto warehouse, BigDecimal rate) {
		if(warehouse != null) {
			if(StringUtil.isNotBlank(warehouse.getRes04())) {
				BigDecimal primeamount = warehouse.getQuantity().multiply(new BigDecimal(warehouse.getRes04())).multiply(rate);
				return primeamount.abs();
			}
		}
		return new BigDecimal(0);
	}
	
	/**
	 * 计算利润率
	 * @param totalprimeamount 税后成本金额
	 * @param totaltaxamount 销售税后金额
	 * @return
	 */
	public static String calcProfitRate(BigDecimal totalprimeamount, BigDecimal totaltaxamount) {
		if(totalprimeamount != null && !totalprimeamount.equals(BigDecimal.ZERO)) {
			BigDecimal profitrate = totaltaxamount.subtract(totalprimeamount).multiply(new BigDecimal(100)).divide(totalprimeamount, 2, BigDecimal.ROUND_HALF_UP);
			return profitrate + "%";
		}
		return "";
	}

	/**
	 * 计算成本价
	 * @param primecostWarehouseDto
	 * @param rate
	 * @return
	 */
	public static BigDecimal calcPrimecost(WarehouseDto primecostWarehouseDto, BigDecimal rate) {
		BigDecimal primecost = null;
		if(primecostWarehouseDto != null) {
			if(primecostWarehouseDto.getAmount() != null && primecostWarehouseDto.getQuantity() != null && !primecostWarehouseDto.getQuantity().equals(BigDecimal.ZERO)) {
				//计算成本价
				//税后总金额
				BigDecimal taxamount = primecostWarehouseDto.getAmount().multiply(rate).setScale(6, BigDecimal.ROUND_HALF_UP);
				//税后单价=税后总金额/库存总数量
				primecost = taxamount.divide(primecostWarehouseDto.getQuantity(), 6, BigDecimal.ROUND_HALF_UP);
			}
		}
		return primecost;
	}
}
