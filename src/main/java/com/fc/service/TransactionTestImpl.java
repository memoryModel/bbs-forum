package com.fc.service;

import com.fc.TransactionTest;
import com.fc.mapper.UserMapper;
import com.fc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: TransactionTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/25 下午3:39
 **/
@Service
public class TransactionTestImpl implements TransactionTest {

    @Autowired
    UserMapper userMapper;


    @Override
    @Transactional
    public void saveTest() {
        updateTest();
    }

    @Override
    public void updateTest() {
        User user = userMapper.getUserByUid(604779243695509504L);
        user.setLikeCount(user.getLikeCount() + 1);
        userMapper.updateLikeCount(user);

        try {
            int i = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        System.out.println("哈哈");

        userMapper.updateScanCount(604779243695509504L);
    }
}
