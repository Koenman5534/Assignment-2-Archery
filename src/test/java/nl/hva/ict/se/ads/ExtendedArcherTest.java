package nl.hva.ict.se.ads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Place all your own tests for Archer in this class. Tests in any other class will be ignored!
 */
public class ExtendedArcherTest extends ArcherTest {
    public List<Archer> archers;

    @BeforeEach
    void init()
    {
        this.archers = Archer.generateArchers(100);
    }

    @Test
    void testToString()
    {
        // Initialize the scorecard so we know the score is 0
        int[][] scoreCard = new int[1][];
        scoreCard = new int[1][];
        for (int i = 0; i < scoreCard.length; i++) {
            scoreCard[i] = new int[1];
        }

        Archer a = archers.get(0);
        a.setFirstName("Nico");
        a.setLastName("TROMP");
        a.setScoreCard(scoreCard);
        assertEquals(Archer.INITIAL_ID + " (0 / 0) Nico TROMP", a.toString());
    }

    @Test
    void testRegisterScoreForRoundAndGetTotalScore()
    {
        Archer a = archers.get(0);
        // clear the scorecard
        int[][] scoreCard = new int[3][];
        for (int i = 0; i < scoreCard.length; i++) {
            scoreCard[i] = new int[3];
        }
        a.setScoreCard(scoreCard);


        // set the score card to some defined values
        int[][] definedScoreCard = new int[][]{
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
        // define the totals the score should be after each round
        int[] totals = new int[]{18, 39, 63};
        // register each round individually and check if the total score is what we expect it to be
        for (int i = 0; i < definedScoreCard.length; i++) {
            a.registerScoreForRound(i, definedScoreCard[i]);
            assertEquals(totals[i], a.getTotalScore());
        }
    }
}
