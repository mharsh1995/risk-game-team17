package com.Risk.Team.View;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.awt.Button;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;


import com.Risk.Team.Controller.MapEditor;
import com.Risk.Team.Controller.MapIO;
import com.Risk.Team.Controller.MapValidate;
import com.Risk.Team.model.Continent;
import com.Risk.Team.model.Country;
import com.Risk.Team.model.Player;
import com.Risk.Team.Controller.StartUpPhase;
import com.Risk.Team.Controller.Gameplay.FortificationPhase;
import com.Risk.Team.Controller.Gameplay.ReinforcementPhase;
import com.Risk.Team.Controller.Gameplay.RoundRobin;



//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Launches the Game and provides the main window and the view for the user,
 * either to load or create a new map.
 *
 * @author Harsh Mehta
 * @author Yash Golwala
 */

public class RiskGameDriver extends JFrame implements ActionListener {
    //private static Logger LOGGER = LogManager.getLogger();
	//private static final long serialVersionUID = 1L;
	//private static final String LOGGER11 = null;
	//private static final Logger LOGGER1 = null;
	public static boolean status;
	private JButton CreateBtn;
	private JButton LoadBtn;
	private JButton SeriesBtn;
	private JFrame WindowFrame;
	private TitledBorder border;
	private JPanel WindowPanel;
	private JLabel TitleLabel;
	MapIO read;

	public RiskGameDriver() {
		try {
			Strt();
		} catch (Exception ex) {
			System.out.println("error loading");
		}
	}

	public void Strt() {

		WindowFrame = new JFrame("Risk-Play!");
		WindowFrame.setVisible(true);

		WindowFrame.setSize(550, 500);
		WindowFrame.setLocation(400, 150);
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

		CreateBtn = new JButton("Create Map");
		WindowPanel.add(CreateBtn);
		CreateBtn.setForeground(new Color(67, 80, 88));
		CreateBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		CreateBtn.setBounds(220, 178, 125, 21);
		CreateBtn.addActionListener(this);

		LoadBtn = new JButton("Edit Map");
		WindowPanel.add(LoadBtn);
		LoadBtn.setFont(new Font("Monospaced", Font.BOLD, 15));
		LoadBtn.setForeground(new Color(67, 80, 88));
		LoadBtn.setBackground(UIManager.getColor("Button.highlight"));
		LoadBtn.setBounds(220, 214, 125, 21);
		LoadBtn.addActionListener(this);

	}

	public static void main(String[] args) {
		RiskGameDriver exp = new RiskGameDriver();

	}
@Override
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();

		Object readMap = null;
		if (e.getSource() == LoadBtn) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a Map File");
			fileChooser.getExtensionFilters()
					.add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				//LoadBtn.getScene().getWindow().hide();
				String fileName = selectedFile.getAbsolutePath();
				System.out.println("File location: " + fileName);
				MapValidate mapValidate = new MapValidate();
				if (mapValidate.validateMapFile(fileName)) {
					readMap = new MapIO(mapValidate);
					new MapEditor(((MapIO) readMap).readFile()).editExistingMap();
				}
			}
		} else {
			
			MapEditor mapEditor = new MapEditor();
			mapEditor.createNewMap();
			readMap = mapEditor.getMapIO();
		}
		if(status) {
			System.out.println("Do you want to play the game?(true or false)");
			Scanner scan = new Scanner(System.in);
			if(Boolean.parseBoolean(scan.nextLine())) {
				gamePlay(read);
			}
		}
	}
    private void gamePlay(MapIO mapIO) {
		Scanner scan = new Scanner(System.in);

		System.out.println("\nBeginning Startup Phase.\n");
		StartUpPhase StartUpPhase = new StartUpPhase(mapIO);
		((com.Risk.Team.Controller.StartUpPhase) StartUpPhase).countryAllocation();
		((com.Risk.Team.Controller.StartUpPhase) StartUpPhase).armyAllocationToPlayers();
		((com.Risk.Team.Controller.StartUpPhase) StartUpPhase).initialArmyAllocationToCountries();
		((com.Risk.Team.Controller.StartUpPhase) StartUpPhase).balanceArmyAllocationToCountries();

		int turn = 1;
		com.Risk.Team.Controller.Gameplay.RoundRobin roundRobin = new com.Risk.Team.Controller.Gameplay.RoundRobin(((com.Risk.Team.Controller.StartUpPhase) StartUpPhase).getListOfPlayers());

		while(turn <= ((com.Risk.Team.Controller.StartUpPhase) StartUpPhase).getListOfPlayers().size()) {
			Player player = roundRobin.next();
			System.out.println("Beginning Reinforcement phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Reinforcement phase? (Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				Continent continent = player.getMyCountries().get(player.getMyCountries().size()-1).getPartOfContinent();
				int balanceArmyCount = (new ReinforcementPhase()).findNoOfArmies(player, continent);
				System.out.println("Armies received for reinforcement: " + balanceArmyCount);
				player.setArmyCount(player.getArmyCount() + balanceArmyCount);
				for(Country country: player.getMyCountries()) {
					if(player.getArmyCount()>0) {
						System.out.println("Number of armies currently assigned to country " + country.getName() + " :" + country.getNoOfArmies());
						System.out.println("Total number of armies available:" + player.getArmyCount());
						System.out.println("Enter number of armies to assign to country " + country.getName() + " :");
						try {
							int assignArmies = Integer.parseInt(scan.nextLine());
							player.addArmiesToCountry(country, assignArmies);
						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
						}
					}
					else {
						System.out.println("You do not have sufficient number of armies available.");
						break;
					}
				}
			}

			System.out.println("Beginning Fortification phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Fortification phase? (Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				if(player.getMyCountries().size()>=2) {
					boolean flag = true;
					String giverCountry = "";
					String receiverCountry = "";

					System.out.println("List of countries owned by you and current army assigned are: ");
					for(Country ownedCountry: player.getMyCountries()) {
						System.out.println(ownedCountry.getName() + ":" + ownedCountry.getNoOfArmies());
					}
					do {
						flag=true;
						System.out.println("Enter the name of country from which you want to move some armies :");
						giverCountry = scan.nextLine();
						System.out.println("Enter the name of country to which you want to move some armies, from country " + giverCountry);
						receiverCountry = scan.nextLine();
						if(!((MapIO) mapIO).getMapGraph().getCountrySet().containsKey(giverCountry.trim()) || !((MapIO) mapIO).getMapGraph().getCountrySet().containsKey(receiverCountry.trim())) {
							flag=false;
							System.out.println("Please enter correct country name.");
						}
						Country givingCountry = ((MapIO) mapIO).getMapGraph().getCountrySet().get(giverCountry.trim());
						Country receviningCountry = ((MapIO) mapIO).getMapGraph().getCountrySet().get(giverCountry.trim());
						if(player.getMyCountries().contains(givingCountry) && player.getMyCountries().contains(receviningCountry)){
							flag = true;
						}
						else {
							System.out.println("Player does not own these country, please enter country names again");
							flag = false;
						}

					}while(flag==false);


					int countOfArmies = 0;
					do {
						flag=true;
						System.out.println("Enter the number of armies to move from " + giverCountry + " to " + receiverCountry);
						try {
							countOfArmies = Integer.parseInt(scan.nextLine());
							if(countOfArmies > ((MapIO) mapIO).getMapGraph().getCountrySet().get(giverCountry).getNoOfArmies()) {
								System.out.println("Sufficient number of armies is not available.");
								flag=false;
							}

						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
						}
					}while(flag==false);

					(new FortificationPhase()).moveArmies(((MapIO) mapIO).getMapGraph().getCountrySet().get(giverCountry), ((MapIO) mapIO).getMapGraph().getCountrySet().get(receiverCountry), countOfArmies);
				}
				else {
					System.out.println("You don't have sufficient number of countries to choose from.");
				}
			}
			turn++;
		}
	}
}
