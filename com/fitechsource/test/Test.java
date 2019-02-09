package com.fitechsource.test;

import java.util.*;

/**
 * Should be improved to reduce calculation time.
 *
 * 1. Change this file or create new one using parallel calculation mode.
 * 2. Do not use `executors`, only plain threads  (max threads count which should be created for calculations is com.fitechsource.test.TestConsts#MAX_THREADS)
 * 3. Try to provide simple solution, do not implement frameworks.
 * 4. Don't forget that calculation method can throw exception, process it right way.
 *   (Stop calculation process and print error message. Ignore already calculated intermediate results, user doesn't need it.)
 *
 *   Please attach code files to email - skhisamov@fitechsource.com
 */

public class Test {
    private static volatile Set<Double> res = new HashSet<>();
    private static volatile Counter counter = new Counter();
    static List<Thread> threads = new ArrayList<Thread>();
    /**
     * testExceptionFlag
     */
    private static volatile boolean ignoreResult = false;

    public static void main(String[] args) throws TestException {
//        long start = System.nanoTime();

        for (int i = 0; i < TestConsts.MAX_THREADS; i++) {
            threads.add(new Thread(() -> {
                try {
                    for (int j = counter.increment(); j < TestConsts.N; j = counter.increment()){
                        res.addAll(TestCalc.calculate(j));
                    }
                } catch (TestException e) {
                    if(!ignoreResult){
                        counter.set(TestConsts.N);
                        ignoreResult = true;

                        for (Thread thread : threads) {
                            if(!thread.isInterrupted()) {
                                thread.interrupt();
                            }
                        }
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                }
            }));
        }

        for (Thread thread: threads) {
            thread.start();
        }
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        long finish = System.nanoTime();
//        long timeConsumedMillis = finish - start;
//        System.out.println(timeConsumedMillis);

        if(!ignoreResult)
            System.out.println(res);
    }


    public static class Counter {
        private int counter = -1;

        public synchronized int  get()      { return counter; }
        public synchronized int set(int n) { return counter = n; }
        public synchronized int increment() {
            return set(get() + 1);
        }
    }
}
