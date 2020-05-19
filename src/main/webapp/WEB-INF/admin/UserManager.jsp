<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Forum Manager</title>
<%@ include file="/WEB-INF/menu/AdminMenu.jsp"%>
</head>
<body>
	<div class="container">
	<br>
		<h1>Liste des utilisateurs :</h1>
		<c:if test="${! empty ajoute_msg}">
			<div class="alert alert-success" role="alert">
				<c:out value="${ajoute_msg}"></c:out>
			</div>
			<c:remove var="ajoute_msg" />
		</c:if>
		<c:if test="${!empty delete_msg}">
			<div class="alert alert-success" role="alert">
				<c:out value="${delete_msg}"></c:out>
			</div>
			<c:remove var="delete_msg" />
		</c:if>
		<br>
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">Prénom</th>
					<th scope="col">Nom</th>
					<th scope="col">Login</th>
					<th scope="col">Gender</th>
					<th scope="col">Role</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${listUser}">
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
							<td>
								<c:out value="${user.role }"></c:out>
							</td>
							<td>
								<form action="UserManager" method="post">
									<input hidden="true" name="action" value="delete">
									<input hidden="true" name="userId" value="${user.id }">
									<button type="submit" class="btn btn-secondary">Delete</button>
								</form>

							</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<div>
			<a href="NouveauUtilisateur.jsp">Ajouter un nouveau Utilisateur</a>
		</div>
	</div>
</body>
</html>