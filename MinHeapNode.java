package huffman;

public class MinHeapNode implements Comparable<MinHeapNode> {
    char data;
    int freq;
    MinHeapNode left, right;

    public MinHeapNode(char data, int freq) {
        this.data = data;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(MinHeapNode other) {
        return Integer.compare(this.freq, other.freq);
    }
}
