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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>入库单明细</title>
<script type="text/javascript">
	function upd() {
		if(checkItem()) {
			if(confirm("确定提交吗？")) {
				document.mainform.action = "../warehouserpt/updWarehouserptInAction.action";
				document.mainform.submit();
			}
		}
	}
	
	//验证数据格式
	function checkItem() {
		//快递ID
		var expressid = $("#expressid").val().trim();
		//快递名
		var expressname = $("#expressname").val().trim();
		//快递地址
		var expressaddress = $("#expressaddress").val().trim();
		//转运费用合计
		var expresstaxamount = $("#expresstaxamount").val().trim();
		//快递联系人
		var expressmanager = $("#expressmanager").val().trim();
		//快递联系人电话
		var expresstel = $("#expresstel").val().trim();
		//快递联系人传真
		var expressfax = $("#expressfax").val().trim();
		//收货日期
		var tmpWarehousedate = $("#tmpWarehousedate").val().trim();
		//信箱
		var expressmail = $("#expressmail").val().trim();
		//备注
		var tempNote = $("#tempNote").val().trim();
		if(expressid == "") {
			alert("请选择快递！");
			$("#expressid").focus();
			return;
		}
		if(expressname == "") {
			alert("快递名称不能为空！");
			$("#expressname").focus();
			return;
		}
/*		if(expressaddress == "") {
			alert("快递地址不能为空！");
			$("#expressaddress").focus();
			return;
		}
*/		
		if(expresstaxamount == "") {
			alert("转运费用合计不能为空！");
			$("#expresstaxamount").focus();
			return;
		}
		if(!isReal(expresstaxamount)) {
			alert("转运费用合计必须为大于0的实数！");
			$("#expresstaxamount").focus();
			return;
		}
		if(expressmanager == "") {
			alert("快递联系人不能为空！");
			$("#expressmanager").focus();
			return;
		}
		if(expresstel == "") {
			alert("快递联系人电话不能为空！");
			$("#expresstel").focus();
			return;
		}
/*		if(expressfax == "") {
			alert("快递联系人传真不能为空！");
			$("#expressfax").focus();
			return;
		}
*/		
		if(tmpWarehousedate == "") {
			alert("收货日期不能为空！");
			$("#tmpWarehousedate").focus();
			return;
		}
/*		if(expressmail == "") {
			alert("信箱不能为空！");
			$("#expressmail").focus();
			return;
		}
*/		
		var b = false;
		//验证退换货
		var list = document.getElementsByName("tmpHasbroken");
		for(var i = 0; i < list.length; i++) {
			var key = list[i].alt;
			var key1 = key.replace("brokennum", "hasbroken");
			if(list[i].checked) {
				var keynum = key.replace("brokennum", "num");
				var brokennum = $("#" + key).val();
				var num = $("#" + keynum).val();
				if(brokennum == "") {
					alert("请输入退换货数量！");
					$("#" + key).focus();
					return;
				}
				if(!isNumber(brokennum)) {
					alert("退换货数量必须是大于0的数字！");
					$("#" + key).focus();
					return;
				}
				if(parseInt(brokennum) > parseInt(num)) {
					alert("退换货数量不能大于总数量！");
					$("#" + key).focus();
					return;
				}
				$("#" + key1).val("1");
				b = true;
			} else {
				$("#" + key1).val("0");
			}
		}
		if(b) {
			//需要退换货，则备注不能为空
			if(tempNote == "") {
				alert("备注不能为空！");
				$("#tempNote").focus();
				return false;
			}
			if(tempNote.length > 2500) {
				alert("备注不能超过250个字！");
				$("#tempNote").focus();
				return false;
			}
		}
		$("#warehousedate").val($("#tmpWarehousedate").val());
		//备注
		$("#note").val($("#tempNote").val());
		return true;
	}
	
	function exportData(isInter) {
		var strname="";
		if (isInter == 1)
			strname="入库单导出!";
		if (isInter == 2)
			strname="入库单明细导出!";
		
		if (confirm(strname)){
			var id = ${updWarehouserptId};
			var exportunitprice = $("#exportunitprice").val().trim();
			window.location.href = "../warehouserpt/exportWarehouserptInDetailAction.action?strExportDetailId=" + id
					+ "&strInter=" + isInter + "&exportunitprice=" + exportunitprice;
		}
	}
	
	//快递
	function selectDelivery() {
		var url = "../delivery/showSelectDeliveryAction.action";
		url += "?date=" + encodeURI(new Date());
		//window.showModalDialog(url, window, "dialogheight:550px;dialogwidth:800px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		showModalDialogN(url, window, "dialogheight:550px;dialogwidth:800px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
	}
	
	function goBack() {
		window.location.href = "../warehouserpt/queryWarehouserptInAction.action";
	}
	
	function checkPrice(obj) {
		if(obj.checked) {
			document.getElementById("exportunitprice").value = 1;
		} else {
			document.getElementById("exportunitprice").value = 0;
		}
	}
	
	//条形码入库页面
	function showBarcodeIn() {
		$("#barcodeInfoList").val("");
		$("#" + "overlay").show();
		$("#" + "scanBarcodeDiv").show();
	}
	
	//条形码入库
	function commitBarcode() {
		var barcodeInfoList = $("#barcodeInfoList").val();
		if(barcodeInfoList == "") {
			alert("条形码为空！");
			$("#barcodeInfoList").focus();
			return;
		}
		var param = new Object();
		param.strScanBarcodeInfo = barcodeInfoList;
		param.barcodeInId = $("#id").val();
		$.getJSON('../warehouse/barcodeWarehouseInCheckAction.action', param, function(data) {
			if(confirm(data.msg + "确定入库吗？")) {
				$.getJSON('../warehouse/barcodeWarehouseInAction.action', param, function(data) {
					if(data.code == 0) {
						alert(data.msg);
						$("#" + "barcodeTd").hide();
						cancelSacnBarCode();
					} else {
						alert(data.msg);
					}
				});
			}
		});
	}
	
	function cancelSacnBarCode() {
		$("#" + "overlay").hide();
		$("#" + "scanBarcodeDiv").hide();
	}
	
	function CGDlistOut(isInter){
		if (confirm("确认用友导出吗？")){
			var list = document.getElementsByName("tmp_strAccountFlg");
			$("#strAccountFlg").val(0);
			for(var i = 0; i < list.length; i++) {
				if(list[i].checked) {
					$("#strAccountFlg").val(i);
				}
			}
	//		alert($("#strAccountFlg").val());
			var id = ${updWarehouserptId};
			var exportunitprice = $("#exportunitprice").val().trim();
	//		var strAccountFlg = $("#strAccountFlg").val().trim();
	//		var strAccountNo1 = $("#strAccountNo1").val().trim();
	//		var strAccountNo2 = $("#strAccountNo2").val().trim();
	//		alert(strAccountNo1);
	//		window.location.href = "../warehouserpt/exportCGDlistAction.action?strExportDetailId=" + id
	//				+ "&strInter=" + isInter + "&exportunitprice=" + exportunitprice +"&strAccountFlg=" + strAccountFlg +"&strAccountNo1=" + strAccountNo1 +"&strAccountNo2=" + strAccountNo2;		
			window.location.href = "../warehouserpt/exportCGDlistAction.action?strExportDetailId=" + id
			+ "&strInter=" + isInter + "&exportunitprice=" + exportunitprice ;		
		}
	}

</script>
</head>
<body>
	<div id="containermain">
		<div id="overlay" class="black_overlay1"></div>
		<div id="scanBarcodeDiv" style="position:fixed;display:none; z-index:1003;height: 261px;width: 605px;left:23%;top:130px;padding:6px 10px;max-height:260px;border:1px solid #999999;background:#fff;overflow-y:auto;">
			<table style="height: 250px; width: 600px;" border="0" cellpadding="0" cellspacing="0" bgcolor="white">
				<tr style="height: 20px;">
					<td colspan="2"> </td>
				</tr>
				<tr>
					<td width="80" align="right" valign="top">条形码：</td>
					<td align="left" valign="top">
						<textarea id="barcodeInfoList" rows="11" cols="65"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<div class="btn" style="margin-left: 170px;">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input type="button" class="input80" value="确定入库" onclick="commitBarcode();"/>
							</div>
							<div class="box1_right"></div>
						</div>
						<div class="btn" style="margin-left: 20px;">
							<div class="box1_left"></div>
							<div class="box1_center">
								<input type="button" class="input80" value="取消" onclick="cancelSacnBarCode();"/>
							</div>
							<div class="box1_right"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="content">
			<div class="tittle">
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px;">
					入库单明细
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="updWarehouserptDto.id" id="id"></s:hidden>
				<s:hidden name="updWarehouserptDto.hasbroken" id="hasbroken"></s:hidden>
				<s:hidden name="updWarehouserptDto.note" id="note"></s:hidden>
				<s:hidden name="updWarehouserptDto.expressid" id="expressid"></s:hidden>
				<s:hidden name="updWarehouserptDto.warehousedate" id="warehousedate"></s:hidden>
				<div class="searchbox update" style="height:0px;">
					<table width="100%" border="0" cellpadding="5" cellspacing="0">
						<tr>
							<td class="red" align="center" colspan="4"><s:actionmessage /></td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">入库单号</label>
							</td>
							<td colspan="3">
								<label class="pdf10"><s:property value="updWarehouserptDto.warehouseno"/></label>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">客户</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.suppliername" disabled="true" id="suppliername" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">快递</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expressname" disabled="true" id="expressname" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input40" type="button" disabled="disabled" value="检索" onclick="selectDelivery();" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td align="right">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">客户地址</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.supplieraddress" disabled="true" id="supplieraddress" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">快递公司地址</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expressaddress" disabled="true" id="expressaddress" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">转运费用合计</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expresstaxamount" disabled="true" id="expresstaxamount" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">联系人</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.suppliermanager" disabled="true" id="suppliermanager" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">联系人</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expressmanager" disabled="true" id="expressmanager" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">联系人电话</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.suppliertel" disabled="true" id="suppliertel" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">联系人电话</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expresstel" disabled="true" id="expresstel" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">联系人传真</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.supplierfax" disabled="true" id="supplierfax" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">联系人传真</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expressfax" disabled="true" id="expressfax" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">收货日期</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center date_input">
									<input type="text" id="tmpWarehousedate" disabled="disabled" style="width:105px;" value="<s:property value="updWarehouserptDto.showWarehousedate"/>" />
									<a class="date" href="javascript:;" onclick=""></a>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">联系人信箱</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.suppliermail" disabled="true" id="suppliermail" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10">联系人信箱</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updWarehouserptDto.expressmail" disabled="true" id="expressmail" cssStyle="width:120px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10">备注</label>
							</td>
							<td colspan="5">
								<textarea rows="5" cols="150" style="width:900px;" disabled="disabled" id="tempNote"><s:property value="updWarehouserptDto.note"/></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="info">
					<table width="100%" border="0">
						<tr>
							<td>
								<div class="tab_content" style="height: 44px;">
									<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
										<tr>
											<td colspan="11" align="center" class="tittle"><strong>产品信息</strong></td>
										</tr>
									</table>
								</div>
								<div class="tab_content" style="height: 175px;">
									<table id="productTable" class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
										<tr style="background:#eee; border-top:black solid 1px;">
											<td width="40">序号</td>
											<td width="40">类型</td>
											<td width="100">品名</td>
											<td width="90">规格</td>
											<td width="40">颜色</td>
											<td width="40">单位</td>
											<td width="40">包装</td>
											<td width="60">产地</td>
											<td width="80">数量</td>
											<td width="80">金额</td>
											<td width="90" style="display: none;">退货OR损毁</td>
											<td width="90" style="display: none;">退货数量</td>
										</tr>
										<s:iterator id="updWarehouserptDto.listProduct" value="updWarehouserptDto.listProduct" status="st1">
											<s:if test="#st1.odd==true">
												<tr class="tr_bg">
											</s:if>
											<s:else>
												<tr>
											</s:else>
												<td><s:property value="#st1.index + 1"/></td>
												<td>
													<s:iterator id="goodsList" value="goodsList" status="st3">
														<s:if test="%{goodsList[#st3.index].code == updWarehouserptDto.listProduct[#st1.index].fieldno}">
															<s:property value="fieldname"/>
														</s:if>
													</s:iterator>
												</td>
												<td><s:property value="tradename"/></td>
												<td><s:property value="typeno"/></td>
												<td>
													<s:iterator id="colorList" value="colorList" status="st3">
														<s:if test="%{colorList[#st3.index].code == updWarehouserptDto.listProduct[#st1.index].color}">
															<s:property value="fieldname"/>
														</s:if>
													</s:iterator>
												</td>
												<td>
													<s:iterator id="unitList" value="unitList" status="st3">
														<s:if test="%{unitList[#st3.index].code == updWarehouserptDto.listProduct[#st1.index].unit}">
															<s:property value="fieldname"/>
														</s:if>
													</s:iterator>
												</td>
												<td>
													<s:if test='%{updWarehouserptDto.listProduct[#st1.index].packaging == "0"}'>整箱</s:if>
													<s:elseif test='%{updWarehouserptDto.listProduct[#st1.index].packaging == "1"}'>乱尺</s:elseif>
													<s:else>
														<s:property value="packaging"/>
													</s:else>
												</td>
												<td>
													<s:iterator id="makeareaList" value="makeareaList" status="st3">
														<s:if test="%{makeareaList[#st3.index].code == updWarehouserptDto.listProduct[#st1.index].makearea}">
															<s:property value="fieldname"/>
														</s:if>
													</s:iterator>
												</td>
												<td align="right">
													<s:property value="num"/>
												</td>
												<td align="right">
													<s:property value="amount"/>
												</td>
												<td style="display: none;">
													<s:if test='%{updWarehouserptDto.listProduct[#st1.index].hasbroken == "1"}'>
														<input type="checkbox" alt="brokennum<s:property value="#st1.index"/>" name="tmpHasbroken" id="tmpHasbroken" checked="checked"/>
													</s:if>
													<s:else>
														<input type="checkbox" alt="brokennum<s:property value="#st1.index"/>" name="tmpHasbroken" id="tmpHasbroken"/>
													</s:else>
													<input type="hidden" name="updWarehouserptDto.listProduct[<s:property value="#st1.index"/>].hasbroken" id="hasbroken<s:property value="#st1.index"/>" value="<s:property value="hasbroken"/>"/>
													<input type="hidden" id="num<s:property value="#st1.index"/>" value="<s:property value="num"/>"/>
												</td>
												<td style="display: none;">
													<input type="text" name="updWarehouserptDto.listProduct[<s:property value="#st1.index"/>].brokennum" id="brokennum<s:property value="#st1.index"/>" maxlength="16" value="<s:property value="brokennum"/>" style="width: 80px;"/>
												</td>
											</tr>
										</s:iterator>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="trade">
					<table cellpadding="10" style="margin:0 auto;">
						<tr>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="入库单导出" onclick="exportData(1);"/>
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" style="width:100px" value="入库单明细导出" onclick="exportData(2);"/>
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<s:if test='updWarehouserptDto.res02 != null && updWarehouserptDto.res02 == "1"'>
								<td id="barcodeTd" style="display: none;">
							</s:if>
							<s:else>
								<td id="barcodeTd">
							</s:else>
									<div class="btn">
										<div class="box1_left"></div>
										<div class="box1_center">
											<input class="input120" type="button" style="width:120px" value="条形码入库" onclick="showBarcodeIn();"/>
										</div>
										<div class="box1_right"></div>
									</div>
								</td>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="用友导出" onclick="CGDlistOut(3);"/>
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="返回" onclick="window.close();"/>
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<input id="exportunitprice" type="checkbox" onclick="checkPrice(this)" value="0"/>导出含税单价
							</td>
						</tr>
					</table>
					<div style="height:225px;"></div>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
