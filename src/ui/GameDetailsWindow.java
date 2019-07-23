package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import chess_engine.ChessEngine;
import chess_engine.entities.BoardElement;
import chess_engine.structs.OnTurnCompleteListener;
import javax.swing.BoxLayout;

public class GameDetailsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ChessWindow cw;

	private JButton btnApplySettings = new JButton("Apply Settings");
	private JCheckBox whiteCom = new JCheckBox("white is computer");
	private JCheckBox blackCom = new JCheckBox("black is computer");

	/**
	 * Create the frame.
	 */
	public GameDetailsWindow(ChessEngine cd, int x, int y, int w, int h) {

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(x, y, 407, 459);
		DefaultListModel<String> history = new DefaultListModel<>();
		DefaultListModel<String> white = new DefaultListModel<>();
		DefaultListModel<String> black = new DefaultListModel<>();
		ButtonGroup rgroup = new ButtonGroup();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 20, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 14, 204, 23, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel label = new JLabel("White's Moves");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);

		JLabel label_1 = new JLabel("Black's Moves");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 0;
		panel_1.add(label_1, gbc_label_1);

		JLabel label_2 = new JLabel("Turn History");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 0;
		panel_1.add(label_2, gbc_label_2);

		JScrollPane scrollPane = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);

		JList<String> whitelist = new JList<>(white);
		scrollPane.setViewportView(whitelist);

		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);

		JList<String> blacklist = new JList<>(black);
		scrollPane_1.setViewportView(blacklist);

		JScrollPane scrollPane_2 = new JScrollPane((Component) null);
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_2.gridx = 2;
		gbc_scrollPane_2.gridy = 1;
		panel_1.add(scrollPane_2, gbc_scrollPane_2);

		JList<String> historylist = new JList<>(history);
		scrollPane_2.setViewportView(historylist);

		JButton whiteBTN = new JButton("Play Selected");
		whiteBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cd.performMove(whitelist.getSelectedValue());
				cw.refreshTiles();
			}
		});
		GridBagConstraints gbc_whiteBTN = new GridBagConstraints();
		gbc_whiteBTN.insets = new Insets(0, 0, 0, 5);
		gbc_whiteBTN.gridx = 0;
		gbc_whiteBTN.gridy = 2;
		panel_1.add(whiteBTN, gbc_whiteBTN);

		JButton blackBTN = new JButton("Play Selected");
		blackBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cd.performMove(blacklist.getSelectedValue());
				cw.refreshTiles();
			}
		});
		blackBTN.setEnabled(false);
		GridBagConstraints gbc_blackBTN = new GridBagConstraints();
		gbc_blackBTN.insets = new Insets(0, 0, 0, 5);
		gbc_blackBTN.gridx = 1;
		gbc_blackBTN.gridy = 2;
		panel_1.add(blackBTN, gbc_blackBTN);

		JButton button_2 = new JButton("Copy PGN");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cd.copyPGNHistoryToClipboard();
			}
		});
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 2;
		panel_1.add(button_2, gbc_button_2);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		contentPane.add(panel_6, gbc_panel_6);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Sylfaen", Font.PLAIN, 20));
		panel_6.add(lblSettings);

		Component verticalStrut = Box.createVerticalStrut(20);
		panel_6.add(verticalStrut);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		contentPane.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 64, 0 };
		gbl_panel_3.rowHeights = new int[] { 23, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JLabel lblNewLabel = new JLabel("Render Mode");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);

		JRadioButton renderWhite = new JRadioButton("white");
		renderWhite.setSelected(true);
		GridBagConstraints gbc_renderWhite = new GridBagConstraints();
		gbc_renderWhite.insets = new Insets(0, 0, 5, 0);
		gbc_renderWhite.gridx = 0;
		gbc_renderWhite.gridy = 1;
		panel_3.add(renderWhite, gbc_renderWhite);

		JRadioButton renderBlack = new JRadioButton("black");
		GridBagConstraints gbc_renderBlack = new GridBagConstraints();
		gbc_renderBlack.gridx = 0;
		gbc_renderBlack.gridy = 2;
		panel_3.add(renderBlack, gbc_renderBlack);

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblPlayerAs = new JLabel("Adjust Players");
		lblPlayerAs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPlayerAs = new GridBagConstraints();
		gbc_lblPlayerAs.insets = new Insets(0, 0, 5, 0);
		gbc_lblPlayerAs.gridx = 0;
		gbc_lblPlayerAs.gridy = 0;
		panel_4.add(lblPlayerAs, gbc_lblPlayerAs);

		whiteCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateButtonText();
			}
		});
		GridBagConstraints gbc_whiteCom = new GridBagConstraints();
		gbc_whiteCom.insets = new Insets(0, 0, 5, 0);
		gbc_whiteCom.gridx = 0;
		gbc_whiteCom.gridy = 1;
		panel_4.add(whiteCom, gbc_whiteCom);

		GridBagConstraints gbc_blackCom = new GridBagConstraints();
		gbc_blackCom.gridx = 0;
		gbc_blackCom.gridy = 2;
		blackCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateButtonText();
			}
		});
		panel_4.add(blackCom, gbc_blackCom);

		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 4;
		contentPane.add(panel_5, gbc_panel_5);

		rgroup.add(renderBlack);
		rgroup.add(renderWhite);

		btnApplySettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cw.renderMode(renderBlack.isSelected());

				if (whiteCom.isSelected() && blackCom.isSelected()) {
					cw.setUpCom(-1);
				}

				if (blackCom.isSelected() && !whiteCom.isSelected()) {
					cw.setUpCom(BoardElement.TYPE_BLACK);

				}

				if (!blackCom.isSelected() && whiteCom.isSelected()) {
					cw.setUpCom(BoardElement.TYPE_WHITE);

				}

				if (!whiteCom.isSelected()) {
					cw.setUpHuman(BoardElement.TYPE_WHITE);
				}

				if (!blackCom.isSelected()) {
					cw.setUpHuman(BoardElement.TYPE_BLACK);
				}
			}
		});
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		panel_5.add(btnApplySettings);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_5.add(horizontalStrut);

		JButton btnRestartGame = new JButton("Restart Game");
		panel_5.add(btnRestartGame);

		ArrayList<String> pgns = cd.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
		for (String s : pgns) {
			white.addElement(s);
		}
		whitelist.setSelectedIndex(0);
		blacklist.setSelectedIndex(0);
		setVisible(true);

		OnTurnCompleteListener standard_game_listner = new OnTurnCompleteListener() {
			@Override
			public void onTurnComplete() {
				white.removeAllElements();
				black.removeAllElements();

				if (cd.getHistoryAsPGNs().size() % 2 == 0) {
					// black's turn
					history.set(history.getSize() - 1, String.format("%s. %s %s", (cd.getHistoryAsPGNs().size() / 2), cd.getHistoryAsPGNs().get(cd.getHistoryAsPGNs().size() - 2), cd.getHistoryAsPGNs().get(cd.getHistoryAsPGNs().size() - 1)));

					whiteBTN.setEnabled(true);
					blackBTN.setEnabled(false);
					ArrayList<String> pgns = cd.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
					for (String s : pgns) {
						white.addElement(s);
					}
				} else {
					// white's turn
					history.addElement(String.format("%s. %s ...", (cd.getHistoryAsPGNs().size() / 2 + 1), cd.getHistoryAsPGNs().get(cd.getHistoryAsPGNs().size() - 1)));

					blackBTN.setEnabled(true);
					whiteBTN.setEnabled(false);
					ArrayList<String> pgns = cd.getValidMovesForTypeAsPGN(BoardElement.TYPE_BLACK);
					for (String s : pgns) {
						black.addElement(s);
					}

				}
				whitelist.setSelectedIndex(0);
				blacklist.setSelectedIndex(0);
			}
		};

		cd.addOnTurnCompleteListener(standard_game_listner);

		btnRestartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				white.removeAllElements();
				black.removeAllElements();
				history.clear();
				whiteBTN.setEnabled(true);
				blackBTN.setEnabled(false);
				cw.restartGame();
				ArrayList<String> pgns = cd.getValidMovesForTypeAsPGN(BoardElement.TYPE_WHITE);
				for (String s : pgns) {
					white.addElement(s);
				}
				whitelist.setSelectedIndex(0);
				blacklist.setSelectedIndex(0);
			}
		});
	}

	private void updateButtonText() {
		if (whiteCom.isSelected() && blackCom.isSelected()) {
			btnApplySettings.setText("Automate Next Move");
		} else {
			btnApplySettings.setText("Apply Settings");
		}
	}

	public void setCW(ChessWindow cw) {
		this.cw = cw;
	}
}
