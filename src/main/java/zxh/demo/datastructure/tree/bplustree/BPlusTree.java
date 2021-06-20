package zxh.demo.datastructure.tree.bplustree;

import java.util.Optional;

/**
 * BPlusTree:
 * @author zhangxuhai
 * @date 2021/6/16
*/
public class BPlusTree<K extends Comparable<K>, V> {
    private BptNode<K> root;
    private int degree;

    public BPlusTree(int degree) {
        root = new LeafNode<>(null);
        this.degree = degree;
    }

    /**
     * 1. find node to be insert
     * 2. if number of keys contained in node(N-keys) < degree-1:
     *     - insert new key into it
     *     - finish operation
     * 3. else if N-keys == degree-1:
     *     - insert new key into the node, then spilt it to two node(N0, N1), each node contains half of the keys
     *     - if node is leaf: COPY the first key at N1 and try insert it to parent
     *     - if node is internal: MOVE the first key at N1 and try insert it to parent
     *     - goto 4
     * 4. switch current node to the parent, goto 2
     */
    public void add(K key, V value) {
        BptNode<K> curr = findNode(key);
        K currKey = key;
        BptNode<K> currChild = null;
        while (true) {
            if (curr.size() < degree - 1) {
                if (isLeafNode(curr)) {
                    ((LeafNode<K, V>) curr).add(currKey, value);
                } else {
                    ((InternalNode<K>) curr).add(currKey, currChild);
                    root = curr;
                }

                break;
            }

            if (isLeafNode(curr)) {
                currChild = ((LeafNode<K, V>) curr).spilt(key, value);
                currKey = ((LeafNode<K, V>) currChild).getFirst().getKey();
            } else {
                currChild = ((InternalNode<K>) curr).spilt(currKey, currChild);
                currKey = ((InternalNode<K>) currChild).popFirst().getKey();
            }

            if (curr.getParent() == null) {
                InternalNode<K> newParent = new InternalNode<>(null);
                // left pointer of new parent is curr
                newParent.add(null, curr);
                curr.setParent(newParent);
                currChild.setParent(newParent);
            }

            curr = curr.getParent();
        }
    }

    public void remove(K key) {

    }

    public Optional<V> get(K key) {
        return Optional.empty();
    }

    public LeafNode<K, V> findNode(K key) {
        if (isLeafNode(root)) {
            return (LeafNode<K, V>) root;
        }

        BptNode<K> curr = root;
        while (true) {
            if (isLeafNode(curr)) {
                return (LeafNode<K, V>) curr;
            }

            curr = ((InternalNode<K>) curr).findChild(key);
        }
    }

    BptNode<K> getRoot() {
        return root;
    }

    private static boolean isLeafNode(BptNode<?> node) {
        return node instanceof LeafNode;
    }

    private static boolean isInternalNode(BptNode<?> node) {
        return node instanceof InternalNode;
    }

}