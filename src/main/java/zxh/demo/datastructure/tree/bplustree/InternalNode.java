package zxh.demo.datastructure.tree.bplustree;

import lombok.Getter;
import java.lang.reflect.Array;

/**
 * InternalNode:
 * @author zhangxuhai
 * @date 2021/6/16
*/
public class InternalNode<K extends Comparable<K>> implements BptNode<K> {
    @Getter
    class INodePair {
        private K key;
        private BptNode<K> pointer;
    }

    private INodePair[] pairs;

    public InternalNode(int degree) {
        //noinspection unchecked
        pairs = (INodePair[]) Array.newInstance(INodePair.class, degree);
    }

    INodePair[] getPairs() {
        return pairs;
    }
}
