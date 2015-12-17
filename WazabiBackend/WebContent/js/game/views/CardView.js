'use strict';

var app = app || {};

app.CardView = Backbone.View.extend({

	tagName: 'div',
	
	className: 'wazabi-card',
	
	template: _.template($('#card-view-template').html()),
	
	templatePromptPlayer: _.template($('#prompt-player-template').html()),
	
	templatePromptDirection: _.template($('#prompt-direction-template').html()),
	
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
		
		var view = this;
		// TODO Change status
		
		if( this.model.get('input')=='JOUEUR' ) {
			
			var modalContent = this.templatePromptPlayer({
				'challengers': app.challengers
			});
			
			alertify.alert(modalContent, function(){
				
				var formData = $('#select-card-player').val();
				view.sendCardToServer(view, formData);
			});
		}
		else if( this.model.get('input')=='SENS' ) {
			
			var modalContent = this.templatePromptDirection();
			
			alertify.alert(modalContent, function(){
				
				var formData = $('#select-card-direction').val();
				view.sendCardToServer(view, formData);
			});
		}
		else {
			this.sendCardToServer(this);
		}
	},
	
	sendCardToServer: function(view, inputData) {
		
		app.Status.instance().set(app.Status.C.REQUESTING);
		
		$.ajax({
			dataType: 'json',
			method: 'POST',
			url: 'api/game/playcard',
			context: view,
			data: {
				cardId: this.model.get('id'),
				inputType: this.model.get('input'),
				inputData: inputData
			}
		})
		.success(function(responseData){
			
			if( responseData.succeed ) {
	
				alertify.success(responseData.message + '<br>Vous pouvez terminer votre tour.');
				app.Status.instance().set(app.Status.C.ENDING);
			}
			else {
				
				alertify.alert('Erreur lors du traitement de la carte: ' + responseData.message);
				app.Status.instance().set(app.Status.C.CHOOSE_CARD);
			}
			
		})
		.fail(function(){
			
			alertify.alert('Erreur lors du traitement de la carte');
			app.Status.instance().set(app.Status.C.CHOOSE_CARD);
  
		});
	}

});