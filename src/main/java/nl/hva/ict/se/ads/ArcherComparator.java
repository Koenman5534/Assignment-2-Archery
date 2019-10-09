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
            int o1Weighted = Archer.calculateWeightedScore(o1);
            int o2Weighted = Archer.calculateWeightedScore(o2);

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
}
