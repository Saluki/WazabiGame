
'use strict';

var app = app || {};

app.ChallengerModel = Backbone.Model.extend({
	
	defaults: {
		'id': 5,
		'name': '',
		'dices': []
	}
	
});