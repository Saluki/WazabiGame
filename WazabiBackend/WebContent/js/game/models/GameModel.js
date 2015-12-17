
'use strict';

var app = app || {};

app.GameModel = Backbone.Model.extend({
	
	defaults: {
		'status': 'EN_ATTENTE'
	},
	
	isWaiting: function() {
		return this.get('status') == 'EN_ATTENTE';
	},
	
	isPlaying: function() {
		return this.get('status') == 'COMMENCE';
	},
	
	isStopped: function() {
		return this.get('status') == 'ANNULEE' || this.get('status') == 'TERMINE';
	},
	
	CONSTANTS : {
		PAS_COMMENCE : 'PAS_COMMENCE',
		COMMENCE     : 'COMMENCE',
		EN_ATTENTE   : 'EN_ATTENTE',
		ANNULEE      : 'ANNULEE',
		TERMINE		 : 'TERMINE'
	}
	
});