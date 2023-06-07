import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public class Board {
    enum Color {
        P,
        U,
        G,
        B,
        O
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
        return this.board;
    }

    public void setBoard(int[] coordinates) {
        switch(this.board[coordinates[0]][coordinates[1]]) {
            case P:
                clearNeighbours(Color.P, coordinates, this.board);
                break;
            case U:
                clearNeighbours(Color.U, coordinates, this.board);
                break;
            case G:
                clearNeighbours(Color.G, coordinates, this.board);
                break;
            case B:
                clearNeighbours(Color.B, coordinates, this.board);
                break;
            default:
                break;
        }
        calculateNewBoard();
    }

    private void calculateNewBoard(){
        moveDown();
        //moveRight();
    }

    private void moveDown(){
        Color[][] boardCopy = new Color[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], board[i].length);
        }

        for (int j = 0; j < boardCopy[0].length; j++) {
            int emptyRow = 0; // Track the empty row position

            // Iterate from top to bottom
            for (int i = 0; i < boardCopy.length; i++) {
                if (boardCopy[i][j] != Color.O) {
                    // Move the non-empty cell to the empty row
                    boardCopy[emptyRow][j] = boardCopy[i][j];
                    emptyRow++;
                }
            }

            // Fill the remaining empty cells with Color.O
            while (emptyRow < boardCopy.length) {
                boardCopy[emptyRow][j] = Color.O;
                emptyRow++;
            }
        }

        this.board = boardCopy;
    }

    private void moveRight(){
        Color[][] boardCopy = new Color[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        for(int i = 0; i < boardCopy.length; i++) {
            if (boardCopy[boardCopy.length - 1][i] == Color.O) {
                boardCopy = moveRightHelper(i);
            }
        }
        this.board = boardCopy;
    }

    private Color[][] moveRightHelper(int xCoordinate){
        Color[][] boardCopy = new Color[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], board[i].length);
        }

        for(int i = xCoordinate; i < 1; i--){
            for(int j = 0; 0 < boardCopy.length; i++){
                boardCopy[j][i] = boardCopy[j][i - 1];
                boardCopy[j][i - 1] = Color.O;
            }
        }
        return boardCopy;
    }

    private boolean validCoordinate(int[] coordinate){
        if(coordinate[0] < 0 || coordinate[0] > 10 || coordinate[1] < 0 || coordinate[1] > 10){
            return false;
        }
        return true;
    }

    private void clearNeighbours(Color color, int[] coordinates, Color[][] boardCopy){
        if(boardCopy[coordinates[0]][coordinates[1]] == color){
            int[] newCoord = new int[2];
            boardCopy[coordinates[0]][coordinates[1]] = Color.O;

            // over
            newCoord[0] = coordinates[0] - 1;
            newCoord[1] = coordinates[1];
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, boardCopy);
            }

            // under
            newCoord[0] = coordinates[0] + 1;
            newCoord[1] = coordinates[1];
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, boardCopy);
            }

            // left
            newCoord[0] = coordinates[0];
            newCoord[1] = coordinates[1] - 1;
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, boardCopy);
            }

            // right
            newCoord[0] = coordinates[0];
            newCoord[1] = coordinates[1] + 1;
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, boardCopy);
            }
        }
    }
}
