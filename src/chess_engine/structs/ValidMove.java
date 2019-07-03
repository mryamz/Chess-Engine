package chess_engine.structs;

import chess_engine.entities.Bishop;
import chess_engine.entities.BoardElement;
import chess_engine.entities.Chessboard;
import chess_engine.entities.King;
import chess_engine.entities.Knight;
import chess_engine.entities.Pawn;
import chess_engine.entities.Queen;
import chess_engine.entities.Rook;

public class ValidMove {

	public void setCheckingMove(boolean isCheckingMove) {
		this.isCheckingMove = isCheckingMove;
	}

	public static final int KING = 12;
	public static final int QUEEN = 10;
	public static final int ROOK = 8;
	public static final int BISHOP = 6;
	public static final int KNIGHT = 4;
	public static final int PAWN = 2;
	public static final int EMPTY = 0;

	private int piece_id = EMPTY, piece_type;
	private int target_id = EMPTY, target_type;
	private int promotion_id = -1;
	private int initial_position_row;
	private int initial_position_col;
	private int next_position_row;
	private int next_position_col;

	private boolean isPawnPromotion;
	private boolean isEnPassant;
	private boolean isCheckingMove;
	private boolean isOutsideOfBounds;
	private boolean isCastlingQueenSide;
	private boolean isCastlingKingSide;
	private Chessboard board;

	public OnValidMovePlayedListener move = new OnValidMovePlayedListener() {
		@Override
		public void move(BoardElement element) {

		}
	};

	public ValidMove(Chessboard board, int initial_position_row, int initial_position_col, int next_position_row, int next_position_col) {
		this.board = board.deepCopy(board);
		this.initial_position_row = initial_position_row;
		this.initial_position_col = initial_position_col;
		this.next_position_row = next_position_row;
		this.next_position_col = next_position_col;

		isOutsideOfBounds = next_position_row < 0 || next_position_row > 7 || next_position_col < 0 || next_position_col > 7;

		BoardElement piece = board.getSquares(initial_position_row, initial_position_col);
		piece_type = piece.getType();

		BoardElement target = board.getSquares(next_position_row, next_position_col);

		if (piece instanceof King) {
			piece_id = KING;
		}

		if (piece instanceof Queen) {
			piece_id = QUEEN;
		}

		if (piece instanceof Rook) {
			piece_id = ROOK;
		}

		if (piece instanceof Bishop) {
			piece_id = BISHOP;
		}

		if (piece instanceof Knight) {
			piece_id = KNIGHT;

		}

		if (piece instanceof Pawn) {
			piece_id = PAWN;

		}

		if (target instanceof King) {
			isCheckingMove = true;
			target_id = KING;
		}

		if (target instanceof Queen) {
			target_id = QUEEN;
		}

		if (target instanceof Rook) {
			target_id = ROOK;
		}

		if (target instanceof Bishop) {
			target_id = BISHOP;
		}

		if (target instanceof Knight) {
			target_id = KNIGHT;
		}

		if (target instanceof Pawn) {
			target_id = PAWN;
		}

		target_type = target.getType();

	}

	public ValidMove(Chessboard board, int initial_position_row, int initial_position_col, int next_position_row, int next_position_col, boolean isCastlingQueenSide, boolean isCastlingKingSide, boolean isEnPassant) {
		this(board, initial_position_row, initial_position_col, next_position_row, next_position_col);
		this.isCastlingKingSide = isCastlingKingSide;
		this.isCastlingQueenSide = isCastlingQueenSide;
		this.isEnPassant = isEnPassant;

	}

	public void setMove(OnValidMovePlayedListener move) {
		this.move = move;
	}

	public int getInitial_position_row() {
		if (initial_position_row > 7)
			return 7;
		if (initial_position_row < 0)
			return 0;

		return initial_position_row;
	}

	public int getInitial_position_col() {
		if (initial_position_col > 7)
			return 7;
		if (initial_position_col < 0)
			return 0;

		return initial_position_col;
	}

	public int getNext_position_row() {
		if (next_position_row > 7)
			return 7;
		if (next_position_row < 0)
			return 0;

		return next_position_row;
	}

	public int getNext_position_col() {
		if (next_position_col > 7)
			return 7;
		if (next_position_col < 0)
			return 0;

		return next_position_col;
	}

	public boolean isPawnPromotion() {
		return isPawnPromotion;
	}

	public boolean isEnPassant() {
		return isEnPassant;
	}

	public boolean isCheckingMove() {
		return isCheckingMove;
	}

	public int getPiece_id() {
		return piece_id;
	}

	public int getPiece_type() {
		return piece_type;
	}

	public int getTarget_id() {
		return target_id;
	}

	public int getTarget_type() {
		return target_type;
	}

	public boolean isOutsideOfBounds() {
		return isOutsideOfBounds;
	}

	public void setPawnPromotion(boolean isPawnPromotion) {

		this.isPawnPromotion = isPawnPromotion;
	}

	public boolean isCastlingKingSide() {
		return isCastlingKingSide;
	}

	public boolean isCastlingQueenSide() {
		return isCastlingQueenSide;
	}

	public Chessboard getChessboard() {
		return board;
	}

	public int getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(int promotion_id) {
		this.promotion_id = promotion_id;
	}

	public void setChessboard(Chessboard board) {
		this.board = board;
	}
	
	@Override
	public String toString() {
		return String.format("%s: (%s, %s) -> (%s, %s)", getPiece_id(), getInitial_position_col(), getInitial_position_row(), getNext_position_col(), getNext_position_row());
	}
}
