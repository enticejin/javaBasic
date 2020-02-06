package javaThread;

/**
 * 小结
 * 到目前位置，介绍了线程离开运行状态的3种方法：
 * 1、调用Thread.sleep()：使当前线程睡眠至少多少毫秒（尽管它可能在指定的时间之前被中断）。
 * 2、调用Thread.yield()：不能保障太多事情，尽管通常它会让当前运行线程回到可运行性状态，使得有相同优先级的线程有机会执行。
 * 3、调用join()方法：保证当前线程停止执行，直到该线程所加入的线程完成为止。然而，如果它加入的线程没有存活，则当前线程不需要停止。
 *
 * 除了以上三种方式外，还有下面几种特殊情况可能使线程离开运行状态：
 * 1、线程的run()方法完成。
 * 2、在对象上调用wait()方法（不是在线程上调用）。
 * 3、线程不能在对象上获得锁定，它正试图运行该对象的方法代码。
 * 4、线程调度程序可以决定将当前运行状态移动到可运行状态，以便让另一个线程获得运行机会，而不需要任何理由。
 */
public class MyThread extends Thread {

    public void run() {
        for (int i = 0; i < 100; i++) {
            if ((i) % 10 == 0) {
                System.out.println("-------" + i);
            }
            System.out.print(i);
            try {
                Thread.sleep(1);
                System.out.print("    线程睡眠1毫秒！\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
    }
}
