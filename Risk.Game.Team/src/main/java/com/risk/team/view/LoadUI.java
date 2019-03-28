package com.risk.team.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import com.risk.team.controller.RiskMapEdit;
import com.risk.team.controller.RiskMapRW;
import com.risk.team.controller.RiskMapVerify;
import com.risk.team.observer.Subject;
/**
 * This class loads the User Interface which allows the user 
 * to choose from the options and play the game.
 * 
 * @author Dhaval Desai
 */

public class LoadUI implements ActionListener {
	public static boolean status;
	RiskMapRW read;
	JButton CreateBtn;
	JButton LoadBtn;
	JButton editBtn,teamBtn;
	JFrame WindowFrame;
	TitledBorder border;
	JPanel WindowPanel;
	JLabel TitleLabel,ImageLabel;
	Subject subject;

	/**
	 * 
	 * This method is used to initialize the User Interface of the application.
	 */
	public void generateUI() {

		WindowFrame = new JFrame("Risk-Play!");
		ImageLabel  = new JLabel("",new ImageIcon("risk.jpg"),JLabel.CENTER);
		WindowFrame.add(ImageLabel);
		ImageLabel.setBounds(0,0,1200,700);
		WindowFrame.setVisible(true);
		WindowFrame.setSize(550, 500);
		WindowFrame.setBackground(Color.CYAN);
		WindowFrame.setLocation(400, 150);
		WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowFrame.setVisible(true);

		border = new TitledBorder("Risk - Ready for War");
		border.setTitleColor(Color.red);
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);

		WindowPanel = new JPanel();
		WindowPanel.setBorder(border);
		WindowPanel.setBackground(Color.BLUE);

		TitleLabel = new JLabel("Risk");

		TitleLabel.setForeground(Color.red);
		TitleLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

		WindowFrame.getContentPane().add(TitleLabel);
		WindowFrame.getContentPane().add(WindowPanel);
		WindowPanel.setLayout(null);

		teamBtn = new JButton("APP -- Team 17");
		WindowPanel.add(teamBtn);
		teamBtn.setForeground(new Color(67, 80, 88));
		teamBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
		teamBtn.setBounds(5, 50 , 525, 50);
		teamBtn.setBackground(Color.white);

		CreateBtn = new JButton("Create Map");
		WindowPanel.add(CreateBtn);
		CreateBtn.setForeground(new Color(67, 80, 88));
		CreateBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		CreateBtn.setBounds(220, 178, 125, 21);
		CreateBtn.addActionListener(this);
		CreateBtn.setBackground(Color.red);

		editBtn = new JButton("Edit Map");
		WindowPanel.add(editBtn);
		editBtn.setForeground(new Color(67, 80, 88));
		editBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		editBtn.setBounds(220, 140, 125, 21);
		editBtn.addActionListener(this);
		editBtn.setBackground(Color.red);

		LoadBtn = new JButton("Load map");
		WindowPanel.add(LoadBtn);
		LoadBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		LoadBtn.setForeground(new Color(67, 80, 88));
		LoadBtn.setBackground(UIManager.getColor("Button.highlight"));
		LoadBtn.setBounds(220, 214, 125, 21);
		LoadBtn.addActionListener((ActionListener) this);
		LoadBtn.setBackground(Color.red);

	}

	/** 
	 * This method takes an event as an input and performs its associated action depending on the
	 * event
	 * @param e Action event object.
	 */
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();

		RiskMapRW readMap = null;
		if (e.getSource() == editBtn) {
			JFileChooser filechooser = new JFileChooser();
			FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("map files (*.map)", "map");
			filechooser.addChoosableFileFilter(mapFilter);
			filechooser.setFileFilter(mapFilter);
			filechooser.setDialogTitle("Select a Map File ");
			filechooser.setBackground(Color.green);
			int selectedFile = filechooser.showOpenDialog(null);
			if (selectedFile == JFileChooser.APPROVE_OPTION) {
				File selectedFile2 =  filechooser.getSelectedFile();
				String fileName = selectedFile2.getName();
				RiskMapVerify mapVerification = new RiskMapVerify();
				if (mapVerification.verifyMapFile(fileName)) {
					readMap = new RiskMapRW(mapVerification);
					new RiskMapEdit(((RiskMapRW) readMap).readMapFile()).createEditMap(false);
					read=readMap;
				}
			}
		} 
		else if (e.getSource() == LoadBtn){

			JFileChooser filechooser = new JFileChooser();
			FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("map files (*.map)", "map");
			filechooser.addChoosableFileFilter(mapFilter);
			filechooser.setFileFilter(mapFilter);
			filechooser.setDialogTitle("Select a Map File");
			filechooser.setBackground(Color.green);
			int selectedFile = filechooser.showOpenDialog(null);
			if (selectedFile == JFileChooser.APPROVE_OPTION) {
				File selectedFile2 =  filechooser.getSelectedFile();
				String fileName = selectedFile2.getName();
				RiskMapVerify mapVerification = new RiskMapVerify();
				if (mapVerification.verifyMapFile(fileName)) {
					readMap = new RiskMapRW(mapVerification);
					this.read=readMap;
					status = true;
				}
			}
		}

		else {

			RiskMapEdit mapEditor = new RiskMapEdit();
			mapEditor.createEditMap(true);
			read=readMap;
		}

		if(status) {
			System.out.println("Do you want to play the game?(Select : true or false)");
			Scanner scan = new Scanner(System.in);
			if(Boolean.parseBoolean(scan.nextLine())) {
				subject = new Subject();
				GamePhaseView gamePhaseView= new GamePhaseView(subject);
				gamePhaseView.gamePlay(read);
			}
		}
	}
}
