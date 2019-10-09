package nl.hva.ict.se.ads;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Place all your own tests for ChampionSelector in this class. Tests in any other class will be ignored!
 */
public class ExtendedChampionSelectorTest extends ChampionSelectorTest {
    @Test
    void testSelInSortAndCollectionSortAndQuickSortResultInSameOrder()
    {
        List<Archer> unsortedArchersForSelIns = Archer.generateArchers(23);
        List<Archer> unsortedArchersForCollection = new ArrayList<>(unsortedArchersForSelIns);

        List<Archer> sortedArchersSelIns = ChampionSelector.selInsSort(unsortedArchersForSelIns, comparator);
        List<Archer> sortedArchersCollection = ChampionSelector.collectionSort(unsortedArchersForCollection, comparator);

        assertEquals(sortedArchersCollection, sortedArchersSelIns);
    }

    @Test
    void benchmarkSortingAlgorithms()
    {
        Comparator<Archer> c = new ArcherComparator();
        int nArchers = 100;
        System.out.println("Elements\t\tCol avg.\t\tIn avg.\t\tQuick avg.");
        while (nArchers < 5000000) {
            System.out.println("Generating " + nArchers + " archers");
            List<Archer> archers = Archer.generateArchers(nArchers);
            System.out.println("Done");

            // Use the same list
            List<Archer> colSortArchers = archers;
            List<Archer> insSortArchers = archers;
            List<Archer> quickSortArchers = archers;

            double colAvg, inAvg, quickAvg;
            long[] colRuns      = new long[10];
            long[] inRuns       = new long[10];
            long[] quickRuns    = new long[10];

            for (int i = 0; i < 10; i++) {
                long colSortStartTime = System.nanoTime();
                ChampionSelector.collectionSort(colSortArchers, c);
                long colSortEndTime = System.nanoTime();
                colRuns[i] = (colSortEndTime-colSortStartTime);
                System.out.println("Done colSorting " + nArchers + " archers (run " + i + ") (" + (int) colRuns[i]/1000000 + "ms)");

                long inSortStartTime = System.nanoTime();
                ChampionSelector.selInsSort(colSortArchers, c);
                long inSortEndTime = System.nanoTime();
                inRuns[i] = (inSortEndTime-inSortStartTime);
                System.out.println("Done inSorting " + nArchers + " archers (run \" + i + \") (" + (int) colRuns[i]/1000000 + "ms)");

                long quickSortStartTime = System.nanoTime();
                ChampionSelector.quickSort(colSortArchers, c);
                long quickSortEndTime = System.nanoTime();
                quickRuns[i] = quickSortEndTime - quickSortStartTime;
                System.out.println("Done quickSorting " + nArchers + " archers (run \" + i + \") (" + (int) colRuns[i]/1000000 + "ms)");
            }
            colAvg      = getAvg(colRuns);
            inAvg       = getAvg(inRuns);
            quickAvg    = getAvg(quickRuns);

            int colMs, inMs, quickMs;
            colMs = (int) colAvg/1000000;
            inMs = (int) inAvg/1000000;
            quickMs = (int) quickAvg/1000000;

            System.out.println(String.format("%d\t\t%dms\t\t%dms\t\t%dms",nArchers,colMs,inMs,quickMs));
            nArchers = nArchers * 2;
        }
    }

    double getAvg(long[] vals)
    {
        return Arrays.stream(vals).average().orElse(Double.NaN);
    }
}
