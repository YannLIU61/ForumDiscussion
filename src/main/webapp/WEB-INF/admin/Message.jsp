<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum-Messages</title>
<%@ include file="/WEB-INF/menu/AdminMenu.jsp"%>
</head>
<body>
	<div class="container">
		<br>
		<h1>Fil de discussion:</h1>
		<c:if test="${empty msg_lst }">
			<div class="alert alert-danger" role="alert">Aucun message pour
				l'instant</div>
		</c:if>
		<br>
		<table class="table table-striped table-bordered" id="datatable"
			style="width: 100%"
		>
			<thead>
				<tr>
					<th scope="col">DataDePublication</th>
					<th scope="col">Contenue</th>
					<th scope="col">Publisher</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="msg" items="${msg_lst}">

					<tr>
						<td>
							<c:out value="${msg.datePublication }"></c:out>
						</td>
						<td>
							<c:out value="${msg.content }"></c:out>
						</td>

						<td>
							<c:out value="${msg.editor.login}"></c:out>
						</td>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>