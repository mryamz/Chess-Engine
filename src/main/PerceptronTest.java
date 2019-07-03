package main;

import java.util.ArrayList;

import chess_engine.ChessEngine;
import chess_engine.entities.BoardElement;
import chess_engine.structs.OnGameCompleteListener;
import perceptron.MLPawnPromotionListener;
import perceptron.MultilayerPerceptron;

public class PerceptronTest {

	private static boolean running = true;
	private static int state = -1;

	public static void main(String... args) {

		ChessEngine ce = new ChessEngine();

		ce.addOnGameCompleteListener(new OnGameCompleteListener() {
			@Override
			public void OnGameComplete(int state) {
				running = false;
				PerceptronTest.state = state;
			}
		});

		MultilayerPerceptron white = MultilayerPerceptron.createEmptyPerceptron();
		ce.setOnPawnPromotionListenerWhite(new MLPawnPromotionListener(white));

		MultilayerPerceptron black = MultilayerPerceptron.createEmptyPerceptron();
		ce.setOnPawnPromotionListenerBlack(new MLPawnPromotionListener(black));

		while (running) {
			white.setInputLayer(ce.getChessboard());
			white.activate();
			ArrayList<String> whitemoves = ce.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
			int whitechoice = (int) (Math.round(white.getMoveSelectionValue() * whitemoves.size()));
			ce.performMove(whitemoves.get(whitechoice));

			System.out.print((whitemoves.get(whitechoice)));

			black.setInputLayer(ce.getChessboard());
			black.activate();
			ArrayList<String> blackmoves = ce.getValidMovesForTypeAsPGN(BoardElement.TYPE_BLACK);
			int blackchoice = (int) (Math.round(black.getMoveSelectionValue() * blackmoves.size()));
			ce.performMove(blackmoves.get(blackchoice));

			System.out.println(", " + (blackmoves.get(blackchoice)));

		}
		ce.copyPGNHistoryToClipboard();
		System.out.println(state);
	}

}
