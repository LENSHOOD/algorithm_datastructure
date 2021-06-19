package zxh.demo.datastructure.tree.bplustree;

/**
 * BptNode:
 * @author zhangxuhai
 * @date 2021/6/16
*/
public interface BptNode<K extends Comparable<K>> {
    int size();

    BptNode<K> getParent();

    void setParent(BptNode<K> parent);
}
