
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="refresh" content="60; url=UserMessage">
<title>Fil de discussion</title>
<%@ include file="/WEB-INF/menu/UserMenu.jsp"%>
</head>
<body>
	<div class="container">
		<br>
		<h1>Fil de discussion:</h1>
		<c:if test="${empty fil_de_discussion }">
			<div class="alert alert-danger" role="alert">Aucun message pour
				l'instant</div>
		</c:if>
		<c:if test="${!empty poster_msg }">
			<div class="alert alert-danger" role="alert">
				<c:out value="${poster_msg}"></c:out>
			</div>
			<c:remove var="poster_msg" />
		</c:if>
		<form action="UserMessage" method="post">
			<div class="form-row">
				<div class="form-group col-md-6 mb-2">
					<textarea rows="2" cols="150" maxlength="300" class="form-control"
						id="postermsg" name="new_msg" placeholder="Entrez vos messages"
					></textarea>
				</div>
				<button type="submit" class="btn btn-primary mb-2">Poster</button>
			</div>
		</form>
		<table class="table table-striped">
			<thead>
				<tr>
					<th scope="col">DataDePublication</th>
					<th scope="col">Contenue</th>
					<th scope="col">Publisher</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="msg" items="${fil_de_discussion}">

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