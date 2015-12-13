
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Connection"/>
	<jsp:param name="cssFile" value="auth.css"/>
</jsp:include>
	
<div class="panel panel-default" id="login-box">
	<div class="panel-heading">
		<h2 class="panel-title">Connection</h2>
	</div>
	<div class="panel-body">
		
		<form method="POST" action="auth.html">
			
			<label>Pseudo</label>
			<input type="text" name="pseudo" placeholder="Pseudo" class="form-control"><br>
			
			<label>Mot de passe</label>
			<input type="password" name="mot_de_passe" placeholder="Mot de passe" class="form-control"><br><br>
			
			<a href="register.html" class="btn btn-default pull-left">Inscription</a>
			<input type="submit" value="Connection" class="btn btn-primary pull-right">
		
		</form>
		
	</div>
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<!-- JS Scripts -->
<script type="text/javascript" src="js/base.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>