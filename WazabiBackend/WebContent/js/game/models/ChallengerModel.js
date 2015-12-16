
'use strict';

var app = app || {};

app.ChallengerModel = Backbone.Model.extend({
	
	defaults: {
		'id': '0',
		'name': '',
		'dices': []
	}
	
});