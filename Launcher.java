import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
*This file will launch the game with Jframe
*@author yashgolwala
*/

/*This class represent Menu to start and Exit game */

public class Launcher extends JFrame implements ActionListener {

	/* Declaring variables for Buttons */
	
	private static final long serialVersionUID = 1L;
	private JButton startBtn;
	private JButton endBtn;
	private JButton createBtn;
	private JButton loadBtn;
	private JButton editBtn;
	private JButton seriesBtn;
	private JFrame windowFrame;
	private TitledBorder border;
	private JPanel windowPanel;
	private JLabel titleLabel;

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

		windowFrame = new JFrame("Risk-Play!");
		windowFrame.setVisible(true);

		windowFrame.setSize(550, 550);
		windowFrame.setLocation(500, 200);
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		border = new TitledBorder("Risk - Ready for War");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);

		windowPanel = new JPanel();
		windowPanel.setBorder(border);

		titleLabel = new JLabel("Risk");

		titleLabel.setForeground(Color.red);
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

		windowFrame.getContentPane().add(TitleLabel);
		windowFrame.getContentPane().add(WindowPanel);
		windowPanel.setLayout(null);
		
		startBtn = new JButton("Start Game");
		windowPanel.add(StartBtn);
		startBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		startBtn.setBackground(UIManager.getColor("Button.highlight"));
		startBtn.setForeground(new Color(67, 80, 88));
		startBtn.setBounds(220, 102, 125, 21);
		startBtn.addActionListener(this);
		
		createBtn = new JButton("Create Map");
		windowPanel.add(CreateBtn);
		createBtn.setForeground(new Color(67, 80, 88));
		createBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		createBtn.setBounds(220, 178, 125, 21);
		createBtn.addActionListener(this);

		loadBtn = new JButton("Load map");
		windowPanel.add(LoadBtn);
		loadBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		loadBtn.setForeground(new Color(67, 80, 88));
		loadBtn.setBackground(UIManager.getColor("Button.highlight"));								
		loadBtn.setBounds(220, 214, 125, 21);
		loadBtn.addActionListener(this);

		editBtn = new JButton("Edit map");
		windowPanel.add(EditBtn);
		editBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		editBtn.setForeground(new Color(67, 80, 88));
		editBtn.setBackground(UIManager.getColor("Button.highlight"));								
		editBtn.setBounds(220, 250, 125, 21);
		editBtn.addActionListener(this);

		endBtn = new JButton("Exit");
		windowPanel.add(EndBtn);
		endBtn.setForeground(new Color(67, 80, 88));
		endBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		endBtn.setBounds(220, 407, 125, 21);
		endBtn.addActionListener(this);

	}
	public static void main(String[] args) {
		Launcher exp = new Launcher();

	}


	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


}

