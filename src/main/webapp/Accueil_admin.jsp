
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Navigation Administrateur</title>
<%@ include file="/WEB-INF/menu/AdminMenu.jsp"%>
</head>
<body>
	<div class="container">
	<br>
		<h1>
			Bonjour
			<c:out value="${login.firstName}"></c:out>
			<c:out value="${login.lastName}"></c:out>
		</h1>
		<br>
		<p>Vous êtres connecté en tant qu'Adminstrateur</p>
	</div>
</body>
</html>