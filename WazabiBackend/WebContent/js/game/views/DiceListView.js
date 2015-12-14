
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
		alert('Rolling dices...');
	}
	
});