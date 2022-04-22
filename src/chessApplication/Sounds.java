package chessApplication;

import javafx.scene.media.AudioClip;

public final class Sounds {

   private static final AudioClip MOVE                  = new AudioClip(Sounds.class.getResource("../resources/sounds/move.mp3").toString());
   private static final AudioClip DRAW                  = new AudioClip(Sounds.class.getResource("../resources/sounds/draw.mp3").toString());
   private static final AudioClip CHECKMATE             = new AudioClip(Sounds.class.getResource("../resources/sounds/checkmate.mp3").toString());
   private static final AudioClip STALEMATE             = new AudioClip(Sounds.class.getResource("../resources/sounds/stalemate.mp3").toString());
   private static final AudioClip OUT_OF_TIME           = new AudioClip(Sounds.class.getResource("../resources/sounds/outoftime.mp3").toString());
   private static final AudioClip INSUFFICIENT_MATERIAL = new AudioClip(Sounds.class.getResource("../resources/sounds/insufficientmaterial.mp3").toString());	
   private static final AudioClip RESIGN                = new AudioClip(Sounds.class.getResource("../resources/sounds/resign.mp3").toString());

   //CONSTRUCTOR
   private Sounds() {}

   //METHODS
   private static void play(AudioClip clip) {
      new Thread(() ->  clip.play()).start();
   }

   public static void move() {
      play(MOVE);
   }

   public static void draw() {
      play(DRAW);
   }

   public static void checkmate() {
      play(CHECKMATE);
   }

   public static void stalemate() {
      play(STALEMATE);
   }

   public static void outOfTime() {
      play(OUT_OF_TIME);
   }

   public static void insufficientMaterial() {
      play(INSUFFICIENT_MATERIAL);
   }

   public static void resign() {
      play(RESIGN);
   }
}
