package com.fc.test.design.template;

/**
 * @ClassName: HouseTemplate
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午11:01
 * @Version V1.0
 **/
public abstract class HouseTemplate {

    protected HouseTemplate(String name){
        this.name = name;
    }

    protected String name;

    protected abstract void buildDoor();

    protected abstract void buildWindow();

    protected abstract void buildWall();

    protected abstract void buildBase();

    protected abstract void buildToilet();

    //钩子方法
    public boolean isBuildToilet(){
        return true;
    }

    //公共逻辑
    public final void buildHouse(){

        buildBase();
        buildWall();
        buildDoor();
        buildWindow();
        if(isBuildToilet()){
            buildToilet();
        }
    }

}
