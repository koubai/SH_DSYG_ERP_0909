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
<title>发票一览</title>
<script type="text/javascript">
	$(document).ready(function(){
		var h = screen.availHeight; 
		$("#container").height(h - 20);
	});
	
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
		document.mainform.action = '../invoice/queryInvoiceOKAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		$("#okIntPageSize").attr("value", pagesize);
		$("#okStartIndex").attr("value", "0");
		document.mainform.action = '../invoice/queryInvoiceOKAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		$("#okStartIndex").attr("value", pageNum);
		document.mainform.action = '../invoice/turnInvoiceOKAction.action';
		document.mainform.submit();
	}

	//页跳转
	function turnPage() {
		var totalPage = "${okPage.totalPage}";
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
					发票一览
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="okStartIndex" id="okStartIndex"/>
				<s:hidden name="okIntPageSize" id="okIntPageSize"/>
				<div class="searchbox">
					<div class="box1">
						<div class="box1_right"></div>
						<label class="pdf10">　发票号</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strInvoiceno" id="strInvoiceno" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
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
					<div class="tab_content" style="height: <s:property value="okIntPageSize * 35"/>px;">
						<div style="width: 120%; overflow: auto;">
							<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
								<tr class="tittle">
									<td width="30" style="display: none;"></td>
									<td width="40">序号</td>
									<td width="130">发票号</td>
									<td width="130">关联单据编号</td>
									<td width="130">账目编号</td>
									<td width="150">客户名称</td>
									<td width="130">开票日期</td>
									<td width="70">开票人</td>
									<td width="110">开票金额(含税)</td>
									<td width="160">备注</td>
									<td width="70">状态</td>
									<td width="130">退货单据号</td>
									<td width="130">退货账目编号</td>
								</tr>
								<s:iterator id="listInvoiceOk" value="listInvoiceOk" status="st1">
									<s:if test="#st1.odd==true">
										<tr class="tr_bg" onclick="checkCheckboxTr(this, event);">
									</s:if>
									<s:else>
										<tr onclick="checkCheckboxTr(this, event);">
									</s:else>
										<td style="display: none;"><input name="radioKey" type="checkbox" value="<s:property value="id"/>"/></td>
										<td><s:property value="okPage.pageSize * (okPage.nextIndex - 1) + #st1.index + 1"/></td>
										<td><s:property value="invoiceno"/></td>
										<td><s:property value="warehouserptno"/></td>
										<td><s:property value="financeno"/></td>
										<td><s:property value="customername"/></td>
										<td>
											<s:date name="invoice_date" format="yyyy/MM/dd HH:mm:ss" />
										</td>
										<td><s:property value="invoide_mem_id"/></td>
										<td align="right"><s:property value="amounttax"/></td>
										<td>
											<div noWrap title="<s:property value="note"/>" style="width:150px;text-overflow:ellipsis;overflow:hidden">
												<s:property value="note"/>
											</div>
										</td>
										<td>
											<s:if test="status == 0">
												预开票
											</s:if>
											<s:elseif test="status == 1">
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
										<td><s:property value="warehouserptdelno"/></td>
										<td><s:property value="finanacedelno"/></td>
									</tr>
								</s:iterator>
							</table>
						</div>
					</div>
					<div class="pages">
						<ul>
							<li style="width: 180px;">
								<s:if test="okIntPageSize != null && okIntPageSize == 20">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" checked="checked" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
								</s:if>
								<s:elseif test="okIntPageSize != null && okIntPageSize == 30">
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
							<li>第<strong>${okPage.startIndex + 1}</strong>页/共<strong>${okPage.totalPage==0?1:okPage.totalPage}</strong>页/共<strong>${okPage.totalCount}</strong>条记录</li>
							<li class="mgl15">跳转到
								<input type="text" id="pagenum" class="text" maxlength="4" size="4"/>
								<input type="button" value="GO" onclick="javascript:turnPage();"/>
							</li>
							<li class="mgl15">
								<a class="first" href="#" onclick="changePage(0);">首页</a>
							</li>
							<li>
								<s:if test="%{okPage.startIndex <= 0}">
									<a class="last" href="#">上一页</a>
								</s:if>
								<s:else>
									<a class="next" href="#" onclick="changePage('${okPage.previousIndex}');">上一页</a>
								</s:else>
							</li>
							<li>
								<s:if test="%{okPage.nextIndex > okPage.totalPage - 1}">
									<a class="last" href="#">下一页</a>
								</s:if>
								<s:else>
									<a class="next" href="#" onclick="changePage('${okPage.nextIndex}');">下一页</a>
								</s:else>
							</li>
							<li>
								<a class="next" href="#" onclick="changePage('${okPage.totalPage - 1}');">末页</a>
							</li>
						</ul>
					</div>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
