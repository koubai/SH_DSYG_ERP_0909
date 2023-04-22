<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>更新一览</title>
<script type="text/javascript">
	$(function() {
	});
	
	function histDetail(id) {
		var id = getSelectedID();
		if(id == "") {
			alert("请选择一条记录！");
			return;
		} else {
			var url = "<%=request.getContextPath()%>/sales/showHistDetailAction.action?salesHistId=" + id + "&date=" + encodeURI(new Date());
			//window.showModalDialog(url, window, "dialogheight:680px;dialogwidth:1200px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no;scrollbars=yes;");
			window.open(url, "_blank", "height=680, width=1200, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
			
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
	
	//翻页
	function changePage(pageNum) {
		$("#startIndexHist").attr("value", pageNum);
		document.mainform.action = '../sales/turnSalesHistListAction.action';
		document.mainform.submit();
	}
	
	function showPurchase(id){
		var url = '<%=request.getContextPath()%>/warehouse/showProductPurchasePage.action';
		//strFlag=1采购单，strFlag=2销售单
		url += "?strProdoctid=" + id + "&strFlag=1" + "&date=" + encodeURI(new Date());
		//window.showModalDialog(url, window, "dialogheight:400px;dialogwidth:600px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		showModalDialogN(url, window, "dialogheight:400px;dialogwidth:600px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
	}
	
	function showSales(id){
		var url = '<%=request.getContextPath()%>/warehouse/showProductSalesPage.action';
		//strFlag=1采购单，strFlag=2销售单
		url += "?strProdoctid=" + id + "&strFlag=2" + "&date=" + encodeURI(new Date());
		//window.showModalDialog(url, window, "dialogheight:400px;dialogwidth:600px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		showModalDialogN(url, window, "dialogheight:400px;dialogwidth:600px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
	}

	//页跳转
	function turnPage() {
		var totalPage = "${pageHist.totalPage}";
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
<body style="background: url(''); overflow-x:hidden;overflow-y:hidden;">
<s:form id="mainform" name="mainform" method="POST">
	<s:hidden name="startIndexHist" id="startIndexHist"/>
	<s:hidden name="intPageSizeHist" id="intPageSizeHist"/>
	<div id="container" style="width: 100%; height: 100%;">
		<div class="searchbox">
			<div class="box1">
				<label class="pdf10">更新一览</label>
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
						<td width="30"></td>
						<td width="35">序号</td>
						<td width="80">更新时间</td>
						<td width="80">更新者</td>
					</tr>
					<s:iterator id="salesHistList" value="salesHistList" status="st1">
						<s:if test="#st1.odd==true">
							<tr class="tr_bg" onclick="checkRadioTr(this, event);">
						</s:if>
						<s:else>
							<tr onclick="checkRadioTr(this, event);"> 
						</s:else>
							<td><input name="radioKey" type="radio" value="<s:property value="id"/>"/></td>
							<td><s:property value="pageHist.pageSize * (pageHist.nextIndex - 1) + #st1.index + 1"/></td>
							<!-- <td><s:property value="updatedate"/></td> -->
							<td><fmt:formatDate value='${updatedate}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
							<td><s:property value="updateuid"/></td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<div class="pages">
				<ul>
					<li>第<strong>${pageHist.startIndex + 1}</strong>页/共<strong>${pageHist.totalPage==0?1:pageHist.totalPage}</strong>页/共<strong>${pageHist.totalCount}</strong>条记录</li>
					<li class="mgl15">跳转到
						<input type="text" id="pagenum" class="text" maxlength="4" size="4"/>
						<input type="button" value="GO" onclick="javascript:turnPage();"/>
					</li>
					<li class="mgl15">
						<a class="first" href="#" onclick="changePage(0);">首页</a>
					</li>
					<li>
						<s:if test="%{pageHist.startIndex <= 0}">
							<a class="last" href="#">上一页</a>
						</s:if>
						<s:else>
							<a class="next" href="#" onclick="changePage('${pageHist.previousIndex}');">上一页</a>
						</s:else>
					</li>
					<li>
						<s:if test="%{pageHist.nextIndex > pageHist.totalPage - 1}">
							<a class="last" href="#">下一页</a>
						</s:if>
						<s:else>
							<a class="next" href="#" onclick="changePage('${pageHist.nextIndex}');">下一页</a>
						</s:else>
					</li>
					<li>
						<a class="next" href="#" onclick="changePage('${pageHist.totalPage - 1}');">末页</a>
					</li>
				</ul>
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
					<td>
						<div class="btn" style="margin-left: 0px;">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input type="button" class="input80" value="详细" onclick="histDetail();"/>
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
