<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
<title>账目信息更新</title>
<script type="text/javascript">
	function upd() {
		if(checkItem()) {
			if(confirm("确定修改吗？")) {
				document.mainform.action = "../finance/updFinanceAction.action";
				document.mainform.submit();
			}
		}
	}
	
	//验证数据格式
	function checkItem() {
		//账目编号
		var receiptid = $("#receiptid").val().trim();
		//经手人
		var handler = $("#handler").val().trim();
		//关联单据编号
		var invoiceid = $("#invoiceid").val().trim();
		//单据日期
		var tmpReceiptdate = $("#tmpReceiptdate").val().trim();
		//类型
		var financetype = $("#financetype").val().trim();
		//方式
		var mode = $("#mode").val().trim();
		//对象
		var customername = $("#customername").val().trim();
		//联系人
		var customermanager = $("#customermanager").val().trim();
		//金额合计
		var amount = $("#amount").val().trim();
		//结算日期
		var tmpAccountdate = $("#tmpAccountdate").val().trim();
		//收款状态
		var status1 = $("#status1").val().trim();
		//付款状态
		var status2 = $("#status2").val().trim();
		
		//发票
		var strBillno1 = $("#strBillno1").val().trim();
		var strBillno2 = $("#strBillno2").val().trim();
		var strBillno3 = $("#strBillno3").val().trim();
		
		var tmpReceiptdate1 = $("#tmpReceiptdate1").val().trim();
		var tmpReceiptdate2 = $("#tmpReceiptdate2").val().trim();
		var tmpReceiptdate3 = $("#tmpReceiptdate3").val().trim();
		
		var strBillamount1 = $("#strBillamount1").val().trim();
		var strBillamount2 = $("#strBillamount2").val().trim();
		var strBillamount3 = $("#strBillamount3").val().trim();
		
		if(receiptid == "") {
		}
		if(handler == "") {
			alert("经手人不能为空！");
			$("#handler").focus();
			return;
		}
		if(invoiceid == "") {
		}
		if(tmpReceiptdate == "") {
		}
		if(financetype == "") {
			alert("请选择主题！");
			$("#theme").focus();
			return;
		} else {
			if(financetype == "4") {
				//主题
				var theme = $("#theme").val().trim();
				if(theme == "") {
					alert("请选择主题！");
					$("#theme").focus();
					return;
				}
			}
		}
		if(mode == "") {
			alert("请选择方式！");
			$("#mode").focus();
			return;
		}
		if(customername == "") {
		}
		if(customermanager == "") {
		}
		
		if(amount == "") {
			alert("金额合计不能为空！");
			$("#amount").focus();
			return;
		}
		//
		if(financetype == "1" || financetype == "2") {
			if(!isAllReal(amount)) {
				alert("金额合计格式不正确！");
				$("#amount").focus();
				return;
			}
		} else {
			if(!isReal(amount)) {
				alert("金额合计必须为大于0的实数！");
				$("#amount").focus();
				return;
			}
		}
		if(tmpAccountdate == "") {
			alert("结算日期不能为空！");
			$("#tmpAccountdate").focus();
			return;
		}
		if(mode == "2") {
			//付款
			if(status2 == "") {
				alert("请选择状态！");
				$("#status2").focus();
				return;
			}
			$("#status").val($("#status2").val());
		} else {
			//收款
			if(status1 == "") {
				alert("请选择状态！");
				$("#status1").focus();
				return;
			}
			$("#status").val($("#status1").val());	
		}
		
		//根据当前状态，判断是否需要输入发票
		if(mode == "2") {
			//付款记录
			if(status2 == "10" || status2 == "99") {
				if(strBillno1 == "" && strBillno2 == "" && strBillno3 == "") {
					alert("请输入发票号！");
					$("#strBillno1").focus();
					return;
				}
			}
		} else {
			//收款记录
			if(status1 == "20" || status1 == "99") {
				if(strBillno1 == "" && strBillno2 == "" && strBillno3 == "") {
					alert("请输入发票号！");
					$("#strBillno1").focus();
					return;
				}
			}
		}
		
		if(strBillno1 != "") {
			if(strBillamount1 == "") {
				alert("请输入发票金额1！");
				$("#strBillamount1").focus();
				return;
			}
			if(!isReal(strBillamount1)) {
				alert("发票金额1必须为大于0的实数！");
				$("#strBillamount1").focus();
				return;
			}
			if(tmpReceiptdate1 == "") {
				alert("请输入开票日期1！");
				$("#tmpReceiptdate1").focus();
				return;
			}
		}
		if(strBillno2 != "") {
			if(strBillamount2 == "") {
				alert("请输入发票金额2！");
				$("#strBillamount2").focus();
				return;
			}
			if(!isReal(strBillamount2)) {
				alert("发票金额2必须为大于0的实数！");
				$("#strBillamount2").focus();
				return;
			}
			if(tmpReceiptdate2 == "") {
				alert("请输入开票日期2！");
				$("#tmpReceiptdate2").focus();
				return;
			}
		}
		if(strBillno3 != "") {
			if(strBillamount3 == "") {
				alert("请输入发票金额3！");
				$("#strBillamount3").focus();
				return;
			}
			if(!isReal(strBillamount3)) {
				alert("发票金额3必须为大于0的实数！");
				$("#strBillamount3").focus();
				return;
			}
			if(tmpReceiptdate3 == "") {
				alert("请输入开票日期3！");
				$("#tmpReceiptdate3").focus();
				return;
			}
		}
		
		if(financetype == "1" || financetype == "2") {
			//预开票CHECK
			var list = document.getElementsByName("itemRadio");
			for(var i = 0; i < list.length; i++) {
				if(list[i].checked) {
					//验证数据
					var tr = list[i].parentNode.parentNode;
					var tds = tr.getElementsByTagName("td");
					var inputs = tds[0].getElementsByTagName("input");
					
					var num = parseFloat(inputs[5].value).toFixed(2);
					var amount = parseFloat(inputs[6].value).toFixed(2);
					
					var remaininvoicenum = parseFloat(inputs[11].value);
					var remaininvoiceamount = parseFloat(inputs[12].value);
					
					var oldinvoicednum = parseFloat(inputs[13].value);
					var oldinvoicedamount = parseFloat(inputs[14].value);
					var oldremaininvoicenum = parseFloat(inputs[15].value);
					var oldremaininvoiceamount = parseFloat(inputs[16].value);
					var currinvoiceamount = tds[16].getElementsByTagName("input")[0].value;
					var currinvoicenum = tds[15].getElementsByTagName("input")[0].value;
					if(!isAllReal(currinvoicenum)) {
						alert("开票数量格式不正确！");
						return;
					}
					if(oldremaininvoicenum < 0) {
						if(oldremaininvoicenum > parseFloat(currinvoicenum)) {
							alert("开票数量不能小于" + oldremaininvoicenum + "！");
							return;
						}
						if(0 < parseFloat(currinvoicenum)) {
							alert("开票数量不能大于0！");
							return;
						}
					} else {
						if(oldremaininvoicenum < parseFloat(currinvoicenum)) {
							alert("开票数量不能大于" + oldremaininvoicenum + "！");
							return;
						}
						if(0 > parseFloat(currinvoicenum)) {
							alert("开票数量不能小于0！");
							return;
						}
					}
					if(!isAllReal(currinvoiceamount)) {
						alert("开票金额格式不正确！");
						return;
					}
					if(oldremaininvoiceamount < 0) {
						if(oldremaininvoiceamount > parseFloat(currinvoiceamount)) {
							alert("开票金额不能小于" + oldremaininvoiceamount + "！");
							return;
						}
						if(0 < parseFloat(currinvoiceamount)) {
							alert("开票金额不能大于0！");
							return;
						}
					} else {
						if(oldremaininvoiceamount < parseFloat(currinvoiceamount)) {
							alert("开票金额不能大于" + oldremaininvoiceamount + "！");
							return;
						}
						if(0 > parseFloat(currinvoiceamount)) {
							alert("开票金额不能小于0！");
							return;
						}
					}
				}
			}
		}
		
		$("#strReceiptdate1").val($("#tmpReceiptdate1").val());
		$("#strReceiptdate2").val($("#tmpReceiptdate2").val());
		$("#strReceiptdate3").val($("#tmpReceiptdate3").val());
		
		$("#receiptdate").val($("#tmpReceiptdate").val());
		$("#accountdate").val($("#tmpAccountdate").val());
		
		//组织talbe
		$("#productTable").empty();
		if(typeof(list) != "undefined") {
			for(var i = 0; i < list.length; i++) {
				var checked = "";
				if(list[i].checked) {
					checked = "1";
				}
				var tr = list[i].parentNode.parentNode;
				var tds = tr.getElementsByTagName("td");
				var inputs = tds[0].getElementsByTagName("input");
				var id = inputs[0].value;
				var averagePrice = inputs[3].value;
				var numabs = inputs[5].value;
				var amount = inputs[6].value;
				var currinvoicenum = inputs[7].value;
				var currinvoiceamount = inputs[8].value;
				var invoicednum = inputs[9].value;
				var invoicedamount = inputs[10].value;
				var remaininvoicenum = inputs[11].value;
				var remaininvoiceamount = inputs[12].value;
				var oldinvoicednum = inputs[13].value;
				var oldinvoicedamount = inputs[14].value;
				var oldremaininvoicenum = inputs[15].value;
				var oldremaininvoiceamount = inputs[16].value;
				
				var fieldno = inputs[17].value;
				var tradename = inputs[18].value;
				var typeno = inputs[19].value;
				var color = inputs[20].value;
				var makearea = inputs[21].value;
				
				var tr = document.createElement("tr");
				var td = document.createElement("td");
				td.appendChild(createInput("tmpProductList[" + i + "].checked", checked));
				td.appendChild(createInput("tmpProductList[" + i + "].id", id));
				td.appendChild(createInput("tmpProductList[" + i + "].averagePrice", averagePrice));
				td.appendChild(createInput("tmpProductList[" + i + "].numabs", numabs));
				td.appendChild(createInput("tmpProductList[" + i + "].amount", amount));
				td.appendChild(createInput("tmpProductList[" + i + "].currinvoicenum", currinvoicenum));
				td.appendChild(createInput("tmpProductList[" + i + "].currinvoiceamount", currinvoiceamount));
				td.appendChild(createInput("tmpProductList[" + i + "].invoicednum", invoicednum));
				td.appendChild(createInput("tmpProductList[" + i + "].invoicedamount", invoicedamount));
				td.appendChild(createInput("tmpProductList[" + i + "].remaininvoicenum", remaininvoicenum));
				td.appendChild(createInput("tmpProductList[" + i + "].remaininvoiceamount", remaininvoiceamount));
				td.appendChild(createInput("tmpProductList[" + i + "].oldinvoicednum", oldinvoicednum));
				td.appendChild(createInput("tmpProductList[" + i + "].oldinvoicedamount", oldinvoicedamount));
				td.appendChild(createInput("tmpProductList[" + i + "].oldremaininvoicenum", oldremaininvoicenum));
				td.appendChild(createInput("tmpProductList[" + i + "].oldremaininvoiceamount", oldremaininvoiceamount));
				
				td.appendChild(createInput("tmpProductList[" + i + "].fieldno", fieldno));
				td.appendChild(createInput("tmpProductList[" + i + "].tradename", tradename));
				td.appendChild(createInput("tmpProductList[" + i + "].typeno", typeno));
				td.appendChild(createInput("tmpProductList[" + i + "].color", color));
				td.appendChild(createInput("tmpProductList[" + i + "].makearea", makearea));
				
				tr.appendChild(td);
				document.getElementById("productTable").appendChild(tr);
			}
		}
		
		return true;
	}
	
	function changeMode() {
		var mode = $("#mode").val().trim();
		if(mode == "1") {
			//收款
			$("#status1").show();
			$("#status2").hide();
		} else if(mode == "2") {
			//付款
			$("#status2").show();
			$("#status1").hide();
		} else {
		}
	}
	
	//用户
	function selectUser() {
		var url = "../user/showSelectUserAction.action";
		url += "?date=" + new Date();
		window.showModalDialog(url, window, "dialogheight:550px;dialogwidth:800px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
	}
	
	function goBack() {
		window.location.href = "../finance/queryFinanceAction.action";
	}
	
	var checkflag = false;
	function calcNum(obj, type) {
		if(checkflag) {
			return;
		}
		var tr = obj.parentNode.parentNode;
		var tds = tr.getElementsByTagName("td");
		var inputs = tds[0].getElementsByTagName("input");
		var averagePrice = parseFloat(inputs[3].value);
		var currinvoicenum = "";
		var currinvoiceamount = "";
		var num = parseFloat(inputs[5].value).toFixed(2);
		var amount = parseFloat(inputs[6].value).toFixed(2);
		var remaininvoicenum = parseFloat(inputs[11].value);
		var remaininvoiceamount = parseFloat(inputs[12].value);
		var oldinvoicednum = parseFloat(inputs[13].value);
		var oldinvoicedamount = parseFloat(inputs[14].value);
		var oldremaininvoicenum = parseFloat(inputs[15].value);
		var oldremaininvoiceamount = parseFloat(inputs[16].value);
		var returnNum = parseFloat(inputs[22].value);
		if(type == "1") {
			//根据数量计算金额
			if(!isAllReal(obj.value)) {
				alert("开票数量格式不正确！");
				checkflag = ture;//故意写错
				obj.focus();
				checkflag = false;
				return;
			}
			if(oldremaininvoicenum < 0) {
				if(oldremaininvoicenum > parseFloat(obj.value)) {
					alert("开票数量不能小于" + oldremaininvoicenum + "！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
				if(0 < parseFloat(obj.value)) {
					alert("开票数量不能大于0！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
			} else {
				if(oldremaininvoicenum < parseFloat(obj.value)) {
					alert("开票数量不能大于" + oldremaininvoicenum + "！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
				if(0 > parseFloat(obj.value)) {
					alert("开票数量不能小于0！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
			}
			currinvoicenum = parseFloat(obj.value).toFixed(2);
			currinvoiceamount = currinvoicenum * averagePrice;
			currinvoiceamount = currinvoiceamount.toFixed(2);
			inputs[8].value = currinvoiceamount;
			inputs[7].value = obj.value;
			tds[16].getElementsByTagName("input")[0].value = currinvoiceamount;
		} else if(type == "2") {
			//根据金额计算数量
			if(!isAllReal(obj.value)) {
				alert("开票金额格式不正确！");
				checkflag = ture;//故意写错
				obj.focus();
				checkflag = false;
				return;
			}
			if(oldremaininvoiceamount < 0) {
				if(oldremaininvoiceamount > parseFloat(obj.value)) {
					alert("开票金额不能小于" + oldremaininvoiceamount + "！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
				if(0 < parseFloat(obj.value)) {
					alert("开票金额不能大于0！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
			} else {
				if(oldremaininvoiceamount < parseFloat(obj.value)) {
					alert("开票金额不能大于" + oldremaininvoiceamount + "！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
				if(0 > parseFloat(obj.value)) {
					alert("开票金额不能小于0！");
					checkflag = ture;//故意写错
					obj.focus();
					checkflag = false;
					return;
				}
			}
			currinvoiceamount = parseFloat(obj.value).toFixed(2);
			currinvoicenum = currinvoiceamount / averagePrice;
			currinvoicenum = currinvoicenum.toFixed(2);
			inputs[7].value = currinvoicenum;
			inputs[8].value = obj.value;
			tds[15].getElementsByTagName("input")[0].value = currinvoicenum;
		}
		var invoicednum = parseFloat(inputs[13].value) + parseFloat(currinvoicenum);
		var invoicedamount = parseFloat(inputs[14].value) + parseFloat(currinvoiceamount);
		var remaininvoicenum = parseFloat(inputs[15].value) - parseFloat(currinvoicenum);
		var remaininvoiceamount = parseFloat(inputs[16].value) - parseFloat(currinvoiceamount);
		
		inputs[9].value = invoicednum.toFixed(2);
		inputs[10].value = invoicedamount.toFixed(2);
		inputs[11].value = remaininvoicenum.toFixed(2);
		inputs[12].value = remaininvoiceamount.toFixed(2);
		
		tds[12].innerHTML = remaininvoicenum.toFixed(2);
		tds[12].style.color = "red";
		tds[13].innerHTML = remaininvoiceamount.toFixed(2);
		tds[13].style.color = "red";
		tds[10].innerHTML = invoicednum.toFixed(2);
		tds[10].style.color = "red";
		tds[11].innerHTML = invoicedamount.toFixed(2);
		tds[11].style.color = "red";
	}
	
	//预出库------作废
	function preInvoice() {
		//类型
		var financetype = $("#financetype").val().trim();
		if(financetype == "1" || financetype == "2") {
			//预开票CHECK
			var list = document.getElementsByName("itemRadio");
			var count = 0;
			for(var i = 0; i < list.length; i++) {
				if(list[i].checked) {
					count++;
					//验证数据
					var tr = list[i].parentNode.parentNode;
					var tds = tr.getElementsByTagName("td");
					var inputs = tds[0].getElementsByTagName("input");
					
					var num = parseFloat(inputs[5].value).toFixed(2);
					var amount = parseFloat(inputs[6].value).toFixed(2);
					var oldinvoicednum = parseFloat(inputs[13].value);
					var oldinvoicedamount = parseFloat(inputs[14].value);
					var currinvoiceamount = tds[16].getElementsByTagName("input")[0].value;
					var currinvoicenum = tds[15].getElementsByTagName("input")[0].value;
					
					if(!isAllReal(currinvoicenum)) {
						alert("开票数量格式不正确！");
						return;
					}
					if(num < parseFloat(currinvoicenum)) {
						alert("开票数量不能大于" + num + "！");
						return;
					}
					if(parseFloat(currinvoicenum) < 0 && parseFloat(currinvoicenum) * -1 > oldinvoicednum) {
						alert("退票数量不能小于" + (-1 * oldinvoicednum) + "！");
						return;
					}
					if(!isAllReal(currinvoiceamount)) {
						alert("开票金额格式不正确！");
						return;
					}
					if(amount < parseFloat(currinvoiceamount)) {
						alert("开票金额不能大于" + amount + "！");
						return;
					}
					if(parseFloat(currinvoiceamount) < 0 && parseFloat(currinvoiceamount) * -1 > oldinvoicedamount) {
						alert("退票金额不能小于-" + (-1 * oldinvoicedamount) + "！");
						return;
					}
					//预出库
					var invoicednum = parseFloat(inputs[13].value) + parseFloat(currinvoicenum);
					var invoicedamount = parseFloat(inputs[14].value) + parseFloat(currinvoiceamount);
					var remaininvoicenum = parseFloat(inputs[15].value) - parseFloat(currinvoicenum);
					var remaininvoiceamount = parseFloat(inputs[16].value) - parseFloat(currinvoiceamount);
					
					inputs[9].value = invoicednum.toFixed(2);
					inputs[10].value = invoicedamount.toFixed(2);
					inputs[11].value = remaininvoicenum.toFixed(2);
					inputs[12].value = remaininvoiceamount.toFixed(2);
					
					tds[12].innerHTML = remaininvoicenum.toFixed(2);
					tds[12].style.color = "red";
					tds[13].innerHTML = remaininvoiceamount.toFixed(2);
					tds[13].style.color = "red";
					tds[10].innerHTML = invoicednum.toFixed(2);
					tds[10].style.color = "red";
					tds[11].innerHTML = invoicedamount.toFixed(2);
					tds[11].style.color = "red";
				}
			}
			if(count == 0) {
				alert("请选择一条预出库记录！");
				return;
			}
			//组织talbe
			$("#productTable").empty();
			for(var i = 0; i < list.length; i++) {
				var checked = "";
				if(list[i].checked) {
				var tr = list[i].parentNode.parentNode;
					var tds = tr.getElementsByTagName("td");
					var inputs = tds[0].getElementsByTagName("input");
					var id = inputs[0].value;
					var averagePrice = inputs[3].value;
					var numabs = inputs[5].value;
					var amount = inputs[6].value;
					var currinvoicenum = inputs[7].value;
					var currinvoiceamount = inputs[8].value;
					var invoicednum = inputs[9].value;
					var invoicedamount = inputs[10].value;
					var remaininvoicenum = inputs[11].value;
					var remaininvoiceamount = inputs[12].value;
					var oldinvoicednum = inputs[13].value;
					var oldinvoicedamount = inputs[14].value;
					var oldremaininvoicenum = inputs[15].value;
					var oldremaininvoiceamount = inputs[16].value;
					
					var fieldno = inputs[17].value;
					var tradename = inputs[18].value;
					var typeno = inputs[19].value;
					var color = inputs[20].value;
					var makearea = inputs[21].value;
					var returnNum = inputs[22].value;
					
					var tr = document.createElement("tr");
					var td = document.createElement("td");
					td.appendChild(createInput("tmpProductList[" + i + "].checked", checked));
					td.appendChild(createInput("tmpProductList[" + i + "].id", id));
					td.appendChild(createInput("tmpProductList[" + i + "].averagePrice", averagePrice));
					td.appendChild(createInput("tmpProductList[" + i + "].numabs", numabs));
					td.appendChild(createInput("tmpProductList[" + i + "].amount", amount));
					td.appendChild(createInput("tmpProductList[" + i + "].currinvoicenum", currinvoicenum));
					td.appendChild(createInput("tmpProductList[" + i + "].currinvoiceamount", currinvoiceamount));
					td.appendChild(createInput("tmpProductList[" + i + "].invoicednum", invoicednum));
					td.appendChild(createInput("tmpProductList[" + i + "].invoicedamount", invoicedamount));
					td.appendChild(createInput("tmpProductList[" + i + "].remaininvoicenum", remaininvoicenum));
					td.appendChild(createInput("tmpProductList[" + i + "].remaininvoiceamount", remaininvoiceamount));
					td.appendChild(createInput("tmpProductList[" + i + "].oldinvoicednum", oldinvoicednum));
					td.appendChild(createInput("tmpProductList[" + i + "].oldinvoicedamount", oldinvoicedamount));
					td.appendChild(createInput("tmpProductList[" + i + "].oldremaininvoicenum", oldremaininvoicenum));
					td.appendChild(createInput("tmpProductList[" + i + "].oldremaininvoiceamount", oldremaininvoiceamount));
					
					td.appendChild(createInput("tmpProductList[" + i + "].fieldno", fieldno));
					td.appendChild(createInput("tmpProductList[" + i + "].tradename", tradename));
					td.appendChild(createInput("tmpProductList[" + i + "].typeno", typeno));
					td.appendChild(createInput("tmpProductList[" + i + "].color", color));
					td.appendChild(createInput("tmpProductList[" + i + "].makearea", makearea));
					td.appendChild(createInput("tmpProductList[" + i + "].returnNum", returnNum));
					
					tr.appendChild(td);
					document.getElementById("productTable").appendChild(tr);
				}
			}
		}
	}
	
	function createInput(id, value) {
		var input = document.createElement("input");
		input.type = "text";
		input.name = id;
		input.value = value;
		return input;
	}
	
	function changeFinancetype(obj) {
		var type = $(obj).val();
		if(type == "1" || type == "2") {
			$("#billTr").hide();
			$("#invoiceDiv").show();
		} else {
			$("#billTr").show();
			$("#invoiceDiv").hide();
		}
	}
</script>
</head>
<body>
	<div id="containermain">
		<div class="content">
			<div class="tittle">
				<div class="tittle_left">
				</div>
				<div class="tittle_center" style="width:150px;">
					账目信息编辑
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="updFinanceDto.receiptdate" id="receiptdate"></s:hidden>
				<s:hidden name="updFinanceDto.accountdate" id="accountdate"></s:hidden>
				<s:hidden name="updFinanceDto.status" id="status"></s:hidden>
				
				<s:hidden name="updFinanceDto.handler" id="handler"></s:hidden>
				<s:hidden name="updFinanceDto.handlername" id="handlername"></s:hidden>
				
				<s:hidden name="strReceiptdate1" id="strReceiptdate1"></s:hidden>
				<s:hidden name="strReceiptdate2" id="strReceiptdate2"></s:hidden>
				<s:hidden name="strReceiptdate3" id="strReceiptdate3"></s:hidden>
				<div class="searchbox update" style="height:0px;">
					<table id="productTable" style="display: none;">
					</table>
					<table width="100%" border="0" cellpadding="5" cellspacing="0">
						<tr>
							<td class="red" align="center" colspan="4"><s:actionmessage /></td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10"><font color="red"></font>账目编号</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updFinanceDto.receiptid" disabled="true" id="receiptid" cssStyle="width:300px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10"><font color="red">*</font>经手人</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<input type="text" id="tmphandlername" disabled="disabled" style="width:300px;" value="<s:property value="updFinanceDto.handlername"/>" />
								</div>
								<div class="box1_right"></div>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input40" type="button" value="检索" onclick="selectUser();" />
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10"><font color="red"></font>关联单据编号</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updFinanceDto.invoiceid" id="invoiceid" cssStyle="width:300px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10"><font color="red"></font>单据日期</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center date_input">
									<input type="text" id="tmpReceiptdate" disabled="disabled" style="width:285px;" value="<s:property value="updFinanceDto.showReceiptdate"/>" />
									<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpReceiptdate'));"></a>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10"><font color="red">*</font>主题</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:if test='updFinanceDto.financetype == "4"'>
										<s:hidden name="updFinanceDto.financetype" id="financetype"></s:hidden>
										<select name="updFinanceDto.theme" id="theme" style="width: 300px;">
											<option value="" selected="selected">请选择</option>
											<s:iterator value="financeDictList" id="financeDictList" status="st1">
												<option value="<s:property value="code"/>" <s:if test="%{financeDictList[#st1.index].code == updFinanceDto.theme}">selected</s:if>><s:property value="fieldname"/></option>
											</s:iterator>
										</select>
									</s:if>
									<s:else>
										<select name="updFinanceDto.financetype" id="financetype" style="width: 300px;" onchange="changeFinancetype(this);">
											<option value="" selected="selected">请选择</option>
											<s:if test='updFinanceDto.financetype == "1"'>
												<option value="1" selected="selected">采购</option>
												<option value="2">订单</option>
												<option value="3">物流</option>
											</s:if>
											<s:elseif test='updFinanceDto.financetype == "2"'>
												<option value="1">采购</option>
												<option value="2" selected="selected">订单</option>
												<option value="3">物流</option>
											</s:elseif>
											<s:elseif test='updFinanceDto.financetype == "3"'>
												<option value="1">采购</option>
												<option value="2">订单</option>
												<option value="3" selected="selected">物流</option>
											</s:elseif>
										</select>
									</s:else>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10"><font color="red">*</font>方式</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<select name="updFinanceDto.mode" id="mode" style="width: 300px;" onchange="changeMode();">
										<s:if test='%{updFinanceDto.mode == "1"}'>
											<option value="">请选择</option>
											<option value="1" selected="selected">收款</option>
											<option value="2">付款</option>
										</s:if>
										<s:elseif test='%{updFinanceDto.mode == "2"}'>
											<option value="">请选择</option>
											<option value="1">收款</option>
											<option value="2" selected="selected">付款</option>
										</s:elseif>
										<s:else>
											<option value="" selected="selected">请选择</option>
											<option value="1">收款</option>
											<option value="2">付款</option>
										</s:else>
									</select>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10"><font color="red"></font>对象</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updFinanceDto.customername" id="customername" cssStyle="width:300px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10"><font color="red"></font>联系人</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updFinanceDto.customermanager" id="customermanager" cssStyle="width:300px;" maxlength="16" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10"><font color="red">*</font>金额合计</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updFinanceDto.amount" id="amount" maxlength="64" cssStyle="width:300px;" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10"><font color="red">*</font>结算日期</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center date_input">
									<input type="text" id="tmpAccountdate" disabled="disabled" style="width:285px;" value="<s:property value="updFinanceDto.showAccountdate"/>" />
									<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpAccountdate'));"></a>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="pdf10"><font color="red">*</font>状态</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:if test='%{updFinanceDto.mode == "2"}'>
										<select id="status2" style="width: 300px;">
									</s:if>
									<s:else>
										<select id="status2" style="width: 300px;display: none;">
									</s:else>
										<s:if test="%{updFinanceDto.status == 1}">
											<option value="">请选择</option>
											<option value="1" selected="selected">未收到发票, 未付款</option>
											<option value="10">收到发票, 安排付款</option>
											<option value="15">未收到发票, 已付款</option>
											<option value="99">收到发票, 已付款</option>
										</s:if>
										<s:elseif test="%{updFinanceDto.status == 10}">
											<option value="">请选择</option>
											<option value="1">未收到发票, 未付款</option>
											<option value="10" selected="selected">收到发票, 安排付款</option>
											<option value="15">未收到发票, 已付款</option>
											<option value="99">收到发票, 已付款</option>
										</s:elseif>
										<s:elseif test="%{updFinanceDto.status == 15}">
											<option value="">请选择</option>
											<option value="1">未收到发票, 未付款</option>
											<option value="10">收到发票, 安排付款</option>
											<option value="15" selected="selected">未收到发票, 已付款</option>
											<option value="99">收到发票, 已付款</option>
										</s:elseif>
										<s:elseif test="%{updFinanceDto.status == 99}">
											<option value="">请选择</option>
											<option value="1">未收到发票, 未付款</option>
											<option value="10">收到发票, 安排付款</option>
											<option value="15">未收到发票, 已付款</option>
											<option value="99" selected="selected">收到发票, 已付款</option>
										</s:elseif>
										<s:else>
											<option value="" selected="selected">请选择</option>
											<option value="1">未收到发票, 未付款</option>
											<option value="10">收到发票, 安排付款</option>
											<option value="15">未收到发票, 已付款</option>
											<option value="99">收到发票, 已付款</option>
										</s:else>
									</select>
									<s:if test='%{updFinanceDto.mode == "1"}'>
										<select id="status1" style="width: 300px;">
									</s:if>
									<s:else>
										<select id="status1" style="width: 300px;display: none;">
									</s:else>
										<s:if test="%{updFinanceDto.status == 1}">
											<option value="">请选择</option>
											<option value="1" selected="selected">未对帐</option>
											<option value="10">已对帐, 未开票</option>
											<option value="15">已收款, 未对账</option>
											<option value="20">已开票, 未收款</option>
											<option value="99">已开票, 已收款</option>
										</s:if>
										<s:elseif test="%{updFinanceDto.status == 10}">
											<option value="">请选择</option>
											<option value="1">未对帐</option>
											<option value="10" selected="selected">已对帐, 未开票</option>
											<option value="15">已收款, 未对账</option>
											<option value="20">已开票, 未收款</option>
											<option value="99">已开票, 已收款</option>
										</s:elseif>
										<s:elseif test="%{updFinanceDto.status == 15}">
											<option value="">请选择</option>
											<option value="1">未对帐</option>
											<option value="10">已对帐, 未开票</option>
											<option value="15" selected="selected">已收款, 未对账</option>
											<option value="20">已开票, 未收款</option>
											<option value="99">已开票, 已收款</option>
										</s:elseif>
										<s:elseif test="%{updFinanceDto.status == 20}">
											<option value="">请选择</option>
											<option value="1">未对帐</option>
											<option value="10">已对帐, 未开票</option>
											<option value="15">已收款, 未对账</option>
											<option value="20" selected="selected">已开票, 未收款</option>
											<option value="99">已开票, 已收款</option>
										</s:elseif>
										<s:elseif test="%{updFinanceDto.status == 99}">
											<option value="">请选择</option>
											<option value="1">未对帐</option>
											<option value="10">已对帐, 未开票</option>
											<option value="15">已收款, 未对账</option>
											<option value="20">已开票, 未收款</option>
											<option value="99" selected="selected">已开票, 已收款</option>
										</s:elseif>
										<s:else>
											<option value="" selected="selected">请选择</option>
											<option value="1">未对帐</option>
											<option value="10">已对帐, 未开票</option>
											<option value="15">已收款, 未对账</option>
											<option value="20">已开票, 未收款</option>
											<option value="99">已开票, 已收款</option>
										</s:else>
									</select>
								</div>
								<div class="box1_right"></div>
							</td>
							<td align="right">
								<label class="pdf10"><font color="red"></font>快递单号</label>
							</td>
							<td>
								<div class="box1_left"></div>
								<div class="box1_center">
									<s:textfield name="updFinanceDto.res08" id="res08" maxlength="32" cssStyle="width:300px;" theme="simple"></s:textfield>
								</div>
								<div class="box1_right"></div>
							</td>
						</tr>
						
						<s:if test="updFinanceDto.financetype == 1 || updFinanceDto.financetype == 2">
						<tr id="billTr" style="display: none;">
						</s:if>
						<s:else>
						<tr id="billTr">
						</s:else>
							<td colspan="4">
								<table width="83%" style="margin-left: 58px;" border="0" cellpadding="5" cellspacing="0">
									<tr>
										<td align="right">
											<label class="pdf10">发票1</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" name="strBillno1" id="strBillno1" maxlength="32" style="width:190px;" value="<s:property value="updFinanceDto.billno1"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td align="right">
											<label class="pdf10">金额1</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" name="strBillamount1" id="strBillamount1" maxlength="13" style="width:190px;" value="<s:property value="updFinanceDto.billamount1"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td align="right">
											<label class="pdf10"><font color="red"></font>开票日期1</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center date_input">
												<input type="text" name="tmpReceiptdate1" id="tmpReceiptdate1" disabled="disabled" style="width:190px;" value="<s:property value="updFinanceDto.receiptdate1"/>" />
												<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpReceiptdate1'));"></a>
											</div>
											<div class="box1_right"></div>
										</td>
									</tr>
									<tr>
										<td align="right">
											<label class="pdf10">发票2</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" name="strBillno2" id="strBillno2" maxlength="32" style="width:190px;" value="<s:property value="updFinanceDto.billno2"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td align="right">
											<label class="pdf10">金额2</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" name="strBillamount2" id="strBillamount2" maxlength="13" style="width:190px;" value="<s:property value="updFinanceDto.billamount2"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td align="right">
											<label class="pdf10"><font color="red"></font>开票日期2</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center date_input">
												<input type="text" name="tmpReceiptdate2" id="tmpReceiptdate2" disabled="disabled" style="width:190px;" value="<s:property value="updFinanceDto.receiptdate2"/>" />
												<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpReceiptdate2'));"></a>
											</div>
											<div class="box1_right"></div>
										</td>
									</tr>
									<tr>
										<td align="right">
											<label class="pdf10">发票3</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" name="strBillno3" id="strBillno3" maxlength="32" style="width:190px;" value="<s:property value="updFinanceDto.billno3"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td align="right">
											<label class="pdf10">金额3</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center">
												<input type="text" name="strBillamount3" id="strBillamount3" maxlength="13" style="width:190px;" value="<s:property value="updFinanceDto.billamount3"/>" />
											</div>
											<div class="box1_right"></div>
										</td>
										<td align="right">
											<label class="pdf10"><font color="red"></font>开票日期3</label>
										</td>
										<td>
											<div class="box1_left"></div>
											<div class="box1_center date_input">
												<input type="text" name="tmpReceiptdate3" id="tmpReceiptdate3" disabled="disabled" style="width:190px;" value="<s:property value="updFinanceDto.receiptdate3"/>" />
												<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('tmpReceiptdate3'));"></a>
											</div>
											<div class="box1_right"></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				
				<s:if test="updFinanceDto.financetype == 1 || updFinanceDto.financetype == 2">
				<div id="invoiceDiv" class="info">
				</s:if>
				<s:else>
				<div id="invoiceDiv" class="info" style="display: none;">
				</s:else>
					<table width="100%" border="0">
						<tr>
							<td>
								<div class="tab_content" style="height: 44px;">
									<table class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
										<tr>
											<td colspan="11" align="center" class="tittle"><strong></strong></td>
										</tr>
									</table>
								</div>
								<div class="tab_content" style="height: 305px;">
									<table id="productTable" class="info_tab" width="100%" border="1" cellpadding="5" cellspacing="0">
										<tr style="background:#eee; border-top:black solid 1px;">
											<td style="width: 0px; display: none"></td>
											<td width="30"></td>
											<td width="35">序号</td>
											<td width="70">类型</td>
											<td width="100">品名</td>
											<td width="90">型号</td>
											<td width="35">颜色</td>
											<td width="60">产地</td>
											<td width="70">数量</td>
											<td width="70">金额</td>
											<td width="80">发票数量</td>
											<td width="80">发票金额</td>
											<td width="80">未开票数量</td>
											<td width="80">未开票金额</td>
											<td width="85">退票数量</td>
											<td width="85">开票数量</td>
											<td width="110">开票金额（含税）</td>
										</tr>
										<tbody id="productData">
											<s:iterator id="productList" value="updFinanceDto.productList" status="st1">
												<s:if test="#st1.odd==true">
													<tr class="tr_bg">
												</s:if>
												<s:else>
													<tr>
												</s:else>
													<td style="width: 0px; display: none;">
														<input type="hidden" value="<s:property value="id"/>" />
														<input type="hidden" value="<s:property value="productid"/>" />
														<input type="hidden" value="<s:property value="theme1"/>" />
														<input type="hidden" value="<s:property value="averagePrice"/>" />
														<input type="hidden" value="" />
														
														<input type="hidden" value="<s:property value="num"/>" />
														<input type="hidden" value="<s:property value="amount"/>" />
														<input type="hidden" value="<s:property value="currinvoicenum"/>" />
														<input type="hidden" value="<s:property value="currinvoiceamount"/>" />
														<input type="hidden" value="<s:property value="invoicednum"/>" />
														<input type="hidden" value="<s:property value="invoicedamount"/>" />
														<input type="hidden" value="<s:property value="remaininvoicenum"/>" />
														<input type="hidden" value="<s:property value="remaininvoiceamount"/>" />
														
														<input type="hidden" value="<s:property value="oldinvoicednum"/>" />
														<input type="hidden" value="<s:property value="oldinvoicedamount"/>" />
														<input type="hidden" value="<s:property value="oldremaininvoicenum"/>" />
														<input type="hidden" value="<s:property value="oldremaininvoiceamount"/>" />
														
														<input type="hidden" value="<s:property value="fieldno"/>" />
														<input type="hidden" value="<s:property value="tradename"/>" />
														<input type="hidden" value="<s:property value="typeno"/>" />
														<input type="hidden" value="<s:property value="color"/>" />
														<input type="hidden" value="<s:property value="makearea"/>" />
														<input type="hidden" value="<s:property value="returnNum"/>" />
													</td>
													<td>
														<s:if test='checked == "1"'>
															<input name="itemRadio" type="checkbox" checked="checked" />
														</s:if>
														<s:else>
															<input name="itemRadio" type="checkbox" />
														</s:else>
													</td>
													<td><s:property value="#st1.index + 1"/></td>
													<td>
														<s:iterator id="goodsList" value="goodsList" status="st3">
															<s:if test="%{goodsList[#st3.index].code == updFinanceDto.productList[#st1.index].fieldno}">
																<s:property value="fieldname"/>
															</s:if>
														</s:iterator>
													</td>
													<td><s:property value="tradename"/></td>
													<td><s:property value="typeno"/></td>
													<td>
														<s:iterator id="colorList" value="colorList" status="st3">
															<s:if test="%{colorList[#st3.index].code == updFinanceDto.productList[#st1.index].color}">
																<s:property value="fieldname"/>
															</s:if>
														</s:iterator>
													</td>
													<td>
														<s:iterator id="makeareaList" value="makeareaList" status="st3">
															<s:if test="%{makeareaList[#st3.index].code == updFinanceDto.productList[#st1.index].makearea}">
																<s:property value="fieldname"/>
															</s:if>
														</s:iterator>
													</td>
													<td>
														<s:property value="num"/>
													</td>
													<td>
														<s:property value="amount"/>
													</td>
													<s:if test="invoicednum != oldinvoicednum">
														<td style="color: red;">
															<s:property value="invoicednum"/>
														</td>
													</s:if>
													<s:else>
														<td>
															<s:property value="invoicednum"/>
														</td>
													</s:else>
													<s:if test="invoicedamount != oldinvoicedamount">
														<td style="color: red;">
															<s:property value="invoicedamount"/>
														</td>
													</s:if>
													<s:else>
														<td>
															<s:property value="invoicedamount"/>
														</td>
													</s:else>
													<s:if test="remaininvoicenum != oldremaininvoicenum">
														<td style="color: red;">
															<s:property value="remaininvoicenum"/>
														</td>
													</s:if>
													<s:else>
														<td>
															<s:property value="remaininvoicenum"/>
														</td>
													</s:else>
													<s:if test="remaininvoiceamount != oldremaininvoiceamount">
														<td style="color: red;">
															<s:property value="remaininvoiceamount"/>
														</td>
													</s:if>
													<s:else>
														<td>
															<s:property value="remaininvoiceamount"/>
														</td>
													</s:else>
													<td>
														<s:property value="returnNum"/>
													</td>
													<td>
														<input type="text" style="width: 80px;" id="tmpCurrinvoicenum_<s:property value="productid"/>" onblur="calcNum(this, '1');" maxlength="13" value="<s:property value="currinvoicenum"/>"/>
													</td>
													<td>
														<input type="text" style="width: 80px;" id="tmpCurrinvoiceamount_<s:property value="productid"/>" onblur="calcNum(this, '2');" maxlength="13" value="<s:property value="currinvoiceamount"/>"/>
													</td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<table cellpadding="10" style="margin:0 auto; display: none;">
					<tr>
						<td>
							<div class="btn1">
								<div class="btn1_left"></div>
								<div class="btn1_center">
									<input class="input80" type="button" onclick="preInvoice();" value="预出库" />
								</div>
								<div class="btn1_right"></div>
							</div>
						</td>
					</tr>
				</table>
				
				<div class="trade">
					<table cellpadding="10" style="margin:0 auto;">
						<tr>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="提交" onclick="upd();"/>
									</div>
									<div class="box1_right"></div>
								</div>
							</td>
							<td>
								<div class="btn">
									<div class="box1_left"></div>
									<div class="box1_center">
										<input class="input80" type="button" value="返回" onclick="goBack();"/>
									</div>
									<div class="box1_right"></div>
								</div>
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
