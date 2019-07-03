package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.OnValidMovePlayedListener;
import chess_engine.structs.ValidMove;

public class Rook extends BoardElement {

	private int moveCount;

	public Rook(int x, int y, Chessboard chessboard, int type) {
		super(x, y, chessboard, type);
	}

	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		ArrayList<ValidMove> validMoves = new ArrayList<>();
		boolean[] flags = { true, true, true, true };
		for (int i = 0; i < 8; i++) {
			BoardElement square0 = chessboard.getSquares(row + i, col);
			BoardElement square1 = chessboard.getSquares(row, col + i);
			BoardElement square2 = chessboard.getSquares(row - i, col);
			BoardElement square3 = chessboard.getSquares(row, col - i);

			if (square0 == this || square1 == this || square2 == this || square3 == this) {
				continue;
			}

			if (square0.type == type)
				flags[0] = false;

			if (square1.type == type)
				flags[1] = false;

			if (square2.type == type)
				flags[2] = false;

			if (square3.type == type)
				flags[3] = false;

			if (flags[0]) {
				ValidMove move = new ValidMove(chessboard, row, col, square0.row, square0.col);
				validMoves.add(move);
			}

			if (flags[1]) {
				ValidMove move = new ValidMove(chessboard, row, col, square1.row, square1.col);
				validMoves.add(move);
			}

			if (flags[2]) {
				ValidMove move = new ValidMove(chessboard, row, col, square2.row, square2.col);
				validMoves.add(move);
			}

			if (flags[3]) {
				ValidMove move = new ValidMove(chessboard, row, col, square3.row, square3.col);
				validMoves.add(move);
			}

			int e = type == TYPE_BLACK ? TYPE_WHITE : TYPE_BLACK;

			if (square0.type == e)
				flags[0] = false;

			if (square1.type == e)
				flags[1] = false;

			if (square2.type == e)
				flags[2] = false;

			if (square3.type == e)
				flags[3] = false;

		}

		filterValidMoves(validMoves);

		for (ValidMove move : validMoves) {
			move.setMove(new OnValidMovePlayedListener() {
				@Override
				public void move(BoardElement element) {
					moveCount++;
				}
			});
		}

		return validMoves;
	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new Rook(row, col, chessboard, getType());
	}

	@Override
	public char getNickName() {
		return type == TYPE_WHITE ? 'R' : 'r';
	}

	public int getMoveCount() {
		return moveCount;
	}

	@Override
	public int getID() {
		return type == TYPE_WHITE ? ValidMove.ROOK : ValidMove.ROOK + 1;
	}
}
