
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

	rollDices: function() {
		
		if( !app.Status.instance().is(app.Status.C.ROLL_DICE) ) {
			alertify.warning('Vous ne pouvez pas relancer les des pour l\'instant');
			return;
		}
		
		app.Status.instance().set(app.Status.C.REQUESTING);
		
		$.getJSON('api/game/rolldices', function(data){
						
			var tempDicesList = [];
			_.each(data.dices, function(diceValue) {
				tempDicesList.push( new app.DiceModel({ value:diceValue }) );
			});
			
			app.playerDices.reset(tempDicesList);
			alertify.success('Les des ont ete relances. Choissisez maintenant une carte.');
			
			app.Status.instance().set(app.Status.C.CHOOSE_CARD);
			
		}).fail(function(){
			
			alertify.alert('Desole, impossible de relancer les des');
			app.Status.instance().set(app.Status.C.ROLL_DICE);
			
		});
		
	}
	
});