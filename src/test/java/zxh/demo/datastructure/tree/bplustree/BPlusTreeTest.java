package zxh.demo.datastructure.tree.bplustree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class BPlusTreeTest {
    /**
     *                  7
     *           /              \
     *        3|5                9
     *    /    |    \          /   \
     * 1|2 -> 3|4 -> 5|6 -> 7|8 -> 9|10
     */
    @Test
    void should_build_b_plus_tree() {
        // given
        BPlusTree<Integer, String> tree = new BPlusTree<>(4);

        // when
        tree.add(1, "1");
        tree.add(2, "2");
        tree.add(3, "3");
        tree.add(4, "4");
        tree.add(5, "5");
        tree.add(6, "6");
        tree.add(7, "7");
        tree.add(8, "8");
        tree.add(9, "9");
        tree.add(10, "10");

        // then level-0
        BptNode<Integer> root = tree.getRoot();
        assertThat(root instanceof InternalNode, is(true));
        InternalNode<Integer>.INodePair[] pairs = ((InternalNode<Integer>) root).getPairs();
        assertThat(pairs.length, is(2));
        assertThat(pairs[1].getKey(), is(7));

        // then level-1
        InternalNode<Integer> l11 = (InternalNode<Integer>) pairs[0].getPointer();
        InternalNode<Integer>.INodePair[] l11Pairs = l11.getPairs();
        assertThat(l11Pairs.length, is(3));
        assertThat(l11Pairs[1].getKey(), is(3));
        assertThat(l11Pairs[2].getKey(), is(5));

        InternalNode<Integer> l12 = (InternalNode<Integer>) pairs[1].getPointer();
        InternalNode<Integer>.INodePair[] l12Pairs = l12.getPairs();
        assertThat(l12Pairs.length, is(2));
        assertThat(l12Pairs[1].getKey(), is(9));

        // then level-2
        LeafNode<Integer, String> l21 = (LeafNode<Integer, String>) l11Pairs[0].getPointer();
        LeafNode<Integer, String>.LNodePair[] l21Pairs = l21.getPairs();
        assertThat(l21Pairs.length, is(2));
        assertThat(l21Pairs[0].getKey(), is(1));
        assertThat(l21Pairs[1].getKey(), is(2));

        LeafNode<Integer, String>.LNodePair[] l22Pairs = l21.getNext().getPairs();
        assertThat(l22Pairs.length, is(2));
        assertThat(l22Pairs[0].getKey(), is(3));
        assertThat(l22Pairs[1].getKey(), is(4));

        assertThat(l21.getNext().getNext().getNext().getNext(), is(l12Pairs[1].getPointer()));
    }

    /**
     *                  7
     *           /              \
     *        3|5                9
     *    /    |    \          /   \
     * 1|2 -> 3|4 -> 5|6 -> 7|8 -> 9|10
     */
    @Test
    void should_build_b_plus_tree_with_arbitrary_insert() {
        // given
        BPlusTree<Integer, String> tree = new BPlusTree<>(4);

        // when
        tree.add(5, "5");
        tree.add(1, "1");
        tree.add(3, "3");
        tree.add(10, "10");
        tree.add(4, "4");
        tree.add(8, "8");
        tree.add(2, "2");
        tree.add(9, "9");
        tree.add(6, "6");
        tree.add(7, "7");

        // then level-0
        BptNode<Integer> root = tree.getRoot();
        assertThat(root instanceof InternalNode, is(true));
        InternalNode<Integer>.INodePair[] pairs = ((InternalNode<Integer>) root).getPairs();
        assertThat(pairs.length, is(2));
        assertThat(pairs[1].getKey(), is(7));

        // then level-1
        InternalNode<Integer> l11 = (InternalNode<Integer>) pairs[0].getPointer();
        InternalNode<Integer>.INodePair[] l11Pairs = l11.getPairs();
        assertThat(l11Pairs.length, is(3));
        assertThat(l11Pairs[1].getKey(), is(3));
        assertThat(l11Pairs[2].getKey(), is(5));

        InternalNode<Integer> l12 = (InternalNode<Integer>) pairs[1].getPointer();
        InternalNode<Integer>.INodePair[] l12Pairs = l12.getPairs();
        assertThat(l12Pairs.length, is(2));
        assertThat(l12Pairs[1].getKey(), is(9));

        // then level-2
        LeafNode<Integer, String> l21 = (LeafNode<Integer, String>) l11Pairs[0].getPointer();
        LeafNode<Integer, String>.LNodePair[] l21Pairs = l21.getPairs();
        assertThat(l21Pairs.length, is(2));
        assertThat(l21Pairs[0].getKey(), is(1));
        assertThat(l21Pairs[1].getKey(), is(2));

        LeafNode<Integer, String>.LNodePair[] l22Pairs = l21.getNext().getPairs();
        assertThat(l22Pairs.length, is(2));
        assertThat(l22Pairs[0].getKey(), is(3));
        assertThat(l22Pairs[1].getKey(), is(4));

        assertThat(l21.getNext().getNext().getNext().getNext(), is(l12Pairs[1].getPointer()));
    }

    /**
     *                  7
     *           /              \
     *        3|5                9
     *    /    |    \          /   \
     * 1|2 -> 3|4 -> 5|6 -> 7|8 -> 9|10
     */
    @Test
    void should_delete_node_from_tree() {
        // given
        BPlusTree<Integer, String> tree = new BPlusTree<>(4);
        tree.add(5, "5");
        tree.add(1, "1");
        tree.add(3, "3");
        tree.add(10, "10");
        tree.add(4, "4");
        tree.add(8, "8");
        tree.add(2, "2");
        tree.add(9, "9");
        tree.add(6, "6");
        tree.add(7, "7");

        // when remove 4, then l-3|4 -> l-3
        tree.remove(4);
        InternalNode<Integer> root = (InternalNode<Integer>) tree.getRoot();

        // then
        InternalNode<Integer> internal0 = (InternalNode<Integer>) root.getPairs()[0].getPointer();
        InternalNode<Integer>.INodePair[] internal0pair = internal0.getPairs();
        LeafNode<Integer, String> leaf1 = (LeafNode<Integer, String>) internal0pair[1].getPointer();
        LeafNode<Integer, String>.LNodePair[] leaf1pairs = leaf1.getPairs();
        assertThat(leaf1pairs.length, is(1));
        assertThat(leaf1pairs[0].getKey(), is(3));

        // when remove 3, i-3|5 -> i-2|5, l-1|2 and l-3 -> l-1 and l-2
        tree.remove(3);

        // then
        internal0pair = internal0.getPairs();
        assertThat(internal0pair[1].getKey(), is(2));
        assertThat(internal0pair[2].getKey(), is(5));

        LeafNode<Integer, String> leaf0 = (LeafNode<Integer, String>) internal0pair[0].getPointer();
        assertThat(leaf0.getPairs().length, is(1));
        assertThat(leaf0.getPairs()[0].getKey(), is(1));

        leaf1 = leaf0.getNext();
        assertThat(leaf1.getPairs().length, is(1));
        assertThat(leaf1.getPairs()[0].getKey(), is(2));

        // when remove 2, then i-2|5 -> i-5|6, l-2 and l-5|6 -> l-5 and l-6
        tree.remove(2);

        // then
        internal0pair = internal0.getPairs();
        assertThat(internal0pair[1].getKey(), is(5));
        assertThat(internal0pair[2].getKey(), is(6));

        leaf1 = (LeafNode<Integer, String>) internal0pair[1].getPointer();
        assertThat(leaf1.getPairs().length, is(1));
        assertThat(leaf1.getPairs()[0].getKey(), is(5));

        LeafNode<Integer, String> leaf2 = leaf1.getNext();
        assertThat(leaf2.getPairs().length, is(1));
        assertThat(leaf2.getPairs()[0].getKey(), is(6));

        // when remove 5, then i-2|6 -> i-6, l-5 -> null
        tree.remove(5);

        // then
        internal0pair = internal0.getPairs();
        assertThat(internal0pair[1].getKey(), is(6));

        leaf1 = (LeafNode<Integer, String>) internal0pair[0].getPointer();
        assertThat(leaf1.getPairs().length, is(1));
        assertThat(leaf1.getPairs()[0].getKey(), is(1));

        leaf1 = leaf1.getNext();
        assertThat(leaf1.getPairs().length, is(1));
        assertThat(leaf1.getPairs()[0].getKey(), is(6));

        // when remove 1, then root -> null(pull down), i-6 -> null, and merged with i-9 -> i-7|9, l-1 -> null, merged with l-6
        tree.remove(1);

        // then
        root = (InternalNode<Integer>) tree.getRoot();
        assertThat(root.getPairs().length, is(3));
        assertThat(root.getPairs()[1].getKey(), is(7));
        assertThat(root.getPairs()[2].getKey(), is(9));

        leaf1 = (LeafNode<Integer, String>) root.getPairs()[0].getPointer();
        assertThat(leaf1.getPairs().length, is(1));
        assertThat(leaf1.getPairs()[0].getKey(), is(6));

        leaf1 = leaf1.getNext();
        assertThat(leaf1.getPairs().length, is(2));
        assertThat(leaf1.getPairs()[0].getKey(), is(7));
        assertThat(leaf1.getPairs()[1].getKey(), is(8));
    }
}