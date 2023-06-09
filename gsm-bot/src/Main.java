import java.awt.*;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static final int FIVE_SECONDS = 5000;
    public static final int MAX_Y = 400;
    public static final int MAX_X = 400;

    public static void main(String[] args) throws Exception {
        Board board = new Board();
        Board.Color[][] initialBoard = board.getBoard();

        for(int i = 0; i < initialBoard.length; i++){
            for(int j = 0; j < initialBoard.length; j++){
                System.out.print(initialBoard[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter coordinate X: ");
        String xCoordinate = myObj.nextLine();  // Read user input
        int x = Integer.parseInt(xCoordinate);
        System.out.println("Enter coordinate Y: ");
        String yCoordinate = myObj.nextLine();  // Read user input
        int y = Integer.parseInt(yCoordinate);

        int[] coordinates = new int[2];
        coordinates[0] = x;
        coordinates[1] = y;

        board.setBoard(coordinates);

        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard().length; j++){
                System.out.print(board.getBoard()[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }


    }

    private static void GameLoop() throws Exception {
        while(true){

        }
    }

   private static void MouseMover() throws Exception {
        Robot robot = new Robot();
        Random random = new Random();
        while (true) {
            robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));

            Thread.sleep(FIVE_SECONDS);
        }
    }
}