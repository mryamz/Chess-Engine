package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.ValidMove;

public class Empty extends BoardElement {

	public Empty(int x, int y, Chessboard chessboard, int type) {
		super(x, y, chessboard, type);
	}

	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		return new ArrayList<>();
	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new Empty(row, col, chessboard, getType());
	}

	@Override
	public ArrayList<ValidMove> getValidMoves() {
		return new ArrayList<>();
	}

	@Override
	public char getNickName() {
		return '0';
	}

	@Override
	public int getID() {
		return ValidMove.EMPTY;
	}
}
