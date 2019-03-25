package com.fc.test.classtest.extebds;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName: ParentClass
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午10:54
 * @Version V1.0
 **/
@Slf4j
public class ParentClassTest {

    public boolean ifDelete() {
        return true;
    }

    public void buildClass() {
        log.info("sssssss");
    }

    public void ExceptionTest () throws NullPointerException {}
}
