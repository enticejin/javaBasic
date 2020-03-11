package com.example.spring.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MultiThread {
    public static void main(String[] args) {
        //获取java线程管理
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //仅获取线程的堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //遍历线程信息，仅打印id和name
        for(ThreadInfo th : threadInfos){
            System.out.println("线程id："+th.getThreadId()+"---线程名称："+th.getThreadName());
        }

        /*
        * 运行结果：
        *   线程id：6---线程名称：Monitor Ctrl-Break //监控事件
            线程id：5---线程名称：Attach Listener //添加事件
            线程id：4---线程名称：Signal Dispatcher // 分发处理给 JVM 信号的线程
            线程id：3---线程名称：Finalizer //调用对象 finalize 方法的线程
            线程id：2---线程名称：Reference Handler //清除 reference 线程
            线程id：1---线程名称：main  //main 线程,程序入口
        * */
    }
}
