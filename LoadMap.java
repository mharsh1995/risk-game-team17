import javax.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Load Map file Dialog class
 * 
 * @author yashgolwala
 */
public class LoadMap extends JFileChooser {
    /**
     * Constructor for selecting and loading of a map file
     */
    public LoadMap(){
        FileNameExtensionFilter file = new FileNameExtensionFilter("Maps", "map");
        setDialogTitle("Select a Map");
        setFileFilter(file);
        setCurrentDirectory(new File(System.getProperty("user.home")));
    }
}