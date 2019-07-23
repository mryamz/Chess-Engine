package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import genetic_algorithm.Chromosome;
import perceptron.MultilayerPerceptron;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.ScrollPane;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.Button;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.ListModel;

public class GeneEditorWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField newAlleleValue;
	private JRadioButton rdbtnEditWhite = new JRadioButton("Edit White");
	private JRadioButton rdbtnEditBlack = new JRadioButton("Edit Black");
	private JSlider slider = new JSlider();
	private JLabel lblGeneSelecter = new JLabel("Gene: ");
	private DefaultListModel<Double> genes = new DefaultListModel<>();
	private MultilayerPerceptron white, black;
	private Chromosome black_chromosome, white_chromosome;

	public GeneEditorWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 20, 75, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblWhitesTraitsEncoded = new JLabel("Edit the Chromosome");
		lblWhitesTraitsEncoded.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblWhitesTraitsEncoded = new GridBagConstraints();
		gbc_lblWhitesTraitsEncoded.insets = new Insets(0, 0, 5, 0);
		gbc_lblWhitesTraitsEncoded.gridx = 0;
		gbc_lblWhitesTraitsEncoded.gridy = 0;
		panel.add(lblWhitesTraitsEncoded, gbc_lblWhitesTraitsEncoded);

		JList<Double> list = new JList<Double>(genes);
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
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);

		GridBagConstraints gbc_lblGeneSelecter = new GridBagConstraints();
		gbc_lblGeneSelecter.insets = new Insets(0, 0, 5, 0);
		gbc_lblGeneSelecter.gridx = 0;
		gbc_lblGeneSelecter.gridy = 2;
		panel.add(lblGeneSelecter, gbc_lblGeneSelecter);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 3;
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
		gbc_panel_1.gridy = 4;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JLabel lblGeneX = new JLabel("Allele");
		panel_1.add(lblGeneX);

		newAlleleValue = new JTextField();
		panel_1.add(newAlleleValue);
		newAlleleValue.setColumns(10);

		JButton btnUpdateAllele = new JButton("Update Allele");
		panel_1.add(btnUpdateAllele);

		Component verticalGlue = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.insets = new Insets(0, 0, 5, 0);
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 1;
		contentPane.add(verticalGlue, gbc_verticalGlue);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		contentPane.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		rdbtnEditWhite.setSelected(true);
		panel_2.add(rdbtnEditWhite);
		panel_2.add(rdbtnEditBlack);

		ButtonGroup rgroup = new ButtonGroup();
		rgroup.add(rdbtnEditWhite);
		rgroup.add(rdbtnEditBlack);

		JButton btnNewButton = new JButton("View as Phenotype (Neural Network)");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

	public void setData(MultilayerPerceptron white, MultilayerPerceptron black, Chromosome black_chromosome, Chromosome white_chromosome) {
		this.white = white;
		this.black = black;
		this.black_chromosome = black_chromosome;
		this.white_chromosome = white_chromosome;

		// handle initial set up
		setTitle(String.format("%s's Traits Encoded as Chromosome", rdbtnEditWhite.isSelected() ? "White" : "Black"));
		genes.clear();
		Chromosome ref = getSelectedChromo();
		for (int i = 0; i < Chromosome.LENGTH; i++) {
			genes.addElement(ref.getAllele(i));
		}
	}

	private Chromosome getSelectedChromo() {
		return rdbtnEditWhite.isSelected() ? white_chromosome : black_chromosome;
	}
}
