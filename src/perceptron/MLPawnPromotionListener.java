package perceptron;

import chess_engine.structs.OnPawnPromotionListener;
import chess_engine.structs.ValidMove;

public class MLPawnPromotionListener implements OnPawnPromotionListener {

	private MultilayerPerceptron perceptron;

	public MLPawnPromotionListener(MultilayerPerceptron perceptron) {
		this.perceptron = perceptron;
	}

	@Override
	public int promote() {
		Layer out = perceptron.getOutput();
		double max = 0;
		int maxIndex = 1;
		for (int i = 1; i < out.neurons.length; i++) {
			if (out.neurons[i] > max) {
				maxIndex = i;
				max = out.neurons[i];
			}
		}

		switch (maxIndex) {
		case 1:
			return ValidMove.QUEEN;
		case 2:
			return ValidMove.ROOK;
		case 3:
			return ValidMove.KNIGHT;
		case 4:
			return ValidMove.BISHOP;
		}

		return 0;
	}

}
