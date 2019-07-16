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
<title>运费信息确认</title>
<script type="text/javascript">
</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<s:hidden name="strCustomerId" id="strCustomerId"></s:hidden>
	<div id="container" style="width: 100%; height: 100%;">
		<table class="info_tab" width="100%" border="0" cellpadding="5" cellspacing="0">
			<tr>
				<td width="100">　 </td>
				<td width="250">　 </td>
				<td width="100">　 </td>
				<td>　 </td>
			</tr>
			<tr>
				<td align="right">客户名称：</td>
				<td colspan="3">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="showCustomerDto.customername" id="customername" disabled="true" cssStyle="width:410px;" maxlength="128" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td align="right">具体地址：</td>
				<td colspan="3">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="showCustomerDto.customeraddress1" id="customeraddress1" disabled="true" cssStyle="width:410px;" maxlength="128" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td align="right">起点：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="strBelongto" id="strBelongto" disabled="true" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
				<td align="right">终点：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="showCustomerDto.res01" id="res01" disabled="true" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td align="right">实重：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<input type="text" id="productWeight" style="width:120px;" maxlength="32"/>
					</div>
					<div class="box1_right"></div>
					<label style="line-height: 35px;">Kg</label>
				</td>
				<td align="right">材积：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<input type="text" id="productVolume" style="width:120px;" maxlength="32"/>
					</div>
					<div class="box1_right"></div>
					<label style="line-height: 35px;">m³</label>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div class="btn" style="margin-left: 0px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="评估" onclick=""/>
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
			</tr>
		</table>
		<div class="tittle">
			<div style="cursor: pointer;">
				<div class="tittle_left"></div>
				<div class="tittle_center" style="color:#000;">实重计费</div>
				<div class="tittle_right"></div>
			</div>
			<div style="cursor: pointer;">
				<div class="tittle_left"></div>
				<div class="tittle_center" style="color:#000;">材积计费</div>
				<div class="tittle_right"></div>
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
						<td width="80"></td>
						<td width="200">快递公司</td>
						<td width="120">单价</td>
						<td width="120">合计</td>
						<td width="180">备考</td>
					</tr>
					<tr class="tr_bg" onclick="checkCheckboxCuTr(this, event, 1, 0);">
					<!-- <tr onclick="checkCheckboxCuTr(this, event, 1, 0);"> -->
						<td>
							<input type="radio"/>
						</td>
						<td>快递公司111</td>
						<td>111</td>
						<td>2222</td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btns" style="margin-top:40px; margin-left: 0px;">
			<table border="0" style="margin:0 auto;">
				<tr>
					<td>
						<div class="btn">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input type="button" class="input80" value="取消" onclick="window.close();"/>
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
