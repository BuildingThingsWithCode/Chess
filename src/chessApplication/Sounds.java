package chessApplication;

import javafx.scene.media.AudioClip;

public class Sounds {

   private final AudioClip move                  = new AudioClip(getClass().getResource("../resources/sounds/move.mp3").toString());
   private final AudioClip draw                  = new AudioClip(getClass().getResource("../resources/sounds/draw.mp3").toString());
   private final AudioClip checkmate             = new AudioClip(getClass().getResource("../resources/sounds/checkmate.mp3").toString());
   private final AudioClip stalemate             = new AudioClip(getClass().getResource("../resources/sounds/stalemate.mp3").toString());
   private final AudioClip outOfTime             = new AudioClip(getClass().getResource("../resources/sounds/outoftime.mp3").toString());
   private final AudioClip insufficientMaterial  = new AudioClip(getClass().getResource("../resources/sounds/insufficientmaterial.mp3").toString());	
   private final AudioClip resign                = new AudioClip(getClass().getResource("../resources/sounds/resign.mp3").toString());

   //CONSTRUCTOR
   public Sounds() {}
   
   //METHODS
   private void play(AudioClip clip) {
      new Thread(new Runnable() {
         public void run() {
            clip.play();
         }
      }).start();
   }
   
   public void move() {
      play(move);
   }

   public void draw() {
      play(draw);
   }

   public void checkmate() {
      play(checkmate);
   }

   public void stalemate() {
      play(stalemate);
   }

   public void outOfTime() {
      play(outOfTime);
   }

   public void insufficientMaterial() {
      play(insufficientMaterial);
   }

   public void resign() {
     play(resign);
   }
}
