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
<title>预开发票确认</title>
<script type="text/javascript">
	$(document).ready(function(){
		var h = screen.availHeight; 
		$("#container").height(h - 20);
	});
	
	function getSelectedID() {
		var ids = "";
		var list = document.getElementsByName("radioKey");
		for(var i = 0; i < list.length; i++) {
			if(list[i].checked) {
				ids += list[i].value + ",";
			}
		}
		return ids;
	}
	
	//查询日期赋值
	function setQueryDate() {
		$("#strInvoiceDateLow").attr("value", $("#invoiceDateLow").val());
		$("#strInvoiceDateHigh").attr("value", $("#invoiceDateHigh").val());
	}

	//查询数据
	function queryList() {
		setQueryDate();
		document.mainform.action = '../invoice/queryInvoiceNewAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		$("#newIntPageSize").attr("value", pagesize);
		$("#newStartIndex").attr("value", "0");
		document.mainform.action = '../invoice/queryInvoiceNewAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		setQueryDate();
		$("#newStartIndex").attr("value", pageNum);
		document.mainform.action = '../invoice/turnInvoiceNewAction.action';
		document.mainform.submit();
	}

	//页跳转
	function turnPage() {
		var totalPage = "${newPage.totalPage}";
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
	
	//废票
	function cancelInvoice() {
		var ids = "";
		var list = document.getElementsByName("radioKey");
		var count = 0;
		for(var i = 0; i < list.length; i++) {
			if(list[i].checked) {
				ids += list[i].value + ",";
				count++;
			}
		}
//		if(count == 0) {
//			alert("请选择一条记录！");
//			return;
//		}
		$("#strIds").val(ids);
		var strInvoicenoOK = $("#strInvoicenoOK").val().trim();
		var strNote = $("#strNote").val().trim();
		if(strInvoicenoOK == "") {
			alert("发票号不能为空！");
			$("#strInvoicenoOK").focus();
			return;
		}
		//废票时备注不可以为空
		if(strNote == "") {
			alert("废票时，备注不能为空！");
			$("#strNote").focus();
			return;
		}
		if(confirm("确定作废吗？")) {
			document.mainform.action = '../invoice/cancelInvoiceAction.action';
			document.mainform.submit();
		}
	}
	
	//预开发票确认
	function invoiceOK(rtn) {
		var ids = getSelectedID();
		$("#strIds").val(ids);
		if(ids == "") {
			alert("请选择一条记录！");
			return;
		}
		var strInvoicenoOK = $("#strInvoicenoOK").val().trim();
		var strNote = $("#strNote").val().trim();
		if(strInvoicenoOK == "") {
			alert("发票号不能为空！");
			$("#strInvoicenoOK").focus();
			return;
		}
		if(confirm("确定开票吗？")) {
			$("#strReturnflg").attr("value", rtn);
			document.mainform.action = '../invoice/invoiceOKAction.action';
			document.mainform.submit();
		}
	}
	
	function delInvoice() {
		var ids = getSelectedID();
		$("#strIds").val(ids);
		if(ids == "") {
			alert("请选择一条记录！");
			return;
		}
		if(confirm("删除将不可恢复！确定删除预出库记录吗？")) {
			document.mainform.action = '../invoice/delInvoiceAction.action';
			document.mainform.submit();
		}
	}
	
	function checkCheckboxTr(tr, evt) {
		var tds = tr.getElementsByTagName("td");
		var inputs = tds[0].getElementsByTagName("input");
		var amountinputs = tds[1].getElementsByTagName("input");
		var sumAmount = parseFloat($("#sumAmount").val());
		if(inputs[0].checked) {
			inputs[0].checked = false;
			sumAmount -= parseFloat(amountinputs[0].value);
		} else {
			inputs[0].checked = true;
			sumAmount += parseFloat(amountinputs[0].value);
		}
		sumAmount = sumAmount.toFixed(2);
		$("#sumAmount").val(sumAmount);
	}
	
    function checkAll(checkall) {   
    	var i;
    	arr = document.getElementsByName('listInvoiceNewTR' );   
    	if (arr.length > 0 ){
			sumAmount = parseFloat(0);
            for(i=0;i<arr.length;i++){   
                checkItem(arr[i], checkall.checked);
            }    		
	   	}
    }
    
	function checkItem(tr, ck) {
		var tds = tr.getElementsByTagName("td");
		var inputs = tds[0].getElementsByTagName("input");
		var amountinputs = tds[1].getElementsByTagName("input");
		var sumAmount = parseFloat($("#sumAmount").val());
		if(!ck) {
			inputs[0].checked = false;
			sumAmount = parseFloat(0);
		} else {
			inputs[0].checked = true;
			sumAmount += parseFloat(amountinputs[0].value);
		}
		sumAmount = sumAmount.toFixed(2);
		$("#sumAmount").val(sumAmount);
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
					预开发票确认
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="newStartIndex" id="newStartIndex"/>
				<s:hidden name="strInvoiceDateLow" id="strInvoiceDateLow"/>
				<s:hidden name="strInvoiceDateHigh" id="strInvoiceDateHigh"/>
				<s:hidden name="strCustomerid" id="strCustomerid"/>
				<s:hidden name="newIntPageSize" id="newIntPageSize"/>
				<s:hidden name="strIds" id="strIds"/>
				<s:hidden name="strReturnflg" id="strReturnflg"/>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">开票日期</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<input type="text" disabled="disabled" style="width: 85px;" id="invoiceDateLow" value="<s:property value="strInvoiceDateLow"/>" maxlength="10" />
							<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('invoiceDateLow'));"></a>
						</div>
						<div class="box1_right"></div>
						<label>-</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<input type="text" disabled="disabled" style="width: 85px;" id="invoiceDateHigh" value="<s:property value="strInvoiceDateHigh"/>" maxlength="10" />
							<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('invoiceDateHigh'));"></a>
						</div>
						
						<label class="pdf10">　客户名</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strCustomerName" id="strCustomerName" cssStyle="width:100px;" maxlength="32" theme="simple"></s:textfield>
						</div>
					</div>
					<div class="btn" style="margin-left: 15px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="检索" onclick="queryList();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1" style="margin-top:-3px; margin-left: 180px; color: red;">
						<s:actionmessage />
					</div>
				</div>
				<div class="data_table" style="padding:0px;">
					<div class="tab_tittle">
						<table width="100%" border="1" cellpadding="5" cellspacing="0">
						</table>
					</div>
					<div class="tab_content" style="height: <s:property value="newIntPageSize * 35"/>px;">
						<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
							<tr class="tittle">
								<td width="30"><input id="allitemRadio"  name="allitemRadio" type="checkbox" onclick="checkAll(allitemRadio)" />全选</td>
								<td style="display: none;"></td>
								<td width="40">序号</td>
								<td width="110">客户名称</td>
								<td width="110">关联单据编号</td>
								<td width="110">账目编号</td>
								<td width="100">开票日期</td>
								<td width="100">开票人</td>
								<td width="100">开票金额(含税)</td>
								<td width="140">备注</td>
							</tr>
							<s:iterator id="listInvoiceNew" value="listInvoiceNew" status="st1">
								<s:if test="#st1.odd==true">
									<tr name="listInvoiceNewTR" class="tr_bg" onclick="checkCheckboxTr(this, event);">
								</s:if>
								<s:else>
									<tr name="listInvoiceNewTR" onclick="checkCheckboxTr(this, event);">
								</s:else>
									<td><input name="radioKey" type="checkbox" value="<s:property value="id"/>"/></td>
									<td style="display: none;">
										<input name="amounttax" type="hidden" value="<s:property value="amounttax"/>"/>
									</td>
									<td><s:property value="newPage.pageSize * (newPage.nextIndex - 1) + #st1.index + 1"/></td>
									<td><s:property value="customername"/></td>
									<td><s:property value="warehouserptno"/></td>
									<td><s:property value="financeno"/></td>
									<td>
										<s:date name="invoice_date" format="yyyy/MM/dd HH:mm:ss" />
									</td>
									<td><s:property value="invoide_mem_id"/></td>
									<td align="right"><s:property value="amounttax"/></td>
									<td>
										<div noWrap title="<s:property value="note"/>" style="width:135px;text-overflow:ellipsis;overflow:hidden">
											<s:property value="note"/>
										</div>
									</td>
								</tr>
							</s:iterator>
						</table>
					</div>
					<div class="pages">
						<ul>
							<li style="width: 400px;">
								<s:if test="newIntPageSize != null && newIntPageSize == 20">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" checked="checked" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" onclick="changepagesize('200')"/>200
								</s:if>
								<s:elseif test="newIntPageSize != null && newIntPageSize == 30">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" checked="checked" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" onclick="changepagesize('200')"/>200
								</s:elseif>
								<s:elseif test="newIntPageSize != null && newIntPageSize == 50">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" checked="checked" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" onclick="changepagesize('200')"/>200
								</s:elseif>
								<s:elseif test="newIntPageSize != null && newIntPageSize == 100">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" checked="checked" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" onclick="changepagesize('200')"/>200
								</s:elseif>
								<s:elseif test="newIntPageSize != null && newIntPageSize == 150">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" checked="checked" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" onclick="changepagesize('200')"/>200
								</s:elseif>
								<s:elseif test="newIntPageSize != null && newIntPageSize == 200">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" checked="checked" onclick="changepagesize('200')"/>200
								</s:elseif>
								<s:else>
									显示：<input name="tmpPagesize" type="radio" value="10" checked="checked" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
									<input name="tmpPagesize" type="radio" value="150" onclick="changepagesize('150')"/>150
									<input name="tmpPagesize" type="radio" value="200" onclick="changepagesize('200')"/>200
								</s:else>
							</li>
							<li>第<strong>${newPage.startIndex + 1}</strong>页/共<strong>${newPage.totalPage==0?1:newPage.totalPage}</strong>页/共<strong>${newPage.totalCount}</strong>条记录</li>
							<li class="mgl15">跳转到
								<input type="text" id="pagenum" class="text" maxlength="4" size="4"/>
								<input type="button" value="GO" onclick="javascript:turnPage();"/>
							</li>
							<li class="mgl15">
								<a class="first" href="#" onclick="changePage(0);">首页</a>
							</li>
							<li>
								<s:if test="%{newPage.startIndex <= 0}">
									<a class="last" href="#">上一页</a>
								</s:if>
								<s:else>
									<a class="next" href="#" onclick="changePage('${newPage.previousIndex}');">上一页</a>
								</s:else>
							</li>
							<li>
								<s:if test="%{newPage.nextIndex > newPage.totalPage - 1}">
									<a class="last" href="#">下一页</a>
								</s:if>
								<s:else>
									<a class="next" href="#" onclick="changePage('${newPage.nextIndex}');">下一页</a>
								</s:else>
							</li>
							<li>
								<a class="next" href="#" onclick="changePage('${newPage.totalPage - 1}');">末页</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="btns" style="margin-top:40px; margin-left: -90px;">
					<table border="0" style="margin:0 auto;">
						<tr>
							<td width="200"></td>
							<td align="right">发票号：</td>
							<td>
								<s:textfield id="strInvoicenoOK" name="strInvoicenoOK" cssStyle="width: 160px;" theme="simple"></s:textfield>
							</td>
							<td align="right" style="width: 100px;">合计金额：</td>
							<td>
								<input id="sumAmount" type="text" value="0.00" style="width: 150px;"/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td align="right">备注：</td>
							<td colspan="3">
								<s:textfield id="strNote" name="strNote" cssStyle="width: 280px;" theme="simple"></s:textfield>
							</td>
						</tr>
					</table>
					<table border="0" style="margin:0 auto;">
						<tr>
							<td width="200"></td>
							<td width="200">
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="开票" onclick="invoiceOK('0');" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td width="200">
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input100" type="button" value="开票(含退货)" onclick="invoiceOK('1');" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="作废" onclick="cancelInvoice();" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="删除" onclick="delInvoice();" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
