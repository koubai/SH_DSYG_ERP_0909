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
<title>产品销售统计</title>
<script type="text/javascript">
	$(document).ready(function(){
		var h = screen.availHeight; 
		$("#container").height(h - 20);
	});
	
	//查询日期赋值
	function setQueryDate() {
		$("#strStartdate").attr("value", $("#tmpDateLow").val());
		$("#strEnddate").attr("value", $("#tmpDateHigh").val());
	}
	
	//查询数据
	function queryList(purYear) {
		$("#yearOnline").attr("value", purYear);
		setQueryDate();
		document.mainform.action = '../salesstatistics/querySalesStatisticsAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		setQueryDate();
		$("#intPageSize").attr("value", pagesize);
		$("#startIndex").attr("value", "0");
		document.mainform.action = '../salesstatistics/querySalesStatisticsAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		setQueryDate();
		$("#startIndex").attr("value", pageNum);
		document.mainform.action = '../salesstatistics/turnSalesStatisticsAction.action';
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
	
	function changeDate(datetype) {
		$("#strDateType").attr("value", datetype);
		document.mainform.action = '../salesstatistics/querySalesStatisticsByDateAction.action';
		document.mainform.submit();
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
					产品销售统计
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden id="strStartdate" name="strStartdate"></s:hidden>
				<s:hidden id="strEnddate" name="strEnddate"></s:hidden>
				<s:hidden name="startIndex" id="startIndex"/>
				<s:hidden name="intPageSize" id="intPageSize"/>
				<s:hidden name="strDateType" id="strDateType"/>
				<s:hidden id="yearOnline" name="yearOnline"></s:hidden>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">期间类型</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<input type="text" disabled="disabled" style="width: 105px;" id="tmpDateLow" value="<s:property value="strStartdate"/>" maxlength="10" />
							<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpDateLow'));"></a>
						</div>
						<div class="box1_right"></div>
						<label>-</label>
						<div class="box1_left"></div>
						<div class="box1_center date_input">
							<input type="text" disabled="disabled" style="width: 105px;" id="tmpDateHigh" value="<s:property value="strEnddate"/>" maxlength="10" />
							<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpDateHigh'));"></a>
						</div>
						<div class="box1_right"></div>
					</div>
				</div>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">类型</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<select id="strTheme1" name="strTheme1">
								<option value="" selected="selected">请选择</option>
								<s:iterator id="goodsList" value="goodsList" status="st1">
									<option value="<s:property value="code"/>" <s:if test="%{goodsList[#st1.index].code == strTheme1}">selected</s:if>><s:property value="fieldname"/></option>
								</s:iterator>
							</select>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<label class="pdf10">品名</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strTradename" id="strTradename" cssClass="input120" maxlength="32" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<label class="pdf10">包装</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strItem10" id="strItem10" cssClass="input120" maxlength="32" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						<label class="pdf10">关键字</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<s:textfield name="strKeyword" id="strKeyword" cssClass="input120" maxlength="32" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn" style="margin-left: 160px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="检索" onclick="queryList('');"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1" style="margin-top:-3px; margin-left: 240px; color: red;">
						<s:actionmessage />
					</div>
				</div>
				<div class="tittle">
					<s:iterator value="yearArray" var="var">
					<div>
						<a href="#" onclick="queryList('<s:property value="var"/>');">
							<div class="tittle_left">
							</div>
							<s:if test="#var == yearOnline">
							<div class="tittle_center"">
							</s:if>
							<s:else>
							<div class="tittle_center" style="color:#000;">
							</s:else>
								<s:property value="var"/>
							</div>
							<div class="tittle_right">
							</div>
						</a>
					</div>
					</s:iterator>
				</div>
				<div class="data_table" style="padding:0px;">
					<div class="tab_tittle">
						<table width="100%" border="1" cellpadding="5" cellspacing="0">
						</table>
					</div>
					<div class="tab_content" style="height: <s:property value="intPageSize * 35"/>px;">
						<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
							<tr class="tittle">
								<td rowspan="2" width="40">序号</td>
								<td rowspan="2" width="140">主题</td>
								<td rowspan="2" width="120">品名</td>
								<td rowspan="2" width="120">规格</td>
								<td rowspan="2" width="60">颜色</td>
								<td rowspan="2" width="60">包装</td>
								<td rowspan="2" width="60">单位</td>
								<td rowspan="2" width="80">形式</td>
								<td rowspan="2" width="80">产地</td>
								<s:iterator id="monthArray" value="monthArray" status="st1">
								<td colspan="3" align="center" width="280"><s:property /></td>
								</s:iterator>
							</tr>
							<tr class="tittle">
								<s:iterator id="monthArray" value="monthArray" status="st1">
								<td width="80">销售数量</td>
								<td width="100">销售金额（含税）</td>
								<td width="100">销售金额比例</td>
								</s:iterator>
							</tr>
							<s:iterator id="listSalesStatistics" value="listSalesStatistics" status="st1">
								<s:if test="#st1.odd==true">
									<tr class="tr_bg">
								</s:if>
								<s:else>
									<tr>
								</s:else>
									<td><s:property value="page.pageSize * (page.nextIndex - 1) + #st1.index + 1"/></td>
									<td>
										<s:iterator id="goodsList" value="goodsList" status="st3">
											<s:if test="%{goodsList[#st3.index].code == listSalesStatistics[#st1.index].fieldno}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
									<td>
										<s:property value="tradename"/>
									</td>
									<td><s:property value="typeno"/></td>
									<td>
										<s:iterator id="colorList" value="colorList" status="st3">
											<s:if test="%{colorList[#st3.index].code == listSalesStatistics[#st1.index].color}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
									<td><s:property value="item10"/></td>
									<td>
										<s:iterator id="unitList" value="unitList" status="st3">
											<s:if test="%{unitList[#st3.index].code == listSalesStatistics[#st1.index].unit}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
									<td>
										<s:if test='%{listSalesStatistics[#st1.index].packaging == "0"}'>整箱</s:if>
										<s:elseif test='%{listSalesStatistics[#st1.index].packaging == "1"}'>乱尺</s:elseif>
										<s:else>
											<s:property value="packaging"/>
										</s:else>
									</td>
									<td>
										<s:iterator id="makeareaList" value="makeareaList" status="st3">
											<s:if test="%{makeareaList[#st3.index].code == listSalesStatistics[#st1.index].makearea}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
								<s:iterator var="ma" value="monthArray" status="st1">
									<s:if test="#ma != null && #ma.substring(5,7) == '01'">
										<td><s:property value="Y_quantity_Month_01 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_01"/></td>
										<td>
											<s:if test="Y_taxamount_Month_01 != null && sumSalesStatisticsDto.Y_taxamount_Month_01 != null">
												<s:property value="Y_taxamount_Month_01 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_01" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '02'">
										<td><s:property value="Y_quantity_Month_02 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_02"/></td>
										<td>
											<s:if test="Y_taxamount_Month_02 != null && sumSalesStatisticsDto.Y_taxamount_Month_02 != null">
												<s:property value="Y_taxamount_Month_02 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_02" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '03'">
										<td><s:property value="Y_quantity_Month_03 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_03"/></td>
										<td>
											<s:if test="Y_taxamount_Month_03 != null && sumSalesStatisticsDto.Y_taxamount_Month_03 != null">
												<s:property value="Y_taxamount_Month_03 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_03" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '04'">
										<td><s:property value="Y_quantity_Month_04 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_04"/></td>
										<td>
											<s:if test="Y_taxamount_Month_04 != null && sumSalesStatisticsDto.Y_taxamount_Month_04 != null">
												<s:property value="Y_taxamount_Month_04 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_04" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '05'">
										<td><s:property value="Y_quantity_Month_05 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_05"/></td>
										<td>
											<s:if test="Y_taxamount_Month_05 != null && sumSalesStatisticsDto.Y_taxamount_Month_05 != null">
												<s:property value="Y_taxamount_Month_05 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_05" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '06'">
										<td><s:property value="Y_quantity_Month_06 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_06"/></td>
										<td>
											<s:if test="Y_taxamount_Month_06 != null && sumSalesStatisticsDto.Y_taxamount_Month_06 != null">
												<s:property value="Y_taxamount_Month_06 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_06" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '07'">
										<td><s:property value="Y_quantity_Month_07 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_07"/></td>
										<td>
											<s:if test="Y_taxamount_Month_07 != null && sumSalesStatisticsDto.Y_taxamount_Month_07 != null">
												<s:property value="Y_taxamount_Month_07 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_07" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '08'">
										<td><s:property value="Y_quantity_Month_08 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_08"/></td>
										<td>
											<s:if test="Y_taxamount_Month_08 != null && sumSalesStatisticsDto.Y_taxamount_Month_08 != null">
												<s:property value="Y_taxamount_Month_08 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_08" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '09'">
										<td><s:property value="Y_quantity_Month_09 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_09"/></td>
										<td>
											<s:if test="Y_taxamount_Month_09 != null && sumSalesStatisticsDto.Y_taxamount_Month_09 != null">
												<s:property value="Y_taxamount_Month_09 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_09" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '10'">
										<td><s:property value="Y_quantity_Month_10 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_10"/></td>
										<td>
											<s:if test="Y_taxamount_Month_10 != null && sumSalesStatisticsDto.Y_taxamount_Month_10 != null">
												<s:property value="Y_taxamount_Month_10 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_10" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '11'">
										<td><s:property value="Y_quantity_Month_11 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_11"/></td>
										<td>
											<s:if test="Y_taxamount_Month_11 != null && sumSalesStatisticsDto.Y_taxamount_Month_11 != null">
												<s:property value="Y_taxamount_Month_11 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_11" />%
											</s:if>
										</td>	
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '12'">
										<td><s:property value="Y_quantity_Month_12 * -1"/></td>
										<td><s:property value="Y_taxamount_Month_12"/></td>
										<td>
											<s:if test="Y_taxamount_Month_12 != null && sumSalesStatisticsDto.Y_taxamount_Month_12 != null">
												<s:property value="Y_taxamount_Month_12 * 100 / sumSalesStatisticsDto.Y_taxamount_Month_12" />%
											</s:if>
										</td>	
									</s:if>
								</s:iterator>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="9" align="right">
									合计
								</td>
								<s:iterator var="ma" value="monthArray" status="st1">
									<s:if test="#ma != null && #ma.substring(5,7) == '01'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_01 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_01 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_01 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_01"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '02'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_02 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_02 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_02 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_02"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '03'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_03 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_03 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_03 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_03"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '04'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_04 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_04 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_04 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_04"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '05'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_05 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_05 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_05 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_05"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '06'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_06 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_06 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_06 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_06"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '07'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_07 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_07 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_07 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_07"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '08'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_08 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_08 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_08 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_08"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '09'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_09 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_09 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_09 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_09"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '10'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_10 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_10 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_10 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_10"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '11'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_11 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_11 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_11 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_11"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
									<s:if test="#ma != null && #ma.substring(5,7) == '12'">
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_quantity_Month_12 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_quantity_Month_12 * -1"/>
											</s:else>
										</td>
										<td>
											<s:if test="sumSalesStatisticsDto == null || sumSalesStatisticsDto.Y_taxamount_Month_12 == null">
												0
											</s:if>
											<s:else>
												<s:property value="sumSalesStatisticsDto.Y_taxamount_Month_12"/>
											</s:else>
										</td>
										<td>
										</td>
									</s:if>
								</s:iterator>
							</tr>
						</table>
					</div>
					<div class="pages">
						<ul>
							<li style="width: 300px;">
								<s:if test="intPageSize != null && intPageSize == 20">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" checked="checked" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
								</s:if>
								<s:elseif test="intPageSize != null && intPageSize == 30">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" checked="checked" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
								</s:elseif>
								<s:elseif test="intPageSize != null && intPageSize == 50">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" checked="checked" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
								</s:elseif>
								<s:elseif test="intPageSize != null && intPageSize == 100">
									显示：<input name="tmpPagesize" type="radio" value="10" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" checked="checked" onclick="changepagesize('100')"/>100
								</s:elseif>
								<s:else>
									显示：<input name="tmpPagesize" type="radio" value="10" checked="checked" onclick="changepagesize('10')"/>10 
									<input name="tmpPagesize" type="radio" value="20" onclick="changepagesize('20')"/>20 
									<input name="tmpPagesize" type="radio" value="30" onclick="changepagesize('30')"/>30
									<input name="tmpPagesize" type="radio" value="50" onclick="changepagesize('50')"/>50
									<input name="tmpPagesize" type="radio" value="100" onclick="changepagesize('100')"/>100
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
