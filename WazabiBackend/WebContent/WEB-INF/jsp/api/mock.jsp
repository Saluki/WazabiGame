<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
{
  "status": "playing",
  "player": {
    "name": "John Doe",
    "play": true
  },
  "hand": {
    "cards": [{
      "name": "Card A",
      "description": "Description of A",
      "image": "images/pic1.png",
      "effect": 2,
      "cost": 3,
      "input": true
    },{
      "name": "Card B",
      "description": "Description of B",
      "image": "images/pic2.png",
      "effect": 1,
      "cost": 2,
      "input": false
    }],
    "dices": ["A", "B", "C"]
  },
  "challengers": [{
      "name": "Jack Forge",
      "dices": ["D", "E"]
    },{
      "name": "Alexander Neil",
      "dices": ["F", "G"]
  }]
}