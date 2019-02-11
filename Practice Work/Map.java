package Risk.Game.Team.View;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Map extends JFrame
{
	private String[] MapArray = {"2","3","4","5","6"};
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JFrame frame = new JFrame();
	JButton btn = new JButton();
	//private JPanel panel;
	//private JFrame frame;
	//private JLabel label;
	//private JButton okayButton;
	private JComboBox combo = new JComboBox();
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
	private int player;
	
	
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
   // GridBagLayout layout = new GridBagLayout();
    frame.setLocation(100, 100);
	//panel.setLayout(layout);
	//GridBagConstraints gbc = new GridBagConstraints();
	//frame.add(panel);
	label = new JLabel("Select the number of players");
	//label.setBounds(10, 10,10,20); 
	frame.getContentPane().add(panel);
	frame.getContentPane().add(label);
	JComboBox cb=new JComboBox(MapArray);     
    cb.setBounds(10,200,95,20);    
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
	
	btn = new JButton("OK");
	btn.setBounds(10,200,95,20);
	//btn.setVisible(true);
	frame.getContentPane().add(btn);
	
	
}

public static void main(String[] args) {
	Map exp = new Map();

}
}