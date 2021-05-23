package com.cn.dsyg.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cn.common.action.BaseAction;
import com.cn.common.util.Constants;
import com.cn.common.util.PropertiesConfig;
import com.cn.common.util.StringUtil;
import com.cn.dsyg.dto.AjaxResultDto;
import com.cn.dsyg.dto.CalcDeliveryPriceDto;
import com.cn.dsyg.dto.CustomerDto;
import com.cn.dsyg.dto.DeliveryDto;
import com.cn.dsyg.dto.DeliveryPriceDto;
import com.cn.dsyg.dto.WarehouserptDto;
import com.cn.dsyg.service.CustomerService;
import com.cn.dsyg.service.DeliveryPriceService;
import com.cn.dsyg.service.DeliveryService;
import com.cn.dsyg.service.Dict01Service;
import com.cn.dsyg.service.WarehouseService;
import com.cn.dsyg.service.WarehouserptService;
import com.cn.dsyg.service.impl.WarehouserptServiceImpl;

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
	private WarehouserptService warehouserptService;
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
	//快递单号
	private String expressno;
	//单据日期
	private Date receiptdate;
	//快递ID
	private String deliveryid;
	//快递金额
	private String deliveryprice;
	//出库单号
	private String updWarehouserptId;
	//保价金额
	private String strIncamount;
	//物流保价金额
	private String expressincamount;
	
	/**
	 * 显示运费评估页面
	 * @return
	 */
	public String showAssessExpressFeeAction() {
		try {
			this.clearMessages();
			strWeight = "";
			strCube = "";
			receiptdate = new Date();
			System.out.println(receiptdate);
			//起点
			String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
			if("1".equals(belongto)) {
				strBelongto = "深圳";
			} else {
				strBelongto = "上海";
			}
			//根据客户ID查询客户信息
			showCustomerDto = customerService.queryEtbCustomerByID(strCustomerId);
			WarehouserptDto warehouserptDto = warehouserptService.queryWarehouserptByID(updWarehouserptId,null);
			strIncamount = warehouserptDto.getIncamount();
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
	public String calcAssessExpressFeeAction() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		AjaxResultDto ajaxResult = new AjaxResultDto();
		try {
			this.clearMessages();
			//根据客户ID查询客户信息
			CustomerDto customerDto = customerService.queryEtbCustomerByID(strCustomerId);
			
			//客户信息不能为空，并且客户的城市信息不能为空
			if(customerDto != null && StringUtil.isNotBlank(customerDto.getRes01())) {
				//开始计算每个快递公司的快递费用
				//起点
				String belongto = PropertiesConfig.getPropertiesValueByKey(Constants.SYSTEM_BELONG);
				String startpoing = "";
				if("1".equals(belongto)) {
					startpoing = "深圳";
				} else {
					startpoing = "上海";
				}
				List<CalcDeliveryPriceDto> data = new ArrayList<CalcDeliveryPriceDto>();
				List<CalcDeliveryPriceDto> data1 = new ArrayList<CalcDeliveryPriceDto>();
				//查询出所有快递公司
				List<DeliveryPriceDto> deliveryPriceList = deliveryPriceService.queryDeliveryPriceByCondition(startpoing, showCustomerDto.getRes01());
				
				BigDecimal minWeight = new BigDecimal(0);
				BigDecimal minCube = new BigDecimal(0);
				//根据快递公司的费用列表来匹配出每个快递公司最优解快递费用
				if(deliveryPriceList != null && deliveryPriceList.size() > 0) {
					for(DeliveryPriceDto deliveryPrice : deliveryPriceList) {
						DeliveryDto deliveryDto = deliveryService.queryEtbDeliveryByID(""+deliveryPrice.getDeliveryid());
						//根据重量计算
						if(StringUtil.isNotBlank(strWeight)) {
							CalcDeliveryPriceDto price = new CalcDeliveryPriceDto();
							price.setDeliveryid(deliveryPrice.getDeliveryid());
							price.setDeliveryname(deliveryPrice.getDeliveryname());
							//费用
							BigDecimal weightprice = calcPrice(strWeight, deliveryPrice.getPricekg());
							//物流保价金额计算
							BigDecimal expressincamount = calcIncPrice(strIncamount, deliveryDto.getRes03());
							price.setExpressincamount(expressincamount);
							price.setDeliveryprice(weightprice.add(expressincamount));
							price.setUnitprice("" + deliveryPrice.getPricekg());
							data.add(price);
							if(minWeight.compareTo(new BigDecimal(0)) == 0) {
								minWeight = weightprice;
							} else {
								if(minWeight.compareTo(weightprice) > 0) {
									minWeight = weightprice;
								}
							}
						}
						
						//根据体积计算
						if(StringUtil.isNotBlank(strCube)) {
							CalcDeliveryPriceDto price1 = new CalcDeliveryPriceDto();
							price1.setDeliveryid(deliveryPrice.getDeliveryid());
							price1.setDeliveryname(deliveryPrice.getDeliveryname());
							//费用
							BigDecimal cubeprice = calcPrice(strCube, deliveryPrice.getPricem3());
							//物流保价金额计算
							BigDecimal expressincamount = calcIncPrice(strIncamount, deliveryDto.getRes03());
							price1.setExpressincamount(expressincamount);
							price1.setDeliveryprice(cubeprice.add(expressincamount));
							price1.setUnitprice("" + deliveryPrice.getPricem3());
							data1.add(price1);
							if(minCube.compareTo(new BigDecimal(0)) == 0) {
								minCube = cubeprice;
							} else {
								if(minCube.compareTo(cubeprice) > 0) {
									minCube = cubeprice;
								}
							}
						}
					}
				}
				if(StringUtil.isBlank(strWeight)) {
					ajaxResult.setMinWeight("");
				} else {
					ajaxResult.setMinWeight("" + minWeight);
				}
				
				if(StringUtil.isBlank(strCube)) {
					ajaxResult.setMinCube("");
				} else {
					ajaxResult.setMinCube("" + minCube);
				}
				
				//对费用排序
				Collections.sort(data, new Comparator<CalcDeliveryPriceDto>() {

					@Override
					public int compare(CalcDeliveryPriceDto o1, CalcDeliveryPriceDto o2) {
						BigDecimal b1 = o1.getDeliveryprice();
						BigDecimal b2 = o2.getDeliveryprice();
						return b1.compareTo(b2);
					}
					
				});
				Collections.sort(data1, new Comparator<CalcDeliveryPriceDto>() {

					@Override
					public int compare(CalcDeliveryPriceDto o1, CalcDeliveryPriceDto o2) {
						BigDecimal b1 = o1.getDeliveryprice();
						BigDecimal b2 = o2.getDeliveryprice();
						return b1.compareTo(b2);
					}
					
				});
				
				ajaxResult.setCode(0);
				ajaxResult.setMsg("");
				ajaxResult.setData(data);
				ajaxResult.setData1(data1);
			}
		} catch(Exception e) {
			ajaxResult.setCode(-1);
			ajaxResult.setMsg("评估快递费用异常：" + e.getMessage());
			log.error("calcAssessExpressFeeAction error:" + e);
		}
		out = response.getWriter();
		String result = JSONArray.fromObject(ajaxResult).toString();
		result = result.substring(1, result.length() - 1);
		log.info(result);
		out.write(result);
		out.flush();
		return null;
	}
	
	/**
	 * 快递费用保持到发货单中
	 * @return
	 * @throws IOException 
	 */
	public String saveAssessExpressFeeAction() throws IOException {
		try {
			this.clearMessages();
			//根据客户ID查询客户信息
			DeliveryDto deliveryDto = deliveryService.queryEtbDeliveryByID(deliveryid);
			//快递单号
			System.out.println("快递单号:" + expressno);
			//单据日期
			System.out.println("单据日期:" + receiptdate);
			//快递ID
			System.out.println("快递ID:" + deliveryid);
			//保价金额
			System.out.println("保价金额:" + strIncamount);
			//物流保价金额
			System.out.println("物流保价金额:" + expressincamount);
			//快递金额
			System.out.println("快递金额:" + deliveryprice);

			//快递费用保持到发货单中
			WarehouserptDto warehouserpt = warehouserptService.queryWarehouserptByID(updWarehouserptId);

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			// 快递单号
			warehouserpt.setExpressid(deliveryid);
			// 快递公司名
			warehouserpt.setExpressname(deliveryDto.getDeliveryname());
			// 快递公司快递地址
			warehouserpt.setExpressaddress(deliveryDto.getDeliveryaddress1());
			// 快递公司快递联系人
			warehouserpt.setExpressmanager(deliveryDto.getDeliverymanager1());
			// 快递公司快递电话
			warehouserpt.setExpresstel(deliveryDto.getDeliverytel1());
			// 快递公司快递传真
			warehouserpt.setExpressfax(deliveryDto.getDeliveryfax1());
			// 快递公司快递信箱
			warehouserpt.setExpressmail(deliveryDto.getDeliverymail1());
			
			
			// 快递单号
			warehouserpt.setExpressno(expressno);
			// 快递包裹重量Kg
			warehouserpt.setRes03(strWeight);
			// 快递金额(含税)
			warehouserpt.setExpresstaxamount(new BigDecimal(deliveryprice));
			// 快递单据日期
			warehouserpt.setRes01(sf.format(receiptdate));

			//新增保价金额和物流保价金额 add by liu 202105
			StringBuffer temInc = new StringBuffer();
			temInc.append(strIncamount);
			temInc.append(",");
			temInc.append(expressincamount);
			warehouserpt.setRes10(temInc.toString());
			
			warehouserptService.updateWarehouserpt(warehouserpt);
			this.addActionMessage("出库单:"+warehouserpt.getWarehouseno()+"添加快递成功！");

		} catch(Exception e) {
			this.addActionMessage("系统异常，出库单添加快递失败！");
			log.error("saveAssessExpressFeeAction error:" + e);
			return ERROR;
		}
		return SUCCESS;

	}
	
	/**
	 * 计算费用
	 * @param amount
	 * @param unitprice
	 * @return
	 */
	private BigDecimal calcPrice(String amount, BigDecimal unitprice) {
		BigDecimal bb = new BigDecimal(amount);
		//四舍五入
		BigDecimal result = bb.multiply(unitprice).setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;
				
	}

	/**
	 * 计算物流保价金额
	 * @param strIncamount
	 * @param incRate
	 * @return
	 */
	private BigDecimal calcIncPrice(String strIncamount, String incRate) {
		BigDecimal result = new BigDecimal(0);
		if(StringUtil.isNotBlank(strIncamount) && StringUtil.isNotBlank(incRate)){
			BigDecimal bb = new BigDecimal(strIncamount);
			BigDecimal rr = new BigDecimal(incRate);
			//四舍五入
			result = bb.multiply(rr).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return result;

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
	
	public String getExpressno() {
		return expressno;
	}

	public void setExpressno(String expressno) {
		this.expressno = expressno;
	}

	public Date getReceiptdate() {
		return receiptdate;
	}

	public void setReceiptdate(Date receiptdate) {
		this.receiptdate = receiptdate;
	}

	public String getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(String deliveryid) {
		this.deliveryid = deliveryid;
	}

	public String getDeliveryprice() {
		return deliveryprice;
	}

	public void setDeliveryprice(String deliveryprice) {
		this.deliveryprice = deliveryprice;
	}
	
	public WarehouserptService getWarehouserptService() {
		return warehouserptService;
	}

	public void setWarehouserptService(WarehouserptService warehouserptService) {
		this.warehouserptService = warehouserptService;
	}

	public String getUpdWarehouserptId() {
		return updWarehouserptId;
	}

	public void setUpdWarehouserptId(String updWarehouserptId) {
		this.updWarehouserptId = updWarehouserptId;
	}

	public String getStrIncamount() {
		return strIncamount;
	}

	public void setStrIncamount(String strIncamount) {
		this.strIncamount = strIncamount;
	}

	public String getExpressincamount() {
		return expressincamount;
	}

	public void setExpressincamount(String expressincamount) {
		this.expressincamount = expressincamount;
	}
}
