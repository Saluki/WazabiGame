'use strict';

var app = app || {};

app.DiceView = Backbone.View.extend({

	tagName: 'div',
	
	className: 'wazabi-dice',
	
	template: _.template($('#dice-view-template').html()),
	
	events : {},

	initialize : function() {
		
		this.render();
	},

	render : function() {
		
		this.$el.html(this.template({
			iconTag: this.model.getIconTag()	
		}));
		return this;
	}

});