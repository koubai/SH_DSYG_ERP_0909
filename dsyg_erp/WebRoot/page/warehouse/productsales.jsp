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
<title>产品销售信息</title>
<script type="text/javascript">
	$(function() {
		var date = new Date();
		var str = date.getFullYear()
		    + '-' + ('0' + (date.getMonth() + 1 - 3)).slice(-2)
		    + '-' + ('0' + date.getDate()).slice(-2);
		var target = document.getElementById("prev3Mdate");
		target.value= str;
	});
	
	function showSaleNoDetail(salesno) {
		if(salesno == "") {
			return;
		} else {
			var url = '<%=request.getContextPath()%>/sales/showUpdSalesitemBySalesNoAction.action';
			//strFlag=1采购单，strFlag=2销售单
			url += "?salesno=" + salesno + "&strCustomerid=" + "" + "&strFlag=2" + "&date=" + encodeURI(new Date());
			//window.showModalDialog(url, window, "dialogheight:800px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
			showModalDialogN(url, window, "dialogheight:800px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		}
	}
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
			<div class="tab_content" style="height: 300px;"><BR>
				品名:<s:property value="salesItemList[0].tradename"/>&nbsp&nbsp&nbsp规格:<s:property value="salesItemList[0].typeno"/>&nbsp&nbsp&nbsp颜色:<s:iterator id="colorList" value="colorList" status="st3"><s:if test="%{colorList[#st3.index].code == salesItemList[0].color}"><s:property value="fieldname"/></s:if></s:iterator>&nbsp&nbsp&nbsp包装:<s:property value="salesItemList[0].item01"/>&nbsp&nbsp&nbsp形式:<s:if test='salesItemList[0].packaging.equals("0")'>整箱</s:if><s:elseif test='salesItemList[0].packaging.equals("1")'>乱尺</s:elseif><s:else>乱尺</s:else>			
				<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<tr class="tittle">
						<td width="20">序号</td>
						<td width="60">客户</td>
						<td width="60">订单号</td>
						<td width="60">未出库数量</td>
						<td width="60">销售日期</td>
					</tr>
					<s:iterator id="salesItemList" value="salesItemList" status="st1">	
						<s:if test="#st1.odd==true">
							<tr class="tr_bg">
						</s:if>
						<s:else>
							<tr>
						</s:else>
							<td><s:property value="#st1.index + 1"/></td>
							<td><s:property value="customername"/></td>
							<td><a href="#" onclick="showSaleNoDetail('<s:property value="salesno"/>');"><s:property value="theme2"/></a></td>
							
							<s:if test="%{showBookdate < prev3Mdate}">
								<td style='background-color: yellow' ><s:property value="remainquantity"/></td>
								<td style='background-color: yellow' ><s:property value="showBookdate"/></td>
							</s:if>
							<s:else>
								<td><s:property value="remainquantity"/></td>
								<td><s:property value="showBookdate"/></td>
							</s:else>
						</tr>
					</s:iterator>
				</table>
				
			</div>
		</div>
		<div class="btns" style="margin-top:40px; margin-left: 0px;">
				逾期3个月合计数量：<s:property value="total3MQuantity"/>
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
