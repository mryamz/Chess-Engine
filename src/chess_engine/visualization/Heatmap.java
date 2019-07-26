package chess_engine.visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import chess_engine.entities.BoardElement;
import chess_engine.entities.Chessboard;
import mapper.ColorMapper;

public class Heatmap {

	private Chessboard board;

	public Heatmap(Chessboard board) {
		this.board = board;
	}

	// presure arrays can be easily converted into an image
	public int[][] genPressureArrays(int type) {
		int[][] pressureMap = new int[8][8];

		for (int i = 0; i < pressureMap.length; i++) {
			for (int j = 0; j < pressureMap[i].length; j++) {
				pressureMap[i][j] = board.pressureAt(i, j, type);
			}
		}

		return pressureMap;
	}

	public BufferedImage grabImage(int width, int height) {
		ColorMapper mapper = new ColorMapper(width, height, 15);
		// BufferedImage img = new BufferedImage(width, height,
		// BufferedImage.TYPE_INT_ARGB);
		// Graphics2D g = (Graphics2D) img.getGraphics();
		int[][] pressureMapWhite = genPressureArrays(BoardElement.TYPE_WHITE);
		int[][] pressureMapBlack = genPressureArrays(BoardElement.TYPE_BLACK);

		for (int i = 0; i < pressureMapWhite.length; i++) {
			for (int j = 0; j < pressureMapWhite[i].length; j++) {
				double valueWhite = pressureMapWhite[i][j] * 60;
				double valueBlack = pressureMapBlack[i][j] * 60;
				valueWhite = Math.max(0, valueWhite);
				valueWhite = Math.min(255, valueWhite);
				
				valueBlack = Math.max(0, valueBlack);
				valueBlack = Math.min(255, valueBlack);
				
				valueWhite /= 255d;
				valueBlack /= 255d;
				
				int w = width / pressureMapWhite.length;
				int h = height / pressureMapWhite[i].length;
				
				mapper.add(j * h, i * w, valueWhite, valueWhite/4, valueWhite/2, 1);
				mapper.add(j * h, i * w, valueBlack/4, valueBlack/3, valueBlack, 1);
				// g.setColor(new Color(value, value, value));
				// g.fillRect(j * h, i * w, w, h);
			}
		}

		return mapper.getBufferedImage();
	}
}
