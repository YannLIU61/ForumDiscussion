<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum</title>
<%@ include file="/WEB-INF/menu/UserMenu.jsp"%>
</head>
<body>
	<div class="container">
	<br>
		<h2>Mes Forums:</h2>
		<br>
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">Forum</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sessionScope.my_forum }" var="item">
					<tr>
						<td>
							<c:out value="${item.title }"></c:out>
						</td>
						<td>
							<form action="UserMessage" method="get">
								<input hidden="true" name="action" value="">
								<input hidden="true" name="current_forum_id" value="${item.id}">
								<button type="submit" class="btn btn-secondary">Consulter</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>