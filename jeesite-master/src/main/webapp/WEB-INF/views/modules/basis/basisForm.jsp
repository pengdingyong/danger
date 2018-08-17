<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评估依据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var grade = '${basis.parent.grade}';
			if(grade == 1){
				$("#nameLabel").text("章节标题：");
			}else if (grade >= 2){
				$("#nameLabel").text("小节标题：");
			}
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
		<li><a href="${ctx}/basis/basis/list">评估依据列表</a></li>
		<li class="active"><a href="${ctx}/basis/basis/form?id=${basis.id}">评估依据<shiro:hasPermission name="basis:basis:edit">${not empty basis.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="basis:basis:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="basis" action="${ctx}/basis/basis/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="urlId"/>
		<form:hidden path="urlParentIds"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">上级：</label>
			<div class="controls">
				<c:if test="${basis.isNewRecord}">
	                <sys:treeselect id="basis" name="parent.id" value="${basis.parent.id}" labelName="parent.name" labelValue="${basis.parent.name}"
						title="制度章节" url="/basis/basis/treeData" extId="${basis.id}" basisChange="true" cssClass="input-large" allowClear="true"/>
					<span class="help-inline">添加规章制度不用选择</span>
				</c:if>
				<c:if test="${!basis.isNewRecord}">
	                <sys:treeselect id="basis" name="parent.id" value="${basis.parent.id}" labelName="parent.name" labelValue="${basis.parent.name}"
						title="制度章节" url="/basis/basis/treeData" extId="${basis.id}" cssClass="input-large" allowClear="true" disabled="disabled"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label id="nameLabel" class="control-label">规章名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="400" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" maxlength="400" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="basis:basis:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>