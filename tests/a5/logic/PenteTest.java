package a5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class PenteTest {
    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        // test case 1
        Pente pente1 = new Pente();
        assertEquals(8, pente1.colSize());
        assertEquals(8, pente1.colSize());
        assertEquals("Pente", pente1.gameType().toString());
    }

    @Test
    void testCopyConstructor() {
        // TODO 2: write at least 3 test cases
        // test 1: copy empty constructor, state should be equal (not sure which assertion should be used here but all three can pass)
        Pente pente1 = new Pente();
        Pente cpente1 = new Pente(pente1);
        assertTrue(pente1.stateEqual(cpente1));
        assertTrue(pente1.equals(cpente1));
        assertEquals(pente1, cpente1);

        // test 2: check state
        pente1.makeMove(new Position(1, 0));
        Pente cpente2 = new Pente(pente1);
        assertTrue(pente1.stateEqual(cpente2));
        assertEquals(1, cpente2.board().get(new Position(1, 0)));

        // test 3: check the current player
        Pente pente3 = new Pente();
        pente3.makeMove(new Position(5, 7));
        Pente pente4 = new Pente(pente3);
        assertEquals(pente3.currentPlayer(), pente4.currentPlayer());
    }

    @Test
    void testHashCode() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();
        Pente game4 = new Pente();
        Pente game5 = new Pente();
        Pente game6 = new Pente();
        Pente game7 = new Pente();

        // test case 1: two games make the same move should have the same hashcode.
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertEquals(game1.hashCode(), game2.hashCode());

        // test case 2: two games make different moves should have different hashcodes.
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());

        // TODO 3: write at least 3 test cases
        // test 3: two games make same moves with capture should have same hashcode
        game4.makeMove(new Position(3, 1));
        game4.makeMove(new Position(3, 2));
        game4.makeMove(new Position(2, 1));
        game4.makeMove(new Position(3, 3));
        game4.makeMove(new Position(3, 4));
        game5.makeMove(new Position(3, 1));
        game5.makeMove(new Position(3, 2));
        game5.makeMove(new Position(2, 1));
        game5.makeMove(new Position(3, 3));
        game5.makeMove(new Position(3, 4));
        assertEquals(game4.hashCode(), game5.hashCode());

        // test 4: two games with different moves (with capture) should have different hashcodes
        game6.makeMove(new Position(5, 1));
        game6.makeMove(new Position(4, 2));
        game6.makeMove(new Position(5, 2));
        game6.makeMove(new Position(3, 3));
        game6.makeMove(new Position(2, 4));
        assertNotEquals(game4.hashCode(), game6.hashCode());

        // test 5: two games make same moves but one game with capture should have different hashcodes
        game7.makeMove(new Position(3,1));
        game7.changePlayer();
        game7.makeMove(new Position(2,1));
        game7.changePlayer();
        game7.makeMove(new Position(3,4));
        assertNotEquals(game4.hashCode(), game7.hashCode());
    }

    @Test
    void makeMove() {
        // TODO 4: write at least 3 test cases
        Pente game1 = new Pente();

        // test 1: make an illegal move (out of board) -> false
        assertFalse(game1.makeMove(new Position(8, 8)));

        // test 2: legal move -> true
        assertTrue(game1.makeMove(new Position(0, 0)));
        assertEquals(1, game1.board().get(new Position(0, 0)));

        // test 3: illegal move (already occupied) -> false
        assertFalse(game1.makeMove(new Position (0, 0)));
    }

    @Test
    void capturedPairsNo() {
        // TODO 5: write at least 3 test cases
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();
        Pente game4 = new Pente();

        // test 1: one pair
        game1.makeMove(new Position(3, 1));
        game1.makeMove(new Position(3, 2));
        game1.makeMove(new Position(2, 1));
        game1.makeMove(new Position(3, 3));
        game1.makeMove(new Position(3, 4));
        assertEquals(1, game1.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));

        // test2
        game2.makeMove(new Position(3, 1));
        game2.makeMove(new Position(3, 2));
        game2.makeMove(new Position(6, 4));
        game2.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 5));
        game2.makeMove(new Position(4, 4));
        game2.makeMove(new Position(4, 5));
        game2.makeMove(new Position(5, 4));
        game2.makeMove(new Position(3, 4));
        assertEquals(2, game2.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game2.capturedPairsNo(PlayerRole.SECOND_PLAYER));

        // test3: two player 0 pair
        game3.makeMove(new Position(3,3));
        game3.makeMove(new Position(3,2));
        assertEquals(0, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game3.capturedPairsNo(PlayerRole.SECOND_PLAYER));

        // test4: diagonal move
        game4.makeMove(new Position(4,3));
        game3.makeMove(new Position(5,4));
        game3.makeMove(new Position(0,0));
        game3.makeMove(new Position(6,5));
        game3.makeMove(new Position(7,6));
        assertEquals(0, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
    }

    @Test
    void hasEnded() {
        // TODO 6: write at least 3 test cases
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test 1: capture 5 -> success
        game1.makeMove(new Position(4, 1));
        game1.makeMove(new Position(4, 0));
        game1.makeMove(new Position(4, 2));
        game1.makeMove(new Position(4, 3));
        // System.out.println(game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game1.makeMove(new Position(3, 3));
        game1.makeMove(new Position(5, 0));
        game1.makeMove(new Position(2, 3));
        game1.makeMove(new Position(1, 3));
        // System.out.println(game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game1.makeMove(new Position(5, 3));
        game1.makeMove(new Position(7, 2));
        game1.makeMove(new Position(6, 3));
        game1.makeMove(new Position(7, 3));
        // System.out.println(game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        game1.makeMove(new Position(5, 4));
        game1.makeMove(new Position(4, 7));
        game1.makeMove(new Position(6, 5));
        game1.makeMove(new Position(7, 6));
        // System.out.println(game1.capturedPairsNo(PlayerRole.SECOND_PLAYER)); // problem here
        game1.makeMove(new Position(4, 4));
        game1.makeMove(new Position(7, 5));
        game1.makeMove(new Position(4, 5));
        game1.makeMove(new Position(4, 6));
        assertTrue(game1.hasEnded());

        // test 2: no pair
        game2.makeMove(new Position(4, 1));
        game2.makeMove(new Position(4, 0));
        game2.makeMove(new Position(4, 2));
        assertFalse(game2.hasEnded());

        // test 3: test super.hasEnded
        game3.makeMove(new Position(7, 0));
        game3.makeMove(new Position(6, 0));
        game3.makeMove(new Position(7, 1));
        game3.makeMove(new Position(6, 1));
        game3.makeMove(new Position(7, 2));
        game3.makeMove(new Position(6, 2));
        game3.makeMove(new Position(7, 3));
        game3.makeMove(new Position(6, 3));
        game3.makeMove(new Position(7, 4));
        assertTrue(game3.hasEnded());
    }

    @Test
    void stateEqual() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test case 1
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertTrue(game1.stateEqual(game2));
        assertTrue(game2.stateEqual(game1));

        // test case 2
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());
        assertFalse(game1.equals(game3));

        // TODO 7: write at least 3 test cases

        // test 3
        TicTacToe game4= new TicTacToe();
        Pente game5 = new Pente();
        game4.makeMove(new Position(1, 1));
        game5.makeMove(new Position(1, 1));
        assertNotEquals(game4.hashCode(), game5.hashCode());

        // test 4
        Pente game6 = new Pente();
        Pente game7 = new Pente();
        game6.makeMove(new Position(4, 1));
        game6.makeMove(new Position(4, 0));
        game6.makeMove(new Position(4, 2));
        game6.makeMove(new Position(4, 3));
        game7.makeMove(new Position(4, 1));
        game7.makeMove(new Position(4, 0));
        game7.makeMove(new Position(4, 2));
        game7.makeMove(new Position(4, 3));
        assertTrue(game6.stateEqual(game7));

        // test 5
        Pente game8 = new Pente();
        game8.makeMove(new Position(4, 1));
        game8.makeMove(new Position(4, 3));
        assertFalse(game6.stateEqual(game8));

    }

}