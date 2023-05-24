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
}
