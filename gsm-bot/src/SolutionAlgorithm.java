import java.util.ArrayList;
import java.util.Random;

public class SolutionAlgorithm {
    WarderobeChallangeLogic board;
    public SolutionAlgorithm(WarderobeChallangeLogic inputBoard){
        board = inputBoard;
    }

    public ArrayList<int[]> getSolutionMoves(){
        return solutionAlgorithm();
    }

    private ArrayList<int[]> solutionAlgorithm(){
        int[] randomCoordinates;
        ArrayList<int[]> moves = new ArrayList();
        while(true){
            randomCoordinates = getRandomCoordinates();
            if(randomCoordinates[0] < 50){
                board.setBoard(randomCoordinates);
                moves.add(randomCoordinates);
                if(board.checkForLose()){
                    printBoard();
                    board.resetBoard();
                    moves.clear();
                }
                if(board.checkForWin()){
                    printBoard();
                    return moves;
                }
            }else{
                printBoard();
                board.resetBoard();
                moves.clear();
            }
        }
    }

    private void printBoard(){
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
    }

    private int[] getRandomCoordinates(){
        /*
        Random random = new Random();
        int[] randomCoordinates = new int[2];
        randomCoordinates[0] = random.nextInt(MAX);
        randomCoordinates[1] = random.nextInt(MAX);

         */
        Random random = new Random();
        ArrayList<int[]> clickableList = board.getClickableCoordinates();
        int[] randomCoordinates = new int[2];
        if(!clickableList.isEmpty()) {
            randomCoordinates = clickableList.get(random.nextInt(clickableList.size()));
        }else{
            randomCoordinates[0] = 100;
            randomCoordinates[1] = 100;
        }
        return randomCoordinates;
    }

}
