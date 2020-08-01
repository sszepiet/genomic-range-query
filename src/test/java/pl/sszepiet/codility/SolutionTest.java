package pl.sszepiet.codility;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SolutionTest {

    private Solution testee = new Solution();

    @Test
    public void readmeExample() {
        // given
        final String input = "CAGCCTA";
        int[] p = new int[]{2, 5, 0};
        int[] q = new int[]{4, 5, 6};
        // when
        final int[] solution = testee.solution(input, p, q);
        // then
        assertArrayEquals(new int[]{2, 4, 1}, solution);
    }

    @Test
    public void performanceTest() {
        // given
        final StringBuilder sb = new StringBuilder();
        IntStream.range(0, 100_000).forEach(i -> sb.append("G"));
        final String input = sb.toString();
        int[] p = new int[50_000];
        int[] q = new int[50_000];
        IntStream.range(0, 50_000).forEach(i -> {
            p[i] = i;
            q[i] = i + 50_000;
        });
        // when
        final int[] solution = testee.solution(input, p, q);
        // then
        assertTrue(Arrays.stream(solution).allMatch(i -> i == 3));
    }
}
