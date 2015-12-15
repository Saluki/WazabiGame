'use strict';

var app = app || {};

app.ChallengersListView = Backbone.View.extend({

	el : '#challengers-list-view',
	
	template: _.template($('#challengers-list-view-template').html()),
	
	events : {},

	initialize : function() {		
		
		this.listenTo(this.collection, 'reset', this.render);
		this.listenTo(this.collection, 'update', this.render);
	},

	render : function() {
			
		this.$el.html(this.template({ 
			'challengers':this.collection.toJSON() 
		}));
		
		return this;
	}
	
});