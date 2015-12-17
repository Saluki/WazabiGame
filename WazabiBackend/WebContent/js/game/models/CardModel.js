
'use strict';

var app = app || {};

app.CardModel = Backbone.Model.extend({
	
	defaults: {
		'id': 0,
		'name': '',
		'description': '',
		'image': '',
		'effect': 0,
		'cost': 0,
		'input': ''
	}
	
});