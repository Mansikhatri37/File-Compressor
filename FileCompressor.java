package huffman;

import java.io.*;
import java.util.*;

public class FileCompressor {

    public static void compressFile(String inputFilePath) {
        try (BufferedReader inputFile = new BufferedReader(new FileReader(inputFilePath))) {

            Map<Character, Integer> freqMap = new HashMap<>();
            int ch;

            // Count character frequencies
            while ((ch = inputFile.read()) != -1) {
                char character = (char) ch;
                freqMap.put(character, freqMap.getOrDefault(character, 0) + 1);
            }

            // Build Huffman Tree
            MinHeapNode root = HuffmanTree.buildHuffmanTree(freqMap);

            // Generate Huffman codes
            Map<Character, String> huffmanCodes = new HashMap<>();
            HuffmanTree.generateCodes(root, "", huffmanCodes);

            // Reopen input file for encoding
            inputFile.close();
            try (BufferedReader inputFileReader = new BufferedReader(new FileReader(inputFilePath));
                 FileOutputStream outputFile = new FileOutputStream(inputFilePath + ".huff")) {

                StringBuilder encodedString = new StringBuilder();
                while ((ch = inputFileReader.read()) != -1) {
                    encodedString.append(huffmanCodes.get((char) ch));
                }

                // Convert string of '0' and '1' to binary
                BitSet bitSet = new BitSet();
                int bitIndex = 0;
                for (char bit : encodedString.toString().toCharArray()) {
                    if (bit == '1') {
                        bitSet.set(bitIndex);
                    }
                    bitIndex++;
                }

                // Write the binary encoded data
                outputFile.write(bitSet.toByteArray());

                System.out.println("File compressed successfully to: " + inputFilePath + ".huff");

            } catch (IOException e) {
                System.err.println("Error during file writing: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the file to compress: ");
        String inputFilePath = scanner.nextLine();
        scanner.close();

        compressFile(inputFilePath);
    }
}
