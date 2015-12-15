
'use strict';

var app = app || {};

app.DiceModel = Backbone.Model.extend({
	
	defaults: {
		'value': '',
	},
	
	getShortValue: function() {
		
		return this.get('value').charAt(0).toUpperCase();
	},
	
	CONSTANTS: {
		WAZABI: 'WAZABI',
		PIOCHE: 'PIOCHE',
		DE: 'DE'
	}
	
});