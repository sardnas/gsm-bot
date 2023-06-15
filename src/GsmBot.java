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

    Map<int[], int[]> coordinateAndScreenPosition = new HashMap<>();

    public GsmBot() {

    }

    public BufferedImage takeScreenShot() throws AWTException {
        Robot robot = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        return image;
    }

    public void findGameBoard() throws AWTException, IOException, InterruptedException {
        BufferedImage screenShotImage = takeScreenShot();
        System.out.println("Took shot.");
        ArrayList<Integer> leftTopCorner = findStartCoordinate(screenShotImage);
        System.out.println("Found top left corner. ");
        System.out.print(leftTopCorner.get(0));
        System.out.print(",");
        System.out.print(leftTopCorner.get(1));
        System.out.print("\n");
        WarderobeChallangeLogic.Color[][] initialBoard = createGameBoard(leftTopCorner, screenShotImage);
        System.out.println("Found board.");
        printBoard(initialBoard);
    }

    private void printBoard(WarderobeChallangeLogic.Color[][] board){
        System.out.println("########################################################");
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
                System.out.print("-");
                System.out.print(i);
                System.out.print(",");
                System.out.print(j);
                System.out.print("      ");
            }
            System.out.print("\n");
            System.out.print("\n");
        }
        System.out.println("########################################################");
    }

    private WarderobeChallangeLogic.Color[][] createGameBoard(ArrayList<Integer> leftTopCorner, BufferedImage screenShotImage) throws IOException, AWTException, InterruptedException {
        WarderobeChallangeLogic.Color[][] board = new WarderobeChallangeLogic.Color[11][11];
        int[] pinkBubbleArray = new int[20 * 20];
        int[] purpleBubbleArray = new int[20 * 20];
        int[] greenBubbleArray = new int[20 * 20];
        int[] blueBubbleArray = new int[20 * 20];

        ImageIO.read(new File("images/pinkBubble.jpg")).getRGB(0, 0, 20, 20, pinkBubbleArray, 0, 20);
        ImageIO.read(new File("images/purpleBubble.jpg")).getRGB(0, 0, 20, 20, purpleBubbleArray, 0, 20);
        ImageIO.read(new File("images/greenBubble.jpg")).getRGB(0, 0, 20, 20, greenBubbleArray, 0, 20);
        ImageIO.read(new File("images/blueBubble.jpg")).getRGB(0, 0, 20, 20, blueBubbleArray, 0, 20);

        int[] rgbPixels = new int[9];

        int x = leftTopCorner.get(0) + 30;
        int y = leftTopCorner.get(1);
        int i = x;
        int j = y;
        int xInArray = 0;
        int yInArray = 0;

        int counter = 0;

        TimeUnit.SECONDS.sleep(3);
        Robot robot = new Robot();
        boolean running = true;
        while (running) {
            //TimeUnit.SECONDS.sleep(1);
            //robot.mouseMove(i,j);
            System.out.print(xInArray);
            System.out.print(",");
            System.out.print(yInArray);
            System.out.print("\n");
            counter++;
            screenShotImage.getRGB(i, j, 3, 3, rgbPixels, 0, 3);
            if (pixelEqual(rgbPixels, pinkBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.PINK;
                int[] boardCoordinate = new int[2];
                boardCoordinate[0] = xInArray;
                boardCoordinate[1] = yInArray;
                int[] screenCoordinate = new int[2];
                screenCoordinate[0] = i;
                screenCoordinate[1] = j;
                coordinateAndScreenPosition.put(boardCoordinate, screenCoordinate);
            } else if (pixelEqual(rgbPixels, purpleBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.PURPLE;
                int[] boardCoordinate = new int[2];
                boardCoordinate[0] = xInArray;
                boardCoordinate[1] = yInArray;
                int[] screenCoordinate = new int[2];
                screenCoordinate[0] = i;
                screenCoordinate[1] = j;
                coordinateAndScreenPosition.put(boardCoordinate, screenCoordinate);
            } else if (pixelEqual(rgbPixels, greenBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.GREEN;
                int[] boardCoordinate = new int[2];
                boardCoordinate[0] = xInArray;
                boardCoordinate[1] = yInArray;
                int[] screenCoordinate = new int[2];
                screenCoordinate[0] = i;
                screenCoordinate[1] = j;
                coordinateAndScreenPosition.put(boardCoordinate, screenCoordinate);
            } else if (pixelEqual(rgbPixels, blueBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.BLUE;
                int[] boardCoordinate = new int[2];
                boardCoordinate[0] = xInArray;
                boardCoordinate[1] = yInArray;
                int[] screenCoordinate = new int[2];
                screenCoordinate[0] = i;
                screenCoordinate[1] = j;
                coordinateAndScreenPosition.put(boardCoordinate, screenCoordinate);
            } else {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.CLEAR;
            }

            i = i + 34;
            xInArray++;
            if (counter > 11 * 12 - 1) {
                running = false;
            } else if (counter % 11 == 0) {
                j = j + 34;
                i = x;
                xInArray = 0;
                yInArray++;
                if(yInArray > 10){
                    running = false;
                }
            }


        }
        return board;
    }


    public ArrayList<Integer> findStartCoordinate(BufferedImage screenShotImage) throws IOException {
        BufferedImage cornerImage = ImageIO.read(new File("images/corner.jpg"));

        int regionSize = 8;
        int maxHeight = screenShotImage.getHeight() - regionSize;
        int maxWidth = screenShotImage.getWidth() - regionSize;
        ArrayList<Integer> coordinatesOnScreen = new ArrayList<>();

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                int[] rgbArray = new int[regionSize * regionSize]; // Increased array size to capture a 10x10 region
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

    private boolean pixelEqual(int[] pixels, int[] bubbleArray) {
        int threshold = 10; // Adjust this threshold as needed
        int matchScore = 0;
        for(int j = 0; j < pixels.length; j++) {
            for (int i = 0; i < bubbleArray.length; i++) {
                int rgb1 = pixels[j];
                int rgb2 = bubbleArray[i];

                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = rgb1 & 0xFF;

                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = rgb2 & 0xFF;

                int diffR = Math.abs(r1 - r2);
                int diffG = Math.abs(g1 - g2);
                int diffB = Math.abs(b1 - b2);

                if (!(diffR > threshold || diffG > threshold || diffB > threshold)) {
                    matchScore++; // RGB values are not within the threshold
                }
            }
        }
        System.out.println(matchScore);
        if(matchScore > 50){
            return true;
        }
        return false;
    }

        private boolean arrayEqualToBufferedImage ( int[] array1, BufferedImage image2){
            int[] array2 = new int[8 * 8]; // Increased array size to match the region size
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

