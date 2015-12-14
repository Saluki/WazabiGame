'use strict';

var app = app || {};

app.PlayerView = Backbone.View.extend({

	el : '#player-view',

	template: _.template($('#player-view-template').html()),
	
	events : {},

	initialize : function() {
		this.listenTo(this.model, 'change', this.render);
	},

	render : function() {
		
		this.$el.html(this.template(this.model.toJSON()));
		return this;
	}

});