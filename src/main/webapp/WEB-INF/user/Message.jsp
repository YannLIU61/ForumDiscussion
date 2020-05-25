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
		<button type="button" class="btn btn-primary mb-2"
			onclick="showTextArea()"
		>Écrire un message</button>
		<form action="UserMessage" method="post" id="myForum"
			style="display: none"
		>
			<div class="form-row">
				<div class="form-group col-md-6 mb-2">
					Remaining characters:
					<span id="count"></span>
					<textarea id="textinput" rows="2" cols="150" class="form-control"
						id="postermsg" name="new_msg" placeholder="Entrez vos messages"
					></textarea>
					<br>
					<button type="submit" class="btn btn-dark mb-2">Poster</button>
					<button type="button" class="btn btn-success mb-2"
						onclick="showTextArea()"
					>Cancel</button>
				</div>

			</div>
		</form>
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
	<script>
		var maxchar = 50;
		var i = document.getElementById("textinput");
		var c = document.getElementById("count");
		c.innerHTML = maxchar;

		i.addEventListener("keydown", count);

		function count(e) {
			var len = i.value.length;
			if (len > maxchar) {
				e.preventDefault();
			} else {
				c.innerHTML = maxchar - len - 1;
			}
		}
		function showTextArea() {
			var x = document.getElementById("myForum");
			if (x.style.display === "none") {
				x.style.display = "block";
			} else {
				x.style.display = "none";
			}
		}
	</script>

</body>
</html>