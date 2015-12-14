
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Partie"/>
</jsp:include>
			
<h2>Wazabi</h2>

<a href="app/dashboard.html" class="btn btn-default">Retour</a>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="bower_components/underscore/underscore-min.js"></script>
<script type="text/javascript" src="bower_components/backbone/backbone-min.js"></script>

<!-- JS Scripts -->
<script type="text/javascript" src="js/game/scheduler.js"></script>
<script type="text/javascript" src="js/game/app.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>