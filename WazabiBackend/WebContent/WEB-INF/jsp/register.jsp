
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Inscription"/>
	<jsp:param name="cssFile" value="auth.css"/>
</jsp:include>
	
<div class="panel panel-default" id="login-box">
	<div class="panel-heading">
		<h2 class="panel-title">Inscription</h2>
	</div>
	<div class="panel-body">
		
		<form method="POST" action="register.html">
			
			<label>Pseudo</label>
			<input type="text" name="pseudo" placeholder="Pseudo" class="form-control"><br>
			
			<label>Mot de passe</label>
			<input type="password" name="mot_de_passe" placeholder="Mot de passe" class="form-control"><br>
			
			<label>Repeter mot de passe</label>
			<input type="password" name="mot_de_passe_repeat" placeholder="Mot de passe" class="form-control"><br><br>
			
			<a href="index.html" class="btn btn-default pull-left">Connection</a>
			<input type="submit" value="Inscription" class="btn btn-primary pull-right">
		
		</form>
		
	</div>
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<!-- JS Scripts -->
<script type="text/javascript" src="js/base.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>