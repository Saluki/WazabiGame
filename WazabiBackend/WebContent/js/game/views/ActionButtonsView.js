
'use strict';

var app = app || {};

app.ActionButtonsView = Backbone.View.extend({
	
	el: '#actions-view',
	
	events: {
		'click #btn-game-quit': 'sendServerQuit',
		'click #btn-game-next': 'sendServerNext'
	},

	initialize: function() {},

	render: function() {
		return this;
	},
	
	sendServerNext: function() {
		
		if( !app.Status.instance().is(app.Status.C.CHOOSE_CARD) && 
				!app.Status.instance().is(app.Status.C.ENDING) ) {
			
			alertify.warning('Attendez votre tour et lancez les des avant de pouvoir continuer');
			return;
		}
		
		$.ajax({
			dataType: 'json',
			url: 'api/game/next',
			context: this
		})
		.success(function(data){
			
			if( data.status==false ) {
				alertify.alert('Desole, impossible de terminer votre tour de jeu');
				return;
			}
			
			alertify.success('Passage au joueur suivant');
			
			app.Status.instance().set(app.Status.C.WAITING);
			app.mainPlayer.set('play', false);
			
		})
		.fail(function(){
			alertify.alert('Desole, impossible de terminer votre tour de jeu');
		});
	},
	
	sendServerQuit: function() {
				
		$.ajax({
			dataType: 'json',
			url: 'api/game/quit',
			context: this
		})
		.success(function(data){
			
			if( data.status==false ) {
				alertify.alert('Desole, impossible de quitter la partie');
				return;
			}
			
			app.board.showFinishingScreen();
			
		})
		.fail(function(){
			alertify.alert('Desole, impossible de quitter la partie');
		});
	}
	
});