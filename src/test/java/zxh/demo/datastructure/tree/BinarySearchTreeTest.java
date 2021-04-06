package zxh.demo.datastructure.tree;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Collectors;

class BinarySearchTreeTest {
    private BinarySearchTree<Integer> tree;

    /**
     *       4
     *     /   \
     *    2     6
     *  /  \   /
     * 1   3  5
     */
    @BeforeEach
    void setup() {
        PointerNode<Integer, String> node1 = new PointerNode<>(1, "value-1");
        PointerNode<Integer, String> node2 = new PointerNode<>(2, "value-2");
        PointerNode<Integer, String> node3 = new PointerNode<>(3, "value-3");
        PointerNode<Integer, String> node4 = new PointerNode<>(4, "value-4");
        PointerNode<Integer, String> node5 = new PointerNode<>(5, "value-5");
        PointerNode<Integer, String> node6 = new PointerNode<>(6, "value-6");

        tree = new BinarySearchTree<>();
        tree.add(node4);
        tree.add(node2);
        tree.add(node6);
        tree.add(node1);
        tree.add(node5);
        tree.add(node3);
    }

    @Test
    void should_add_nodes_to_tree() {
        // given
        PointerNode<Integer, String> node7 = new PointerNode<>(7, "value-7");

        // when
        tree.add(node7);

        // then
        assertThat(tree.size(), is(7));
        assertThat(tree.asOrderedList().stream().map(TreeNode::getKey).collect(Collectors.toList()), contains(
                equalTo(1), equalTo(2), equalTo(3),
                equalTo(4), equalTo(5), equalTo(6), equalTo(7)));
    }

    @Test
    void should_get_nodes_from_tree() {
        // given
        PointerNode<Integer, String> node7 = new PointerNode<>(7, "value-7");
        tree.add(node7);

        // when
        List<TreeNode<Integer>> third = tree.get(3);
        List<TreeNode<Integer>> fifth = tree.get(5);

        // then
        assertThat(third.get(0).getKey(), equalTo(3));
        assertThat(fifth.get(0).getKey(), equalTo(5));
    }

    @Test
    void should_remove_nodes_to_tree() {
        // given
        PointerNode<Integer, String> node7 = new PointerNode<>(7, "value-7");
        tree.add(node7);

        // when
        tree.remove(2);
        tree.remove(3);
        tree.remove(6);

        // then
        assertThat(tree.size(), is(4));
        assertThat(tree.asOrderedList().stream().map(TreeNode::getKey).collect(Collectors.toList()), contains(
                equalTo(1), equalTo(4), equalTo(5), equalTo(7)));
    }
}