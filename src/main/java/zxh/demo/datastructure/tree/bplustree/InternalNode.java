package zxh.demo.datastructure.tree.bplustree;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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
        var rightNode = new InternalNode<>(parent);

        // spilt key-pairs between two node
        add(key, pointer);
        List<INodePair> leftPairs = pairs.stream().limit(size() / 2 + 1L).collect(Collectors.toList());
        pairs.removeAll(leftPairs);
        rightNode.pairs.addAll(pairs);

        // special case of far left key-pair
        rightNode.pairs.get(0).pointer = pairs.get(0).pointer;

        // assign new parent to right children
        rightNode.pairs.forEach(pair -> pair.pointer.setParent(rightNode));
        pairs = leftPairs;

        return rightNode;
    }

    @Override
    public void remove(K key) {
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

    void borrowLeft(InternalNode<K> leftSibling) {
        // pop left sibling's last key
        InternalNode<K>.INodePair leftLast = leftSibling.popLast();

        // move last key to parent
        var parentKey = parent.getByPointer(this);
        parent.replacePairKey(this, leftLast.getKey());

        // move parent key to me
        leftLast.getPointer().setParent(this);
        add(parentKey, getLeftPointer());
        add(null, leftLast.getPointer());
    }

    void borrowRight(InternalNode<K> rightSibling) {
        // pop right sibling's first key
        InternalNode<K>.INodePair rightFirst = rightSibling.popFirst();
        BptNode<K> rightSiblingLeftPointer = rightSibling.getLeftPointer();

        // move right sibling's first key's pointer to far left pointer
        rightSibling.add(null, rightFirst.getPointer());

        // move right first key to parent
        var parentKey = parent.getByPointer(rightSibling);
        parent.replacePairKey(rightSibling, rightFirst.getKey());

        // move parent key to me
        rightSiblingLeftPointer.setParent(this);
        add(parentKey, rightSiblingLeftPointer);
    }

    K mergeToLeft(InternalNode<K> leftSibling) {
        // compose far left pointer with parent key as a new key-pair to be merged to left sibling
        var parentKey = parent.getByPointer(this);
        pairs.get(0).key = parentKey;

        // do merge
        leftSibling.pairs.addAll(pairs);

        // update parent
        leftSibling.pairs.forEach(pair -> pair.pointer.setParent(leftSibling));

        return parentKey;
    }

    K mergeToRight(InternalNode<K> rightSibling) {
        // compose right sibling's far left pointer with parent key as a new key-pair
        var parentKey = rightSibling.parent.getByPointer(rightSibling);
        rightSibling.pairs.get(0).key = parentKey;

        // do merge
        pairs.addAll(rightSibling.pairs);
        rightSibling.pairs = pairs;

        // update parent
        rightSibling.pairs.forEach(pair -> pair.pointer.setParent(rightSibling));

        return parentKey;
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
