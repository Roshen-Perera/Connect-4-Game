package lk.ijse.dep.service;

public class HumanPlayer extends Player{ // The AiPlayer class inherits the Player Class by using the "extend" keyword.

    public HumanPlayer(Board newBoard) {
        super(newBoard);
    }

    @Override
    public void movePiece(int col) {
        if(board.isLegalMove(col)){
            board.updateMove(col,Piece.BLUE);
            board.getBoardUI().update(col,true);
            Winner winner = board.findWinner();
            if(winner != null) {
                board.getBoardUI().notifyWinner(winner);
            }else if(!board.existLegalMoves()){
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }
    }
}
