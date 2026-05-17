package com.example.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class RingBufferTest {

    @Test
    public void emptyRead() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        // here I check that reading from an empty buffer gives nothing
        assertNull(reader.read());
    }

    @Test
    public void oneValue() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(10);

        // after writing one value, logically the reader should get that value
        assertEquals(Integer.valueOf(10), reader.read());

        // after that there is nothing new to read
        assertNull(reader.read());
    }

    @Test
    public void readInOrder() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(1);
        rb.write(2);
        rb.write(3);

        // here I check that values come out in the same order they were written
        assertEquals(Integer.valueOf(1), reader.read());
        assertEquals(Integer.valueOf(2), reader.read());
        assertEquals(Integer.valueOf(3), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void overwriteOldValue() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(1);
        rb.write(2);
        rb.write(3);
        rb.write(4);

        // capacity is 3, so after writing 4 values, the oldest value 1 is overwritten
        // logically we should get 2, 3, 4
        assertEquals(Integer.valueOf(2), reader.read());
        assertEquals(Integer.valueOf(3), reader.read());
        assertEquals(Integer.valueOf(4), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void newReaderAfterWrites() {
        RingBuffer rb = new RingBuffer(3);

        rb.write(1);
        rb.write(2);
        rb.write(3);
        rb.write(4);
        rb.write(5);

        Reader reader = rb.createReader();

        // this reader is created after the writes
        // the buffer can only keep the last 3 values, so we should get 3, 4, 5
        assertEquals(Integer.valueOf(3), reader.read());
        assertEquals(Integer.valueOf(4), reader.read());
        assertEquals(Integer.valueOf(5), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void twoReaders() {
        RingBuffer rb = new RingBuffer(3);

        Reader r1 = rb.createReader();
        Reader r2 = rb.createReader();

        rb.write(7);
        rb.write(8);

        // here I check that first reader can read both values
        assertEquals(Integer.valueOf(7), r1.read());
        assertEquals(Integer.valueOf(8), r1.read());

        // second reader should still get the same values
        // because one reader should not remove data for another reader
        assertEquals(Integer.valueOf(7), r2.read());
        assertEquals(Integer.valueOf(8), r2.read());

        assertNull(r1.read());
        assertNull(r2.read());
    }

    @Test
    public void readThenWriteMore() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(1);

        // Reader reads the first value
        assertEquals(Integer.valueOf(1), reader.read());
        assertNull(reader.read());

        rb.write(2);
        rb.write(3);

        // after new writes, the same reader should continue from the next unread value
        assertEquals(Integer.valueOf(2), reader.read());
        assertEquals(Integer.valueOf(3), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void slowReaderMissesData() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(1);
        assertEquals(Integer.valueOf(1), reader.read());

        rb.write(2);
        rb.write(3);
        rb.write(4);
        rb.write(5);

        // reader did not read 2 in time
        // since capacity is 3, only 3, 4, 5 are still available
        assertEquals(Integer.valueOf(3), reader.read());
        assertEquals(Integer.valueOf(4), reader.read());
        assertEquals(Integer.valueOf(5), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void capacityOne() {
        RingBuffer rb = new RingBuffer(1);
        Reader reader = rb.createReader();

        rb.write(1);
        rb.write(2);
        rb.write(3);

        // with capacity 1, every new write overwrites the previous one
        // logically only the newest value 3 should remain
        assertEquals(Integer.valueOf(3), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void zeroValue() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(0);

        // here I check that 0 is stored as a normal value
        assertEquals(Integer.valueOf(0), reader.read());
        assertNull(reader.read());
    }

    @Test
    public void negativeValues() {
        RingBuffer rb = new RingBuffer(3);
        Reader reader = rb.createReader();

        rb.write(-1);
        rb.write(-5);

        // here I check that negative numbers also work
        assertEquals(Integer.valueOf(-1), reader.read());
        assertEquals(Integer.valueOf(-5), reader.read());
        assertNull(reader.read());
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroCapacity() {
        // capacity cannot be 0, so this should throw an exception
        new RingBuffer(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeCapacity() {
        // negative capacity also should not be accepted
        new RingBuffer(-3);
    }
}