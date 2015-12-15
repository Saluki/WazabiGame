
'use strict';

var app = app || {};

app.Status = (function() {
	
	var CONSTANTS = {
		WAITING		: 'WAITING',
		ROLL_DICE	: 'ROLL_DICE',
		REQUESTING	: 'REQUESTING',
		CHOOSE_CARD : 'CHOOSE_CARD',
		ENDING		: 'ENDING'
	}
	
	var instance;
	var currentStatus = CONSTANTS.WAITING;
	
	function createInstance() {
		
		return {
			
			get: function(){
				return currentStatus;
			},
			
			set: function(statusName){
				console.log('Changing status to ' + statusName);
				currentStatus = statusName;
			},
			
			is: function(statusName) {
				return (statusName==currentStatus);
			}
			
		};
	}
	
	return {
		
		instance: function() {
			
			if( !instance ){
				instance=createInstance();
			}
			return instance;
		},
		
		C: CONSTANTS
	}

})();