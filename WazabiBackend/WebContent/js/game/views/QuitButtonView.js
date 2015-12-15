
'use strict';

var app = app || {};

app.QuitButtonView = Backbone.View.extend({
	
	el: '#btn-game-quit',
	
	events: {
		'click': 'sendServerQuit'
	},

	initialize: function() {
		// No initialization
	},

	render: function() {
		return this;
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
			
			alertify.alert('Merci d\'avoir joue une partie de Wazabi.<br>Vous allez etre redirige vers votre dashboard dans quelques secondes...', function(){
				window.location.replace('app/dashboard.html');
			});

			setTimeout(function(){
				window.location.replace('app/dashboard.html');
			}, 7000);
			
		})
		.fail(function(){
			alertify.alert('Desole, impossible de quitter la partie');
		});
	}
	
});