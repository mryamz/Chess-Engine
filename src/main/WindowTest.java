package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import chess_engine.ChessEngine;
import ui.ChessWindow;
import ui.GameDetailsWindow;

public class WindowTest {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		ChessEngine cd = new ChessEngine();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float size = (float) (Math.min(screenSize.getWidth(), screenSize.getHeight()) * .8d);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		GameDetailsWindow gdw = new GameDetailsWindow(cd, 0, 25, (int) (screenSize.width / 2 - size / 2), (int) size);
		ChessWindow win = new ChessWindow((int) size, (int) size, cd, gdw);
		gdw.setCW(win);
		
	}
}
