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
				
		if( !app.Status.instance().is(app.Status.C.CHOOSE_CARD) ) {
			alertify.warning('Vous ne pouvez pas choisir de carte pour l\'instant');
			return;
		}
		
		if( this.model.get('input') ) {
			
			var that = this;
			alertify.prompt('La carte a besoin d\'une valeur:', '', function(e, value){
				that.sendCardToServer(that);
			});
		}
		else {
			this.sendCardToServer(this);
		}
	},
	
	sendCardToServer: function(view) {
		
		app.Status.instance().set(app.Status.C.REQUESTING);
		
		$.ajax({
			dataType: 'json',
			url: 'api/game/playcard/' + this.model.get('effect'),
			context: view
		})
		.success(function(data){
			
			this.model.destroy();
			this.remove();
			
			alertify.success('Traitement termine');
			app.Status.instance().set(app.Status.C.ENDING);
			
		})
		.fail(function(){
			
			alertify.alert('Erreur lors du traitement de la carte');
			app.Status.instance().set(app.Status.C.CHOOSE_CARD);
  
		});
	}

});