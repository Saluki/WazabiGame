'use strict';

var app = app || {};

app.CardView = Backbone.View.extend({

	tagName: 'div',
	
	className: 'wazabi-card',
	
	template: _.template($('#card-view-template').html()),
	
	events : {
		'click': 'chooseCard'
	},

	initialize : function() {
		this.render();
	},

	render : function() {
		
		this.$el.html(this.template(this.model.toJSON()));
		return this;
	},
	
	chooseCard : function(e) {		
		console.log('Choosed card ' + this.model.get('name'));
	}

});