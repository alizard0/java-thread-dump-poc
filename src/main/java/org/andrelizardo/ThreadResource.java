package org.andrelizardo;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Path("/")
public class ThreadResource {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello()";
    }

    @GET
    @Path("thread")
    @Produces(MediaType.TEXT_PLAIN)
    public String blockSingleThread() {
        Thread t1 = new BlockingThread();
        t1.start();
        return "blockSingleThread()";
    }

    @GET
    @Path("deadlock")
    @Produces(MediaType.TEXT_PLAIN)
    public String createDeadlock() {
        Thread t1 = new DeadlockThread(lock1, lock2);
        Thread t2 = new DeadlockThread(lock2, lock1);
        t1.start();
        t2.start();
        return "deadlockThread()";
    }
}
