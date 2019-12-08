<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>产品出入库明细</title>
<script type="text/javascript">
</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<div id="container" style="width: 100%; height: 100%;">
		<div class="searchbox">
			<div class="box1">
				<label class="pdf10">品名：<s:property value="productDetail.tradename"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label class="pdf10">规格：<s:property value="productDetail.typeno"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label class="pdf10">颜色：
				<s:iterator id="colorList" value="colorList" status="st3">
					<s:if test="%{colorList[#st3.index].code == productDetail.color}">
						<s:property value="fieldname"/>
					</s:if>
				</s:iterator>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</label>
				<label class="pdf10">包装：<s:property value="productDetail.item10"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<label class="pdf10">形式：
				<s:if test='%{productDetail.packaging == "0"}'>整箱</s:if>
				<s:elseif test='%{productDetail.packaging == "1"}'>乱尺</s:elseif>
				<s:else>
					<s:property value="productDetail.packaging"/>
				</s:else>
				</label>
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
						<td width="20">序号</td>
						<td width="120">客户</td>
						<td width="70">订单号</td>
						<td width="60">订单日</td>
						<td width="60">入库数量</td>
						<td width="60">入库日期</td>
					</tr>
					<s:iterator id="inDetailList" value="inDetailList" status="st1">
						<s:if test="#st1.odd==true">
							<tr class="tr_bg">
						</s:if>
						<s:else>
							<tr>
						</s:else>
							<td><s:property value="#st1.index + 1"/></td>
							<td>
								<s:property value="customername"/>
							</td>
							<td>
								<s:property value="theme2"/>
							</td>
							<td>
								<s:date name="orderdate" format="yyyy/MM/dd" />
							</td>
							<td>
								<s:property value="quantity"/>
							</td>
							<td>
								<s:date name="warehousedate" format="yyyy/MM/dd" />
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="3" align="right">
							合计
						</td>
						<td>
							<s:property value="sumInQuantity"/>
						</td>
						<td>
						</td>
					</tr>
				</table>
				<br />
				<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<tr class="tittle">
						<td width="20">序号</td>
						<td width="120">客户</td>
						<td width="70">订单号</td>
						<td width="60">订单日</td>
						<td width="60">出库数量</td>
						<td width="60">出库日期</td>
					</tr>
					<s:iterator id="outDetailList" value="outDetailList" status="st1">
						<s:if test="#st1.odd==true">
							<tr class="tr_bg">
						</s:if>
						<s:else>
							<tr>
						</s:else>
							<td><s:property value="#st1.index + 1"/></td>
							<td>
								<s:property value="customername"/>
							</td>
							<td>
								<s:property value="theme2"/>
							</td>
							<td>
								<s:date name="orderdate" format="yyyy/MM/dd" />
							</td>
							<td>
								<s:property value="quantity"/>
							</td>
							<td>
								<s:date name="warehousedate" format="yyyy/MM/dd" />
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="3" align="right">
							合计
						</td>
						<td>
							<s:property value="sumOutQuantity"/>
						</td>
						<td>
						</td>
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
