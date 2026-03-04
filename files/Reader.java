public class Reader {

    private final RingBuffer rb;
    private long nextSeq;

    public Reader(RingBuffer rb, long startSeq) {
        this.rb = rb;
        this.nextSeq = startSeq;
    }

    // Returns null if there is nothing new to read
    public Integer read() {
        synchronized (rb) {
            long newest = rb.getWriteSeq();
            long oldest = Math.max(0, newest - rb.getCapacity());

            if (nextSeq < oldest) {
                nextSeq = oldest;
            }

            if (nextSeq >= newest) {
                return null;
            }

            int value = rb.getValueAt(nextSeq);
            nextSeq++;
            return value;
        }
    }
}
