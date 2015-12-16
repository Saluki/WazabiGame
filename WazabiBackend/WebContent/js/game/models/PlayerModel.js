
'use strict';

var app = app || {};

app.PlayerModel = Backbone.Model.extend({
	
	defaults: {
		'name': '',
		'play': false,
		'skip': 0
	}
	
});