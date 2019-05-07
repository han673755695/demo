package com.example.demo.handler;

public class Handle {

	private PlayGame playGame;
	
	public Handle(PlayGame playGame) {
		this.playGame = playGame;
	}
	
	public String doSomething() {
		
		return playGame.play();
	}
	
}
