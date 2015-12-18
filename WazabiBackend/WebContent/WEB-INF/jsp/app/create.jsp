<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Nouvelle Partie"/>
	<jsp:param name="cssFile" value="create.css"/>
</jsp:include>
			
<div id="create-form">
	<div class="panel panel-default">
		<div class="panel-heading">
			
			<h2 class="panel-title">
				Creer une nouvelle partie
			</h2>
			
		</div>
		<div class="panel-body">
			
			<form method="POST" action="app/game.html">
				
				<br>
				<label>Donnez un nom de partie</label>
				<input type="text" name="nom" placeholder="Nom partie" class="form-control"><br>
				<br>
				
				<a href="app/dashboard.html" class="btn btn-default">
					<i class="glyphicon glyphicon-chevron-left"></i>&nbsp;&nbsp;Retour
				</a>
				<input type="submit" value="Lancer la partie" class="btn btn-danger pull-right">
				
			</form>
		
		</div>
	</div>
	
	<c:if test="${ not empty requestScope.errorMessage }">
		<div class="alert alert-info">
			<i class="glyphicon glyphicon-fire"></i>&nbsp;
			<b>Attention!</b>&nbsp;
			${ requestScope.errorMessage }
		</div>
	</c:if>
	
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
		
<!-- JS Utilities -->
<script>

	$('input[type="text"]:first').focus();

</script>		
	
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>