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
<title>库存盘点报告信息修改</title>
<script type="text/javascript">
	function upd() {
		var warehousereportname = $("#warehousereportname").val().trim();
		var tmpnote = $("#tmpnote").val();
		$("#note").attr("value", tmpnote);
		if(warehousereportname == "") {
			alert("库存盘点报告名不能为空！");
			$("#warehousereportname").focus();
			return;
		}

		var tmpregisterdate = $("#tmpregisterdate").val().trim();
		if(tmpregisterdate == "") {
			alert("登记日期不能为空！");
			$("#tmpregisterdate").focus();
			return;
		}
		$("#registerdate").attr("value", tmpregisterdate);
		/*
		var handler = $("#handler").val();
		if(handler.trim() == "") {
			alert("负责人不能为空！");
			$("#handler").focus();
			return;
		} */
		if(tmpnote.length > 500) {
			alert("备注不能超过500个字！");
			$("#tmpnote").focus();
			return;
		}
		//文件验证
		var file01Name = $("#updReportFile01").val();
		var file02Name = $("#updReportFile02").val();
		var file03Name = $("#updReportFile03").val();
		//文件格式
		/*
		if(file01Name == "") {
			alert("请选择对应库存盘点报告文件1！");
			$("#updReportFile01").focus();
			return;
		}
		if(file02Name == "") {
			alert("请选择对应库存盘点报告文件2！");
			$("#updReportFile02").focus();
			return;
		}
		if(file03Name == "") {
			alert("请选择对应库存盘点报告文件3！");
			$("#updReportFile03").focus();
			return;
		} */

		//文件名
		$("#file01Name").val(file01Name);
		$("#file02Name").val(file02Name);
		$("#file03Name").val(file03Name);
		
		document.mainform.action = '<c:url value="/warehousereport/updWarehouseReportAction.action"></c:url>';
		document.mainform.submit();
	}
	
	function golist() {
		document.mainform.action = '<c:url value="/warehousereport/queryWarehouseReportList.action"></c:url>';
		document.mainform.submit();
	};

</script>
<base target="_self"/>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:scroll;">
<s:form id="mainform" name="mainform" method="POST" enctype="multipart/form-data">
	<s:hidden name="updateWarehouseReportDto.note" id="note"></s:hidden>
	<s:hidden name="updateWarehouseReportDto.registerdate" id="registerdate"></s:hidden>
	<s:hidden name="file01Name" id="file01Name"></s:hidden>
	<s:hidden name="file02Name" id="file02Name"></s:hidden>
	<s:hidden name="file03Name" id="file03Name"></s:hidden>
	<div id="container" style="width: 100%; height: 100%;">
		<div class="content" style="margin-top: 0px;">
			<div class="tittle" style="width:750px">
				<div class="icons"></div>
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px;">
					库存盘点报告信息详细
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
				<td width="130">
					<label class="pdf10">报告编号</label></td>
				<td width="500">&nbsp;<s:property value="updateWarehouseReportDto.salesreportno"/></td>
			</tr>
			<tr>
				<td>
					<label class="pdf10"><font color="red">*</font>报告名</label></td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateWarehouseReportDto.warehousereportname" id="warehousereportname" disabled="true" cssStyle="width:350px;" maxlength="40" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td>
					<label class="pdf10">负责人</label></td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
						<s:textfield name="updateWarehouseReportDto.handler" id="handler" disabled="true" cssStyle="width:350px;" maxlength="32" theme="simple"></s:textfield>
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td>
					<label class="pdf10"><font color="red">*</font>登记日期</label></td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center date_input">
						<input type="text" disabled="true" style="width: 100px;" id="tmpregisterdate" value="<s:property value="updateWarehouseReportDto.showregisterdate"/>" maxlength="10" />
						<!-- <a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpregisterdate'));"></a> -->
					</div>
					<div class="box1_right"></div>
				</td>
			</tr>
			<tr>
				<td class="td_tittle">
					<label class="pdf10">文件1上传</label></td>
				<td>
					<!-- <input type="file" name="updReportFile01" id="updReportFile01" disabled="true" style="width: 500px;"/> -->
					<s:property value="updateWarehouseReportDto.reportpath01"/><br />
				</td>
			</tr>
			<tr>
				<td class="td_tittle">
					<label class="pdf10">文件2上传</label></td>
				<td>
					<!-- <input type="file" name="updReportFile02" id="updReportFile02" disabled="true" style="width: 500px;"/> -->
					<s:property value="updateWarehouseReportDto.reportpath02"/><br />
				</td>
			</tr>
			<tr>
				<td class="td_tittle">
					<label class="pdf10">文件3上传</label></td>
				<td>
					<!-- <input type="file" name="updReportFile03" id="updReportFile03" disabled="true" style="width: 500px;"/> -->
					<s:property value="updateWarehouseReportDto.reportpath03"/><br />
				</td>
			</tr>
			<tr>
				<td><label class="pdf10">备注</label></td>
				<td>
					<textarea name="" id="tmpnote" cols="" disabled="true" rows="10" style="width:400px;"><s:property value="updateWarehouseReportDto.note"/></textarea>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<!--<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="修改" onclick="upd();"/>
						</div>
						<div class="box1_right"></div>
					</div> -->
			 		<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="关闭" onclick="window.close();"/>
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
