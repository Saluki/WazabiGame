<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Scoreboard"/>
	<jsp:param name="cssFile" value="scoreboard.css"/>
</jsp:include>
		
<div id="scoreboard-container">

	<div id="header-picture">
		<img src="assets/mitten.png">
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Historique des parties</h2>
		</div>	
		<div class="panel-body">
			
			<div class="alert alert-warning">
				<c:choose>
					<c:when test="${ fn:length(requestScope.listeHistorique) == 0 }">
						<b>Vous n'avez aucune partie dans l'historique</b>
					</c:when>
					<c:when test="${ fn:length(requestScope.listeHistorique) == 1 }">
						<b>Une seule partie dans l'historique</b>
					</c:when>
					<c:when test="${ fn:length(requestScope.listeHistorique) > 1 }">
						<b>${ fn:length(requestScope.listeHistorique) } parties dans l'historique</b>
					</c:when>
				</c:choose>
			</div>
						
			<c:if test="${ fn:length(requestScope.listeHistorique) > 0 }">
										
				<table class="table">
					<thead>
					  <tr>
					    <th>Nom partie</th>
					    <th>Gagnant</th>
					    <th>Date de début</th>
					    <th>Statut</th>
					  </tr>
				  	</thead>
				  	<tbody>
						  <c:forEach var="partie" items="${ requestScope.listeHistorique }">
							<tr>
								<td>${ partie.nom }</td>
								<td>
									<c:if test="${ empty partie.vainqueur.pseudo }">
										<span class="label label-info">Pas de vainqueur</span>
									</c:if>
									${ partie.vainqueur.pseudo }
								</td>
								<td>${ partie.timestamp_creation }</td>
								<td>
									<c:choose>
										<c:when test="${ partie.statut == 'ANNULEE' }">
											<span class="label label-danger">Annulee</span>	
										</c:when>
										<c:when test="${ partie.statut == 'PAS_COMMENCE' }">
											<span class="label label-success">Terminee</span>
										</c:when>
										<c:otherwise>
											<span class="label label-info">Autre</span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<br>
			
			</c:if>
			
			<a href="app/dashboard.html" class="btn btn-default">
				<i class="glyphicon glyphicon-chevron-left"></i>&nbsp;&nbsp;Retour
			</a>
			
		</div>
	</div>
</div>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>