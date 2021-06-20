package zxh.demo.datastructure.tree.bplustree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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

    private final INodePair HEAD = new INodePair(null, null);

    private InternalNode<K> parent;
    private List<INodePair> pairs = new ArrayList<>();

    public InternalNode(InternalNode<K> parent) {
        this.parent = parent;
        pairs.add(HEAD);
    }

    @Override
    public int size() {
        return pairs.size() - 1;
    }

    @Override
    public void setParent(BptNode<K> parent) {
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
        add(key, pointer);
        List<INodePair> n0Pair = pairs.stream().limit(size() / 2 + 1).collect(Collectors.toList());
        InternalNode<K> n1 = new InternalNode<>(parent);
        pairs.removeAll(n0Pair);
        n1.pairs.addAll(pairs);
        n1.pairs.get(0).pointer = pairs.get(0).pointer;
        n1.pairs.get(1).pointer = null;
        pairs = n0Pair;
        return n1;
    }

    INodePair popFirst() {
        return pairs.remove(1);
    }

    INodePair[] getPairs() {
        //noinspection unchecked
        return pairs.toArray((INodePair[]) Array.newInstance(INodePair.class, size()));
    }

    BptNode<K> findChild(K key) {
        for (int i = size(); i > 0; i--) {
            if (key.compareTo(pairs.get(i).getKey()) >= 0) {
                return pairs.get(i).pointer;
            }
        }

        return pairs.get(0).pointer;
    }
}
