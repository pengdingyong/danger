<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>隐患分类管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(page.list)}, rootId = "${not empty hiddenDanger.id ? hiddenDanger.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 1});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				var isPid=false;
				if(pid==0){
					isPid=true;
				}
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					var startUpFlag = (row.startUpFlag == 0)?true:false;
					$(list).append(Mustache.render(tpl, {
						pid: (root?0:pid), row: row,index:i,isPid: isPid
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
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
		<li class="active"><a href="${ctx}/hiddendanger/hiddenDanger/">隐患分类列表</a></li>
		<shiro:hasPermission name="hiddendanger:hiddenDanger:edit"><li><a href="${ctx}/hiddendanger/hiddenDanger/form">隐患分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hiddenDanger" action="${ctx}/hiddendanger/hiddenDanger/" method="post" class="breadcrumb form-search">
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
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<shiro:hasPermission name="hiddendanger:hiddenDanger:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td>{{row.name}}</td>
			<shiro:hasPermission name="hiddendanger:hiddenDanger:edit"><td>
				<a href="${ctx}/hiddendanger/hiddenDanger/form?id={{row.id}}">修改</a>
				<a href="${ctx}/hiddendanger/hiddenDanger/delete?id={{row.id}}" onclick="return confirmx('要删除该隐患分类吗？', this.href)">删除</a>
				{{#isPid}}<a href="${ctx}/hiddendanger/hiddenDanger/form?parentId={{row.id}}">添加子隐患分类</a> {{/isPid}}
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>