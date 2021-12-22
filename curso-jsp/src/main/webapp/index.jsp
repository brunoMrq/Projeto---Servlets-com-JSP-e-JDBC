<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>Curso JSP</title>

<style type="text/css">
form {
	position: absolute;
	top: 40%;
	left: 33%;
	right: 33%;
}

h2 {
	position: absolute;
	top: 25%;
	left: 33%;
	right: 33%;
}

.msg {
	position: absolute;
	bottom: 10%; left : 33%;
	color: red;
	left: 33%;
}
</style>
</head>
<body>
	<h2>Bem Vindo Humano</h2>

	<form action="<%=request.getContextPath() %>/ServletLogin" method="post"
		class="row g-3 needs-validation" novalidate>
		<input type="hidden" value="<%=request.getParameter("url")%>"name="url">
		<div class="col-md-6">
			<label class="form-label">Login</label> <input name="login"	type="text" class="form-control" required="required">
			<div class="invalid-feedback">Insira o Login</div>
		</div>
		<div class="col-md-6">
			<label class="form-label">Senha</label> <input name="senha"	type="password" class="form-control" required="required">
			<div class="invalid-feedback">Insira a Senha</div>
		</div>

		<div class="col-12">
			<button type="submit" class="btn btn-primary">Acessar</button>
		</div>



	</form>
	<h5 class="msg">${msg}</h5>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script type="text/javascript">
		//Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms).forEach(function(form) {
				form.addEventListener('submit', function(event) {
					if (!form.checkValidity()) {
						event.preventDefault()
						event.stopPropagation()
					}

					form.classList.add('was-validated')
				}, false)
			})
		})()
	</script>

</body>
</html>