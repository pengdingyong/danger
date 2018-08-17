<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>隐患分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hiddendanger/hiddenDanger/">隐患分类列表</a></li>
		<li class="active"><a href="${ctx}/hiddendanger/hiddenDanger/form?id=${hiddenDanger.id}">隐患分类<shiro:hasPermission name="hiddendanger:hiddenDanger:edit">${not empty hiddenDanger.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hiddendanger:hiddenDanger:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hiddenDanger" action="${ctx}/hiddendanger/hiddenDanger/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:if test="${hiddenDanger.parentId != null && hiddenDanger.parentId != '' && hiddenDanger.parentId != '0'}">
			<div class="control-group">
				<label class="control-label">上级隐患分类：</label>
				<div class="controls">
					<form:hidden path="parentId"/>
					${hiddenDanger.parentName}
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="hiddendanger:hiddenDanger:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>