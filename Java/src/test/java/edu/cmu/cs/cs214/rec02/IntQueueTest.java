package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


/**
 * TODO: Write more unit tests to test the implementation of ArrayIntQueue
 * for the {@link LinkedIntQueue} and
 * {@link ArrayIntQueue} classes, as described in the handout. The
 * {@link ArrayIntQueue} class contains a few bugs. Use the tests you wrote for
 * the {@link LinkedIntQueue} class to test the {@link ArrayIntQueue}
 *
 * @author Alex Lockwood, George Guo
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    private final int arraySize = 20;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
       mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(Stream.iterate(1, n->n+1).limit(arraySize)
            .collect(Collectors.toList()));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
        assertTrue(mQueue.size() == 0);
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        Integer input = 1;
        mQueue.enqueue(input);
        assertEquals(input, mQueue.peek());
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testIsEmptyAfterChange() {
        mQueue.enqueue(1);
        assertTrue(mQueue.peek() == 1);
        mQueue.dequeue();
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        testList.forEach(n -> mQueue.enqueue(n));
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), mQueue.dequeue());
            assertEquals(testList.size() - i - 1, mQueue.size());
        }
    }

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testEnsureCapacity() {
        final int originalSize = 10;
        for (int i = 0; i < originalSize; i++) {
            mQueue.enqueue(testList.get(i));
        }
        mQueue.dequeue();
        mQueue.enqueue(11);
        mQueue.enqueue(12);
        mQueue.dequeue();
        for (int i = 0; i < originalSize; i++) {
            assertEquals(testList.get(i+2), mQueue.dequeue());
            assertEquals(originalSize - i - 1, mQueue.size());
        }
    }

    @Test
    public void testClear() {
        testList.forEach(n -> mQueue.enqueue(n));
        assertFalse(mQueue.isEmpty());
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertTrue(mQueue.size() == 0);
        assertNull(mQueue.peek());
    }

    @Test
    public void testDequeueEmpty() {
        assertNull(mQueue.dequeue());
    }

}
