package javaThread;

public class MyRunnable implements Runnable {
    private Foo foo = new Foo();

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 =  new Thread(myRunnable,"Thread-A");
        Thread thread2 =  new Thread(myRunnable,"Thread-B");

        thread1.start();
        thread2.start();
    }
    @Override
    public void run() {
        for (int i =0; i < 3; i++){
          this.fix(30);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : 当前foo对象的x值= " + foo.getNum());
        }
    }

    public int fix(int num){
        return foo.fix(num);
    }
}
