
'use strict';

var app = app || {};

$(function(){
		
	console.log('Starting application...');
	
	app.scheduler = new app.SchedulerClass(5);
	
	app.mainPlayer = new app.PlayerModel();
	app.playerCards = new app.CardCollection();
	
	// TODO Show game waiting screen following extracted game status
	// Put #board on 'waiting status' and when status
	// - when 'playing', erase and show toolbars
	// - when 'waiting', reshow default waiting ==> TEMPLATE
	
	// TODO Current data loader, and yes, it's really ugly
	app.scheduler.registerListener(function(statusData){
		
		app.mainPlayer.set('name', statusData.player.name);
		app.mainPlayer.set('play', statusData.player.play);
		
		app.playerCards.reset();
		app.playerCards.add(statusData.hand.cards);
		
		// TODO Load dices
				
	});
	
	app.mainPlayerView = new app.PlayerView({ model:app.mainPlayer });
	app.playerCardsView = new app.CardListView({ collection:app.playerCards });
	
	app.scheduler.start();
	
});