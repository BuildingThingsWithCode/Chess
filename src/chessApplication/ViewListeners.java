package chessApplication;

import static chessApplication.DragAndDropHandler.getCapturedPieces;
import static chessApplication.DragAndDropHandler.getMove;
import static chessApplication.DragAndDropHandler.getMoveLegal;

import java.util.Collections;

import chess.Move;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Labeled;

public class ViewListeners {

	private Controller		   controller;
	private BoardHandler		   boardHandler;
	private Move			   modelMove;
	private ListChangeListener<String> capturedPieces;
	private ChangeListener<Move> 	   move;
	private ChangeListener<Boolean>    moveLegal;
	
	//CONSTRUCTOR
	public ViewListeners(Controller controller) {
		this.controller   = controller;
		this.boardHandler = controller.getBoardHandler();
	}
	
	
	//METHODS
	public void set() {
		/*
		 * set changeListener on ObservableList captured pieces.
		 */
		getCapturedPieces().addListener(capturedPieces = change -> {
			change.next();
			if (change.wasAdded()) {
				controller.captured.setVisible(true);
				Collections.sort(change.getList());
				for (int i=0; i<=change.getList().size()-1; i++) {
					((Labeled) controller.captured.getChildren().get(i)).setText(change.getList().get(i));
				}
			}
		});

		/*
		 * set a listener on the move from the user. Translates the move (to account for
		 * playing with white or black at the bottom of the board). Transforms the move
		 * into a move in the model and lets the model play this move.
		 */
		getMove().addListener(move = (ObservableValue, oldValue, newValue) -> {
			Move translatedMove = boardHandler.translateMove.apply(newValue);
			modelMove = controller.getGame().viewToModel(translatedMove);
			controller.getGame().play(modelMove);
		});

		/*
		 * we want to switch players when dragAndDrop was success and validated by the model.
		 * when promotion, the player's timer has to continue until he chooses a piece for promotion.
		 */
		getMoveLegal().addListener(moveLegal = (ObservableValue, oldValue, newValue) -> {
			if (newValue == true && !controller.getGame().isPromotion.test(modelMove)) boardHandler.switchPlayer();
		});
	}

	public void remove() {
		getCapturedPieces().removeListener(capturedPieces);
		getMove().removeListener(move);
		getMoveLegal().removeListener(moveLegal);
	}
}
