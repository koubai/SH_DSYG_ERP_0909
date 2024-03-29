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
<title>发货单一览</title>
<script type="text/javascript">
	$(document).ready(function(){
		var h = screen.availHeight; 
		$("#container").height(h - 20);
	});
	
	function showDetail() {
		var id = getSelectedID();
		if(id == "") {
			alert("请选择一条记录！");
			return;
		} else {
			var url = '<%=request.getContextPath()%>/warehouserpt/showUpdWarehouserptOutItemAction.action';
			url += "?updWarehouserptId=" + id + "&date=" + encodeURI(new Date());
			////window.showModalDialog(url, window, "dialogheight:800px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
			//document.mainform.action = "../warehouserpt/showUpdWarehouserptOutItemAction.action?updWarehouserptId=" + id;
			//document.mainform.submit();
			showModalDialogN(url, window, "dialogheight:800px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		}
	}
	
	function upd() {
		var id = getSelectedID();
		if(id == "") {
			alert("请选择一条记录！");
			return;
		} else {
			document.mainform.action = "../warehouserpt/showUpdWarehouserptOutAction.action?updWarehouserptId=" + id;
			document.mainform.submit();
		}
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
		$("#strCreatedateLow").attr("value", $("#createdateLow").val());
		$("#strCreatedateHigh").attr("value", $("#createdateHigh").val());
		document.mainform.action = '../warehouserpt/queryWarehouserptOutAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		$("#intPageSize").attr("value", pagesize);
		$("#startIndex").attr("value", "0");
		$("#strCreatedateLow").attr("value", $("#createdateLow").val());
		$("#strCreatedateHigh").attr("value", $("#createdateHigh").val());
		document.mainform.action = '../warehouserpt/queryWarehouserptOutAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		$("#startIndex").attr("value", pageNum);
		$("#strCreatedateLow").attr("value", $("#createdateLow").val());
		$("#strCreatedateHigh").attr("value", $("#createdateHigh").val());
		document.mainform.action = '../warehouserpt/turnWarehouserptOutAction.action';
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
	
	function exportData() {
		document.mainform.action = '../warehouserpt/exportWarehouserptOutAction.action';
		document.mainform.submit();
	}
	
	//导出库清单
	function exportWarehouseList() {
		var totalCount = "${page.totalCount}";
		if (totalCount > 200){
			alert("超过200条出库单记录了！");
			return;
		}	
		document.mainform.action = '../warehouserpt/exportWarehouserptOutAllListAction.action';
		document.mainform.submit();
	}
	
	//运费评估
	function assess() {
		var id = getSelectedID();
		if(id == "") {
			alert("请选择一条记录！");
			return;
		} else {
			//获得客户ID
			var customerId = "";
			var list = document.getElementsByName("radioKey");
			for(var i = 0; i < list.length; i++) {
				if(list[i].checked) {
					customerId = list[i].alt;
					break;
				}
			}
			var url = '<%=request.getContextPath()%>/assess/showAssessExpressFeeAction.action?strCustomerId=' + customerId + '&&updWarehouserptId=' + id;
			//window.showModalDialog(url, window, "dialogheight:600px;dialogwidth:900px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
			showModalDialogN(url, window, "dialogheight:600px;dialogwidth:900px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
			//window.open(url);
		}
	}
	
	function showDeliveryList(rptid) {
		var url = '<%=request.getContextPath()%>/warehouserpt/showDeliveryListAction.action?rptDeliveryId=' + rptid;
		//window.showModalDialog(url, window, "dialogheight:600px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		showModalDialogN(url, window, "dialogheight:600px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
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
					发货单一览
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="startIndex" id="startIndex"/>
				<s:hidden name="intPageSize" id="intPageSize"/>
				<s:hidden name="strCreatedateLow" id="strCreatedateLow"/>
				<s:hidden name="strCreatedateHigh" id="strCreatedateHigh"/>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">发货单号</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<s:textfield name="strWarehouseno" cssStyle="width:200px;" id="strWarehouseno" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<select id="strLocation" name="strLocation" style="width: 80px;">
								<s:if test='strLocation == "1"'>
									<option value="">请选择</option>
									<option value="1" selected="selected">上海</option>
									<option value="2">深圳</option>
								</s:if>
								<s:elseif test='strLocation == "2"'>
									<option value="">请选择</option>
									<option value="1">上海</option>
									<option value="2" selected="selected">深圳</option>
								</s:elseif>
								<s:else>
									<option value="" selected="selected">请选择</option>
									<option value="1">上海</option>
									<option value="2">深圳</option>
								</s:else>
							</select></div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<label class="pdf10">客户名</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<s:textfield name="strSuppliername" cssStyle="width:200px;" id="strSuppliername" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<label class="pdf10">销售单号</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strNo" cssStyle="width:200px;" id="strNo" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
				</div>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">创建日期</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<input type="text" disabled="disabled" style="width: 105px;" id="createdateLow" value="<s:property value="strCreatedateLow"/>" maxlength="10" />
							<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('createdateLow'));"></a>
						</div>
						<div class="box1_right"></div>
						<label>-</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<input type="text" disabled="disabled" style="width: 105px;" id="createdateHigh" value="<s:property value="strCreatedateHigh"/>" maxlength="10" />
							<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('createdateHigh'));"></a>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn" style="margin-left: 160px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="检索" onclick="queryList();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1" style="margin-top:-3px; margin-left: -240px; color: red;">
						<s:actionmessage />
					</div>
					<div class="icons thums">
						<a class="edit" onclick="upd();">编辑</a>
						<!--
						<a class="add" onclick="add();">增加</a>
						<a class="delete" onclick="del();">删除</a>
						-->
					</div>
					<div class="btn" style="margin-left: 160px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="运费评估" onclick="assess();" />
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn" style="margin-left: 160px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" value="出库清单" onclick="exportWarehouseList();" />
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
								<td width="30"></td>
								<td width="40">序号</td>
								<td width="120">发货单号</td>
								<td width="60">创建时间</td>
								<td width="60">来源类型</td>
								<td width="120">客户</td>
								<!-- <td width="80">联系人</td> -->
								<td width="80">制单人</td>
								<td width="80">含税金额</td>
								<!--<td width="80">利润率</td>
								<td width="120">快递单号</td>
								-->
								<td width="120">快递公司</td>
								<td width="80">快递费</td>
								<td width="80">快递费比例</td>
							</tr>
							<s:iterator id="warehouserptList" value="warehouserptList" status="st1">
								<s:if test="#st1.odd==true">
									<tr class="tr_bg" onclick="checkRadioTr(this, event);">
								</s:if>
								<s:else>
									<tr onclick="checkRadioTr(this, event);">
								</s:else>
									<td><input name="radioKey" type="radio" alt="<s:property value="supplierid"/>" value="<s:property value="id"/>"/></td>
									<td><s:property value="page.pageSize * (page.nextIndex - 1) + #st1.index + 1"/></td>
									<s:if test="rptlogCount > 1">
										<td style="cursor:pointer; " onclick="showDeliveryList('<s:property value="id"/>');">
											<s:property value="warehouseno"/>
										</td>
									</s:if>
									<s:else>
										<td>
											<s:property value="warehouseno"/>
										</td>
									</s:else>
									<td><s:property value="createdate"/></td>
									<td>
										<s:if test="%{warehousetype == 1}">
											入库单
										</s:if>
										<s:elseif test="%{warehousetype == 2}">
											发货单
										</s:elseif>
										<s:elseif test="%{warehousetype == 3}">
											退换货
										</s:elseif>
										<s:elseif test="%{warehousetype == 4}">
											手动录入
										</s:elseif>
										<s:else>
											<s:property value="warehousetype"/>
										</s:else>
									</td>
									<td><s:property value="suppliername"/></td>
									<!--<td><s:property value="suppliermanager"/></td>-->
									<td><s:property value="createuid"/></td>
									<td align="right"><s:property value="totaltaxamount"/></td>
									<!--
									<td align="right">
										<s:if test="%{res09 != null && res09 != ''}">
											<s:property value="res09"/>%
										</s:if>
									</td>
									<td><s:property value="expressno"/></td>
									-->
									<s:if test="rptlogCount > 1">
										<td style="cursor:pointer; background-color: yellow;" onclick="showDeliveryList('<s:property value="id"/>');">
											<s:property value="expressname"/>
										</td>
									</s:if>
									<s:else>
										<td>
											<s:property value="expressname"/>
										</td>
									</s:else>
									<s:if test="rptlogCount > 1">
										<td align="right" style="cursor:pointer; background-color: yellow;" onclick="showDeliveryList('<s:property value="id"/>');">
											<s:property value="expresstaxamount"/>
										</td>
									</s:if>
									<s:else>
										<td align="right">
											<s:property value="expresstaxamount"/>
										</td>
									</s:else>
									<s:if test="expresstaxrate != null">
										<td align="right" >
											<s:property value="expresstaxrate"/> % 
										</td>
									</s:if>
									<s:else>
										<td align="right">
											<s:property value=""/>
										</td>
									</s:else>
								</tr>
							</s:iterator>
						</table>
					</div>
					<div class="pages">
						合计含税金额: <s:textfield name="strTotalAmount" id="strTotalAmount" cssStyle="width:250px;" maxlength="32" theme="simple"></s:textfield>
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
				<div class="btns" style="margin-top:40px; margin-left:-90px;">
					<table border="0" style="margin:0 auto;">
						<tr>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="详细" onclick="showDetail();" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<!--  <div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="导出" onclick="exportData();" />
									</div>
									<div class="box1_right"></div>
								</div> -->
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
