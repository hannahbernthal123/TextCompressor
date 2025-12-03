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
 *  @author Zach Blick, YOUR NAME HERE
 */
public class TextCompressor {

    // Two letter common words.
    public static final int be = 1;
    public static final int to = 2;
    public static final int of = 3;
    public static final int in = 6;
    public static final int he = 14;
    public static final int as = 15;
    public static final int on = 12;
    public static final int it = 10;
    public static final int at = 17;

    // Three letter
    public static final int the = 0;
    public static final int not = 11;
    public static final int and = 4;
    public static final int you = 16;

    // Four letter
    public static final int that = 7;
    public static final int have = 8;
    public static final int with = 13;



    private static void compress() {

        String s = BinaryStdIn.readString();
        int n = s.length();

        BinaryStdOut.write(n);

        // Write out each character
//        for (int i = 0; i < n; i++) {
//            if (i + 1 <= n && s.substring(i,i+1).equals("a")) {
//                BinaryStdOut.write(a);
//            }
//            if (i + 1 <= n && s.substring(i,i+1).equals("I")) {
//                BinaryStdOut.write(a);
//            }



//        }

        String text = "hi i am testing if this even words";
        String[] words = text.split(" ");

        HashMap<String, Integer> counts = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            int count = 0;

            // rescans entire list for each word
            for (int j = 0; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    count++;
                }
            }

            counts.put(words[i], count);
        }

        // Here I would theoretically make escape keys for the words that appeared a bunch.

        BinaryStdOut.close();
    }

    private static void expand() {

        // TODO: Complete the expand() method

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
