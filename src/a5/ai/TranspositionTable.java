package a5.ai;

import cms.util.maybe.Maybe;

/**
 * A transposition table for an arbitrary game. It maps a game state
 * to a search depth and a heuristic evaluation of that state to the
 * recorded depth. Unlike a conventional map abstraction, a state is
 * associated with a depth, so that clients can look for states whose
 * entry has at least the desired depth.
 *
 * @param <GameState> A type representing the state of a game.
 */
public class TranspositionTable<GameState> {

    /**
     * Information about a game state, for use by clients.
     */
    public interface StateInfo {

        /**
         * The heuristic value of this game state.
         */
        int value();

        /**
         * The depth to which the game tree was searched to determine the value.
         */
        int depth();
    }

    /**
     * A Node is a node in a linked list of nodes for a chaining-based implementation of a hash
     * table.
     *
     * @param <GameState>
     */
    static private class Node<GameState> implements StateInfo {
        /**
         * The state
         */
        GameState state;
        /**
         * The depth of this entry. >= 0
         */
        int depth;
        /**
         * The value of this entry.
         */
        int value;
        /**
         * The next node in the list. May be null.
         */
        Node<GameState> next;

        Node(GameState state, int depth, int value, Node<GameState> next) {
            this.state = state;
            this.depth = depth;
            this.value = value;
            this.next = next;
        }

        public int value() {
            return value;
        }

        public int depth() {
            return depth;
        }
    }

    /**
     * The number of entries in the transposition table.
     */
    private int size;

    /**
     * The buckets array may contain null elements.
     * Class invariant:
     * All transposition table entries are found in the linked list of the
     * bucket to which they hash, and the load factor is no more than 1.
     */
    private Node<GameState>[] buckets;

    // TODO 1: implement the classInv() method. You may also
    // strengthen the class invariant. The classInv()
    // method is likely to be expensive, so you may want to turn
    // off assertions in this file, but only after you have the transposition
    // table fully tested and working.
    boolean classInv() {
           return false;
    }

    @SuppressWarnings("unchecked")
    /** Creates: a new, empty transposition table. */
    TranspositionTable() {
        // TODO 2
        size = 0;
        buckets = new Node[5];
    }

    /** The number of entries in the transposition table. */
    public int size() {
        return size;
    }

    /**
     * Returns: the information in the transposition table for a given
     * game state, package in an Optional. If there is no information in
     * the table for this state, returns an empty Optional.
     */
    public Maybe<StateInfo> getInfo(GameState state) {
        // TODO 3
        if (buckets == null) return Maybe.none();
        int index = getBucketIndex(state);
        Node<GameState> head = buckets[index];
        while(head != null){
            if (head.state.equals(state))
                return Maybe.some(head);
            head = head.next;
        }
        return Maybe.none();
    }

    /**
     * Effect: Add a new entry in the transposition table for a given
     * state and depth, or overwrite the existing entry for this state
     * with the new state and depth. Requires: if overwriting an
     * existing entry, the new depth must be greater than the old one.
     */
    public void add(GameState state, int depth, int value) {
        // TODO 4
        int index = getBucketIndex(state);
        Node<GameState> head = buckets[index];
        // create a new node
        Node<GameState> node = new Node<GameState>(state, depth, value, null);
        // overwrite the existing entry
        if (head == null){
            buckets[index] = node;
            size++;
        }else{
            while (head != null){
                if (head.state.equals(state)) {
                    head.value = value;
                    head.depth = depth;
                } else {
                    if (head.next == null){
                        head.next = node;
                        size++;
                    }
                }
                head = head.next;
            }
        }
    }

    /**
     * Effect: Make sure the hash table has at least {@code target} buckets.
     * Returns true if the hash table actually resized.
     */
    private boolean grow(int target) {
        // TODO 5
        int n = buckets.length;
        if (target < n-1){
            size = 0;
            Node<GameState>[] newBuckets = new Node[2 * n];
            for(Node<GameState> head : buckets){
                while(head != null){
                    int hashCode = head.state.hashCode();
                    int index = hashCode % newBuckets.length;
                    // state -> key
                    newBuckets[index] = head;
                    // null -> new node, not null -> next
                    if (head != null){ // Ask TA
                        head = head.next;
                    }else{
                        head = new Node<GameState>(head.state, head.depth, head.value, null);
                        head = head.next;
                    }
                }
            }
            buckets = newBuckets;
            return true;
        }else{
            return false;
        }

    }

    // You may want to write some additional helper methods.
    private int getBucketIndex(GameState state){
        int hashCode = state.hashCode();
        int index = Math.abs(hashCode % buckets.length);
        return index;
    }


    /**
     * Estimate clustering. With a good hash function, clustering
     * should be around 1.0. Higher values of clustering lead to worse
     * performance.
     */
    double estimateClustering() {
        final int N = 500;
        int m = buckets.length, n = size;
        double sum2 = 0;
        for (int i = 0; i < N; i++) {
            int j = Math.abs((i * 82728353) % buckets.length);
            int count = 0;
            Node<GameState> node = buckets[j];
            while (node != null) {
                count++;
                node = node.next;
            }
            sum2 += count*count;
        }
        double alpha = (double)n/m;
        return sum2/(N * alpha * (1 - 1.0/m + alpha));
    }
}
