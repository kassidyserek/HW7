/******************************************************************
 *
 *   KASS SEREK / COMP272 002
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values        - int[] array to be sorted.
     * @param ascending     - if true,method performs an ascending sort, else descending.
     *                        There are two method signatures allowing this parameter
     *                        to not be passed and defaulting to 'true (or ascending sort).
     */

    public  void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending ) {
        // length of array
        int n = values.length;

        // iterate through entire array excluding the last element
        for (int i = 0; i < n - 1; i++) {
            // current element
            int selectedIndex = i;
            // find the smallest or largest element in the remaining unsorted part of the array
            for (int j = i + 1; j < n; j++) {
                // find minimum if ascending, find maximum if descending
                if ((ascending && values[j] < values[selectedIndex]) ||
                        (!ascending && values[j] > values[selectedIndex])) {
                    // update index if found
                    selectedIndex = j;
                }
            }
            // switch the selected element with the current at i
            int temp = values[selectedIndex];
            values[selectedIndex] = values[i];
            values[i] = temp;

        }

    } // End class selectionSort


    /**
     *  Method mergeSortDivisibleByKFirst
     *
     *  This method will perform a merge sort algorithm. However, all numbers
     *  that are divisible by the argument 'k', are returned first in the sorted
     *  list. Example:
     *        values = { 10, 3, 25, 8, 6 }, k = 5
     *        Sorted result should be --> { 10, 25, 3, 6, 8 }
     *
     *        values = { 30, 45, 22, 9, 18, 39, 6, 12 }, k = 6
     *        Sorted result should be --> { 30, 18, 6, 12, 9, 22, 39, 45 }
     *
     * As shown above, this is a normal merge sort operation, except for the numbers
     * divisible by 'k' are first in the sequence.
     *
     * @param values    - input array to sort per definition above
     * @param k         - value k, such that all numbers divisible by this value are first
     */

    public void mergeSortDivisibleByKFirst(int[] values, int k) {
        // Protect against bad input values
        if (k == 0)  return;
        if (values.length <= 1)  return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length-1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first
     */

    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right)
    {
        //temporary array to hold the merged result
        int[] temp = new int[right - left + 1];
        int idx = 0;

        //pointers for the left and right halves
        int i = left, j = mid + 1;

        //merge both halves
        while (i <= mid && j <= right) {
            if (arr[i] % k == 0 && arr[j] % k != 0) {
                temp[idx++] = arr[i++];
            } else if (arr[j] % k == 0 && arr[i] % k != 0) {
                temp[idx++] = arr[j++];
            } else if (arr[i] % k == 0 && arr[j] % k == 0) {
                temp[idx++] = arr[i++]; // Maintain original order for divisible
            } else {
                temp[idx++] = arr[i] <= arr[j] ? arr[i++] : arr[j++]; // Ascending order for non-divisible
            }
        }

        //add remaining elements from the left half
        while (i <= mid) {
            temp[idx++] = arr[i++];
        }

        //add remaining elements from the right half
        while (j <= right) {
            temp[idx++] = arr[j++];
        }

        //copy the merged result back into the original array
        for (int t = 0; t < temp.length; t++) {
            arr[left + t] = temp[t];
        }
    }


    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order.
     * If the mass of the planet is greater than or equal to the mass of the asteroid, the
     * asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the
     * planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     *
     * Example 1:
     *   Input: mass = 10, asteroids = [3,9,19,5,21]
     *   Output: true
     *
     * Explanation: One way to order the asteroids is [9,19,5,3,21]:
     * - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
     * - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
     * - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
     * - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
     * - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
     * All asteroids are destroyed.
     *
     * Example 2:
     *   Input: mass = 5, asteroids = [4,9,23,4]
     *   Output: false
     *
     * Explanation:
     * The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
     * After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
     * This is less than 23, so a collision would not destroy the last asteroid.
     *
     * Constraints:
     *     1 <= mass <= 105
     *     1 <= asteroids.length <= 105
     *     1 <= asteroids[i] <= 105
     *
     * @param mass          - integer value representing the mass of the planet
     * @param asteroids     - integer array of the mass of asteroids
     * @return              - return true if all asteroids destroyed, else false.
     */

    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {
        // sort the asteroids array in ascending order
        Arrays.sort(asteroids);

        // long to store the current mass to prevent overflow
        long currentMass = mass;

        // iterate through each asteroid
        for (int asteroid : asteroids) {
            // we can't destroy it if the current mass is less than the asteroid's size
            if (currentMass < asteroid) {
                return false;
            }
            // otherwise add its size to the current mass
            currentMass += asteroid;
        }

        // if all the asteroids can be destroyed return true
        return true;
    }


    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time, provided the
     * sum of the weight of those people is at most limit. Return the minimum number
     * of rescue sleds to carry every given person.
     *
     * Example 1:
     *    Input: people = [1,2], limit = 3
     *    Output: 1
     *    Explanation: 1 sled (1, 2)
     *
     * Example 2:
     *    Input: people = [3,2,2,1], limit = 3
     *    Output: 3
     *    Explanation: 3 sleds (1, 2), (2) and (3)
     *
     * Example 3:
     *    Input: people = [3,5,3,4], limit = 5
     *    Output: 4
     *    Explanation: 4 sleds (3), (3), (4), (5)
     *
     * @param people    - an array of weights for people that need to go in a sled
     * @param limit     - the weight limit per sled
     * @return          - the minimum number of rescue sleds required to hold all people
     */

    public static int numRescueSleds(int[] people, int limit) {
        // sort so pairs can be efficient
        Arrays.sort(people);

        // pointer starting from lightest person
        int left = 0;
        // pointer starting from heaviest person
        int right = people.length - 1;
        // count for number of sleds needed
        int sleds = 0;

        // go through all the people until all are assigned to a sled
        while (left <= right) {
            // if the lightest and heaviest person can share a sled move the pointer forward
            if (people[left] + people[right] <= limit) {
                left++;
            }
            // the heaviest person gets a sled in all cases so move the pointer backward
            right--;

            // increment the sled count for each pair
            sleds++;
        }

        // reutrn total number of sleds needed
        return sleds;
    }

} // End Class ProblemSolutions
