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
<title>快递信息一览</title>
<script type="text/javascript">
	$(function() {
	});
</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<div id="container" style="width: 100%; height: 100%;">
		<div class="data_table" style="padding:0px;">
			<div class="tab_tittle">
				<table width="100%" border="1" cellpadding="5" cellspacing="0">
				</table>
			</div>
			<div class="tab_content">
				<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<tr class="tittle">
						<td width="20">序号</td>
						<td width="60">快递名称</td>
						<td width="60">快递单号</td>
						<td width="60">快递公司地址</td>
						<td width="30">联系人</td>
						<td width="60">联系人电话</td>
						<td width="30">重量</td>
						<td width="60">转运费用合计</td>
						<td width="60">单据日期</td>
						<td width="60">发货日期</td>
						<td width="60">操作者</td>
						<td width="60">操作日期</td>
					</tr>
					<s:iterator id="rptLogList" value="rptLogList" status="st1">
						<s:if test="#st1.odd==true">
							<tr class="tr_bg">
						</s:if>
						<s:else>
							<tr>
						</s:else>
							<td><s:property value="#st1.index + 1"/></td>
							<td><s:property value="expressname"/></td>
							<td><s:property value="expressno"/></td>
							<td><s:property value="expressaddress"/></td>
							<td><s:property value="expressmanager"/></td>
							<td><s:property value="expresstel"/></td>
							<td><s:property value="res03"/></td>
							<td><s:property value="expresstaxamount"/></td>
							<td><s:property value="res01"/></td>
							<td><s:property value="showWarehousedate"/></td>
							<td><s:property value="createuid"/></td>
							<td><s:date name="createdate" format="yyyy/MM/dd HH:mm:ss" /></td>
						</tr>
					</s:iterator>
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
