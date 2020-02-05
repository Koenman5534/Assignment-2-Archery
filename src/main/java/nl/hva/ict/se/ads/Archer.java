package nl.hva.ict.se.ads;

import java.util.*;
import java.util.function.Consumer;
import java.lang.Math.*;

/**
 * Holds the name, archer-id and the points scored for 30 arrows.
 *
 * Archers MUST be created by using one of the generator methods. That is way the constructor is private and should stay
 * private. You are also not allowed to add any constructor with an access modifier other then private unless it is for
 * testing purposes in which case the reason why you need that constructor must be contained in a very clear manner
 * in your report.
 */
public class Archer {
    public static int MAX_ARROWS = 3;
    public static int MAX_ROUNDS = 10;
    private static Random randomizer = new Random();
    private int id; // Once assigned a value is not allowed to change.
    private String firstName;
    private String lastName;

    private int[][] scoreCard;

    static final int INITIAL_ID = 135788;
    static int LAST_ID = 0;
    /**
     * Constructs a new instance of bowman and assigns a unique ID to the instance. The ID is not allowed to ever
     * change during the lifetime of the instance! For this you need to use the correct Java keyword.Each new instance
     * is a assigned a number that is 1 higher than the last one assigned. The first instance created should have
     * ID 135788;
     *
     * @param firstName the archers first name.
     * @param lastName the archers surname.
     */
    private Archer(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);

        // Initialize the scorecard
        scoreCard = new int[MAX_ROUNDS][];
        for (int i = 0; i < scoreCard.length; i++) {
            scoreCard[i] = new int[MAX_ARROWS];
        }
    }

    /**
     * Registers the point for each of the three arrows that have been shot during a round. The <code>points</code>
     * parameter should hold the three points, one per arrow.
     *
     * @param round the round for which to register the points.
     * @param points the points shot during the round.
     */
    public void registerScoreForRound(int round, int[] points) {
        this.scoreCard[round] = points;
    }

    public int getTotalScore() {
        int total = 0;
        for (int i = 0; i < scoreCard.length; i++) {
            for (int j = 0; j < scoreCard[i].length; j++)
            {
                total += scoreCard[i][j];
            }
        }
        return total;
    }

    /**
     * The weighted score incorporates the skill of the archer by awarding
     * extra points for a high number of good shots and by giving a penalty for misses.
     * The weighted score awards 1 extra point per shot, unless the shot missed, in
     * which case the archer receives a 7 point penalty.
     *
     * @param a
     * @return
     */
    public static int calculateWeightedScore(Archer a)
    {
        int wScore = 0;
        int[][] scoreCard = a.getScoreCard();
        for (int i = 0; i < scoreCard.length; i++) {
            for (int j = 0; j < scoreCard[i].length; j++) {
                if (scoreCard[i][j] == 0) // penalty
                {
                    wScore -= 7;
                }
                wScore += scoreCard[i][j] + 1; // extra points
            }
        }

        return wScore;
    }

    /**
     * Generates the next archer ID that should be given out based on the initial archer ID and
     * the last ID that was given out
     *
     * @return the next archer ID to be given out
     */
    private static int generateNextArcherId()
    {
        if (LAST_ID == 0)
        {
            LAST_ID = INITIAL_ID;
            return INITIAL_ID;
        }
        else
        {
            LAST_ID++;
            return LAST_ID;
        }
    }

    /**
     * This methods creates a List of archers.
     *
     * @param nrOfArchers the number of archers in the list.
     * @return a list of the created archers
     */
    static List<Archer> generateArchers(int nrOfArchers) {
        List<Archer> archers = new ArrayList<>(nrOfArchers);
        for (int i = 0; i < nrOfArchers; i++) {
            Archer archer = generateArcher(nrOfArchers % 100 == 0);
            archers.add(archer);
        }
        return archers;

    }

    /**
     * Generates a new archer. This is moved to a separate method to keep the logic the same for both
     * List and Iterator implementations.
     *
     * @param isBeginner indicates if this archer is beginner and should use the different scoring scheme
     * @return the generated archer
     */
    private static Archer generateArcher(boolean isBeginner)
    {
        Archer archer = new Archer(Names.nextFirstName(), Names.nextSurname());
        archer.setId(generateNextArcherId());
        letArcherShoot(archer, isBeginner);
        return archer;
    }

    /**
     * This methods creates a Iterator that can be used to generate all the required archers. If you implement this
     * method it is only allowed to create an instance of Archer inside the next() method!
     *
     * <b>THIS METHODS IS OPTIONAL</b>
     *
     * @param nrOfArchers the number of archers the Iterator will create.
     * @return
     */
    public static Iterator<Archer> generateArchers(long nrOfArchers)
    {
        return new IterableArcher(nrOfArchers);
    }

    static class IterableArcher implements Iterator<Archer>
    {
        private long nrOfArchers;
        private long definedArchers = 0;
        private List<Archer> archers;

        public IterableArcher(long nrOfArchers)
        {
            this.nrOfArchers = nrOfArchers;
            this.archers = new ArrayList<>();
        }

        @Override
        public boolean hasNext() {
            return definedArchers < nrOfArchers;
        }

        @Override
        public Archer next() {
            Archer a = generateArcher(nrOfArchers % 100 == 0);
            archers.add(a);
            definedArchers++;
            return a;
        }

        @Override
        public void remove() {
            archers.remove(archers.size());
        }
    }

    public int getId() {
        return id;
    }

    private static void letArcherShoot(Archer archer, boolean isBeginner) {
        for (int round = 0; round < MAX_ROUNDS; round++) {
            archer.registerScoreForRound(round, shootArrows(isBeginner ? 0 : 1));
        }
    }

    private static int[] shootArrows(int min) {
        int[] points = new int[MAX_ARROWS];
        for (int arrow = 0; arrow < MAX_ARROWS; arrow++) {
            points[arrow] = shoot(min);
        }
        return points;
    }

    private static int shoot(int min) {
        return Math.max(min, randomizer.nextInt(11));
    }

    private void setId(int id) {
        if (this.id == 0)
        {
            this.id = id;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setScoreCard(int[][] scoreCard) {
        this.scoreCard = scoreCard;
    }

    public int[][] getScoreCard() {
        return scoreCard;
    }

    @Override
    public String toString()
    {
        return String.format("%d (%d / %d) %s %s",
                this.getId(),
                this.getTotalScore(),
                this.getTotalScore(), // TODO: use weighed score
                this.getFirstName(),
                this.getLastName()
        );
    }


}
