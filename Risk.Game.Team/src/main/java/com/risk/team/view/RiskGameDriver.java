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

import com.risk.team.controller.RiskLaunchPhase;
import com.risk.team.controller.RiskMapEdit;
import com.risk.team.controller.RiskMapRW;
import com.risk.team.controller.RiskMapVerify;
import com.risk.team.controller.gamephase.Fortification;
import com.risk.team.controller.gamephase.Reinforcement;
import com.risk.team.controller.gamephase.RiskRoundRobin;
import com.risk.team.model.Continent;
import com.risk.team.model.Country;
import com.risk.team.model.Player;


/**
 * Launches the Game and provides the main window and the view for the user,
 * to load, Create and Edit a map.
 *
 * @author Harsh Mehta
 * @author Yash Golwala
 */

public class RiskGameDriver extends JFrame implements ActionListener {

	public static boolean status;
	private JButton CreateBtn;
	private JButton LoadBtn;
	private JButton editBtn,teamBtn;
	private JFrame WindowFrame;
	private TitledBorder border;
	private JPanel WindowPanel;
	private JLabel TitleLabel,ImageLabel;
	RiskMapRW read;

	/**
	 * This method launches the game and displays the start menu
	 */
	public RiskGameDriver() {
		try {
			Strt();
		} catch (Exception ex) {
			System.out.println("error loading");
		}
	}
	/**
	 * this method initialize the UI of the game
	 */

	public void Strt() {

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
		LoadBtn.addActionListener(this);
		LoadBtn.setBackground(Color.red);

	}
	/**
	 * main method
	 * 
	 * @param args String array.
	 */
	public static void main(String[] args) {
		RiskGameDriver exp = new RiskGameDriver();

	}
	/**
	 * This method takes an event as an input and performs task depending on the
	 * event
	 * @param e Action event object.
	 */
	@Override
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
			int selectedFile = filechooser.showOpenDialog(this);
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
			int selectedFile = filechooser.showOpenDialog(this);
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
				gamePlay(read);
			}
		}
	}

	/**
	 * This gamePlay method begins the startup phase and depending on the no of the turns,
	 * enters into the reinforcement phase and assigns the armies based on the input value.
	 * 
	 * @param RiskMapRW Object
	 * 
	 */
	private void gamePlay(RiskMapRW mapObj) {
		Scanner scan = new Scanner(System.in);
		System.out.println("\n**************GAME IS ON*****************\n");
		System.out.println("\nBeginning Startup Phase.\n");
		RiskLaunchPhase RiskLaunchPhase = new RiskLaunchPhase(mapObj);
		RiskLaunchPhase.getPlayerDetails();
		RiskLaunchPhase.allocateCountries();
		RiskLaunchPhase.armyAllocateToPlayer();
		RiskLaunchPhase.initialArmyToCountries();
		RiskLaunchPhase.balanceArmyToCountries();

		int turn = 1;
		RiskRoundRobin roundRobin = new RiskRoundRobin(RiskLaunchPhase.getPlayerList());

		while(turn <= (RiskLaunchPhase).getPlayerList().size()) {
			Player player = roundRobin.nextPlayer();
			System.out.println("\nBeginning Reinforcement phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Reinforcement phase? (Select : Yes or No)");
			if(scan.nextLine().trim().equalsIgnoreCase("Yes")) {
				Continent continent = player.getMyCountries().get(player.getMyCountries().size()-1).getPartOfContinent();
				int balanceArmyCount = (new Reinforcement()).assignArmies(player, continent);
				System.out.println("Armies received for reinforcement for player: " + balanceArmyCount);
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
						System.out.println("Now !! You do not have sufficient number of armies available.");
						break;
					}
				}
			}

			System.out.println("Beginning Fortification phase for player : " + player.getName() + "\n\n");
			System.out.println("Do you want to continue with Fortification phase? (Select : Yes or No)");
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
						if(!(mapObj.getMapGraph().getAllCountries().containsKey(giverCountry.trim())) || !(mapObj.getMapGraph().getAllCountries().containsKey(receiverCountry.trim()))) {
							flag=false;
							System.out.println("Please enter correct country name.");
						}
						Country givingCountry = (mapObj.getMapGraph().getAllCountries().get(giverCountry.trim()));
						Country receviningCountry = (mapObj.getMapGraph().getAllCountries().get(receiverCountry.trim()));


						if(player.getMyCountries().contains(givingCountry) && player.getMyCountries().contains(receviningCountry)){


							if(givingCountry.equals(receviningCountry)){

								System.out.println("Source and Destination Country can not be same");
								flag = false;

							}else {

								if((mapObj.getMapGraph().getAllCountries().get(giverCountry).getNoOfArmies()) <= 1)

								{
									System.out.println("Please select a giver country having armies more than one");
									flag=false;
								}
								else
								{
									flag = true;
								}
							}

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
							if(countOfArmies >= mapObj.getMapGraph().getAllCountries().get(giverCountry).getNoOfArmies()) {
								System.out.println("Sufficient number of armies is not available.");
								flag=false;
							}


						}catch(NumberFormatException e) {
							System.out.println("Invalid number of armies.");
							flag=false;
						}
					}while(flag==false);	

					(new Fortification()).moveArmies(mapObj.getMapGraph().getAllCountries().get(giverCountry), (mapObj.getMapGraph().getAllCountries().get(receiverCountry)), countOfArmies,player,mapObj.getMapGraph().getAllCountries().values());
				}
				else {
					System.out.println("You don't have sufficient number of countries to choose from.");
				}
			}
			turn++;
		}

		System.out.println("\n****************GAME-OVER*****************\n");
	}
}