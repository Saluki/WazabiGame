<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Scoreboard"/>
</jsp:include>
			
<h2>Historique des parties</h2>

<a href="app/dashboard.html" class="btn btn-default">Retour</a>

<c:choose>
	<c:when test="${ fn:length(requestScope.listeHistorique) == 0 }">
		<b>Aucune partie</b>
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

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>