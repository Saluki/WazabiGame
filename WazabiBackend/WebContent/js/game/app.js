
'use strict';

var app = app || {};

$(function(){
			
	// Alertify settings
    alertify.set('notifier','position', 'top-right');
    alertify.defaults.glossary.title = 'Wazabi';
    alertify.alert().setting({
		'closable': false
	});
	
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
	app.quitButton      = new app.ActionButtonsView();
		
	// Updating local status
	app.mainPlayer.on('change:play', function(){
		
		if( !app.mainPlayer.get('play') ) {
			return;
		}
		
		app.Status.instance().set(app.Status.C.ROLL_DICE);
		
		if( app.game.get('status')=='COMMENCE' ) {
			alertify.success('C\'est maintenant a vous de jouer! Relancer les des en cliquant dessus...');
		}
	});
	
	// Updating skip number
	app.mainPlayer.on('change:skip', function(){
		
		var nbSkips = app.mainPlayer.get('skip');
		
		if( nbSkips>0 ) {
			
			var displayText = '<b>Perdu!</b> Vous ne pourrez pas jouer pendant ' + nbSkips + ' tours';
			
			if( nbSkips==1 ) {
				displayText = '<b>Attention!</b> Vous ne pourrez pas jouer au prochain tour';
			}
			
			$('#wazabi-skip-tile').html(displayText);
			$('#wazabi-skip-tile').css('display', 'block');
		}
		else {
			$('#wazabi-skip-tile').css('display', 'none');
		}		
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
	app.mainPlayer.set('skip', statusData.player.skip);
	
	app.playerCards.reset(statusData.hand.cards);
	
	var tempDicesList = [];
	_.each(statusData.hand.dices, function(diceValue) {
		tempDicesList.push( new app.DiceModel({ value:diceValue }) );
	});		
	app.playerDices.reset(tempDicesList);
	
	app.challengers.reset(statusData.challengers);
}