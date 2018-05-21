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
<title>发货单信息</title>
<script type="text/javascript">
	$(function() {
	});
	
	function init(){
		var trs=document.getElementsByTagName("tr");
		var m=0;
		for(var i=1;i<trs.length-2;i++){
			var tds=trs[i].getElementsByTagName("td");
			m+=parseFloat(tds[2].innerHTML);
		}
		if (m!=0){
			document.getElementById("sumqty").value = m.toFixed(2);
		}
		
	}
</script>
</head>
<body onload="init()" style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<div id="container" style="width: 100%; height: 100%;">
		<div class="data_table" style="padding:0px;">
			<div class="tab_tittle">
				<table width="100%" border="1" cellpadding="5" cellspacing="0">
				</table>
			</div>
			<div class="tab_content" style="height: 600px;"><BR>
				客户:<s:property value="warehouserptList[0].suppliername"/>
				<BR>
				品名:<s:property value="productInfo.tradename"/>&nbsp&nbsp&nbsp规格:<s:property value="productInfo.typeno"/>&nbsp&nbsp&nbsp颜色:<s:iterator id="colorList" value="colorList" status="st3"><s:if test="%{colorList[#st3.index].code == productInfo.color}"><s:property value="fieldname"/></s:if></s:iterator>&nbsp&nbsp&nbsp包装:<s:property value="productInfo.item10"/>&nbsp&nbsp&nbsp形式:<s:if test='productInfo.packaging == "0"'>整箱</s:if><s:elseif test='productInfo.packaging == "1"'>乱尺</s:elseif><s:else>乱尺</s:else>
				<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
					<tr class="tittle">
						<td width="20">序号</td>
						<td width="80">发货单号</td>
						<td width="60">发货数量</td>
						<td width="60">发货日期</td>
					</tr>
					<s:iterator id="warehouserptList" value="warehouserptList" status="st1">
						<s:if test="#st1.odd==true">
							<tr class="tr_bg">
						</s:if>
						<s:else>
							<tr>
						</s:else>
							<td><s:property value="#st1.index + 1"/></td>
							<td><s:property value="warehouseno"/></td>
							<td><s:property value="productinfo" /></td>
							<td><s:property value="warehousedate" /></td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
		<div class="btns" style="margin-top:10px; margin-left: 0px;">
			<table border="0" style="margin:0 auto;">
				<tr>
					<td style="margin-left: 100px;">
						合计<input type="text" class="qtycls" id="sumqty" value="" disabled="true" />
					</td>
				</tr>
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
