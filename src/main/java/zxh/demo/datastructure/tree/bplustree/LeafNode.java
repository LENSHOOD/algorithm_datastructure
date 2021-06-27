package zxh.demo.datastructure.tree.bplustree;

import static java.util.Objects.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        private K key;
        private V value;
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
        assert parent instanceof InternalNode;
        this.parent = (InternalNode<K>) parent;
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
        add(key, value);
        List<LNodePair> n0Pair = pairs.stream().limit(size() / 2).collect(Collectors.toList());
        LeafNode<K, V> n1 = new LeafNode<>(parent);
        pairs.removeAll(n0Pair);
        n1.pairs = pairs;
        pairs = n0Pair;

        n1.next = next;
        n1.prev = this;
        next = n1;
        return n1;
    }

    void remove(K key) {
        pairs.removeIf(pair -> pair.getKey().equals(key));
    }

    Optional<LeafNode<K, V>> getLeftSibling() {
        if (isNull(prev) || prev.parent.equals(parent)) {
            return Optional.empty();
        }

        return Optional.of(prev);
    }

    Optional<LeafNode<K, V>> getRightSibling() {
        if (isNull(next) || next.parent.equals(parent)) {
            return Optional.empty();
        }

        return Optional.of(next);
    }

    K borrowLeft() {
        LNodePair leftLast = requireNonNull(prev).getPairs()[pairs.size() - 1];
        prev.remove(leftLast.key);
        add(leftLast.key, leftLast.value);
        return leftLast.key;
    }

    K borrowRight() {
        LNodePair rightFirst = requireNonNull(next).getPairs()[0];
        prev.remove(rightFirst.key);
        add(rightFirst.key, rightFirst.value);
        return next.getPairs()[0].key;
    }

    void mergeLeft() {
        prev.next = next;
        if (nonNull(next)) {
            next.prev = prev;
        }

        prev.pairs.addAll(pairs);
    }

    void mergeRight() {
        next.prev = prev;
        if (nonNull(prev)) {
            prev.next = next;
        }

        next.pairs.addAll(pairs);
    }

    LNodePair getFirst() {
        return pairs.get(0);
    }

    void setNext(LeafNode<K, V> next) {
        this.next = next;
    }

    LNodePair[] getPairs() {
        //noinspection unchecked
        return pairs.toArray((LNodePair[]) Array.newInstance(LNodePair.class, size()));
    }
}
