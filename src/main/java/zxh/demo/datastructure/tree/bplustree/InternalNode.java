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

    void add(K key, BptNode<K> child) {
        // todo: binary search
        for (int i = 1; i < size(); i++) {
            if (pairs.get(i).getKey().compareTo(key) > 0) {
                continue;
            }

            pairs.add(i, new INodePair(key, child));
        }
    }

    InternalNode<K> spilt(K key, BptNode<K> pointer) {
        add(key, pointer);
        List<INodePair> n0Pair = pairs.stream().limit(size() / 2 + 1).collect(Collectors.toList());
        InternalNode<K> n1 = new InternalNode<>(parent);
        pairs.removeAll(n0Pair);
        n1.pairs.addAll(pairs);
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
        for (int i = size() - 1; i > 0; i--) {
            if (key.compareTo(pairs.get(i).getKey()) >= 0) {
                return pairs.get(i).pointer;
            }
        }

        return pairs.get(0).pointer;
    }
}
