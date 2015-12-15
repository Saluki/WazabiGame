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
	
	// TODO Check permissions, must be CHOOSE_CARD
	chooseCard : function(e) {
		
		// Put and remove interact lock
		var apiUrl = 'api/game/playcard/' + this.model.get('effect');
		$.getJSON(apiUrl, function(data){
			
			this.model.destroy();
			this.remove();
			
			// Change to other status
			
		}, this).fail(function(){
			console('Could not play card');
		});
	}

});