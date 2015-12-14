
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Error"/>
</jsp:include>
			
<h1 class="text-danger">
	Ooooooops, something went wrong...
</h1>

<p>
	${ requestScope.errorMessage }
</p>
		
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>