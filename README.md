# thread dump poc using quarkus

Run the program using the maven wrapper command. Note that it requires java v17+.

```bash
# change java version (using sdkman)
$ curl -s "https://get.sdkman.io" | bash

# installs latest java version
$ sdk install java

# check the current java version and if maven is using it
$ java --version
$ mvn --version

# recompile the project
$ ./mvnw -X install

# run the application
$ ./mvwn quarkus:dev
```

1. For the first thread dump, it is recommended to hit GET /hello and run the jstack command.

```bash
# Make the http request
$ curl http://localhost:8080/hello
hello()

# Find the PID for your program
$ ps aux | grep java-thread-dump-poc
alizardo         23315   0.0  4.0 415366016 332592 s000  S+    5:01PM   0:06.57 /Users/alizardo/.sdkman/candidates/java/21.0.5-jbr/bin/java -Dquarkus-internal.serialized-app-model.path=/Users/alizardo/Documents/java-thread-dump-poc/target/quarkus/bootstrap/dev-app-model.dat -javaagent:/Users/alizardo/.m2/repository/io/quarkus/quarkus-class-change-agent/3.17.2/quarkus-class-change-agent-3.17.2.jar -XX:TieredStopAtLevel=1 -agentlib:jdwp=transport=dt_socket,address=localhost:5005,server=y,suspend=n -Djava.util.logging.manager=org.jboss.logmanager.LogManager -jar /Users/alizardo/Documents/java-thread-dump-poc/target/java-thread-dump-poc-dev.jar

# run the thread dump with jstack
$ jstack 23315 > /tmp/23315.threaddump
```
2. Thread dump for /hello
```log
2024-12-03 17:10:58
Full thread dump OpenJDK 64-Bit Server VM (21.0.5+8-b631.8 mixed mode, emulated-client, sharing):

Threads class SMR info:
_java_thread_list=0x00006000038e6720, length=23, elements={
0x0000000125813c00, 0x0000000125810400, 0x0000000125816600, 0x0000000125816e00,
0x0000000125817600, 0x0000000125817e00, 0x00000001240bba00, 0x00000001241c5400,
0x0000000125828e00, 0x00000001241c7e00, 0x00000001241d3000, 0x00000001241e0200,
0x0000000120811c00, 0x000000011652b200, 0x0000000125cfda00, 0x0000000124b67200,
0x0000000121066c00, 0x0000000121067400, 0x0000000126014800, 0x00000001167c9000,
0x0000000116567200, 0x0000000120035a00, 0x000000012080d600
}

"Reference Handler" #9 [30467] daemon prio=10 os_prio=31 cpu=4.25ms elapsed=556.63s tid=0x0000000125813c00 nid=30467 waiting on condition  [0x000000016ed66000]
   java.lang.Thread.State: RUNNABLE
	at java.lang.ref.Reference.waitForReferencePendingList(java.base@21.0.5/Native Method)
	at java.lang.ref.Reference.processPendingReferences(java.base@21.0.5/Reference.java:246)
	at java.lang.ref.Reference$ReferenceHandler.run(java.base@21.0.5/Reference.java:208)

"Finalizer" #10 [25347] daemon prio=8 os_prio=31 cpu=4.59ms elapsed=556.63s tid=0x0000000125810400 nid=25347 in Object.wait()  [0x000000016ef72000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait0(java.base@21.0.5/Native Method)
	- waiting on <no object reference available>
	at java.lang.Object.wait(java.base@21.0.5/Object.java:366)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:339)
	at java.lang.ref.NativeReferenceQueue.await(java.base@21.0.5/NativeReferenceQueue.java:48)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:158)
	at java.lang.ref.NativeReferenceQueue.remove(java.base@21.0.5/NativeReferenceQueue.java:89)
	- locked <0x0000000780798920> (a java.lang.ref.NativeReferenceQueue$Lock)
	at java.lang.ref.Finalizer$FinalizerThread.run(java.base@21.0.5/Finalizer.java:173)

"Signal Dispatcher" #11 [25603] daemon prio=9 os_prio=31 cpu=1.59ms elapsed=556.63s tid=0x0000000125816600 nid=25603 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Service Thread" #12 [26115] daemon prio=9 os_prio=31 cpu=15.39ms elapsed=556.63s tid=0x0000000125816e00 nid=26115 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Deflation Thread" #13 [29699] daemon prio=9 os_prio=31 cpu=35.11ms elapsed=556.63s tid=0x0000000125817600 nid=29699 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread0" #14 [26627] daemon prio=9 os_prio=31 cpu=1203.76ms elapsed=556.63s tid=0x0000000125817e00 nid=26627 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"Common-Cleaner" #18 [29443] daemon prio=8 os_prio=31 cpu=5.36ms elapsed=556.63s tid=0x00000001240bba00 nid=29443 waiting on condition  [0x000000016f9ae000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007807fd390> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.parkNanos(java.base@21.0.5/LockSupport.java:269)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(java.base@21.0.5/AbstractQueuedSynchronizer.java:1852)
	at java.lang.ref.ReferenceQueue.await(java.base@21.0.5/ReferenceQueue.java:71)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:143)
	at java.lang.ref.ReferenceQueue.remove(java.base@21.0.5/ReferenceQueue.java:218)
	at jdk.internal.ref.CleanerImpl.run(java.base@21.0.5/CleanerImpl.java:140)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)
	at jdk.internal.misc.InnocuousThread.run(java.base@21.0.5/InnocuousThread.java:186)

"JDWP Transport Listener: dt_socket" #19 [27395] daemon prio=10 os_prio=31 cpu=0.14ms elapsed=556.43s tid=0x00000001241c5400 nid=27395 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Event Helper Thread" #20 [28931] daemon prio=10 os_prio=31 cpu=0.03ms elapsed=556.43s tid=0x0000000125828e00 nid=28931 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Notification Thread" #21 [27651] daemon prio=9 os_prio=31 cpu=0.02ms elapsed=556.43s tid=0x00000001241c7e00 nid=27651 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Reference Reaper #1" #23 [28163] daemon prio=5 os_prio=31 cpu=5.55ms elapsed=556.39s tid=0x00000001241d3000 nid=28163 waiting on condition  [0x00000001701de000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780798a00> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:371)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode.block(java.base@21.0.5/AbstractQueuedSynchronizer.java:519)
	at java.util.concurrent.ForkJoinPool.unmanagedBlock(java.base@21.0.5/ForkJoinPool.java:4013)
	at java.util.concurrent.ForkJoinPool.managedBlock(java.base@21.0.5/ForkJoinPool.java:3961)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(java.base@21.0.5/AbstractQueuedSynchronizer.java:1712)
	at java.lang.ref.ReferenceQueue.await(java.base@21.0.5/ReferenceQueue.java:67)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:158)
	at java.lang.ref.ReferenceQueue.remove(java.base@21.0.5/ReferenceQueue.java:234)
	at io.smallrye.common.ref.References$ReaperThread.run(References.java:58)

"Quarkus Devmode keep alive thread" #28 [34051] prio=5 os_prio=31 cpu=0.07ms elapsed=556.17s tid=0x00000001241e0200 nid=34051 waiting on condition  [0x0000000170c1a000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007811a05f8> (a java.util.concurrent.CountDownLatch$Sync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(java.base@21.0.5/AbstractQueuedSynchronizer.java:1099)
	at java.util.concurrent.CountDownLatch.await(java.base@21.0.5/CountDownLatch.java:230)
	at io.quarkus.deployment.dev.IsolatedDevModeMain$4.run(IsolatedDevModeMain.java:393)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"Aesh InputStream Reader" #95 [65539] daemon prio=5 os_prio=31 cpu=45.16ms elapsed=555.75s tid=0x0000000120811c00 nid=65539 runnable  [0x000000017851e000]
   java.lang.Thread.State: RUNNABLE
	at java.io.FileInputStream.readBytes(java.base@21.0.5/Native Method)
	at java.io.FileInputStream.read(java.base@21.0.5/FileInputStream.java:263)
	at org.aesh.readline.tty.terminal.TerminalConnection.openBlocking(TerminalConnection.java:214)
	at org.aesh.readline.tty.terminal.TerminalConnection.openBlocking(TerminalConnection.java:203)
	at org.aesh.readline.tty.terminal.TerminalConnection$$Lambda/0x000000050131b8f8.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@21.0.5/ThreadPoolExecutor.java:1144)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@21.0.5/ThreadPoolExecutor.java:642)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vertx-blocked-thread-checker" #106 [58631] daemon prio=5 os_prio=31 cpu=37.01ms elapsed=555.06s tid=0x000000011652b200 nid=58631 in Object.wait()  [0x00000001726f6000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait0(java.base@21.0.5/Native Method)
	- waiting on <no object reference available>
	at java.lang.Object.wait(java.base@21.0.5/Object.java:366)
	at java.util.TimerThread.mainLoop(java.base@21.0.5/Timer.java:563)
	- locked <0x000000078425fe58> (a java.util.TaskQueue)
	at java.util.TimerThread.run(java.base@21.0.5/Timer.java:516)

"vert.x-eventloop-thread-0" #109 [59143] prio=5 os_prio=31 cpu=409.11ms elapsed=555.02s tid=0x0000000125cfda00 nid=59143 runnable  [0x0000000172902000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x000000078427b580> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x000000078427b528> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-eventloop-thread-1" #110 [64775] prio=5 os_prio=31 cpu=219.44ms elapsed=555.02s tid=0x0000000124b67200 nid=64775 runnable  [0x0000000172b0e000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x0000000784288f40> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x0000000784288ee8> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:142)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:62)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:883)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-acceptor-thread-0" #111 [62215] prio=5 os_prio=31 cpu=21.27ms elapsed=554.98s tid=0x0000000121066c00 nid=62215 runnable  [0x0000000172d1a000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x00000007842a44f0> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x00000007842a4498> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-eventloop-thread-2" #112 [37403] prio=5 os_prio=31 cpu=0.05ms elapsed=554.97s tid=0x0000000121067400 nid=37403 runnable  [0x0000000172f26000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x0000000784296a18> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x00000007842969c0> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"DestroyJavaVM" #114 [5379] prio=5 os_prio=31 cpu=594.33ms elapsed=554.97s tid=0x0000000126014800 nid=5379 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Quarkus Main Thread" #196 [62479] prio=5 os_prio=31 cpu=25.02ms elapsed=152.66s tid=0x00000001167c9000 nid=62479 waiting on condition  [0x000000017229d000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000781266890> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:371)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode.block(java.base@21.0.5/AbstractQueuedSynchronizer.java:519)
	at java.util.concurrent.ForkJoinPool.unmanagedBlock(java.base@21.0.5/ForkJoinPool.java:4013)
	at java.util.concurrent.ForkJoinPool.managedBlock(java.base@21.0.5/ForkJoinPool.java:3961)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitUninterruptibly(java.base@21.0.5/AbstractQueuedSynchronizer.java:1665)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:163)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
	at java.lang.invoke.LambdaForm$DMH/0x0000000501080c00.invokeStatic(java.base@21.0.5/LambdaForm$DMH)
	at java.lang.invoke.LambdaForm$MH/0x0000000501827800.invoke(java.base@21.0.5/LambdaForm$MH)
	at java.lang.invoke.Invokers$Holder.invokeExact_MT(java.base@21.0.5/Invokers$Holder)
	at jdk.internal.reflect.DirectMethodHandleAccessor.invokeImpl(java.base@21.0.5/DirectMethodHandleAccessor.java:154)
	at jdk.internal.reflect.DirectMethodHandleAccessor.invoke(java.base@21.0.5/DirectMethodHandleAccessor.java:103)
	at java.lang.reflect.Method.invoke(java.base@21.0.5/Method.java:580)
	at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:116)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"executor-thread-1" #200 [64275] daemon prio=5 os_prio=31 cpu=1.22ms elapsed=152.64s tid=0x0000000116567200 nid=64275 waiting on condition  [0x00000001724aa000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000789f1b578> (a org.jboss.threads.EnhancedQueueExecutor)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at org.jboss.threads.EnhancedQueueExecutor$PoolThreadNode.park(EnhancedQueueExecutor.java:2418)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1661)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-worker-thread-5" #202 [30999] prio=5 os_prio=31 cpu=2.61ms elapsed=137.31s tid=0x0000000120035a00 nid=30999 waiting on condition  [0x0000000173132000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000789fcb5f8> (a org.jboss.threads.EnhancedQueueExecutor)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at org.jboss.threads.EnhancedQueueExecutor$PoolThreadNode.park(EnhancedQueueExecutor.java:2418)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1661)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"Attach Listener" #203 [39191] daemon prio=9 os_prio=31 cpu=1.90ms elapsed=0.11s tid=0x000000012080d600 nid=39191 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"G1 Conc#1" os_prio=31 cpu=87.41ms elapsed=555.75s tid=0x00000001250d3570 nid=56323 runnable

"GC Thread#7" os_prio=31 cpu=65.21ms elapsed=556.09s tid=0x0000000123eb5da0 nid=36355 runnable

"GC Thread#6" os_prio=31 cpu=72.03ms elapsed=556.09s tid=0x0000000123eb5440 nid=35843 runnable

"GC Thread#5" os_prio=31 cpu=66.98ms elapsed=556.09s tid=0x0000000123eb4ed0 nid=41731 runnable

"GC Thread#4" os_prio=31 cpu=70.88ms elapsed=556.21s tid=0x0000000123e53590 nid=42755 runnable

"GC Thread#3" os_prio=31 cpu=69.41ms elapsed=556.27s tid=0x0000000123e50380 nid=33539 runnable

"GC Thread#2" os_prio=31 cpu=74.13ms elapsed=556.27s tid=0x0000000123e4f730 nid=43267 runnable

"GC Thread#1" os_prio=31 cpu=73.99ms elapsed=556.27s tid=0x0000000123e4f1c0 nid=32771 runnable

"VM Thread" os_prio=31 cpu=27.54ms elapsed=556.64s tid=0x0000000125006570 nid=19203 runnable

"VM Periodic Task Thread" os_prio=31 cpu=187.75ms elapsed=556.64s tid=0x0000000123e0ebc0 nid=20483 waiting on condition

"G1 Service" os_prio=31 cpu=10.89ms elapsed=556.64s tid=0x0000000123e0d110 nid=21507 runnable

"G1 Refine#0" os_prio=31 cpu=257.46ms elapsed=556.64s tid=0x000000012401ac00 nid=13827 runnable

"G1 Conc#0" os_prio=31 cpu=88.12ms elapsed=556.64s tid=0x0000000123e0abf0 nid=14083 runnable

"G1 Main Marker" os_prio=31 cpu=0.79ms elapsed=556.64s tid=0x0000000123e0a280 nid=14339 runnable

"GC Thread#0" os_prio=31 cpu=76.89ms elapsed=556.64s tid=0x0000000123e09ae0 nid=14851 runnable

JNI global refs: 51, weak refs: 1

JNI global refs memory usage: 1467, weak refs: 833


```

3. Hit GET /thread and a new thread will be created with a sleep
```log
2024-12-03 17:52:25
Full thread dump OpenJDK 64-Bit Server VM (21.0.5+8-b631.8 mixed mode, emulated-client, sharing):

Threads class SMR info:
_java_thread_list=0x0000600002e104e0, length=30, elements={
0x000000010281e400, 0x0000000102824600, 0x000000010280a000, 0x0000000100808800,
0x0000000100811a00, 0x000000010d00b400, 0x000000010d00bc00, 0x000000010d00cc00,
0x000000010d00d400, 0x0000000100810800, 0x0000000102826e00, 0x0000000102a7be00,
0x000000010d643800, 0x0000000100853a00, 0x000000010d644000, 0x0000000102a7c600,
0x0000000100857200, 0x000000011ca92200, 0x000000011ce17200, 0x000000011ce10c00,
0x0000000102e2f400, 0x0000000102e18600, 0x000000011ccc7600, 0x000000011ccc2a00,
0x00000001408e1800, 0x0000000102de3200, 0x000000010d00c400, 0x000000011ce0d800,
0x00000001408f3600, 0x000000010f82da00
}

"Reference Handler" #9 [24067] daemon prio=10 os_prio=31 cpu=1.68ms elapsed=108.38s tid=0x000000010281e400 nid=24067 waiting on condition  [0x0000000170ef6000]
   java.lang.Thread.State: RUNNABLE
	at java.lang.ref.Reference.waitForReferencePendingList(java.base@21.0.5/Native Method)
	at java.lang.ref.Reference.processPendingReferences(java.base@21.0.5/Reference.java:246)
	at java.lang.ref.Reference$ReferenceHandler.run(java.base@21.0.5/Reference.java:208)

"Finalizer" #10 [29443] daemon prio=8 os_prio=31 cpu=0.20ms elapsed=108.38s tid=0x0000000102824600 nid=29443 in Object.wait()  [0x0000000171102000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait0(java.base@21.0.5/Native Method)
	- waiting on <0x00000007807aaaf0> (a java.lang.ref.NativeReferenceQueue$Lock)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:366)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:339)
	at java.lang.ref.NativeReferenceQueue.await(java.base@21.0.5/NativeReferenceQueue.java:48)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:158)
	at java.lang.ref.NativeReferenceQueue.remove(java.base@21.0.5/NativeReferenceQueue.java:89)
	- locked <0x00000007807aaaf0> (a java.lang.ref.NativeReferenceQueue$Lock)
	at java.lang.ref.Finalizer$FinalizerThread.run(java.base@21.0.5/Finalizer.java:173)

"Signal Dispatcher" #11 [24579] daemon prio=9 os_prio=31 cpu=0.48ms elapsed=108.38s tid=0x000000010280a000 nid=24579 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Service Thread" #12 [28675] daemon prio=9 os_prio=31 cpu=6.29ms elapsed=108.38s tid=0x0000000100808800 nid=28675 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Deflation Thread" #13 [25091] daemon prio=9 os_prio=31 cpu=8.02ms elapsed=108.38s tid=0x0000000100811a00 nid=25091 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread0" #14 [28419] daemon prio=9 os_prio=31 cpu=689.84ms elapsed=108.38s tid=0x000000010d00b400 nid=28419 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"Common-Cleaner" #18 [25859] daemon prio=8 os_prio=31 cpu=2.14ms elapsed=108.37s tid=0x000000010d00bc00 nid=25859 waiting on condition  [0x0000000171b3e000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007807aac18> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.parkNanos(java.base@21.0.5/LockSupport.java:269)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(java.base@21.0.5/AbstractQueuedSynchronizer.java:1852)
	at java.lang.ref.ReferenceQueue.await(java.base@21.0.5/ReferenceQueue.java:71)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:143)
	at java.lang.ref.ReferenceQueue.remove(java.base@21.0.5/ReferenceQueue.java:218)
	at jdk.internal.ref.CleanerImpl.run(java.base@21.0.5/CleanerImpl.java:140)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)
	at jdk.internal.misc.InnocuousThread.run(java.base@21.0.5/InnocuousThread.java:186)

"JDWP Transport Listener: dt_socket" #19 [26115] daemon prio=10 os_prio=31 cpu=0.07ms elapsed=108.30s tid=0x000000010d00cc00 nid=26115 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Event Helper Thread" #20 [26627] daemon prio=10 os_prio=31 cpu=0.07ms elapsed=108.30s tid=0x000000010d00d400 nid=26627 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Notification Thread" #21 [26883] daemon prio=9 os_prio=31 cpu=0.04ms elapsed=108.30s tid=0x0000000100810800 nid=26883 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Reference Reaper #1" #23 [27139] daemon prio=5 os_prio=31 cpu=2.61ms elapsed=108.24s tid=0x0000000102826e00 nid=27139 waiting on condition  [0x000000017236e000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007808643a8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:371)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode.block(java.base@21.0.5/AbstractQueuedSynchronizer.java:519)
	at java.util.concurrent.ForkJoinPool.unmanagedBlock(java.base@21.0.5/ForkJoinPool.java:4013)
	at java.util.concurrent.ForkJoinPool.managedBlock(java.base@21.0.5/ForkJoinPool.java:3961)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(java.base@21.0.5/AbstractQueuedSynchronizer.java:1712)
	at java.lang.ref.ReferenceQueue.await(java.base@21.0.5/ReferenceQueue.java:67)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:158)
	at java.lang.ref.ReferenceQueue.remove(java.base@21.0.5/ReferenceQueue.java:234)
	at io.smallrye.common.ref.References$ReaperThread.run(References.java:58)

"Quarkus Devmode keep alive thread" #28 [41731] prio=5 os_prio=31 cpu=0.09ms elapsed=107.98s tid=0x0000000102a7be00 nid=41731 waiting on condition  [0x0000000172daa000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x000000078111f298> (a java.util.concurrent.CountDownLatch$Sync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(java.base@21.0.5/AbstractQueuedSynchronizer.java:1099)
	at java.util.concurrent.CountDownLatch.await(java.base@21.0.5/CountDownLatch.java:230)
	at io.quarkus.deployment.dev.IsolatedDevModeMain$4.run(IsolatedDevModeMain.java:393)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"ForkJoinPool.commonPool-worker-1" #29 [41475] daemon prio=5 os_prio=31 cpu=1.52ms elapsed=107.89s tid=0x000000010d643800 nid=41475 waiting on condition  [0x0000000172fb6000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780ab5bc8> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-2" #30 [33795] daemon prio=5 os_prio=31 cpu=0.47ms elapsed=107.89s tid=0x0000000100853a00 nid=33795 waiting on condition  [0x00000001731c2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780ab5bc8> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-3" #31 [34307] daemon prio=5 os_prio=31 cpu=0.39ms elapsed=107.89s tid=0x000000010d644000 nid=34307 waiting on condition  [0x00000001733ce000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780ab5bc8> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-4" #32 [34563] daemon prio=5 os_prio=31 cpu=0.34ms elapsed=107.89s tid=0x0000000102a7c600 nid=34563 waiting on condition  [0x00000001735da000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780ab5bc8> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-5" #33 [40963] daemon prio=5 os_prio=31 cpu=0.84ms elapsed=107.89s tid=0x0000000100857200 nid=40963 waiting on condition  [0x00000001737e6000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780ab5bc8> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-6" #34 [40451] daemon prio=5 os_prio=31 cpu=0.79ms elapsed=107.89s tid=0x000000011ca92200 nid=40451 waiting on condition  [0x00000001739f2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780ab5bc8> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"Aesh InputStream Reader" #95 [65539] daemon prio=5 os_prio=31 cpu=0.13ms elapsed=107.39s tid=0x000000011ce17200 nid=65539 runnable  [0x000000017a6ae000]
   java.lang.Thread.State: RUNNABLE
	at java.io.FileInputStream.readBytes(java.base@21.0.5/Native Method)
	at java.io.FileInputStream.read(java.base@21.0.5/FileInputStream.java:263)
	at org.aesh.readline.tty.terminal.TerminalConnection.openBlocking(TerminalConnection.java:214)
	at org.aesh.readline.tty.terminal.TerminalConnection.openBlocking(TerminalConnection.java:203)
	at org.aesh.readline.tty.terminal.TerminalConnection$$Lambda/0x0000007001355960.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@21.0.5/ThreadPoolExecutor.java:1144)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@21.0.5/ThreadPoolExecutor.java:642)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"Quarkus Main Thread" #100 [37127] prio=5 os_prio=31 cpu=229.11ms elapsed=106.88s tid=0x000000011ce10c00 nid=37127 waiting on condition  [0x000000017446d000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007813a0c98> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:371)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode.block(java.base@21.0.5/AbstractQueuedSynchronizer.java:519)
	at java.util.concurrent.ForkJoinPool.unmanagedBlock(java.base@21.0.5/ForkJoinPool.java:4013)
	at java.util.concurrent.ForkJoinPool.managedBlock(java.base@21.0.5/ForkJoinPool.java:3961)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitUninterruptibly(java.base@21.0.5/AbstractQueuedSynchronizer.java:1665)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:163)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
	at java.lang.invoke.LambdaForm$DMH/0x0000007001080c00.invokeStatic(java.base@21.0.5/LambdaForm$DMH)
	at java.lang.invoke.LambdaForm$MH/0x0000007001104800.invoke(java.base@21.0.5/LambdaForm$MH)
	at java.lang.invoke.Invokers$Holder.invokeExact_MT(java.base@21.0.5/Invokers$Holder)
	at jdk.internal.reflect.DirectMethodHandleAccessor.invokeImpl(java.base@21.0.5/DirectMethodHandleAccessor.java:154)
	at jdk.internal.reflect.DirectMethodHandleAccessor.invoke(java.base@21.0.5/DirectMethodHandleAccessor.java:103)
	at java.lang.reflect.Method.invoke(java.base@21.0.5/Method.java:580)
	at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:116)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"executor-thread-1" #104 [44043] daemon prio=5 os_prio=31 cpu=11.05ms elapsed=106.71s tid=0x0000000102e2f400 nid=44043 waiting on condition  [0x000000017467a000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007877a63c0> (a org.jboss.threads.EnhancedQueueExecutor)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at org.jboss.threads.EnhancedQueueExecutor$PoolThreadNode.park(EnhancedQueueExecutor.java:2418)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1661)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vertx-blocked-thread-checker" #106 [64775] daemon prio=5 os_prio=31 cpu=11.13ms elapsed=106.68s tid=0x0000000102e18600 nid=64775 in Object.wait()  [0x0000000174886000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait0(java.base@21.0.5/Native Method)
	- waiting on <0x000000078720f3e8> (a java.util.TaskQueue)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:366)
	at java.util.TimerThread.mainLoop(java.base@21.0.5/Timer.java:563)
	- locked <0x000000078720f3e8> (a java.util.TaskQueue)
	at java.util.TimerThread.run(java.base@21.0.5/Timer.java:516)

"vert.x-eventloop-thread-0" #109 [51719] prio=5 os_prio=31 cpu=14.41ms elapsed=106.60s tid=0x000000011ccc7600 nid=51719 runnable  [0x0000000174a92000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x0000000786f6d8a0> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x0000000786f6d650> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-eventloop-thread-1" #110 [54023] prio=5 os_prio=31 cpu=89.61ms elapsed=106.60s tid=0x000000011ccc2a00 nid=54023 runnable  [0x0000000174c9e000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x0000000786f71458> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x0000000786f71208> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-acceptor-thread-0" #111 [59911] prio=5 os_prio=31 cpu=34.21ms elapsed=106.55s tid=0x00000001408e1800 nid=59911 runnable  [0x0000000174eaa000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x0000000786f92db0> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x0000000786f92b60> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-eventloop-thread-2" #112 [64263] prio=5 os_prio=31 cpu=0.08ms elapsed=106.54s tid=0x0000000102de3200 nid=64263 runnable  [0x00000001750b6000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x0000000786f59888> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x0000000786f51390> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"DestroyJavaVM" #114 [4867] prio=5 os_prio=31 cpu=696.95ms elapsed=106.54s tid=0x000000010d00c400 nid=4867 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"vert.x-worker-thread-1" #115 [30471] prio=5 os_prio=31 cpu=7.68ms elapsed=95.64s tid=0x000000011ce0d800 nid=30471 waiting on condition  [0x00000001752c2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000787240c70> (a org.jboss.threads.EnhancedQueueExecutor)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at org.jboss.threads.EnhancedQueueExecutor$PoolThreadNode.park(EnhancedQueueExecutor.java:2418)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1661)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"Thread-55" #116 [58631] daemon prio=5 os_prio=31 cpu=28.14ms elapsed=67.90s tid=0x00000001408f3600 nid=58631 waiting on condition  [0x00000001754ce000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep0(java.base@21.0.5/Native Method)
	at java.lang.Thread.sleep(java.base@21.0.5/Thread.java:509)
	at org.andrelizardo.BlockingThread.run(BlockingThread.java:10)

"Attach Listener" #117 [61963] daemon prio=9 os_prio=31 cpu=1.79ms elapsed=0.11s tid=0x000000010f82da00 nid=61963 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"G1 Conc#1" os_prio=31 cpu=39.82ms elapsed=107.42s tid=0x000000011d0a7830 nid=52995 runnable

"GC Thread#7" os_prio=31 cpu=14.95ms elapsed=107.55s tid=0x000000011d087b60 nid=35331 runnable

"GC Thread#6" os_prio=31 cpu=17.20ms elapsed=107.86s tid=0x00000001004310e0 nid=39683 runnable

"GC Thread#5" os_prio=31 cpu=14.78ms elapsed=107.86s tid=0x000000011d047f70 nid=35075 runnable

"GC Thread#4" os_prio=31 cpu=20.93ms elapsed=108.03s tid=0x000000011d13f1c0 nid=33027 runnable

"GC Thread#3" os_prio=31 cpu=23.84ms elapsed=108.10s tid=0x000000011c618550 nid=42499 runnable

"GC Thread#2" os_prio=31 cpu=23.21ms elapsed=108.10s tid=0x000000011d13cb70 nid=43011 runnable

"GC Thread#1" os_prio=31 cpu=22.14ms elapsed=108.10s tid=0x000000011d13c600 nid=32771 runnable

"VM Thread" os_prio=31 cpu=13.65ms elapsed=108.39s tid=0x00000001004059a0 nid=18691 runnable

"VM Periodic Task Thread" os_prio=31 cpu=39.98ms elapsed=108.39s tid=0x000000011d10d510 nid=17411 waiting on condition

"G1 Service" os_prio=31 cpu=2.44ms elapsed=108.39s tid=0x000000011d10ba60 nid=21507 runnable

"G1 Refine#0" os_prio=31 cpu=57.75ms elapsed=108.39s tid=0x000000011c80e800 nid=14083 runnable

"G1 Conc#0" os_prio=31 cpu=40.23ms elapsed=108.39s tid=0x000000011d109540 nid=13571 runnable

"G1 Main Marker" os_prio=31 cpu=0.51ms elapsed=108.39s tid=0x000000011d108bd0 nid=14595 runnable

"GC Thread#0" os_prio=31 cpu=27.23ms elapsed=108.39s tid=0x000000011d108430 nid=14851 runnable

JNI global refs: 58, weak refs: 1

JNI global refs memory usage: 1467, weak refs: 833


```

In this case, THREAD-55 should appear as sleeping/TIMED_WAITING. waiting on condition.
```log
"Thread-55" #116 [58631] daemon prio=5 os_prio=31 cpu=28.14ms elapsed=67.90s tid=0x00000001408f3600 nid=58631 waiting on condition  [0x00000001754ce000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep0(java.base@21.0.5/Native Method)
	at java.lang.Thread.sleep(java.base@21.0.5/Thread.java:509)
	at org.andrelizardo.BlockingThread.run(BlockingThread.java:10)
```

4. For deadlocks, hit GET /deadlock. The thread dump will detail that there is a deadlock. Also, in thread-55 and 56 details state is waiting/parking.

```
java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007899c9d10> (a java.util.concurrent.locks.ReentrantLock$FairSync)
```

```log
2024-12-03 18:27:37
Full thread dump OpenJDK 64-Bit Server VM (21.0.5+8-b631.8 mixed mode, emulated-client, sharing):

Threads class SMR info:
_java_thread_list=0x000060000288d640, length=33, elements={
0x00000001470a0200, 0x0000000137028800, 0x000000013780a400, 0x000000013702a400,
0x000000013702c000, 0x000000013702c800, 0x0000000137008800, 0x00000001470a1200,
0x00000001370fbc00, 0x00000001370fc400, 0x00000001370ffa00, 0x00000001379d1400,
0x0000000137213600, 0x0000000137213e00, 0x0000000137214600, 0x0000000140008600,
0x0000000117008200, 0x00000001379d1c00, 0x0000000140008e00, 0x0000000137458600,
0x00000001477d5a00, 0x000000013747c600, 0x0000000137100800, 0x0000000137103a00,
0x0000000117ce2e00, 0x0000000117cfa800, 0x0000000117cb0e00, 0x00000001477cc200,
0x0000000140239600, 0x00000001475c0000, 0x00000001470a0a00, 0x0000000117cc5c00,
0x000000012180f600
}

"Reference Handler" #9 [30467] daemon prio=10 os_prio=31 cpu=1.29ms elapsed=27.44s tid=0x00000001470a0200 nid=30467 waiting on condition  [0x000000016ee0e000]
   java.lang.Thread.State: RUNNABLE
	at java.lang.ref.Reference.waitForReferencePendingList(java.base@21.0.5/Native Method)
	at java.lang.ref.Reference.processPendingReferences(java.base@21.0.5/Reference.java:246)
	at java.lang.ref.Reference$ReferenceHandler.run(java.base@21.0.5/Reference.java:208)

"Finalizer" #10 [25347] daemon prio=8 os_prio=31 cpu=0.18ms elapsed=27.44s tid=0x0000000137028800 nid=25347 in Object.wait()  [0x000000016f01a000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait0(java.base@21.0.5/Native Method)
	- waiting on <0x0000000780800078> (a java.lang.ref.NativeReferenceQueue$Lock)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:366)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:339)
	at java.lang.ref.NativeReferenceQueue.await(java.base@21.0.5/NativeReferenceQueue.java:48)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:158)
	at java.lang.ref.NativeReferenceQueue.remove(java.base@21.0.5/NativeReferenceQueue.java:89)
	- locked <0x0000000780800078> (a java.lang.ref.NativeReferenceQueue$Lock)
	at java.lang.ref.Finalizer$FinalizerThread.run(java.base@21.0.5/Finalizer.java:173)

"Signal Dispatcher" #11 [25859] daemon prio=9 os_prio=31 cpu=0.29ms elapsed=27.44s tid=0x000000013780a400 nid=25859 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Service Thread" #12 [30211] daemon prio=9 os_prio=31 cpu=4.71ms elapsed=27.44s tid=0x000000013702a400 nid=30211 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Deflation Thread" #13 [26627] daemon prio=9 os_prio=31 cpu=1.62ms elapsed=27.44s tid=0x000000013702c000 nid=26627 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread0" #14 [26883] daemon prio=9 os_prio=31 cpu=582.86ms elapsed=27.44s tid=0x000000013702c800 nid=26883 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"Common-Cleaner" #18 [29699] daemon prio=8 os_prio=31 cpu=1.70ms elapsed=27.43s tid=0x0000000137008800 nid=29699 waiting on condition  [0x000000016fa56000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007807b0110> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.parkNanos(java.base@21.0.5/LockSupport.java:269)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(java.base@21.0.5/AbstractQueuedSynchronizer.java:1852)
	at java.lang.ref.ReferenceQueue.await(java.base@21.0.5/ReferenceQueue.java:71)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:143)
	at java.lang.ref.ReferenceQueue.remove(java.base@21.0.5/ReferenceQueue.java:218)
	at jdk.internal.ref.CleanerImpl.run(java.base@21.0.5/CleanerImpl.java:140)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)
	at jdk.internal.misc.InnocuousThread.run(java.base@21.0.5/InnocuousThread.java:186)

"JDWP Transport Listener: dt_socket" #19 [29187] daemon prio=10 os_prio=31 cpu=0.03ms elapsed=27.40s tid=0x00000001470a1200 nid=29187 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"JDWP Event Helper Thread" #20 [27395] daemon prio=10 os_prio=31 cpu=0.03ms elapsed=27.40s tid=0x00000001370fbc00 nid=27395 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Notification Thread" #21 [28675] daemon prio=9 os_prio=31 cpu=0.02ms elapsed=27.40s tid=0x00000001370fc400 nid=28675 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Reference Reaper #1" #23 [28419] daemon prio=5 os_prio=31 cpu=2.41ms elapsed=27.37s tid=0x00000001370ffa00 nid=28419 waiting on condition  [0x0000000170286000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780800158> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:371)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode.block(java.base@21.0.5/AbstractQueuedSynchronizer.java:519)
	at java.util.concurrent.ForkJoinPool.unmanagedBlock(java.base@21.0.5/ForkJoinPool.java:4013)
	at java.util.concurrent.ForkJoinPool.managedBlock(java.base@21.0.5/ForkJoinPool.java:3961)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(java.base@21.0.5/AbstractQueuedSynchronizer.java:1712)
	at java.lang.ref.ReferenceQueue.await(java.base@21.0.5/ReferenceQueue.java:67)
	at java.lang.ref.ReferenceQueue.remove0(java.base@21.0.5/ReferenceQueue.java:158)
	at java.lang.ref.ReferenceQueue.remove(java.base@21.0.5/ReferenceQueue.java:234)
	at io.smallrye.common.ref.References$ReaperThread.run(References.java:58)

"Quarkus Devmode keep alive thread" #28 [34051] prio=5 os_prio=31 cpu=0.09ms elapsed=27.15s tid=0x00000001379d1400 nid=34051 waiting on condition  [0x0000000170cc2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x000000078117a168> (a java.util.concurrent.CountDownLatch$Sync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(java.base@21.0.5/AbstractQueuedSynchronizer.java:1099)
	at java.util.concurrent.CountDownLatch.await(java.base@21.0.5/CountDownLatch.java:230)
	at io.quarkus.deployment.dev.IsolatedDevModeMain$4.run(IsolatedDevModeMain.java:393)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"ForkJoinPool.commonPool-worker-1" #29 [34307] daemon prio=5 os_prio=31 cpu=0.49ms elapsed=27.10s tid=0x0000000137213600 nid=34307 waiting on condition  [0x0000000170ece000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-2" #30 [34563] daemon prio=5 os_prio=31 cpu=0.26ms elapsed=27.10s tid=0x0000000137213e00 nid=34563 waiting on condition  [0x00000001710da000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-3" #31 [34819] daemon prio=5 os_prio=31 cpu=0.30ms elapsed=27.10s tid=0x0000000137214600 nid=34819 waiting on condition  [0x00000001712e6000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-4" #32 [41475] daemon prio=5 os_prio=31 cpu=0.24ms elapsed=27.10s tid=0x0000000140008600 nid=41475 waiting on condition  [0x00000001714f2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-5" #33 [35075] daemon prio=5 os_prio=31 cpu=0.24ms elapsed=27.10s tid=0x0000000117008200 nid=35075 waiting on condition  [0x00000001716fe000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-6" #34 [40707] daemon prio=5 os_prio=31 cpu=0.24ms elapsed=27.10s tid=0x00000001379d1c00 nid=40707 waiting on condition  [0x000000017190a000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"ForkJoinPool.commonPool-worker-7" #35 [40195] daemon prio=5 os_prio=31 cpu=0.09ms elapsed=27.10s tid=0x0000000140008e00 nid=40195 waiting on condition  [0x0000000171b16000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780b2f438> (a java.util.concurrent.ForkJoinPool)
	at java.util.concurrent.ForkJoinPool.awaitWork(java.base@21.0.5/ForkJoinPool.java:2145)
	at java.util.concurrent.ForkJoinPool.runWorker(java.base@21.0.5/ForkJoinPool.java:2036)
	at java.util.concurrent.ForkJoinWorkerThread.run(java.base@21.0.5/ForkJoinWorkerThread.java:187)

"process reaper" #39 [36611] daemon prio=10 os_prio=31 cpu=1.19ms elapsed=26.84s tid=0x0000000137458600 nid=36611 waiting on condition  [0x000000017217a000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000780019220> (a java.util.concurrent.SynchronousQueue$Transferer)
	at java.util.concurrent.locks.LockSupport.parkNanos(java.base@21.0.5/LockSupport.java:410)
	at java.util.concurrent.LinkedTransferQueue$DualNode.await(java.base@21.0.5/LinkedTransferQueue.java:452)
	at java.util.concurrent.SynchronousQueue$Transferer.xferLifo(java.base@21.0.5/SynchronousQueue.java:194)
	at java.util.concurrent.SynchronousQueue.xfer(java.base@21.0.5/SynchronousQueue.java:235)
	at java.util.concurrent.SynchronousQueue.poll(java.base@21.0.5/SynchronousQueue.java:338)
	at java.util.concurrent.ThreadPoolExecutor.getTask(java.base@21.0.5/ThreadPoolExecutor.java:1069)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@21.0.5/ThreadPoolExecutor.java:1130)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@21.0.5/ThreadPoolExecutor.java:642)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)
	at jdk.internal.misc.InnocuousThread.run(java.base@21.0.5/InnocuousThread.java:186)

"Aesh InputStream Reader" #98 [66051] daemon prio=5 os_prio=31 cpu=0.11ms elapsed=26.74s tid=0x00000001477d5a00 nid=66051 runnable  [0x0000000178bea000]
   java.lang.Thread.State: RUNNABLE
	at java.io.FileInputStream.readBytes(java.base@21.0.5/Native Method)
	at java.io.FileInputStream.read(java.base@21.0.5/FileInputStream.java:263)
	at org.aesh.readline.tty.terminal.TerminalConnection.openBlocking(TerminalConnection.java:214)
	at org.aesh.readline.tty.terminal.TerminalConnection.openBlocking(TerminalConnection.java:203)
	at org.aesh.readline.tty.terminal.TerminalConnection$$Lambda/0x000000f0013472b0.run(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(java.base@21.0.5/ThreadPoolExecutor.java:1144)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(java.base@21.0.5/ThreadPoolExecutor.java:642)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"Quarkus Main Thread" #100 [86791] prio=5 os_prio=31 cpu=173.43ms elapsed=26.31s tid=0x000000013747c600 nid=86791 waiting on condition  [0x0000000172385000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x000000078118c720> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:371)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionNode.block(java.base@21.0.5/AbstractQueuedSynchronizer.java:519)
	at java.util.concurrent.ForkJoinPool.unmanagedBlock(java.base@21.0.5/ForkJoinPool.java:4013)
	at java.util.concurrent.ForkJoinPool.managedBlock(java.base@21.0.5/ForkJoinPool.java:3961)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.awaitUninterruptibly(java.base@21.0.5/AbstractQueuedSynchronizer.java:1665)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:163)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
	at java.lang.invoke.LambdaForm$DMH/0x000000f001080c00.invokeStatic(java.base@21.0.5/LambdaForm$DMH)
	at java.lang.invoke.LambdaForm$MH/0x000000f001104800.invoke(java.base@21.0.5/LambdaForm$MH)
	at java.lang.invoke.Invokers$Holder.invokeExact_MT(java.base@21.0.5/Invokers$Holder)
	at jdk.internal.reflect.DirectMethodHandleAccessor.invokeImpl(java.base@21.0.5/DirectMethodHandleAccessor.java:154)
	at jdk.internal.reflect.DirectMethodHandleAccessor.invoke(java.base@21.0.5/DirectMethodHandleAccessor.java:103)
	at java.lang.reflect.Method.invoke(java.base@21.0.5/Method.java:580)
	at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:116)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"executor-thread-1" #103 [65799] daemon prio=5 os_prio=31 cpu=8.84ms elapsed=26.19s tid=0x0000000137100800 nid=65799 waiting on condition  [0x0000000172592000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x0000000784569d28> (a org.jboss.threads.EnhancedQueueExecutor)
	at java.util.concurrent.locks.LockSupport.parkNanos(java.base@21.0.5/LockSupport.java:269)
	at org.jboss.threads.EnhancedQueueExecutor$PoolThreadNode.park(EnhancedQueueExecutor.java:2458)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1673)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vertx-blocked-thread-checker" #106 [65547] daemon prio=5 os_prio=31 cpu=4.54ms elapsed=26.17s tid=0x0000000137103a00 nid=65547 in Object.wait()  [0x000000017279e000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait0(java.base@21.0.5/Native Method)
	- waiting on <0x000000078454f5f8> (a java.util.TaskQueue)
	at java.lang.Object.wait(java.base@21.0.5/Object.java:366)
	at java.util.TimerThread.mainLoop(java.base@21.0.5/Timer.java:563)
	- locked <0x000000078454f5f8> (a java.util.TaskQueue)
	at java.util.TimerThread.run(java.base@21.0.5/Timer.java:516)

"vert.x-eventloop-thread-0" #109 [87047] prio=5 os_prio=31 cpu=92.24ms elapsed=26.12s tid=0x0000000117ce2e00 nid=87047 runnable  [0x00000001729aa000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x00000007841ca628> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x00000007841ca4f8> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-eventloop-thread-1" #110 [49927] prio=5 os_prio=31 cpu=2.00ms elapsed=26.12s tid=0x0000000117cfa800 nid=49927 runnable  [0x0000000172bb6000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x000000078451a940> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x000000078451a8e8> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-acceptor-thread-0" #111 [58375] prio=5 os_prio=31 cpu=26.82ms elapsed=26.08s tid=0x0000000117cb0e00 nid=58375 runnable  [0x0000000172dc2000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x00000007845844a8> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x0000000784584450> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"vert.x-eventloop-thread-2" #112 [56071] prio=5 os_prio=31 cpu=0.06ms elapsed=26.07s tid=0x00000001477cc200 nid=56071 runnable  [0x0000000172fce000]
   java.lang.Thread.State: RUNNABLE
	at sun.nio.ch.KQueue.poll(java.base@21.0.5/Native Method)
	at sun.nio.ch.KQueueSelectorImpl.doSelect(java.base@21.0.5/KQueueSelectorImpl.java:125)
	at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.5/SelectorImpl.java:130)
	- locked <0x000000078459ebd0> (a io.netty.channel.nio.SelectedSelectionKeySet)
	- locked <0x000000078459eb78> (a sun.nio.ch.KQueueSelectorImpl)
	at sun.nio.ch.SelectorImpl.select(java.base@21.0.5/SelectorImpl.java:147)
	at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:68)
	at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:879)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"DestroyJavaVM" #114 [4867] prio=5 os_prio=31 cpu=575.32ms elapsed=26.07s tid=0x0000000140239600 nid=4867 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"vert.x-worker-thread-1" #115 [31239] prio=5 os_prio=31 cpu=2.60ms elapsed=18.30s tid=0x00000001475c0000 nid=31239 waiting on condition  [0x00000001731da000]
   java.lang.Thread.State: TIMED_WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x000000078453be28> (a org.jboss.threads.EnhancedQueueExecutor)
	at java.util.concurrent.locks.LockSupport.parkNanos(java.base@21.0.5/LockSupport.java:269)
	at org.jboss.threads.EnhancedQueueExecutor$PoolThreadNode.park(EnhancedQueueExecutor.java:2458)
	at org.jboss.threads.EnhancedQueueExecutor.runThreadBody(EnhancedQueueExecutor.java:1673)
	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1594)
	at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:11)
	at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:11)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.runWith(java.base@21.0.5/Thread.java:1596)
	at java.lang.Thread.run(java.base@21.0.5/Thread.java:1583)

"Thread-55" #116 [51463] daemon prio=5 os_prio=31 cpu=0.48ms elapsed=18.30s tid=0x00000001470a0a00 nid=51463 waiting on condition  [0x00000001733e6000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007899c9d10> (a java.util.concurrent.locks.ReentrantLock$FairSync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:990)
	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@21.0.5/ReentrantLock.java:153)
	at java.util.concurrent.locks.ReentrantLock.lock(java.base@21.0.5/ReentrantLock.java:322)
	at org.andrelizardo.DeadlockThread.run(DeadlockThread.java:19)

"Thread-56" #117 [65287] daemon prio=5 os_prio=31 cpu=0.35ms elapsed=18.30s tid=0x0000000117cc5c00 nid=65287 waiting on condition  [0x00000001735f2000]
   java.lang.Thread.State: WAITING (parking)
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007899c9ce0> (a java.util.concurrent.locks.ReentrantLock$FairSync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:990)
	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@21.0.5/ReentrantLock.java:153)
	at java.util.concurrent.locks.ReentrantLock.lock(java.base@21.0.5/ReentrantLock.java:322)
	at org.andrelizardo.DeadlockThread.run(DeadlockThread.java:19)

"Attach Listener" #118 [49671] daemon prio=9 os_prio=31 cpu=0.92ms elapsed=0.11s tid=0x000000012180f600 nid=49671 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"G1 Conc#1" os_prio=31 cpu=32.24ms elapsed=26.78s tid=0x0000000116f06330 nid=86531 runnable

"GC Thread#7" os_prio=31 cpu=22.51ms elapsed=26.87s tid=0x0000000116f06e80 nid=36099 runnable

"GC Thread#6" os_prio=31 cpu=18.45ms elapsed=27.08s tid=0x0000000136e71570 nid=39683 runnable

"GC Thread#5" os_prio=31 cpu=20.05ms elapsed=27.08s tid=0x0000000136e71000 nid=35331 runnable

"GC Thread#4" os_prio=31 cpu=19.96ms elapsed=27.19s tid=0x0000000136e430b0 nid=42755 runnable

"GC Thread#3" os_prio=31 cpu=28.76ms elapsed=27.26s tid=0x0000000136f09de0 nid=43267 runnable

"GC Thread#2" os_prio=31 cpu=25.49ms elapsed=27.26s tid=0x0000000136e3ee50 nid=33283 runnable

"GC Thread#1" os_prio=31 cpu=30.42ms elapsed=27.26s tid=0x0000000136e3e8e0 nid=32771 runnable

"VM Thread" os_prio=31 cpu=10.63ms elapsed=27.45s tid=0x0000000136e07af0 nid=19715 runnable

"VM Periodic Task Thread" os_prio=31 cpu=8.64ms elapsed=27.45s tid=0x0000000146e0da50 nid=16899 waiting on condition

"G1 Service" os_prio=31 cpu=0.98ms elapsed=27.45s tid=0x0000000146e0bfa0 nid=21507 runnable

"G1 Refine#0" os_prio=31 cpu=12.98ms elapsed=27.45s tid=0x0000000147091c00 nid=12803 runnable

"G1 Conc#0" os_prio=31 cpu=30.48ms elapsed=27.45s tid=0x0000000146e09a80 nid=13059 runnable

"G1 Main Marker" os_prio=31 cpu=0.31ms elapsed=27.45s tid=0x0000000146e09110 nid=13827 runnable

"GC Thread#0" os_prio=31 cpu=30.66ms elapsed=27.45s tid=0x0000000146e08970 nid=14339 runnable

JNI global refs: 61, weak refs: 1

JNI global refs memory usage: 1467, weak refs: 833


Found one Java-level deadlock:
=============================
"Thread-55":
  waiting for ownable synchronizer 0x00000007899c9d10, (a java.util.concurrent.locks.ReentrantLock$FairSync),
  which is held by "Thread-56"

"Thread-56":
  waiting for ownable synchronizer 0x00000007899c9ce0, (a java.util.concurrent.locks.ReentrantLock$FairSync),
  which is held by "Thread-55"

Java stack information for the threads listed above:
===================================================
"Thread-55":
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007899c9d10> (a java.util.concurrent.locks.ReentrantLock$FairSync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:990)
	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@21.0.5/ReentrantLock.java:153)
	at java.util.concurrent.locks.ReentrantLock.lock(java.base@21.0.5/ReentrantLock.java:322)
	at org.andrelizardo.DeadlockThread.run(DeadlockThread.java:19)
"Thread-56":
	at jdk.internal.misc.Unsafe.park(java.base@21.0.5/Native Method)
	- parking to wait for  <0x00000007899c9ce0> (a java.util.concurrent.locks.ReentrantLock$FairSync)
	at java.util.concurrent.locks.LockSupport.park(java.base@21.0.5/LockSupport.java:221)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:754)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(java.base@21.0.5/AbstractQueuedSynchronizer.java:990)
	at java.util.concurrent.locks.ReentrantLock$Sync.lock(java.base@21.0.5/ReentrantLock.java:153)
	at java.util.concurrent.locks.ReentrantLock.lock(java.base@21.0.5/ReentrantLock.java:322)
	at org.andrelizardo.DeadlockThread.run(DeadlockThread.java:19)

Found 1 deadlock.
```