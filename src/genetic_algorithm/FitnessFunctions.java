package genetic_algorithm;

import java.util.ArrayList;
import java.util.Collections;

import chess_engine.ChessEngine;
import chess_engine.entities.BoardElement;
import chess_engine.entities.Chessboard;
import perceptron.MLPawnPromotionListener;
import perceptron.MultilayerPerceptron;

public class FitnessFunctions {

	public static ArrayList<Float> getFitnessTrial2(Genotype genotype) {
		ArrayList<Float> fitnessScores = new ArrayList<>();
		ArrayList<MultilayerPerceptron> players = genotype.getPhenotype();

		Collections.shuffle(genotype.population);

		// play a game of chess
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
