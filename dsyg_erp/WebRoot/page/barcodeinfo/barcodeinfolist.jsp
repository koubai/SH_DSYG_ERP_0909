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
<title>条形码管理</title>
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
		document.mainform.action = '../barcodeinfo/queryBarcodeInfoAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		$("#intPageSize").attr("value", pagesize);
		$("#startIndex").attr("value", "0");
		document.mainform.action = '../barcodeinfo/queryBarcodeInfoAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		$("#startIndex").attr("value", pageNum);
		document.mainform.action = '../barcodeinfo/turnBarcodeInfoAction.action';
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
					条形码管理
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="startIndex" id="startIndex"/>
				<s:hidden name="intPageSize" id="intPageSize"/>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">　产品</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strTradename" id="strTradename" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
						
						<label class="pdf10">　条形码</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strBarcode" id="strBarcode" cssStyle="width:120px;" maxlength="32" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
						
						<label class="pdf10">　条形码状态</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<select id="strOperatetype" name="strOperatetype" style="width: 80px;">
								<s:if test="strOperatetype == 10">
									<option value="">请选择</option>
									<option value="10" selected="selected">新生成</option>
									<option value="20">打印完成</option>
									<option value="30">已经贴标</option>
									<option value="40">条形码入库</option>
									<option value="50">条形码出库</option>
									<option value="60">废除</option>
								</s:if>
								<s:elseif test="strOperatetype == 20">
									<option value="">请选择</option>
									<option value="10">新生成</option>
									<option value="20" selected="selected">打印完成</option>
									<option value="30">已经贴标</option>
									<option value="40">条形码入库</option>
									<option value="50">条形码出库</option>
									<option value="60">废除</option>
								</s:elseif>
								<s:elseif test="strOperatetype == 30">
									<option value="">请选择</option>
									<option value="10">新生成</option>
									<option value="20">打印完成</option>
									<option value="30" selected="selected">已经贴标</option>
									<option value="40">条形码入库</option>
									<option value="50">条形码出库</option>
									<option value="60">废除</option>
								</s:elseif>
								<s:elseif test="strOperatetype == 40">
									<option value="">请选择</option>
									<option value="10">新生成</option>
									<option value="20">打印完成</option>
									<option value="30">已经贴标</option>
									<option value="40" selected="selected">条形码入库</option>
									<option value="50">条形码出库</option>
									<option value="60">废除</option>
								</s:elseif>
								<s:elseif test="strOperatetype == 50">
									<option value="">请选择</option>
									<option value="10">新生成</option>
									<option value="20">打印完成</option>
									<option value="30">已经贴标</option>
									<option value="40">条形码入库</option>
									<option value="50" selected="selected">条形码出库</option>
									<option value="60">废除</option>
								</s:elseif>
								<s:elseif test="strOperatetype == 60">
									<option value="">请选择</option>
									<option value="10">新生成</option>
									<option value="20">打印完成</option>
									<option value="30">已经贴标</option>
									<option value="40">条形码入库</option>
									<option value="50">条形码出库</option>
									<option value="60" selected="selected">废除</option>
								</s:elseif>
								<s:else>
									<option value="">请选择</option>
									<option value="10">新生成</option>
									<option value="20">打印完成</option>
									<option value="30">已经贴标</option>
									<option value="40">条形码入库</option>
									<option value="50">条形码出库</option>
									<option value="60">废除</option>
								</s:else>
							</select>
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
					<div class="box1" style="margin-top:-3px; margin-left: -240px; color: red;">
						<s:actionmessage />
					</div>
					<div class="icons thums">
					</div>
				</div>
				<div class="data_table" style="padding:0px;">
					<div class="tab_tittle">
						<table width="100%" border="1" cellpadding="5" cellspacing="0">
						</table>
					</div>
					<div class="tab_content" style="height: <s:property value="intPageSize * 35"/>px;">
						<div style="width: 100%; overflow: auto;">
							<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
								<tr class="tittle">
									<td width="30"></td>
									<td width="40">序号</td>
									<td width="130">产品名</td>
									<td width="130">条形码</td>
									<td width="130">条形码状态</td>
									<td width="80">扫码枪编号</td>
									<td width="110">单位数量</td>
									<td width="110">入/出库单号</td>
									<td width="80">更新时间</td>
								</tr>
								<s:iterator id="barcodeInfoList" value="barcodeInfoList" status="st1">
									<s:if test="#st1.odd==true">
										<tr class="tr_bg" onclick="checkRadioTr(this, event);">
									</s:if>
									<s:else>
										<tr onclick="checkRadioTr(this, event);">
									</s:else>
										<td><input name="radioKey" type="radio" alt="<s:property value="invoiceid"/>" value="<s:property value="id"/>"/></td>
										<td><s:property value="page.pageSize * (page.nextIndex - 1) + #st1.index + 1"/></td>
										<td><s:property value="tradename"/></td>
										<td><s:property value="barcode"/></td>
										<td>
											<s:if test="operatetype == 10">
												新生成
											</s:if>
											<s:elseif test="operatetype == 20">
												打印完成
											</s:elseif>
											<s:elseif test="operatetype == 30">
												已经贴标
											</s:elseif>
											<s:elseif test="operatetype == 40">
												条形码入库
											</s:elseif>
											<s:elseif test="operatetype == 50">
												条形码出库
											</s:elseif>
											<s:elseif test="operatetype == 60">
												废除
											</s:elseif>
											<s:else>
												<s:property value="operatetype"/>
											</s:else>
										</td>
										<td><s:property value="scanno"/></td>
										<td><s:property value="quantity"/></td>
										<td><s:property value="res01"/></td>
										<td>
											<s:date name="updatedate" format="yyyy/MM/dd HH:mm:ss"/>
										</td>
									</tr>
								</s:iterator>
							</table>
						</div>
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
