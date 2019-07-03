package ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess_engine.structs.ValidMove;

public class PawnPromotionWindow {

	private static int promotion_id;

	public static int showPopup() {
		promotion_id = -1;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float size = (float) (Math.min(screenSize.getWidth(), screenSize.getHeight()) * .4d) + 5;
		panel.setPreferredSize(new Dimension((int) size, (int) size));

		JButton rookBtn = new JButton("Rook");
		rookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				promotion_id = ValidMove.ROOK;
				panel.setVisible(false);
			}
		});
		panel.add(rookBtn);

		JButton bishopBtn = new JButton("Bishop");
		bishopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				promotion_id = ValidMove.BISHOP;
				panel.setVisible(false);
			}
		});
		panel.add(bishopBtn);

		JButton queenBtn = new JButton("Queen");
		queenBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				promotion_id = ValidMove.QUEEN;
				panel.setVisible(false);
			}
		});
		panel.add(queenBtn);

		JButton knightBtn = new JButton("Knight");
		knightBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				promotion_id = ValidMove.KNIGHT;
				panel.setVisible(false);
			}
		});
		panel.add(knightBtn);
		while (promotion_id == -1)
			JOptionPane.showMessageDialog(null, panel, "Pawn Promotion : ", JOptionPane.PLAIN_MESSAGE);

		return promotion_id;
	}

}
