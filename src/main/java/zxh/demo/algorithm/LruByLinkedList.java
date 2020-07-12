package zxh.demo.algorithm;

import zxh.demo.datastructure.linked_list.TwoWayLinkedList;

/**
 * LruByLinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public class LruByLinkedList {
    private TwoWayLinkedList<String> lruRecorder = new TwoWayLinkedList<>();

    public void access(String key) {
        int keyIndex = lruRecorder.indexOf(key);
        if (keyIndex != -1) {
            lruRecorder.remove(keyIndex);
        }

        lruRecorder.addHead(key);
    }

    public String retire() {
        String tailKey;
        try {
            tailKey = lruRecorder.getTail();
        } catch (UnsupportedOperationException e) {
            return null;
        }

        lruRecorder.removeTail();
        return tailKey;
    }

    public String getHead() {
        return lruRecorder.getHead();
    }
}
