
'use strict';

var app = app || {};

$(function(){
			
	// Alertify settings
    alertify.set('notifier','position', 'top-right');
    alertify.defaults.glossary.title = 'Wazabi';
	
	// Models
	app.game       = new app.GameModel();
	app.mainPlayer = new app.PlayerModel();
	
	// Collections
	app.playerCards = new app.CardCollection();
	app.playerDices = new app.DiceCollection();
	app.challengers = new app.ChallengerCollection();
		
	// Views
	app.board           = new app.BoardView({ model:app.game });
	app.mainPlayerView  = new app.PlayerView({ model:app.mainPlayer });
	app.playerCardsView = new app.CardListView({ collection:app.playerCards });
	app.playerDicesView = new app.DiceListView({ collection:app.playerDices });
	app.challengersView = new app.ChallengersListView({ collection:app.challengers });
	app.quitButton      = new app.QuitButtonView();
	
	// Updating local status
	app.mainPlayer.on('change:play', function(){
		
		app.Status.instance().set(app.Status.C.ROLL_DICE);
		alertify.success('C\'est maintenant a vous de jouer! Relancer les des en cliquant dessus...');
	});
	
	// Scheduler
	app.scheduler = new app.SchedulerClass(5);
	app.scheduler.registerListener(app.registerSchedulerListeners);
	app.scheduler.start();
	
});

app.registerSchedulerListeners = function(statusData) {
	
	app.game.set('status', statusData.status);
	
	app.mainPlayer.set('name', statusData.player.name);
	app.mainPlayer.set('play', statusData.player.play);
	
	app.playerCards.reset(statusData.hand.cards);
	
	var tempDicesList = [];
	_.each(statusData.hand.dices, function(diceValue) {
		tempDicesList.push( new app.DiceModel({ value:diceValue }) );
	});		
	app.playerDices.reset(tempDicesList);
	
	app.challengers.reset(statusData.challengers);
}