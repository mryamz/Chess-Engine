package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chess_engine.entities.Chessboard;
import chess_engine.visualization.Heatmap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChessboardDataVisualizationWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private Heatmap map;
	private Chessboard board;

	/**
	 * Create the application.
	 */
	public ChessboardDataVisualizationWindow(Chessboard board) {
		initialize();
		map = new Heatmap(board);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 500, 600);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel root = new JPanel();
		getContentPane().add(root, BorderLayout.CENTER);
		GridBagLayout gbl_root = new GridBagLayout();
		gbl_root.columnWidths = new int[] { 0, 0 };
		gbl_root.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_root.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_root.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		root.setLayout(gbl_root);

		JLabel lblHeatmap = new JLabel("Heatmap of Pressure (This isn't correct yet, since valid moves are layered instead of pressure)");
		GridBagConstraints gbc_lblHeatmap = new GridBagConstraints();
		gbc_lblHeatmap.insets = new Insets(0, 0, 5, 0);
		gbc_lblHeatmap.gridx = 0;
		gbc_lblHeatmap.gridy = 0;
		root.add(lblHeatmap, gbc_lblHeatmap);

		JPanel draw_panel = new JPanel();
		GridBagConstraints gbc_draw_panel = new GridBagConstraints();
		gbc_draw_panel.insets = new Insets(0, 0, 5, 0);
		gbc_draw_panel.fill = GridBagConstraints.BOTH;
		gbc_draw_panel.gridx = 0;
		gbc_draw_panel.gridy = 1;
		root.add(draw_panel, gbc_draw_panel);

		JButton btnUpdateMap = new JButton("update map");
		btnUpdateMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int w = draw_panel.getWidth();
				int h = draw_panel.getHeight();
				int n = Math.min(w, h);
				if (draw_panel != null)
					draw_panel.getGraphics().drawImage(map.grabImage(n, n), 0, 0, null);
			}
		});
		GridBagConstraints gbc_btnUpdateMap = new GridBagConstraints();
		gbc_btnUpdateMap.gridx = 0;
		gbc_btnUpdateMap.gridy = 2;
		root.add(btnUpdateMap, gbc_btnUpdateMap);
	}

	public void setBoard(Chessboard board) {
		this.board = board;
	}
}
