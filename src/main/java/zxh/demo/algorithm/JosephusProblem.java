package zxh.demo.algorithm;

import zxh.demo.datastructure.linked_list.CircularLinkedList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * JosephusProblem:
 * @author zhangxuhai
 * @date 2020/7/13
*/
public class JosephusProblem {
    private final int totalPersons;
    private final int countStep;
    private CircularLinkedList<Integer> circularLinkedList;

    public JosephusProblem(int totalPersons, int countStep) {
        this.totalPersons = totalPersons;
        this.countStep = countStep;

        initCLL();
    }

    private void initCLL() {
        circularLinkedList = new CircularLinkedList<>();
        IntStream.rangeClosed(1, totalPersons)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .forEach(i -> circularLinkedList.addHead(i));
    }

    public int[] getSuicideSequence() {
        ArrayList<Integer> resultList = new ArrayList<>();
        Iterator<Integer> iterator = circularLinkedList.iterator();
        int currentStep = 1;
        while (!circularLinkedList.isEmpty()) {
            Integer current = iterator.next();
            if (currentStep % countStep == 0) {
                resultList.add(current);
                iterator.remove();
                currentStep++;
            }
            currentStep++;
        }

        return resultList.stream().mapToInt(i -> i).toArray();
    }
}
