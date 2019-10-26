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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>开票</title>
<script type="text/javascript">
	$(function() {
	});
	
	function kaipiao() {
		if($("#strNewFaPiaoNo").val() == "") {
			alert("发票号不能为空！");
			$("#strNewFaPiaoNo").focus();
			return;
		}
		if($("#tmpNewFaPiaodate").val() == "") {
			alert("请选择开票日期！");
			$("#tmpNewFaPiaodate").focus();
			return;
		}
		$("#strNewFaPiaodate").val($("#tmpNewFaPiaodate").val());
		document.mainform.action = "../finance/newKaiPiaoAction.action";
		document.mainform.submit();
	}
	
	//页面关闭响应
	window.onunload = function() {
		//刷新父页面
		getOpener().window.location = getOpener().window.location;
	};
</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<s:hidden name="strNewKaipiaoIds" id="strNewKaipiaoIds"></s:hidden>
	<s:hidden name="strNewFaPiaodate" id="strNewFaPiaodate"></s:hidden>
	<div id="container" style="width: 80%; height: 100%; margin-left: 100px; margin-top: 40px;">
		<div class="box1" style="position:absolute; margin-top:-30px; margin-left: 200px; color: red;">
			<s:actionmessage />
		</div>
		<table>
			<tr>
				<td width="80">
					<label class="pdf10">主题：</label>
				</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<select style="width: 180px;" disabled="disabled">
							<option value="">请选择</option>
							<option value="1">采购</option>
							<option value="2">订单</option>
							<option value="3" selected="selected">物流</option>
							<option value="4">手动录入</option>
						</select>
					</div>
					<div class="box1_right"></div>
				</td>
				<td width="180"></td>
				<td width="80">
					<label class="pdf10">发票号：</label>
				</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="strNewFaPiaoNo" id="strNewFaPiaoNo" cssStyle="width:180px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td>
					<label class="pdf10">对象：</label>
				</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="strNewFaPiaoCustomername" id="strNewFaPiaoCustomername" disabled="true" cssStyle="width:180px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
				<td>
				</td>
				<td>
					<label class="pdf10">开票日期：</label>
				</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center date_input">
						<input type="text" name="tmpNewFaPiaodate" id="tmpNewFaPiaodate" disabled="disabled" style="width:165px;" value="<s:property value="strNewFaPiaodate"/>" />
						<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpNewFaPiaodate'));"></a>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td>
					<label class="pdf10">金额合计：</label>
				</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="strNewFaPiaoAmount" id="strNewFaPiaoAmount" disabled="true" cssStyle="width:180px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
				<td colspan="3">
				</td>
			</tr>
		</table>
		<div class="btns" style="margin-top:40px; margin-left: 0px;">
			<table border="0" style="margin:0 auto;">
				<tr>
					<td align="center">
						<s:if test='strNewFaPiaoFlag == "1"'>
							<div class="btn">
								<div class="box1_left"></div>
								<div class="box1_center">
									<input type="button" class="input80" value="开票" onclick="kaipiao();"/>
								</div>
								<div class="box1_right"></div>
							</div>
						</s:if>
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
