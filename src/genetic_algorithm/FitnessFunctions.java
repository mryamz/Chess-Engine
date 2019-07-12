package genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;

import chess_engine.ChessEngine;
import chess_engine.entities.BoardElement;
import chess_engine.entities.Chessboard;
import perceptron.MLPawnPromotionListener;
import perceptron.MultilayerPerceptron;

public class FitnessFunctions {

	private static void createThreadSafeMatch(Genotype genotype, int i, ArrayList<MultilayerPerceptron> players) {
		System.out.println("Started " + genotype.population.get(i * 2).getID() + " vs " + genotype.population.get(i * 2 + 1).getID());

		MultilayerPerceptron white = players.get(i * 2);
		MultilayerPerceptron black = players.get(i * 2 + 1);
		// play a game of chess
		Collections.shuffle(genotype.population);
		float whiteFitnessValue = 0;
		float blackFitnessValue = 0;

		ChessEngine ce = new ChessEngine();

		GAOnGameOverListener gameOverListener = new GAOnGameOverListener();
		ce.addOnGameCompleteListener(gameOverListener);
		ce.setOnPawnPromotionListenerWhite(new MLPawnPromotionListener(white));
		ce.setOnPawnPromotionListenerBlack(new MLPawnPromotionListener(black));

		int turn = 0;
		// each iteration is one complete turn
		while (gameOverListener.gameInSession()) {
			white.setInputLayer(ce.getChessboard());
			white.activate();
			ArrayList<String> whitemoves = ce.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
			int whitechoice = (int) (Math.round(white.getMoveSelectionValue() * (whitemoves.size() - 1)));
			if (!whitemoves.isEmpty())
				ce.performMove(whitemoves.get(whitechoice));

			black.setInputLayer(ce.getChessboard());
			black.activate();
			ArrayList<String> blackmoves = ce.getValidMovesForTypeAsPGN(BoardElement.TYPE_BLACK);
			int blackchoice = (int) (Math.round(black.getMoveSelectionValue() * (blackmoves.size() - 1)));
			if (!blackmoves.isEmpty())
				ce.performMove(blackmoves.get(blackchoice));

			turn++;
		}

		if (gameOverListener.getFinish_state() == Chessboard.BLACK_WINS_BY_CHECKMATE_STATE) {
			blackFitnessValue += GeneticUtils.getPointsBasedOnTime(turn);
		}

		if (gameOverListener.getFinish_state() == Chessboard.WHITE_WINS_BY_CHECKMATE_STATE) {
			whiteFitnessValue += GeneticUtils.getPointsBasedOnTime(turn);
		}

		genotype.population.get(i * 2).instantaneous_fitness_score = whiteFitnessValue;
		genotype.population.get(i * 2).accumulative_fitness_score += whiteFitnessValue;

		genotype.population.get(i * 2 + 1).instantaneous_fitness_score = blackFitnessValue;
		genotype.population.get(i * 2 + 1).accumulative_fitness_score += blackFitnessValue;

		System.out.println("Ended " + genotype.population.get(i * 2).getID() + " vs " + genotype.population.get(i * 2 + 1).getID());
	}

	public static void getFitnessTrial3(Genotype genotype) {
		ArrayList<MultilayerPerceptron> players = genotype.getPhenotype();

		int len = genotype.getOriginalValueN() / 2;
		if (len % 2 != 0) {
			throw new IllegalArgumentException("Len should be an even number, not " + len);
		}

		Thread[] threads = new Thread[len];
		boolean[] finished_executing = new boolean[len];

		threads[0] = new Thread(() -> {
			createThreadSafeMatch(genotype, 0, players);
			finished_executing[0] = true;
		});
		threads[0].start();

		threads[1] = new Thread(() -> {
			createThreadSafeMatch(genotype, 1, players);
			finished_executing[1] = true;
		});
		threads[1].start();

		threads[2] = new Thread(() -> {
			createThreadSafeMatch(genotype, 2, players);
			finished_executing[0] = true;
		});
		threads[2].start();

		threads[3] = new Thread(() -> {
			createThreadSafeMatch(genotype, 3, players);
			finished_executing[3] = true;
		});
		threads[3].start();

		threads[4] = new Thread(() -> {
			createThreadSafeMatch(genotype, 4, players);
			finished_executing[4] = true;
		});
		threads[4].start();

		threads[5] = new Thread(() -> {
			createThreadSafeMatch(genotype, 5, players);
			finished_executing[5] = true;
		});
		threads[5].start();

		// pausing execution in main thread until all others have finished
		while (Thread.activeCount() != 1)
			;
	}

	@Deprecated
	public static ArrayList<Float> getFitnessTrial2(Genotype genotype) {
		ArrayList<Float> fitnessScores = new ArrayList<>();
		ArrayList<MultilayerPerceptron> players = genotype.getPhenotype();

		// play a game of chess
		Collections.shuffle(genotype.population);
		for (int i = 0; i < genotype.getOriginalValueN() / 2; i++) {
			float whiteFitnessValue = 0;
			float blackFitnessValue = 0;

			ChessEngine ce = new ChessEngine();
			MultilayerPerceptron white = players.get(i * 2);
			MultilayerPerceptron black = players.get(i * 2 + 1);

			GAOnGameOverListener gameOverListener = new GAOnGameOverListener();
			ce.addOnGameCompleteListener(gameOverListener);
			ce.setOnPawnPromotionListenerWhite(new MLPawnPromotionListener(white));
			ce.setOnPawnPromotionListenerBlack(new MLPawnPromotionListener(black));

			int turn = 0;
			// each iteration is one complete turn
			while (gameOverListener.gameInSession()) {
				white.setInputLayer(ce.getChessboard());
				white.activate();
				ArrayList<String> whitemoves = ce.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
				int whitechoice = (int) (Math.round(white.getMoveSelectionValue() * (whitemoves.size() - 1)));
				if (!whitemoves.isEmpty())
					ce.performMove(whitemoves.get(whitechoice));

				black.setInputLayer(ce.getChessboard());
				black.activate();
				ArrayList<String> blackmoves = ce.getValidMovesForTypeAsPGN(BoardElement.TYPE_BLACK);
				int blackchoice = (int) (Math.round(black.getMoveSelectionValue() * (blackmoves.size() - 1)));
				if (!blackmoves.isEmpty())
					ce.performMove(blackmoves.get(blackchoice));

				turn++;
			}

			if (gameOverListener.getFinish_state() == Chessboard.BLACK_WINS_BY_CHECKMATE_STATE) {
				blackFitnessValue += GeneticUtils.getPointsBasedOnTime(turn);
			}

			if (gameOverListener.getFinish_state() == Chessboard.WHITE_WINS_BY_CHECKMATE_STATE) {
				whiteFitnessValue += GeneticUtils.getPointsBasedOnTime(turn);
			}

			genotype.population.get(i * 2).instantaneous_fitness_score = whiteFitnessValue;
			genotype.population.get(i * 2).accumulative_fitness_score += whiteFitnessValue;

			genotype.population.get(i * 2 + 1).instantaneous_fitness_score = blackFitnessValue;
			genotype.population.get(i * 2 + 1).accumulative_fitness_score += blackFitnessValue;

			fitnessScores.add(whiteFitnessValue);
			fitnessScores.add(blackFitnessValue);

		}

		return fitnessScores;
	}

}
