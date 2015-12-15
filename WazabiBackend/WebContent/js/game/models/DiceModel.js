
'use strict';

var app = app || {};

app.DiceModel = Backbone.Model.extend({
	
	defaults: {
		'value': '',
	},
	
	getIconTag: function() {
		
		var value = this.get('value');
		
		if( value==this.CONSTANTS.WAZABI ) {
			return 'star';
		}
		else if( value==this.CONSTANTS.PIOCHE ) {
			return 'gift';
		}
		else if( value==this.CONSTANTS.DE ) {
			return 'share-alt';
		}
		
		return 'flash';
	},
	
	CONSTANTS: {
		WAZABI: 'WAZABI',
		PIOCHE: 'PIOCHE',
		DE: 'DE'
	}
	
});