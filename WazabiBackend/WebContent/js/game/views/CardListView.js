'use strict';

var app = app || {};

app.CardListView = Backbone.View.extend({

	el : '#cards-list-view',
	
	events : {},

	initialize : function() {		
		this.listenTo(this.collection, 'update', this.render);
	},

	render : function() {
			
		this.cleanList();
		this.collection.each(function(cardModel){
			
			var cardView = new app.CardView({ model:cardModel });
			this.$el.append(cardView.el);
			
		}, this);
		
		return this;
	},
	
	cleanList : function() {
		
		this.$el.html('');
	}

});