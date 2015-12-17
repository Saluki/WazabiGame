'use strict';

var app = app || {};

app.BoardView = Backbone.View.extend({

	el: '#board',
	
	events : {},
	
	initialize : function() {
		
		this.listenTo(this.model, 'change', this.render);
		
		this.$waitingScreen = $('#waiting-screen');
		this.$finishingScreen = $('#finishing-screen');
		
		this.$otherElements = this.$el
			.children()
			.not('#waiting-screen')
			.not('#finishing-screen');
		
		this.render();
	},

	render : function() {
				
		if( this.model.isWaiting() ) {
			this.showWaitScreen();
		}
		else if( this.model.isStopped() ) {
			
			this.showFinishingScreen();
			
			app.scheduler.stop();
			
			setTimeout(function(){
				window.location.replace('app/dashboard.html');
			}, 4000);
		}
		else {
			this.showBoardScreen();
		}
		
		return this;
	},
	
	showWaitScreen : function() {
		
		this.$finishingScreen.hide();
		this.$otherElements.hide();
		
		this.$waitingScreen.show();
	},
	
	showFinishingScreen : function() {
		
		this.$waitingScreen.hide();
		this.$otherElements.hide();
		
		this.$finishingScreen.show();
	},
	
	showBoardScreen : function() {
		
		this.$waitingScreen.hide();
		this.$finishingScreen.hide();
		
		this.$otherElements.show();
	}

});