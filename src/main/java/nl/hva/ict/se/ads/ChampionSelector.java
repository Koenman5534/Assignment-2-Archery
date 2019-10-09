package nl.hva.ict.se.ads;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Given a list of Archer's this class can be used to sort the list using one of three sorting algorithms.
 */
public class ChampionSelector {

    private static ArcherComparator archerComparator = new ArcherComparator();

    /**
     * This method uses either selection sort or insertion sort for sorting the archers.
     */
    public static List<Archer> selInsSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        int i, j;

        for (i = 1; i < archers.size(); i++) {
            Archer archer = archers.get(i);
            j = i;
            while ((j > 0) && (archerComparator.compare(archers.get(j - 1), archer) < 0)) {
                archers.set(j, archers.get(j - 1));
                j--;
            }
            archers.set(j, archer);
        }

        Collections.reverse(archers);
        return archers;
    }

    /**
     * This method uses quick sort for sorting the archers.
     */
    public static List<Archer> quickSort(List<Archer> archers, Comparator<Archer> scoringScheme, int from, int to)
    {
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            while (left <= right) {
                while (left <= to && scoringScheme.compare(archers.get(pivot), archers.get(left)) > -1) {
                    left++;
                }
                while (right > from && scoringScheme.compare(archers.get(pivot), archers.get(right)) < 0) {
                    right--;
                }
                if (left < right) {
                    Collections.swap(archers, left, right);
                }
            }
            Collections.swap(archers, pivot, left-1);
            quickSort(archers, scoringScheme, from, right-1);
            quickSort(archers, scoringScheme, right+1, to);
        }
        return archers;
    }

    /**
     * This method uses the Java collections sort algorithm for sorting the archers.
     */
    public static List<Archer> collectionSort(List<Archer> archers, Comparator<Archer> scoringScheme) {
        archers.sort(scoringScheme);
        return archers;
    }

    /**
     * This method uses quick sort for sorting the archers in such a way that it is able to cope with an Iterator.
     *
     * <b>THIS METHOD IS OPTIONAL</b>
     */
    public static Iterator<Archer> quickSort(Iterator<Archer> archers, Comparator<Archer> scoringScheme) {
        return null;
    }

    private int partition(int[] arr, int low, int high)
    {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++)
        {
            if (arr[j] < pivot)
            {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i+1, high);

        return i + 1;
    }

    private void swap(int[] arr, int i, int j)
    {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
