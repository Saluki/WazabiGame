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
	<c:when test="${ fn:length(requestScope.listeHistorique) > 0 }">
		<b>Nombre de parties: ${ fn:length(requestScope.listeHistorique) > 0 }</b>
	</c:when>
</c:choose>

<br><br>

<c:forEach items="${ requestScope.listeHistorique }">
	PARTIE #<br>
</c:forEach>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>