
var app = app || {};

app.SchedulerClass = function(refreshTime) {
	
	var refreshTime = refreshTime;
	var timerInstance = undefined;
	var listeners = [];
	
	var emitPulse = function() {
		
		_.each(listeners, function(listener) {
			listener();
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
		
		timerInstance = setInterval(emitPulse, refreshTime*1000);
		return true;
	}
	
	this.stop = function() {
		
		if( !this.isRunning() ) {
			return false;
		}
		
		clearInterval(timerInstance);
		return true;
	}
}