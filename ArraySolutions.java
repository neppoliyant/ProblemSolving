import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nthangavelu on 3/30/17.
 */
public final class ArraySolutions {
    public static void printArrayInZigZagOrder(String[][] array, int totalRow, int totalColumn) {
        int rStart = 0, rEnd = totalRow - 1, cStart = 0, cEnd = totalColumn - 1;

        int startRowPosition = 0, startColumnPosition = totalColumn - 1;

        List<String> endResult = new ArrayList<>();

        char direction = 'W';
        int count = 0;
        while (count < (totalColumn * totalRow)) {
            endResult.add(array[startRowPosition][startColumnPosition]);
            count++;

            switch (direction) {
                case 'N':
                    startRowPosition--;
                    if (startRowPosition == rStart) {
                        cEnd--;
                        direction = 'W';
                    }
                    break;
                case 'S':
                    startRowPosition++;
                    if (startRowPosition == rEnd) {
                        cStart++;
                        direction = 'E';
                    }
                    break;
                case 'E':
                    startColumnPosition++;
                    if (startColumnPosition == cEnd) {
                        rEnd--;
                        direction = 'N';
                    }
                    break;
                case 'W':
                    startColumnPosition--;
                    if (startColumnPosition == cStart) {
                        rStart++;
                        direction = 'S';
                    }
                    break;
            }
        }
        System.out.println(endResult);
    }

    public static int[] MultiplyAndAssignInSameVariable(int[] arr) {
        int resultOfArrayMultiplication = 1;

        for (int item : arr) {
            resultOfArrayMultiplication *= item;
        }

        final int result = resultOfArrayMultiplication;

        return Arrays.stream(arr)
                .map(i -> Math.floorDiv(result, i)).toArray();
    }

    public static void permutationCombinationOfAnGivenStringArray(String search_query) {
        String[] queries = search_query.split(" ");
        StringBuilder sb = new StringBuilder();
        int count = 0;
        String startString = "";
        sb.append(queries[count] + "\n");
        while (count < queries.length) {
            startString += queries[count] + " ";
            for (int j = count + 1; j < queries.length; j++) {
                sb.append(startString + queries[j] + "\n");
            }
            count++;
        }
        System.out.print(sb.toString());
    }

    public static void createChristmasTree(int treeSize) {
        int count = treeSize;
        String startSpace = "";
        StringBuilder sb = new StringBuilder();
        while (count > 0) {
            for (int i = 0; i < count; i++) {
                sb.append("* ");
            }
            sb.append(startSpace);
            sb.append("\n");
            count--;
            startSpace += " ";
        }
        System.out.println(sb.reverse().toString());
    }



    public static boolean split53(int[] nums) {
        return split53Helper(0, nums, 0, 0);
    }

    public static boolean split53Helper(int start, int[] nums, int mult5, int mult3) {
        if (start >= nums.length)
            return mult5 == mult3;

        if (nums[start] % 5 == 0)
            return split53Helper(start + 1, nums, mult5 + nums[start], mult3);

        if (nums[start] % 3 == 0)
            return split53Helper(start + 1, nums, mult5, mult3 + nums[start]);

        if (split53Helper(start + 1, nums, mult5 + nums[start], mult3))
            return true;

        if (split53Helper(start + 1, nums, mult5, mult3 + nums[start]))
            return true;

        return false;
    }

    public static boolean groupSum5(int start, int[] nums, int target) {
        if (start >= nums.length)
            return target == 0;

        if (nums[start] % 5 == 0) {
            if (start <= nums.length - 2 && nums[start + 1] == 1)
                return groupSum5(start + 2, nums, target - nums[start]);

            return groupSum5(start + 1, nums, target - nums[start]);
        }

        if (groupSum5(start + 1, nums, target - nums[start]))
            return true;

        if (groupSum5(start + 1, nums, target))
            return true;

        return false;
    }

    public static boolean splitArray(int[] nums) {
        return splitArrayHelper(0, nums, 0, 0);
    }

    public static boolean splitArrayHelper(int start, int[] nums, int group1, int group2) {
        if (start >= nums.length) {
            return group1 == group2;
        }

        if (splitArrayHelper(start + 1, nums, group1 + nums[start], group2)) {
            return true;
        }

        if (splitArrayHelper(start + 1, nums, group1, group2 + nums[start])) {
            return true;
        }

        return false;
    }

    public static boolean groupSum6(int start, int[] nums, int target) {
        if (start >= nums.length)
            return target == 0;

        if (nums[start] == 6)
            return groupSum6(start + 1, nums, target - 6);

        if (groupSum6(start + 1, nums, target - nums[start]))
            return true;

        if (groupSum6(start + 1, nums, target))
            return true;

        return false;
    }

    public static boolean groupSum(int start, int[] nums, int target) {
        if (start >= nums.length) {
            return target == 0;
        }

        if (groupSum(start + 1, nums, target - nums[start])) {
            return true;
        }

        if (groupSum(start + 1, nums, target)) {
            return true;
        }

        return false;
    }

    public boolean groupSumClump(int start, int[] nums, int target) {
        if (start >= nums.length)
            return target == 0;

        int i = start;
        int sum = 0;

        while (i < nums.length && nums[start] == nums[i]) {
            sum += nums[i];
            i++;
        }

        if (groupSumClump(i, nums, target - sum))
            return true;

        if (groupSumClump(i, nums, target))
            return true;

        return false;
    }

    public boolean groupNoAdj(int start, int[] nums, int target) {
        if (start >= nums.length)
            return target == 0;

        if (groupNoAdj(start + 2, nums, target - nums[start]))
            return true;

        if (groupNoAdj(start + 1, nums, target))
            return true;

        return false;
    }

    public boolean splitOdd10(int[] nums) {
        return splitOdd10Helper(0, nums, 0, 0);
    }

    public boolean splitOdd10Helper(int start, int[] nums, int mult, int odd) {
        if (start >= nums.length)
            return mult % 10 == 0 && odd % 2 == 1;

        if (splitOdd10Helper(start + 1, nums, mult + nums[start], odd))
            return true;

        if (splitOdd10Helper(start + 1, nums, mult, odd + nums[start]))
            return true;

        return false;
    }



    public static boolean endsLy(String str) {
        if (str.length() >= 2) {
            return str.substring(str.length()-2).equalsIgnoreCase("ly");
        }
        return false;
    }

    public static String firstHalf(String str) {
        return str.substring(0, str.length() / 2);
    }

    public static String without2(String str) {
        if (str.length() >= 2 && str.substring(str.length() - 2).equalsIgnoreCase(str.substring(0, 2))) {
            return str.substring(2);
        }
        return str;
    }

    public static String withouEnd2(String str) {
        if (str.length() >= 2) {
            return str.substring(1, str.length() - 1);
        }

        return "";

    }

    public static boolean hasBad(String str) {
        if (str.length() > 3) {
            return str.substring(0, 3).equals("bad") || str.substring(1, 4).equals("bad");
        } else if (str.length() == 3) {
            return str.substring(0, 3).equals("bad");
        }
        return false;
    }

    public static String withoutX2(String str) {
        if (str.length() > 2) {
            return str.substring(0, 2).replaceAll("x", "") + str.substring(2);
        }
        return str.replace("x", "");
    }

    public Map<String, Integer> word0(String[] strings) {
        Map<String, Integer> map = new HashMap<>();

        for (String s: strings) {
            if (map.containsKey(s)) {
                continue;
            } else {
                map.put(s, 0);
            }
        }

        return map;
    }


    public static int[] doSelectionSort(int[] arr){

        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[index])
                    index = j;

            int smallerNumber = arr[index];
            arr[index] = arr[i];
            arr[i] = smallerNumber;
        }
        return arr;
    }

    public static void main(String a[]) {

        int row = 100, col =100, dim =50;

        int[][][] threeDimension = triangleOrTree(row, col, dim);

        System.out.println(threeDimension);
    }

    public static int[][][] circleOrBall(int row, int col, int dim, int radius) {
        int[][][] threeDimension = new int[dim][row][col];

        int rStart =0, rEnd=0, cStart=0, cEnd=0, sDim =0;

        int cCol = col/2, cRow = row/2;

        threeDimension[sDim][cRow][cCol] = 1;
        sDim++;

        while(sDim < radius) {
            rStart = cRow - sDim;
            cStart = cCol - sDim;
            rEnd = cRow + sDim;
            cEnd = cCol + sDim;

            for(; rStart <= rEnd; rStart++) {
                for(int j=cStart; j<= cEnd; j++) {
                    threeDimension[sDim][rStart][j] = 1;
                }
            }
            sDim++;
        }
        return threeDimension;
    }


    public static int[][][] triangleOrTree(int row, int col, int dim) {
        int[][][] threeDimension = new int[dim][row][col];

        int rStart =0, rEnd=0, cStart=0, cEnd=0, sDim =0;

        int cCol = col/2, cRow = row/2;

        threeDimension[sDim][cRow][cCol] = 1;
        sDim++;

        while(sDim < dim) {
            rStart = cRow - sDim;
            cStart = cCol - sDim;
            rEnd = cRow + sDim;
            cEnd = cCol + sDim;

            for(; rStart <= rEnd; rStart++) {
                for(int j=cStart; j<= cEnd; j++) {
                    threeDimension[sDim][rStart][j] = 1;
                }
            }
            sDim++;
        }
        return threeDimension;
    }
}
