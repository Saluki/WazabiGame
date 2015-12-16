
'use strict';

var app = app || {};

app.DiceListView = Backbone.View.extend({
	
	el: '#dices-list-view',
	
	giveDicesTemplate: _.template( $('#give-dices-template').html() ),
	
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
		
		if( !app.Status.instance().is(app.Status.C.ROLL_DICE) ) {
			alertify.warning('Vous ne pouvez pas relancer les des pour l\'instant');
			return;
		}
		
		app.Status.instance().set(app.Status.C.REQUESTING);
		
		$.ajax({
			'url': 'api/game/rolldices',
			'method': 'GET',
			'dataType': 'json',
			'context': this
		})
		.success(function(data){
						
			// Recreate dice collection
			var tempDicesList = [];
			_.each(data.dices, function(diceValue) {
				tempDicesList.push( new app.DiceModel({ value:diceValue }) );
			});
			this.collection.reset(tempDicesList);
					
			this.showGiveDicesModal(this);
			
		}).fail(function(){
			
			alertify.alert('Desole, impossible de relancer les des');
			app.Status.instance().set(app.Status.C.ROLL_DICE);
			
		});
		
	},
	
	showGiveDicesModal: function(view) {
		
		app.Status.instance().set(app.Status.C.GIVE_DICES);
		
		var numberDices = view.collection.where({
			'value' : 'DE'
		}).length;
		
		if( numberDices==0 ) {
			return;
		}
		
		var modalContent = view.giveDicesTemplate({
			'number' : numberDices,
			'challengers' : app.challengers
		});
		
		alertify.alert(modalContent, function(){
			view.giveDices(view);
		})
		.setting({
			'closable': false,
			'label': 'Donner aux adversaires'
		});
	},
	
	giveDices: function(view) {
		
		if( !app.Status.instance().is(app.Status.C.GIVE_DICES) ) {
			alertify.warning('Vous ne pouvez pas donner des aux adversaires pour l\'instant');
			return;
		}
		
		app.Status.instance().set(app.Status.C.REQUESTING);
		
		var challengersId = [];
		$('.select-give-dice').each(function(){
			challengersId.push( $(this).val() );
		});
		
		$.ajax({
			'url': 'api/game/givedices',
			'method': 'POST',
			'data': {
				challengers: challengersId,
			},
			'dataType': 'json'
		})
		.success(function(data){
			
			if( data.status==true ) {
				
				// TODO Remove dices in collection
				alertify.success('Bien joue! Vous pouvez maintenant choisir une carte ou terminer votre tour...');
				app.Status.instance().set(app.Status.C.CHOOSE_CARD);
			}
			else {
				
				alertify.error('Impossible d\'appliquer les changements');
				app.Status.instance().set(app.Status.C.GIVE_DICES);
			}
			
		})
		.fail(function(){
			
			alertify.error('Impossible d\'appliquer les changements');
			app.Status.instance().set(app.Status.C.GIVE_DICES);
			
		});
	}
	
});