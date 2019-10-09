package nl.hva.ict.se.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

public class ArcherComparatorTest {
    private Comparator<Archer> comparator;

    private Archer archer1;
    private Archer archer2;

    private int[][] definedScoreCard = new int[][]{
            new int[]{
                    5,6,7
            },
            new int[]{
                    6,7,8
            },
            new int[]{
                    7,8,9
            }
    };

    @BeforeEach
    void init() {
        this.comparator = new ArcherComparator();

        List<Archer> archers = Archer.generateArchers(2);
        archer1 = archers.get(0);
        archer2 = archers.get(1);

        archer1.setScoreCard(definedScoreCard);
        archer2.setScoreCard(definedScoreCard);
    }

    @Test
    void testNormalCase()
    {
        // normal case is where one archer has a higher score than the other
        int[][] archer2Score = new int[3][];
        archer2Score[0] = definedScoreCard[0];
        archer2Score[1] = definedScoreCard[1];
        archer2Score[2] = new int[]{9,9,9};

        archer2.setScoreCard(archer2Score);

        assertEquals(-1, comparator.compare(archer1, archer2));
    }

    @Test
    void testWeightedScoreNormalCase()
    {
        /*
        we'll test the weighted score by giving one of the archers
        an imaginary extra round in which they missed 3 times, which
        gives them a bunch of penalty points, giving the edge to the
        other archer
         */
        int[][] archer2Score = new int[4][];
        archer2Score[0] = definedScoreCard[0];
        archer2Score[1] = definedScoreCard[1];
        archer2Score[2] = definedScoreCard[2];
        archer2Score[3] = new int[]{
                0,0,0
        };
        archer2.setScoreCard(archer2Score);

        assertEquals(1, comparator.compare(archer1, archer2));
    }

    @Test
    void testSameWeightedScore()
    {
        /*
        in this case we change nothing about the score, so archer 1 should win
        as they have a lower id
         */
        assertEquals(1, comparator.compare(archer1, archer2));
    }
}
