import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;


public class GsmBot {

    Map<int[],int[]> coordinateAndScreenPosition = new HashMap<>();
    public GsmBot(){

    }

    public BufferedImage takeScreenShot() throws AWTException {
        Robot robot = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        return image;
    }

    private void findGameBoard() throws AWTException, IOException {
        BufferedImage screenShotImage = takeScreenShot();
        System.out.println("Took shot.");
        ArrayList<Integer> leftTopCorner = findStartCoordinate(screenShotImage);
        System.out.println("Found top left corner. ");
        System.out.print(leftTopCorner.get(0));
        System.out.print(",");
        System.out.print(leftTopCorner.get(1));
        WarderobeChallangeLogic.Color[][] initialBoard = createGameBoard(leftTopCorner, screenShotImage);
    }

    private WarderobeChallangeLogic.Color[][] createGameBoard(ArrayList<Integer> leftTopCorner, BufferedImage screenShotImage){
        WarderobeChallangeLogic.Color[][] board = new WarderobeChallangeLogic.Color[11][11];
        int[] rgbArray = new int[1];

        int x = leftTopCorner.get(0) + 30;
        int y = leftTopCorner.get(1);
        int i = x;
        int j = y;
        int counter = 0;

        boolean running = true;
        while(running){
            counter++;
            screenShotImage.getRGB(i, j, 1, 1, rgbArray, 0, 1);
            i = i + 34;
            if (counter > 11*12-1) {
                running = false;
            } else if(counter % 11 == 0){

                j = j + 34;
                i = x;
            }

        }
        return board;
    }


    public ArrayList<Integer> findStartCoordinate(BufferedImage screenShotImage) throws IOException {
        BufferedImage cornerImage = ImageIO.read(new File("images/corner.jpg"));
        BufferedImage purpleBubble = ImageIO.read(new File("images/purpleBubble.jpg"));
        BufferedImage greenBubble = ImageIO.read(new File("images/greenBubble.jpg"));
        BufferedImage blueBubble = ImageIO.read(new File("images/blueBubble.jpg"));

        int regionSize = 8;
        int maxHeight = screenShotImage.getHeight() - regionSize;
        int maxWidth = screenShotImage.getWidth() - regionSize;
        ArrayList<Integer> coordinatesOnScreen = new ArrayList<>();

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                int[] rgbArray = new int[regionSize*regionSize]; // Increased array size to capture a 10x10 region
                screenShotImage.getRGB(j, i, regionSize, regionSize, rgbArray, 0, regionSize);

                if (arrayEqualToBufferedImage(rgbArray, cornerImage)) {
                    coordinatesOnScreen.add(j);
                    coordinatesOnScreen.add(i);
                    return coordinatesOnScreen; // Found a match, returning coordinates
                }
            }
        }
        return coordinatesOnScreen; // No match found
    }

    private boolean pixelEqual(int pixelOne, int pixelTwo){
        int threshold = 100; // Adjust this threshold as needed
        int rgb1 = pixelOne;
        int rgb2 = pixelTwo;

        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = rgb1 & 0xFF;

        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = rgb2 & 0xFF;

        int diffR = Math.abs(r1 - r2);
        int diffG = Math.abs(g1 - g2);
        int diffB = Math.abs(b1 - b2);

        if (diffR > threshold || diffG > threshold || diffB > threshold) {
            return false; // RGB values are not within the threshold
        }
        return true;
    }

    private boolean arrayEqualToBufferedImage(int[] array1, BufferedImage image2) {
        int[] array2 = new int[8*8]; // Increased array size to match the region size
        image2.getRGB(0, 0, 8, 8, array2, 0, 8);

        int threshold = 100; // Adjust this threshold as needed

        for (int i = 0; i < array1.length; i++) {
            int rgb1 = array1[i];
            int rgb2 = array2[i];

            int r1 = (rgb1 >> 16) & 0xFF;
            int g1 = (rgb1 >> 8) & 0xFF;
            int b1 = rgb1 & 0xFF;

            int r2 = (rgb2 >> 16) & 0xFF;
            int g2 = (rgb2 >> 8) & 0xFF;
            int b2 = rgb2 & 0xFF;

            int diffR = Math.abs(r1 - r2);
            int diffG = Math.abs(g1 - g2);
            int diffB = Math.abs(b1 - b2);

            if (diffR > threshold || diffG > threshold || diffB > threshold) {
                return false; // RGB values are not within the threshold
            }
        }

        return true; // RGB values are within the threshold
    }

/*
    private WarderobeChallangeLogic.Color[][] createGameBoard(){

    }

 */
}
