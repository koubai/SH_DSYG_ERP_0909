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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>

<title>运费信息确认</title>
<script type="text/javascript">
function changeStyle(obj, flag) {
	/* if(flag == 1) {
		$(obj).css("background", "6699CC");
	} else {
		$(obj).css("background", "");
	} */
}

function showCubePriceList() {
	$("#weightprice_tab").hide();
	$("#cubeprice_tab").show();
	$("#weightDiv").css({"background": ""});
	$("#cubeDiv").css({"background": "#6699CC"});
}

function showWeightPriceList() {
	$("#weightprice_tab").show();
	$("#cubeprice_tab").hide();
	$("#weightDiv").css({"background": "#6699CC"});
	$("#cubeDiv").css({"background": ""});
}

function calcPrice() {
	//var strBelongto = $("#strBelongto").val();
	var res01 = $("#res01").val();
	var productWeight = $("#productWeight").val();
	var productVolume = $("#productVolume").val();
	var strIncamount = $("#strIncamount").val();
	if(res01 == "") {
		alert("终点信息为空，不能计算快递费用！");
		$("#res01").focus();
		return;
	}
	if(productWeight == "" && productVolume == "") {
		alert("请输入货物重量或体积！");
		$("#productWeight").focus();
		return;
	}
	if(productWeight != "" && !isReal(productWeight)) {
		alert("货物重量格式不正确！");
		$("#productWeight").focus();
		return;
	}
	if(productVolume != "" && !isReal(productVolume)) {
		alert("货物体积格式不正确！");
		$("#productVolume").focus();
		return;
	}
	//开始计算
	var param = new Object();
	param.strWeight = productWeight;
	param.strCube = productVolume;
	param.strIncamount = strIncamount;
	$("#weightprice_body").empty();
	$("#cubeprice_body").empty();
	$.getJSON('<%=request.getContextPath()%>/assess/calcAssessExpressFeeAction.action', param, function(data) {
		if(data.code == 0) {
			//重量
			var items = data.data;
			$.each(items, function(i, n) {
				var html = '';
				html += '<tr>';
				html += '	<td><input name="radioKey" type="radio" value='+ n.deliveryid + "@@" + n.deliveryprice + "@@" + n.expressincamount + '></input></td>';
				html += '	<td>' + (i + 1) + '</td>';
				html += '	<td>' + n.deliveryname + '</td>';
				html += '	<td>' + n.unitprice + '</td>';
				html += '	<td>' + n.expressincamount + '</td>';
				html += '	<td>' + n.deliveryprice + '</td>';
				html += '	<td>' + '</td>';
				html += '</tr>';
				$("#weightprice_body").append(html);
			});
			if(data.minWeight != "") {
				$("#weightPriceDetail").html("最优合计：" + data.minWeight + "元");
			} else {
				$("#weightPriceDetail").html("");
			}
			
			//体积
			var items1 = data.data1;
			$.each(items1, function(i, n) {
				var html = '';
				html += '<tr>';
				html += '	<td><input name="radioKey" type="radio" value='+ n.deliveryid + "@@" + n.deliveryprice + "@@" + n.expressincamount + '></input></td>';
				html += '	<td>' + (i + 1) + '</td>';
				html += '	<td>' + n.deliveryname + '</td>';
				html += '	<td>' + n.unitprice + '</td>';
				html += '	<td>' + n.expressincamount + '</td>';
				html += '	<td>' + n.deliveryprice + '</td>';
				html += '	<td>' + '</td>';
				html += '</tr>';
				$("#cubeprice_body").append(html);
			});
			if(data.minCube != "") {
				$("#cubePriceDetail").html("最优合计：" + data.minCube + "元");
			} else {
				$("#cubePriceDetail").html("");
			}
		} else {
			alert(data.msg);
		}
	});
}

function savePrice() {
	
	var expressno = $("#expressno").val();
	var receiptdate = $("#receiptdate").val();
	var delivery = getSelectedDelivery();
	var id, deliveryprice;
	var expressincamount;
	if(delivery == "") {
		alert("请选择一条记录！");
		return;
	} else {
		//var idx = delivery.indexOf("@@");
		var idx = delivery.split("@@");
		if (idx.length >= 3){
			//id = delivery.substring(0, idx);
			//deliveryprice = delivery.substring(idx + 2, delivery.length);
			id = idx[0];
			deliveryprice = idx[1];
			expressincamount = idx[2];
			alert("\n快递公司号：" + id + "\n快递单号: " +  expressno + "\n单据日期: " + receiptdate  + "\n合计金额: " + deliveryprice + "\n保费金额: " + expressincamount);
			document.mainform.action = "<%=request.getContextPath()%>/assess/saveAssessExpressFeeAction.action?deliveryid="+id+"&&expressno="+expressno+"&&receiptdate=" +receiptdate+"&&deliveryprice="+deliveryprice+"&&expressincamount="+expressincamount;
			document.mainform.submit();
		}
	}
}

function getSelectedDelivery() {
	var id = "";
	var list = document.getElementsByName("radioKey");
	for(var i = 0; i < list.length; i++) {
		if(list[i].checked) {
			id = list[i].value;
			break;
		}
	}
	return id;
}


</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<s:hidden name="strCustomerId" id="strCustomerId"></s:hidden>
	<s:hidden name="expresscost" id="expresscost"></s:hidden>
	<div style="position:absolute; margin-left: 150px; margin-top: 1px; text-align: center; color: red;">
		<s:actionmessage />
	</div>	
	<div id="container" style="width: 100%; height: 100%;">
		<table class="info_tab" width="100%" border="0" cellpadding="5" cellspacing="0">
			<tr>
				<td width="150">　 </td>
				<td width="150">　 </td>
				<td width="150">　 </td>
				<td >　 </td>
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
						<s:textfield name="strBelongto" id="strBelongto" disabled="true" cssStyle="width:60px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
				<td align="right">终点：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="showCustomerDto.res01" id="res01" disabled="true" cssStyle="width:60px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
				<td>
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="关闭" onclick="window.close();"/>
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right">实重：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<input type="text" id="productWeight" style="width:60px;" maxlength="32"/>
					</div>
					<div class="box1_right"></div>
					<label style="line-height: 35px;">Kg</label>
				</td>
				<td align="right">材积：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<input type="text" id="productVolume" style="width:60px;" maxlength="32"/>
					</div>
					<div class="box1_right"></div>
					<label style="line-height: 35px;">m³</label>
				</td>
				<td colspan="4">
					<div class="btn" style="margin-left: 0px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="评估" onclick="calcPrice();"/>
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right">快递单号：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<input type="text" id="expressno" style="width:120px;" maxlength="50"/>
					</div>
					<div class="box1_right"></div>
				</td>
				<td align="right">单据日期：</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center date_input">
						<input type="text" id="receiptdate" disabled="disabled" style="width:105px;" value="<s:date format="yyyy-MM-dd" name="receiptdate"/>" />
						<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('receiptdate'));"></a>
					</div>
					<div class="box1_right"></div>
				</td>
				<td colspan="4">
					<div class="btn" style="margin-left: 0px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="提交" onclick="savePrice();"/>
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right">保价金额：</td>
				<td colspan="4">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="strIncamount" id="strIncamount" cssStyle="width:60px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
		</table>
		<div class="tittle" style="width: 600px;">
			<table width="100%" border="1" cellpadding="5" cellspacing="0" style="height: 100%;">
				<tr>
					<td id="weightDiv" style="cursor: pointer; width:250px; background:#6699CC;" width="300" onclick="showWeightPriceList();" onmouseout="changeStyle(this, 0);" onmouseover="changeStyle(this, 1);">
						<div>
							实重计费
						</div>
						<div id="weightPriceDetail" style="float: right; line-height: 0px; vertical-align: middle; color: red; margin-top: -5px;">
						</div>
					</td>
					<td id="cubeDiv" style="cursor: pointer; width:250px;" width="300" onclick="showCubePriceList();" onmouseout="changeStyle(this, 0);" onmouseover="changeStyle(this, 1);">
						<div>
							材积计费
						</div>
						<div id="cubePriceDetail" style="float: right; line-height: 0px; vertical-align: middle; color: red; margin-top: -5px;">
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="data_table" style="padding:0px; margin-top: 0px;">
			<div class="tab_tittle">
				<table width="100%" border="1" cellpadding="5" cellspacing="0">
				</table>
			</div>
			<div class="tab_content">
				<table id="weightprice_tab" class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<thead>
						<tr class="tittle">
							<td width="80"></td>
							<td width="80">#</td>
							<td width="200">快递公司</td>
							<td width="120">单价</td>
							<td width="120">保费</td>
							<td width="120">合计（元）</td>
							<td width="180">备考</td>
						</tr>
					</thead>
					<tbody id="weightprice_body">
					</tbody>
				</table>
				<table id="cubeprice_tab" style="display: none;" class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<thead>
						<tr class="tittle">
							<td width="80"></td>
							<td width="80">#</td>
							<td width="200">快递公司</td>
							<td width="120">单价</td>
							<td width="120">保费</td>
							<td width="120">合计（元）</td>
							<td width="180">备考</td>
						</tr>
					</thead>
					<tbody id="cubeprice_body">
					</tbody>
				</table>
			</div>
		</div>
<!-- 		<div class="btns" style="margin-top:40px; margin-left: 0px;">
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
		</div> -->
	</div>
</s:form>
</body>
</html>
