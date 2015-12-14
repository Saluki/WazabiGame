
'use strict';

var app = app || {};

$(function(){
		
	console.log('Starting application...');
	
	app.scheduler = new app.SchedulerClass(5);
	
	app.game = new app.GameModel();
	app.mainPlayer = new app.PlayerModel();
	
	app.playerCards = new app.CardCollection();
	app.playerDices = new app.DiceCollection();
	
	// TODO Current data loader, and yes, it's really ugly
	app.scheduler.registerListener(function(statusData){
		
		app.game.set('status', statusData.status);
		
		app.mainPlayer.set('name', statusData.player.name);
		app.mainPlayer.set('play', statusData.player.play);
		
		app.playerCards.reset(statusData.hand.cards);
		
		var tempDicesList = [];
		_.each(statusData.hand.dices, function(diceValue) {
			tempDicesList.push( new app.DiceModel({ value:diceValue }) );
		});		
		app.playerDices.reset(tempDicesList);
				
	});
	
	app.board = new app.BoardView({ model:app.game });
	app.mainPlayerView = new app.PlayerView({ model:app.mainPlayer });
	app.playerCardsView = new app.CardListView({ collection:app.playerCards });
	app.playerDicesView = new app.DiceListView({ collection:app.playerDices });
	
	app.scheduler.start();
	
});