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
        int maxSeconds = 20;
        System.out.println("Elements\t\tCol avg.\t\tIn avg.\t\tQuick avg.");
        boolean colDone = false;
        while (nArchers < 5000000) {
            if (colDone)
                break;
            System.out.println("Generating " + nArchers + " archers");
            List<Archer> archers = Archer.generateArchers(nArchers);
            System.out.println("Done");

            // Use the same list
            List<Archer> colSortArchers = archers;

            double colAvg;
            long[] colRuns      = new long[10];


            for (int i = 0; i < 10; i++) {
                long colSortStartTime = System.currentTimeMillis();
                ChampionSelector.collectionSort(colSortArchers, c);
                long colSortEndTime = System.currentTimeMillis();
                colRuns[i] = (colSortEndTime-colSortStartTime);
                if (colRuns[i] > maxSeconds * 1000) {
                    colDone = true;
                    break;
                }
                System.out.println("Done colSorting " + nArchers + " archers (run " + i + ") (" + (int) colRuns[i] + "ms)");
            }
            colAvg      = getAvg(colRuns);

            int colMs, inMs, quickMs;
            colMs = (int) colAvg;

            System.out.println(String.format("%d\t\t%dms",nArchers,colMs));
            nArchers = nArchers * 2;
        }
        System.out.println("Elements\t\tCol avg.\t\tIn avg.\t\tQuick avg.");
        nArchers = 100;
        boolean inDone = false;
        while (nArchers < 5000000) {
            if (inDone)
                break;
            System.out.println("Generating " + nArchers + " archers");
            List<Archer> archers = Archer.generateArchers(nArchers);
            System.out.println("Done");

            // Use the same list
            List<Archer> insSortArchers = archers;

            double inAvg;
            long[] inRuns       = new long[10];

            for (int i = 0; i < 10; i++) {
                long inSortStartTime = System.currentTimeMillis();
                ChampionSelector.selInsSort(insSortArchers, c);
                long inSortEndTime = System.currentTimeMillis();
                inRuns[i] = (inSortEndTime-inSortStartTime);
                if (inRuns[i] > maxSeconds * 1000) {
                    inDone = true;
                    break;
                }
                System.out.println("Done inSorting " + nArchers + " archers (run " + i + ") (" + (int) inRuns[i] + "ms)");
            }
            inAvg       = getAvg(inRuns);

            int inMs;
            inMs = (int) inAvg;

            System.out.println(String.format("%d\t\t%dms",nArchers,inMs));
            nArchers = nArchers * 2;
        }
        System.out.println("Elements\t\tCol avg.\t\tIn avg.\t\tQuick avg.");
        nArchers = 100;
        boolean quickDone = false;
        while (nArchers < 5000000) {
            if (quickDone)
                break;
            System.out.println("Generating " + nArchers + " archers");
            List<Archer> archers = Archer.generateArchers(nArchers);
            System.out.println("Done");

            // Use the same list
            List<Archer> quickSortArchers = archers;


            double quickAvg;
            long[] quickRuns    = new long[10];

            for (int i = 0; i < 10; i++) {
                long quickSortStartTime = System.currentTimeMillis();
                ChampionSelector.quickSort(quickSortArchers, c, 0, quickSortArchers.size() -1);
                long quickSortEndTime = System.currentTimeMillis();
                quickRuns[i] = (quickSortEndTime-quickSortStartTime);
                if (quickRuns[i] > maxSeconds * 1000) {
                    quickDone = true;
                    break;
                }
                System.out.println("Done quicksorting " + nArchers + " archers (run " + i + ") (" + (int) quickRuns[i] + "ms)");
            }
            quickAvg    = getAvg(quickRuns);

            int quickMs;
            quickMs = (int) quickAvg;

            System.out.println(String.format("%d\t\t%dms",nArchers,quickMs));
            nArchers = nArchers * 2;
        }
    }

    double getAvg(long[] vals)
    {
        int nonZeroes = 0;
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] != 0) {
                nonZeroes++;
            }
        }
        long[] arr = new long[nonZeroes];
        for (int i = 0; i < nonZeroes; i++) {
            if (vals[i] != 0) {
                arr[i] = vals[i];
            }
        }
        return Arrays.stream(arr).average().orElse(Double.NaN);
    }
}
