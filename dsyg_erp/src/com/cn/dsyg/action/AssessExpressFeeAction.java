package com.cn.dsyg.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.PropertiesConfig;
import com.cn.dsyg.dto.AjaxResultDto;
import com.cn.dsyg.dto.CustomerDto;
import com.cn.dsyg.service.CustomerService;
import com.cn.dsyg.service.DeliveryPriceService;
import com.cn.dsyg.service.DeliveryService;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.WarehouseService;

import net.sf.json.JSONArray;

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
	//起点所属
	private String strBelongto;

	/**
	 * 显示运费评估页面
	 * @return
	 */
	public String showAssessExpressFeeAction() {
		try {
			this.clearMessages();
			strWeight = "";
			strCube = "";
			//起点
			String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
			if("1".equals(belongto)) {
				strBelongto = "深圳";
			} else {
				strBelongto = "上海";
			}
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
	 * @throws IOException 
	 */
	public String assessExpressFeeAction() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			//根据客户ID查询客户信息
			showCustomerDto = customerService.queryEtbCustomerByID(strCustomerId);
			//计算客户的快递费用
			//查询出所有快递公司
			//根据快递公司的费用列表来匹配出每个快递公司最优解快递费用
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("评估快递费用异常：" + e.getMessage());
			log.error("assessExpressFeeAction error:" + e);
		}
		out = response.getWriter();
		String result = JSONArray.fromObject(ajaxResult).toString();
		result = result.substring(1, result.length() - 1);
		log.info(result);
		out.write(result);
		out.flush();
		return null;
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

	public String getStrBelongto() {
		return strBelongto;
	}

	public void setStrBelongto(String strBelongto) {
		this.strBelongto = strBelongto;
	}
}
