package a5.logic;

import static org.junit.jupiter.api.Assertions.*;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void testEquals() {
        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);

        // test 1
        assertEquals(board1, board2);

        // test 2
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1, board2);

        // TODO 1: write at least 3 test cases
    }

    @Test
    void testHashCode() {
        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);

        // test 1
        assertEquals(board1.hashCode(), board2.hashCode());

        // test 2
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1.hashCode(), board2.hashCode());

        // TODO 2: write at least 3 test cases
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