import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {

    private static void printBoard(WarderobeChallangeLogic.Color[][] board){
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

    public static void main(String[] args) throws Exception {
/*
        WarderobeChallangeLogic board = new WarderobeChallangeLogic();
        printBoard(board.getBoard());
        SolutionAlgorithm alg = new SolutionAlgorithm(board);
        for ( int[] move: alg.getSolutionMoves()
             ) {
            board.setBoard(move);
            printBoard(board.getBoard());
            TimeUnit.SECONDS.sleep(1);
        };*/

        int levels = 10;
        for(int i = 0; i < levels; i++){
            TimeUnit.SECONDS.sleep(5);
            GsmBot gsmBot = new GsmBot();
            WarderobeChallangeLogic.Color[][] theBoardFromScreen = gsmBot.findGameBoard();
            WarderobeChallangeLogic boardLogic = new WarderobeChallangeLogic(theBoardFromScreen);
            SolutionAlgorithm solutionAlgorithm = new SolutionAlgorithm(boardLogic);
            ArrayList<int[]> solutionMoves = solutionAlgorithm.getSolutionMoves();
            gsmBot.naturalMovementToCoordinates(solutionMoves);
        }

/*

        TimeUnit.SECONDS.sleep(3);
        GsmBot gsmBot = new GsmBot();
        BufferedImage screenShot = gsmBot.takeScreenShot();
        ArrayList<Integer> match = gsmBot.findStartCoordinate(screenShot);

        int x = 810 + 30;
        int y = 329;
        int i = x;
        int j = y;
        int counter = 0;

        TimeUnit.SECONDS.sleep(3);
        Robot robot = new Robot();
        robot.mouseMove(x + 35, y + 35);

        boolean running = true;
        while(running){
            TimeUnit.SECONDS.sleep(1);
            counter++;
            robot.mouseMove(i, j);
            i = i + 34;
            if (counter > 11*12-1) {
                running = false;
            } else if(counter % 11 == 0){

                j = j + 34;
                i = x;
            }

        }
        */

    }

}

// 810,329
// x + 30, y första bubblan
// x + 34, y + 34 röra sig höger eller vänster

// höger hörn topp x + 380, y
// höger hörn botten x + 380, y + 410
// vänster hörn topp x y
// vänster hörn botten = x, y + 410