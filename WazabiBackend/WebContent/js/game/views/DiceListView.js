
'use strict';

var app = app || {};

app.DiceListView = Backbone.View.extend({
	
	el: '#dices-list-view',
	
	events: {
		'click' : 'rollDices'
	},
	
	initialize: function() {
		// TODO this.listenTo(app.)
	},
	
	render: function() {
		
		this.cleanList();
		this.$el.html('DICES');
		
		return this;
	},
	
	cleanList : function() {
		
		this.$el.html('');
	},

	rollDices: function() {
		console.log('Rolling dices')
	}
	
});