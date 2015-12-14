
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Nouvelle Partie"/>
</jsp:include>
			
<h2>Creer une nouvelle partie</h2>

<form method="POST" action="app/game.html">
	<label>Nom partie</label>
	<input type="text" name="nom" placeholder="Nom"><br><br>
	<input type="submit" value="Creer">
</form>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
		
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>