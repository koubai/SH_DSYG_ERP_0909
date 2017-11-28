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
<title>关联发票一览</title>
<script type="text/javascript">
</script>
</head>
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
<div id="container" style="width: 100%; height: 100%;">
		<div id="container" style="width: 100%; height: 100%;">
			<div class="searchbox">
				<div class="box1">
					<label class="pdf10">账目编号：<s:property value="strReceptid"/></label>
				</div>
			</div>
			<div class="tab_tittle">
				<table width="100%" border="1" cellpadding="5" cellspacing="0">
				</table>
			</div>
			<div class="tab_content">
				<table class="data_table" style="padding:0px;">
					<tr class="tittle">
						<td width="40">序号</td>
						<td width="100">发票号</td>
						<td width="160">客户名称</td>
						<td width="120">关联单据编号</td>
						<td width="80">开票日期</td>
						<td width="80">开票人</td>
						<td width="100">开票金额(含税)</td>
						<td width="80">状态</td>					
						<td width="160">备注</td>
					</tr>
					<s:iterator id="listInvoiceOk" value="listInvoiceOk" status="st1">
						<s:if test="#st1.odd==true">
							<tr class="tr_bg">
						</s:if>
						<s:else>
							<tr>
						</s:else>
							<td><s:property value="#st1.index + 1"/></td>
							<td><s:property value="invoiceno"/></td>
							<td><s:property value="customername"/></td>
							<td><s:property value="warehouserptno"/></td>
							<td><s:property value="strInvoice_date"/></td>
							<td><s:property value="invoide_mem_id"/></td>
							<td align="right"><s:property value="amounttax"/></td>
							<td><s:if test="status==0">
									预开票
								</s:if>
								<s:elseif test="status==1">
									开票
								</s:elseif>
								<s:elseif test="status == 2">
									退票
								</s:elseif>
								<s:elseif test="status == 99">
									作废
								</s:elseif>
								<s:else>
									<s:property value="status"/>
								</s:else>
							</td>
							<td><s:property value="note"/>
								<!-- <div noWrap title="<s:property value="note"/>" style="width:135px;text-overflow:ellipsis;overflow:hidden">
									<s:property value="note"/>
								</div> -->
							</td>
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
