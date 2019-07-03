package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.ValidMove;

public class Knight extends BoardElement {

	public Knight(int x, int y, Chessboard chessboard, int type) {
		super(x, y, chessboard, type);
	}

	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		ArrayList<ValidMove> validMoves = new ArrayList<>();

		BoardElement m1 = chessboard.getSquares(row - 2, col + 1);
		BoardElement m2 = chessboard.getSquares(row - 2, col - 1);
		BoardElement m3 = chessboard.getSquares(row + 2, col + 1);
		BoardElement m4 = chessboard.getSquares(row + 2, col - 1);

		BoardElement n1 = chessboard.getSquares(row - 1, col + 2);
		BoardElement n2 = chessboard.getSquares(row - 1, col - 2);
		BoardElement n3 = chessboard.getSquares(row + 1, col + 2);
		BoardElement n4 = chessboard.getSquares(row + 1, col - 2);

		if (m1.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, m1.row, m1.col));
		if (m2.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, m2.row, m2.col));
		if (m3.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, m3.row, m3.col));
		if (m4.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, m4.row, m4.col));
		if (n1.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, n1.row, n1.col));
		if (n2.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, n2.row, n2.col));
		if (n3.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, n3.row, n3.col));
		if (n4.getType() != type)
			validMoves.add(new ValidMove(chessboard, row, col, n4.row, n4.col));

		filterValidMoves(validMoves);
		return validMoves;
	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new Knight(row, col, chessboard, getType());
	}

	@Override
	public char getNickName() {
		return type == TYPE_WHITE ? 'H' : 'h';
	}
	
	@Override
	public int getID() {
		return type == TYPE_WHITE ? ValidMove.KNIGHT : ValidMove.KNIGHT + 1;
	}
}
