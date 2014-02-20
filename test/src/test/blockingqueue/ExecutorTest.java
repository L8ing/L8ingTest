package test.blockingqueue;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jackyuj
 */
public class ExecutorTest {

    public static void main(String[] args) throws InterruptedException {

        TestThread producer1 = new TestThread();
        TestThread2 producer2 = new TestThread2();

        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);

        // 执行10s
        Thread.sleep(10 * 1000);
        producer1.stop();
        producer2.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }

    static class TestThread implements Runnable {

        public TestThread() {
        }

        public void run() {
            String data = null;
            Random r = new Random();

            System.out.println("启动生产者线程！");
            try {
                while (isRunning) {
                    System.out.println("正在生产数据...");
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

                    System.out.println("将数据：" + data + "放入队列...");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出生产者线程！");
            }
        }

        public void stop() {
            isRunning = false;
        }

        private volatile boolean isRunning               = true;
        private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

    }

    static class TestThread2 implements Runnable {

        public TestThread2() {
        }

        public void run() {

            System.out.println("启动生产者线程2！");
            try {
                while (isRunning) {
                    System.out.println("正在生产数据2...");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出生产者线程2！");
            }
        }

        public void stop() {
            isRunning = false;
        }

        private volatile boolean isRunning = true;

    }
}