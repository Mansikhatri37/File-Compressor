package huffman;

import java.util.*;

public class HuffmanTree {

    // Build Huffman Tree from frequency map
    public static MinHeapNode buildHuffmanTree(Map<Character, Integer> freqMap) {
        PriorityQueue<MinHeapNode> minHeap = new PriorityQueue<>();

        // Create a min-heap for each character and its frequency
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            minHeap.add(new MinHeapNode(entry.getKey(), entry.getValue()));
        }

        // Build the tree
        while (minHeap.size() > 1) {
            MinHeapNode left = minHeap.poll();
            MinHeapNode right = minHeap.poll();

            MinHeapNode newNode = new MinHeapNode('$', left.freq + right.freq);
            newNode.left = left;
            newNode.right = right;

            minHeap.add(newNode);
        }

        return minHeap.poll();
    }

    // Generate Huffman codes for each character
    public static void generateCodes(MinHeapNode root, String code, Map<Character, String> huffmanCodes) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.data, code);
        }

        generateCodes(root.left, code + "0", huffmanCodes);
        generateCodes(root.right, code + "1", huffmanCodes);
    }
}
