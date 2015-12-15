'use strict';

var app = app || {};

app.BoardView = Backbone.View.extend({

	el: '#board',
	
	events : {},
	
	initialize : function() {
		
		this.listenTo(this.model, 'change', this.render);
		
		this.$waitingScreen = $('#waiting-screen');
		this.$otherElements = this.$el.children().not('#waiting-screen');
		
		this.render();
	},

	render : function() {
		
		if( this.model.isWaiting() ) {
			this.showWaitScreen();
		}
		else {
			this.hideWaitScreen();
		}
		
		return this;
	},
	
	showWaitScreen : function() {
		
		this.$otherElements.hide();
		this.$waitingScreen.show();
	},
	
	hideWaitScreen : function() {
		
		this.$otherElements.show();
		this.$waitingScreen.hide();
	}

});