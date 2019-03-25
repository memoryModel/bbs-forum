package com.fc.test.other;

import com.fc.test.design.decorator.Decorator;
import com.fc.test.design.decorator.HouseBefore;
import com.fc.test.design.decorator.MacHouse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: SupplierTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/11 下午2:06
 **/
@Slf4j
public class SupplierTest {

    public static void main(String[] args) {
        String result = CompletableFuture.supplyAsync(() -> CompletableFutureTest.call()).thenApplyAsync(s -> s + " world").join();
        System.out.println(result);

    }


}

class CompletableFutureTest {
    public static Decorator call() {
        return new Decorator(new MacHouse());
    }
}
