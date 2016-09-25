<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/orderstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
<title>Online订单一览</title>
<script type="text/javascript">
	$(document).ready(function(){
		var h = screen.availHeight; 
		$("#container").height(h - 20);
	});
	
	function showDetail(id) {
		//window.showModalDialog(url, window, "dialogheight:680px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no;");
		document.mainform.action = "../order/showOrderDetailAction.action?strOrderDetailId=" + id;
		document.mainform.submit();
	}
	
	function getSelectedID() {
		var id = "";
		var list = document.getElementsByName("radioKey");
		for(var i = 0; i < list.length; i++) {
			if(list[i].checked) {
				id = list[i].value;
				break;
			}
		}
		return id;
	}
	
	//查询数据
	function queryList() {
		document.mainform.action = '../order/queryOrderListAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		$("#intPageSize").attr("value", pagesize);
		$("#startIndex").attr("value", "0");
		document.mainform.action = '../order/queryOrderListAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		$("#startIndex").attr("value", pageNum);
		document.mainform.action = '../order/turnOrderListAction.action';
		document.mainform.submit();
	}

	//页跳转
	function turnPage() {
		var totalPage = "${page.totalPage}";
		var turnPage = document.getElementById("pagenum").value;
		//判断是否输入页码
		if ('' != turnPage) {
			//判断页码是否是大于0的数字
			if(!iscInteger(turnPage)){
				alert("页码必须是大于0的整数！");
				return;
			}
			turnPage = parseInt(turnPage);
			if(turnPage < 1){
				alert("页码必须是大于0的整数！");
				return;
			}
			//判断页码大小是否正确
			if(turnPage > parseInt(totalPage)){
				alert("页码不能超过最大页数！");
				return;
			}
			//换页
			changePage(turnPage - 1);
		} else {
			alert("页码不能为空！");
			return;
		}
	}
</script>
</head>
<body>
	<div id="containermain">
		<div class="content">
			<jsp:include page="../info.jsp" flush="true" />
			<div class="tittle">
				<div class="icons"><a class="home" href="#" onclick="goHome();">返回首页</a><a class="quit" href="#" onclick="logout();">退出</a></div>
				<div class="tittle_left">
				</div>
				<div class="tittle_center">
					Online订单一览
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="startIndex" id="startIndex"/>
				<s:hidden name="intPageSize" id="intPageSize"/>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">订单号</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strOrdercode" cssStyle="width:200px;" id="strTheme2" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<label class="pdf10">状态</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<select id="strStatus" name="strStatus" style="width: 160px;">
								<s:if test='strStatus == 10'>
									<option value="">请选择</option>
									<option value="10" selected="selected">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:if>
								<s:elseif test='strStatus == 20'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20" selected="selected">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 30'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30" selected="selected">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 40'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40" selected="selected">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 50'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50" selected="selected">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 60'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60" selected="selected">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 70'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70" selected="selected">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 80'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80" selected="selected">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:elseif>
								<s:elseif test='strStatus == 99'>
									<option value="">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99" selected="selected">订单已关闭</option>
								</s:elseif>
								<s:else>
									<option value="" selected="selected">请选择</option>
									<option value="10">询货中</option>
									<option value="20">交期确认，待回复</option>
									<option value="30">已回复，受理中</option>
									<option value="40">下单成功，待付款</option>
									<option value="50">已付款，待确认</option>
									<option value="60">已收款，待发货</option>
									<option value="70">已发货，待收货</option>
									<option value="80">已收货，订单完成</option>
									<option value="99">订单已关闭</option>
								</s:else>
							</select>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn" style="margin-top: 6px; margin-left: 100px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="检索" onclick="queryList();"/>
						</div>
						<div class="box1_right"></div>
					</div>
				</div>
				<div class="data_table" style="padding:0px;">
					<div class="tab_tittle">
						<table width="100%" border="1" cellpadding="5" cellspacing="0">
						</table>
					</div>
					<div class="tab_content" style="height: <s:property value="intPageSize * 35"/>px;">
						<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
							<tr class="tittle">
								<td>&nbsp;</td>
								<td>订单号</td>
								<td>金额</td>
								<td>含税金额</td>
								<td>订单状态</td>
								<td>创建时间</td>
								<td>备注</td>
								<td></td>
							</tr>
							<s:iterator value="orderList" id="orderList" status="st2">
								<s:if test="#st1.odd==true">
									<tr class="tr_bg" onclick="checkRadioTr(this, event);">
								</s:if>
								<s:else>
									<tr onclick="checkRadioTr(this, event);">
								</s:else>
									<td><s:property value="page.pageSize * (page.nextIndex - 1) + #st2.index + 1"/></td>
									<td>
										<a href="#" onclick="showDetail('<s:property value="id"/>');"><s:property value="ordercode"/></a>
									</td>
									<td><s:property value="amount"/></td>
									<td><s:property value="taxamount"/></td>
									<td>
										<s:if test="status == 10">询货中</s:if>
										<s:elseif test="status == 20">交期确认，待回复</s:elseif>
										<s:elseif test="status == 30">已回复，受理中</s:elseif>
										<s:elseif test="status == 40">下单成功，待付款</s:elseif>
										<s:elseif test="status == 50">已付款，待确认</s:elseif>
										<s:elseif test="status == 60">已收款，待发货</s:elseif>
										<s:elseif test="status == 70">已发货，待收货</s:elseif>
										<s:elseif test="status == 80">已收货，订单完成</s:elseif>
										<s:elseif test="status == 99">订单已关闭</s:elseif>
									</td>
									<td><s:date name="createdate" format="yyyy/MM/dd" /></td>
									<td><s:property value="note"/></td>
									<td>
										<input type="button" value="明细" onclick="showDetail('<s:property value="id"/>');"/>
									</td>
								</tr>
							</s:iterator>
						</table>
					</div>
					<div class="pages">
						<ul>
							<li style="width: 180px;">
								<s:if test="intPageSize != null && intPageSize == 20">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" checked="checked" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
								</s:if>
								<s:elseif test="intPageSize != null && intPageSize == 30">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" checked="checked" onclick="changepagesize('30')"/>30
								</s:elseif>
								<s:else>
									显示：<input name="tmpPagesize" type="radio" value="10" checked="checked" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
								</s:else>
							</li>
							<li>第<strong>${page.startIndex + 1}</strong>页/共<strong>${page.totalPage==0?1:page.totalPage}</strong>页/共<strong>${page.totalCount}</strong>条记录</li>
							<li class="mgl15">跳转到
								<input type="text" id="pagenum" class="text" maxlength="4" size="4"/>
								<input type="button" value="GO" onclick="javascript:turnPage();"/>
							</li>
							<li class="mgl15">
								<a class="first" href="#" onclick="changePage(0);">首页</a>
							</li>
							<li>
								<s:if test="%{page.startIndex <= 0}">
									<a class="last" href="#">上一页</a>
								</s:if>
								<s:else>
									<a class="next" href="#" onclick="changePage('${page.previousIndex}');">上一页</a>
								</s:else>
							</li>
							<li>
								<s:if test="%{page.nextIndex > page.totalPage - 1}">
									<a class="last" href="#">下一页</a>
								</s:if>
								<s:else>
									<a class="next" href="#" onclick="changePage('${page.nextIndex}');">下一页</a>
								</s:else>
							</li>
							<li>
								<a class="next" href="#" onclick="changePage('${page.totalPage - 1}');">末页</a>
							</li>
						</ul>
					</div>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
