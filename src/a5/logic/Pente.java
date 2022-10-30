package a5.logic;

import a5.util.PlayerRole;
import a5.util.GameType;
import a5.util.GameResult;

import java.util.Arrays;


/**
 * A Pente game, where players take turns to place stones on board.
 * When consecutive two stones are surrounded by the opponent's stones on two ends,
 * these two stones are removed (captured).
 * A player wins by placing 5 consecutive stones or capturing stones 5 times.
 */
public class Pente extends MNKGame {

    private final int capture = 5;
    private int captureWhite;
    private int captureBlack;

    /**
     * Create an 8-by-8 Pente game.
     */
    public Pente() {
        super(8, 8, 5);
        // TODO 1
        captureWhite = 0;
        captureBlack = 0;

    }

    /**
     * Creates: a copy of the game state.
     */
    public Pente(Pente game) {
        super(game);
        // TODO 2
        captureBlack = game.captureBlack;
        captureWhite = game.captureWhite;
    }

    @Override
    public boolean makeMove(Position p) {
        // TODO 3
        if (!board().validPos(p))
            return false;

        // stones
        board().place(p, currentPlayer());

        // 8 directions
        // -1,1  | 0,1  | 1,1
        // -1,0  | 0,0  | 1,0
        // -1,-1 | 0,-1 | 1,-1
        int[][] directions = {{-1,1},{0,1},{1,1},{-1,0},{1,0},{-1,-1},{0,-1},{1,-1}};
        if(board().get(p) != 0){
            for (int[] direction : directions){ // step : steps
                Position p1 = new Position(p.row()+direction[0], p.col()+direction[1]);
                Position p2 = new Position(p1.row()+direction[0], p1.col()+direction[1]);
                Position p3 = new Position(p2.row()+direction[0], p2.col()+direction[1]);

                // board.get() -> return {@code boardValue}. ask TA
                if(board().onBoard(p1) && board().onBoard(p2) && board().onBoard(p3)){
                    if(board().get(p) != board().get(p1) && board().get(p1) == board().get(p2)
                            && board().get(p) == board().get(p3)) {
                        board().erase(p1);
                        board().erase(p2);
                        int status = 1;
                        if(currentPlayer().boardValue() == status){
                            captureWhite++;
                        }else{
                            captureBlack++;
                        }
                    }
                }
            }
        }
        changePlayer();
        advanceTurn();

        return true;
    }

    /**
     * Returns: a new game state representing the state of the game after the current player takes a
     * move {@code p}.
     */
    public Pente applyMove(Position p) {
        Pente newGame = new Pente(this);
        newGame.makeMove(p);
        return newGame;
    }

    /**
     * Returns: the number of captured pairs by {@code playerRole}.
     */
    public int capturedPairsNo(PlayerRole playerRole) {
        // TODO 4
        if(playerRole.boardValue() == 1){
            return captureWhite;
        }else{
            return captureBlack;
        }
    }

    @Override
    public boolean hasEnded() {
        // TODO 5

        // not sure
        return capturedPairsNo(this.currentPlayer()) == capture || super.hasEnded();
    }

    @Override
    public GameType gameType() {
        return GameType.PENTE;
    }


    @Override
    public String toString() {
        String board = super.toString();
        return board + System.lineSeparator() + "Captured pairs: " +
                "first: " + capturedPairsNo(PlayerRole.FIRST_PLAYER) + ", " +
                "second: " + capturedPairsNo(PlayerRole.SECOND_PLAYER);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Pente p = (Pente) o;
        return stateEqual(p);
    }

    /**
     * Returns: true if the two games have the same state.
     */
    protected boolean stateEqual(Pente p) {
        // TODO 6
        if (!board().equals(p.board())) return false;
        if (super.countToWin() != p.countToWin()) return false;
        if (captureBlack != p.captureBlack || captureWhite != p.captureWhite) return false;
        return true;
    }

    @Override
    public int hashCode() {
        // TODO 7

        // ask TA
        return Arrays.hashCode(new int[]{
                super.hashCode(),
                capture});
    }
}
