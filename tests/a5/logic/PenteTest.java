package a5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PenteTest {
    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        // test case 1
        Pente pente1 = new Pente();
        assertEquals(8, pente1.colSize());
        assertEquals(8, pente1.colSize());
        assertEquals(0, pente1.captureWhite());
        assertEquals(0, pente1.captureBlack());

    }

    @Test
    void testCopyConstructor() {
        // TODO 2: write at least 3 test cases
        // ???
        // test 1: copy empty constructor, state should be equal (not sure which assertion should be used here but all three can pass)
        Pente pente1 = new Pente();
        Pente cpente1 = new Pente(pente1);
        assertTrue(pente1.stateEqual(cpente1));
        assertTrue(pente1.equals(cpente1));
        assertEquals(pente1, cpente1);

        // test 2:
        pente1.makeMove(new Position(1, 0));
        Pente cpente2 = new Pente(pente1);
        assertTrue(pente1.stateEqual(cpente2));
        assertEquals(1, cpente2.board().get(new Position(1, 0)));

        // test 3
    }

    @Test
    void testHashCode() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test case 1: two games make the same move should have the same hashcode.
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertEquals(game1.hashCode(), game2.hashCode());

        // test case 2: two games make different moves should have different hashcodes.
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());

        // TODO 3: write at least 3 test cases

        // test 3:
        // test 4
        // test 5
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

        // test 1
        // test 2
        // test 3
    }

    @Test
    void hasEnded() {
        // TODO 6: write at least 3 test cases

        // test 1
        // test 2
        // test 3
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
        // test 4
        // test 5

    }

}