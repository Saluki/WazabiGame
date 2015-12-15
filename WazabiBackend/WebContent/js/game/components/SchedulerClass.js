
'use strict';

var app = app || {};

app.SchedulerClass = function(refreshTime) {
	
	var refreshTime = refreshTime;
	var timerInstance = undefined;
	var listeners = [];
	var lastStatus = {};
	
	var fetchStatus = function() {
		
		$.getJSON('api/game/status', function(statusData){
			
			lastStatus = statusData;
			emitPulse();
			
		}).fail(function(){
			console.error('Status call failed');
		});
	}
	
	var emitPulse = function() {
		
		_.each(listeners, function(listener) {
			listener(lastStatus);
		});
	}
	
	this.registerListener = function(listener) {
		listeners.push(listener);
	}
	
	this.getRefreshTime = function() {
		return refreshTime;
	}
	
	this.isRunning = function() {
		return (timerInstance!=undefined);
	}
	
	this.start = function() {
		
		if( this.isRunning() ) {
			return false;
		}
		
		timerInstance = setInterval(fetchStatus, refreshTime*1000);
		return true;
	}
	
	this.stop = function() {
		
		if( !this.isRunning() ) {
			return false;
		}
		
		clearInterval(timerInstance);
		timerInstance = undefined;
		return true;
	}
}