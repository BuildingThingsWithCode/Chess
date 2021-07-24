package chessApplication;


import static chess.util.Converter.getAllWhiteViewPieces;
import static chess.util.Converter.getModelPiece;
import static chess.util.Converter.getPathToImage;
import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.DARKGOLDENROD;
import static javafx.scene.paint.Color.WHITE;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import chess.Field;
import chess.Move;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class DragAndDropHandler {

	private static final Label 	      TEMP_BG        = new Label();
	private static final Background       DARK_GOLD	     = new Background(new BackgroundFill(DARKGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY));
	private static Sounds 		      sounds 	     = new Sounds();
	private static ObjectProperty<Move>   move	     = new SimpleObjectProperty<Move>();
	private static ObservableBooleanValue moveLegal      = new SimpleBooleanProperty(false);
	private static ObservableList<String> capturedPieces = FXCollections.observableList(new ArrayList<String>());

	//CONSTRUCTOR
	private DragAndDropHandler() {}
	
	//METHODS
	/*
	 * Drag detected, start drag&drop gesture. Allow any transfer mode.
	 * Put piece on dragBoard.
	 * Set the dragView image.
	 */
	static Consumer<Label> setOnDragDetected = l -> {
		EventHandler<MouseEvent> onDragDetected = e -> {
			Dragboard db = l.startDragAndDrop(TransferMode.ANY);
			if (l.getText() != "") {
				ClipboardContent content = new ClipboardContent();
				content.putString(l.getText());
				int offset = l.getWidth() < 78.0 ? 14 : 22;
				db.setDragView(new Image(getPathToImage(l.getText(), l.getWidth())), offset, offset);
				l.setText("");
				db.setContent(content);
			}
			e.consume();
		};
		l.setOnDragDetected(onDragDetected);
	};
	
	/*
	 * returns true if the label we enter is empty or contains an enemy.
	 */
	private static BiPredicate<Label, DragEvent> emptyOrDifferentColors = (l, e) -> {
		if (l.getText() == "") return true;
		Color  attack  = getAllWhiteViewPieces().contains(e.getDragboard().getString()) ? WHITE : BLACK;
		Color  defense = getAllWhiteViewPieces().contains(l.getText()) ? WHITE : BLACK;
		return attack != defense;
	};

	/*
	 *  Data is dragged over the target, accept only if not dragged from the same node
	 *  and if it has string data and if the field is empty, or contains enemy. 
	 */
	static Consumer<Label> setOnDragOver = l -> {
		EventHandler<DragEvent> onDragOver = e -> {
			if (e.getGestureSource() != l 
					&& e.getDragboard().hasString() 
					&& emptyOrDifferentColors.test(l, e)) e.acceptTransferModes(TransferMode.MOVE);
			e.consume();
		};
		l.setOnDragOver(onDragOver);
	};

	/*
	 * Mouse moved away, remove the graphical cues 
	 */
	static Consumer<Label> setOnDragExited = l -> {
		EventHandler<DragEvent> onDragExited = e -> {
			if (emptyOrDifferentColors.test(l, e)) l.setBackground(TEMP_BG.getBackground());
			e.consume();
		};
		l.setOnDragExited(onDragExited);
	};

	/*
	 * The drag-and-drop gesture entered the target.
	 * Show to the user that it is an actual gesture target. 
	 */
	static Consumer<Label> setOnDragEntered = l -> {
		EventHandler<DragEvent> onDragEntered = e -> {
			if (e.getDragboard().hasString() && emptyOrDifferentColors.test(l, e)) { 
				TEMP_BG.setBackground(l.getBackground());
				if (e.getGestureSource() != l) l.setBackground(DARK_GOLD);
			}
			e.consume();
		};
		l.setOnDragEntered(onDragEntered);
	};

	/*
	 * Method that creates a move for the model, from the move made by the user
	 * in the view.
	 */
	private static BiFunction<Label, DragEvent, Move> getMoveFromUserInput = (l, e) -> {
		Field start = new Field(getRowIndex(((Label) e.getGestureSource())), getColumnIndex(((Label) e.getGestureSource())));
		Field end   = new Field(getRowIndex(l), getColumnIndex(l));
		start.setPiece(getModelPiece(e.getDragboard().getString()));
		return new Move(start, end);
	};
	
	/*
	 * If there is a string data on dragBoard, read it and use it. 
	 * Move will be played in the model and moveLegal will be set.
	 * Add captured piece to observableList so GUI can be updated.
	 */
	static Consumer<Label> setOnDragDropped = l -> {
		EventHandler<DragEvent> onDragDropped = e -> {
			((SimpleBooleanProperty) moveLegal).set(false);
			if (e.getDragboard().hasString()) {
				move.set(getMoveFromUserInput.apply(l, e));
				if (moveLegal.get() == true) {
					if (l.getText() != "") capturedPieces.add(l.getText());
					l.setBackground(TEMP_BG.getBackground());
					l.setText(e.getDragboard().getString());
					sounds.move();
				}
			}
			//let source know whether the string was successfully 
			//transferred and used 
			e.setDropCompleted(moveLegal.get());
			e.consume();
		};
		l.setOnDragDropped(onDragDropped);
	};
	
	/*
	 * The drag and drop gesture ended unsuccessful,
	 * place piece back on it's start field.
	 */
	static Consumer<Label> setOnDragDone = l -> {
		EventHandler<DragEvent> onDragDone = e -> {
			if (e.getTransferMode() != TransferMode.MOVE) {
				((Labeled) e.getGestureSource()).setText(e.getDragboard().getString());
			}
			e.consume();
		};
		l.setOnDragDone(onDragDone);
	};
	
	
	//GETTERS & SETTERS
	public static ObjectProperty<Move> getMove() {
		return move;
	}

	public static ObservableBooleanValue getMoveLegal() {
		return moveLegal;
	}

	public static void  setMoveLegal(boolean value) {
		((BooleanPropertyBase) moveLegal).set(value);
	}

	public static ObservableList<String> getCapturedPieces(){
		return capturedPieces;
	}


}

