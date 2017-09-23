<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>客户财务信息一览</title>
<script type="text/javascript">
	$(function() {
	});
	
</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<s:hidden name="strIds" id="strIds"></s:hidden>
	<div id="container" style="width: 100%; height: 100%;">
		<div class="searchbox">
			<div class="box1">
				<label class="pdf10">客户名：</label>
				<div class="box1_left"></div>
				<div class="box1_center">
					<s:textfield name="strCustomername" id="strCustomername" cssStyle="width:150px;" maxlength="32" theme="simple"></s:textfield>
				</div>
				<div class="box1_right"></div>
			</div>
			<div class="box1">
				<label class="pdf10">开始日：</label>
				<div class="box1_left"></div>
				<div class="box1_center">
					<s:textfield name="strReceiptdateLow" id="strReceiptdateLow" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
				</div>
				<div class="box1_right"></div>
			</div>
			<div class="box1">
				<label class="pdf10">终了日：</label>
				<div class="box1_left"></div>
				<div class="box1_center">
					<s:textfield name="strReceiptdateHigh" id="strReceiptdateHigh" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
				</div>
				<div class="box1_right"></div>
			</div>
		</div>
		<div class="data_table" style="padding:0px;">
			<div class="tab_tittle">
				<table width="100%" border="1" cellpadding="5" cellspacing="0">
				</table>
			</div>
			<div class="tab_content">
				<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<tr class="tittle">
						<td width="40">序号</td>
						<td width="110">账目编号</td>
						<td width="110">关联单据编号</td>
						<td width="80">主题</td>
						<td width="110">对象</td>
						<td width="60">联系人</td>
						<td width="60">经手人</td>
						<td width="90">单据日期</td>
						<td width="100">金额（含税）</td>
						<td width="90">结算日期</td>
						<td width="120">发票号</td>
						<td width="160">状态</td>
					</tr>
					<s:iterator id="kaipiaoList" value="kaipiaoList" status="st1">
						<tr>
							<td><s:property value="#st1.index + 1"/></td>
							<td><s:property value="receiptid"/></td>
							<td><s:property value="invoiceid"/></td>
							<td>
								<s:if test="financetype == 1">
									采购
								</s:if>
								<s:elseif test="financetype == 2">
									订单
								</s:elseif>
								<s:elseif test="financetype == 3">
									物流
								</s:elseif>
								<s:elseif test="financetype == 4">
									<s:iterator id="financeDictList" value="financeDictList" status="st3">
										<s:if test="%{financeDictList[#st3.index].code == kaipiaoList[#st1.index].theme}">
											<s:property value="fieldname"/>
										</s:if>
									</s:iterator>
								</s:elseif>
								<s:else>
									<s:property value="financetype"/>
								</s:else>
							</td>
							<td><s:property value="customername"/></td>
							<td><s:property value="customermanager"/></td>
							<td><s:property value="handlername"/></td>
							<td><s:property value="showReceiptdate"/></td>
							<td align="right"><s:property value="amount"/></td>
							<td><s:property value="showAccountdate"/></td>
							<td>
								<div noWrap title="<s:property value="res10"/>" style="width:105px;text-overflow:ellipsis;overflow:hidden">
									<s:property value="res10"/>
								</div>
							</td>
							<td>
								<s:if test='mode == "1"'>
									<s:if test="%{status == 1}">
										未对帐
									</s:if>
									<s:elseif test="%{status == 10}">
										已对帐, 未开票
									</s:elseif>
									<s:elseif test="%{status == 15}">
										已收款, 未对账
									</s:elseif>
									<s:elseif test="%{status == 20}">
										已开票, 未收款
									</s:elseif>
									<s:elseif test="%{status == 99}">
										已开票, 已收款
									</s:elseif>
								</s:if>
								<s:else>
									<s:if test="%{status == 1}">
										未收到发票, 未付款
									</s:if>
									<s:elseif test="%{status == 10}">
										收到发票, 安排付款
									</s:elseif>
									<s:elseif test="%{status == 15}">
										未收到发票, 已付款
									</s:elseif>
									<s:elseif test="%{status == 99}">
										收到发票, 已付款
									</s:elseif>
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
		<div class="btns" style="margin-top:40px; margin-left: 0px;">
			<table border="0" style="margin:0 auto;">
				<tr>
					<td align="center">
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input type="button" class="input80" value="关闭" onclick="window.close();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</s:form>
</body>
</html>
