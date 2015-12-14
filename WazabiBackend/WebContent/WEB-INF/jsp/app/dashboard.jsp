
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Dashboard"/>
	<jsp:param name="cssFile" value="dashboard.css"/>
</jsp:include>
			
<h2>Dashboard</h2>

<a href="app/scoreboard.html" class="btn btn-default">Historique parties</a>
<a href="app/game.html" class="btn btn-default">Commencer partie</a>
<a href="app/logout.html" class="btn btn-danger">Quitter</a>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
		
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>