package com.fc.test.design.template;

/**
 * @ClassName: ChildrenClass
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午9:44
 * @Version V1.0
 **/
public class ChildrenClass {

    public static void main(String[] args) {
        AbstractParent parent = new AbstractParent() {
            @Override
            public Student initStudent() {
                return new Student(1L, "马称");
            }
        };

        Student student = parent.initStudent();
        Student student1 = parent.buildStudent();


    }
}
