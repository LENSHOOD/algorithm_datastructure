package zxh.demo.datastructure.tree.bplustree;

import lombok.Getter;
import java.lang.reflect.Array;

/**
 * LeafNode:
 * @author zhangxuhai
 * @date 2021/6/16
*/
@Getter
public class LeafNode<K extends Comparable<K>, V> implements BptNode<K> {
    @Getter
    class LNodePair {
        private K key;
        private V value;
    }

    private InternalNode<K> parent;
    private LNodePair[] pairs;
    private LeafNode<K, V> next;

    public LeafNode(InternalNode<K> parent, int degree) {
        this.parent = parent;
        //noinspection unchecked
        pairs = (LNodePair[]) Array.newInstance(LNodePair.class, degree);
    }

    void setNext(LeafNode<K, V> next) {
        this.next = next;
    }

    LNodePair[] getPairs() {
        return pairs;
    }
}
