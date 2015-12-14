
'use strict';

var app = app || {};

app.GameModel = Backbone.Model.extend({
	
	defaults: {
		'status': 'waiting',
	},
	
	isWaiting: function() {
		return this.get('status')=='waiting';
	},
	
	isPlaying: function() {
		return !this.isWaiting();
	}
	
});