import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws Exception {
        System.out.print("WELCOME TO GSM BOT!");
        Board board = new Board();
        GsmBot bot = new GsmBot(board);
        ArrayList<int[]> solutionMoves = bot.getSolutionMoves();
/*
        board.resetBoard();
        for (int[] coordinate : solutionMoves ) {
            board.setBoard(coordinate);
            for(int i = 0; i < board.getBoard().length; i++) {
                for (int j = 0; j < board.getBoard().length; j++) {
                    System.out.print(board.getBoard()[i][j]);
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
            TimeUnit.SECONDS.sleep(3);
        }

        System.out.println("WIN WITH FOLLOWING MOVES");
        for(int i = 0; i < solutionMoves.size(); i++){
            System.out.print(solutionMoves.get(i)[0]);
            System.out.print(",");
            System.out.print(solutionMoves.get(i)[1]);
            System.out.print("  ");
        }*/
    }

    private static void GameLoop() throws Exception {
        boolean running = true;
        Board board = new Board();
        Board.Color[][] initialBoard = board.getBoard();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        int x;
        int y;
        int[] coordinates = new int[2];

        while(running){

            for(int i = 0; i < board.getBoard().length; i++){
                for(int j = 0; j < board.getBoard().length; j++){
                    System.out.print(board.getBoard()[i][j]);
                    System.out.print("-");
                    System.out.print(i);
                    System.out.print(",");
                    System.out.print(j);
                    System.out.print("      ");
                }
                System.out.print("\n");
                System.out.print("\n");
                System.out.print("\n");
            }

            System.out.println("Enter coordinate X: ");
            String xCoordinate = myObj.nextLine();  // Read user input
            x = Integer.parseInt(xCoordinate);

            System.out.println("Enter coordinate Y: ");
            String yCoordinate = myObj.nextLine();  // Read user input
            y = Integer.parseInt(yCoordinate);

            coordinates[0] = x;
            coordinates[1] = y;

            board.setBoard(coordinates);
            if(board.checkForLose()){
                System.out.print("GAME OVER");
                running = false;
            }

            if(board.checkForWin()){
                System.out.print("CONGRATULATIONS!");
                running = false;
            }

        }
    }

}