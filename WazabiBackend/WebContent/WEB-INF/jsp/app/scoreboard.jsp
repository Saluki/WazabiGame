<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Scoreboard"/>
	<jsp:param name="cssFile" value="scoreboard.css"/>
</jsp:include>
		
<div id="scoreboard-container">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Historique des parties</h2>
		</div>	
		<div class="panel-body">
			
			<c:choose>
				<c:when test="${ fn:length(requestScope.listeHistorique) == 0 }">
					<b>Aucune partie dans l'historique</b>
				</c:when>
				<c:when test="${ fn:length(requestScope.listeHistorique) == 1 }">
					<b>Une seule partie dans l'historique</b>
				</c:when>
				<c:when test="${ fn:length(requestScope.listeHistorique) > 1 }">
					<b>${ fn:length(requestScope.listeHistorique) } parties dans l'historique</b>
				</c:when>
			</c:choose>
			
			<br><br>
			
			<table class="table">
			  <tr>
			    <th>Nom partie</th>
			    <th>Gagnant</th>
			    <th>Date de début</th>
			  </tr>
			  <c:forEach var="partie" items="${ requestScope.listeHistorique }">
				<tr>
					<td>${ partie.nom }</td>
					<td>${ partie.vainqueur.pseudo }</td>
					<td>${ partie.timestamp_creation }</td>
				</tr>
			</c:forEach>
			</table>
			
			<br><br>
			
			<a href="app/dashboard.html" class="btn btn-default">Retour</a>
			
		</div>
	</div>
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>