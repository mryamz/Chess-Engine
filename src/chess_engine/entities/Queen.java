package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.ValidMove;

public class Queen extends BoardElement {

	public Queen(int x, int y, Chessboard chessboard, int type) {
		super(x, y, chessboard, type);
	}

	// ideally this would inherit from bishop and rook, consider if redesigned
	// again
	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		ArrayList<ValidMove> validMoves = new ArrayList<>();
		boolean[] flags = { true, true, true, true, true, true, true, true };
		for (int i = 0; i < 8; i++) {
			BoardElement[] squares = { chessboard.getSquares(row + i, col), chessboard.getSquares(row, col + i), chessboard.getSquares(row - i, col), chessboard.getSquares(row, col - i), chessboard.getSquares(row + i, col + i), chessboard.getSquares(row + i, col - i), chessboard.getSquares(row - i, col + i), chessboard.getSquares(row - i, col - i) };

			int e = type == TYPE_BLACK ? TYPE_WHITE : TYPE_BLACK;
			for (int j = 0; j < squares.length; j++) {
				BoardElement square = squares[j];
				if (square == this)
					break;

				if (square.type == type)
					flags[j] = false;

				if (flags[j]) {
					ValidMove move = new ValidMove(chessboard, row, col, square.row, square.col);
					validMoves.add(move);
				}

				if (square.type == e) {
					flags[j] = false;
				}
			}
		}
		filterValidMoves(validMoves);
		return validMoves;
	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new Queen(row, col, chessboard, getType());
	}

	@Override
	public char getNickName() {
		return type == TYPE_WHITE ? 'Q' : 'q';
	}
	
	@Override
	public int getID() {
		return type == TYPE_WHITE ? ValidMove.QUEEN : ValidMove.QUEEN + 1;
	}
}
