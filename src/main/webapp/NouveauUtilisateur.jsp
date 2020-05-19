<!DOCTYPE html>

<html>
<head>
<title>Créer Utilisateur</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ include file="/WEB-INF/menu/AdminMenu.jsp"%>
</head>
<body>
	<div class="container">
		<h1>Créer un nouveau utilisateur:</h1>
		<c:if test="${!empty error_msg }">
			<div class="alert alert-danger" role="alert">
				<c:out value="${error_msg}"></c:out>
			</div>
			<c:remove var="error_msg" />
		</c:if>
		<br>
		<form action="Validation?item=user" method="post">
			<div class="form-group row">
				<label for="frname" class="col-sm-2 col-form-label">First
					name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="frname"
						name="User first name" value="${newUser.getFirstName()}" required
					>
				</div>
			</div>
			<div class="form-group row">
				<label for="faname" class="col-sm-2 col-form-label">Last
					name</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="faname"
						name="User familly name" value="${newUser.getLastName()}" required
					>
				</div>
			</div>

			<div class="form-group row">
				<label for="email" class="col-sm-2 col-form-label">Login(mail)
				</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" id="email"
						name="User email" value="${newUser.getLogin()}" required
					>
				</div>
			</div>

			<div class="form-group row">
				<label for="psw" class="col-sm-2 col-form-label">Password </label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="psw"
						name="User password" value="${newUser.getPwd()}" required
					>
				</div>
			</div>
			<label> male </label>
			<input type="radio" id="male" name="gender" value="male"
				<c:if test="${newUser.getGender()=='male'}">checked</c:if>
			/>
			<br>
			<label> female </label>
			<input type="radio" id="female" name="gender" value="female"
				<c:if test="${newUser.getGender()=='female'}">checked</c:if>
			/>
			<br>

			<label> Admin </label>
			<input type="checkbox" name="role" value="Admin"
				<c:if test="${newUser.getRole()=='Admin' }">checked</c:if>
			/>

			<c:if test="${empty exist_msg}">
				<div class="form-group row">
					<div class="col-sm-10">
						<button type="submit" value="Submit" class="btn btn-primary">Submit</button>
					</div>
				</div>
			</c:if>
			<c:if test="${!empty exist_msg}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${exist_msg}"></c:out>
				</div>
				<div class="form-group row">
					<div class="col-sm-10">
						<button type="submit" value="oui" name="validator"
							class="btn btn-primary"
						>Valide</button>
					</div>
				</div>
				<c:remove var="exist_msg" />
			</c:if>
		</form>
	</div>
</body>
</html>

