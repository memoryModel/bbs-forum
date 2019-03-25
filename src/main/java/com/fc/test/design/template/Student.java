package com.fc.test.design.template;

import lombok.Data;

/**
 * @ClassName: Student
 * @Description: Test
 * @Author mac
 * @Date 2019/3/9 下午9:43
 * @Version V1.0
 **/
@Data
public class Student {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    public Student() {

    }

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student initStudent() {
        this.name = "马称";
        this.id = 100L;

        return this;
    }


}
