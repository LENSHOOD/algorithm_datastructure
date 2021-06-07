package zxh.demo.datastructure.graph;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import zxh.demo.datastructure.linked_list.LinkedList;
import java.util.Optional;

class GraphTest {
    @Test
    void should_create_graph_with_5_nodes() {
        Graph<Integer> graph = Graph.<Integer>builder()
                .put(1, 2)
                .put(1, 3)
                .put(1, 5)
                .put(2, 1)
                .put(2, 3)
                .put(2, 5)
                .put(3, 4)
                .put(3, 5)
                .put(4, 2)
                .put(5, 1)
                .put(5, 4)
                .build();

        Optional<LinkedList<Integer>> sOf1 = graph.successors(1);
        assertThat(sOf1.isPresent(), is(true));
        assertThat(sOf1.get().toArray(new Integer[]{}), arrayContaining(2, 3, 5));

        Optional<LinkedList<Integer>> sOf2 = graph.successors(2);
        assertThat(sOf2.isPresent(), is(true));
        assertThat(sOf2.get().toArray(new Integer[]{}), arrayContaining(1, 3, 5));

        Optional<LinkedList<Integer>> sOf3 = graph.successors(3);
        assertThat(sOf3.isPresent(), is(true));
        assertThat(sOf3.get().toArray(new Integer[]{}), arrayContaining(4, 5));

        Optional<LinkedList<Integer>> sOf4 = graph.successors(4);
        assertThat(sOf4.isPresent(), is(true));
        assertThat(sOf4.get().toArray(new Integer[]{}), arrayContaining(2));

        Optional<LinkedList<Integer>> sOf5 = graph.successors(5);
        assertThat(sOf5.isPresent(), is(true));
        assertThat(sOf5.get().toArray(new Integer[]{}), arrayContaining(1, 4));
    }
}