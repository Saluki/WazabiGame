
'use strict';

var app = app || {};

app.ChallengerModel = Backbone.Model.extend({
	
	defaults: {
		'name': '',
		'dices': new app.DiceCollection()
	}
	
});