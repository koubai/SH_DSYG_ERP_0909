<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>DSYG ERP CHART</title>
		
		<!-- 1. Add these JavaScript inclusions in the head of your page -->
		<!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/highcharts.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/Calendar3.js"></script>
		<jsp:include  page="chart_common.jsp"/> 
		
		<!-- 2. Add the JavaScript to initialize the chart on document ready -->
		<script type="text/javascript">

		Date.prototype.Format = function(fmt)   
		{ 
		  var o = {   
		    "M+" : this.getMonth()+1,                 //月份   
		    "d+" : this.getDate(),                    //日   
		    "h+" : this.getHours(),                   //小时   
		    "m+" : this.getMinutes(),                 //分   
		    "s+" : this.getSeconds(),                 //秒   
		    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		    "S"  : this.getMilliseconds()             //毫秒   
		  };   
		  if(/(y+)/.test(fmt))   
		    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  for(var k in o)   
		    if(new RegExp("("+ k +")").test(fmt))   
		  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		  return fmt;   
		}

		$(function () {  
			$("#fromDate").val("2015-08-01");
			$("#toDate").val(new Date().Format("yyyy-MM-dd"));
			getSupplierData3M();
		});	     	    

	    function getSupplierData3M() {
//     		alert("供应商3");
		   	var rds = document.getElementsByName("mtype");
	   		var fromDate = new Date();
	   		var toDate = new Date();
			fromDate.setMonth(toDate.getMonth()+1-3);
		   	for(var i=0;i<rds.length;i++){
	           	if(rds[i].checked){
	   				ajaxRequestData("getSupplierData", fromDate.format("yyyy-MM-dd"), toDate.format("yyyy-MM-dd"), rds[i].value, "供应商");
	           	}
		   	}
		}

	    function getSupplierData6M() {
//     		alert("供应商6");
		   	var rds = document.getElementsByName("mtype");
	   		var fromDate = new Date();
	   		var toDate = new Date();
			fromDate.setMonth(toDate.getMonth()+1-6);
		   	for(var i=0;i<rds.length;i++){
	           	if(rds[i].checked){
	   				ajaxRequestData("getSupplierData", fromDate.format("yyyy-MM-dd"), toDate.format("yyyy-MM-dd"), rds[i].value, "供应商");
	           	}
		   	}
		}
	    
		function getSupplierData12M() {
//     		alert("供应商12");
		   	var rds = document.getElementsByName("mtype");
	   		var fromDate = new Date();
	   		var toDate = new Date();
			fromDate.setMonth(toDate.getMonth()+1-12);
		   	for(var i=0;i<rds.length;i++){
	           	if(rds[i].checked){
	   				ajaxRequestData("getSupplierData", fromDate.format("yyyy-MM-dd"), toDate.format("yyyy-MM-dd"), rds[i].value, "供应商");
	           	}
		   	}
		}
		
		function ck(){
		   	var rds = document.getElementsByName("mtype");
		   	var fromDate = document.getElementById("fromDate").value;
			var today = new Date();
		   	if (fromDate == null || fromDate ==""){
		   		fromDate ="1900-01-01";
		   	}
		   	var toDate = document.getElementById("toDate").value;
		   	if (toDate == null || toDate ==""){
		   		toDate = today.format("yyyy-MM-dd");		
		   	}
		   	for(var i=0;i<rds.length;i++){
	           	if(rds[i].checked){
//	        		alert("fromDate:" +fromDate +" toDate:"+toDate + " type:" + rds[i].value);
			   		if (IsDate(fromDate) && IsDate(toDate))
			   			ajaxRequestData("getSupplierData", fromDate, toDate, rds[i].value, "供应商");
	           }
		    }
		}
		
		function addUserList() {
			var theme1 = "";
			var url = '<%=request.getContextPath()%>/chart/showSupplierSelectPage.action';
			//strFlag=1采购单，strFlag=2销售单
			url += "?strFieldno=" + theme1 + "&date=" + encodeURI(new Date());
			
			//window.showModalDialog(url, window, "dialogheight:550px;dialogwidth:800px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
			showModalDialogN(url, window, "dialogheight:550px;dialogwidth:800px;center:yes;status:0;resizable=no;Minimize=no;Maximize=no");
		};

		function drawChart(d1, d2, dur_type, chart_data, tit) {			
     		$(document).ready(function() {  
		    	options = {  
		            chart: {  
		                renderTo: 'container',  
		            },		            
		            credits: { 
		                enabled: false   //右下角不显示LOGO 
		            },
		            title: {  
		                text: tit + "曲线",
		            },  
		            xAxis: {  
		                categories: get_X_Data(d1, d2, dur_type),
		            },  
		            yAxis: {  
		                min: 0,  
		                title: {  
		                    text: '金额 (元)'  
		                }  
		            },  
		            legend: {  
		                layout: 'vertical',  
		                backgroundColor: '#FFFFFF',  
		                align: 'left',  
		                verticalAlign: 'top',  
		                x: 100,  
		                y: 50,  
		                floating: true,  
		                shadow: true  
		            },  
		            tooltip: {  
		                formatter: function() {  
		                    return ''+  
		                        this.x +': '+ this.y +' ';  
		                }  
		            },  
		            plotOptions: {  
		                column: {  
		                    pointPadding: 0.2,  
		                    borderWidth: 0  
		                }  
		            },  
	                series: eval(chart_data)		         
		    	};
	            var chart = new Highcharts.Chart(options);
	        });
	    };  

		</script>
		<!-- <script src="${pageContext.request.contextPath}/js/themes/gray.js"></script> -->
	</head>
	<body>
		<div class="containerchart">
		<jsp:include page="../info.jsp" flush="true" />
		<div class="tittle">
			<div class="icons"><a class="home" href="#" onclick="goHome();">返回首页</a><a class="quit" href="#" onclick="logout();">退出</a></div>
			<div class="tittle_left">
			</div>
			<div class="tittle_center">供应商查询
			</div>
			<div class="tittle_right">
			</div>
		</div>
		<div class="tab_content2" >	
		<!-- 3. Add the container -->
		<br><br>
	
		<input type="hidden" id="h1" value="<s:property value="str" />" />
		<input type="hidden" id="h2" value="<s:property value="series" />" />
		<input type="hidden" id="h3" value="<s:property value="series_X" />" />
		<input type="hidden" id="periodtype" value="<s:property value="periodtype" />" />
		<table width="50%" border="0" cellpadding="5" cellspacing="0">
			<tr>
				<td>供应商ID</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center">
					<input type="text" name="handerList" id="handerList" value="" />
					</div>
					<div class="box1_right"></div>
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input class="input80" type="button" onclick="addUserList();" value="供应商检索" />
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
				<td></td>
			</tr>
			<tr>		
				<td>期间类型</td>
				<td>
					<div class="box1_left"></div>
					<div class="box1_center date_input">				
						<input type="text" name="fromDate" id="fromDate" value="#fromDate" />
						<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('fromDate'));"></a>
					</div>
					<div class="box1_right">&nbsp&nbsp-</div>
					<div class="box1_left" style="margin-left: 30px;"></div>
					<div class="box1_center date_input">				
						<input type="text" name="toDate" id="toDate" value="#toDate" />
						<a class="date" href="javascript:;" onclick="new Calendar().show(document.getElementById('toDate'));"></a>
					</div>
					<div class="box1_right"></div>
				</td>
				<td>
					<div style = "float:left">
						<input name="mtype" type="radio" id="radio1" value="1" checked>月</input>
						<input name="mtype" type="radio" id="radio2" value="2" >季</input>
						<input name="mtype" type="radio" id="radio3" value="3" >年</input>
					</div>
					<div class="btn">
						<div class="box1_left"></div>
						<div class="box1_center">
							<input type="button" value="供应商信息查询" onclick="ck();" />
						</div>
						<div class="box1_right"></div>
					</div>
				</td>
			</tr>
		</table>
		<table>
		<tr>
			<td>
				<div class="btn">
					<div class="box1_left"></div>
					<div class="box1_center">
			           <Input id="btn1" type=button value="供应商 近3个月" onClick="javascripts:getSupplierData3M();" />
					</div>
					<div class="box1_right"></div>
				</div>
			</td>
			<td>
				<div class="btn">
					<div class="box1_left"></div>
					<div class="box1_center">
			           <Input id="btn2" type=button value="供应商 近6个月" onClick="javascripts:getSupplierData6M();" />
					</div>
					<div class="box1_right"></div>
				</div>
			</td>
			<td>
				<div class="btn">
					<div class="box1_left"></div>
					<div class="box1_center">
			           <Input id="btn3" type=button value="供应商 近12个月" onClick="javascripts:getSupplierData12M();" />
					</div>
					<div class="box1_right"></div>
				</div>
			</td>
		</tr>
		</table>				
		<br><br><br>
		<table>
		<tr>
			<td>
				<div id="container" style="width: 600px; height: 400px; margin: 2 "></div>
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td>
				<div id="container2" style="width: 600px; height: 400px; margin: 2 "></div>
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>
		</table>
		<br><br><br>
		<div id="dateMessage">   
		<!-- <table id="planTable" border:1px solid #000 style="border-collapse:collapse;"> -->
		<table id="planTable" border:1px  cellpadding="3" cellspacing="1" style="background-color: #b9d8f3;">
		</table>			
	    </div>
	    </div>
	    </div>
	</body>
</html>
