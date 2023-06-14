import java.util.ArrayList;
import java.util.Scanner;


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
    }

}