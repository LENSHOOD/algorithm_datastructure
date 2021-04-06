package zxh.demo.datastructure.tree;

import java.util.Optional;

/**
 * TreeNode:
 * @author zhangxuhai
 * @date 2021/4/6
*/
public interface TreeNode<K extends Comparable<K>> {
    K getKey();
    Optional<TreeNode<K>> getParent();
    void setParent(TreeNode<K> parent);
    Optional<TreeNode<K>> getLeft();
    void setLeft(TreeNode<K> left);
    Optional<TreeNode<K>> getRight();
    void setRight(TreeNode<K> right);
}
