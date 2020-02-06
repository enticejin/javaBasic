package thread;

public class TestRunnable {
    public static void main(String[] args) {
        DoSomething doSmothing1 = new DoSomething("张三");
        DoSomething doSmothing2 = new DoSomething("李四");

        Thread thread1 = new Thread(doSmothing1);
        Thread thread2 = new Thread(doSmothing2);

        thread1.start();
        thread2.start();
    }
}
