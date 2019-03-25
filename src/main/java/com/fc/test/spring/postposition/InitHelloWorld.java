package com.fc.test.spring.postposition;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @ClassName: InitHelloWorld
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/23 下午6:27
 **/
public class InitHelloWorld implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("After Initialization: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Before Initialization: " + beanName);
        return bean;
    }
}
