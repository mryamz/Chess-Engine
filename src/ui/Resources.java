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

			blackBishop = ImageIO.read(Class.class.getResource("/blackBishop.png"));
			blackKing = ImageIO.read(Class.class.getResource("/blackKing.png"));
			blackKnight = ImageIO.read(Class.class.getResource("/blackKnight.png"));
			blackPawn = ImageIO.read(Class.class.getResource("/blackPawn.png"));
			blackRook = ImageIO.read(Class.class.getResource("/blackRook.png"));
			blackQueen = ImageIO.read(Class.class.getResource("/blackQueen.png"));
			whiteBishop = ImageIO.read(Class.class.getResource("/whiteBishop.png"));
			whiteKing = ImageIO.read(Class.class.getResource("/whiteKing.png"));
			whiteKnight = ImageIO.read(Class.class.getResource("/whiteKnight.png"));
			whitePawn = ImageIO.read(Class.class.getResource("/whitePawn.png"));
			whiteRook = ImageIO.read(Class.class.getResource("/whiteRook.png"));
			whiteQueen = ImageIO.read(Class.class.getResource("/whiteQueen.png"));

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
