package com.fc.test.design.template;

/**
 * @ClassName: HouseOne
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午11:02
 * @Version V1.0
 **/
public class HouseOne extends HouseTemplate {

    HouseOne(String name){
        super(name);
    }

    HouseOne(String name, boolean isBuildToilet){
        this(name);
        this.isBuildToilet = isBuildToilet;
    }

    public boolean isBuildToilet;

    @Override
    protected void buildDoor() {
        System.out.println(name +"的门要采用防盗门");
    }

    @Override
    protected void buildWindow() {
        System.out.println(name + "的窗户要面向北方");
    }

    @Override
    protected void buildWall() {
        System.out.println(name + "的墙使用大理石建造");
    }

    @Override
    protected void buildBase() {
        System.out.println(name + "的地基使用钢铁地基");
    }

    @Override
    protected void buildToilet() {
        System.out.println(name + "的厕所建在东南角");
    }

    @Override
    public boolean isBuildToilet(){
        return isBuildToilet;
    }

}