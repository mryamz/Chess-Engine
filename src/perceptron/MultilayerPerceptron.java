package perceptron;

import chess_engine.entities.Chessboard;

public class MultilayerPerceptron {

	public static MultilayerPerceptron createEmptyPerceptron() {
		Layer[] layers = new Layer[64];

		layers[0] = new Layer(64);
		for (int i = 1; i < layers.length - 1; i++) {
			layers[i] = new Layer(64);
		}
		layers[layers.length - 1] = new Layer(5);

		return new MultilayerPerceptron(layers);
	}

	private Layer[] layers;

	public MultilayerPerceptron(Layer[] layers) {
		this.layers = layers;
		randomizePerceptron();
	}

	public void randomizePerceptron() {
		// give input to first layer
		for (int j = 0; j < layers[0].neurons.length; j++) {
			layers[0].neurons[j] = Math.random();
		}

		// randomize weights and biases (input layer bias should be bias=zero)
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].neurons.length; j++) {
				layers[i].weights[j] = (Math.random() * 2) - 1;
				if (i > 0)
					layers[i].biases[j] = j * (Math.random() * 2) - 1;
			}
		}
	}

	public double getMoveSelectionValue() {
		return getOutput().neurons[0];
	}

	public void setInputLayer(Chessboard board) {
		int index = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				layers[0].neurons[index++] = board.getSquares(i, j).getID();
			}
		}
	}

	public Layer getOutput() {
		return layers[layers.length - 1];
	}

	public void activate() {
		for (int i = 0; i < layers.length - 1; i++) {
			setActivations(i);
		}
	}

	private void setActivations(int layer) {

		for (int i = 0; i < layers[layer + 1].neurons.length; i++) {

			double weightActivationProductBiasSum = layers[layer + 1].biases[i];

			for (int j = 0; j < layers[layer].neurons.length; j++) {
				weightActivationProductBiasSum += layers[layer].weights[j] * layers[layer].neurons[j];
			}

			layers[layer + 1].neurons[i] = MLMath.sigmoid(weightActivationProductBiasSum);

		}
	}

	public Layer[] getLayers() {
		return layers;
	}

	public void printOutput() {
		System.out.println("OUTPUT");
		for (int i = 0; i < getOutput().neurons.length; i++) {
			System.out.println(String.format("%s. value: %s", i + 1, getOutput().neurons[i]));
		}
	}
}
