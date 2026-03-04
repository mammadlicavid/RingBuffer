public class Main {
    public static void main(String[] args) {

        RingBuffer rb = new RingBuffer(3);

        Reader slow = rb.createReader();

        rb.write(1);
        rb.write(2);
        rb.write(3);
        rb.write(4); // overwrites 1

        System.out.println(slow.read()); // should print 2 (1 was missed)
        System.out.println(slow.read()); // 3
        System.out.println(slow.read()); // 4
        System.out.println(slow.read()); // null
    }
}
