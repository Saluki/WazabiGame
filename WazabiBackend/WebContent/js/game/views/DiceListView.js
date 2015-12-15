
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
		
		// TODO Change status to INTERACT = true;
		
		$.getJSON('api/game/rolldices', function(data){
			
			// TODO Change game local status to CARD_PICKING and INTERACT = false;
			
			var tempDicesList = [];
			_.each(data.dices, function(diceValue) {
				tempDicesList.push( new app.DiceModel({ value:diceValue }) );
			});
			
			app.playerDices.reset(tempDicesList);
			
		}).fail(function(){
			
			// TODO Rechange back to INTERACT = false;
			console.error('Unable to roll dices');
			
		});
		
	}
	
});