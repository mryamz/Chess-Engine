package chess_engine.entities;

import java.util.ArrayList;

import chess_engine.structs.OnPawnPromotionListener;
import chess_engine.structs.ValidMove;

public class Chessboard {

	private OnPawnPromotionListener onPawnPromotionListenerBlack = new OnPawnPromotionListener() {
		@Override
		public int promote() {
			return -1;
		}
	};

	private OnPawnPromotionListener onPawnPromotionListenerWhite = new OnPawnPromotionListener() {
		@Override
		public int promote() {
			return -1;
		}
	};

	private BoardElement[][] squares = new BoardElement[8][8];
	private ArrayList<ValidMove> history = new ArrayList<>();

	public static final int WHITE_WINS_BY_CHECKMATE_STATE = 1;
	public static final int BLACK_WINS_BY_CHECKMATE_STATE = 5;
	public static final int STALEMATE_STATE = 2;
	public static final int RUNNING_STATE = 3;
	public static final int FIFTY_MOVE_RULE_STATE = 4;

	// sets up classic chess
	public Chessboard() {
		squares[0][0] = new Rook(0, 0, this, BoardElement.TYPE_BLACK);
		squares[0][1] = new Knight(0, 1, this, BoardElement.TYPE_BLACK);
		squares[0][2] = new Bishop(0, 2, this, BoardElement.TYPE_BLACK);
		squares[0][3] = new Queen(0, 3, this, BoardElement.TYPE_BLACK);
		squares[0][4] = new King(0, 4, this, BoardElement.TYPE_BLACK);
		squares[0][5] = new Bishop(0, 5, this, BoardElement.TYPE_BLACK);
		squares[0][6] = new Knight(0, 6, this, BoardElement.TYPE_BLACK);
		squares[0][7] = new Rook(0, 7, this, BoardElement.TYPE_BLACK);

		// place pawns
		for (int i = 0; i < 8; i++) {
			squares[1][i] = new Pawn(1, i, this, BoardElement.TYPE_BLACK, false, 0);
			squares[6][i] = new Pawn(6, i, this, BoardElement.TYPE_WHITE, false, 0);
		}

		// fill in empty squares
		for (int x = 2; x < 6; x++) {
			for (int y = 0; y < 8; y++) {
				squares[x][y] = new Empty(x, y, this, BoardElement.TYPE_EMPTY);
			}
		}

		squares[7][0] = new Rook(7, 0, this, BoardElement.TYPE_WHITE);
		squares[7][1] = new Knight(7, 1, this, BoardElement.TYPE_WHITE);
		squares[7][2] = new Bishop(7, 2, this, BoardElement.TYPE_WHITE);
		squares[7][3] = new Queen(7, 3, this, BoardElement.TYPE_WHITE);
		squares[7][4] = new King(7, 4, this, BoardElement.TYPE_WHITE);
		squares[7][5] = new Bishop(7, 5, this, BoardElement.TYPE_WHITE);
		squares[7][6] = new Knight(7, 6, this, BoardElement.TYPE_WHITE);
		squares[7][7] = new Rook(7, 7, this, BoardElement.TYPE_WHITE);

	}

	public ArrayList<ValidMove> getAllPotentialMovesFor(int type) {
		ArrayList<ValidMove> moves = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (squares[i][j].getType() == type) {
					moves.addAll(squares[i][j].getPotentiallyValidMoves());
				}
			}
		}
		return moves;
	}

	public ArrayList<ValidMove> getAllPotentialMoves() {
		ArrayList<ValidMove> moves = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				moves.addAll(squares[i][j].getPotentiallyValidMoves());
			}
		}
		return moves;
	}

	public boolean wouldThisMoveEnterCheck(ValidMove move) {
		int other_type = move.getPiece_type() == BoardElement.TYPE_BLACK ? BoardElement.TYPE_WHITE : BoardElement.TYPE_BLACK;
		Chessboard fake = deepCopy(this);
		fake.move(move);
		ArrayList<ValidMove> moves = fake.getAllPotentialMovesFor(other_type);
		// TODO: if move is a pawn-promotion, ensure all promotions to Q, N, B,
		// and R are checked!!!!!
		return !fake.containsCheck(moves);
	}

	public void move(ValidMove move) {
		int row = move.getInitial_position_row();
		int nextRow = move.getNext_position_row();
		int col = move.getInitial_position_col();
		int nextCol = move.getNext_position_col();
		int type = getSquares()[row][col].type;
		// int other = type == BoardElement.TYPE_BLACK ? BoardElement.TYPE_WHITE
		// : BoardElement.TYPE_BLACK;
		int backrank = type == BoardElement.TYPE_WHITE ? 7 : 0;

		getSquares()[nextRow][nextCol] = getSquares()[row][col].deepCopy(this);
		getSquares()[nextRow][nextCol].set(nextRow, nextCol);
		getSquares()[row][col] = new Empty(row, col, this, BoardElement.TYPE_EMPTY);

		if (move.isPawnPromotion()) {
			int promotion_id = move.getPromotion_id() == -1 ? type == BoardElement.TYPE_BLACK ? onPawnPromotionListenerBlack.promote() : onPawnPromotionListenerWhite.promote() : move.getPromotion_id();
			move.setPromotion_id(promotion_id);
			switch (promotion_id) {
			case ValidMove.KNIGHT:
				getSquares()[nextRow][nextCol] = new Knight(nextRow, nextCol, this, move.getPiece_type());
				break;
			case ValidMove.BISHOP:
				getSquares()[nextRow][nextCol] = new Bishop(nextRow, nextCol, this, move.getPiece_type());
				break;
			case ValidMove.ROOK:
				getSquares()[nextRow][nextCol] = new Rook(nextRow, nextCol, this, move.getPiece_type());
				break;
			case ValidMove.QUEEN:
				getSquares()[nextRow][nextCol] = new Queen(nextRow, nextCol, this, move.getPiece_type());
				break;
			default:
				break;
			}

			ArrayList<ValidMove> m = getSquares()[nextRow][nextCol].getValidMoves();
			for (ValidMove vm : m) {
				if (vm.isCheckingMove()) {
					move.setCheckingMove(true);
				}
			}
		}

		if (move.isCastlingQueenSide()) {

			getSquares()[backrank][3] = getSquares()[backrank][0].deepCopy(this);
			getSquares()[backrank][3].set(backrank, 3);
			getSquares()[backrank][0] = new Empty(backrank, 0, this, BoardElement.TYPE_EMPTY);

		}

		if (move.isCastlingKingSide()) {

			getSquares()[backrank][5] = getSquares()[backrank][7].deepCopy(this);
			getSquares()[backrank][5].set(backrank, 5);
			getSquares()[backrank][7] = new Empty(backrank, 7, this, BoardElement.TYPE_EMPTY);
		}

		if (move.isEnPassant()) {
			int offset = getSquares()[nextRow][nextCol].getType() == BoardElement.TYPE_BLACK ? -1 : 1;
			getSquares()[nextRow + offset][nextCol] = new Empty(nextRow + offset, nextCol, this, BoardElement.TYPE_EMPTY);
		}

	}

	public int getBoardstate(int type) {
		int other = type == BoardElement.TYPE_WHITE ? BoardElement.TYPE_BLACK : BoardElement.TYPE_WHITE;
		if (history.size() > 100) {
			// check for 50 move rule
			boolean noPawnHasMovedinLast50turns = true; // disprove the
														// assumption

			boolean boardHasPawn = false; // prove the presumption
			BREAK: {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (squares[i][j] instanceof Pawn) {
							boardHasPawn = true; // proved
							if (((Pawn) squares[i][j]).getLastMovedTimer() < 50) {
								noPawnHasMovedinLast50turns = false;// proved
								break BREAK;
							}
						}
					}
				}
			}

			boolean hasCapturedInLast50turns = false;
			if (noPawnHasMovedinLast50turns || !boardHasPawn) {
				// check for any captures in the last 50 turns
				for (int i = history.size() - 1; i > history.size() - 100; i--) {
					if (history.get(i).getTarget_id() != ValidMove.EMPTY && history.get(i).getTarget_id() != ValidMove.KING) {
						hasCapturedInLast50turns = true;
						break;
					}
				}

				if (!hasCapturedInLast50turns) {
					return FIFTY_MOVE_RULE_STATE;
				}
			}

		}

		// check for valid moves
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (squares[i][j].getType() == other) {
					ArrayList<ValidMove> m = squares[i][j].getValidMoves();
					if (!m.isEmpty())
						return RUNNING_STATE;
				}
			}
		}

		boolean hasCheck = false;
		BACK_OUT: {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (squares[i][j].getType() == type) {
						ArrayList<ValidMove> m = squares[i][j].getPotentiallyValidMoves();
						if (containsCheck(m)) {
							hasCheck = true;
							break BACK_OUT;
						}
					}
				}
			}
		}

		return hasCheck ? type == BoardElement.TYPE_WHITE ? WHITE_WINS_BY_CHECKMATE_STATE : BLACK_WINS_BY_CHECKMATE_STATE : STALEMATE_STATE;
	}

	public ArrayList<ValidMove> getValidMovesForType(int type) {
		ArrayList<ValidMove> vms = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (getSquares(i, j).getType() == type)
					vms.addAll(getSquares(i, j).getValidMoves());
			}
		}
		return vms;
	}

	public int pressureAt(int row, int col, int type) {
		int sum = 0;

		for (ValidMove move : getAllPotentialMoves()) {
			if (move.getPiece_type() == type)
				if (move.getNext_position_row() == row && move.getNext_position_col() == col) {
					sum++;
				}
		}

		return sum;
	}

	// returns true if type: BLACK or WHITE is in check
	public boolean containsCheck(ArrayList<ValidMove> moves) {
		for (ValidMove move : moves)
			if (move.isCheckingMove()) {
				return true;
			}

		return false;
	}

	// always finds a king
	public King getKing(int type) {
		King k = null;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (squares[i][j].getType() == type && squares[i][j] instanceof King) {
					k = (King) squares[i][j];
				}
			}
		}

		return k;
	}

	public void loadFromPGN(String path) {

	}

	public BoardElement[][] getSquares() {
		return squares;
	}

	public BoardElement getSquares(int i, int j) {
		if (i < 0 || i > 7 || j < 0 || j > 7)
			return new Empty(i, j, this, BoardElement.TYPE_EMPTY);

		return squares[i][j];
	}

	public ArrayList<ValidMove> getHistory() {
		return history;
	}

	public Chessboard deepCopy(Chessboard og_board) {
		Chessboard board = new Chessboard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board.squares[i][j] = og_board.squares[i][j].deepCopy(board);
			}
		}
		return board;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				sb.append(squares[i][j].getNickName() + " ");
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public void setOnPawnPromotionListenerBlack(OnPawnPromotionListener onPawnPromotionListener) {
		this.onPawnPromotionListenerBlack = onPawnPromotionListener;
	}

	public void setOnPawnPromotionListenerWhite(OnPawnPromotionListener onPawnPromotionListenerWhite) {
		this.onPawnPromotionListenerWhite = onPawnPromotionListenerWhite;
	}

}
