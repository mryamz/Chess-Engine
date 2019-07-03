package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import chess_engine.entities.Bishop;
import chess_engine.entities.BoardElement;
import chess_engine.entities.King;
import chess_engine.entities.Knight;
import chess_engine.entities.Pawn;
import chess_engine.entities.Queen;
import chess_engine.entities.Rook;
import chess_engine.structs.ValidMove;

public class Tile {

	private Rectangle rectangle;
	private BoardElement element;
	public boolean isFlipped;

	private boolean isLight, isActivated;
	private ValidMove move;

	private Color dark = new Color(55, 55, 55);
	private Color light = new Color(155, 155, 155);
	private Color activated = new Color(155, 55, 55);
	private Color valid_move = new Color(155, 155, 55);

	public Tile(float w, float h, BoardElement element, boolean isLight) {
		rectangle = new Rectangle((int) w, (int) h);

		this.isLight = isLight;
		this.element = element;
	}

	public void draw(Graphics2D g) {

		g.setColor(move != null ? valid_move : isActivated ? activated : isLight ? light : dark);

		rectangle.x = element.getCol() * rectangle.height;
		rectangle.y = element.getRow() * rectangle.width;
		if(isFlipped) {
			rectangle.y = (7 * rectangle.height) - rectangle.y;
		}

		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

		if (element instanceof Pawn)
			g.drawImage(element.getType() == BoardElement.TYPE_BLACK ? Resources.getRes().blackPawn : Resources.getRes().whitePawn, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);

		if (element instanceof Rook)
			g.drawImage(element.getType() == BoardElement.TYPE_BLACK ? Resources.getRes().blackRook : Resources.getRes().whiteRook, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);

		if (element instanceof Knight)
			g.drawImage(element.getType() == BoardElement.TYPE_BLACK ? Resources.getRes().blackKnight : Resources.getRes().whiteKnight, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);

		if (element instanceof Bishop)
			g.drawImage(element.getType() == BoardElement.TYPE_BLACK ? Resources.getRes().blackBishop : Resources.getRes().whiteBishop, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);

		if (element instanceof Queen)
			g.drawImage(element.getType() == BoardElement.TYPE_BLACK ? Resources.getRes().blackQueen : Resources.getRes().whiteQueen, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);

		if (element instanceof King)
			g.drawImage(element.getType() == BoardElement.TYPE_BLACK ? Resources.getRes().blackKing : Resources.getRes().whiteKing, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public void setMove(ValidMove move) {
		this.move = move;
	}

	public Rectangle getBounds() {
		return rectangle;
	}

	public BoardElement getElement() {
		return element;
	}

	public ValidMove getMove() {
		return move;
	}
	
	public void setElement(BoardElement element) {
		this.element = element;
	}
}
