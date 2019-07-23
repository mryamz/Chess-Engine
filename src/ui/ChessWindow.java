package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chess_engine.ChessEngine;
import chess_engine.entities.BoardElement;
import chess_engine.structs.OnPawnPromotionListener;
import chess_engine.structs.OnTurnCompleteListener;
import chess_engine.structs.ValidMove;
import genetic_algorithm.GeneticsIO;
import genetic_algorithm.Genotype;
import perceptron.MLPawnPromotionListener;
import perceptron.MultilayerPerceptron;

@SuppressWarnings("serial")
public class ChessWindow extends JPanel implements Runnable, MouseListener, KeyListener {

	private BufferedImage image;
	private Graphics2D g;
	private Thread thread;
	private Tile[][] tiles = new Tile[8][8];
	private GameDetailsWindow deets;
	private ChessEngine cd;

	private boolean isBlackCom, isWhiteCom;
	private MultilayerPerceptron whiteCom, blackCom;
	private int turn = 0;

	private OnTurnCompleteListener standardListner = new OnTurnCompleteListener() {

		@Override
		public void onTurnComplete() {
			turn++;
			if (isWhiteCom && !isBlackCom && turn % 2 == 0)
				doWhiteMove();

			if (isBlackCom && !isWhiteCom && turn % 2 == 1)
				doBlackMove();

		}
	};

	public ChessWindow(int w, int h, ChessEngine cd, GameDetailsWindow deets) {
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.createGraphics();
		setPreferredSize(new Dimension(w, h));
		this.cd = cd;
		this.deets = deets;

		int gen[] = new int[1];
		Genotype pop = GeneticsIO.loadSaveFile("save_test.data", gen);

		whiteCom = pop.popMostFitSolution(true).getPerceptron();
		blackCom = pop.popMostFitSolution(true).getPerceptron();

		cd.addOnTurnCompleteListener(standardListner);

		setUpHuman(BoardElement.TYPE_WHITE);
		setUpHuman(BoardElement.TYPE_BLACK);

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = new Tile(w / 8, h / 8, cd.get(i, j), (i + j) % 2 == 0);
			}
		}

		thread = new Thread(this);
		thread.start();

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addKeyListener(this);
		frame.addMouseListener(this);

		frame.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						tiles[i][j].setActivated(false);
						tiles[i][j].setMove(null);
					}
				}
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});
	}

	@Override
	public void run() {
		while (true) {
			try {
				getGraphics().drawImage(image, 0, 0, null);

				g.setColor(Color.RED);
				g.fillRect(0, 0, image.getWidth(), image.getHeight());
				draw(g);

			} catch (Exception e) {
				if (!(e instanceof NullPointerException))
					e.printStackTrace();
			}
		}
	}

	public void draw(Graphics2D g) {

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j].draw(g);
			}
		}
	}

	public void renderMode(boolean isWhite) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tiles[i][j].isFlipped = isWhite;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			deets.setVisible(true);
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		// select valid move
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (tiles[i][j].getBounds().contains(e.getPoint()) && tiles[i][j].getMove() != null) {
					cd.performMove(tiles[i][j].getMove());

					refreshTiles();
				}
				tiles[i][j].setMove(null);
			}
		}

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {

				// generate valid moves
				if (tiles[i][j].getBounds().contains(e.getPoint())) {
					tiles[i][j].setActivated(true);

					for (ValidMove move : tiles[i][j].getElement().getValidMoves()) {
						tiles[move.getNext_position_row()][move.getNext_position_col()].setMove(move);
					}

				} else {
					tiles[i][j].setActivated(false);
				}
			}
		}

	}

	public void setUpHuman(int type) {

		if (type == BoardElement.TYPE_BLACK) {
			cd.setOnPawnPromotionListenerBlack(new OnPawnPromotionListener() {

				@Override
				public int promote() {
					return PawnPromotionWindow.showPopup();
				}
			});
		}
		if (type == BoardElement.TYPE_WHITE) {
			cd.setOnPawnPromotionListenerWhite(new OnPawnPromotionListener() {

				@Override
				public int promote() {
					return PawnPromotionWindow.showPopup();
				}
			});
		}
	}

	public void doWhiteMove() {
		whiteCom.setInputLayer(cd.getChessboard());
		whiteCom.activate();
		ArrayList<String> whitemoves = cd.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
		int whitechoice = (int) (Math.round(whiteCom.getMoveSelectionValue() * (whitemoves.size() - 1)));
		if (!whitemoves.isEmpty())
			cd.performMove(whitemoves.get(whitechoice));
		refreshTiles();

	}

	public void doBlackMove() {
		blackCom.setInputLayer(cd.getChessboard());
		blackCom.activate();
		ArrayList<String> blackmoves = cd.getValidMovesForTypeAsPGN(BoardElement.TYPE_BLACK);
		int blackchoice = (int) (Math.round(blackCom.getMoveSelectionValue() * (blackmoves.size() - 1)));

		if (!blackmoves.isEmpty())
			cd.performMove(blackmoves.get(blackchoice));

		refreshTiles();
	}

	public void setUpCom(int type) {

		isWhiteCom = false;
		isBlackCom = false;
		if (type == BoardElement.TYPE_WHITE || type == -1) {
			isWhiteCom = true;
			cd.setOnPawnPromotionListenerWhite(new MLPawnPromotionListener(whiteCom));
		}

		if (type == BoardElement.TYPE_BLACK || type == -1) {
			isBlackCom = true;
			cd.setOnPawnPromotionListenerBlack(new MLPawnPromotionListener(blackCom));
		}

		if (isWhiteCom && turn % 2 == 0) {
			doWhiteMove();
		}
		if (isBlackCom && turn % 2 == 1) {
			doBlackMove();
		}
	}

	public void refreshTiles() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j].setElement(cd.get(i, j));
			}
		}
	}

	public void restartGame() {
		cd.restart(false);
		refreshTiles();
	}
}
