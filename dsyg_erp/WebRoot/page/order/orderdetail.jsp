<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/orderstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
<title>订单明细</title>
<script type="text/javascript">
	function goOrderList() {
		document.mainform.action = '<%=request.getContextPath()%>/order/showOrderListAction.action';
		document.mainform.submit();
	}
	
	//交期确认
	function confirmDelivery() {
		var chk_flg = 0;
		for(var j=0; j < 10 && chk_flg == 0; j++){
			var deli_date_element = document.getElementsByName("showOrderDto.orderDetailList["+j+"].deliverydate"); 
			if (deli_date_element.length == 1){
				if (deli_date_element[0].value.trim() == ""){
					alert("交期未填写");
					chk_flg=1;
				}				
			}
		}
		if (chk_flg==0){
			if(confirm("确定提交吗？")) {
				document.mainform.action = '<%=request.getContextPath()%>/order/confirmDeliveryAction.action';
				document.mainform.submit();
			}			
		}
	}
	
	//确认订单
	function confirmOrder() {
		/*
		var companytax = $("#companytax").val().trim();
		var accountbank = $("#accountbank").val().trim();
		var accountid = $("#accountid").val().trim();
		if(companytax == "") {
			alert("公司税号不能为空！");
			$("#companytax").focus();
			return;
		}
		if(accountbank == "") {
			alert("公司开户行不能为空！");
			$("#accountbank").focus();
			return;
		}
		if(accountid == "") {
			alert("开户行账号不能为空！");
			$("#accountid").focus();
			return;
		}//*/
		if(confirm("确定生成该订单吗？")) {
			document.mainform.action = '<%=request.getContextPath()%>/order/confirmOrderAction.action';
			document.mainform.submit();
		}
	}
	
	//确认收款
	function confirmPay() {
		if(confirm("确定收款吗？")) {
			document.mainform.action = '<%=request.getContextPath()%>/order/confirmPayAction.action';
			document.mainform.submit();
		}
	}
	
	//发货
	function sendProduct() {
		if(confirm("确定发货吗？")) {
			document.mainform.action = '<%=request.getContextPath()%>/order/sendProductAction.action';
			document.mainform.submit();
		}
	}
	
	//关闭订单
	function cancelOrder() {
		if(confirm("确定关闭该订单吗？")) {
			document.mainform.action = '<%=request.getContextPath()%>/order/cancelOrderAction.action';
			document.mainform.submit();
		}
	}
	
	function transferOrder() {
		if(confirm("确定转移该订单吗？")) {
			document.mainform.action = '<%=request.getContextPath()%>/order/transferOrderAction.action';
			document.mainform.submit();
		}
	}
	
	function cancelTransferOrder() {
		if(confirm("确定取消转移吗？")) {
			document.mainform.action = '<%=request.getContextPath()%>/order/cancelTransferOrderAction.action';
			document.mainform.submit();
		}
	}
</script>
</head>
<body>
	<div id="containermain">
		<div class="content">
			<div class="tittle">
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px; height: 30px;">
					订单明细
				</div>
				<div class="tittle_right">
				</div>
				<div style="position:absolute; margin-left:400px; margin-top:-7px; color: red; font-size: 14px;">
					<s:actionmessage />
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="strOrderDetailId" id="strOrderDetailId"></s:hidden>
				<table class="input_table" border="0" cellspacing="0" cellpadding="10" style="margin-left: 0px;margin-top: 30px; width: 50%;">
					<tr>
						<td class="td_tittle" width="120">订单号：</td>
						<td>
							<s:property value="showOrderDto.ordercode"/>
						</td>
					</tr>
					<s:if test="showOrderDto.status > 40">
						<tr>
							<td class="td_tittle" width="120">汇款用税号：</td>
							<td>
								<s:property value="showOrderDto.companytax"/>
							</td>
						</tr>
						<tr>
							<td class="td_tittle" width="120">汇款用开户行：</td>
							<td>
								<s:property value="showOrderDto.accountbank"/>
							</td>
						</tr>
						<tr>
							<td class="td_tittle" width="120">汇款用账号：</td>
							<td>
								<s:property value="showOrderDto.accountid"/>
							</td>
						</tr>
					</s:if>
					<s:elseif test="showOrderDto.status == 30 || showOrderDto.status == 40">
						<tr>
							<td class="td_tittle" width="120">汇款用税号：</td>
							<td>
								<s:property value="showOrderDto.companytax"/>
							</td>
						</tr>
						<tr>
							<td class="td_tittle" width="120">汇款用开户行：</td>
							<td>
								<s:property value="showOrderDto.accountbank"/>
							</td>
						</tr>
						<tr>
							<td class="td_tittle" width="120">汇款用账号：</td>
							<td>
								<s:property value="showOrderDto.accountid"/>
							</td>
						</tr>
						<!--
						<tr>
							<td class="td_tittle" width="120">汇款用税号：</td>
							<td>
								<s:textfield id="companytax" name="showOrderDto.companytax" cssStyle="width: 400px;" maxlength="32" theme="simple"></s:textfield>
							</td>
						</tr>
						<tr>
							<td class="td_tittle" width="120">汇款用开户行：</td>
							<td>
								<s:textfield id="accountbank" name="showOrderDto.accountbank" cssStyle="width: 400px;" maxlength="64" theme="simple"></s:textfield>
							</td>
						</tr>
						<tr>
							<td class="td_tittle" width="120">汇款用账号：</td>
							<td>
								<s:textfield id="accountid" name="showOrderDto.accountid" cssStyle="width: 400px;" maxlength="32" theme="simple"></s:textfield>
							</td>
						</tr>
						-->
					</s:elseif>
					<s:if test="showOrderDto.transfer == 1">
						<tr>
							<td class="td_tittle" width="120">订单转移：</td>
							<td>
								该订单已被转移
							</td>
						</tr>
					</s:if>
					<tr>
						<td class="td_tittle" width="120">订单状态：</td>
						<td>
							<s:if test="showOrderDto.status == 10">询货中</s:if>
							<s:elseif test="showOrderDto.status == 20">交期确认，待回复</s:elseif>
							<s:elseif test="showOrderDto.status == 30">交期已回复，受理中</s:elseif>
							<s:elseif test="showOrderDto.status == 40">下单成功，待付款</s:elseif>
							<s:elseif test="showOrderDto.status == 50">已付款，待确认</s:elseif>
							<s:elseif test="showOrderDto.status == 60">已收款，待发货</s:elseif>
							<s:elseif test="showOrderDto.status == 70">已发货，待收货</s:elseif>
							<s:elseif test="showOrderDto.status == 80">已收货，订单完成</s:elseif>
							<s:elseif test="showOrderDto.status == 99">订单已关闭</s:elseif>
						</td>
					</tr>
					<s:if test="showOrderDto.status >= 50 && showOrderDto.status != 99">
						<tr>
							<td class="td_tittle" width="120">付款时间：</td>
							<td>
								<s:date name="showOrderDto.cashdate" format="yyyy/MM/dd HH:mm:ss"/>
							</td>
						</tr>
					</s:if>
				</table>
				<table class="input_table" border="1" cellspacing="5" cellpadding="10" style="margin-top: 15px; width: 100%;">
					<tr class="tab_tittle">
						<td width="5%">&nbsp;</td>
						<td width="30%">商品</td>
						<td width="10%">单价</td>
						<td width="10%">订单数</td>
						<td width="15%">金额</td>
						<td width="20%">交期</td>
						<td width="10%">批号</td>
					</tr>
					<s:iterator value="showOrderDto.orderDetailList" id="orderDetailList" status="st2">
						<s:if test="#st2.odd==true">
							<tr>
						</s:if>
						<s:else>
							<tr class="bg2">
						</s:else>
							<td><s:property value="#st2.index + 1"/></td>
							<td>
								<span style="font-weight: bold; font-size: 14px; color: black;">产品名称：</span><s:property value="tradename"/><br />
								<span style="font-weight: bold; font-size: 14px; color: black;">产品规格：</span><s:property value="typeno"/>　
								<s:iterator value="colorList" id="colorList" status="st1">
									<s:if test="%{colorList[#st1.index].code == showOrderDto.orderDetailList[#st2.index].color}">
										<s:property value="fieldname"/>
									</s:if>
								</s:iterator>　　
								<span style="font-weight: bold; font-size: 14px; color: black;">
									<s:iterator value="makeareaList" id="makeareaList" status="st1">
										<s:if test="%{makeareaList[#st1.index].code == showOrderDto.orderDetailList[#st2.index].makearea}">
											<s:property value="fieldname"/>
										</s:if>
									</s:iterator>
								</span>
								<br />
								<span style="font-weight: bold; font-size: 14px; color: black;">包装数：</span><s:property value="minnum"/>
								<s:iterator value="unitList" id="unitList" status="st3">
									<s:if test="%{unitList[#st3.index].code == showOrderDto.orderDetailList[#st2.index].unit}"><s:property value="fieldname"/></s:if>
								</s:iterator>
							</td>
							<td>
								<span style="color: black;"><s:property value="taxprice"/></span>元
							</td>
							<td align="right">
								<span style="color: black;"><s:property value="num"/></span>
								<s:iterator value="unitList" id="unitList" status="st3">
									<s:if test="%{unitList[#st3.index].code == showOrderDto.orderDetailList[#st2.index].unit}"><s:property value="fieldname"/></s:if>
								</s:iterator>
							</td>
							<td align="right">
								<s:property value="amount"/>元<br />
								（含增值税）<s:property value="taxamount"/>元
							</td>
							<td>
								<s:if test="showOrderDto.status < 30">
									<div class="box1_left"></div>
									<div class="box1_center date_input">
										<input id="tmpdeliverydate_<s:property value="id"/>" readonly="readonly" style="width: 135px;" name="showOrderDto.orderDetailList[<s:property value="#st2.index"/>].deliverydate" value="<s:date name="deliverydate" format="yyyy-MM-dd" />"/>
										<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpdeliverydate_<s:property value="id"/>'));"></a>
									</div>
									<div class="box1_right"></div>
								</s:if>
								<s:else>
									<s:date name="deliverydate" format="yyyy-MM-dd" />
								</s:else>
							</td>
							<td>
								<s:if test='batchno == "001"'>第一批次</s:if>
								<s:elseif test='batchno == "002"'>第二批次</s:elseif>
								<s:elseif test='batchno == "003"'>第三批次</s:elseif>
								<s:elseif test='batchno == "004"'>第四批次</s:elseif>
								<s:elseif test='batchno == "005"'>第五批次</s:elseif>
								<s:elseif test='batchno == "006"'>第六批次</s:elseif>
								<s:elseif test='batchno == "007"'>第七批次</s:elseif>
								<s:elseif test='batchno == "008"'>第八批次</s:elseif>
								<s:elseif test='batchno == "009"'>第九批次</s:elseif>
								<s:elseif test='batchno == "010"'>第十批次</s:elseif>
							</td>
						</tr>
					</s:iterator>
					<tr style="height: 40px;">
						<td align="right" colspan="4" style="font-size: 14px;font-weight: bold;">合计</td>
						<td align="right" colspan="3" style="font-size: 14px;font-weight: bold;">
							<s:property value="showOrderDto.amount"/>元<br />
							（含增值税）<s:property value="showOrderDto.taxamount"/>元
						</td>
					</tr>
				</table>
				<table class="input_table" border="0" cellspacing="0" cellpadding="10" style="margin-left: 0px; margin-top: 15px; width: 50%;">
					<tr>
						<td colspan="2"><p style="font-size: 14px; font-weight: bold;">购买方信息</p></td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">公司名：</td>
						<td>
							<s:property value="showOrderDto.companycn" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">英文公司名或拼音：</td>
						<td>
							<s:property value="showOrderDto.companyen" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120"><span></span>所属部门：</td>
						<td>
							<s:property value="showOrderDto.department" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">姓名：</td>
						<td>
							<s:property value="showOrderDto.name" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">邮编：</td>
						<td>
							<s:property value="showOrderDto.postcode" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">地址：</td>
						<td>
							<s:property value="showOrderDto.address" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">电话号码：</td>
						<td>
							<s:property value="showOrderDto.tell" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">E-mail地址：</td>
						<td>
							<s:property value="showOrderDto.customermail" />
						</td>
					</tr>
				</table>
				<table class="input_table" border="0" cellspacing="0" cellpadding="10" style="margin-left: 0px; margin-top: 40px; width: 50%;">
					<tr>
						<td colspan="2"><p style="font-size: 14px; font-weight: bold;">客户开户银行信息</p></td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">公司税号：</td>
						<td>
							<s:property value="showOrderDto.bankcompanytax" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">开户行：</td>
						<td>
							<s:property value="showOrderDto.bankname" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">帐号：</td>
						<td>
							<s:property value="showOrderDto.bankaccount" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">发票：</td>
						<td>
							<s:if test='showOrderDto.receipttype == "2"'>专用发票</s:if>
							<s:elseif test='showOrderDto.receipttype == "1"'>普通发票</s:elseif>
							<s:else></s:else>
						</td>
					</tr>
				</table>
				<table class="input_table" border="0" cellspacing="0" cellpadding="10" style="margin-left: 0px; margin-top: 40px; width: 50%;">
					<tr>
						<td colspan="2">
							<p style="font-size: 14px; font-weight: bold;">收件人信息</p>
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">公司名：</td>
						<td>
							<s:property value="showOrderDto.companycn2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">英文公司名或拼音：</td>
						<td>
							<s:property value="showOrderDto.companyen2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120"><span></span>所属部门：</td>
						<td>
							<s:property value="showOrderDto.department2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">姓名：</td>
						<td>
							<s:property value="showOrderDto.name2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">邮编：</td>
						<td>
							<s:property value="showOrderDto.postcode2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">地址：</td>
						<td>
							<s:property value="showOrderDto.address2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">电话号码：</td>
						<td>
							<s:property value="showOrderDto.tell2" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">E-mail地址：</td>
						<td>
							<s:property value="showOrderDto.customermail" />
						</td>
					</tr>
					<tr>
						<td class="td_tittle" width="120">交货方法 ：</td>
						<td>
							<s:if test='showOrderDto.paytype == "2"'>
								自提
							</s:if>
							<s:else>
								配送
							</s:else>
						</td>
					</tr>
				</table>
				<div align="center" style="margin-top: 40px;">
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input  class="input80" type="button" value="返回" onclick="goOrderList();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<s:if test="showOrderDto.status == 10">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input80" type="button" value="交期确认" onclick="confirmDelivery();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
					<s:if test="showOrderDto.status == 20">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input80" type="button" value="交期修改" onclick="confirmDelivery();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
					<s:if test="showOrderDto.status == 30">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input80" type="button" value="订单生成" onclick="confirmOrder();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
<!-- 					<s:if test="showOrderDto.status == 40">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input120" type="button" value="确认汇款账户信息" onclick="confirmOrder();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
 -->					
					<s:if test="showOrderDto.status >= 40 && showOrderDto.status <= 50">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input80" type="button" value="确认收款" onclick="confirmPay();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
					<s:if test="showOrderDto.status == 60">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input80" type="button" value="发货" onclick="sendProduct();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
					<s:if test="showOrderDto.status < 50">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input class="input80" type="button" value="关闭订单" onclick="cancelOrder();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</s:if>
					<s:if test="showOrderDto.status < 50">
						<s:if test="showOrderDto.transfer == 1">
							<div class="btn">
								<div class="box1_left"></div>
								<div class="box1_center">
									<input class="input120" type="button" value="取消订单转移" onclick="cancelTransferOrder();"/>
								</div>
								<div class="box1_right"></div>
							</div>
						</s:if>
						<s:else>
							<div class="btn">
								<div class="box1_left"></div>
								<div class="box1_center">
									<input class="input80" type="button" value="订单转移" onclick="transferOrder();"/>
								</div>
								<div class="box1_right"></div>
							</div>
						</s:else>
					</s:if>
				</div>
				<br/><br/><br/><br/>
			</s:form>
		</div>
	</div>
</body>
</html>
