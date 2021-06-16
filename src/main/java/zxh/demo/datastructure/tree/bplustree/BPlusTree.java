package zxh.demo.datastructure.tree.bplustree;

import java.util.Optional;

/**
 * BPlusTree:
 * @author zhangxuhai
 * @date 2021/6/16
*/
public class BPlusTree<K extends Comparable<K>, V> {
    private BptNode<K> root;

    public BPlusTree(int degree) {
        root = new LeafNode<>(null, degree);
    }

    public void add(K key, V value) {

    }

    public void remove(K key) {

    }

    public Optional<V> get(K key) {
        return Optional.empty();
    }

    BptNode<K> getRoot() {
        return root;
    }
}
