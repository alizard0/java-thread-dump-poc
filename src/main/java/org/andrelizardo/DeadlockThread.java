package org.andrelizardo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockThread extends Thread {

    private final Lock lock1;
    private final Lock lock2;

    DeadlockThread(Lock lock1, Lock lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }
    public void run() {
        while (true) {
            lock1.lock();
            System.out.println(this.getName() + ": locking lock1 and trying to locking lock2");
            lock2.lock();
            System.out.println(this.getName() + ": locking lock2");
        }
    }
}