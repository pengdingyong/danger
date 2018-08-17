<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评估定级管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/rating/rating/">评估定级列表</a></li>
		<shiro:hasPermission name="rating:rating:edit"><li><a href="${ctx}/rating/rating/form">评估定级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="rating" action="${ctx}/rating/rating/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<shiro:hasPermission name="rating:rating:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="rating" varStatus="status">
			<tr>
				<td>
			    	${(page.pageNo-1)*page.pageSize + status.index+1}
				</td>
				<td>${rating.name}</td>
				<shiro:hasPermission name="rating:rating:edit"><td>
    				<a href="${ctx}/rating/rating/form?id=${rating.id}">修改</a>
					<a href="${ctx}/rating/rating/delete?id=${rating.id}" onclick="return confirmx('确认要删除该评估定级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>