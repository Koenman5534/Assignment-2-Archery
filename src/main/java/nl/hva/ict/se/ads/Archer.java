package nl.hva.ict.se.ads;

import java.util.*;

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

    public static final int INITIAL_ID = 135788;
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
     * This methods creates a List of archers.
     *
     * @param nrOfArchers the number of archers in the list.
     * @return
     */
    public static List<Archer> generateArchers(int nrOfArchers) {
        List<Archer> archers = new ArrayList<>(nrOfArchers);
        for (int i = 0; i < nrOfArchers; i++) {
            Archer archer = new Archer(Names.nextFirstName(), Names.nextSurname());
            // Set the first archer ID to the defined starting ID. Add 1 for every subsequent archer
            if (i == 0)
            {
                archer.setId(INITIAL_ID);
            }
            else
            {
                archer.setId(INITIAL_ID + i);
            }
            letArcherShoot(archer, nrOfArchers % 100 == 0);
            archers.add(archer);
        }
        return archers;

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
        return null;
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

    public void setId(int id) {
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
