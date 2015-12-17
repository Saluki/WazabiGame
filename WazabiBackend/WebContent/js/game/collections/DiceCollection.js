
'use strict';

var app = app || {};

app.DiceCollection = Backbone.Collection.extend({
	
	model: app.DiceModel,
	
	countWazabis: function() {
		
		return app.playerDices.where({'value':'WAZABI'}).length;
	}
	
});