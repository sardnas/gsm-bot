import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class GsmBot {

    public GsmBot(){

    }

    public BufferedImage takeScreenShot() throws AWTException {
        Robot robot = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        System.out.println("Took shot. Height: ");
        System.out.println(image.getHeight());
        return image;
    }

    private void findGameBoard(BufferedImage screenShotImage) {


    }

    public void findStartCoordinate(BufferedImage screenShotImage) throws IOException {
        ArrayList<Integer> leftTopCorner = findMatch(screenShotImage);
        System.out.print(leftTopCorner.get(0));
        System.out.print(",");
        System.out.print(leftTopCorner.get(1));
    }

    private ArrayList<Integer> findMatch(BufferedImage screenShotImage) throws IOException {
        BufferedImage pinkBubble = null;
        pinkBubble = ImageIO.read(new File("images/pinkBubble.jpg"));

        BufferedImage purpleBubble = null;
        purpleBubble = ImageIO.read(new File("images/purpleBubble.jpg"));

        BufferedImage greenBubble = null;
        greenBubble = ImageIO.read(new File("images/greenBubble.jpg"));

        BufferedImage blueBubble = null;
        blueBubble = ImageIO.read(new File("images/blueBubble.jpg"));

        int[] rgbArrayPink = new int[10];
        int[] rgbArrayPurple = new int[10];
        int[] rgbArrayGreen = new int[10];
        int[] rgbArrayBlue = new int[10];

        pinkBubble.getRGB(0, 0,10,10, rgbArrayPink, 0, 0);
        purpleBubble.getRGB(0, 0,10,10, rgbArrayPurple, 0, 0);
        greenBubble.getRGB(0, 0,10,10, rgbArrayGreen, 0, 0);
        blueBubble.getRGB(0, 0,10,10, rgbArrayBlue, 0, 0);

        int maxHeight = screenShotImage.getHeight()-20;
        int maxWidth = screenShotImage.getWidth()-20;
        ArrayList<Integer> coordinatesOnScreen = new ArrayList<>();
        for(int i = 0; i < maxHeight; i++){
            for(int j = 0; j < maxWidth; j++){
                int[] rgbArray = new int[10];
                screenShotImage.getRGB(i, j,10,10, rgbArray, 0, 0);
                if(rgbArray.equals(rgbArrayPink)|| rgbArray.equals(rgbArrayPurple) || rgbArray.equals(rgbArrayGreen) || rgbArray.equals(rgbArrayBlue)){
                    coordinatesOnScreen.add(i);
                    coordinatesOnScreen.add(j);
                    return coordinatesOnScreen;
                }
            }
        }
        return coordinatesOnScreen;
    }
/*
    private WarderobeChallangeLogic.Color[][] createGameBoard(){

    }

 */
}
