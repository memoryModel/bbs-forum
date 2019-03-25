package com.fc.test.thread.future;

import java.util.concurrent.*;

/**
 * @ClassName: FutureDemo
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/18 上午8:25
 * @Version V1.0
 **/
public class FutureDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        InteriorClass interior = new InteriorClass();

        FutureTask<Integer> futureTask = new FutureTask<Integer>(interior);
        executor.submit(futureTask);

        Future<Integer> future = executor.submit(interior);

        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("future.get():" + future.get());
            System.out.println("futureTask.get():" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class InteriorClass implements Callable<Integer> {

    int sum = 0;

    @Override
    public Integer call() throws Exception {
        for (int i = 0; i < 100; i++) {
            sum++;
        }
        return sum;
    }
}
