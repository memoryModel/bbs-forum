package com.fc.test.design.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AbstractParent
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午9:43
 * @Version V1.0
 **/
@Slf4j
public abstract class AbstractParent {

    /**
     * 初始化学生
     * @return
     */
    public abstract Student initStudent();

    public Student buildStudent() {
        log.info("student:{}",initStudent());
        return initStudent();
    }
}
