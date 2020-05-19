<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum-Subscription</title>
<%@ include file="/WEB-INF/menu/AdminMenu.jsp"%>
</head>
<body>
	<div class="container">
	<br>
		<h1>Liste des Subscriptions :</h1>
		<br>
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">Prénom</th>
					<th scope="col">Nom</th>
					<th scope="col">Login</th>
					<th scope="col">Gender</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${sub_lst}">
					<c:if test="${user.login!='anonyme'}">
						<tr>
							<td>
								<c:out value="${user.firstName }"></c:out>
							</td>
							<td>
								<c:out value="${user.lastName }"></c:out>
							</td>
							<td>
								<c:out value="${user.login }"></c:out>
							</td>
							<td>
								<c:out value="${user.gender }"></c:out>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>