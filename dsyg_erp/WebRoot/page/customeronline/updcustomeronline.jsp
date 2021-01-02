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
<title>线上客户信息修改</title>
<script type="text/javascript">
	function upd() {
		//用友账套
		$("#updateCustomerOnlineDto.res03").attr("value", $("#tmpRes03").val());

		var customeremail = $("#customeremail").val().trim();
		if(customeremail == "") {
			alert("客户邮件地址不能为空！");
			$("#customeremail").focus();
			return;
		}
		
		if(!isMail(customeremail)) {
			alert("客户邮件地址格式不正确！");
			$("#customeremail").focus();
			return;
		}

		/* var password = $("#password").val().trim();
		//确认密码
		var repassword = $("#repassword").val().trim();
		if(password == "") {
			alert("密码不能为空！");
			$("#password").focus();
			return;
		}
		
		if(password != repassword) {
			alert("两次密码不一致！");
			$("#repassword").focus();
			return;
		} */
	
		var companycn = $("#companycn").val().trim();
		if(companycn == "") {
			alert("购买方公司名不能为空！");
			$("#companycn").focus();
			return;
		}
	
		var companyen = $("#companyen").val().trim();
/*		if(companyen == "") {
			alert("购买方英文公司名或拼音不能为空！");
			$("#companyen").focus();
			return;
		}
*/

		var name = $("#name").val();
		if(name.trim() == "") {
			alert("购买方姓名不能为空！");
			$("#name").focus();
			return;
		}
		
		var postcode = $("#postcode").val();
		if(postcode.trim() == "") {
			alert("购买方邮编不能为空！");
			$("#postcode").focus();
			return;
		}
		
		var address = $("#address").val();
		if(address.trim() == "") {
			alert("购买方地址不能为空！");
			$("#address").focus();
			return;
		}
		
		var tell = $("#tell").val();
		if(tell.trim() == "") {
			alert("购买方电话号码不能为空！");
			$("#tell").focus();
			return;
		}
		
		//收货人信息同上
		if($("#tmpInfo").attr("checked")) {
			$("#infoflag").val(1);
		} else {
			$("#infoflag").val(0);
		}
	
		var companycn2 = $("#companycn2").val().trim();
		if(companycn2 == "") {
			alert("收货人公司名不能为空！");
			$("#companycn2").focus();
			return;
		}
	
		var companyen2 = $("#companyen2").val().trim();
/*		if(companyen2 == "") {
			alert("收货人英文公司名或拼音不能为空！");
			$("#companyen2").focus();
			return;
		}
*/

		var name2 = $("#name2").val();
		if(name2.trim() == "") {
			alert("收货人姓名不能为空！");
			$("#name2").focus();
			return;
		}
		
		var postcode2 = $("#postcode2").val();
		if(postcode2.trim() == "") {
			alert("收货人邮编不能为空！");
			$("#postcode2").focus();
			return;
		}
		
		var address2 = $("#address2").val();
		if(address2.trim() == "") {
			alert("收货人地址不能为空！");
			$("#address2").focus();
			return;
		}
		
		var tell2 = $("#tell2").val();
		if(tell2.trim() == "") {
			alert("收货人电话号码不能为空！");
			$("#tell2").focus();
			return;
		}
		
		//交货方法
		var list = document.getElementsByName("tmpPaytype");
		var v = 0;
		for(var j = 0; j < list.length; j++) {
			if(list[j].checked) {
				v = list[j].value;
				break;
			}
		}
		$("#paytype").val(v);
		
		var paytype = $("#paytype").val().trim();
		if(paytype == "0") {
			alert("请选择交货方法！");
			$("#tmpPaytype").focus();
			return;
		}
		
		//企业
		var listA = document.getElementsByName("tmpAccounttype");
		var va = 0;
		for(var j = 0; j < listA.length; j++) {
			if(listA[j].checked) {
				va = listA[j].value;
				break;
			}
		}
		$("#accounttype").val(va);
		
		//发票
		var listR = document.getElementsByName("tmpReceipttype");
		var vr = 0;
		for(var j = 0; j < listR.length; j++) {
			if(listR[j].checked) {
				vr = listR[j].value;
				break;
			}
		}
		$("#receipttype").val(vr);

		var accounttype = $("#accounttype").val().trim();
		var companytax = $("#companytax").val().trim();
		var accountbank = $("#accountbank").val().trim();
		var accountid = $("#accountid").val().trim();
		var receipttype = $("#receipttype").val().trim();
		if(accounttype == "1"){
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
			}
			if(receipttype == "0") {
				alert("发票类型不能为空！");
				$("#tmpReceipttype").focus();
				return;
			}
		} else if(accounttype == "0"){
			if(companytax != "" || accountbank!= "" || accountid != "" || receipttype!= "0"){
				alert("开户银行企业类型不能为空！");
				$("#tmpAccounttype").focus();
				return;
			}
		}
		
		$("#companycn2").removeAttr("disabled"); 
		$("#companyen2").removeAttr("disabled"); 
		$("#department2").removeAttr("disabled"); 
		$("#name2").removeAttr("disabled"); 
		$("#postcode2").removeAttr("disabled"); 
		$("#address2").removeAttr("disabled"); 
		$("#tell2").removeAttr("disabled");
		$("#companytax").removeAttr("disabled");
		$("#accountbank").removeAttr("disabled");
		$("#accountid").removeAttr("disabled");
		
		document.mainform.action = '<c:url value="/customeronline/updCustomerOnlineAction.action"></c:url>';
		document.mainform.submit();
	}
	
	function golist() {
		document.mainform.action = '<c:url value="/customeronline/queryCustomerOnlineList.action"></c:url>';
		document.mainform.submit();
	}
	
	function changeInfo(obj) {
		if(obj.checked) {
			$("#companycn2").attr("disabled", true);
			$("#companyen2").attr("disabled", true);
			$("#department2").attr("disabled", true);
			$("#name2").attr("disabled", true);
			$("#postcode2").attr("disabled", true);
			$("#address2").attr("disabled", true);
			$("#tell2").attr("disabled", true);
			var companycn = $("#companycn").val().trim();
			$("#companycn2").attr("value", companycn);
			var companyen = $("#companyen").val().trim();
			$("#companyen2").attr("value", companyen);
			var department = $("#department").val().trim();
			$("#department2").attr("value", department);
			var name = $("#name").val().trim();
			$("#name2").attr("value", name);
			var postcode = $("#postcode").val().trim();
			$("#postcode2").attr("value", postcode);
			var address = $("#address").val().trim();
			$("#address2").attr("value", address);
			var tell = $("#tell").val().trim();
			$("#tell2").attr("value", tell);
			
		} else {
			$("#companycn2").attr("disabled", false);
			$("#companyen2").attr("disabled", false);
			$("#department2").attr("disabled", false);
			$("#name2").attr("disabled", false);
			$("#postcode2").attr("disabled", false);
			$("#address2").attr("disabled", false);
			$("#tell2").attr("disabled", false);
		}
	}
	
	function typeCheck(tc){
		if(tc == 2){
			$("#tr_person").show();
			$("#tr_company").hide();
			$("#companytax").attr("disabled", true);
			$("#accountbank").attr("disabled", true);
			$("#accountid").attr("disabled", true);
			$("#tmpReceipttype").attr("disabled", true); 
			$("#tmpReceipttype2").attr("disabled", true); 
			$("#tmpReceipttype").attr("checked", true);
			$("#companytax").attr("value", "");
			$("#accountbank").attr("value", "");
			$("#accountid").attr("value", "");
		} else {
			$("#tr_person").hide();
			$("#tr_company").show();
			$("#companytax").attr("disabled", false);
			$("#accountbank").attr("disabled", false);
			$("#accountid").attr("disabled", false);
			$("#tmpReceipttype").attr("disabled", false); 
			$("#tmpReceipttype2").attr("disabled", false); 
		}
	}
	
	function getRadioValue(name) {
		var id = "";
		var list = document.getElementsByName(name);
		for(var i = 0; i < list.length; i++) {
			if(list[i].checked) {
				id = list[i].value;
				break;
			}
		}
		return id;
	}
	
	function changeAccount_old(obj, type) {
		var accounttype = getRadioValue("tmpAccounttype");	
		if(type == "1") {
			var company = $("#companycn").val().trim();
			$("#td_company").html(company);
		} else if(type == "2"){
			var name = $("#name").val().trim();
			$("#td_name").html(name);
		} else if(type == "3"){
			var address = $("#address").val().trim();
			$("#td_address").html(address);
		} else if(type == "4"){
			var tell = $("#tell").val().trim();
			$("#td_tell").html(tell);
		}
	}
	
	function changeAccount(type, key) {	
		if($("#tmpInfo").attr("checked")) {
			if(type == "both") {
				var value = $("#" + key).val().trim();
				$("#td_" + key).html(value);
				$("#" + key + "2").attr("value", value);
			} else {
				var value = $("#" + key).val().trim();
				$("#" + key + "2").attr("value", value);
			}
		} else {
			if(type == "both") {
				var value = $("#" + key).val().trim();
				$("#td_" + key).html(value);
			}
		}
	}

</script>
</head>
<body>
	<div id="containermain">
		<div class="content">
			<div class="tittle" >
				<div class="icons"></div>
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px;">
					线上客户信息修改
				</div>
				<div class="tittle_right">
				</div>
			</div>
		</div>
		<s:form id="mainform" name="mainform" method="POST">
			<s:hidden name="updateCustomerOnlineDto.customerid" id="customerid"></s:hidden>
				<s:hidden name="updateCustomerOnlineDto.paytype" id="paytype"></s:hidden>
				<s:hidden name="updateCustomerOnlineDto.infoflag" id="infoflag"></s:hidden>
				<s:hidden name="updateCustomerOnlineDto.accounttype" id="accounttype"></s:hidden>
				<s:hidden name="updateCustomerOnlineDto.receipttype" id="receipttype"></s:hidden>
		<div style="position:absolute; margin-left: 150px; margin-top: 10px; text-align: center; color: red;">
			<s:actionmessage />
		</div>
		<table style="margin-left: 50px; margin-top: 30px;" border="0" cellspacing="15" cellpadding="0">
			<tr>
				<td width="150"><font color="red">*</font>客户邮件地址</td>
				<td width="550">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateCustomerOnlineDto.customeremail" id="customeremail" disabled="true" cssStyle="width:350px;" maxlength="64" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<%-- <tr>
				<td width="150"><font color="red">*</font>密码</td>
				<td width="550">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:password name="updateCustomerOnlineDto.password" id="password" cssStyle="width:350px;" maxlength="64" theme="simple"></s:password>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td width="150"><font color="red">*</font>密码（确认）</td>
				<td width="550">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:password name="updateCustomerOnlineDto.repassword" id="repassword" cssStyle="width:350px;" maxlength="64" theme="simple"></s:password>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr> --%>
			<tr>
				<td colspan="2">
					<div class="trade">
						<table class="trade_tab" width="80%" border="0">
							<tr>
								<td align="left" width="15%"><strong>购买方信息</strong></td>
								<td><font color="red">*</font>公司名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.companycn" id="companycn" onblur="changeAccount('both', 'companycn');" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">&nbsp;</font>英文公司名或拼音</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.companyen" onblur="changeAccount('single', 'companyen');" id="companyen" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;所属部门</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateCustomerOnlineDto.department" onblur="changeAccount('single', 'department');" id="department" cssStyle="width:250px;" maxlength="64" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.name" id="name" onblur="changeAccount('both', 'name');" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>邮编</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.postcode" onblur="changeAccount('single', 'postcode');" id="postcode" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.address" id="address" onblur="changeAccount('both', 'address');" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>电话号码</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.tell" id="tell" onblur="changeAccount('both', 'tell');" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
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
								<td align="left" width="15%"><strong>收货人信息</strong></td>
								<td colspan="2">
									<input id="tmpInfo" type="checkbox" style="width:20px;" onclick="changeInfo(this);" value="0"/>收货人信息同上
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>公司名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.companycn2" id="companycn2" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">&nbsp;</font>英文公司名或拼音</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.companyen2" id="companyen2" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;所属部门</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateCustomerOnlineDto.department2" id="department2" cssStyle="width:250px;" maxlength="64" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.name2" id="name2" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>邮编</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.postcode2" id="postcode2" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.address2" id="address2" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>电话号码</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="updateCustomerOnlineDto.tell2" id="tell2" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>交货方法</td>
								<td>
									<s:if test='%{updateCustomerOnlineDto.paytype == "2"}'>
										<input type="radio" name="tmpPaytype" style="width:40px;" value="1"/>配送(运费到付)
										<input type="radio" name="tmpPaytype" style="width:30px;" value="2" checked="checked"/>自提
									</s:if>
									<s:elseif test='%{updateCustomerOnlineDto.paytype == "1"}'>
										<input type="radio" name="tmpPaytype" style="width:40px;" value="1" checked="checked"/>配送(运费到付)
										<input type="radio" name="tmpPaytype" style="width:30px;" value="2"/>自提
									</s:elseif>
									<s:else>
										<input type="radio" name="tmpPaytype" style="width:40px;" value="1" checked="checked"/>配送(运费到付)
										<input type="radio" name="tmpPaytype" style="width:30px;" value="2"/>自提
									</s:else>
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
								<td align="left" width="15%"><strong>开户银行</strong></td>
								<td>&nbsp;企业</td>
								<td>
									<s:if test='%{updateCustomerOnlineDto.accounttype == "2"}'>
										<input type="radio" name="tmpAccounttype" style="width:40px;" value="1"onclick="typeCheck(1);"/>企业
										<input type="radio" name="tmpAccounttype" style="width:30px;" value="2" checked="checked" onclick="typeCheck(2);"/>个人
									</s:if>
									<s:elseif test='%{updateCustomerOnlineDto.accounttype == "1"}'>
										<input type="radio" name="tmpAccounttype" style="width:40px;" value="1" checked="checked"onclick="typeCheck(1);"/>企业
										<input type="radio" name="tmpAccounttype" style="width:30px;" value="2" onclick="typeCheck(2);"/>个人
									</s:elseif>
									<s:else>
										<input type="radio" name="tmpAccounttype" style="width:40px;" value="1" onclick="typeCheck(1);"/>企业
										<input type="radio" name="tmpAccounttype" style="width:30px;" value="2" onclick="typeCheck(2);"/>个人
									</s:else>
								</td>
							</tr>
							<s:if test='%{updateCustomerOnlineDto.accounttype == "2"}'>
								<tr id="tr_person">
									<td>&nbsp;</td>
									<td>&nbsp;姓名</td>
									<td style="height:31px" id="td_name">
										<s:property value="updateCustomerOnlineDto.name"/>
									</td>
								</tr>
								<tr id="tr_company" style="display: none">
									<td>&nbsp;</td>
									<td>&nbsp;公司名</td>
									<td style="height:31px" id="td_companycn">
										<s:property value="updateCustomerOnlineDto.companycn"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;公司税号</td>
									<td>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateCustomerOnlineDto.companytax" disabled="true" id="companytax" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;地址</td>
									<td style="height:31px" id="td_address">
										<s:property value="updateCustomerOnlineDto.address"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;电话号码</td>
									<td style="height:31px" id="td_tell">
										<s:property value="updateCustomerOnlineDto.tell"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;公司开户行</td>
									<td>
										<div class="box1">
											<div class="box1_left"></div>
											<div class="box1_center">
												<s:textfield name="updateCustomerOnlineDto.accountbank" disabled="true" id="accountbank" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
											</div>
											<div class="box1_right"></div>
										</div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;开户行账号</td>
									<td>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateCustomerOnlineDto.accountid" disabled="true" id="accountid" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;发票</td>
									<td>
									<s:if test='%{updateCustomerOnlineDto.receipttype == "2"}'>
										<input type="radio" name="tmpReceipttype" disabled="disabled" id="tmpReceipttype" style="width:40px;" value="1"/>普通发票
										<input type="radio" name="tmpReceipttype" disabled="disabled" id="tmpReceipttype2" style="width:30px;" value="2" checked="checked"/>专用发票
									</s:if>
									<s:elseif test='%{updateCustomerOnlineDto.receipttype == "1"}'>
										<input type="radio" name="tmpReceipttype" disabled="disabled" id="tmpReceipttype" style="width:40px;" value="1" checked="checked"/>普通发票
										<input type="radio" name="tmpReceipttype" disabled="disabled" id="tmpReceipttype2" style="width:30px;" value="2"/>专用发票
									</s:elseif>
									<s:else>
										<input type="radio" name="tmpReceipttype" disabled="disabled" id="tmpReceipttype" style="width:40px;" value="1"/>普通发票
										<input type="radio" name="tmpReceipttype" disabled="disabled" id="tmpReceipttype2" style="width:30px;" value="2"/>专用发票
									</s:else>
									</td>
								</tr>
							</s:if>
							<s:else>
								<tr id="tr_person" style="display: none">
									<td>&nbsp;</td>
									<td>&nbsp;姓名</td>
									<td style="height:31px" id="td_name">
										<s:property value="updateCustomerOnlineDto.name"/>
									</td>
								</tr>
								<tr id="tr_company">
									<td>&nbsp;</td>
									<td>&nbsp;公司名</td>
									<td style="height:31px" id="td_companycn">
										<s:property value="updateCustomerOnlineDto.companycn"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;公司税号</td>
									<td>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateCustomerOnlineDto.companytax" id="companytax" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;地址</td>
									<td style="height:31px" id="td_address">
										<s:property value="updateCustomerOnlineDto.address"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;电话号码</td>
									<td style="height:31px" id="td_tell">
										<s:property value="updateCustomerOnlineDto.tell"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;公司开户行</td>
									<td>
										<div class="box1">
											<div class="box1_left"></div>
											<div class="box1_center">
												<s:textfield name="updateCustomerOnlineDto.accountbank" id="accountbank" cssStyle="width:250px;" maxlength="128" theme="simple"></s:textfield>
											</div>
											<div class="box1_right"></div>
										</div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;开户行账号</td>
									<td>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="updateCustomerOnlineDto.accountid" id="accountid" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;发票</td>
									<td>
										<s:if test='%{updateCustomerOnlineDto.receipttype == "2"}'>
											<input type="radio" name="tmpReceipttype" id="tmpReceipttype" style="width:40px;" value="1"/>普通发票
											<input type="radio" name="tmpReceipttype" id="tmpReceipttype2" style="width:30px;" value="2" checked="checked"/>专用发票
										</s:if>
										<s:elseif test='%{updateCustomerOnlineDto.receipttype == "1"}'>
											<input type="radio" name="tmpReceipttype" id="tmpReceipttype" style="width:40px;" value="1" checked="checked"/>普通发票
											<input type="radio" name="tmpReceipttype" id="tmpReceipttype2" style="width:30px;" value="2"/>专用发票
										</s:elseif>
										<s:else>
											<input type="radio" name="tmpReceipttype" id="tmpReceipttype" style="width:40px;" value="1"/>普通发票
											<input type="radio" name="tmpReceipttype" id="tmpReceipttype2" style="width:30px;" value="2"/>专用发票
										</s:else>
									</td>
								</tr>
							</s:else>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="120"><font color="red"></font>用友客户编号</td>
				<td width="500">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateCustomerOnlineDto.res02" id="res02" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td width="120"><font color="red"></font>用友账套编号</td>
				<td>
					<s:if test='updateCustomerDto.res03 == "2"'>
						<input type="radio" id="tmpRes03" name="updateCustomerOnlineDto.res03" value='1' />贸易
						<input type="radio" name="updateCustomerOnlineDto.res03" checked="checked" value='2'/>发展　
					</s:if>
					<s:else>
						<input type="radio" id="tmpRes03" name="updateCustomerOnlineDto.res03" checked="checked" value='1' />贸易
						<input type="radio" name="updateCustomerOnlineDto.res03" value='2'/>发展　
					</s:else>
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
