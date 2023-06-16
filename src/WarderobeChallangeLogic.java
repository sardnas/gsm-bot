import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public class WarderobeChallangeLogic {
    enum Color {
        PINK,
        PURPLE,
        GREEN,
        BLUE,
        CLEAR
    }

    Color[][] board = new Color[12][11];
    int[] colorCounter;
    Color[][] initialBoard;
    int[] initialColorCount;

    public WarderobeChallangeLogic(Color[][] theBoard){
/*
        Color[] initialValues = new Color[4];
        initialValues[0] = Color.PINK;
        initialValues[1] = Color.PURPLE;
        initialValues[2] = Color.GREEN;
        initialValues[3] = Color.BLUE;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                board[i][j] = initialValues[ThreadLocalRandom.current().nextInt(0, 4)];
            }
        }*/
        board = theBoard;
        initialBoard = copyOfCurrentBoard();
        colorCounter = countColors(board);
        initialColorCount = countColors(board);
    }

    public void resetBoard(){
        board = initialBoard;
        board = copyOfCurrentBoard();
        for(int i = 0; i < colorCounter.length; i++){
            colorCounter[i] = initialColorCount[i];
        }
    }

    public boolean checkForWin(){
        if(colorCounter[0] == 0 && colorCounter[1] == 0 && colorCounter[2] == 0 && colorCounter[3] == 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkForLose(){
        if(colorCounter[0] == 1 || colorCounter[1] == 1 || colorCounter[2] == 1 || colorCounter[3] == 1){
            return true;
        }else{
            return false;
        }
    }


    public ArrayList<int[]> getClickableCoordinates(){
        ArrayList<int[]> clickableList = new ArrayList<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != Color.CLEAR){
                    // over
                    int[] newCoordOne = new int[2];
                    newCoordOne[0] = i - 1;
                    newCoordOne[1] = j;
                    if(validCoordinate(newCoordOne) && board[newCoordOne[0]][newCoordOne[1]] == board[i][j]){
                        clickableList.add(newCoordOne);
                        continue;
                    }

                    // under
                    int[] newCoordTwo = new int[2];
                    newCoordTwo[0] = i + 1;
                    newCoordTwo[1] = j;
                    if(validCoordinate(newCoordTwo) && board[newCoordTwo[0]][newCoordTwo[1]] == board[i][j]){
                        clickableList.add(newCoordTwo);
                        continue;
                    }

                    // left
                    int[] newCoordThree = new int[2];
                    newCoordThree[0] = i;
                    newCoordThree[1] = j - 1;
                    if(validCoordinate(newCoordThree) && board[newCoordThree[0]][newCoordThree[1]] == board[i][j]){
                        clickableList.add(newCoordThree);
                        continue;
                    }

                    // right
                    int[] newCoordFour = new int[2];
                    newCoordFour[0] = i;
                    newCoordFour[1] = j + 1;
                    if(validCoordinate(newCoordFour) && board[newCoordFour[0]][newCoordFour[1]] == board[i][j]){
                        clickableList.add(newCoordFour);
                    }
                }
            }
        }

        return clickableList;
    }

    private int[] countColors(Color[][] board){
        int[] colors = new int[4];
        int pinkCount = 0;
        int purpleCount = 0;
        int greenCount = 0;
        int blueCount = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                switch(this.board[i][j]) {
                    case PINK:
                        pinkCount++;
                        break;
                    case PURPLE:
                        purpleCount++;
                        break;
                    case GREEN:
                        greenCount++;
                        break;
                    case BLUE:
                        blueCount++;
                        break;
                    default:
                        break;
                }
            }
        }
        colors[0] = pinkCount;
        colors[1] = purpleCount;
        colors[2] = greenCount;
        colors[3] = blueCount;

        return colors;
    }

    private Color[][] copyOfCurrentBoard(){
        Color[][] boardCopy = new Color[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return boardCopy;
    }

    public Color[][] getBoard() {
        return this.board;
    }

    public void setBoard(int[] coordinates) {
        switch(this.board[coordinates[0]][coordinates[1]]) {
            case PINK:
                clearNeighbours(Color.PINK, coordinates, this.board, true);
                break;
            case PURPLE:
                clearNeighbours(Color.PURPLE, coordinates, this.board, true);
                break;
            case GREEN:
                clearNeighbours(Color.GREEN, coordinates, this.board, true);
                break;
            case BLUE:
                clearNeighbours(Color.BLUE, coordinates, this.board, true);
                break;
            default:
                break;
        }
        moveDown();
        moveRight();
    }

    private void moveDown(){
        Color[][] boardCopy = copyOfCurrentBoard();

        for (int j = 0; j < boardCopy[0].length; j++) {
            int emptyRow = boardCopy.length - 1;  // Track the empty row position

            // Iterate from bottom to top
            for (int i = boardCopy.length - 1; i >= 0; i--) {
                if (boardCopy[i][j] != Color.CLEAR) {
                    // Move the non-empty cell to the empty row
                    boardCopy[emptyRow][j] = boardCopy[i][j];
                    emptyRow--;
                }
            }

            // Fill the remaining empty cells with Color.O
            while (emptyRow >= 0) {
                boardCopy[emptyRow][j] = Color.CLEAR;
                emptyRow--;
            }
        }

        this.board = boardCopy;
    }

    private void moveRights() {
        Color[][] boardCopy = copyOfCurrentBoard();
        int rows = boardCopy.length;
        int cols = boardCopy[0].length;
        ArrayList<Integer> emptyCols = new ArrayList<>();

        // Check if the last row is entirely clear
        boolean isLastRowClear = false;
        for (int j = cols - 1; j > 0; j--) {
            if (boardCopy[rows - 1][j] == Color.CLEAR) {
                isLastRowClear = true;
                emptyCols.add(j);
            }
        }

        if (isLastRowClear) {
            // Move each column to the left by shifting all cells one step to the right
            for (int j = cols - 1; j > 0; j--) {
                for (int i = 0; i < rows; i++) {
                    boardCopy[i][j] = boardCopy[i][j - 1];
                }
            }

            // Clear the first column
            for (int i = 0; i < rows; i++) {
                boardCopy[i][0] = Color.CLEAR;
            }
        }

        this.board = boardCopy;
    }



    private void moveRight() {
        Color[][] boardCopy = copyOfCurrentBoard();
        ArrayList<Integer> emptyColumnCounter = new ArrayList();

        for(int i = 0; i < boardCopy[0].length; i++) {
            if (boardCopy[boardCopy.length - 1][i] == Color.CLEAR) {
                emptyColumnCounter.add(i);
            }
        }
        for (int column : emptyColumnCounter) {
            boardCopy = moveRightHelper(column);
            this.board = boardCopy;
        }
    }



    private Color[][] moveRightHelper(int xCoordinate){
        Color[][] boardCopy = copyOfCurrentBoard();

        if(xCoordinate != 10){
            for(int i = 0; i < xCoordinate; i++){
                for(int j = 0; j < boardCopy.length ; j++){
                    boardCopy[j][i] = Color.CLEAR;
                }
            }
            for(int i = 0; i < xCoordinate; i++){
                for(int j = 0; j < boardCopy.length; j++){
                    boardCopy[j][i + 1] = board[j][i];
                }
            }
        }else{
            for(int i = 0; i < xCoordinate; i++){
                for(int j = 0; j < boardCopy.length ; j++){
                    boardCopy[j][i] = Color.CLEAR;
                }
            }
            for(int i = 1; i < xCoordinate; i++){
                for(int j = 0; j < boardCopy.length; j++){
                    boardCopy[j][i] = board[j][i - 1];
                }
            }
        }
/*
        // Move each column to the left by shifting all cells one step to the right
        for (int j = cols - 1; j > 0; j--) {
            for (int i = 0; i < rows; i++) {
                boardCopy[i][j] = boardCopy[i][j - 1];
            }
        }

        // Clear the first column
        for (int i = 0; i < rows; i++) {
            boardCopy[i][0] = Color.CLEAR;
        }
*/
        return boardCopy;
    }


    private boolean validCoordinate(int[] coordinate){
        if(coordinate[0] < 0 || coordinate[0] > 11 || coordinate[1] < 0 || coordinate[1] > 10){
            return false;
        }
        return true;
    }

    private void clearNeighbours(Color color, int[] coordinates, Color[][] currentBoard, boolean firstIteration){
        if(currentBoard[coordinates[0]][coordinates[1]] == color){
            int[] newCoord = new int[2];

            if(!firstIteration){
                currentBoard[coordinates[0]][coordinates[1]] = Color.CLEAR;
                switch(color) {
                    case PINK:
                        colorCounter[0]--;
                        break;
                    case PURPLE:
                        colorCounter[1]--;
                        break;
                    case GREEN:
                        colorCounter[2]--;
                        break;
                    case BLUE:
                        colorCounter[3]--;
                        break;
                    default:
                        break;
                }
            }
            // over
            newCoord[0] = coordinates[0] - 1;
            newCoord[1] = coordinates[1];
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, currentBoard, false);
            }

            // under
            newCoord[0] = coordinates[0] + 1;
            newCoord[1] = coordinates[1];
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, currentBoard, false);
            }

            // left
            newCoord[0] = coordinates[0];
            newCoord[1] = coordinates[1] - 1;
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, currentBoard, false);
            }

            // right
            newCoord[0] = coordinates[0];
            newCoord[1] = coordinates[1] + 1;
            if(validCoordinate(newCoord)){
                clearNeighbours(color, newCoord, currentBoard, false);
            }
        }
    }
}
