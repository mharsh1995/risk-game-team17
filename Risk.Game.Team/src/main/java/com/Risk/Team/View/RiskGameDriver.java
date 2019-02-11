package com.Risk.Team.View;

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

//import com.risk.services.MapEditor;
//import com.risk.services.MapIO;
//import com.risk.services.MapValidate;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

//import Risk.Game.Team.View.Map;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Launches the Game and provides the main window and the view for the user, 
 * either to load or create a new map.
 *
 * @author Harsh Mehta
 */

public class RiskGameDriver extends JFrame implements ActionListener {
	//private static Logger LOGGER = LogManager.getLogger();
	private static final long serialVersionUID = 1L;
	private static final String LOGGER1 = null;
	private JButton StartBtn;
	private JButton EndBtn;
	private JButton CreateBtn;
	private JButton LoadBtn;
	private JButton EditBtn;
	private JButton SeriesBtn;
	private JFrame WindowFrame;
	private TitledBorder border;
	private JPanel WindowPanel;
	private JLabel TitleLabel;

	public RiskGameDriver () {
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

		WindowFrame = new JFrame("Risk-Play!");
		WindowFrame.setVisible(true);

		WindowFrame.setSize(550, 550);
		WindowFrame.setLocation(500, 200);
		WindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		border = new TitledBorder("Risk - Ready for War");
		border.setTitleJustification(TitledBorder.CENTER);
		border.setTitlePosition(TitledBorder.TOP);

		WindowPanel = new JPanel();
		WindowPanel.setBorder(border);

		TitleLabel = new JLabel("Risk");

		TitleLabel.setForeground(Color.red);
		TitleLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

		WindowFrame.getContentPane().add(TitleLabel);
		WindowFrame.getContentPane().add(WindowPanel);
		WindowPanel.setLayout(null);
		
		//StartBtn = new JButton("Start Game");
		//WindowPanel.add(StartBtn);
		//StartBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		//StartBtn.setBackground(UIManager.getColor("Button.highlight"));
		//StartBtn.setForeground(new Color(67, 80, 88));
		//StartBtn.setBounds(220, 102, 125, 21);
		//StartBtn.addActionListener(this);
		
		CreateBtn = new JButton("Create Map");
		WindowPanel.add(CreateBtn);
		CreateBtn.setForeground(new Color(67, 80, 88));
		CreateBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		CreateBtn.setBounds(220, 178, 125, 21);
		CreateBtn.addActionListener(this);

		LoadBtn = new JButton("Load map");
		WindowPanel.add(LoadBtn);
		LoadBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		LoadBtn.setForeground(new Color(67, 80, 88));
		LoadBtn.setBackground(UIManager.getColor("Button.highlight"));								
		LoadBtn.setBounds(220, 214, 125, 21);
		LoadBtn.addActionListener(this);

		//EditBtn = new JButton("Edit map");
		//WindowPanel.add(EditBtn);
		//EditBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		//EditBtn.setForeground(new Color(67, 80, 88));
		//EditBtn.setBackground(UIManager.getColor("Button.highlight"));								
		//EditBtn.setBounds(220, 250, 125, 21);
		//EditBtn.addActionListener(this);

		EndBtn = new JButton("Exit");
		WindowPanel.add(EndBtn);
		EndBtn.setForeground(new Color(67, 80, 88));
		EndBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		EndBtn.setBounds(220, 407, 125, 21);
		EndBtn.addActionListener(this);

	}
	public static void main(String[] args) {
		RiskGameDriver exp = new RiskGameDriver();

	}


	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
		
			Object readMap;
			if(e.getSource()==LoadBtn) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select a Map File");
				fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
				File selectedFile = fileChooser.showOpenDialog(null);
				if (selectedFile != null) {
					LoadBtn.getName().getWindow().hide();
					String fileName = selectedFile.getAbsolutePath();
					System.out.println("File location: " + fileName);
					MapValidate mapValidate = new MapValidate();
					if (mapValidate.validateMapFile(fileName)) {
						readMap = new MapIO(mapValidate);
						new MapEditor(readMap.readFile()).editExistingMap();
					}
				}
			}
			else {
				CreateBtn.getName().getWindow().hide();
				MapEditor mapEditor = new MapEditor();
				mapEditor.createNewMap();
				readMap = mapEditor.getMapIO();
			}
		
		
		//if (e.getSource() == StartBtn) {
		//	LOGGER.info("####  startGameButton is clicked ####");
		//	WindowFrame.setVisible(false);
		//	Map map = new Map();
		//	map.setVisible(true);

		
		//} else if (e.getSource() == EndBtn) {
	//		System.exit(0);
	//	}

	}
}