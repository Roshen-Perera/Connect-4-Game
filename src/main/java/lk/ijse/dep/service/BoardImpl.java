package lk.ijse.dep.service;

import static lk.ijse.dep.service.Piece.EMPTY;

public class BoardImpl implements Board { // The BoardImpl class inherits the Board Interface by using the "implements" keyword.
    private Piece[][] pieces;
    private BoardUI boardUI;
    public BoardImpl(BoardUI boardUI){
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        for(int i = 0; i < NUM_OF_COLS; i++) {
            for(int j = 0; j < NUM_OF_ROWS; j++) {
                pieces[i][j] = EMPTY;
            }
        }
        this.boardUI = boardUI;
    }


    @Override
    public BoardUI getBoardUI() {
        return this.boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col)  {
       for (int i = 0; i < NUM_OF_ROWS; i++){
           if(this.pieces[col][i] == EMPTY) {
               return i;
           }
       }
       return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        return findNextAvailableSpot(col) != -1;
    }

    @Override
    public boolean existLegalMoves() {
        for(int i = 0; i < NUM_OF_COLS; i++) {
            for(int j = 0; j < NUM_OF_ROWS; j++) {
                if(pieces[i][j] == EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)] = move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        this.pieces[col][row] = move;
    }

    @Override
    public Winner findWinner() {
        for (int i = 0; i < NUM_OF_ROWS; i++){
            for (int j = 0; j< NUM_OF_COLS / 2; j++){
                if((!pieces[j][i].equals(Piece.EMPTY)) && pieces[j][i].equals(pieces[j+1][i]) && pieces[j][i].equals(pieces[j+2][i]) && pieces[j][i].equals(pieces[j+3][i])){
                    return new Winner(pieces[j][i],j,i,j+3,i);
                }
            }
        }

        for (int j = 0; j < NUM_OF_COLS; j++){
            for (int i = 0; i < NUM_OF_ROWS / 2; i++){
                if (!pieces[j][i].equals(Piece.EMPTY) && pieces[j][i].equals(pieces[j][i+1]) && pieces[j][i].equals(pieces[j][i+2]) && pieces[j][i].equals(pieces[j][i+3])){
                    return new Winner(pieces[j][i],j,i,j,i+3);
                }
            }
        }
        return null;
    }
}
