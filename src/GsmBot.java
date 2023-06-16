import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;


public class GsmBot {

    CoordinateMapper coordinateMapper = new CoordinateMapper();

    public GsmBot() {

    }

    public void naturalMovementToCoordinates(ArrayList<int[]> solutionMoves) throws InterruptedException, AWTException {
        Robot robot = new Robot();
        Random rand = new Random();
        int x1 = 1500;
        int y1 = 444;
        int x2;
        int y2;
        int t = 1000;
        int n = 4000;
        int randomX = rand.nextInt(1700 - 1200 + 1) + 1200;
        int randomY = rand.nextInt(450 - 350 + 1) + 350;
        Coordinate coord;
        for(int i = 0; i < solutionMoves.size(); i++){
            coord = coordinateMapper.getSecondCoordinate(solutionMoves.get(i)[0],solutionMoves.get(i)[1]);
            x2 = coord.getX();
            y2 = coord.getY();
            mouseGlide(x1, y1, x2, y2, t, n);
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(300 - 200 + 1) + 200);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(600 - 300 + 1) + 300);
            mouseGlide(x2, y2, randomX, randomY, t, n);
            //TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 3));
            x1 = randomX;
            y1 = randomY;
            randomX = rand.nextInt(1700 - 1200 + 1) + 1200;
            randomY = rand.nextInt(450 - 350 + 1) + 350;
            t = rand.nextInt(2000 - 1000 + 1) + 1000;
        }
        mouseGlide(x1, y1, 1500, 760, t, n);
    }


    private void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) throws InterruptedException, AWTException {

            Robot r = new Robot();
            double dx = (x2 - x1) / ((double) n);
            double dy = (y2 - y1) / ((double) n);
            double dt = t / ((double) n);
            for (int step = 1; step <= n; step++) {
                TimeUnit.MILLISECONDS.sleep((int) dt);
                //Thread.sleep((int) dt);
                r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
            }

    }

    public BufferedImage takeScreenShot() throws AWTException {
        Robot robot = new Robot();
        Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(capture);
        return image;
    }

    public WarderobeChallangeLogic.Color[][] findGameBoard() throws AWTException, IOException, InterruptedException {
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
        return initialBoard;
    }

    private void printBoard(WarderobeChallangeLogic.Color[][] board){
        System.out.println("########################################################");
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
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
        WarderobeChallangeLogic.Color[][] board = new WarderobeChallangeLogic.Color[12][11];
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

        //TimeUnit.SECONDS.sleep(3);
        //Robot robot = new Robot();
        boolean running = true;
        while (running) {
            //TimeUnit.SECONDS.sleep(1);
            //robot.mouseMove(i,j);
            /*System.out.print(xInArray);
            System.out.print(",");
            System.out.print(yInArray);
            System.out.print("\n");
            int num = counter;*/
            counter++;
            screenShotImage.getRGB(i, j, 3, 3, rgbPixels, 0, 3);
            if (pixelEqual(rgbPixels, pinkBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.PINK;
                Coordinate coord2 = new Coordinate(i, j);
                coordinateMapper.addMapping(yInArray, xInArray, coord2);
            } else if (pixelEqual(rgbPixels, purpleBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.PURPLE;
                Coordinate coord2 = new Coordinate(i, j);
                coordinateMapper.addMapping(yInArray, xInArray, coord2);
            } else if (pixelEqual(rgbPixels, greenBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.GREEN;
                Coordinate coord2 = new Coordinate(i, j);
                coordinateMapper.addMapping(yInArray, xInArray, coord2);
            } else if (pixelEqual(rgbPixels, blueBubbleArray)) {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.BLUE;
                Coordinate coord2 = new Coordinate(i, j);
                coordinateMapper.addMapping(yInArray, xInArray, coord2);
            } else {
                board[yInArray][xInArray] = WarderobeChallangeLogic.Color.CLEAR;
            }

            i = i + 34;
            xInArray++;
            if (counter % 11 == 0) {
                j = j + 34;
                i = x;
                xInArray = 0;
                yInArray++;
                if(yInArray > 11){
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
        //System.out.println(matchScore);
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

