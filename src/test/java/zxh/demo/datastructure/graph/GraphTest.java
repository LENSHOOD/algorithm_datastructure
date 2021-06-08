package zxh.demo.datastructure.graph;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import zxh.demo.datastructure.linked_list.LinkedList;
import java.util.List;
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

    @Test
    void should_get_road_1() {
        // given
        Graph<Integer> graph = Graph.<Integer>builder()
                .put(1, 2)
                .put(1, 3)
                .put(2, 4)
                .put(2, 5)
                .put(4, 6)
                .put(5, 6)
                .put(6, 7)
                .build();

        // when
        List<Integer> bfsRoad = graph.bfs(1, 7);
        List<Integer> dfsRoad = graph.dfs(1, 7);

        // then
        assertThat(bfsRoad, contains(1, 2, 4, 6, 7));
        assertThat(dfsRoad, contains(1, 2, 4, 6, 7));
    }

    @Test
    void should_get_road_2() {
        // given
        Graph<Integer> graph = Graph.<Integer>builder()
                .put(0, 1)
                .put(0, 3)
                .put(1, 0)
                .put(1, 2)
                .put(1, 4)
                .put(2, 1)
                .put(2, 5)
                .put(3, 0)
                .put(3, 4)
                .put(4, 1)
                .put(4, 3)
                .put(4, 5)
                .put(4, 6)
                .put(5, 2)
                .put(5, 4)
                .put(5, 7)
                .put(6, 4)
                .put(6, 7)
                .put(7, 5)
                .put(7, 6)
                .build();

        // when
        List<Integer> bfsRoad = graph.bfs(0, 6);
        List<Integer> dfsRoad = graph.dfs(0, 6);

        // then
        assertThat(bfsRoad, contains(0, 1, 4, 6));
        assertThat(dfsRoad, contains(0, 1, 2, 5, 4, 6));
    }

    @Test
    void should_get_self_when_same() {
        // given
        Graph<Integer> graph = Graph.<Integer>builder()
                .put(1, 2)
                .put(2, 1)
                .build();

        // when
        List<Integer> bfsRoad = graph.bfs(1, 1);
        List<Integer> dfsRoad = graph.dfs(1, 1);

        // then
        assertThat(bfsRoad, contains(1));
        assertThat(dfsRoad, contains(1));
    }

    @Test
    void should_get_empty_when_no_road() {
        // given
        Graph<Integer> graph = Graph.<Integer>builder()
                .put(1, 2)
                .put(3, 4)
                .build();

        // when
        List<Integer> bfsRoad = graph.bfs(1, 4);
        List<Integer> dfsRoad = graph.dfs(1, 4);

        // then
        assertThat(bfsRoad, empty());
        assertThat(dfsRoad, empty());
    }
}