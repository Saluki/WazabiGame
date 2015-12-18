
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Dashboard"/>
	<jsp:param name="cssFile" value="dashboard.css"/>
</jsp:include>
			
<div id="dashboard-container">

	<img src="assets/gift.png">
	
	<h2>Bienvenue ${ sessionScope.authenticated.pseudo }</h2>
	
	<div class="button-toolbar">
		<a href="app/scoreboard.html" class="btn btn-default">
			<img src="assets/mitten.png"><br>
			Voir historique
		</a>
		<a href="app/game.html" class="btn btn-default">
			<img src="assets/penguin.png"><br>
			Commencer partie
		</a>
		<a href="app/logout.html" class="btn btn-default">
			<img src="assets/star.png"><br>
			Quitter
		</a>
	</div>
	
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
		
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>