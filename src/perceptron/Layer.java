package perceptron;

public class Layer {
	public double[] neurons;
	public double[] weights;
	public double[] biases;

	public Layer(int numberOfNeurons) {
		neurons = new double[numberOfNeurons];
		weights = new double[numberOfNeurons];
		biases = new double[numberOfNeurons];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Neurons: [");
		for (int i = 0; i < neurons.length; i++) {
			sb.append(neurons[i]).append(" ");
		}
		sb.append("]" + System.lineSeparator() + "Weights: [");
		for (int i = 0; i < neurons.length; i++) {
			sb.append(weights[i]).append(" ");
		}
		sb.append("]" + System.lineSeparator() + "Biases: [");
		for (int i = 0; i < neurons.length; i++) {
			sb.append(biases[i]).append(" ");
		}
		sb.append("]" + System.lineSeparator());
		return sb.toString();
	}
}
