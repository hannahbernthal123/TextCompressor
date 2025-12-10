/******************************************************************************
 *  Compilation:  javac TextCompressor.java
 *  Execution:    java TextCompressor - < input.txt   (compress)
 *  Execution:    java TextCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   abra.txt
 *                jabberwocky.txt
 *                shakespeare.txt
 *                virus.txt
 *
 *  % java DumpBinary 0 < abra.txt
 *  136 bits
 *
 *  % java TextCompressor - < abra.txt | java DumpBinary 0
 *  104 bits    (when using 8-bit codes)
 *
 *  % java DumpBinary 0 < alice.txt
 *  1104064 bits
 *  % java TextCompressor - < alice.txt | java DumpBinary 0
 *  480760 bits
 *  = 43.54% compression ratio!
 ******************************************************************************/

import java.util.HashMap;
import java.util.Map;

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Hannah Bernthal
 */
public class TextCompressor {
    public static final int bitChunk = 12;

    private static void compress() {

        TST TST = new TST();
        String text = BinaryStdIn.readString();
        int length = text.length();
        int index = 0;

        // New codes start at 81
        int newCodes = 81;
        int EOF = 80;

        // Variables used in compress
        String prefix;
        int preCode;
        int newIndex;
        String newPrefix;

        // 12 bits can represent 4096 codes, so the max "new code" value is 4095 because you start at 0.
        int max = 4095;

        // Go through each letter in the string, starting at 0.
        while (index < length) {
            // Find the longest prefix
            prefix = TST.getLongestPrefix(text, index);

            // Find the code with that prefix
            preCode = TST.lookup(prefix);

            // Write out the code to the file
            BinaryStdOut.write(preCode, bitChunk);

            // Create temporary index variable to look ahead and add new prefix
            newIndex = index + prefix.length() + 1;

            // Check to make sure there is space to even look ahead in both the dictionary AND the text
            if (newCodes < max && newIndex < length) {

                // Add to our current prefix the first char after our previous prefix.
                newPrefix = prefix + text.charAt(newIndex);

                // Add the new prefix to our TST.
                TST.insert(newPrefix, newCodes);

                // Index the number to prime it for the next code.
                newCodes++;

            }

            index++;

        }

        // Write the EOF marker.
        BinaryStdOut.write(EOF, bitChunk);
        BinaryStdOut.close();
    }

    private static void expand() {
        String[] map = new String[numBits];

        if (map[lookupCode] == null) {
            String lookaheadString = map[index] + map[index].charAt(0);
            BinaryStdOut.write(lookaheadString);
        }


        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
