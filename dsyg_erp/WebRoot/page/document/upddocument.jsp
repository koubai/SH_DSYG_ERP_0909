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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
<title>文件物品信息修改</title>
<script type="text/javascript">
	function upd() {
		var documentname = $("#documentname").val().trim();
		var tmpnote = $("#tmpnote").val().trim();
		$("#note").attr("value", tmpnote);
		if(documentname == "") {
			alert("文件名不能为空！");
			$("#documentname").focus();
			return;
		}

		var tmpregisterdate = $("#tmpregisterdate").val().trim();
		if(tmpregisterdate == "") {
			alert("登记日期不能为空！");
			$("#tmpregisterdate").focus();
			return;
		}
		$("#registerdate").attr("value", tmpregisterdate);
		
		var handler = $("#handler").val();
		if(handler.trim() == "") {
			alert("负责人不能为空！");
			$("#handler").focus();
			return;
		}
		
		var approverid = $("#approverid").val();
		if(approverid.trim() == "") {
			alert("确认者不能为空！");
			$("#approverid").focus();
			return;
		}
		if(tmpnote.length > 500) {
			alert("备注不能超过500个字！");
			$("#tmpnote").focus();
			return;
		}
		document.mainform.action = '<c:url value="/document/updEtbDocumentAction.action"></c:url>';
		document.mainform.submit();
	}

	function golist() {
		document.mainform.action = '<c:url value="/document/queryEtbDocumentList.action"></c:url>';
		document.mainform.submit();
	};

</script>
<base target="_self"/>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:scroll;">
<s:form id="mainform" name="mainform" method="POST">
	<s:hidden name="updateDocumentDto.note" id="note"></s:hidden>
	<s:hidden name="updateDocumentDto.registerdate" id="registerdate"></s:hidden>
	<div id="container" style="width: 100%; height: 100%;">
		<div class="content" style="margin-top: 0px;">
			<div class="tittle" style="width:750px">
				<div class="icons"></div>
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px;">
					文件物品信息修改
				</div>
				<div class="tittle_right">
				</div>
			</div>
		</div>
		<div style="position:absolute; margin-left: 150px; margin-top: 10px; text-align: center; color: red;">
			<s:actionmessage />
		</div>
		<table style="margin-left: 50px; margin-top: 30px;" border="0" cellspacing="15" cellpadding="0">
			<tr>
				<td width="130"><font color="red">*</font>文件编号</td>
				<td width="500">&nbsp;<s:property value="updateDocumentDto.documentno"/></td>
			</tr>
			<tr>
				<td><font color="red">*</font>文件名</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateDocumentDto.documentname" id="documentname" cssStyle="width:350px;" maxlength="128" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td><font color="red">*</font>负责人</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateDocumentDto.handler" id="handler" cssStyle="width:350px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td><font color="red">*</font>登记日期</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center date_input">
						<input type="text" disabled="disabled" style="width: 100px;" id="tmpregisterdate" value="<s:property value="updateDocumentDto.showregisterdate"/>" maxlength="10" />
						<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpregisterdate'));"></a>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td><font color="red">*</font>确认者</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateDocumentDto.approverid" id="approverid" cssStyle="width:350px;" maxlength="5" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<textarea name="" id="tmpnote" cols="" rows="5" style="width:350px;"><s:property value="updateDocumentDto.note"/></textarea>
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
	</div>
</s:form>
</body>
</html>
