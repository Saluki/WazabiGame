
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
		return !this.isWaiting();
	},
	
	CONSTANTS : {
		PAS_COMMENCE : 'PAS_COMMENCE',
		COMMENCE     : 'COMMENCE',
		EN_ATTENTE   : 'EN_ATTENTE',
		ANNULEE      : 'ANNULEE'
	}
	
});