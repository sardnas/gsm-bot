import java.awt.*;
import java.util.Random;

public class Main {
    public static final int FIVE_SECONDS = 5000;
    public static final int MAX_Y = 400;
    public static final int MAX_X = 400;

    public static void main(String[] args) {
        Board board = new Board();
        Board.Color[][] initialBoard = board.getBoard();
        for(int i = 0; i < initialBoard.length; i++){
            for(int j = 0; j < initialBoard.length; j++){
                System.out.print(initialBoard[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }

    }

   private void MouseMover() throws Exception {
        Robot robot = new Robot();
        Random random = new Random();
        while (true) {
            robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));

            Thread.sleep(FIVE_SECONDS);
        }
    }
}