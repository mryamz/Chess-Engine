package main;

import java.util.ArrayList;
import java.util.Random;

import chess_engine.ChessEngine;
import chess_engine.entities.BoardElement;
import chess_engine.structs.OnGameCompleteListener;
import chess_engine.structs.OnPawnPromotionListener;
import chess_engine.structs.ValidMove;

public class EngineTest {

	private static boolean running = true;
	private static int state = -1;

	public static void main(String[] args) {
		ChessEngine ce = new ChessEngine();

		ce.addOnGameCompleteListener(new OnGameCompleteListener() {

			@Override
			public void OnGameComplete(int state) {
				running = false;

				System.out.println("____________DONE____________");
				System.out.println(ce.buildPGNFileAsString());

				EngineTest.state = state;
				System.out.println("____________DONE____________");

			}
		});

		int[] promo = { ValidMove.BISHOP, ValidMove.KNIGHT, ValidMove.ROOK, ValidMove.QUEEN };

		ce.setOnPawnPromotionListenerBlack(new OnPawnPromotionListener() {

			@Override
			public int promote() {
				return promo[new Random().nextInt(4)];
			}
		});

		ce.setOnPawnPromotionListenerWhite(new OnPawnPromotionListener() {

			@Override
			public int promote() {
				return promo[new Random().nextInt(4)];
			}
		});

		boolean iswhite = true;
		while (running) {
			iswhite = !iswhite;
			int type = iswhite ? BoardElement.TYPE_WHITE : BoardElement.TYPE_BLACK;
			ArrayList<ValidMove> moves = ce.getValidMovesForType(type);
			if (!moves.isEmpty())
				ce.performMove(moves.get(new Random().nextInt(moves.size())));

		}
		
		System.out.println(state);

		ce.copyPGNHistoryToClipboard();
	}
}
