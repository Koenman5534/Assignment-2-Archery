package nl.hva.ict.se.ads;

import java.util.Comparator;

public class ArcherComparator implements Comparator<Archer> {
    @Override
    public int compare(Archer o1, Archer o2) {
        int o1Total = o1.getTotalScore();
        int o2Total = o2.getTotalScore();
        if (o1Total < o2Total) // Compare the regular scores
        {
            return -1;
        }
        else if (o1Total == o2Total) // If they are equal, we'll use the weighted scores
        {
            int o1Weighted = calculateWeightedScore(o1);
            int o2Weighted = calculateWeightedScore(o2);

            if (o1Weighted < o2Weighted)
            {
                return -1;
            }
            // if the weighted scores are equal, the archer with the highest id wins
            else if (o1Weighted == o2Weighted)
            {
                if (o1.getId() < o2.getId())
                {
                    return 1;
                }

                return -1;
            }

            return 1;
        }

        return 1;
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
    private int calculateWeightedScore(Archer a)
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
}
