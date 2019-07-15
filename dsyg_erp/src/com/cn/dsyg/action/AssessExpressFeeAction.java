package com.cn.dsyg.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cn.common.action.BaseAction;
import com.cn.dsyg.dto.CustomerDto;
import com.cn.dsyg.service.CustomerService;
import com.cn.dsyg.service.DeliveryPriceService;
import com.cn.dsyg.service.DeliveryService;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.WarehouseService;

/**
 * 运费评估
 * @version 1.0
 * @createtime 2019年7月15日 下午11:54:40
 */
public class AssessExpressFeeAction extends BaseAction {

	private static final long serialVersionUID = -138395128332255976L;
	private static final Logger log = LogManager.getLogger(AssessExpressFeeAction.class);
	
	private WarehouseService warehouseService;
	private DeliveryService deliveryService;
	private DeliveryPriceService deliveryPriceService;
	private Dict01Service dict01Service;
	private CustomerService customerService;
	//预出库客户ID
	private String strCustomerId;
	private String strWeight;//重量
	private String strCube;//体积
	private CustomerDto showCustomerDto;

	/**
	 * 显示运费评估页面
	 * @return
	 */
	public String showAssessExpressFeeAction() {
		try {
			this.clearMessages();
			strWeight = "";
			strCube = "";
			//根据客户ID查询客户信息
			showCustomerDto = customerService.queryEtbCustomerByID(strCustomerId);
		} catch(Exception e) {
			log.error("showAssessExpressFeeAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 评估快递费用
	 * @return
	 */
	public String assessExpressFeeAction() {
		try {
			this.clearMessages();
			//根据客户ID查询客户信息
			showCustomerDto = customerService.queryEtbCustomerByID(strCustomerId);
			//计算客户的快递费用
			//查询出所有快递公司
			//根据快递公司的费用列表来匹配出每个快递公司最优解快递费用
		} catch(Exception e) {
			log.error("showAssessExpressFeeAction error:" + e);
			return ERROR;
		}
		return SUCCESS;
	}

	public DeliveryPriceService getDeliveryPriceService() {
		return deliveryPriceService;
	}

	public void setDeliveryPriceService(DeliveryPriceService deliveryPriceService) {
		this.deliveryPriceService = deliveryPriceService;
	}

	public Dict01Service getDict01Service() {
		return dict01Service;
	}

	public void setDict01Service(Dict01Service dict01Service) {
		this.dict01Service = dict01Service;
	}

	public WarehouseService getWarehouseService() {
		return warehouseService;
	}

	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	public String getStrCustomerId() {
		return strCustomerId;
	}

	public void setStrCustomerId(String strCustomerId) {
		this.strCustomerId = strCustomerId;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerDto getShowCustomerDto() {
		return showCustomerDto;
	}

	public void setShowCustomerDto(CustomerDto showCustomerDto) {
		this.showCustomerDto = showCustomerDto;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public String getStrWeight() {
		return strWeight;
	}

	public void setStrWeight(String strWeight) {
		this.strWeight = strWeight;
	}

	public String getStrCube() {
		return strCube;
	}

	public void setStrCube(String strCube) {
		this.strCube = strCube;
	}
}
