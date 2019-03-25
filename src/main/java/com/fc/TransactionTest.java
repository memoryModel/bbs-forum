package com.fc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: TransactionTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/25 下午3:39
 **/
public interface TransactionTest {

    void saveTest();

    void updateTest();
}
