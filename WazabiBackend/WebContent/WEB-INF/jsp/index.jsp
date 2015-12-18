<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Connection" />
	<jsp:param name="cssFile" value="auth.css" />
</jsp:include>

<div id="login-box">

	<div id="header-picture">
		<img src="assets/bell.png">
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">
				Connectez-vous
			</h2>
		</div>
		<div class="panel-body">

			<form method="POST" action="auth.html">

				<br>

				<div class="input-group">
					<span class="input-group-addon">
						<i class="glyphicon glyphicon-user"></i>
					</span> 
					<input type="text" name="pseudo"
					placeholder="Pseudo" class="form-control" value="${ param.pseudo }">
				</div>
				
				<br>

				<div class="input-group">
					<span class="input-group-addon">
						<i class="glyphicon glyphicon-lock"></i>
					</span> 
					<input type="password"
					name="mot_de_passe" placeholder="Mot de passe" class="form-control">
				</div>
				
				<br><br>
				
				<a href="register.html" class="btn btn-default pull-left">
					<i class="glyphicon glyphicon-chevron-left"></i>&nbsp;&nbsp;Inscription
				</a>
				<input type="submit" value="Connection" class="btn btn-danger pull-right">

			</form>

		</div>
	</div>

	<c:if test="${ not empty requestScope.errorMessage }">
		<div class="alert alert-danger">
			<i class="glyphicon glyphicon-fire"></i>&nbsp;&nbsp;${ requestScope.errorMessage }
		</div>
	</c:if>

</div>

<!-- JS Dependencies -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>

<!-- JS Utilities -->
<script>

	$('input[type="text"]:first').focus();

</script>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />