package Risk.Game.Team.View;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import Risk.Game.Team.View.Launcher;


/* This class represent Menu to start and Exit game */

public class Launcher extends JFrame implements ActionListener {
	
	private JButton StartGameButton;
	private JButton EndGameButton;
	private JButton CreateMapButton;
	private JButton EditMapButton;
	private JButton SeriesButton;
	private JFrame GameFrame;
	private TitledBorder border;
	private JPanel GamePanel;
	private JLabel TitleLabel;
	
	public Launcher () {
		try {
		Strt();
		}
	catch(Exception ex)
	{
		System.out.println ("error loading");	
	}
	}
	
	/* This scope is used for the Editing Buttons */ 
	
	
	public void Strt() {
		
		GameFrame = new JFrame("Risk Game");
		GameFrame.setVisible(true);

		GameFrame.setSize(500, 500);
		GameFrame.setLocation(500, 200);
		GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		border = new TitledBorder("Risk Game");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);

		GamePanel = new JPanel();
		GamePanel.setBorder(border);

		TitleLabel = new JLabel("Risk Game");

		TitleLabel.setForeground(Color.red);
		TitleLabel.setFont(new Font("TimesRoman", Font.BOLD, 14));

		GameFrame.getContentPane().add(TitleLabel);
		GameFrame.getContentPane().add(GamePanel);
		GamePanel.setLayout(null);
		StartGameButton = new JButton("Start Game");
		GamePanel.add(StartGameButton);
		StartGameButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
		StartGameButton.setBackground(UIManager.getColor("Button.highlight"));
		StartGameButton.setForeground(new Color(102, 0, 0));
		StartGameButton.setForeground(new Color(102, 0, 0));

		StartGameButton.setBounds(186, 102, 121, 21);
		StartGameButton.addActionListener(this);
		CreateMapButton = new JButton("Create Map");
		GamePanel.add(CreateMapButton);
		CreateMapButton.setForeground(new Color(102, 0, 0));
		CreateMapButton.setFont(new Font("TimesRomen", Font.BOLD, 15));

		CreateMapButton.setBounds(186, 158, 121, 21);
		CreateMapButton.addActionListener(this);
		EditMapButton = new JButton("Edit map");
		GamePanel.add(EditMapButton);
		EditMapButton.setFont(new Font("TimesRoman", Font.BOLD, 15));
		EditMapButton.setForeground(new Color(102, 0, 0));
		EditMapButton.setBackground(UIManager.getColor("Button.highlight"));

		EditMapButton.setBounds(186, 214, 121, 21);
		EditMapButton.addActionListener(this);

		EndGameButton = new JButton("Exit");
		GamePanel.add(EndGameButton);
		EndGameButton.setForeground(new Color(102, 0, 0));
		EndGameButton.setFont(new Font("TimesRoman", Font.BOLD, 15));

		EndGameButton.setBounds(186, 371, 121, 21);
		EndGameButton.addActionListener(this);

	}
	public static void main(String[] args) {
		Launcher exp = new Launcher();

	}

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	}

