package chessApplication;

import static chess.util.MoveValidator.ValidationResult.LEGAL_MOVE;
import static chessApplication.DragAndDropHandler.setMoveLegal;

import chess.Game;
import chess.Move;
import chess.util.Action;
import chess.util.GameEvaluator.EvaluationResult;
import chess.util.MoveValidator.ValidationResult;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;

public class ModelListeners {

   private Game                                  game;
   private BoardHandler                          boardHandler;
   private Controller                            controller;
   private MapChangeListener<Enum<Action>, Move> extraAction;
   private ListChangeListener<EvaluationResult>  evaluation;
   private ListChangeListener<ValidationResult>  validation;

   //CONSTRUCTOR
   public ModelListeners(Controller controller) {
      this.controller   = controller;
      this.boardHandler = controller.getBoardHandler();
      this.game         = controller.getGame();
      controller.setModelListeners(this);
   }


   //METHODS
   public void set() {
      //add listener for the extra actions that are required with castling, en passant or promotion.
      game.getAction().addListener(extraAction = change -> {
         if (change.wasAdded()) {
            boardHandler.executeAction(change.getKey(), change.getValueAdded());
         };
      });
      //add listener for evaluation of the game.
      game.getEvaluationResult().addListener(evaluation = change -> {
         change.next();
         if (change.wasAdded()) {
            controller.handleEvaluationResult.accept(change.getAddedSubList().get(0));
         }
      });
      //add listener for validation of the move.
      game.getValidationResult().addListener(validation = change -> {
         change.next();
         if (change.wasAdded()) {
            if (change.getAddedSubList().get(0) == LEGAL_MOVE) {
               setMoveLegal(true);
               controller.validationMessages.setText("");
            }
            else controller.validationMessages.setText(change.getAddedSubList().get(0).toString());
         }
      });
   }

   public void remove() {
      game.getAction().removeListener(extraAction);
      game.getEvaluationResult().removeListener(evaluation);
      game.getValidationResult().removeListener(validation);
   }
}
