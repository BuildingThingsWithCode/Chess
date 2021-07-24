package chessApplication;

import javafx.scene.media.AudioClip;

public class Sounds {
	
	private final AudioClip MOVE 				  = new AudioClip(getClass().getResource("../resources/sounds/move.mp3").toString());
	private final AudioClip DRAW 				  = new AudioClip(getClass().getResource("../resources/sounds/draw.mp3").toString());
	private final AudioClip CHECKMATE 			  = new AudioClip(getClass().getResource("../resources/sounds/checkmate.mp3").toString());
	private final AudioClip STALEMATE 			  = new AudioClip(getClass().getResource("../resources/sounds/stalemate.mp3").toString());
	private final AudioClip OUT_OF_TIME 		  = new AudioClip(getClass().getResource("../resources/sounds/outoftime.mp3").toString());
	private final AudioClip INSUFFICIENT_MATERIAL = new AudioClip(getClass().getResource("../resources/sounds/insufficientmaterial.mp3").toString());	
	private final AudioClip RESIGN 				  = new AudioClip(getClass().getResource("../resources/sounds/resign.mp3").toString());
	
	//CONSTRUCTOR
	public Sounds() {}
	
	//METHODS
	public void move() {
		MOVE.play();
	}
	
	public void draw() {
		DRAW.play();
	}
	
	public void checkmate() {
		CHECKMATE.play();
	}

	public void stalemate() {
		STALEMATE.play();
	}
	
	public void outOfTime() {
		OUT_OF_TIME.play();
	}
	
	public void insufficientMaterial() {
		INSUFFICIENT_MATERIAL.play();
	}
	
	public void resign() {
		RESIGN.play();
	}
}
