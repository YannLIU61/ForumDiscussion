<!DOCTYPE html>
<html>
<head>
<title>Connection</title>
<meta charset="UTF-8">
<link href="assets/css/mystyle.css" rel="stylesheet">
</head>
<body>
	<div class="login-page">	
		<div class="form">
		<p>SR03 Forum application</p>
			<form action="Connexion" method="POST">
				<div>
					<label for="username">Login:</label>
					<input type="text" id="username" name="username" required>
				</div>

				<div>
					<label for="pass">Password:</label>
					<input type="password" id="pass" name="password" required>
				</div>
				<div>
					<p style="color: red;">
						<c:if test="${!empty bd_Info}">
							<c:out value="${bd_Info}"></c:out>
							<c:remove var="bd_Info" />
						</c:if>
					</p>
				</div>

				<input type="submit" value="Sign in">
			</form>
		</div>
	</div>
</body>
</html>
