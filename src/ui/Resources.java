package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	public BufferedImage blackBishop;
	public BufferedImage blackKing;
	public BufferedImage blackKnight;
	public BufferedImage blackPawn;
	public BufferedImage blackQueen;
	public BufferedImage blackRook;

	public BufferedImage whiteBishop;
	public BufferedImage whiteKing;
	public BufferedImage whiteKnight;
	public BufferedImage whitePawn;
	public BufferedImage whiteQueen;
	public BufferedImage whiteRook;

	private Resources() {
		try {

			blackBishop = ImageIO.read(new File("res/blackBishop.png"));
			blackKing = ImageIO.read(new File("res/blackKing.png"));
			blackKnight = ImageIO.read(new File("res/blackKnight.png"));
			blackPawn = ImageIO.read(new File("res/blackPawn.png"));
			blackRook = ImageIO.read(new File("res/blackRook.png"));
			blackQueen = ImageIO.read(new File("res/blackQueen.png"));
			whiteBishop = ImageIO.read(new File("res/whiteBishop.png"));
			whiteKing = ImageIO.read(new File("res/whiteKing.png"));
			whiteKnight = ImageIO.read(new File("res/whiteKnight.png"));
			whitePawn = ImageIO.read(new File("res/whitePawn.png"));
			whiteRook = ImageIO.read(new File("res/whiteRook.png"));
			whiteQueen = ImageIO.read(new File("res/whiteQueen.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Resources res;
	
	public static Resources getRes() {
		if(res == null)
			res = new Resources();
		
		return res;
	}
}
