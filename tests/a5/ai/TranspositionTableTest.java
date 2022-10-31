package a5.ai;

import static org.junit.jupiter.api.Assertions.*;

import a5.ai.TranspositionTable.StateInfo;
import a5.logic.Pente;
import a5.logic.Position;
import a5.logic.TicTacToe;
import cms.util.maybe.NoMaybeValue;
import org.junit.jupiter.api.Test;

class TranspositionTableTest {

    @Test
    void testConstructor(){
        // TODO 1: write at least 1 test case
        TranspositionTable<Pente> table = new TranspositionTable<>();
        Pente state = new Pente();
        //no entry as default
        assertEquals(0, table.size());
        //no state info as default
        assertThrows(NoMaybeValue.class, ()-> table.getInfo(state).get());
        //add one entry
        table.add(state, 0, GameModel.WIN);
        assertEquals(1, table.size());
    }


    @Test
    void getInfo() throws NoMaybeValue {
        // test case 1: add one entry at depth 0
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // test case 2: state2 created but not added into the table thus not found
        TicTacToe state2 = state.applyMove(new Position(0, 0));
        assertThrows(NoMaybeValue.class, () -> table.getInfo(state2).get());

        // TODO 2: write at least 3 more test cases

        //test case 3: add state2 to the table in depth 1
        table.add(state2, 1, 100);
        StateInfo info2 = table.getInfo(state2).get();
        assertEquals(100, info2.value());
        assertEquals(1, info2.depth());

        //test case 4: update state with depth 2 and value 2000 -> should overwrite depth 0 state (size should still be 2)
        table.add(state, 2, 2000);
        assertEquals(2000, info.value());
        assertEquals(2, info.depth());

        //test case 5: add state3 that has the different state with state2??
        TicTacToe state3 = state2.applyMove(new Position(0, 1));
        table.add(state3, 1, -10);
        StateInfo info3 = table.getInfo(state3).get();
        assertEquals(-10, info3.value());
        assertEquals(1, info3.depth());
        assertEquals(1, info2.depth());
    }

    @Test
    void add() throws NoMaybeValue {
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();

        // test case 1
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // TODO 3: write at least 3 more test cases
        //test case 2: add state2 to table -> size should update to 2
        TicTacToe state2 = state.applyMove(new Position(1, 1));
        table.add(state2, 1, 1000);
        assertEquals(2, table.size());

        //test case 3: overwrite state with updated depth and value -> size should still be 2
        table.add(state, 3, 10);
        assertEquals(2, table.size());

        //test case 4: add 11 nodes to the pente table and see if it grows (default bucket length = 10)
        //no overwrite in this case
        TranspositionTable<Pente> ptable = new TranspositionTable<>();
        Pente s1 = new Pente();
        Pente s2 = s1.applyMove(new Position(1,0));
        Pente s3 = s1.applyMove(new Position(1,1));
        Pente s4 = s1.applyMove(new Position(1,1));
        Pente s5 = s1.applyMove(new Position(0,1));
        Pente s6 = s1.applyMove(new Position(0,-1));
        Pente s7 = s1.applyMove(new Position(-1,1));
        Pente s8 = s1.applyMove(new Position(-1,-1));
        Pente s9 = s2.applyMove(new Position(1,1));
        Pente s10 = s3.applyMove(new Position(1,1));
        Pente s11 = s4.applyMove(new Position(1,1));

        ptable.add(s1, 0, 10);
        ptable.add(s2, 1, 100);
        ptable.add(s3, 2, 1000);
        ptable.add(s4, 3, -10);
        ptable.add(s5, 2, 10);
        ptable.add(s6, 4, 0);
        ptable.add(s7, 5, 100);
        ptable.add(s8, 6, -100);
        ptable.add(s9, 7, 10000);
        ptable.add(s10, 8, 100);
        ptable.add(s11, 8, 10);

        assertEquals(11, ptable.size());

    }
}