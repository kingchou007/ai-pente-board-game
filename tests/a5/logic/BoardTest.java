package a5.logic;

import static org.junit.jupiter.api.Assertions.*;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void testEquals() {
        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);
        Board board3 = new Board(8, 8);

        // test 1: two boards with same rowSize and colSize without placing stones should be equal
        assertEquals(board1, board2);

        // test 2: place stone on board2, now two boards are not equal.
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1, board2);

        // TODO 1: write at least 3 test cases

        // test 3: two board with different rowSize and colSize should not be equal
        assertNotEquals(board1, board3);

        // test 4: same player placing at the same position on two boards with same dimension should be equal
        board1.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertEquals(board1, board2);

        // test 5: different player placing at the same position on two boards with same dimension should not be equal
        Board board4 = new Board(3,3);
        board4.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        assertNotEquals(board1, board4);
    }

    @Test
    void testHashCode() {
        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);
        Board board3 = new Board(3, 3);

        // test 1: hashcode for two board with same rowSize and colSize should be equal.
        assertEquals(board1.hashCode(), board2.hashCode());

        // test 2: one board being placed a stone, hashcode for two boards should not be equal.
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1.hashCode(), board2.hashCode());

        // TODO 2: write at least 3 test cases

        // test 3: hashcode for two board with different dimensions should not be equal.
        board3.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        assertNotEquals(board2.hashCode(), board3.hashCode());

        // test 4: two same dimension boards placing the stone in the same place, hashcode for two boards should be equal.
        board1.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertEquals(board1.hashCode(), board2.hashCode());

        // test 5: two same dimension boards placing stone in different places
        board1.place(new Position(1, 0), PlayerRole.SECOND_PLAYER);
        assertNotEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    void get() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        // test 1
        assertEquals(0, board.get(p));

        // test 2
        board.place(p, PlayerRole.FIRST_PLAYER);
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), board.get(p));
    }

    @Test
    void place() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        assertEquals(0, board.get(p));
        board.place(p, PlayerRole.SECOND_PLAYER);
        assertEquals(PlayerRole.SECOND_PLAYER.boardValue(), board.get(p));
    }

    @Test
    void erase() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        assertEquals(0, board.get(p));
        board.place(p, PlayerRole.SECOND_PLAYER);
        assertEquals(PlayerRole.SECOND_PLAYER.boardValue(), board.get(p));

        // test 1
        board.erase(p);
        assertEquals(0, board.get(p));
    }

    @Test
    void rowSize() {
        Board board = new Board(5, 6);
        assertEquals(5, board.rowSize());
    }

    @Test
    void colSize() {
        Board board = new Board(5, 6);
        assertEquals(6, board.colSize());
    }

    @Test
    void validPos() {
        Board board = new Board(5, 6);
        Position p = new Position(3, 3);
        // test 1
        assertTrue(board.validPos(p));
        board.place(p, PlayerRole.FIRST_PLAYER);

        // test 2
        assertFalse(board.validPos(p));

        // test 3
        p = new Position(5, 5);
        assertFalse(board.validPos(p));
    }

    @Test
    void onBoard() {
        Board board = new Board(5, 6);
        Position p = new Position(3, 3);
        // test 1
        assertTrue(board.onBoard(p));
        // test 2
        board.place(p, PlayerRole.FIRST_PLAYER);
        assertTrue(board.onBoard(p));
        // test 3
        p = new Position(5, 5);
        assertFalse(board.onBoard(p));
    }
}