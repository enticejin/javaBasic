package javaThread;

/**
 * 线程的同步是为了防止多个线程访问一个数据对象时，对数据造成的破坏。
 * 例如：两个线程ThreadA、ThreadB都操作同一个对象Foo对象，并修改Foo对象上的数据。
 */
public class Foo {
    private int num = 100;

    public int getNum() {
        return num;
    }

    public int fix(int num2){
        num = num - num2;
        return num;
    }
}
