package lk.ijse.dep.service;

public class AiPlayer extends Player{ // The AiPlayer class inherits the Player Class by using the "extend" keyword.

    public AiPlayer(Board newBoard) {
        super(newBoard);
    }

    /*public void movePiece(int col) {
        int randomNumber;
        do{
            randomNumber = (int) (Math.random() * 6);
        }while (!board.isLegalMove(randomNumber));board.updateMove(randomNumber,Piece.GREEN);
        board.getBoardUI().update(randomNumber, false);
        Winner winner = board.findWinner();
        if (winner != null) {
            board.getBoardUI().notifyWinner(winner);
        }else if(!board.existLegalMoves()) {
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }
    }*/
    @Override
    public void movePiece(int col) {
        int maxEval = (int) Double.NEGATIVE_INFINITY;
        int row = 0;
        for (int tempCol = 0; tempCol < board.NUM_OF_COLS; tempCol++) {
            if(board.isLegalMove(tempCol)) {
                int tempRow = board.findNextAvailableSpot(tempCol);
                board.updateMove(tempCol, tempRow, Piece.GREEN);
                int heuristicVal = minimax(0, false);
                board.updateMove(tempCol, tempRow, Piece.EMPTY);
                if(maxEval < heuristicVal) {
                    maxEval = heuristicVal;
                    col = tempCol;
                    row = tempRow;
                }
            }
        }
        board.updateMove(col, row, Piece.GREEN);
        board.getBoardUI().update(col,false);
        Winner winner = board.findWinner();
        if(winner != null) {
            board.getBoardUI().notifyWinner(winner);
        }else if(!board.existLegalMoves()) {
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }
    }

    int minimax(int depth, boolean maximizingPlayer) {
        Winner winner = board.findWinner();
        // Checks whether the player has won or maximum depth has been reached.
        if(depth == 4 || winner != null) {
            if (winner == null) {
                return 0;
            }
            if (winner.getWinningPiece() == Piece.GREEN) {
                return 1;
            }
            if (winner.getWinningPiece() == Piece.BLUE) {
                return -1;
            }
        }
        if (maximizingPlayer) {
            int maxEval = (int) Double.NEGATIVE_INFINITY;
            for (int col = 0; col < 6; col++) {
                if(board.isLegalMove(col)) {
                    int row = board.findNextAvailableSpot(col);
                    board.updateMove(col, row, Piece.GREEN);
                    int heuristicVal = minimax(depth + 1, false);
                    board.updateMove(col, row, Piece.EMPTY);
                    maxEval = Math.max(heuristicVal, maxEval);
                }
            }
            return maxEval;
        }else{
            int minEval = (int) Double.POSITIVE_INFINITY;
            for (int col = 0; col < 6; col++) {
                if(board.isLegalMove(col)) {
                    int row = board.findNextAvailableSpot(col);
                    board.updateMove(col, row, Piece.BLUE);
                    int heuristicVal = minimax(depth + 1, true);
                    board.updateMove(col, row, Piece.EMPTY);
                    minEval = Math.min(heuristicVal, minEval);
                }
            }
            return minEval;
        }
    }
}
