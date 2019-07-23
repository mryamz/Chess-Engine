package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import genetic_algorithm.Chromosome;

public class GeneEditorWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField newAlleleValue;
	private JRadioButton rdbtnEditWhite = new JRadioButton("Edit White");
	private JRadioButton rdbtnEditBlack = new JRadioButton("Edit Black");
	private JSlider slider = new JSlider();
	private JLabel lblGeneSelecter = new JLabel("Gene: ");
	private DefaultListModel<Double> genes = new DefaultListModel<>();
	private Chromosome black_chromosome, white_chromosome;
	private ChessWindow cw;
	private JList<Double> list = new JList<Double>(genes);

	public GeneEditorWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		Component verticalGlue_2 = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue_2 = new GridBagConstraints();
		gbc_verticalGlue_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalGlue_2.gridx = 0;
		gbc_verticalGlue_2.gridy = 0;
		contentPane.add(verticalGlue_2, gbc_verticalGlue_2);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 20, 0, 250, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblWhitesTraitsEncoded = new JLabel("Edit the Chromosome");
		lblWhitesTraitsEncoded.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblWhitesTraitsEncoded = new GridBagConstraints();
		gbc_lblWhitesTraitsEncoded.insets = new Insets(0, 0, 5, 0);
		gbc_lblWhitesTraitsEncoded.gridx = 0;
		gbc_lblWhitesTraitsEncoded.gridy = 1;
		panel.add(lblWhitesTraitsEncoded, gbc_lblWhitesTraitsEncoded);

		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(list.isSelectionEmpty())
					return;
				lblGeneSelecter.setText(String.format("Gene: %s", list.getSelectedIndex()));
				newAlleleValue.setText(("") + getSelectedChromo().getAllele(list.getSelectedIndex()));
				slider.setValue(list.getSelectedIndex());
			}
		});

		JLabel lblNewLabel = new JLabel("This chromosome encodes weights, and biases in the pattern w[n] b[n+1]");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.HORIZONTAL;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;

		panel.add(list, gbc_list);
		JScrollPane scrollPane = new JScrollPane(list);

		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		panel.add(scrollPane, gbc_scrollPane);

		GridBagConstraints gbc_lblGeneSelecter = new GridBagConstraints();
		gbc_lblGeneSelecter.insets = new Insets(0, 0, 5, 0);
		gbc_lblGeneSelecter.gridx = 0;
		gbc_lblGeneSelecter.gridy = 4;
		panel.add(lblGeneSelecter, gbc_lblGeneSelecter);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 5;
		panel.add(slider, gbc_slider);

		slider.setMaximum(Chromosome.LENGTH - 1);

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblGeneSelecter.setText(String.format("Gene: %s", slider.getValue()));
				newAlleleValue.setText(("") + getSelectedChromo().getAllele(slider.getValue()));
				list.setSelectedIndex(slider.getValue());

			}
		});

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 6;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JLabel lblGeneX = new JLabel("Allele");
		panel_1.add(lblGeneX);

		newAlleleValue = new JTextField();
		panel_1.add(newAlleleValue);
		newAlleleValue.setColumns(10);

		JButton btnUpdateAllele = new JButton("Update Allele");
		btnUpdateAllele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double value = Double.parseDouble(newAlleleValue.getText());
				genes.set(list.getSelectedIndex(), value);
			}
		});
		panel_1.add(btnUpdateAllele);

		Component verticalGlue = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.insets = new Insets(0, 0, 5, 0);
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 2;
		contentPane.add(verticalGlue, gbc_verticalGlue);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		contentPane.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		rdbtnEditWhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeSelection();
			}
		});

		rdbtnEditWhite.setSelected(true);
		panel_2.add(rdbtnEditWhite);
		rdbtnEditBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeSelection();
			}
		});
		panel_2.add(rdbtnEditBlack);

		ButtonGroup rgroup = new ButtonGroup();
		rgroup.add(rdbtnEditWhite);
		rgroup.add(rdbtnEditBlack);

		JButton btnSaveChangesTo = new JButton("Save changes to the Chromosome");
		btnSaveChangesTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < Chromosome.LENGTH; i++) {
					getSelectedChromo().setAllele(i, genes.get(i));
				}
				if (rdbtnEditBlack.isSelected()) {
					cw.setBlackCom(getSelectedChromo().getPerceptron());
				} else {
					cw.setWhiteCom(getSelectedChromo().getPerceptron());
				}
			}
		});
		GridBagConstraints gbc_btnSaveChangesTo = new GridBagConstraints();
		gbc_btnSaveChangesTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveChangesTo.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveChangesTo.gridx = 0;
		gbc_btnSaveChangesTo.gridy = 4;
		contentPane.add(btnSaveChangesTo, gbc_btnSaveChangesTo);

		JButton btnNewButton = new JButton("View as Phenotype (Neural Network)");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue_1 = new GridBagConstraints();
		gbc_verticalGlue_1.gridx = 0;
		gbc_verticalGlue_1.gridy = 6;
		contentPane.add(verticalGlue_1, gbc_verticalGlue_1);
	}

	public void setData(ChessWindow cw, Chromosome black_chromosome, Chromosome white_chromosome) {
		this.black_chromosome = black_chromosome;
		this.white_chromosome = white_chromosome;
		this.cw = cw;
		
		// handle initial set up
		changeSelection();
	}
	
	private void changeSelection() {
		setTitle(String.format("%s's Traits Encoded as Chromosome", rdbtnEditWhite.isSelected() ? "White" : "Black"));
		genes.removeAllElements();
		Chromosome ref = getSelectedChromo();
		for (int i = 0; i < Chromosome.LENGTH; i++) {
			genes.addElement(ref.getAllele(i));
		}
		list.setSelectedIndex(slider.getValue());
	}

	private Chromosome getSelectedChromo() {
		return rdbtnEditWhite.isSelected() ? white_chromosome : black_chromosome;
	}
}
