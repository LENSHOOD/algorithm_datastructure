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
 * InternalNode:
 * @author zhangxuhai
 * @date 2021/6/16
*/
@Getter
public class InternalNode<K extends Comparable<K>> implements BptNode<K> {
    @Getter
    @AllArgsConstructor
    class INodePair {
        private K key;
        private BptNode<K> pointer;
    }

    private final INodePair head = new INodePair(null, null);

    private InternalNode<K> parent;
    private List<INodePair> pairs = new ArrayList<>();

    public InternalNode(InternalNode<K> parent) {
        this.parent = parent;
        pairs.add(head);
    }

    @Override
    public int size() {
        return pairs.size() - 1;
    }

    @Override
    public void setParent(BptNode<K> parent) {
        if (isNull(parent)) {
            this.parent = null;
            return;
        }

        assert parent instanceof InternalNode;
        this.parent = (InternalNode<K>) parent;
    }

    void add(K key, BptNode<K> child) {
        if (key == null) {
            pairs.get(0).pointer = child;
            return;
        }

        if (size() == 0) {
            pairs.add(new INodePair(key, child));
            return;
        }

        // todo: binary search
        for (var i = 1; i <= size(); i++) {
            if (key.compareTo(pairs.get(i).getKey()) <= 0) {
                pairs.add(i, new INodePair(key, child));
                return;
            }
        }

        pairs.add(new INodePair(key, child));
    }

    InternalNode<K> spilt(K key, BptNode<K> pointer) {
        InternalNode<K> rightNode = new InternalNode<>(parent);

        // spilt key-pairs between two node
        add(key, pointer);
        List<INodePair> leftPairs = pairs.stream().limit(size() / 2 + 1).collect(Collectors.toList());
        pairs.removeAll(leftPairs);
        rightNode.pairs.addAll(pairs);

        // special case of far left key-pair
        rightNode.pairs.get(0).pointer = pairs.get(0).pointer;

        // assign new parent to right children
        rightNode.pairs.forEach(pair -> pair.pointer.setParent(rightNode));
        pairs = leftPairs;

        return rightNode;
    }

    void remove(K key) {
        if (isNull(key)) {
            if (size() == 0) {
                // shouldn't goes here
                throw new IllegalStateException();
            }

            pairs.remove(0);
            pairs.get(0).key = null;
            return;
        }

        pairs.removeIf(pair -> key.equals(pair.getKey()));
    }

    K getByPointer(BptNode<K> pointer) {
        for (var i = 0; i <= size(); i++) {
            if (pairs.get(i).pointer.equals(pointer)) {
                return pairs.get(i).key;
            }
        }

        // shouldn't goes here
        throw new IllegalStateException();
    }

    void replacePairKey(BptNode<K> pointer, K newKey) {
        for (var i = 1; i <= size(); i++) {
            if (pointer.equals(pairs.get(i).getPointer())) {
                pairs.get(i).key = newKey;
            }
        }
    }

    Optional<InternalNode<K>> getLeftSibling() {
        if (isNull(parent)) {
            return Optional.empty();
        }

        for (var i = 0; i <= parent.size(); i++) {
            if (this.equals(parent.pairs.get(i).getPointer())) {
                if (i == 0) {
                    return Optional.empty();
                }

                return Optional.of((InternalNode<K>) parent.pairs.get(i - 1).pointer);
            }
        }

        // shouldn't goes here
        throw new IllegalStateException();
    }

    Optional<InternalNode<K>> getRightSibling() {
        if (isNull(parent)) {
            return Optional.empty();
        }

        for (var i = 0; i <= parent.size(); i++) {
            if (this.equals(parent.pairs.get(i).getPointer())) {
                if (i == parent.size()) {
                    return Optional.empty();
                }

                return Optional.of((InternalNode<K>) parent.pairs.get(i + 1).pointer);
            }
        }

        // shouldn't goes here
        throw new IllegalStateException();
    }

    void mergeRight(K key, InternalNode<K> right) {
        right.pairs.get(0).key = key;
        pairs.addAll(right.pairs);
        pairs.forEach(pair -> pair.pointer.setParent(this));
    }

    void mergeLeft(InternalNode<K> left, K key) {
        pairs.get(0).key = key;
        left.pairs.addAll(pairs);
        pairs = left.pairs;
        pairs.forEach(pair -> pair.pointer.setParent(this));
    }

    BptNode<K> getLeftPointer() {
        return pairs.get(0).pointer;
    }

    INodePair popFirst() {
        return pairs.remove(1);
    }

    INodePair popLast() {
        return pairs.remove(size());
    }

    INodePair[] getPairs() {
        //noinspection unchecked
        return pairs.toArray((INodePair[]) Array.newInstance(INodePair.class, pairs.size()));
    }

    BptNode<K> findChild(K key) {
        for (int i = size(); i > 0; i--) {
            if (key.compareTo(pairs.get(i).getKey()) >= 0) {
                return pairs.get(i).pointer;
            }
        }

        return pairs.get(0).pointer;
    }

    boolean hasChild(BptNode<K> node) {
        return pairs.stream().anyMatch(pair -> pair.pointer == node);
    }

    @Override
    public String toString() {
        return pairs.stream()
                .filter(pair -> nonNull(pair.getKey()))
                .map(pair -> String.valueOf(pair.getKey()))
                .collect(Collectors.joining("|"));
    }
}
