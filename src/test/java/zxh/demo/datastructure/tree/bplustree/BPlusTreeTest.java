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
        assertThat(l21Pairs.length, is(3));
        assertThat(l21Pairs[1].getKey(), is(1));
        assertThat(l21Pairs[2].getKey(), is(2));

        LeafNode<Integer, String>.LNodePair[] l22Pairs = l21.getNext().getPairs();
        assertThat(l22Pairs.length, is(3));
        assertThat(l22Pairs[1].getKey(), is(3));
        assertThat(l22Pairs[2].getKey(), is(4));

        assertThat(l21.getNext().getNext().getNext().getNext(), is(l12Pairs[1].getPointer()));
    }
}