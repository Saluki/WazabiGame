
'use strict';

var app = app || {};

app.Status = (function() {
	
	var CONSTANTS = {
		PAS_COMMENCE : 'PAS_COMMENCE',
		COMMENCE     : 'COMMENCE',
		EN_ATTENTE   : 'EN_ATTENTE',
		ANNULEE      : 'ANNULEE'
	}
	
	var instance;
	var currentStatus = CONSTANTS.EN_ATTENTE;
	
	function createInstance() {
		
		return {
			
			get: function(){
				return currentStatus;
			},
			
			set: function(statusName){
				currentStatus = statusName;
			},
			
			is: function(statusName) {
				return (statusName==currentStatus);
			},
			
			CONSTANTS: CONSTANTS
			
		};
	}
	
	return {
		
		instance: function() {
			
			if( !instance ){
				instance=createInstance();
			}
			return instance;
		}
	}

})();