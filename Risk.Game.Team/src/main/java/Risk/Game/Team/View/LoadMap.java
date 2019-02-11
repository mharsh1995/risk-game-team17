package Risk.Game.Team.View;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Collections;

import javax.* ;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.imageio.IIOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Risk.Game.Team.model.Map.MapParseProcess;


/**
 * This file allows you to select.map file for the game
 * 
 * @author yashgolwala harshmehta
 */
public class LoadMap extends JFrame {
    private static Logger LOGGER = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private JFrame countFrame;
	private JPanel panel = new JPanel();
	private JLabel countLabel;
    private JLabel selectMapLabel;
    private JButton okayButton;
	private JFileChooser fileChooser;
    private FileNameExtensionFilter filenameFilter;
    private String filePath = null;
    private MapParseProcessor mapParseObject;

	/**
     * Constructor for selecting and loading of a map file
     */
    public LoadMap(){
        try {
			Count();
		} catch (Exception e) {
			LOGGER.error("Error Message : " + e.getMessage());
		}
    }
    public void Count(){
    okayButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            try{
                if (event.getSource() == okayButton) {
                HashMap<String, String> playerType = new HashMap<>();
                LOGGER.info("Okay Button Clicked");
						filenameFilter = new FileNameExtensionFilter(" .map", "map", "Map");
						countFrame.setVisible(false);
						fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Select a map file");
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
						fileChooser.setFileFilter(filenameFilter);
						int res = fileChooser.showOpenDialog(fileChooser);
						fileChooser.setLocation(490, 190);
						fileChooser.setSize(490, 490);
						fileChooser.setVisible(true);
						if (res == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							LOGGER.info("Selected file: " + selectedFile.getAbsolutePath().toString());
							filePath = selectedFile.getAbsolutePath().toString();
							mapParseObject = new MapParseProcessor();
							mapParseObject.mapParser(selectedFile.getAbsolutePath().toString(),playerCountCombo.getSelectedItem().toString(), playerType, "single");
                        }
                    }
                }
                catch(Exception e){
                    LOGGER.error("Error Message : " + e.getMessage());
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
		return filePath;
	}

	/**
	 * method sets the filePath
	 * 
	 * @param filePath path of the file
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
    }
}