import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;


public class GsmBot {
    public static final long serialVersionUID = 1L;
    public GsmBot(){

    }

    public void takeScreenShot() throws InterruptedException, AWTException {
        Robot robot = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
    }

    private void findGameBoard(BufferedImage screenShotImage) throws IOException {
        BufferedImage pinkBubble = null;
        pinkBubble = ImageIO.read(new File("pinkBubble.jpg"));

        BufferedImage purpleBubble = null;
        purpleBubble = ImageIO.read(new File("purpleBubble.jpg"));

        BufferedImage greenBubble = null;
        greenBubble = ImageIO.read(new File("greenBubble.jpg"));

        BufferedImage blueBubble = null;
        blueBubble = ImageIO.read(new File("blueBubble.jpg"));
        
        int maxHeight = screenShotImage.getHeight()/10;
        int maxWidth = screenShotImage.getWidth()/10;
        for(int i = 0; i < maxHeight; i++){
            for(int j = 0; j < maxWidth; j++){
                int[] rgbArray = new int[100];
                screenShotImage.getRGB(i*10, j*10,10,10, rgbArray, 0, 0);
            }
        }

    }

    private WarderobeChallangeLogic.Color[][] createGameBoard(){

    }
}
