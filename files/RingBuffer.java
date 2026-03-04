public class RingBuffer {

    private final int capacity;
    private final int[] buffer;
    private final long[] seq;
    private long writeSeq;

    public RingBuffer(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");

        this.capacity = capacity;
        this.buffer = new int[capacity];
        this.seq = new long[capacity];

        for (int i = 0; i < capacity; i++) {
            seq[i] = -1;
        }

        this.writeSeq = 0;
    }

    public synchronized void write(int x) {
        int index = (int) (writeSeq % capacity);
        buffer[index] = x;
        seq[index] = writeSeq;
        writeSeq++;
    }

    public Reader createReader() {
        long start;
        synchronized (this) {
            start = Math.max(0, writeSeq - capacity);
        }
        return new Reader(this, start);
    }

    // package-private helpers for Reader (same package)
    synchronized long getWriteSeq() {
        return writeSeq;
    }

    int getCapacity() {
        return capacity;
    }

    int getValueAt(long sequence) {
        int index = (int) (sequence % capacity);
        return buffer[index];
    }
}
