package chess_engine.structs;

import java.util.ArrayList;

import chess_engine.entities.Bishop;
import chess_engine.entities.BoardElement;
import chess_engine.entities.Chessboard;
import chess_engine.entities.Empty;
import chess_engine.entities.King;
import chess_engine.entities.Knight;
import chess_engine.entities.Pawn;
import chess_engine.entities.Queen;
import chess_engine.entities.Rook;

public final class ChessUtils {

	// useful for pgn string concats
	public static char getRankFromRow(int row) {
		switch (row) {
		case 7:
			return '1';
		case 6:
			return '2';
		case 5:
			return '3';
		case 4:
			return '4';
		case 3:
			return '5';
		case 2:
			return '6';
		case 1:
			return '7';
		case 0:
			return '8';
		default:
			return '~';
		}
	}

	public static char getFileFromCol(int col) {
		switch (col) {
		case 7:
			return 'h';
		case 6:
			return 'g';
		case 5:
			return 'f';
		case 4:
			return 'e';
		case 3:
			return 'd';
		case 2:
			return 'c';
		case 1:
			return 'b';
		case 0:
			return 'a';
		default:
			return '*';
		}
	}

	public static String getFileRank(int row, int col) {
		return getFileFromCol(col) + "" + getRankFromRow(row);
	}

	public static String getPGN(ValidMove move) {
		int row = move.getInitial_position_row();
		int nextRow = move.getNext_position_row();
		int col = move.getInitial_position_col();
		int nextCol = move.getNext_position_col();
		Chessboard board = move.getChessboard().deepCopy(move.getChessboard());
		int type = board.getSquares()[row][col].getType();

		BoardElement target = board.getSquares(nextRow, nextCol);
		BoardElement friendly = board.getSquares()[row][col];

		StringBuilder pgn = new StringBuilder();

		// KING'S PGN NOTATION
		if (friendly instanceof King) {
			if (target instanceof Empty) {
				pgn.append("K" + ChessUtils.getFileRank(nextRow, nextCol));
			} else {
				pgn.append("Kx" + ChessUtils.getFileRank(nextRow, nextCol));
			}
		}

		// QUEEN'S PGN NOTATION
		if (friendly instanceof Queen) {
			if (target instanceof Empty) {
				pgn.append("Q" + ChessUtils.getFileRank(nextRow, nextCol));
			} else {
				pgn.append("Qx" + ChessUtils.getFileRank(nextRow, nextCol));
			}
		}
		// QUEEN'S PGN NOTATION when ambiguous
		if (friendly instanceof Queen)
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					BoardElement square = board.getSquares(i, j);
					if (square.getType() == type && (i != row || j != col) && square instanceof Queen) {
						// there's now a possibility for ambiguity with the
						// other friendly Knight's PGN
						ArrayList<ValidMove> validMoves = square.getValidMoves();
						for (ValidMove m : validMoves) {
							if (m.getNext_position_row() == nextRow && m.getNext_position_col() == nextCol) {
								// ambiguity confirmed
								// handle accordingly
								pgn.setLength(0);
								if (target instanceof Empty)
									pgn.append("Q" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + ChessUtils.getFileRank(nextRow, nextCol));
								else
									pgn.append("Q" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + "x" + ChessUtils.getFileRank(nextRow, nextCol));

							}
						}
					}
				}
			}


		// BISHOP'S PGN NOTATION
		if (friendly instanceof Bishop) {
			if (target instanceof Empty) {
				pgn.append("B" + ChessUtils.getFileRank(nextRow, nextCol));
			} else {
				pgn.append("Bx" + ChessUtils.getFileRank(nextRow, nextCol));
			}
		}
		
		if (friendly instanceof Bishop)
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					BoardElement square = board.getSquares(i, j);
					if (square.getType() == type && (i != row || j != col) && square instanceof Bishop) {
						// there's now a possibility for ambiguity with the
						// other friendly Knight's PGN
						ArrayList<ValidMove> validMoves = square.getValidMoves();
						for (ValidMove m : validMoves) {
							if (m.getNext_position_row() == nextRow && m.getNext_position_col() == nextCol) {
								// ambiguity confirmed
								// handle accordingly
								pgn.setLength(0);
								if (target instanceof Empty)
									pgn.append("B" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + ChessUtils.getFileRank(nextRow, nextCol));
								else
									pgn.append("B" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + "x" + ChessUtils.getFileRank(nextRow, nextCol));

							}
						}
					}
				}
			}

		// KNIGHT'S PGN NOTATION
		if (friendly instanceof Knight)
			if (target instanceof Empty) {
				pgn.append("N" + ChessUtils.getFileRank(nextRow, nextCol));
			} else {
				pgn.append("Nx" + ChessUtils.getFileRank(nextRow, nextCol));
			}

		// KNIGHT'S PGN NOTATION
		if (friendly instanceof Knight)
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					BoardElement square = board.getSquares(i, j);
					if (square.getType() == type && (i != row || j != col) && square instanceof Knight) {
						// there's now a possibility for ambiguity with the
						// other friendly Knight's PGN
						ArrayList<ValidMove> validMoves = square.getValidMoves();
						for (ValidMove m : validMoves) {
							if (m.getNext_position_row() == nextRow && m.getNext_position_col() == nextCol) {
								// ambiguity confirmed
								// handle accordingly
								pgn.setLength(0);
								if (target instanceof Empty)
									pgn.append("N" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + ChessUtils.getFileRank(nextRow, nextCol));
								else
									pgn.append("N" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + "x" + ChessUtils.getFileRank(nextRow, nextCol));

							}
						}
					}
				}
			}

		// KNIGHT'S PGN NOTATION
		if (friendly instanceof Rook)
			if (target instanceof Empty) {
				pgn.append("R" + ChessUtils.getFileRank(nextRow, nextCol));
			} else {
				pgn.append("Rx" + ChessUtils.getFileRank(nextRow, nextCol));
			}

		if (friendly instanceof Rook)
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					BoardElement square = board.getSquares(i, j);
					if (square.getType() == type && (i != row || j != col) && square instanceof Rook) {
						// there's now a possibility for ambiguity with the
						// other friendly Knight's PGN
						ArrayList<ValidMove> validMoves = square.getValidMoves();
						for (ValidMove m : validMoves) {
							if (m.getNext_position_row() == nextRow && m.getNext_position_col() == nextCol) {
								// ambiguity confirmed
								// handle accordingly
								pgn.setLength(0);
								if (target instanceof Empty)
									pgn.append("R" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + ChessUtils.getFileRank(nextRow, nextCol));
								else
									pgn.append("R" + (col == m.getInitial_position_col() ? ChessUtils.getRankFromRow(row) : ChessUtils.getFileFromCol(col)) + "x" + ChessUtils.getFileRank(nextRow, nextCol));

							}
						}
					}
				}
			}

		// PAWN NOTATION
		if (friendly instanceof Pawn) {
			char file0 = ChessUtils.getFileFromCol(col);
			char file1 = ChessUtils.getFileFromCol(nextCol);
			if (file0 == file1) {
				pgn.append(ChessUtils.getFileRank(nextRow, nextCol));
			} else {
				pgn.append(ChessUtils.getFileFromCol(col) + "x" + ChessUtils.getFileRank(nextRow, nextCol));

			}
		}

		// PAWN NOTATION
		if (move.isPawnPromotion()) {
			switch (move.getPromotion_id()) {
			case ValidMove.KNIGHT:
				pgn.append("=N");
				break;
			case ValidMove.BISHOP:
				pgn.append("=B");

				break;
			case ValidMove.ROOK:
				pgn.append("=R");
				break;
			case ValidMove.QUEEN:
				pgn.append("=Q");
				break;
			default:
				pgn.append("=?");
				break;
			}
		}

		// CASTLING
		if (move.isCastlingQueenSide()) {
			pgn.setLength(0);
			pgn.append("O-O-O");
		}
		if (move.isCastlingKingSide()) {
			pgn.setLength(0);
			pgn.append("O-O");
		}

		// CHECKING and MATING NOTATION
		board.move(move);
		ArrayList<ValidMove> friendly_moves = board.getAllPotentialMovesFor(type);
		boolean inCheck = board.containsCheck(friendly_moves);
		int boardstate = board.getBoardstate(type);
		if (boardstate == Chessboard.WHITE_WINS_BY_CHECKMATE_STATE || boardstate == Chessboard.BLACK_WINS_BY_CHECKMATE_STATE) {
			pgn.append("#");
		} else if (boardstate == Chessboard.STALEMATE_STATE || boardstate == Chessboard.FIFTY_MOVE_RULE_STATE) {
			pgn.append(" 1/2-1/2");
		} else if (inCheck) {
			pgn.append("+");
		}

		return pgn.toString();
	}
	
}
