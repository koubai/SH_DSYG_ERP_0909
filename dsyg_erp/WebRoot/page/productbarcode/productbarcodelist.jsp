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
<title>产品条形码信息一览</title>
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
	
	//查询数据
	function queryList() {
		document.mainform.action = '../productbarcode/queryProductBarcodeAction.action';
		document.mainform.submit();
	}
	
	//修改pagesize
	function changepagesize(pagesize) {
		$("#intPageSize").attr("value", pagesize);
		$("#startIndex").attr("value", "0");
		document.mainform.action = '../productbarcode/queryProductBarcodeAction.action';
		document.mainform.submit();
	}
	
	//翻页
	function changePage(pageNum) {
		$("#startIndex").attr("value", pageNum);
		document.mainform.action = '../productbarcode/turnProductBarcodeAction.action';
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
	
	//生成
	function createBarCode() {
		var strBarcodeProductIds = "";
		var strBarcodeSeq = "";
		var strBarcodeQuantity = "";
		var strProductItem14 = "";
		var list = document.getElementsByName("radioKey");
		for(var i = 0; i < list.length; i++) {
			if(list[i].checked) {
				var productid = list[i].value;
				var item14 = $("#item14_" + productid).val();
				var barcodeseq = $("#barcodeseq_" + productid).val();
				var barcodequantity = $("#barcodequantity_" + productid).val();
				if(item14 == "") {
					alert("单位长度不能为空！");
					$("#item14_" + productid).focus()
					return;
				}
				if(!isNumber(item14)) {
					alert("单位长度格式不正确！");
					$("#item14_" + productid).focus()
					return;
				}
				if(barcodeseq == "") {
					alert("条码开始号不能为空！");
					$("#barcodeseq_" + productid).focus()
					return;
				}
				if(!isNumber(barcodeseq)) {
					alert("条码开始号格式不正确！");
					$("#barcodeseq_" + productid).focus()
					return;
				}
				if(barcodequantity == "") {
					alert("新增数不能为空！");
					$("#barcodequantity_" + productid).focus()
					return;
				}
				if(!isNumber(barcodequantity)) {
					alert("新增数格式不正确！");
					$("#barcodequantity_" + productid).focus()
					return;
				}
				
				strBarcodeProductIds += productid + ",";
				strBarcodeSeq += barcodeseq + ",";
				strBarcodeQuantity += barcodequantity + ",";
				strProductItem14 += item14 + ",";
			}
		}
		if(strBarcodeProductIds == "") {
			alert("请选择一个产品！");
			return;
		}
		
		var param = new Object();
		param.strBarcodeProductIds = strBarcodeProductIds;
		param.strBarcodeSeq = strBarcodeSeq;
		param.strBarcodeQuantity = strBarcodeQuantity;
		param.strProductItem14 = strProductItem14;
		$.getJSON('../productbarcode/createProductBarcodeAction.action', param, function(data) {
			if(data.code == 0) {
				//解析返回值
				$.each(data.data, function(i, n){
					var productid = n.productid;
					//更新列表里的最大序列号值
					var maxSeq = n.barcodeseq;
					$("#barcodeseq_td_" + productid).html(maxSeq);
					$("#barcodeseq_" + productid).val(maxSeq);
					
					$.each(n.barcodeLogList, function(ii, nn){
						//条形码数据XXXXX-XXX-XXXXXXXXXXXXXXX
						var barcode = nn.barcode;
						//条形码后面15位顺序号
						var barcodeno = nn.barcodeno;
						alert("barcode=" + barcode + ",maxSeq=" + maxSeq);
						//这里调用打印函数
						//TODO
						//printBarCode
					});
				});
			} else {
				alert(data.msg);
			}
		});
	}
	
	//打印
	function printBarCode(){
		var fso, tf, tf2, str_barcode, barcode, i;
		   fso = new ActiveXObject("Scripting.FileSystemObject");
		   barcode = "3456789012341";
			for (i = 0; i<6; i++) {
				   tf = fso.CreateTextFile("c:\\bartest001.ext", true);
				   // 写一行，并且带有新行字符。
				   tf.WriteLine("N") ;
				   str_barcode = "B30,20,0,1,1,6,60,B,\"01571-000-" + barcode + "\"" ;
				   tf.WriteLine(str_barcode) ;
				   tf.WriteLine("P1") ;
				   // 写一行。
				   tf2 = fso.GetFile("c:\\bartest001.ext");
				   tf2.Copy("\\\\WATERMELON\\GK888d\\testfile.txt");
//				   tf2.Close();
//				   tf2.Delete();
				   tf.Close();
//				   tf.Delete();
				barcode++;
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
					产品条形码信息一览
				</div>
				<div class="tittle_right">
				</div>
			</div>
			<s:form id="mainform" name="mainform" method="POST">
				<s:hidden name="startIndex" id="startIndex"/>
				<s:hidden name="intPageSize" id="intPageSize"/>
				<div class="searchbox">
					<div class="box1">
						<label class="pdf10">品种</label>
						<div class="box1_left"></div>
						<div class="box1_center">
							<select id="strFieldno" name="strFieldno">
								<option value="" selected="selected">请选择</option>
								<s:iterator value="goodsList" id="goodsList" status="st1">
									<option value="<s:property value="code"/>" <s:if test="%{goodsList[#st1.index].code == strFieldno}">selected</s:if>><s:property value="fieldname"/></option>
								</s:iterator>
							</select>
						</div>
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
							<s:textfield name="strKeyword" id="strKeyword" cssClass="input120" maxlength="16" theme="simple"></s:textfield>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1">
						&nbsp;&nbsp;
						<s:if test='%{strPackaging == "1"}'>
							<input type="radio" name="strPackaging" value="0"/>整箱
							<input type="radio" name="strPackaging" value="1" checked="checked"/>乱尺
						</s:if>
						<s:elseif test='%{strPackaging == "0"}'>
							<input type="radio" name="strPackaging" value="0" checked="checked"/>整箱
							<input type="radio" name="strPackaging" value="1"/>乱尺
						</s:elseif>
						<s:else>
							<input type="radio" name="strPackaging" value="0"/>整箱
							<input type="radio" name="strPackaging" value="1"/>乱尺
						</s:else>
					</div>
					<div class="btn" style="margin-left: 50px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input40" value="检索" onclick="queryList();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn" style="margin-left: 50px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input80" value="条形码打印" onclick="printBarCode();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="btn" style="margin-left: 30px;">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" class="input80" value="条形码生成" onclick="createBarCode();"/>
						</div>
						<div class="box1_right"></div>
					</div>
					<div class="box1" style="margin-top:-3px; margin-left: -240px; color: red;">
						<s:actionmessage />
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
								<td width="20"></td>
								<td width="20">序号</td>
								<td width="40">类型</td>
								<td width="60">品名</td>
								<td width="80">规格</td>
								<td width="40">颜色</td>
								<td width="40">形式</td>
								<td width="60">包装</td>
								<td width="40">产地</td>
								<td width="82">住友编码</td>
								<td width="82">单位长度</td>
								<td width="82">当前条码号</td>
								<td width="82">条码开始号</td>
								<td width="82">新增数</td>
							</tr>
							<s:iterator id="productList" value="productList" status="st1">
								<s:if test="#st1.odd==true">
									<tr class="tr_bg" onclick="checkCheckboxTr(this, event);">
								</s:if>
								<s:else>
									<tr onclick="checkCheckboxTr(this, event);">
								</s:else>
									<td><input name="radioKey" type="checkbox" value="<s:property value="id"/>"/></td>
									<td><s:property value="page.pageSize * (page.nextIndex - 1) + #st1.index + 1"/></td>
									<td>
										<s:iterator id="goodsList" value="goodsList" status="st3">
											<s:if test="%{goodsList[#st3.index].code == productList[#st1.index].fieldno}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
									<td><s:property value="tradename"/></td>
									<td><s:property value="typeno"/></td>
									<td>
										<s:iterator id="colorList" value="colorList" status="st3">
											<s:if test="%{colorList[#st3.index].code == productList[#st1.index].color}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
									<td>
										<s:if test='%{productList[#st1.index].packaging == "0"}'>整箱</s:if>
										<s:elseif test='%{productList[#st1.index].packaging == "1"}'>乱尺</s:elseif>
										<s:elseif test='%{productList[#st1.index].packaging == "2"}'>样品</s:elseif>
										<s:else>
											<s:property value="packaging"/>
										</s:else>
									</td>
									<td>
										<s:property value="item10"/>
									</td>
									<td>
										<s:iterator id="makeareaList" value="makeareaList" status="st3">
											<s:if test="%{makeareaList[#st3.index].code == productList[#st1.index].makearea}">
												<s:property value="fieldname"/>
											</s:if>
										</s:iterator>
									</td>
									<td>
										<s:property value="item11"/>
									</td>
									<td>
										<s:if test='item14 != null && item14 != ""'>
											<input id="item14_<s:property value="id"/>" type="hidden" value="<s:property value="item14"/>"/>
											<s:property value="item14"/>
										</s:if>
										<s:else>
											<input id="item14_<s:property value="id"/>" style="width: 80px;" type="text" value=""/>
										</s:else>
									</td>
									<td id="barcodeseq_td_<s:property value="id"/>">
										<s:property value="barcodeseq"/>
									</td>
									<td>
										<input id="barcodeseq_<s:property value="id"/>" style="width: 80px;" type="text" value="<s:property value="barcodeseq"/>"/>
									</td>
									<td>
										<input id="barcodequantity_<s:property value="id"/>" style="width: 80px;" type="text" value=""/>
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
						</tr>
					</table>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
