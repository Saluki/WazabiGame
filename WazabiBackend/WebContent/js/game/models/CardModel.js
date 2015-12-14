
'use strict';

var app = app || {};

app.CardModel = Backbone.Model.extend({
	
	defaults: {
		'name': '',
		'description': '',
		'image': '',
		'effect': 0,
		'cost': 0,
		'input': false
	}
	
});