package zxh.demo.datastructure.tree;

import java.util.Optional;

/**
 * PointerNode:
 * @author zhangxuhai
 * @date 2021/4/6
*/
public class PointerNode<K extends Comparable<K>, V> implements TreeNode<K> {
    private final K key;
    private final V value;
    private TreeNode<K> parent;
    private TreeNode<K> left;
    private TreeNode<K> right;

    public PointerNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public Optional<TreeNode<K>> getParent() {
        return Optional.ofNullable(parent);    }

    @Override
    public void setParent(TreeNode<K> parent) {
        this.parent = parent;
    }

    @Override
    public Optional<TreeNode<K>> getLeft() {
        return Optional.ofNullable(left);
    }

    @Override
    public void setLeft(TreeNode<K> left) {
        this.left = left;
    }

    @Override
    public Optional<TreeNode<K>> getRight() {
        return Optional.ofNullable(right);
    }

    @Override
    public void setRight(TreeNode<K> right) {
        this.right = right;
    }

    public V getValue() {
        return value;
    }
}
