package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.ValidMove;

public abstract class BoardElement {

	public static final int TYPE_BLACK = 0, TYPE_WHITE = 1, TYPE_EMPTY = 2;

	protected int row, col;
	protected Chessboard chessboard;
	protected int type;

	public BoardElement(int row, int col, Chessboard chessboard, int type) {
		this.chessboard = chessboard;
		this.type = type;
		this.row = row;
		this.col = col;
	}

	protected abstract ArrayList<ValidMove> getPotentiallyValidMoves();

	// there's only two modes, a piece can move to any potential valid move
	// or a piece's valid moves must be restricted if in check
	public ArrayList<ValidMove> getValidMoves() {
		int enemy = type == TYPE_BLACK ? TYPE_WHITE : TYPE_BLACK;
		ArrayList<ValidMove> enemyMoves = chessboard.getAllPotentialMovesFor(enemy);
		ArrayList<ValidMove> validMoves = getPotentiallyValidMoves();

		// checks for pins
		for (int i = 0; i < validMoves.size(); i++) {
			// generates a hypothetical scenario to resolve properly
			if (!chessboard.wouldThisMoveEnterCheck(validMoves.get(i))) {
				validMoves.remove(i);
				i--;
			}
		}

		if (!chessboard.containsCheck(enemyMoves)) {
			return validMoves;
		}
		if (this instanceof King)
			((King) this).isInCheck = this instanceof King;

		ArrayList<ValidMove> teamMoves = chessboard.getSquares(row, col).getPotentiallyValidMoves();
		validMoves.clear();
		for (ValidMove move : teamMoves) {
			if (chessboard.wouldThisMoveEnterCheck(move)) {
				// forcing move found for 'this' piece, this is only move that
				// prevents a check.
				validMoves.add(move);
			}
		}

		return validMoves;
	}

	public abstract BoardElement deepCopy(Chessboard board);

	public abstract int getID();

	public abstract char getNickName();

	protected void filterValidMoves(ArrayList<ValidMove> moves) {

		// remove a move that would lead to no board change
		for (int i = 0; i < moves.size(); i++) {
			ValidMove move = moves.get(i);
			if ((move.getNext_position_row() == row && move.getNext_position_col() == col) || move.isOutsideOfBounds()) {
				moves.remove(i);
				i--;
			}
		}

	}

	public int getType() {
		return type;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void set(int row, int col) {
		this.row = row;
		this.col = col;
	}

}
