
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Partie"/>
	<jsp:param name="cssFile" value="game.css"/>
</jsp:include>
			
<div id="board">
	
	<div id="player-view"></div>	
	<div id="cards-list-view" class="clearfix"></div>
	<div id="dices-list-view"></div>
	
	<div id="waiting-screen">
		En attente de joueurs...
	</div>
	
</div>

<script type="text/template" id="player-view-template">
	
	Player <\%= name %> 

	<\% if( play ) { %>
		is playing
	<\% } else { %>
		is waiting
	<\% } %>
	
</script>

<script type="text/template" id="card-view-template">
	
	<div class="card-cost">
		<\%= cost %> Wazabi
	</div>

	<div class="card-title">
		<\%= name %>
	</div>

	<div class="card-description">
		<\%= description %>
	</div>
	
</script>

<script type="text/template" id="dice-view-template">
	
	<\%= value %>

</script>

<!-- JS Dependencies -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<script src="bower_components/underscore/underscore-min.js"></script>
<script src="bower_components/backbone/backbone-min.js"></script>

<!-- JS Models and Collections -->
<script src="js/game/models/GameModel.js"></script>
<script src="js/game/models/DiceModel.js"></script>
<script src="js/game/collections/DiceCollection.js"></script>
<script src="js/game/models/CardModel.js"></script>
<script src="js/game/collections/CardCollection.js"></script>
<script src="js/game/models/ChallengerModel.js"></script>
<script src="js/game/models/PlayerModel.js"></script>
<script src="js/game/components/SchedulerClass.js"></script>

<!-- JS Views -->
<script src="js/game/views/BoardView.js"></script>
<script src="js/game/views/PlayerView.js"></script>
<script src="js/game/views/CardView.js"></script>
<script src="js/game/views/CardListView.js"></script>
<script src="js/game/views/DiceView.js"></script>
<script src="js/game/views/DiceListView.js"></script>

<!-- JS Application -->
<script src="js/game/app.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>