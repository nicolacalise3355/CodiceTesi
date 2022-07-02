package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    /**
     * https://mkyong.com/java/java-convert-string-to-binary/
     * @param input
     * @return
     */
    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();

    }

    /**
     * https://mkyong.com/java/java-convert-string-to-binary/
     * @param binary
     * @param blockSize
     * @param separator
     * @return
     */
    public static String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }

    /**
     * https://www.geeksforgeeks.org/bitwise-or-of-n-binary-strings/
     * @param input
     * @return
     */
    public static String reverse(String input)
    {
        char[] temparray = input.toCharArray();
        int left, right = 0;
        right = temparray.length - 1;

        for (left = 0; left < right; left++, right--)
        {
            // Swap values of left and right
            char temp = temparray[left];
            temparray[left] = temparray[right];
            temparray[right] = temp;
        }
        return String.valueOf(temparray);
    }

    /**
     * https://www.geeksforgeeks.org/bitwise-or-of-n-binary-strings/
     * @param arr
     * @param n
     * @return
     */
    public static String strBitwiseOR(String[] arr, int n)
    {
        String res="";

        int max_size = Integer.MIN_VALUE;

        // Get max size and reverse each string
        // Since we have to perform OR operation
        // on bits from right to left
        // Reversing the string will make it easier
        // to perform operation from left to right
        for (int i = 0; i < n; i++)
        {
            max_size = Math.max(max_size, (int)arr[i].length());
            arr[i] = reverse(arr[i]);
        }

        for (int i = 0; i < n; i++)
        {

            // Add 0s to the end of strings
            // if needed
            String s="";
            for (int j = 0; j < max_size - arr[i].length(); j++)
                s += '0';

            arr[i] = arr[i] + s;
        }

        // Perform OR operation on each bit
        for (int i = 0; i < max_size; i++)
        {
            int curr_bit = 0;
            for (int j = 0; j < n; j++)
                curr_bit = curr_bit | (arr[j].charAt(i) - '0');

            res += (char)(curr_bit + '0');
        }

        // Reverse the resultant string
        // to get the final string
        res = reverse(res);

        // Return the final string
        return res;
    }
}
