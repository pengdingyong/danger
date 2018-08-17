<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>隐患管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.fileupload{
			border:none;
			background:none;
			border: 1px solid #cccccc;
			border-radius: 4px;
			background: white;
			
		}
		
		.sjr {
			border-radius:3px;
			background: #0f6099;
			color: #fff;	
			line-height: 30px;
			padding-left: 10px;
			padding-right: 10px;
			cursor: pointer;
			float: left;
		}
		.hidden
			{
				z-index:1;
				width: 58px;
				opacity:0;
				-moz-opacity:0; 
				filter:alpha(opacity=0);
				cursor: pointer;
			}
			.tjfj .fj {
			border-radius: 3px;
			background: #e3eaf4;
			color: #88898c;
			font-size: 12px;
			padding: 3px 7px;
			line-height: 30px;
			float: left;
			margin-right: 5px;
			margin-top: 2px;
			}
			.tjfj .fj .fileDelBtn {
			float: right;
			margin-left: 10px;
			color: #518cb5;
			padding-left: 5px;
			padding-right: 5px;
			cursor: pointer;
		}
		 .tjfj .fj .fileDelBtn:hover {
			border-radius:3px;
			color: #0096ff;
			text-decoration: underline;
		}
		.fileName:hover{
			color: #0f6099;
			text-decoration: underline;
		}
		.layui-tab {
				    text-align: left !important;
				}
		.layui-tab-title {	
			width:98%;
			margin: 0 auto;
		    position: relative;
		    left: 0;
		    height: 40px;
		    white-space: nowrap;
		    font-size: 0;
		    border-bottom-width: 1px;
		    border-bottom-style: solid;
		    border-color: #e6e6e6;
		    transition: all .2s;
		    -webkit-transition: all .2s;
		}
		.layui-tab-title .layui-this {
			border-bottom: 1px solid green;
		    color: #000;
		}
		.layui-tab-title li {
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;
		    vertical-align: middle;
		    font-size: 14px;
		    transition: all .2s;
		    -webkit-transition: all .2s;
		    position: relative;
		    line-height: 40px;
		    min-width: 65px;
		    padding: 0 15px;
		    text-align: center;
		    cursor: pointer;
		}
		li {
		    list-style: none;
		}
		
		.layui-tab-item {
		    display: none;
		}
		.layui-show {
		    display: block !important;
		}
	</style>
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
		});
		
		//下载附件
		function downFile(id){
			window.open("${ctx}/governancedocuments/governanceDocuments/downFile?fileid="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs"> 
		<li><a href="${ctx}/dangerbank/dangerBank/">隐患列表</a></li>
		<li class="active"><a href="${ctx}/dangerbank/dangerBank/view?id=${dangerBank.id}">隐患查看</a></li>
		<li><a href="${ctx}/dangerbank/dangerBank/chart">统计图表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dangerBank" action="${ctx}/dangerbank/dangerBank/save" method="post" class="form-horizontal" enctype ="multipart/form-data">
		<form:hidden path="id"/>
		<input id="delFileIds" name="delFileIds" type="hidden">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="company" name="company.id" value="${dangerBank.company.id}" labelName="company.name" labelValue="${dangerBank.company.name}"
					disabled="disabled" title="单位" url="/sys/office/treeData?type=1" cssClass="" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患编号：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
				<span class="help-inline">例如，国网XX供电公司2月14日220kV XX线路OPGW光缆断5芯的安全隐患</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患（问题）来源：</label>
			<div class="controls">
				<form:input path="source" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
				<span class="help-inline">提示：隐患（问题）来源于安全大检查或自身巡检</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患（问题）分类：</label>
			<div class="controls">
				<form:select path="hiddenDangerParent.id" htmlEscape="false" disabled="true" class="input-xlarge hiddenDangerParent">
					<form:option label="--请选择--" value="" />
					<form:options items="${hiddenDangerList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<form:select path="hiddenDanger.id" htmlEscape="false" disabled="true" class="input-xlarge hiddenDanger">
					<form:option label="--请选择--" value="" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" >专业分类：</label>
			<div class="controls">
				<form:select path="professional.id" htmlEscape="false" disabled="true" class="input-xlarge ">
					<form:option label="--请选择--" value="" />
					<form:options items="${professionalList}" itemLabel="name" itemValue="id" htmlEscape="false" disabled="true"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发现时间：</label>
			<div class="controls">
				<input name="findTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${dangerBank.findTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear: true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患位置：</label>
			<div class="controls">
				<form:input path="dangerPosition" htmlEscape="false" disabled="true" maxlength="200" class="input-xlarge "/>
				<span class="help-inline">例如，xx站xx机房xx屏柜xx设备</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">隐患（问题）简述：</label>
			<div class="controls">
				<form:textarea path="dangerResume" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
				<span class="help-inline">例如，xx问题存在xx隐患，不符合xx制度xx条规定......</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评估定级：</label>
			<div class="controls">
				<form:select path="rating.id" htmlEscape="false" disabled="true" class="input-xlarge ">
					<form:option label="--请选择--" value="" />
					<form:options items="${ratingList}" itemLabel="name" itemValue="id" htmlEscape="false" disabled="true"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评估依据：</label>
			<div class="controls">
			 <sys:treeselect id="basis" name="basis.id" value="${dangerBank.basis.id}" labelName="basis.name" labelValue="${dangerBank.basis.name}"
						title="评估依据" url="/basis/basis/allRreeData" extId="${basis.id}" cssClass="input-large" allowClear="true"  notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整改（预控）措施：</label>
			<div class="controls">
				<form:textarea path="corrective" htmlEscape="false" disabled="true" maxlength="200" class="input-xlarge "/>
				<span class="help-inline">简述对此条隐患的防控措施</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">责任人：</label>
			<div class="controls">
				<form:input path="personLiable" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划完成时间：</label>
			<div class="controls">
				<input name="planDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${dangerBank.planDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear: true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际完成时间：</label>
			<div class="controls">
				<input name="completeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${dangerBank.completeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear: true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成情况：</label>
			<div class="controls">
				<form:select path="completeSituation" disabled="true" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:option value="1" label="未完成"/>
					<form:option value="2" label="部分完成"/>
					<form:option value="3" label="已完成"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前治理情况：</label>
			<div class="controls">
				<form:textarea path="rectificationProgressMonth" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">督办人：</label>
			<div class="controls">
				<form:input path="supervisor" htmlEscape="false" disabled="true" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remark" htmlEscape="false" disabled="true" maxlength="400" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">治理方案文档：</label>
			<div class="controls">
				<div class="sjr"></div>
						<div class="tjfj" id="fjfile0" <c:if test="${empty docList}">style="display: none;"</c:if>>
							<c:forEach var="documents" items="${docList }">
								<div class="fj">
		   							<span class="fileName" onclick="downFile('${documents.id }')" style="cursor: pointer;">${documents.name }</span>
		   						</div>
   							</c:forEach>
   						</div>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>