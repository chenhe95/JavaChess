
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import net.sf.image4j.codec.ico.ICODecoder;

/**
 * Manages all the images needed for the game
 *
 * @author He
 */
public class ImageLibrary {

    private static final String DIRECTORY = "images/";
    private static final int IMAGE_SIZE = Grid.CELL_SIZE - 2 * ChessUI.IMAGE_OFFSET;
    private static final HashMap<String, Image> IMAGES = new HashMap<>();
    private static final HashMap<String, Image> IMAGES_RESIZED = new HashMap<>();

    static {
        File dir = new File(DIRECTORY);
        try {
            for (File file : dir.listFiles()) {
                String fileName = file.getName();
                if (fileName.endsWith(".ico")) {
                    String pieceName = fileName.substring(0,
                            fileName.length() - 4);
                    List<BufferedImage> iconList = ICODecoder.read(file);
                    BufferedImage icon = iconList.get(0);
                    IMAGES.put(pieceName, icon);
                    IMAGES_RESIZED.put(pieceName, icon.getScaledInstance(
                            IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
                } else if (fileName.endsWith(".jpg")) {
                    BufferedImage img = ImageIO.read(file);
                    IMAGES.put(fileName.substring(0, fileName.length() - 4), img);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(String imgkey) {
        return IMAGES.get(imgkey);
    }

    public static Image getResizedImage(String imgkey) {
        return IMAGES_RESIZED.get(imgkey);
    }

    public static Image getCustomResizedImage(String imgkey, int w, int h) {
        return IMAGES.get(imgkey).getScaledInstance(w, h, Image.SCALE_SMOOTH);
    }
}
