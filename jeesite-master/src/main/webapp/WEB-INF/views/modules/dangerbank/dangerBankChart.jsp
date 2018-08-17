<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>隐患管理</title>
	<meta name="decorator" content="default"/>
	<!-- 引入 ECharts 文件 -->
	<script src="${ctxStatic}/common/echarts.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			getHiddengDangerByParentId();
			$("select.hiddenDanger").select2("val","${dangerBank.hiddenDanger.id}");
			function getHiddengDangerByParentId(){
				var parentId = $("select.hiddenDangerParent").val();
        		var optionHtml = "<option value=''>--请选择--</option>";
        		$("select.hiddenDanger").select2("val","");
				if(!parentId){
					$("select.hiddenDanger").html(optionHtml);
					return;
				}
				$.ajax({
		            url: "${ctx}/hiddendanger/hiddenDanger/getHiddengDangerByParentId?parentId="+parentId,
		            type: "post",
		            async: false,
		            contentType: "application/json; charset=utf-8",
		            dataType:"json",
		            success: function(hiddenDangerList){
		            	if(hiddenDangerList){
		            		for (var i in hiddenDangerList){
			            		var hiddenDanger = hiddenDangerList[i]
			            		optionHtml += "<option value='"+hiddenDanger.id+"'>"+hiddenDanger.name+"</option>";
		            		}
		            	}
	            		$("select.hiddenDanger").html(optionHtml);
		            }
		        });
			}
			$("select.hiddenDangerParent").on("change",function(){
				getHiddengDangerByParentId();
			});

			$("#chartType").on("change",function(){
				var chartType = $("#chartType").val();
				if (chartType == '1'){
					$("#hiddenDangerTypeLi").show();
				}else{
					$("#hiddenDangerTypeLi").hide();
				}
				if (chartType == '3'){
					$("#hiddenDangerParentIdLi").show();
					$("#hiddenDangerIdLi").show();
				}else{
					$("#hiddenDangerParentIdLi").hide();
					$("#hiddenDangerIdLi").hide();
				}
				if (chartType == '4'){
					$("#yearLi").show();
				}else{
					$("#yearLi").hide();
				}
			});
		});
		
		
		function getDangerChart(){
			var chartType = $("#chartType").val();
			if (chartType == 1){
				getDangerChartFirst(chartType);
			}else if (chartType == 2){
				getDangerChartSecond(chartType);
			}else if (chartType == 3){
				getDangerChartThird(chartType);
			}else if (chartType == 4){
				getDangerChartFourth(chartType);
			}else if (chartType == 5){
				getDangerChartFifth(chartType);
			}
		}
		function getDangerChartFirst(chartType){
			$.ajax({
	            url: "${ctx}/dangerbank/dangerBank/getDangerChart",
	            data: {
	            	"chartType" : chartType,
	            	"hiddenDangerType": $("#hiddenDangerType").val()
	            	},
	            type: "post",
	            async: false,
// 	            contentType: "application/json; charset=utf-8",
	            dataType:"json",
	            success: function(data){
					if (data){
						// 基于准备好的dom，初始化echarts实例
				        var myChart = echarts.init(document.getElementById('main'));
				        // 指定图表的配置项和数据
				        var option = {
				            title: {
				                text: '各类隐患总数对比图'
				            },
				            tooltip: {},
				            legend: {
				                data:['隐患数']
				            },
				            xAxis: {
				                data: data.xAxisList
				            },
				            yAxis: {},
				            series: [{
				                name: '隐患数',
				                type: 'bar',
				                barWidth: '50%',
				                data: data.yAxisList
				            }]
				        };

				        // 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(option);
					}
	            }
	        });
		}
		function getDangerChartSecond(chartType){
			$.ajax({
	            url: "${ctx}/dangerbank/dangerBank/getDangerChart",
	            data: {"chartType" : chartType},
	            type: "post",
	            async: false,
	            dataType:"json",
	            success: function(data){
					if (data){
						// 基于准备好的dom，初始化echarts实例
				        var myChart = echarts.init(document.getElementById('main'));
				        // 指定图表的配置项和数据
				        var option = {
				            title: {
				                text: '各单位隐患总数对比图'
				            },
				            tooltip: {},
				            legend: {
				                data:['隐患数']
				            },
				            xAxis: {
				                data: data.xAxisList
				            },
				            yAxis: {},
				            series: [{
				                name: '隐患数',
				                type: 'bar',
				                barWidth: '50%',
				                data: data.yAxisList
				            }]
				        };

				        // 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(option);
					}
	            }
	        });
		}
		function getDangerChartThird(chartType){
			$.ajax({
	            url: "${ctx}/dangerbank/dangerBank/getDangerChart",
	            data: {
	            	"chartType" : chartType,
	            	"hiddenDangerParent.id" : $("select.hiddenDangerParent").val(),
	            	"hiddenDanger.id" : $("select.hiddenDanger").val()
	            	},
	            type: "post",
	            async: false,
	            dataType:"json",
	            success: function(data){
					if (data){
						// 基于准备好的dom，初始化echarts实例
				        var myChart = echarts.init(document.getElementById('main'));
				        // 指定图表的配置项和数据
				        var option = {
				            title: {
				                text: '各单位单类隐患数量最大对比图'
				            },
				            tooltip: {},
				            legend: {
				                data:['隐患数']
				            },
				            xAxis: {
				                data: data.xAxisList
				            },
				            yAxis: {},
				            series: [{
				                name: '隐患数',
				                type: 'bar',
				                barWidth: '50%',
				                data: data.yAxisList
				            }]
				        };

				        // 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(option);
					}
	            }
	        });
		}
		//年度隐患总数走势统计图
		function getDangerChartFourth(chartType){
			$.ajax({
	            url: "${ctx}/dangerbank/dangerBank/getDangerChart",
	            data: {
	            	"chartType" : chartType,
	            	"year" : $("#year").val()
	            	},
	            type: "post",
	            async: false,
	            dataType:"json",
	            success: function(data){
					if (data){
						// 基于准备好的dom，初始化echarts实例
				        var myChart = echarts.init(document.getElementById('main'));
				        // 指定图表的配置项和数据
				        var option = {
				            title: {
				                text: '年度隐患总数走势统计图'
				            },
				            tooltip: {},
				            legend: {
				                data:['隐患数']
				            },
				            xAxis: {
				                data: data.xAxisList
				            },
				            yAxis: {},
				            series: [{
				                name: '隐患数',
				                type: 'line',
				                barWidth: '50%',
				                data: data.yAxisList
				            }]
				        };

				        // 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(option);
					}
	            }
	        });
		}
		//各级别隐患占比
		function getDangerChartFifth(chartType){
			$.ajax({
	            url: "${ctx}/dangerbank/dangerBank/getDangerChart",
	            data: {
	            	"chartType" : chartType,
	            	"year" : $("#year").val()
	            	},
	            type: "post",
	            async: false,
	            dataType:"json",
	            success: function(data){
					if (data){
						// 基于准备好的dom，初始化echarts实例
				        var myChart = echarts.init(document.getElementById('main'));
				        myChart.dispose();
				        myChart = echarts.init(document.getElementById('main'));
				        // 指定图表的配置项和数据
				        option = {
						    title : {
						        text: '各级别隐患占比',
						        subtext: '',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        left: 'left',
						        data: data.xAxisList
						    },
						    series : [
						        {
						            name: '隐患数',
						            type: 'pie',
						            radius : '55%',
						            center: ['50%', '60%'],
						            data: data.ratingList,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
						            }
						        }
						    ]
						};

				        // 使用刚指定的配置项和数据显示图表。
				        myChart.setOption(option);
					}
	            }
	        });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/dangerbank/dangerBank/">隐患列表</a></li>
		<shiro:hasPermission name="dangerbank:dangerBank:edit"><li><a href="${ctx}/dangerbank/dangerBank/form">隐患添加</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/dangerbank/dangerBank/chart">统计图表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dangerBank" action="${ctx}/dangerbank/dangerBank/chart" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>图表类型：</label>
				<select id="chartType" name="chartType" class="input-large ">
					<option value="">--请选择--</option>
					<option value="1">各类隐患总数对比图</option>
					<option value="2">各单位隐患总数对比图</option>
					<option value="3">各单位单类隐患数量最大对比图</option>
					<option value="4">年度隐患总数走势统计图</option>
					<option value="5">各级别隐患占比</option>
				</select>
			</li>
			<li id="hiddenDangerTypeLi" hidden>
				<select id=hiddenDangerType name="hiddenDangerType" class="input-medium " style="margin-left: 10px;">
					<option value="1">按隐患父类统计</option>
					<option value="2">按隐患子类统计</option>
				</select>
			</li>
			<li id="yearLi" hidden>
				<form:select path="year" htmlEscape="false" class="input-medium" style="margin-left: 10px;">
					<form:options items="${yearList}" itemLabel="year" itemValue="year" htmlEscape="false"/>
				</form:select>
			</li>
			<li id="companyLi" hidden><label>单位：</label>
				<sys:treeselect id="company" name="company.id" value="${dangerBank.company.id}" labelName="company.name" labelValue="${dangerBank.company.name}"
					title="部门" url="/sys/office/treeData?type=1" cssClass="input-medium" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li id="hiddenDangerParentIdLi" hidden><label>隐患分类：</label>
				<form:select path="hiddenDangerParent.id" htmlEscape="false" class="input-medium hiddenDangerParent">
					<form:option label="--请选择--" value="" />
					<form:options items="${hiddenDangerList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li id="hiddenDangerIdLi" hidden><label>隐患子类：</label>
				<form:select path="hiddenDanger.id" htmlEscape="false" class="input-medium hiddenDanger">
					<form:option label="--请选择--" value="" />
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" onclick="getDangerChart()" value="生成图表"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<div id="main" style="width: 1200px;height:500px;"></div>
</body>
</html>