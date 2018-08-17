<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>评估依据管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty basis.id ? basis.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				var gradeOne = (row.grade==1)? true : false;
				var gradeTwo = (row.grade==2)? true : false;
				var gradeOther = (row.grade>=3)? true : false;
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						pid: (root?0:pid), 
						row: row,
						gradeOne: gradeOne,
						gradeTwo: gradeTwo,
						gradeOther: gradeOther,
						urlId: '${basis.id}',
						urlParentIds: '${basis.parentIds}'}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/basis/basis/list?id=${basis.id}&parentIds=${basis.parentIds}">评估依据列表</a></li>
		<shiro:hasPermission name="basis:basis:edit"><li><a href="${ctx}/basis/basis/form?urlId=${basis.id}&urlParentIds=${basis.parentIds}">评估依据添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td>{{row.name}}</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="${ctx}/basis/basis/form?id={{row.id}}&urlId={{urlId}}&urlParentIds={{urlParentIds}}">修改</a>
				<a href="${ctx}/basis/basis/delete?id={{row.id}}&urlId={{urlId}}&urlParentIds={{urlParentIds}}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除</a>
				{{#gradeOne}}
                      <a href="${ctx}/basis/basis/form?parent.id={{row.id}}&urlId={{urlId}}&urlParentIds={{urlParentIds}}">添加章节</a>
            	{{/gradeOne}}
				{{#gradeTwo}}
                      <a href="${ctx}/basis/basis/form?parent.id={{row.id}}&urlId={{urlId}}&urlParentIds={{urlParentIds}}">添加小节</a>
            	{{/gradeTwo}}  
				{{#gradeOther}}
                      <a href="${ctx}/basis/basis/form?parent.id={{row.id}}&urlId={{urlId}}&urlParentIds={{urlParentIds}}">添加小节</a>
            	{{/gradeOther}}  
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>