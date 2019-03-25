package com.fc.test.spring.importtest;

import com.fc.test.design.template.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @ClassName: ImportTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/23 下午10:49
 **/
@Import(Student.class)
public class ImportTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ImportTest.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}
