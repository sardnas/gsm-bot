import java.util.concurrent.ThreadLocalRandom;

public class Board {
    enum Color {
        P,
        U,
        G,
        B,
        CLEAR
    }

    Color[][] board = new Color[11][11];
    public Board(){
        Color[] initialValues = new Color[4];
        initialValues[0] = Color.P;
        initialValues[1] = Color.U;
        initialValues[2] = Color.G;
        initialValues[3] = Color.B;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = initialValues[ThreadLocalRandom.current().nextInt(0, 4)];
            }
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    public void setBoard(Color[][] board, int[] coordinates) {
        switch(board[coordinates[0]][coordinates[1]]) {
            case P:
                clearNeighbours(Color.P, coordinates, board);
                break;
            case U:
                clearNeighbours(Color.U, coordinates, board);
                break;
            case G:
                clearNeighbours(Color.G, coordinates, board);
                break;
            case B:
                clearNeighbours(Color.B, coordinates, board);
                break;
            default:
                break;
        }

        this.board = board;
        calculateNewBoard();
    }

    private void calculateNewBoard(){

    }

    // LÄS PÅ OM REKURSIV PROGRAMMERING HÄR ANNARS BLIR DET FEEEEEL
    private void clearNeighbours(Color color, int[] coordinates, Color[][] board){
        if(board[coordinates[0]][coordinates[1]] == Color.CLEAR){
            return;
        }else if(board[coordinates[0]][coordinates[1]] == color){
            int[] newCoord = new int[2];
            board[coordinates[0]][coordinates[1]] = Color.CLEAR;

            newCoord[0] = coordinates[0] -1;
            newCoord[1] = coordinates[1] -1; // MATTE!!!
            clearNeighbours(color, newCoord, board);

            newCoord[0] = coordinates[0] -1;
            newCoord[1] = coordinates[1] -1; // MATTE!!!
            clearNeighbours(color, newCoord, board);

            newCoord[0] = coordinates[0] -1;
            newCoord[1] = coordinates[1] -1; // MATTE!!!
            clearNeighbours(color, newCoord, board);

            newCoord[0] = coordinates[0] -1;
            newCoord[1] = coordinates[1] -1; // MATTE!!!
            clearNeighbours(color, newCoord, board);
        }
    }
}
