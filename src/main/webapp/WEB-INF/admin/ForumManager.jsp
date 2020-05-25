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
		<h1>Liste des Forums :</h1>
		<c:if test="${! empty ajoute_msg}">
			<p style="color: green;">
				<c:out value="${ajoute_msg}"></c:out>
			</p>
			<c:remove var="ajoute_msg" />
		</c:if>
		<c:if test="${!empty delete_msg}">
			<p style="color: green;">
				<c:out value="${delete_msg}"></c:out>
			</p>
			<c:remove var="delete_msg" />
		</c:if>
		<br>
		<table  class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">Title</th>
					<th scope="col">Owner</th>
					<th scope="col">Description</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="forum" items="${lst_Forum}">
					<tr>
						<td>
							<c:out value="${forum.title }"></c:out>
						</td>
						<td>
							<c:out value="${forum.owner.login }"></c:out>
						</td>
						<td>
							<c:out value="${forum.description }"></c:out>
						</td>
						<td>
							<a href="SubscriptionManager?id=${forum.id }">Subscriptions</a>
						</td>
						<td>
							<a href="MessageManager?id=${forum.id }">Messages</a>
						</td>
						<td>
							<form action="ForumManager" method="post">
								<input hidden="true" name="action" value="delete">
								<input hidden="true" name="forumId" value="${forum.id }">
								<button type="submit" class="btn btn-secondary">delete</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>

		<p>Ajouter un nouveau Forum:</p>
		<form action="Validation?item=forum" method="post">
			<label> Title: </label>
			<input type="text" name="Forum title" value="${title}">
			<br>
			<label> Description: </label>
			<input type="text" name="Forum description" value="${description}">
			<br>
			<c:if test="${!empty exist_msg}">
				<p style="color: red;">
					<c:out value="${exist_msg}"></c:out>
				</p>
				<c:remove var="exist_msg" />
			</c:if>
			<input type="submit" value="Submit" class="btn btn-secondary">
		</form>
	</div>
</body>
</html>