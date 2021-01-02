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
<title>客户信息输入</title>
<script type="text/javascript">
	function add() {
		//初期化邮箱
		for(var i = 1; i <= 5; i++) {
			//邮箱
			var mail_pr = $("#" + "mail_pr" + i).val();
			var mail_suffix = $("#" + "mail_suffix" + i).val();
			if(mail_pr != "" || mail_suffix != "") {
				$("#" + "customermail" + i).attr("value", mail_pr + "@" + mail_suffix);
			} else {
				$("#" + "customermail" + i).attr("value", "");
			}
		}
	
		var id = $("#id").val().trim();
		var customername = $("#customername").val().trim();
		//客户地址
		var res01 = $("#res01").val().trim();
		//用友账套
		$("#addCustomerDto.res03").attr("value", $("#tmpRes03").val());
		var tmpnote = $("#tmpnote").val();
		$("#note").attr("value", tmpnote);
/*		if(id == "") {
			alert("客户代码不能为空！");
			$("#id").focus();
			return;
		}
*/		
		if(customername == "") {
			alert("客户名称不能为空！");
			$("#customername").focus();
			return;
		}
		if(res01 == "") {
			alert("客户地址不能为空！");
			$("#res01").focus();
			return;
		}
		
		var customermanager1 = $("#customermanager1").val();
		if(customermanager1.trim() == "") {
			alert("联系人1不能为空！");
			$("#customermanager1").focus();
			return;
		}
		//联系人信息check
		var all = "";
		for(var i = 1; i <= 5; i++) {
			var customermanager = $("#" + "customermanager" + i).val();
			var customertel = $("#" + "customertel" + i).val();
			var customeraddress = $("#" + "customeraddress" + i).val();
			var customermail = $("#" + "customermail" + i).val();
			var customerfax = $("#" + "customerfax" + i).val();
			all += customermanager;
			if(customermanager.trim() != "") {
				if(customermanager.length > 6) {
					alert("联系人" + i + "姓名不能超过6个字！");
					$("#" + "customermanager" + i).focus();
					return;
				}
				if(customertel.trim() == "") {
					alert("联系人" + i + "电话不能为空！");
					$("#" + "customertel" + i).focus();
					return;
				}
				//邮箱
/*				if(customermail.trim() == "") {
					alert("联系人" + i + "邮箱不能为空！");
					$("#" + "mail_pr" + i).focus();
					return;
				}
*/				
				if(customeraddress.trim() == "") {
					alert("联系人" + i + "地址不能为空！");
					$("#" + "customeraddress" + i).focus();
					return;
				}
				
/*				if(customerfax.trim() == "") {
					alert("联系人" + i + "传真不能为空！");
					$("#" + "customerfax" + i).focus();
					return;
				}
*/				
			}
			if(customermail != "" && !isMail(customermail)) {
				alert("联系人" + i + "邮箱格式不正确！");
				$("#" + "mail_pr" + i).focus();
				return;
			}
		}
		if(all == "") {
			alert("联系人不能为空！");
			$("#customermanager1").focus();
			return;
		}
		if(tmpnote.length > 100) {
			alert("备注不能超过100个字！");
			$("#tmpnote").focus();
			return;
		}
		document.mainform.action = '<c:url value="/customer/addEtbCustomerAction.action"></c:url>';
		document.mainform.submit();
	}
	
	function golist() {
		document.mainform.action = '<c:url value="/customer/queryEtbCustomerList.action"></c:url>';
		document.mainform.submit();
	};

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
					客户信息输入
				</div>
				<div class="tittle_right">
				</div>
			</div>
		</div>
		<s:form id="mainform" name="mainform" method="POST">
			<s:hidden name="addCustomerDto.id" id="id"></s:hidden>
			<s:hidden name="addCustomerDto.note" id="note"></s:hidden>
			<s:hidden name="addCustomerDto.customermail1" id="customermail1"></s:hidden>
			<s:hidden name="addCustomerDto.customermail2" id="customermail2"></s:hidden>
			<s:hidden name="addCustomerDto.customermail3" id="customermail3"></s:hidden>
			<s:hidden name="addCustomerDto.customermail4" id="customermail4"></s:hidden>
			<s:hidden name="addCustomerDto.customermail5" id="customermail5"></s:hidden>
		<div style="position:absolute; margin-left: 150px; margin-top: 10px; text-align: center; color: red;">
			<s:actionmessage />
		</div>
		<table style="margin-left: 50px; margin-top: 30px;" border="0" cellspacing="15" cellpadding="0">
			<tr>
				<td width="150"><font color="red">*</font>客户名称</td>
				<td width="550">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="addCustomerDto.customername" id="customername" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td width="150"><font color="red">*</font>客户城市</td>
				<td width="550">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="addCustomerDto.res01" id="res01" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customermanager1" id="customermanager1" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customertel1" id="customertel1" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addCustomerDto.mail_pr1" id="mail_pr1" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_suffix1" id="mail_suffix1" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><font color="red">*</font>联系人1地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customeraddress1" id="customeraddress1" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customerfax1" id="customerfax1" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customermanager2" id="customermanager2" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customertel2" id="customertel2" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
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
											<s:textfield name="addCustomerDto.mail_pr2" id="mail_pr2" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_suffix2" id="mail_suffix2" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customeraddress2" id="customeraddress2" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
										<s:textfield name="addCustomerDto.customerfax2" id="customerfax2" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
								<td align="left" width="15%"><strong>收货人3</strong></td>
								<td>收货人3姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customermanager3" id="customermanager3" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>收货人3电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customertel3" id="customertel3" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>收货人3邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_pr3" id="mail_pr3" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_suffix3" id="mail_suffix3" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>收货人3地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customeraddress3" id="customeraddress3" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>收货人3传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customerfax3" id="customerfax3" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
								<td align="left" width="15%"><strong>发票收件人4</strong></td>
								<td>发票收件人4姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customermanager4" id="customermanager4" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>发票收件人4电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customertel4" id="customertel4" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>发票收件人4邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_pr4" id="mail_pr4" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_suffix4" id="mail_suffix4" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>发票收件人4地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customeraddress4" id="customeraddress4" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>发票收件人4传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customerfax4" id="customerfax4" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
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
								<td align="left" width="15%"><strong>其他5</strong></td>
								<td>其他5姓名</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customermanager5" id="customermanager5" cssStyle="width:250px;" maxlength="24" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>其他5电话</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customertel5" id="customertel5" cssStyle="width:250px;" maxlength="30" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>其他5邮箱</td>
								<td>
									<div class="box1">
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_pr5" id="mail_pr5" cssStyle="width:116px;" maxlength="29" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
										<label>@</label>
										<div class="box1_left"></div>
										<div class="box1_center">
											<s:textfield name="addCustomerDto.mail_suffix5" id="mail_suffix5" cssStyle="width:116px;" maxlength="30" theme="simple"></s:textfield>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>其他5地址</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customeraddress5" id="customeraddress5" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>其他5传真</td>
								<td>
									<div class="box1_left"></div>
									<div class="box1_center">
										<s:textfield name="addCustomerDto.customerfax5" id="customerfax5" cssStyle="width:250px;" maxlength="40" theme="simple"></s:textfield>
									</div>
									<div class="box1_right"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="120"><font color="red"></font>用友客户编号</td>
				<td width="500">
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="addCustomerDto.res02" id="res02" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td width="120"><font color="red"></font>用友账套编号</td>
				<td>
					<s:if test='addCustomerDto.res03 == "2"'>
						<input type="radio" id="tmpRes03" name="addCustomerDto.res03" value='1' />贸易
						<input type="radio" name="addCustomerDto.res03" checked="checked" value='2'/>发展　
					</s:if>
					<s:else>
						<input type="radio" id="tmpRes03" name="addCustomerDto.res03" checked="checked" value='1' />贸易
						<input type="radio" name="addCustomerDto.res03" value='2'/>发展　
					</s:else>
			</tr>			
			<tr>
				<td>备注</td>
				<td>
					<textarea name="" id="tmpnote" cols="" rows="5" style="width:350px;"><s:property value="addCustomerDto.note"/></textarea>
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
