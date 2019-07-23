package chess_engine;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import chess_engine.entities.BoardElement;
import chess_engine.entities.Chessboard;
import chess_engine.entities.Pawn;
import chess_engine.structs.ChessUtils;
import chess_engine.structs.OnGameCompleteListener;
import chess_engine.structs.OnPawnPromotionListener;
import chess_engine.structs.OnTurnCompleteListener;
import chess_engine.structs.ValidMove;

public class ChessEngine {

	private Chessboard chessboard;
	private int turnCount = 0;
	private ArrayList<OnTurnCompleteListener> onTurnCompleteListeners = new ArrayList<>();
	private ArrayList<OnGameCompleteListener> onGameCompleteListeners = new ArrayList<>();

	private ArrayList<String> pgn_history = new ArrayList<>();
	private ArrayList<ValidMove> raw_history = new ArrayList<>();

	public ChessEngine() {
		chessboard = new Chessboard();
	}

	public void performMove(String pgn) {
		performMove(getValidMoveFromPGN(pgn));
	}

	public void performMove(ValidMove move) {
		turnCount++;
		BoardElement element_to_move = chessboard.getSquares()[move.getInitial_position_row()][move.getInitial_position_col()];
		boolean shouldReturn = element_to_move.getType() == BoardElement.TYPE_WHITE && turnCount % 2 == 0 || element_to_move.getType() == BoardElement.TYPE_BLACK && turnCount % 2 != 0;
		if (shouldReturn) {
			turnCount--;
			return;
		}

		chessboard.getHistory().add(move);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessboard.getSquares()[i][j] instanceof Pawn) {
					Pawn p = ((Pawn) chessboard.getSquares()[i][j]);
					if (p.getType() == element_to_move.getType())
						p.tick();
				}
			}
		}

		move.move.move(element_to_move);
		chessboard.move(move);

		// its important to call this here, as a call before
		// chessboard.move(move)
		// would lead to immature deduction in the event of pawn promotion ;)
		pgn_history.add(ChessUtils.getPGN(move));
		raw_history.add(move);

		for (OnTurnCompleteListener listener : onTurnCompleteListeners) {
			listener.onTurnComplete();
		}

		int boardstate = chessboard.getBoardstate(move.getPiece_type());
		for (OnGameCompleteListener listener : onGameCompleteListeners) {
			if (boardstate != Chessboard.RUNNING_STATE)
				listener.OnGameComplete(boardstate);
		}
	}

	public BoardElement get(int i, int j) {
		return chessboard.getSquares()[i][j];
	}

	public void setOnPawnPromotionListenerBlack(OnPawnPromotionListener onPawnPromotionListener) {
		chessboard.setOnPawnPromotionListenerBlack(onPawnPromotionListener);
	}

	public void setOnPawnPromotionListenerWhite(OnPawnPromotionListener onPawnPromotionListener) {
		chessboard.setOnPawnPromotionListenerWhite(onPawnPromotionListener);
	}

	public void addOnTurnCompleteListener(OnTurnCompleteListener onTurnCompleteListener) {
		this.onTurnCompleteListeners.add(onTurnCompleteListener);
	}

	public void addOnGameCompleteListener(OnGameCompleteListener onGameCompleteListener) {
		this.onGameCompleteListeners.add(onGameCompleteListener);
	}

	public ArrayList<String> getHistoryAsPGNs() {
		return pgn_history;
	}

	public ArrayList<ValidMove> getValidMovesForType(int type) {
		return chessboard.getValidMovesForType(type);
	}

	public ArrayList<String> getValidMovesForTypeAsPGN(int type) {
		ArrayList<ValidMove> vms = getValidMovesForType(type);
		ArrayList<String> pgns = new ArrayList<>();

		for (ValidMove v : vms) {
			pgns.add(ChessUtils.getPGN(v));
		}

		// sort alphabetically
		Collections.sort(pgns, Collator.getInstance(new Locale("English")));

		return pgns;
	}

	public ValidMove getValidMoveFromPGN(String pgn) {
		int type = turnCount % 2 == 0 ? BoardElement.TYPE_WHITE : BoardElement.TYPE_BLACK;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				BoardElement bm = chessboard.getSquares(i, j);
				if (bm.getType() == type)
					for (ValidMove m : bm.getValidMoves()) {
						if (ChessUtils.getPGN(m).equals(pgn)) {
							return m;
						}
					}
			}
		}

		System.out.println("ERROR, INVALID PGN MOVE");
		return null;
	}

	@Override
	public String toString() {
		return chessboard.toString();
	}

	public void copyPGNHistoryToClipboard() {

		String contents = buildPGNFileAsString();
		StringSelection stringSelection = new StringSelection(contents);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	public String buildPGNFileAsString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < pgn_history.size(); i += 2) {
			String line = (i / 2 + 1) + ". " + pgn_history.get(i) + " " + (i + 1 > pgn_history.size() - 1 ? "..." : pgn_history.get(i + 1) + " \n");
			sb.append(line);
		}

		return sb.toString();
	}

	public void restart(boolean clearListeners) {
		raw_history.clear();
		chessboard = new Chessboard();
		turnCount = 0;
		pgn_history.clear();
		if (clearListeners) {
			onTurnCompleteListeners.clear();
			onGameCompleteListeners.clear();
		}
	}

	public ArrayList<ValidMove> getRaw_history() {
		return raw_history;
	}

	public Chessboard getChessboard() {
		return chessboard;
	}
}
