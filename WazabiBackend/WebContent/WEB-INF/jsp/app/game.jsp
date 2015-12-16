
<jsp:include page="/WEB-INF/jspf/header.jspf">
	<jsp:param name="pageTitle" value="Partie"/>
	<jsp:param name="cssFile" value="game.css"/>
</jsp:include>
			
<div id="board">
	
	<div id="game-header" class="clearfix">
		<div id="player-view"></div>	
		<div id="actions-view">
			<div id="btn-game-next" class="btn btn-primary">
				<i class="glyphicon glyphicon-repeat"></i>&nbsp;&nbsp;Terminer tour de jeu
			</div>
			<div id="btn-game-quit" class="btn btn-danger">
				<i class="glyphicon glyphicon-ban-circle"></i>&nbsp;&nbsp;Quitter la partie
			</div>
		</div>
	</div>	
		
	<div id="cards-list-view" class="clearfix"></div>
	<div id="dices-list-view" class="clearfix"></div>
	<div id="challengers-list-view" class="clearfix"></div>
	
	<div id="waiting-screen">
		
		En attente de joueurs...
		
		<div class="progress">
		  <div class="progress-bar progress-bar-striped active" style="width: 100%"></div>
		</div>
		
	</div>
	
</div>

<script type="text/template" id="player-view-template">
	
	Joueur <b><\%= name %></b> 

	<\% if( play ) { %>
		peut jouer&nbsp;&nbsp;<i class="glyphicon glyphicon-play text-success"></i>
	<\% } else { %>
		est en attente&nbsp;&nbsp;<i class="glyphicon glyphicon-pause text-warning"></i>
	<\% } %>
	
</script>

<script type="text/template" id="card-view-template">
	
	<div class="card-cost">

		<\% for(var i=0; i<cost; i++) { %> 
			<i class="glyphicon glyphicon-star"></i> 
		<\% }  %>

	</div>

	<div class="card-title">
		<\%= name %>
	</div>

	<div class="card-description">
		<\%= description %>
	</div>
	
</script>

<script type="text/template" id="dice-view-template">
	
	<i class="glyphicon glyphicon-<\%= iconTag %>"></i>

</script>

<script type="text/template" id="challengers-list-view-template">
	
	<\% _.each(challengers, function(challenger){  %>

		<div class="challenger-profile">

			<i class="glyphicon glyphicon-user"></i>
			<br>
			<\%= challenger.name %><br>

		</div>

	<\% }) %>

</script>

<script type="text/template" id="give-dices-template">

	Suite � votre combinaison de des, vous pouvez faire un <b>don de des 
	a certains adversaires</b>. Choissisez-les dans le formulaire: 
	<br>

	<\% for(i=0; i<number; i++) { %>

	<div>
		<br>
		<label>Joueur</label>
		<select class="form-control select-give-dice">

			<\% challengers.forEach(function(challenger){ %>
				<option value="<\%= challenger.get('id') %>">
					<\%= challenger.get('name') %>
				</option>
			<\% }); %>			

		</select>
	</div>

	<\% } %>

</script>

<!-- JS Dependencies -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<script src="bower_components/underscore/underscore-min.js"></script>
<script src="bower_components/backbone/backbone-min.js"></script>
<script src="bower_components/alertify-js/build/alertify.min.js"></script>

<!-- JS Models and Collections -->
<script src="js/game/models/GameModel.js"></script>
<script src="js/game/models/DiceModel.js"></script>
<script src="js/game/collections/DiceCollection.js"></script>
<script src="js/game/models/CardModel.js"></script>
<script src="js/game/collections/CardCollection.js"></script>
<script src="js/game/models/ChallengerModel.js"></script>
<script src="js/game/models/PlayerModel.js"></script>
<script src="js/game/collections/ChallengerCollection.js"></script>

<!-- JS Views -->
<script src="js/game/views/BoardView.js"></script>
<script src="js/game/views/PlayerView.js"></script>
<script src="js/game/views/CardView.js"></script>
<script src="js/game/views/CardListView.js"></script>
<script src="js/game/views/DiceView.js"></script>
<script src="js/game/views/DiceListView.js"></script>
<script src="js/game/views/ChallengersListView.js"></script>
<script src="js/game/views/ActionButtonsView.js"></script>

<!-- Other JS Components -->
<script src="js/game/components/SchedulerClass.js"></script>
<script src="js/game/components/Status.js"></script>

<!-- JS Application -->
<script src="js/game/app.js"></script>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>