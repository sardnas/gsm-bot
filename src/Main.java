import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        WarderobeChallangeLogic board = new WarderobeChallangeLogic();
        SolutionAlgorithm solutionAlgorithm = new SolutionAlgorithm(board);
        ArrayList<int[]> solutionMoves = solutionAlgorithm.getSolutionMoves();

        System.out.println("WIN WITH FOLLOWING MOVES");
        for(int i = 0; i < solutionMoves.size(); i++){
            System.out.print(solutionMoves.get(i)[0]);
            System.out.print(",");
            System.out.print(solutionMoves.get(i)[1]);
            System.out.print("  ");
            }


/*

        TimeUnit.SECONDS.sleep(3);
        GsmBot gsmBot = new GsmBot();
        BufferedImage screenShot = gsmBot.takeScreenShot();
        ArrayList<Integer> match = gsmBot.findStartCoordinate(screenShot);
*/
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
    }

}

// 810,329
// x + 30, y första bubblan
// x + 34, y + 34 röra sig höger eller vänster

// höger hörn topp x + 380, y
// höger hörn botten x + 380, y + 410
// vänster hörn topp x y
// vänster hörn botten = x, y + 410