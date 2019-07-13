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
<title>快递信息输入</title>
<script type="text/javascript">
	function add() {
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
	
		var id = $("#id").val().trim();
		var deliveryname = $("#deliveryname").val().trim();
		var tmpnote = $("#tmpnote").val();
		$("#note").attr("value", tmpnote);
/*		if(id == "") {
			alert("快递代码不能为空！");
			$("#id").focus();
			return;
		}
*/		
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
*/				
/*				if(deliveryaddress.trim() == "") {
					alert("联系人" + i + "地址不能为空！");
					$("#" + "deliveryaddress" + i).focus();
					return;
				}
*/
/*				if(deliveryfax.trim() == "") {
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
		} */

		if(tmpnote.length > 100) {
			alert("备注不能超过100个字！");
			$("#tmpnote").focus();
			return;
		}
		
		if(!setPriceItemList()) {
			return false;
		}
		document.mainform.action = '<c:url value="/delivery/addEtbDeliveryAction.action"></c:url>';
		document.mainform.submit();
	}
	
	//单价列表
	function setPriceItemList() {
		$("#priceItemTable").empty();
		var rows = document.getElementById("priceData").rows;
		var priceList = "";
		for(var i = 0; i < rows.length; i++) {
			var marketcitylist = rows[i].cells[2].getElementsByTagName("input");
			var marketcity = marketcitylist[0].value;
			var arrivalcitylist = rows[i].cells[4].getElementsByTagName("input");
			var arrivalcity = arrivalcitylist[0].value;
			var pricekglist = rows[i].cells[5].getElementsByTagName("input");
			var pricekg = pricekglist[0].value;
			var pricem3list = rows[i].cells[7].getElementsByTagName("input");
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
			
			td.appendChild(createInput("addPriceItemList[" + i + "].marketcity", marketcity));
			td.appendChild(createInput("addPriceItemList[" + i + "].arrivalcity", arrivalcity));
			td.appendChild(createInput("addPriceItemList[" + i + "].pricekg", pricekg));
			td.appendChild(createInput("addPriceItemList[" + i + "].pricem3", pricem3));
			
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
	function addPrice() {
		//验证该产品是否在产品列表中
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
		
		//出发城市
		td = createTdInput("marketcity", "120", "20", "");
		tr.appendChild(td);
		//~
		td = document.createElement("td");
		td.appendChild(document.createTextNode("~"));
		tr.appendChild(td);
		//到达城市
		td = createTdInput("arrivalcity", "120", "20", "");
		tr.appendChild(td);
		//重量单价
		td = createTdInput("pricekg", "200", "40", "");
		tr.appendChild(td);
		//（/Kg）
		td = document.createElement("td");
		td.appendChild(document.createTextNode("（/Kg）"));
		tr.appendChild(td);
		//体积单价
		td = createTdInput("pricem3", "200", "40", "");
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
	}
	
	function createTdInput(name, wid, maxlength, onblurevent) {
		var td = document.createElement("td");
		var input = document.createElement("input");
		input.id = name;
		input.style.width = wid + "px";
		input.setAttribute("maxlength", maxlength);
		input.type = "text";
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
					快递信息输入
				</div>
				<div class="tittle_right">
				</div>
			</div>
		</div>
		<s:form id="mainform" name="mainform" method="POST">
			<s:hidden name="addDeliveryDto.id" id="id"></s:hidden >
			<s:hidden name="addDeliveryDto.note" id="note"></s:hidden>
			<s:hidden name="addDeliveryDto.deliverymail1" id="deliverymail1"></s:hidden>
			<s:hidden name="addDeliveryDto.deliverymail2" id="deliverymail2"></s:hidden>
			<s:hidden name="addDeliveryDto.deliverymail3" id="deliverymail3"></s:hidden>
			<s:hidden name="addDeliveryDto.deliverymail4" id="deliverymail4"></s:hidden>
			<s:hidden name="addDeliveryDto.deliverymail5" id="deliverymail5"></s:hidden>
		<div style="position:absolute; margin-left: 150px; margin-top: 10px; text-align: center; color: red;">
			<s:actionmessage />
		</div>
		<table id="priceItemTable" style="display: none;">
		</table>
		<table style="margin-left: 50px; margin-top: 30px;" border="0" cellspacing="15" cellpadding="0">
			<tr>
				<td width="60"><font color="red">*</font>快递名称</td>
				<td width="500">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="addDeliveryDto.deliveryname" id="deliveryname" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverymanager1" id="deliverymanager1" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverytel1" id="deliverytel1" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addDeliveryDto.mail_pr1" id="mail_pr1" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addDeliveryDto.mail_suffix1" id="mail_suffix1" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryaddress1" id="deliveryaddress1" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryfax1" id="deliveryfax1" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverymanager2" id="deliverymanager2" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverytel2" id="deliverytel2" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addDeliveryDto.mail_pr2" id="mail_pr2" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addDeliveryDto.mail_suffix2" id="mail_suffix2" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryaddress2" id="deliveryaddress2" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryfax2" id="deliveryfax2" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverymanager3" id="deliverymanager3" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverytel3" id="deliverytel3" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addDeliveryDto.mail_pr3" id="mail_pr3" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addDeliveryDto.mail_suffix3" id="mail_suffix3" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryaddress3" id="deliveryaddress3" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryfax3" id="deliveryfax3" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverymanager4" id="deliverymanager4" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverytel4" id="deliverytel4" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addDeliveryDto.mail_pr4" id="mail_pr4" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addDeliveryDto.mail_suffix4" id="mail_suffix4" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryaddress4" id="deliveryaddress4" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryfax4" id="deliveryfax4" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverymanager5" id="deliverymanager5" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliverytel5" id="deliverytel5" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addDeliveryDto.mail_pr5" id="mail_pr5" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addDeliveryDto.mail_suffix5" id="mail_suffix5" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryaddress5" id="deliveryaddress5" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addDeliveryDto.deliveryfax5" id="deliveryfax5" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
					<div class="tab_content" style="height: 205px;">
						<table class="trade_tab" width="100%" border="0">
							<tr>
								<td colspan="9"><strong>单价信息</strong></td>
							</tr>
							<tr>
								<td style="width: 0px; display: none;"></td>
								<td></td>
								<td align="center">出发城市</td>
								<td></td>
								<td align="center">到达城市</td>
								<td align="center">重量单价（/Kg）</td>
								<td></td>
								<td align="center">体积单价（/M^3）</td>
								<td></td>
							</tr>
							<tbody id="priceData">
								<s:iterator id="addPriceItemList" value="addPriceItemList" status="st1">
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
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" id="marketcity" style="width:120px;" maxlength="20" value="<s:property value="marketcity"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td>~</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" id="arrivalcity" style="width:120px;" maxlength="20" value="<s:property value="arrivalcity"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" id="pricekg" style="width:200px;" maxlength="20" value="<s:property value="pricekg"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td>（/Kg）</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" id="pricem3" style="width:200px;" maxlength="20" value="<s:property value="pricem3"/>" />
											</div>
											<div class="box1_right"></div>
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
										<input class="input80" type="button" onclick="addPrice();" value="新增" />
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
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<textarea name="" id="tmpnote" cols="" rows="5" style="width:350px;"><s:property value="addDeliveryDto.note"/></textarea>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="追加" onclick="add();"/>
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
