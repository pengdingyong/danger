<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>隐患管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出隐患库数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/dangerbank/dangerBank/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
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
			})
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/dangerbank/dangerBank/list");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/dangerbank/dangerBank/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/dangerbank/dangerBank/import/template">下载模板</a>；
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dangerbank/dangerBank/">隐患列表</a></li>
		<shiro:hasPermission name="dangerbank:dangerBank:edit"><li><a href="${ctx}/dangerbank/dangerBank/form">隐患添加</a></li></shiro:hasPermission>
		<li><a href="${ctx}/dangerbank/dangerBank/chart">统计图表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dangerBank" action="${ctx}/dangerbank/dangerBank/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="company" name="company.id" value="${dangerBank.company.id}" labelName="company.name" labelValue="${dangerBank.company.name}"
					title="单位" url="/sys/office/treeData?type=1" cssClass="input-medium" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>隐患编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>隐患名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label style="width: 140px;">隐患（问题）来源：</label>
				<form:input path="source" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>专业分类：</label>
				<form:select path="professional.id" htmlEscape="false" class="input-medium ">
					<form:option label="--请选择--" value="" />
					<form:options items="${professionalList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发现时间：</label>
				<input name="beginFindTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${dangerBank.beginFindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endFindTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${dangerBank.endFindTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label style="width: 140px;">隐患（问题）分类：</label>
				<form:select path="hiddenDangerParent.id" htmlEscape="false" class="input-medium hiddenDangerParent">
					<form:option label="--请选择--" value="" />
					<form:options items="${hiddenDangerList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: 140px;">隐患（问题）子类：</label>
				<form:select path="hiddenDanger.id" htmlEscape="false" class="input-medium hiddenDanger">
					<form:option label="--请选择--" value="" />
				</form:select>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>单位</th>
				<th>隐患编号</th>
				<th>隐患名称</th>
				<th>隐患（问题）来源</th>
				<th>隐患（问题）分类</th>
				<th>隐患（问题）子类</th>
				<th>专业分类</th>
				<th>发现时间</th>
				<!-- <th>隐患位置</th>
				<th>隐患简述</th> -->
				<shiro:hasPermission name="dangerbank:dangerBank:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dangerBank">
			<tr>
				<td>${dangerBank.company.name}</td>
				<td>
					${dangerBank.code}
				</td>
				<td>
					${dangerBank.name}
				</td>
				<td>
					${dangerBank.source}
				</td>
				<td>
					${dangerBank.hiddenDangerParent.name}
				</td>
				<td>
					${dangerBank.hiddenDanger.name}
				</td>
				<td>
					${dangerBank.professional.name}
				</td>
				<td>
					<fmt:formatDate value="${dangerBank.findTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <td>
					${dangerBank.dangerPosition}
				</td>
				<td>
					${dangerBank.dangerResume}
				</td> --%>
				<shiro:hasPermission name="dangerbank:dangerBank:edit"><td>
					<a href="${ctx}/dangerbank/dangerBank/view?id=${dangerBank.id}">查看</a>
					<c:if test="${user.roleList[0].id == '1' || (user.roleList[0].id == '6' && user.id == dangerBank.createBy.id && user.company.id == dangerBank.company.id)|| (user.roleList[0].id == '2' && user.office.id == dangerBank.office.id && user.company.id == dangerBank.company.id) }">
    					<a href="${ctx}/dangerbank/dangerBank/form?id=${dangerBank.id}">修改</a>
						<a href="${ctx}/dangerbank/dangerBank/delete?id=${dangerBank.id}" onclick="return confirmx('确认要删除该隐患吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>