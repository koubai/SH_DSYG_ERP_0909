<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>快递信息修改</title>
<script type="text/javascript">
	function upd() {
		//初期化邮箱
		for(var i = 1; i <= 5; i++) {
			//邮箱
			var mail_pr = $("#" + "mail_pr" + i).val();
			var mail_suffix = $("#" + "mail_suffix" + i).val();
			if(mail_pr != "" || mail_suffix != "") {
				$("#" + "deliverymail" + i).attr("value", mail_pr + "@" + mail_suffix);
			} else {
				$("#" + "deliverymail" + i).attr("value", "");
			}
		}
		var deliveryname = $("#deliveryname").val().trim();
		var tmpnote = $("#tmpnote").val();
		$("#note").attr("value", tmpnote);
		
		if($("#assessChk").attr("checked")) {
			$("#res02").attr("value", "1");
		} else {
			$("#res02").attr("value", "0");
		}
		//保价比例
		var res03 = $("#res03").val().trim();
		if(res03 == "") {
			$("#res03").attr("value", "0");
		}
		
		if(deliveryname == "") {
			alert("快递名称不能为空！");
			$("#deliveryname").focus();
			return;
		}
		
		var deliverymanager1 = $("#deliverymanager1").val();
		if(deliverymanager1.trim() == "") {
			alert("联系人1不能为空！");
			$("#deliverymanager1").focus();
			return;
		}

		//联系人信息check
		var all = "";
		for(var i = 1; i <= 5; i++) {
			var deliverymanager = $("#" + "deliverymanager" + i).val();
			var deliverytel = $("#" + "deliverytel" + i).val();
			var deliveryaddress = $("#" + "deliveryaddress" + i).val();
			var deliverymail = $("#" + "deliverymail" + i).val();
			var deliveryfax = $("#" + "deliveryfax" + i).val();
			all += deliverymanager;
			if(deliverymanager.trim() != "") {
				if(deliverymanager.length > 6) {
					alert("联系人" + i + "姓名不能超过6个字！");
					$("#" + "deliverymanager" + i).focus();
					return;
				}
				if(deliverytel.trim() == "") {
					alert("联系人" + i + "电话不能为空！");
					$("#" + "deliverytel" + i).focus();
					return;
				}
				//邮箱
/*				if(deliverymail.trim() == "") {
					alert("联系人" + i + "邮箱不能为空！");
					$("#" + "mail_pr" + i).focus();
					return;
				}
				if(deliveryaddress.trim() == "") {
					alert("联系人" + i + "地址不能为空！");
					$("#" + "deliveryaddress" + i).focus();
					return;
				}
				if(deliveryfax.trim() == "") {
					alert("联系人" + i + "传真不能为空！");
					$("#" + "deliveryfax" + i).focus();
					return;
				}
*/				
			}
			if(deliverymail != "" && !isMail(deliverymail)) {
				alert("联系人" + i + "邮箱格式不正确！");
				$("#" + "mail_pr" + i).focus();
				return;
			}
		}
		if(all == "") {
			alert("联系人不能为空！");
			$("#deliverymanager1").focus();
			return;
		}
		//快递单价（/Kg）
		/* var areaprice01 = $("#areaprice01").val().trim();
		if (areaprice01 != "" && !isReal(areaprice01)) {
			alert("区域1单价必须为大于0的实数！");
			$("#areaprice01").focus();
			return;
		}
		var areaprice02 = $("#areaprice02").val().trim();
		if (areaprice02 != "" && !isReal(areaprice02)) {
			alert("区域2单价必须为大于0的实数！");
			$("#areaprice02").focus();
			return;
		}
		var areaprice03 = $("#areaprice03").val().trim();
		if (areaprice03 != "" && !isReal(areaprice03)) {
			alert("区域3单价必须为大于0的实数！");
			$("#areaprice03").focus();
			return;
		}
		var areaprice04 = $("#areaprice04").val().trim();
		if (areaprice04 != "" && !isReal(areaprice04)) {
			alert("区域4单价必须为大于0的实数！");
			$("#areaprice04").focus();
			return;
		}
		var areaprice05 = $("#areaprice05").val().trim();
		if (areaprice05 != "" && !isReal(areaprice05)) {
			alert("区域5单价必须为大于0的实数！");
			$("#areaprice05").focus();
			return;
		}
		$("#res01").attr("value", areaprice01 + ";" + areaprice02 + ";" + areaprice03 + ";" + areaprice04 + ";" + areaprice05 + ";"); */

		if(tmpnote.length > 100) {
			alert("备注不能超过100个字！");
			$("#tmpnote").focus();
			return;
		}
		
		if(!setPriceItemList()) {
			return false;
		}
		document.mainform.action = '<c:url value="/delivery/updEtbDeliveryAction.action"></c:url>';
		document.mainform.submit();
	}
	
	//单价列表
	function setPriceItemList() {
		$("#priceItemTable").empty();
		var rows = document.getElementById("priceData").rows;
		var priceList = "";
		for(var i = 0; i < rows.length; i++) {
			var marketcitylist = rows[i].cells[3].getElementsByTagName("input");
			var marketcity = marketcitylist[0].value;
			var arrivalcitylist = rows[i].cells[5].getElementsByTagName("input");
			var arrivalcity = arrivalcitylist[0].value;
			var pricekglist = rows[i].cells[6].getElementsByTagName("input");
			var pricekg = pricekglist[0].value;
			var pricem3list = rows[i].cells[8].getElementsByTagName("input");
			var pricem3 = pricem3list[0].value;
			var currentPrice = marketcity+arrivalcity;
			
			var tr = document.createElement("tr");
			//单价列表
			var td = document.createElement("td");
			
			if(priceList != ""){
				var ll = priceList.split(",");
				var newPricelist = "";
				for(var j = 0; j < ll.length; j++) {
					if(ll[j] == currentPrice) {
						alert(marketcity + "到"+ arrivalcity +"单价信息有重复！");
						return false;
					}
				}
			}
			priceList += currentPrice + ",";
			
			//出发城市check
			if(marketcity == "") {
				alert("出发城市不能为空！");
				$("#" + marketcity).focus();
				return false;
			}
			
			//到达城市check
			if(arrivalcity == "") {
				alert("到达城市不能为空！");
				$("#" + arrivalcity).focus();
				return false;
			}
			
			if(pricekg == ""){
				pricekg = 0;
			}
			
			if(pricem3 == ""){
				pricem3 = 0;
			}
			
			td.appendChild(createInput("tmpUpdPriceItemList[" + i + "].marketcity", marketcity));
			td.appendChild(createInput("tmpUpdPriceItemList[" + i + "].arrivalcity", arrivalcity));
			td.appendChild(createInput("tmpUpdPriceItemList[" + i + "].pricekg", pricekg));
			td.appendChild(createInput("tmpUpdPriceItemList[" + i + "].pricem3", pricem3));
			
			tr.appendChild(td);
			document.getElementById("priceItemTable").appendChild(tr);
		}
		return true;
	}
	
	function createInput(id, value) {
		var input = document.createElement("input");
		input.type = "text";
		input.name = id;
		input.value = value;
		return input;
	}
	
	//添加记录
	function addPrice(belongTo) {
		if(belongTo == 0){
			belongTo = "上海";
		} else {
			belongTo = "深圳";
		}
		var td0 = document.createElement("td");
		td0.style.display = "none";
		var tr = document.createElement("tr");
		tr.appendChild(td0);
		var td = document.createElement("td");
		//单选框
		var radio = document.createElement("input");
		radio.name = "itemRadio";
		radio.type = "radio";
		radio.style.width = "10px";
		td.appendChild(radio);
		tr.appendChild(td);
		//序号
		var rows = document.getElementById("priceData").rows;
		var seq = rows.length + 1;
		td = document.createElement("td");
		td.appendChild(document.createTextNode(seq));
		td.align = "center";
		tr.appendChild(td);
		
		//出发城市
		td = createTdInput("marketcity", "120", "20", "", belongTo);
		tr.appendChild(td);
		//~
		td = document.createElement("td");
		td.appendChild(document.createTextNode("~"));
		tr.appendChild(td);
		//到达城市
		td = createTdInput("arrivalcity", "120", "20", "", "");
		tr.appendChild(td);
		//重量单价
		td = createTdInput("pricekg", "200", "40", "", "");
		tr.appendChild(td);
		//（/Kg）
		td = document.createElement("td");
		td.appendChild(document.createTextNode("（/Kg）"));
		tr.appendChild(td);
		//体积单价
		td = createTdInput("pricem3", "200", "40", "", "");
		tr.appendChild(td);
		//（/M^3）
		td = document.createElement("td");
		td.appendChild(document.createTextNode("（/M^3）"));
		tr.appendChild(td);
		
		document.getElementById("priceData").appendChild(tr);
	}
	
	function delPrice() {
		var list = document.getElementsByName("itemRadio");
		var currentPrice = "";
		for(var i = 0; i < list.length; i++) {
			if(list[i].checked) {
				if(confirm("确定删除该记录吗？")) {
					var tr = list[i].parentNode.parentNode;
					var tbody = list[i].parentNode.parentNode.parentNode;
					tbody.removeChild(tr);
					break;
				} else {
					return;
				}
			}
		}
		refreshItemData();
	}
	
	//刷新序号
	function refreshItemData() {
		var rows = document.getElementById("priceData").rows;
		for(var i = 0; i < rows.length; i++) {
			var num = i + 1;
			rows[i].cells[2].innerHTML = num;
		}
	}
	
	function createTdInput(name, wid, maxlength, onblurevent, value) {
		var td = document.createElement("td");
		var input = document.createElement("input");
		input.id = name;
		input.style.width = wid + "px";
		input.setAttribute("maxlength", maxlength);
		input.type = "text";
		input.value = value;
		if(onblurevent != "") {
			input.setAttribute("onblur", onblurevent); 
		}
		td.appendChild(input);
		return td;
	}
	
	function golist() {
		document.mainform.action = '<c:url value="/delivery/queryEtbDeliveryList.action"></c:url>';
		document.mainform.submit();
	};

</script>
<base target="_self"/>
</head>
<body>
	<div id="containermain">
		<div class="content">
			<div class="tittle">
				<div class="icons"></div>
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px;">
					快递信息修改
				</div>
				<div class="tittle_right">
				</div>
			</div>
		</div>
		<s:form id="mainform" name="mainform" method="POST">
			<s:hidden name="updateDeliveryDto.note" id="note"></s:hidden>
			<s:hidden name="updateDeliveryDto.deliverymail1" id="deliverymail1"></s:hidden>
			<s:hidden name="updateDeliveryDto.deliverymail2" id="deliverymail2"></s:hidden>
			<s:hidden name="updateDeliveryDto.deliverymail3" id="deliverymail3"></s:hidden>
			<s:hidden name="updateDeliveryDto.deliverymail4" id="deliverymail4"></s:hidden>
			<s:hidden name="updateDeliveryDto.deliverymail5" id="deliverymail5"></s:hidden>
			<s:hidden name="updateDeliveryDto.res02" id="res02"></s:hidden>
		<div style="position:absolute; margin-left: 150px; margin-top: 10px; text-align: center; color: red;">
			<s:actionmessage />
		</div>
		<table id="priceItemTable" style="display: none;">
		</table>
		<table style="margin-left: 50px; margin-top: 30px;" border="0" cellspacing="15" cellpadding="0">
			<tr>
				<td width="120"><font color="red">*</font>快递代码</td>
				<td width="500">&nbsp;<s:property value="updateDeliveryDto.id"/></td>
			</tr>
			<tr>
				<td><font color="red">*</font>快递名称</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateDeliveryDto.deliveryname" id="deliveryname" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">
							<tr>
								<td align="left" width="15%"><strong>联系人1</strong></td>
								<td><font color="red">*</font>联系人1姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverymanager1" id="deliverymanager1" cssStyle="width:300px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>联系人1电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverytel1" id="deliverytel1" cssStyle="width:300px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;联系人1邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_pr1" id="mail_pr1" cssStyle="width:141px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_suffix1" id="mail_suffix1" cssStyle="width:141px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;联系人1地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryaddress1" id="deliveryaddress1" cssStyle="width:300px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;联系人1传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryfax1" id="deliveryfax1" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">
							<tr>
								<td align="left" width="15%"><strong>联系人2</strong></td>
								<td>联系人2姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverymanager2" id="deliverymanager2" cssStyle="width:300px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人2电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverytel2" id="deliverytel2" cssStyle="width:300px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人2邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_pr2" id="mail_pr2" cssStyle="width:141px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_suffix2" id="mail_suffix2" cssStyle="width:141px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人2地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryaddress2" id="deliveryaddress2" cssStyle="width:300px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人2传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryfax2" id="deliveryfax2" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">
							<tr>
								<td align="left" width="15%"><strong>联系人3</strong></td>
								<td>联系人3姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverymanager3" id="deliverymanager3" cssStyle="width:300px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人3电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverytel3" id="deliverytel3" cssStyle="width:300px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人3邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_pr3" id="mail_pr3" cssStyle="width:141px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_suffix3" id="mail_suffix3" cssStyle="width:141px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人3地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryaddress3" id="deliveryaddress3" cssStyle="width:300px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人3传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryfax3" id="deliveryfax3" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">
							<tr>
								<td align="left" width="15%"><strong>联系人4</strong></td>
								<td>联系人4姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverymanager4" id="deliverymanager4" cssStyle="width:300px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人4电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverytel4" id="deliverytel4" cssStyle="width:300px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人4邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_pr4" id="mail_pr4" cssStyle="width:141px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_suffix4" id="mail_suffix4" cssStyle="width:141px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人4地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryaddress4" id="deliveryaddress4" cssStyle="width:300px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人4传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryfax4" id="deliveryfax4" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">
							<tr>
								<td align="left" width="15%"><strong>联系人5</strong></td>
								<td>联系人5姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverymanager5" id="deliverymanager5" cssStyle="width:300px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人5电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliverytel5" id="deliverytel5" cssStyle="width:300px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人5邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_pr5" id="mail_pr5" cssStyle="width:141px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateDeliveryDto.mail_suffix5" id="mail_suffix5" cssStyle="width:141px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人5地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryaddress5" id="deliveryaddress5" cssStyle="width:300px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>联系人5传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateDeliveryDto.deliveryfax5" id="deliveryfax5" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<%-- <tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">		
							<tr>
								<td>区域1单价（/Kg）</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="areaprice01" id="areaprice01" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>区域2单价（/Kg）</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="areaprice02" id="areaprice02" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>区域3单价（/Kg）</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="areaprice03" id="areaprice03" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>区域4单价（/Kg）</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="areaprice04" id="areaprice04" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>区域5单价（/Kg）</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="areaprice05" id="areaprice05" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>		
					</div>
				</td>
			</tr> --%>
			<tr>
				<td>保价比例</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateDeliveryDto.res03" id="res03" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="tab_content" style="height: 205px;">
						<table class="trade_tab" width="100%" border="0">
							<tr>
								<td colspan="9"><strong>单价信息</strong></td>
							</tr>
							<tr>
								<td style="width: 0px; display: none;"></td>
								<td></td>
								<td align="center">序号</td>
								<td align="center">出发城市</td>
								<td></td>
								<td align="center">到达城市</td>
								<td align="center">重量单价（/Kg）</td>
								<td></td>
								<td align="center">体积单价（/M^3）</td>
								<td></td>
							</tr>
							<tbody id="priceData">
								<s:iterator id="updPriceItemList" value="updPriceItemList" status="st1">
									<tr>
										<td style="width: 0px; display: none;">
											<input type="hidden" value="<s:property value="id"/>" />
											<input type="hidden" value="<s:property value="deliveryid"/>" />
											<input type="hidden" value="<s:property value="marketcity"/>" />
											<input type="hidden" value="<s:property value="arrivalcity"/>" />
											<input type="hidden" value="<s:property value="pricekg"/>" />
											<input type="hidden" value="<s:property value="pricem3"/>" />
										</td>
										<td><input name="itemRadio" type="radio" style="width:10px"/></td>
										<td align="center"><s:property value="#st1.index + 1"/></td>
										<td>
											<input type="text" id="marketcity" style="width:120px;" maxlength="20" value="<s:property value="marketcity"/>" />
										</td>
										<td>~</td>
										<td>
											<input type="text" id="arrivalcity" style="width:120px;" maxlength="20" value="<s:property value="arrivalcity"/>" />
										</td>
										<td>
											<input type="text" id="pricekg" style="width:200px;" maxlength="20" value="<s:property value="pricekg"/>" />
										</td>
										<td>（/Kg）</td>
										<td>
											<input type="text" id="pricem3" style="width:200px;" maxlength="20" value="<s:property value="pricem3"/>" />
										</td>
										<td>（/M^3）</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>		
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table cellpadding="10" style="margin:0 auto;">
						<tr>
							<td>
								<div class="btn1">
									<div class="btn1_left"></div>
									<div class="btn1_center">
										<input class="input80" type="button" onclick="addPrice(<s:property value="belongTo"/>);" value="新增" />
									</div>
									<div class="btn1_right"></div>
								</div>
							</td>
							<td>
								<div class="btn1">
									<div class="btn1_left"></div>
									<div class="btn1_center">
										<input class="input80" type="button" onclick="delPrice();" value="删除" />
									</div>
									<div class="btn1_right"></div>
								</div>
							</td>
							<td>
								<s:if test='updateDeliveryDto.res02 != null && updateDeliveryDto.res02 == "0"'>
									<input type="checkbox" id="assessChk" value="0">参与运费评估</input>
								</s:if>
								<s:else>
									<input type="checkbox" id="assessChk" checked="checked" value="1">参与运费评估</input>
								</s:else>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<textarea name="" id="tmpnote" cols="" rows="5" style="width:350px;"><s:property value="updateDeliveryDto.note"/></textarea>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="修改" onclick="upd();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="关闭" onclick="golist();"/>
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
			</tr>
		</table>
	</s:form>
	</div>
</body>
</html>
