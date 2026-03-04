public class Main {
    public static void main(String[] args) {

        RingBuffer rb = new RingBuffer(3);

        Reader r1 = rb.createReader();
        Reader r2 = rb.createReader();

        rb.write(10);
        rb.write(20);

        System.out.println(r1.read()); // 10
        System.out.println(r2.read()); // 10
        System.out.println(r2.read()); // 20

        rb.write(30);
        rb.write(40); // overwrites oldest

        System.out.println(r1.read()); // 20 (still ok)
        System.out.println(r1.read()); // 30
        System.out.println(r1.read()); // 40
        System.out.println(r1.read()); // null
    }
}
