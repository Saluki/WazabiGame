<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Inscription" />
	<jsp:param name="cssFile" value="auth.css" />
</jsp:include>

<div id="login-box">

	<div id="header-picture">
		<img src="assets/tree.png">
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Inscription</h2>
		</div>
		<div class="panel-body">

			<form method="POST" action="register.html">

				<br>

				<div class="input-group">
					<span class="input-group-addon">
						<i class="glyphicon glyphicon-user"></i>
					</span> 
					<input type="text" name="pseudo" placeholder="Pseudo" class="form-control" value="${ param.pseudo }">
				</div>				
				<br> 
			
				<div class="input-group">
					<span class="input-group-addon">
						<i class="glyphicon glyphicon-lock"></i>
					</span> 
					<input type="password" name="mot_de_passe" placeholder="Mot de passe" class="form-control">
				</div>	
				<br> 
			
				<div class="input-group">
					<span class="input-group-addon">
						<i class="glyphicon glyphicon-repeat"></i>
					</span> 
					<input type="password" name="mot_de_passe_repeat" placeholder="Repeter mot de passe" class="form-control">
				</div>		
				<br><br> 
				
				<a href="index.html" class="btn btn-default pull-left">
					<i class="glyphicon glyphicon-chevron-left"></i>&nbsp;&nbsp;Connection
				</a>
				<input type="submit" value="Inscription" class="btn btn-danger pull-right">

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
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<!-- JS Utilities -->
<script>

	$('input[type="text"]:first').focus();

</script>

<jsp:include page="/WEB-INF/jspf/footer.jspf" />