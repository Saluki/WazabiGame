
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Error"/>
</jsp:include>
			
<h1 class="text-danger">
	Ooooooops, something went wrong...
</h1>

<p>
	${ requestScope.errorMessage }
</p>

<!-- JS Dependencies -->
<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>

<!-- JS Scripts -->
<script type="text/javascript" src="js/base.js"></script>
		
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>