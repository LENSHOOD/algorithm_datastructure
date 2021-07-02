package zxh.demo.datastructure.tree.bplustree;

import static java.util.Objects.*;

import java.util.*;
import java.util.stream.Stream;

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
                        root.setParent(null);
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
        LeafNode<K, V> leafNode = findNode(key);
        if (!leafNode.contains(key)) {
            return;
        }

        BptNode<K> curr = leafNode;
        BptNode<K> prev = null;
        K currKey = key;
        while (true) {
            // if number of curr's keys beyond half, then the remove operation can be simply finished
            // remove to root is a special case to be treat with
            if (curr.beyondHalf(degree) || curr == root) {
                curr.remove(currKey);
                // if the last key of the root has been removed,
                // then root should be replaced to it's successor (or set to a new root if it has no successor)
                if (curr.size() == 0) {
                    root = isNull(prev) ? new LeafNode<>(null) : prev;
                    root.setParent(null);
                }
                return;
            }

            curr.remove(currKey);
            if (isLeafNode(curr)) {
                LeafNode<K, V> leafCurr = (LeafNode<K, V>) curr;

                // left sibling
                Optional<LeafNode<K, V>> leftSiblingOp = leafCurr.getLeftSibling();
                if (leftSiblingOp.isPresent() && leftSiblingOp.get().beyondHalf(degree)) {
                    leafCurr.borrowLeft();
                    return;
                }

                // right sibling
                Optional<LeafNode<K, V>> rightSiblingOp = leafCurr.getRightSibling();
                if (rightSiblingOp.isPresent() && rightSiblingOp.get().beyondHalf(degree)) {
                    leafCurr.borrowRight();
                    return;
                }

                // merge
                // once merge happened, the delete operation will spread to parent level
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
            } else {
                InternalNode<K> internalCurr = (InternalNode<K>) curr;

                // left sibling
                Optional<InternalNode<K>> leftSiblingOp = internalCurr.getLeftSibling();
                if (leftSiblingOp.isPresent() && leftSiblingOp.get().beyondHalf(degree)) {
                    internalCurr.borrowLeft(leftSiblingOp.get());
                    return;
                }

                // right sibling
                Optional<InternalNode<K>> rightSiblingOp = internalCurr.getRightSibling();
                if (rightSiblingOp.isPresent() && rightSiblingOp.get().beyondHalf(degree)) {
                    internalCurr.borrowRight(rightSiblingOp.get());
                    return;
                }

                // merge
                K parentKey;
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
            }

            // current node move to parent
            curr = curr.getParent();
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

    public void print() {
        List<Integer> levelCounts = new ArrayList<>();
        getLevelNodeNums(root, 0, levelCounts);
        // add root node count
        levelCounts.add(0, 1);

        List<List<String>> levels = new ArrayList<>();
        int h = 1;
        Deque<BptNode<K>> queue = new ArrayDeque<>();
        BptNode<K> curr = root;
        while (nonNull(curr)){
            if (levelCounts.get(h-1) != 0){
                Integer count = levelCounts.get(h-1);
                levelCounts.set(h-1, --count);
            }

            if (isLeafNode(curr)) {
                LeafNode<K, V> leafCurr = (LeafNode<K, V>) curr;
                List<String> currLevel;
                if (levels.size() < h) {
                    currLevel = new ArrayList<>();
                    levels.add(currLevel);
                } else {
                    currLevel = levels.get(h-1);
                }
                currLevel.add(leafCurr.toString());
            } else {
                InternalNode<K> internalCurr = (InternalNode<K>) curr;
                List<String> currLevel;
                if (levels.size() < h) {
                    currLevel = new ArrayList<>();
                    levels.add(currLevel);
                } else {
                    currLevel = levels.get(h-1);
                }

                currLevel.add(internalCurr.toString());
                Stream.of(internalCurr.getPairs()).forEach(pair -> queue.offer(pair.getPointer()));
            }

            curr = queue.poll();
            if (levelCounts.get(h-1) == 0) {
                h++;
            }
        }

        levels.forEach(inner -> {
            inner.forEach(ns -> System.out.print(ns + " "));
            System.out.println();
        });
    }

    private void getLevelNodeNums(BptNode<K> curr, int height, List<Integer> levels) {
        height++;
        if (isLeafNode(curr)) {
            if (levels.size() < height) {
                int originalSize = levels.size();
                for (int i = 0; i < height - originalSize - 1; i++) {
                    levels.add(0);
                }
            }

            return;
        }

        InternalNode<K> iCurr = (InternalNode<K>) curr;
        for (InternalNode<K>.INodePair pair : iCurr.getPairs()) {
            getLevelNodeNums(pair.getPointer(), height, levels);
            Integer count = levels.get(height - 1);
            levels.set(height-1, ++count);
        }
    }
}
