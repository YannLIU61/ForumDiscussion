<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Abonnements</title>
<%@ include file="/WEB-INF/menu/UserMenu.jsp"%>
</head>
<body>
	<div class="container">
		<br>
		<h2>Mes abonnements & Tous les Forums Disponibles:</h2>
		<c:if test="${empty lst_forum}">
			<div class="alert alert-danger" role="alert">Pas de Forum
				disponibles pour l'instant!!!</div>
		</c:if>
		<c:if test="${! empty inscrire_msg}">
			<div class="alert alert-success" role="alert">
				<c:out value="${inscrire_msg}"></c:out>
			</div>
			<c:remove var="inscrire_msg" />
		</c:if>
		<c:if test="${!empty desinscrire_msg}">
			<div class="alert alert-success" role="alert">
				<c:out value="${desinscrire_msg}"></c:out>
			</div>
			<c:remove var="desinscrire_msg" />
		</c:if>
		<br>
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">Title</th>
					<th scope="col">Description</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sessionScope.lst_forum }" var="item">
					<tr>
						<td>
							<c:out value="${item.title }"></c:out>
						</td>
						<td>
							<c:out value="${item.description }"></c:out>
						</td>

						<c:choose>
							<c:when test="${sessionScope.my_forumId.contains(item.id)}">
								<td style="color: green;">Registered</td>
								<td>
									<form action="UserSubscription" method="post">
										<input hidden="true" name="action" value="desinscrire">
										<input hidden="true" name="forum_id" value="${item.id }">
										<button type="submit" class="btn btn-secondary">DésInscrire</button>
									</form>
								</td>
							</c:when>
							<c:otherwise>
								<td style="color: red;">Non Registered</td>
								<td>
									<form action="UserSubscription" method="post">
										<input hidden="true" name="action" value="inscrire">
										<input hidden="true" name="forum_id" value="${item.id }">
										<button type="submit" class="btn btn-secondary">S'Inscrire</button>
									</form>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>