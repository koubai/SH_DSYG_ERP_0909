package com.cn.common.util;

import java.math.BigDecimal;

import com.cn.dsyg.dto.WarehouseDto;

public class WarehouseUtil {

	/**
	 * 计算成本价
	 * @param primecostWarehouseDto
	 * @param rate
	 * @return
	 */
	public static BigDecimal calcPrimecost(WarehouseDto primecostWarehouseDto, BigDecimal rate) {
		BigDecimal primecost = null;
		if(primecostWarehouseDto != null) {
			if(primecostWarehouseDto.getAmount() != null && primecostWarehouseDto.getQuantity() != null) {
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
