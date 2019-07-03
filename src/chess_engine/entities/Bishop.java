package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.ValidMove;

public class Bishop extends BoardElement {

	public Bishop(int x, int y, Chessboard chessboard, int type) {
		super(x, y, chessboard, type);
	}

	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		ArrayList<ValidMove> validMoves = new ArrayList<>();
		attack(validMoves);
		filterValidMoves(validMoves);
		return validMoves;
	}

	private void attack(ArrayList<ValidMove> validMoves) {

		// (i, i), (i, -i), (-i, i), (-i, -i)
		// honestly, ahaha, should ve used an array, typed everything 4 times :(
		boolean canD1 = true, canD2 = true, canD3 = true, canD4 = true;
		for (int i = 0; i < 8; i++) {
			BoardElement d1 = chessboard.getSquares(row + i, col + i);
			BoardElement d2 = chessboard.getSquares(row + i, col - i);
			BoardElement d3 = chessboard.getSquares(row - i, col + i);
			BoardElement d4 = chessboard.getSquares(row - i, col - i);

			if (d1 == this || d2 == this || d3 == this || d4 == this)
				continue;

			int enemy = type == TYPE_BLACK ? TYPE_WHITE : TYPE_BLACK;

			if (d1.getType() == type) {
				canD1 = false;
			}

			if (d2.getType() == type) {
				canD2 = false;
			}

			if (d3.getType() == type) {
				canD3 = false;
			}

			if (d4.getType() == type) {
				canD4 = false;
			}

			if (canD1)
				validMoves.add(new ValidMove(chessboard, row, col, d1.row, d1.col));
			if (canD2)
				validMoves.add(new ValidMove(chessboard, row, col, d2.row, d2.col));
			if (canD3)
				validMoves.add(new ValidMove(chessboard, row, col, d3.row, d3.col));
			if (canD4)
				validMoves.add(new ValidMove(chessboard, row, col, d4.row, d4.col));

			if (d1.getType() == enemy) {
				canD1 = false;
			}

			if (d2.getType() == enemy) {
				canD2 = false;
			}

			if (d3.getType() == enemy) {
				canD3 = false;
			}

			if (d4.getType() == enemy) {
				canD4 = false;
			}
		}
	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new Bishop(row, col, chessboard, getType());
	}

	@Override
	public char getNickName() {
		return type == TYPE_WHITE ? 'B' : 'b';
	}

	@Override
	public int getID() {
		return type == TYPE_WHITE ? ValidMove.BISHOP : ValidMove.BISHOP + 1;
	}

}
