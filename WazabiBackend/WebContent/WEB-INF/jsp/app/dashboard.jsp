
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Dashboard"/>
	<jsp:param name="cssFile" value="dashboard.css"/>
</jsp:include>
			
<div id="dashboard-container">

	<h2>Dashboard</h2>
	
	<div class="button-toolbar">
		<a href="app/scoreboard.html" class="btn btn-default">
			<i class="glyphicon glyphicon-list-alt"></i>Historique parties
		</a>
		<a href="app/game.html" class="btn btn-default">
			<i class="glyphicon glyphicon-send"></i>Commencer partie
		</a>
	</div>
	
	<a href="app/logout.html" class="btn btn-danger btn-lg btn-bottom">
		<i class="glyphicon glyphicon-off"></i>&nbsp;&nbsp;Quitter
	</a>
	
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
		
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>