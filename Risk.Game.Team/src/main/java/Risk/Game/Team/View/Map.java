package Risk.Game.Team.View;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Map extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final String LOGGER1 = null;
	private String[] MapArray = {"2","3","4","5","6"};
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private static JFrame frame = new JFrame();
	private static JButton btn = new JButton("OK");
	//private JPanel panel;
	//private JFrame frame;
	//private JLabel label;
	//private JButton btn;
	private static JComboBox combo = new JComboBox();
	private JLabel player1 = new JLabel();
	private JLabel player2 = new JLabel();
	private JLabel player3 = new JLabel();          
	private JLabel player4 = new JLabel();
	private JLabel player5 = new JLabel();
	private JLabel player6 = new JLabel();
	private JComboBox Player1;
	private JComboBox Player2;
	private JComboBox Player3;
	private JComboBox Player4;
	private JComboBox Player5;
	private JComboBox Player6;
	private String player;
	protected static MapParseProcessor mapParseObject;
	private static JFileChooser filechooser;
	private static String filePath;
	
	
	public Map() {
		try {
			ply();
		} catch (Exception e) {
			System.out.println ("error loading");
		}
	}
	
public void ply(){
	
  
    frame.setTitle("Risk Game");
    frame.setVisible(true);
    frame.setSize(400, 400);
    //frame.setLocationRelativeTo(null);
    GridBagLayout layout = new GridBagLayout();
    frame.setLocation(100, 100);
	//panel.setLayout(layout);
	final GridBagConstraints gbc = new GridBagConstraints();
	frame.add(panel);
	label = new JLabel("Select the number of players");
	//label.setBounds(10, 10,10,20); 
	frame.getContentPane().add(panel);
	frame.getContentPane().add(label);
	JComboBox cb=new JComboBox(MapArray);     
    cb.setBounds(10,200,95,20);    
    //panel.add(label, gbc);
    frame.add(cb);        
    frame.setLayout(null);    
    frame.setSize(400,500);    
    frame.setVisible(true); 
    player1.setText("Player1");
	player1.setVisible(true);
	player2.setText("Player2");
	player2.setVisible(true);
	player3.setText("Player3");
	player3.setVisible(true);    
	player4.setText("Player4");
	player4.setVisible(true);
	player5.setText("Player5");
	player5.setVisible(true);
	player6.setText("Player6");
	player6.setVisible(true);
	
	//btn = new JButton("OK");
	//btn.setBounds(10,200,95,20);
	//btn.setVisible(true);
	
	//frame.add(btn);
	//frame.getContentPane().add(btn);

    //btn = new JButton("OK");
   // btn.setBounds(20,20,70, 70);  
    //btn.setVisible(true);
   frame.getContentPane().add(btn);
    frame.setSize(700,700);  
    
    frame.setLayout(null);  
    frame.setVisible(true);
    btn.setLocation(12, 230);
    btn.setPreferredSize(new Dimension(116, 40));
    combo.addActionListener(new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			player = (String) combo.getSelectedItem();

			if (player.equals("2") || player.equals("3") || player.equals("4")
					|| player.equals("5") || player.equals("6")) {

				gbc.gridx = 0;
				gbc.gridy = 4;
				panel.add(player1, gbc);
				gbc.gridx = 2;
				gbc.gridy = 4;
				panel.add(Player1, gbc);

				gbc.gridx = 0;
				gbc.gridy = 6;
				panel.add(player2, gbc);
				gbc.gridx = 2;
				gbc.gridy = 6;
				panel.add(Player2, gbc);
				panel.remove(Player3);
				panel.remove(player3);
				panel.remove(Player4);
				panel.remove(player4);
				panel.remove(Player5);
				panel.remove(player5);
				panel.remove(Player6);
				panel.remove(player6);

			}
			if (player.equals("3") || player.equals("4") || player.equals("5")
					|| player.equals("6")) {

				gbc.gridx = 0;
				gbc.gridy = 8;
				panel.add(player3, gbc);
				gbc.gridx = 2;
				gbc.gridy = 8;
				panel.add(Player3, gbc);
				panel.remove(Player4);
				panel.remove(player4);
				panel.remove(Player5);
				panel.remove(player5);
				panel.remove(Player6);
				panel.remove(player6);
			}
			if (player.equals("4") || player.equals("5") || player.equals("6")) {
				gbc.gridx = 0;
				gbc.gridy = 10;
				panel.add(player4, gbc);
				gbc.gridx = 2;
				gbc.gridy = 10;
				panel.add(Player4, gbc);
				panel.remove(Player5);
				panel.remove(player5);
				panel.remove(Player6);
				panel.remove(player6);
			}
			if (player.equals("5") || player.equals("6")) {
				gbc.gridx = 0;
				gbc.gridy = 12;
				panel.add(player5, gbc);
				gbc.gridx = 2;
				gbc.gridy = 12;
				panel.add(Player5, gbc);
				panel.remove(Player6);
				panel.remove(player6);
			}
			if (player.equals("6")) {
				gbc.gridx = 0;
				gbc.gridy = 14;
				panel.add(player6, gbc);
				gbc.gridx = 2;
				gbc.gridy = 14;
				panel.add(Player6, gbc);
			}
			gbc.gridx = 2;
			gbc.gridy = 18;
			panel.add(btn, gbc);
			frame.validate();
			frame.repaint();
			frame.repaint();
		}
	});
}

public static void main(String[] args) {
	Map exp = new Map();




btn.addActionListener(new ActionListener() {
    private FileNameExtensionFilter filenameFilter;

	public void actionPerformed(ActionEvent e) {
        try{
            if (e.getSource() == btn) {
            HashMap<String, String> playerType = new HashMap();
            LOGGER.info("Okay Button Clicked");
					filenameFilter = new FileNameExtensionFilter(" .map", "map", "Map");
					frame.setVisible(false);
					filechooser = new JFileChooser();
					filechooser.setDialogTitle("Select a map file");
					filechooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					filechooser.setFileFilter(filenameFilter);
					int res = filechooser.showOpenDialog(filechooser);
					filechooser.setLocation(490, 190);
					filechooser.setSize(490, 490);
					filechooser.setVisible(true);
					if (res == JFileChooser.APPROVE_OPTION) {
						File selectedFile = filechooser.getSelectedFile();
						LOGGER.info("Selected file: " + selectedFile.getAbsolutePath().toString());
						filePath = selectedFile.getAbsolutePath().toString();
						mapParseObject = new MapParseProcessor();
						mapParseObject.mapParser(selectedFile.getAbsolutePath().toString(),combo.getSelectedItem().toString(), playerType, "single");
                    }
                }
            }
            catch(Exception ex){
                LOGGER.error("Error Message : " + ex.getMessage());
            }
        }
    });
}
/**
 * This method get the filePath
 * 
 * @return filePath
 */
public String getFilePath() {
	return getFilePath();
}

/**
 * method sets the filePath
 * 
 * @param filePath path of the file
 */
public void setFilePath(String filePath) {
	this.filePath = filePath;
}

public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}


}

		



