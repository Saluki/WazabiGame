
'use strict';

var app = app || {};

app.DiceListView = Backbone.View.extend({
	
	el: '#dices-list-view',
	
	events: {
		'click' : 'rollDices'
	},
	
	initialize: function() {
		
		this.listenTo(this.collection, 'reset', this.render);
		this.listenTo(this.collection, 'update', this.render);
	},
	
	render: function() {
		
		this.cleanList();
		this.collection.each(function(diceModel){
			
			var diceView = new app.DiceView({ model:diceModel });
			this.$el.append(diceView.el);
			
		}, this);
		
		return this;
	},
	
	cleanList : function() {
		
		this.$el.html('');
	},

	// TODO Check permissions, must be ROLL_DICE
	rollDices: function() {
		
		// TODO Change status to ROLLING
		
		$.getJSON('api/game/rolldices', function(){
			
			// TODO Change game local status to CARD_PICKING
			console.log('Dices rolled');
			
		}).fail(function(){
			
			// TODO Rechange back to ROLL_DICE
			console.error('Unable to roll dices');
			
		});
		
	}
	
});