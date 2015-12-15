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
		
		// TODO Put and remove interact lock
				
		if( this.model.get('input') ) {
			
			var that = this;
			alertify.prompt('La carte a besoin d\'une valeur', '', function(e, value){
				that.sendCardToServer(that);
			});
		}
		else {
			this.sendCardToServer(this);
		}
	},
	
	sendCardToServer: function(view) {
		
		$.ajax({
			dataType: 'json',
			url: 'api/game/playcard/' + this.model.get('effect'),
			context: view
		})
		.success(function(data){
			
			alertify.success('Traitement termine');
			
			this.model.destroy();
			this.remove()
			
			// TODO Change lock
			
		})
		.fail(function(){
			
			  alertify.alert('Erreur lors du traitement de la carte');
			
		});
	}

});