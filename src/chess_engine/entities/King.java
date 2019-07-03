package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.OnValidMovePlayedListener;
import chess_engine.structs.ValidMove;

public class King extends BoardElement {

	// must be package private to grant visibility up the hierarchy.
	boolean isInCheck;
	private int moveCount;

	public King(int x, int y, Chessboard chessboard, int type) {
		super(x, y, chessboard, type);
	}

	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		ArrayList<ValidMove> validMoves = new ArrayList<>();

		BoardElement m[] = new BoardElement[8];
		m[0] = chessboard.getSquares(row, col + 1);
		m[1] = chessboard.getSquares(row, col - 1);
		m[2] = chessboard.getSquares(row + 1, col);
		m[3] = chessboard.getSquares(row - 1, col);
		m[4] = chessboard.getSquares(row + 1, col + 1);
		m[5] = chessboard.getSquares(row - 1, col - 1);
		m[6] = chessboard.getSquares(row + 1, col - 1);
		m[7] = chessboard.getSquares(row - 1, col + 1);

		for (int i = 0; i < 8; i++) {
			if (m[i].type != type) {
				ValidMove move = new ValidMove(chessboard, row, col, m[i].row, m[i].col);
				validMoves.add(move);

			}

		}

		// handle castling
		int backrank = type == TYPE_WHITE ? 7 : 0;
		if (moveCount == 0 && row == backrank && col == 4) {
			// check for queenside castling
			BoardElement[] queenside = { chessboard.getSquares(backrank, 0), chessboard.getSquares(backrank, 1), chessboard.getSquares(backrank, 2), chessboard.getSquares(backrank, 3) };
			if (queenside[0] instanceof Rook)
				if (((Rook) queenside[0]).getMoveCount() == 0 && queenside[1].type == TYPE_EMPTY && queenside[2].type == TYPE_EMPTY && queenside[3].type == TYPE_EMPTY) {
					// queenside[2] and queenside[3] musn't be attacked, and it
					// must move the rook
					// this is handled when processing a move() call in
					// chessboard class
					validMoves.add(new ValidMove(chessboard, row, col, row, col - 2, true, false, false));
				}

			// see if path of castling is in check
			BoardElement[] kingside = { chessboard.getSquares(backrank, 5), chessboard.getSquares(backrank, 6), chessboard.getSquares(backrank, 7) };
			if (kingside[2] instanceof Rook)
				if (((Rook) kingside[2]).getMoveCount() == 0 && kingside[0].type == TYPE_EMPTY && kingside[1].type == TYPE_EMPTY) {
					validMoves.add(new ValidMove(chessboard, row, col, row, col + 2, false, true, false));
				}
		}

		filterValidMoves(validMoves);

		for (ValidMove move : validMoves) {
			move.setMove(new OnValidMovePlayedListener() {
				@Override
				public void move(BoardElement element) {
					// if we chose a move before castling, then we forego
					// castling rights. Alas moveCount != 0
					moveCount++;
				}
			});
		}
		return validMoves;
	}

	@Override
	public ArrayList<ValidMove> getValidMoves() {
		ArrayList<ValidMove> moves = super.getValidMoves();
		int other = type == BoardElement.TYPE_BLACK ? BoardElement.TYPE_WHITE : BoardElement.TYPE_BLACK;
		int backrank = type == BoardElement.TYPE_WHITE ? 7 : 0;
		for (int i = 0; i < moves.size(); i++) {
			ValidMove move = moves.get(i);
			if (move.isCastlingQueenSide()) {
				int cfile = chessboard.pressureAt(backrank, 2, other);
				int dfile = chessboard.pressureAt(backrank, 3, other);
				int efile = chessboard.pressureAt(backrank, 4, other);

				// ensure player isn't castling through check
				if (cfile + dfile + efile != 0) {
					moves.remove(i);
					i--;
				}
			}

			if (move.isCastlingKingSide()) {
				int efile = chessboard.pressureAt(backrank, 4, other);
				int ffile = chessboard.pressureAt(backrank, 5, other);
				int gfile = chessboard.pressureAt(backrank, 6, other);

				// ensure player isn't castling through check
				if (efile + ffile + gfile != 0) {
					moves.remove(i);
					i--;
				}
			}
		}

		return moves;

	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new King(row, col, chessboard, getType());
	}

	@Override
	public char getNickName() {
		return type == TYPE_WHITE ? 'K' : 'k';
	}

	@Override
	public int getID() {
		return type == TYPE_WHITE ? ValidMove.KING : ValidMove.KING + 1;
	}
}
