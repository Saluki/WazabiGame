
'use strict';

var app = app || {};

app.CardModel = Backbone.Model.extend({
	
	defaults: {
		'id': 0,
		'name': '',
		'description': '',
		'effect': 0,
		'cost': 0,
		'input': ''
	}
	
});