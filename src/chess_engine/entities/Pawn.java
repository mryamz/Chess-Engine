package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.OnValidMovePlayedListener;
import chess_engine.structs.ValidMove;

public class Pawn extends BoardElement {

	// used for employing en passant
	private boolean turbostarted;

	// used for employing the draws at 50 and 75
	private int lastMovedTimer;

	public Pawn(int x, int y, Chessboard chessboard, int type, boolean turbostarted, int lastMovedTimer) {
		super(x, y, chessboard, type);
		this.turbostarted = turbostarted;
		this.lastMovedTimer = lastMovedTimer;
	}

	@Override
	public ArrayList<ValidMove> getPotentiallyValidMoves() {
		ArrayList<ValidMove> validMoves = new ArrayList<>();

		advance1square(validMoves);
		advance2squares(validMoves);
		enPassant(validMoves);
		attack(validMoves);
		pawnPromotion(validMoves);

		filterValidMoves(validMoves);

		return validMoves;
	}

	private void pawnPromotion(ArrayList<ValidMove> validMoves) {
		int lastRow = type == TYPE_BLACK ? 7 : 0;

		for (ValidMove move : validMoves) {
			if (move.getNext_position_row() == lastRow) {
				move.setPawnPromotion(true);
			}
		}
	}

	private void attack(ArrayList<ValidMove> validMoves) {

		int offset = type == TYPE_BLACK ? 1 : -1;
		BoardElement left = chessboard.getSquares(row + offset, col + offset);
		BoardElement right = chessboard.getSquares(row + offset, col - offset);

		if (left.getType() != type && left.getType() != TYPE_EMPTY) {
			ValidMove move = new ValidMove(chessboard, row, col, left.getRow(), left.getCol());
			validMoves.add(move);

			move.setMove(new OnValidMovePlayedListener() {
				@Override
				public void move(BoardElement element) {
					((Pawn) element).lastMovedTimer = 0;
				}
			});
		}
		if (right.getType() != type && right.getType() != TYPE_EMPTY) {
			ValidMove move = new ValidMove(chessboard, row, col, right.getRow(), right.getCol());
			validMoves.add(move);
			move.setMove(new OnValidMovePlayedListener() {
				@Override
				public void move(BoardElement element) {
					((Pawn) element).lastMovedTimer = 0;
				}
			});
		}
	}

	private void enPassant(ArrayList<ValidMove> validMoves) {
		int enpassantRow = type == TYPE_BLACK ? 4 : 3;

		if (row == enpassantRow) {
			// check adjacent squares
			BoardElement left = chessboard.getSquares(row, col - 1);
			BoardElement right = chessboard.getSquares(row, col + 1);
			if (left instanceof Pawn) {
				Pawn left_pawn = (Pawn) left;

				// he just moved two units
				if (left_pawn.getLastMovedTimer() == 0 && left_pawn.turbostarted) {
					// we have the right to en passant left
					ValidMove move = new ValidMove(chessboard, row, col, row + (type == TYPE_BLACK && row == 4 ? 1 : -1), col - 1, false, false, true);
					move.setMove(new OnValidMovePlayedListener() {
						@Override
						public void move(BoardElement element) {
							((Pawn) element).lastMovedTimer = 0;
						}
					});
					validMoves.add(move);
				}
			}

			if (right instanceof Pawn) {
				Pawn right_pawn = (Pawn) right;
				// he just moved two units
				if (right_pawn.getLastMovedTimer() == 0 && right_pawn.turbostarted) {
					// we have the right to en passant right
					ValidMove move = new ValidMove(chessboard, row, col, row + (type == TYPE_BLACK && row == 4 ? 1 : -1), col + 1, false, false, true);
					move.setMove(new OnValidMovePlayedListener() {
						@Override
						public void move(BoardElement element) {
							((Pawn) element).lastMovedTimer = 0; // aka
																	// this.lastmovedtimer
																	// = 0
						}
					});
					validMoves.add(move);
				}
			}
		}
	}

	private void advance2squares(ArrayList<ValidMove> validMoves) {
		int startRow = type == TYPE_BLACK ? 1 : 6;
		if (row == startRow) {
			int offset = type == TYPE_BLACK ? 1 : -1;
			BoardElement up1 = chessboard.getSquares(row + 1 * offset, col);
			BoardElement up2 = chessboard.getSquares(row + 2 * offset, col);
			if (up1 instanceof Empty && up2 instanceof Empty) {
				ValidMove move = new ValidMove(chessboard, row, col, up2.row, up2.col);
				validMoves.add(move);
				move.setMove(new OnValidMovePlayedListener() {
					@Override
					public void move(BoardElement element) {
						((Pawn) element).turbostarted = true;
						((Pawn) element).lastMovedTimer = 0;
					}
				});
			}
		}
	}

	private void advance1square(ArrayList<ValidMove> validMoves) {
		int offset = type == TYPE_BLACK ? 1 : -1;
		BoardElement up1 = chessboard.getSquares(row + offset, col);
		if (up1 instanceof Empty) {
			ValidMove v = new ValidMove(chessboard, row, col, up1.row, up1.col);
			validMoves.add(v);
			v.setMove(new OnValidMovePlayedListener() {

				@Override
				public void move(BoardElement element) {
					((Pawn) element).lastMovedTimer = 0;

				}
			});
		}
	}

	public void tick() {
		lastMovedTimer++;
	}

	public int getLastMovedTimer() {
		return lastMovedTimer;
	}

	public boolean isTurbostarted() {
		return turbostarted;
	}

	@Override
	public BoardElement deepCopy(Chessboard chessboard) {
		return new Pawn(row, col, chessboard, getType(), turbostarted, lastMovedTimer);
	}

	@Override
	public char getNickName() {
		return type == TYPE_WHITE ? '+' : '-';
	}

	@Override
	public int getID() {
		return type == TYPE_WHITE ? ValidMove.PAWN : ValidMove.PAWN + 1;
	}
}
