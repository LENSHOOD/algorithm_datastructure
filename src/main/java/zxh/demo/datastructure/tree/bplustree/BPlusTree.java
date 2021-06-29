package zxh.demo.datastructure.tree.bplustree;

import java.util.Optional;

/**
 * BPlusTree:
 * @author zhangxuhai
 * @date 2021/6/16
*/
public class BPlusTree<K extends Comparable<K>, V> {
    private BptNode<K> root;
    private final int degree;

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
            if (curr.notFull(degree)) {
                if (isLeafNode(curr)) {
                    ((LeafNode<K, V>) curr).add(currKey, value);
                } else {
                    ((InternalNode<K>) curr).add(currKey, currChild);
                    if (root != curr && ((InternalNode<K>) curr).hasChild(root)) {
                        root = curr;
                    }
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

    /**
     * 1. find node(current) to be delete
     * 2. if number of keys contained in node(N-keys) > N/2 - 1:
     *     - find the key then delete it
     *     - finish operation
     * 3. else if N-keys == N/2 - 1,
     *     at first, delete it from the node, then:
     *     - when node is leaf:
     *         a. if it's LEFT sibling has more than N/2 keys,
     *            borrow the last key and put it to current node,
     *            replace parent key to the last key. finish operation.
     *         b. if it's RIGHT sibling has more than N/2 keys,
     *            borrow the first key and put it to current node,
     *            replace parent key to the new first key of
     *            right sibling. finish operation.
     *         c. if no siblings can borrow, first merge current
     *            node to one of the siblings, then delete parent key.
     *     - when node is internal:
     *         a. if it's LEFT sibling has more than N/2 keys,
     *            move the parent key to current node, then borrow
     *            the left sibling's last key, to fill parent.
     *         b. if it's RIGHT sibling has more than N/2 keys,
     *            move the parent key to current node, then borrow
     *            the right sibling's first key, to fill parent.
     *         c. if no siblings can borrow, pull down the parent key
     *            to the sibling, then merge current node to sibling to.
     *     - goto 4
     * 4. switch current node to the parent, goto 2
     */
    public void remove(K key) {
        BptNode<K> curr = findNode(key);
        BptNode<K> prev = null;
        K currKey = key;
        while (true) {
            if (curr.beyondHalf(degree) || curr == root) {
                if (isLeafNode(curr)) {
                    ((LeafNode<K, V>) curr).remove(currKey);
                } else {
                    ((InternalNode<K>) curr).remove(currKey);
                }

                if (curr.size() == 0) {
                    root = prev;
                }
                break;
            }

            if (isLeafNode(curr)) {
                LeafNode<K, V> leafCurr = (LeafNode<K, V>) curr;
                leafCurr.remove(currKey);

                // left sibling
                Optional<LeafNode<K, V>> leftSiblingOp = leafCurr.getLeftSibling();
                if (leftSiblingOp.isPresent() && leftSiblingOp.get().beyondHalf(degree)) {
                    K keyToBeReplaced = leafCurr.borrowLeft();
                    leafCurr.getParent().replacePairKey(leafCurr, keyToBeReplaced);
                    break;
                }

                // right sibling
                Optional<LeafNode<K, V>> rightSiblingOp = leafCurr.getRightSibling();
                if (rightSiblingOp.isPresent() && rightSiblingOp.get().beyondHalf(degree)) {
                    K keyToBeReplaced = leafCurr.borrowRight();
                    leafCurr.getParent().replacePairKey(leafCurr, keyToBeReplaced);
                    leafCurr.getParent().replacePairKey(leafCurr.getNext(), rightSiblingOp.get().getFirst().getKey());
                    break;
                }

                // merge
                if (leftSiblingOp.isPresent()) {
                    leafCurr.mergeToLeft();
                    prev = leftSiblingOp.get();
                } else if (rightSiblingOp.isPresent()) {
                    leafCurr.mergeToRight();
                    prev = rightSiblingOp.get();
                } else {
                    // shouldn't goes here
                    throw new IllegalStateException();
                }

                currKey = leafCurr.getParent().getByPointer(leafCurr);
                curr = leafCurr.getParent();
            } else {
                InternalNode<K> internalCurr = (InternalNode<K>) curr;
                internalCurr.remove(currKey);

                // left sibling
                K parentKey;
                Optional<InternalNode<K>> leftSiblingOp = internalCurr.getLeftSibling();
                if (leftSiblingOp.isPresent() && leftSiblingOp.get().beyondHalf(degree)) {
                    InternalNode<K>.INodePair leftLast = leftSiblingOp.get().popLast();
                    parentKey = internalCurr.getParent().getByPointer(internalCurr);
                    internalCurr.add(parentKey, internalCurr.getLeftPointer());
                    internalCurr.add(null, leftLast.getPointer());
                    internalCurr.getParent().replacePairKey(internalCurr, leftLast.getKey());
                    break;
                }

                // right sibling
                Optional<InternalNode<K>> rightSiblingOp = internalCurr.getRightSibling();
                if (rightSiblingOp.isPresent() && rightSiblingOp.get().beyondHalf(degree)) {
                    InternalNode<K>.INodePair rightFirst = rightSiblingOp.get().popFirst();
                    parentKey = internalCurr.getParent().getByPointer(rightSiblingOp.get());
                    internalCurr.add(parentKey, rightSiblingOp.get().getLeftPointer());
                    rightSiblingOp.get().add(null, rightFirst.getPointer());
                    internalCurr.getParent().replacePairKey(rightSiblingOp.get(), rightFirst.getKey());
                    break;
                }

                // merge
                if (leftSiblingOp.isPresent()) {
                    parentKey = internalCurr.getParent().getByPointer(internalCurr);
                    leftSiblingOp.get().mergeRight(parentKey, internalCurr);
                    prev = leftSiblingOp.get();
                } else if (rightSiblingOp.isPresent()) {
                    parentKey = internalCurr.getParent().getByPointer(rightSiblingOp.get());
                    rightSiblingOp.get().mergeLeft(internalCurr, parentKey);
                    prev = rightSiblingOp.get();
                } else {
                    // shouldn't goes here
                    throw new IllegalStateException();
                }

                currKey = parentKey;
                curr = internalCurr.getParent();
            }
        }
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
