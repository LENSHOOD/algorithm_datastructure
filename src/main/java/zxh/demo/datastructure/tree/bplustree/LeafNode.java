package zxh.demo.datastructure.tree.bplustree;

import static java.util.Objects.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LeafNode:
 * @author zhangxuhai
 * @date 2021/6/16
*/
@Getter
public class LeafNode<K extends Comparable<K>, V> implements BptNode<K> {
    @Getter
    @AllArgsConstructor
    class LNodePair {
        private final K key;
        private final V value;
    }

    private InternalNode<K> parent;
    private List<LNodePair> pairs;
    private LeafNode<K, V> prev;
    private LeafNode<K, V> next;

    public LeafNode(InternalNode<K> parent) {
        this.parent = parent;
        pairs = new ArrayList<>();
    }

    @Override
    public int size() {
        return pairs.size();
    }

    @Override
    public void setParent(BptNode<K> parent) {
        if (isNull(parent)) {
            this.parent = null;
            return;
        }

        this.parent = (InternalNode<K>) parent;
    }

    public boolean contains(K key) {
        return pairs.stream().anyMatch(pair -> pair.key.equals(key));
    }

    void add(K key, V value) {
        if (size() == 0) {
            pairs.add(new LNodePair(key, value));
            return;
        }

        // todo: binary search
        for (var i = 0; i < size(); i++) {
            if (key.compareTo(pairs.get(i).getKey()) <= 0) {
                pairs.add(i, new LNodePair(key, value));
                return;
            }
        }

        pairs.add(new LNodePair(key, value));
    }

    LeafNode<K, V> spilt(K key, V value) {
        var rightNode = new LeafNode<K, V>(parent);

        // spilt key-pairs between two node
        add(key, value);
        List<LNodePair> leftPairs = pairs.stream().limit(size() / 2).collect(Collectors.toList());
        pairs.removeAll(leftPairs);
        rightNode.pairs = pairs;
        pairs = leftPairs;

        // add right node to leaf chain
        rightNode.next = next;
        rightNode.prev = this;
        if (nonNull(next)) {
            next.prev = rightNode;
        }
        next = rightNode;

        return rightNode;
    }

    @Override
    public void remove(K key) {
        pairs.removeIf(pair -> pair.getKey().equals(key));
    }

    Optional<LeafNode<K, V>> getLeftSibling() {
        if (isNull(prev) || !prev.parent.equals(parent)) {
            return Optional.empty();
        }

        return Optional.of(prev);
    }

    Optional<LeafNode<K, V>> getRightSibling() {
        if (isNull(next) || !next.parent.equals(parent)) {
            return Optional.empty();
        }

        return Optional.of(next);
    }

    void borrowLeft() {
        // move left sibling's last key to me
        LNodePair leftLast = requireNonNull(prev).getPairs()[prev.size() - 1];
        prev.remove(leftLast.key);
        add(leftLast.key, leftLast.value);

        // replace parent key that point to me to the borrowed key
        // (to fulfill the requirement of all right node key should greater or equal with parent key)
        parent.replacePairKey(this, leftLast.key);
    }

    void borrowRight() {
        // move right sibling's first key to me
        LNodePair rightFirst = requireNonNull(next).getPairs()[0];
        next.remove(rightFirst.key);
        add(rightFirst.key, rightFirst.value);

        // replace parent key that point to me to the borrowed key,
        // meanwhile replace parent key that point to right sibling to the right sibling's new first key
        // (to fulfill the requirement of all right node key should greater or equal with parent key)
        parent.replacePairKey(this, rightFirst.key);
        parent.replacePairKey(next, next.getFirst().key);
    }

    void mergeToLeft() {
        prev.next = next;
        if (nonNull(next)) {
            next.prev = prev;
        }

        prev.pairs.addAll(pairs);
    }

    void mergeToRight() {
        next.prev = prev;
        if (nonNull(prev)) {
            prev.next = next;
        }

        next.pairs.addAll(pairs);
    }

    LNodePair getFirst() {
        return pairs.get(0);
    }

    LNodePair[] getPairs() {
        //noinspection unchecked
        return pairs.toArray((LNodePair[]) Array.newInstance(LNodePair.class, size()));
    }

    @Override
    public String toString() {
        return pairs.stream()
                .filter(pair -> nonNull(pair.getKey()))
                .map(pair -> String.valueOf(pair.getKey()))
                .collect(Collectors.joining("|"));
    }
}
